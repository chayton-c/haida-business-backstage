package com.yingda.lkj.service.backstage.device;

import com.yingda.lkj.beans.entity.backstage.device.DeviceMaintenancePlanUser;

import java.util.List;

/**
 * @author hood  2020/12/7
 */
public interface DeviceMaintenancePlanUserService {
    List<DeviceMaintenancePlanUser> getByDeviceMaintenancePlanId(String deviceMaintenancePlanId);

    void saveExecuteUsers(String deviceMaintenancePlanId, List<String> executeUserIds);
}
