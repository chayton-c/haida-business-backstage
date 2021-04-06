package com.yingda.lkj.controller.backstage;

import com.yingda.lkj.beans.Constant;
import com.yingda.lkj.beans.entity.backstage.device.*;
import com.yingda.lkj.beans.entity.backstage.line.RailwayLine;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementTemplate;
import com.yingda.lkj.beans.entity.backstage.organization.Organization;
import com.yingda.lkj.beans.entity.system.User;
import com.yingda.lkj.beans.enums.devicemaintenance.DeviceMaintenancePlanStrategy;
import com.yingda.lkj.beans.exception.CustomException;
import com.yingda.lkj.beans.pojo.device.DeviceMaintenancePlanPojo;
import com.yingda.lkj.beans.system.Json;
import com.yingda.lkj.beans.system.JsonMessage;
import com.yingda.lkj.controller.BaseController;
import com.yingda.lkj.service.backstage.device.DeviceMaintenancePlanDeviceService;
import com.yingda.lkj.service.backstage.device.DeviceMaintenancePlanService;
import com.yingda.lkj.service.backstage.device.DeviceMaintenancePlanUserService;
import com.yingda.lkj.service.backstage.line.RailwayLineService;
import com.yingda.lkj.service.backstage.organization.OrganizationClientService;
import com.yingda.lkj.service.base.BaseService;
import com.yingda.lkj.service.system.UserService;
import com.yingda.lkj.utils.StreamUtil;
import com.yingda.lkj.utils.StringUtils;
import com.yingda.lkj.utils.hql.HqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.*;

/**
 * 设备维护计划页
 *
 * @author hood  2020/3/30
 */
@Controller
@RequestMapping("/backstage/deviceMaintenancePlan")
public class DeviceMaintenancePlanController extends BaseController {

    @Autowired
    private BaseService<DeviceMaintenancePlan> deviceMaintenancePlanBaseService;
    @Autowired
    private DeviceMaintenancePlanService deviceMaintenancePlanService;
    @Autowired
    private BaseService<DeviceMaintenancePlanDevice> deviceMaintenancePlanDeviceBaseService;
    @Autowired
    private BaseService<BigInteger> bigIntegerBaseService;
    @Autowired
    private DeviceMaintenancePlanDeviceService deviceMaintenancePlanDeviceService;
    @Autowired
    private DeviceMaintenancePlanUserService deviceMaintenancePlanUserService;
    @Autowired
    private UserService userService;

    private DeviceMaintenancePlan pageDeviceMaintenancePlan;

