package com.yingda.lkj.beans.pojo.app.etms.freeetms;

import lombok.Data;

/**
 * @author hood  2020/6/10
 */
@Data
public class AppFreeDeviceMaintenanceRecordFieldValue {
    private String value; // 测量值
    private String unit; // 测量单位
    private String subFunctionCode; // 子功能码
    private String mainFunctionCode; // 子功能码
    private String deviceMeasurementItemId;
}
