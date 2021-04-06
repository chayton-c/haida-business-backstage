package com.yingda.lkj.beans.pojo.app.etms.freeetms;

import lombok.Data;

import java.util.List;

/**
 * @author hood  2020/6/10
 */
@Data
public class AppFreeDeviceMaintenanceRecord {
    private String deviceId;
    private double lon;
    private double lat;
    private long startTime; // 开始测量时间
    private long timer; // 完成测量时间
    private String funCode; // 主功能码
    private String userId; // 测量人
    private List<AppFreeDeviceMaintenanceRecordFieldValue> value;
}