    @RequestMapping("")
    @ResponseBody
    public Json getList() throws Exception {
        Map<String, Object> attributes = new HashMap<>();

        String nameOrCode = req.getParameter("nameOrCode");
        String executorNames = req.getParameter("executorNames");
        String stopped = req.getParameter("stopped");

        Map<String, Object> params = new HashMap<>();
        String sql = """
                SELECT
                	plan.id AS id,
                	plan.`name` AS `name`,
                	plan.`code` AS `code`,
                	plan.execution_strategy AS executionStrategy,
                	plan.previous_create_task_date AS previousCreateTaskDate,
                	plan.next_task_start_time AS nextTaskStartTime,
                	plan.stopped AS stopped,
                	executeUsers.display_names AS executorNames,
                	devices.quantity AS deviceQuantity
                FROM
                	device_maintenance_plan plan
                	# 关联计划执行人表
                	LEFT JOIN ( 
                	    SELECT 
                	        GROUP_CONCAT( '  ', execute_user_display_name) AS display_names, 
                	        device_maintenance_plan_id 
                	    FROM device_maintenance_plan_user 
                	    GROUP BY device_maintenance_plan_id 
                	) executeUsers ON executeUsers.device_maintenance_plan_id = plan.id
                	# 关联计划设备表
                	LEFT JOIN ( 
                	    SELECT 
                	        COUNT(device_id) AS quantity, 
                	        device_maintenance_plan_id 
                	    FROM device_maintenance_plan_device 
                	    GROUP BY device_maintenance_plan_id 
                	) devices ON devices.device_maintenance_plan_id = plan.id
                WHERE
                    1 = 1	
                """;
        if (StringUtils.isNotEmpty(stopped)) {
            sql += "AND plan.stopped = :stopped\n";
            params.put("stopped", Byte.parseByte(stopped));
        }
        if (StringUtils.isNotEmpty(nameOrCode)) {
            sql  += "AND (plan.name LIKE :nameOrCode OR plan.code LIKE :nameOrCode)";
            params.put("nameOrCode", "%" + nameOrCode + "%");
        }
        if (StringUtils.isNotEmpty(executorNames)) {
            sql += "AND executeUsers.display_names like :executorNames\n";
            params.put("executorNames", "%" + executorNames + "%");
        }
        sql += "ORDER BY plan.update_time desc";

        List<DeviceMaintenancePlan> deviceMaintenancePlans = deviceMaintenancePlanBaseService.findSQL(
                sql, params, DeviceMaintenancePlan.class, page.getCurrentPage(), page.getPageSize()
        );

        List<BigInteger> count = bigIntegerBaseService.findSQL(HqlUtils.getCountSql(sql), params);
        page.setDataTotal(count);

        attributes.put("deviceMaintenancePlans", deviceMaintenancePlans);
        attributes.put("page", page);
        // 巡检计划策略
        attributes.put("deviceMaintenancePlanStrategies", DeviceMaintenancePlanStrategy.showdown());

        return new Json(JsonMessage.SUCCESS, attributes);
    }

