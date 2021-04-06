package com.yingda.lkj.service.impl.backstage.device;

import com.yingda.lkj.beans.entity.backstage.device.DeviceMaintenancePlanUser;
import com.yingda.lkj.beans.entity.system.User;
import com.yingda.lkj.dao.BaseDao;
import com.yingda.lkj.service.backstage.device.DeviceMaintenancePlanUserService;
import com.yingda.lkj.service.system.UserService;
import com.yingda.lkj.utils.StreamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author hood  2020/12/7
 */
@Service("deviceMaintenancePlanUserService")
public class DeviceMaintenancePlanUserServiceImpl implements DeviceMaintenancePlanUserService {

    @Autowired
    private BaseDao<DeviceMaintenancePlanUser> deviceMaintenancePlanUserBaseDao;
    @Autowired
    private UserService userService;

    @Override
    public List<DeviceMaintenancePlanUser> getByDeviceMaintenancePlanId(String deviceMaintenancePlanId) {
        return deviceMaintenancePlanUserBaseDao.find(
                "from DeviceMaintenancePlanUser where deviceMaintenancePlanId = :deviceMaintenancePlanId",
                Map.of("deviceMaintenancePlanId", deviceMaintenancePlanId)
        );
    }

    @Override
    public void saveExecuteUsers(String deviceMaintenancePlanId, List<String> executeUserIds) {
        if (executeUserIds.isEmpty())
            return; // 怎么可能

        deviceMaintenancePlanUserBaseDao.executeHql(
                "delete from DeviceMaintenancePlanUser where deviceMaintenancePlanId = :deviceMaintenancePlanId",
                Map.of("deviceMaintenancePlanId", deviceMaintenancePlanId)
        );
        List<User> users = userService.getByIds(executeUserIds);
        List<DeviceMaintenancePlanUser> deviceMaintenancePlanUsers =
                StreamUtil.getList(users, x -> new DeviceMaintenancePlanUser(x, deviceMaintenancePlanId));
        deviceMaintenancePlanUserBaseDao.bulkInsert(deviceMaintenancePlanUsers);
    }
}
