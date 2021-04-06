package com.yingda.lkj.service.impl.backstage.device;

import com.yingda.lkj.beans.Constant;
import com.yingda.lkj.beans.entity.backstage.device.DeviceMaintenancePlan;
import com.yingda.lkj.beans.entity.backstage.device.DeviceMaintenancePlanDevice;
import com.yingda.lkj.beans.entity.backstage.device.DeviceMaintenancePlanUser;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementTask;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementTaskDetail;
import com.yingda.lkj.beans.entity.backstage.organization.Organization;
import com.yingda.lkj.beans.entity.system.User;
import com.yingda.lkj.beans.enums.devicemaintenance.DeviceMaintenancePlanStrategy;
import com.yingda.lkj.dao.BaseDao;
import com.yingda.lkj.service.backstage.device.DeviceMaintenancePlanDeviceService;
import com.yingda.lkj.service.backstage.device.DeviceMaintenancePlanService;
import com.yingda.lkj.service.backstage.device.DeviceMaintenancePlanUserService;
import com.yingda.lkj.service.backstage.measurement.MeasurementTaskExecuteUserService;
import com.yingda.lkj.service.backstage.measurement.MeasurementTaskService;
import com.yingda.lkj.service.backstage.organization.OrganizationClientService;
import com.yingda.lkj.utils.StreamUtil;
import com.yingda.lkj.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hood  2020/4/3
 */
@Service("deviceMaintenancePlanService")
public class DeviceMaintenancePlanServiceImpl implements DeviceMaintenancePlanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceMaintenancePlanServiceImpl.class);

    @Autowired
    private BaseDao<DeviceMaintenancePlan> deviceMaintenancePlanBaseDao;
    @Autowired
    private BaseDao<DeviceMaintenancePlanUser> deviceMaintenancePlanUserBaseDao;
    @Autowired
    private MeasurementTaskService measurementTaskService;
    @Autowired
    private DeviceMaintenancePlanUserService deviceMaintenancePlanUserService;
    @Autowired
    private DeviceMaintenancePlanDeviceService deviceMaintenancePlanDeviceService;

    @Override
    public DeviceMaintenancePlan getById(String id) {
        return deviceMaintenancePlanBaseDao.get(DeviceMaintenancePlan.class, id);
    }

    @Override
    public void delete(String id) {
        deviceMaintenancePlanBaseDao.executeHql(
                "delete from DeviceMaintenancePlan where id = :id",
                Map.of("id", id)
        );
    }

    @Override
    public DeviceMaintenancePlan saveOrUpdate(DeviceMaintenancePlan pageDeviceMaintenancePlan) {
        String id = pageDeviceMaintenancePlan.getId();
        DeviceMaintenancePlan deviceMaintenancePlan = StringUtils.isNotEmpty(id) ?
                getById(id) : new DeviceMaintenancePlan(pageDeviceMaintenancePlan);
        BeanUtils.copyProperties(pageDeviceMaintenancePlan, deviceMaintenancePlan, "id", "addTime");
        deviceMaintenancePlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        deviceMaintenancePlanBaseDao.saveOrUpdate(deviceMaintenancePlan);
        return deviceMaintenancePlan;
    }

    @Override
    public void timedGenerateMeasurementTask() {
        // 查询所有的周期计划
        List<DeviceMaintenancePlan> deviceMaintenancePlans = deviceMaintenancePlanBaseDao.find(
                "from DeviceMaintenancePlan where stopped = :stopped",
                Map.of("stopped", Constant.TRUE)
        );

        for (DeviceMaintenancePlan deviceMaintenancePlan : deviceMaintenancePlans)
            generateMeasurementTask(deviceMaintenancePlan);
    }

    @Override
    public Map<String, List<String>> getExecuteUserNames(List<DeviceMaintenancePlan> deviceMaintenancePlans) {
        List<String> deviceMaintenancePlanIds = StreamUtil.getList(deviceMaintenancePlans, DeviceMaintenancePlan::getId);
        List<DeviceMaintenancePlanUser> deviceMaintenancePlanUsers = deviceMaintenancePlanUserBaseDao.find(
                "from DeviceMaintenancePlanUser where deviceMaintenancePlanId in :deviceMaintenancePlanIds",
                Map.of("deviceMaintenancePlanIds", deviceMaintenancePlanIds)
        );

        // key:计划id value:执行人list
        Map<String, List<DeviceMaintenancePlanUser>> collect =
                deviceMaintenancePlanUsers.stream().collect(Collectors.groupingBy(DeviceMaintenancePlanUser::getDeviceMaintenancePlanId));

        Map<String, List<String>> returnMap = new HashMap<>();
        for (Map.Entry<String, List<DeviceMaintenancePlanUser>> entry : collect.entrySet()) {
            List<DeviceMaintenancePlanUser> executeUsers = entry.getValue();
            String planId = entry.getKey();

            List<String> executeUserName =
                    executeUsers.stream().map(DeviceMaintenancePlanUser::getExecuteUserDisplayName).collect(Collectors.toList());
            returnMap.put(planId, executeUserName);
        }

        return returnMap;
    }

    /**
     * 周期计划生成测量计划
     */
    @Override
    public void generateMeasurementTask(String deviceMaintenancePlanId) {
        generateMeasurementTask(getById(deviceMaintenancePlanId));
    }

    private void generateMeasurementTask(DeviceMaintenancePlan deviceMaintenancePlan) {
        String deviceMaintenancePlanId = deviceMaintenancePlan.getId();

        // 周期计划设备副表
        List<DeviceMaintenancePlanDevice> deviceMaintenancePlanDevices =
                deviceMaintenancePlanDeviceService.getByDeviceMaintenancePlanId(deviceMaintenancePlanId);
        if (deviceMaintenancePlanDevices.isEmpty())
            return;

        MeasurementTask rawMeasurementTask =
                DeviceMaintenancePlanStrategy.getStrategy(deviceMaintenancePlan).createNextTask(deviceMaintenancePlan);

        if (rawMeasurementTask == null)
            return;

        Timestamp nextTaskStartTime = deviceMaintenancePlan.getNextTaskStartTime();
        if (nextTaskStartTime != null)
            deviceMaintenancePlan.setPreviousCreateTaskDate(nextTaskStartTime);
        deviceMaintenancePlan.setPreviousCreateTaskDate(nextTaskStartTime);

        deviceMaintenancePlan.setNextTaskStartTime(rawMeasurementTask.getStartTime());
        deviceMaintenancePlanBaseDao.saveOrUpdate(deviceMaintenancePlan);

        // 周期计划执行人副表

        List<DeviceMaintenancePlanUser> deviceMaintenancePlanUsers =
                deviceMaintenancePlanUserService.getByDeviceMaintenancePlanId(deviceMaintenancePlanId);
        List<String> executeUserIds = deviceMaintenancePlanUsers.stream().
                map(DeviceMaintenancePlanUser::getExecuteUserId)
                .collect(Collectors.toList());// 执行人userIds

        measurementTaskService.saveOrUpdate(rawMeasurementTask, deviceMaintenancePlanDevices, executeUserIds);
    }

}