    @RequestMapping("/info")
    @ResponseBody
    public Json info() {
        Map<String, Object> attributes = new HashMap<>();

        String id = pageDeviceMaintenancePlan.getId();

        DeviceMaintenancePlan deviceMaintenancePlan = StringUtils.isNotEmpty(id) ? deviceMaintenancePlanService.getById(id) : new DeviceMaintenancePlan();

        if (StringUtils.isNotEmpty(id)) {
            List<DeviceMaintenancePlanUser> deviceMaintenancePlanUsers = deviceMaintenancePlanUserService.getByDeviceMaintenancePlanId(id);
            List<String> userIds = StreamUtil.getList(deviceMaintenancePlanUsers, DeviceMaintenancePlanUser::getExecuteUserId);
            attributes.put("executeUsers", userService.getByIds(userIds));
        }

        attributes.put("deviceMaintenancePlan", deviceMaintenancePlan);
        attributes.put("deviceMaintenancePlanStrategies", DeviceMaintenancePlanStrategy.showdown());

        return new Json(JsonMessage.SUCCESS, attributes);
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Json saveOrUpdate() throws Exception {
        pageDeviceMaintenancePlan.setSubmitUserName(getUser().getDisplayName());
        DeviceMaintenancePlanStrategy.getStrategy(pageDeviceMaintenancePlan).checkPlanDataComplete(pageDeviceMaintenancePlan);
        DeviceMaintenancePlan deviceMaintenancePlan = deviceMaintenancePlanService.saveOrUpdate(pageDeviceMaintenancePlan);

        String executeUserIds = req.getParameter("executeUserIds"); // 肯定是非空，除非前端错了
        deviceMaintenancePlanUserService.saveExecuteUsers(deviceMaintenancePlan.getId(), Arrays.asList(executeUserIds.split(",")));

//        deviceMaintenancePlanService.generateMeasurementTask(deviceMaintenancePlan);

        return new Json(JsonMessage.SUCCESS);
    }

    @RequestMapping("/generate")
    @ResponseBody
    public Json generate() throws CustomException, ParseException {
        String id = req.getParameter("id");
        DeviceMaintenancePlan deviceMaintenancePlan = deviceMaintenancePlanService.getById(id);
        if (deviceMaintenancePlan == null)
            throw new CustomException(JsonMessage.DATE_HAS_BEEN_DELETED);

        deviceMaintenancePlanService.generateMeasurementTask(id);

        return new Json(JsonMessage.SUCCESS);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Json delete() {
        String id = req.getParameter("id");
        deviceMaintenancePlanService.delete(id);
        return new Json(JsonMessage.SUCCESS);
    }

    @RequestMapping("/test")
    @ResponseBody
    public Json test() throws ParseException {
        deviceMaintenancePlanService.timedGenerateMeasurementTask();
        return new Json(JsonMessage.SUCCESS);
    }

    @RequestMapping("/updateSeq")
    @ResponseBody
    public Json updateSeq() throws Exception {
        String deviceMaintenancePlanId = req.getParameter("id");
        int seq = Integer.parseInt(req.getParameter("seq"));

        DeviceMaintenancePlanDevice deviceMaintenancePlanDevice =
                deviceMaintenancePlanDeviceBaseService.get(DeviceMaintenancePlanDevice.class, deviceMaintenancePlanId);

        deviceMaintenancePlanDevice.setSeq(seq);
        deviceMaintenancePlanDevice.setUpdateTime(current());
        deviceMaintenancePlanDeviceBaseService.saveOrUpdate(deviceMaintenancePlanDevice);

        return new Json(JsonMessage.SUCCESS);
    }

    /**
     * 移除设备
     */
    @RequestMapping("/removeDeviceList")
    @ResponseBody
    public Json removeDeviceList() throws Exception {
        String[] deviceIds = req.getParameterMap().get("deviceIds[]");
        String[] deviceMaintenancePlanIds = req.getParameterMap().get("deviceMaintenancePlanId[]");

        if (StringUtils.isEmpty(deviceIds))
            return new Json(JsonMessage.DATA_NO_COMPLETE, "不能为空");

        List<DeviceMaintenancePlanDevice> deviceMaintenancePlanDevices = deviceMaintenancePlanDeviceBaseService.find(
                "from DeviceMaintenancePlanDevice where deviceId in :deviceIds and deviceMaintenancePlanId = :deviceMaintenancePlanId",
                Map.of("deviceIds", deviceIds, "deviceMaintenancePlanId", deviceMaintenancePlanIds[0])
        );

        for (DeviceMaintenancePlanDevice deviceMaintenancePlanDevice : deviceMaintenancePlanDevices)
            deviceMaintenancePlanDeviceBaseService.delete(deviceMaintenancePlanDevice);

        return new Json(JsonMessage.SUCCESS);
    }

    /**
     * 为计划设备表添加模板
     */
    @RequestMapping("/setMeasurementTemplates")
    @ResponseBody
    public Json setMeasurementTemplates() throws Exception {
        String measurementTemplateId = req.getParameter("measurementTemplateId");
        String deviceMaintenancePlanDeviceIds = req.getParameter("deviceMaintenancePlanDeviceIds");

        List<DeviceMaintenancePlanDevice> deviceMaintenancePlanDevices = deviceMaintenancePlanDeviceBaseService.find(
                "from DeviceMaintenancePlanDevice where id in :deviceMaintenancePlanDeviceIds",
                Map.of("deviceMaintenancePlanDeviceIds", deviceMaintenancePlanDeviceIds.split(","))
        );

        for (DeviceMaintenancePlanDevice deviceMaintenancePlanDevice : deviceMaintenancePlanDevices) {
            deviceMaintenancePlanDevice.setMeasurementTemplateId(measurementTemplateId);
            deviceMaintenancePlanDevice.setUpdateTime(current());
        }

        deviceMaintenancePlanDeviceBaseService.bulkInsert(deviceMaintenancePlanDevices);

        return new Json(JsonMessage.SUCCESS);
    }

    /**
     * 为计划添加设备
     */
    @ResponseBody
    @RequestMapping("/addDevice")
    public Json addDevice() throws Exception {

        return new Json(JsonMessage.SUCCESS);
    }


    @ModelAttribute
    public void setPageDeviceMaintenancePlan(DeviceMaintenancePlan pageDeviceMaintenancePlan) {
        this.pageDeviceMaintenancePlan = pageDeviceMaintenancePlan;
    }
}
