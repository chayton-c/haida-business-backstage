package com.yingda.lkj.beans.pojo.app;

import com.yingda.lkj.beans.entity.backstage.device.Device;
import com.yingda.lkj.beans.entity.backstage.device.DeviceType;
import com.yingda.lkj.beans.entity.backstage.organization.Organization;
import com.yingda.lkj.utils.OptionalUtil;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author hood  2020/4/14
 */
@Data
public class AppDevice {
    private String deviceId;
    private String name;
    private String deviceCode;
    private String deviceTypeId;
    private String deviceTypeName;
    private Byte hasNFC;
    private String railwayLineId;
    private String stationId;
    private String workshopId;
    private String sectionName;
    private String workshopName;
    private String positionInfo;
    private String spare; // 使用情况
    private String manufacturerName; // 生产厂家
    private String remark; // 备注

    public AppDevice() {
    }

    public AppDevice(Device device, DeviceType deviceType, Organization workshop) {
        deviceId = device.getId();
        name = OptionalUtil.removeNull(device.getName());
        positionInfo = OptionalUtil.removeNull(device.getPositionInfo());
        spare = Device.USING == device.getSpare() ? "使用" : "备用";
        manufacturerName = OptionalUtil.removeNull(device.getManufacturerName());
        remark = OptionalUtil.removeNull(device.getRemark());

        deviceTypeName = deviceType.getName();

        workshopName = workshop.getName();
    }

}
