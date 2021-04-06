package com.yingda.lkj.beans.pojo.app.etms;

import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementUnit;
import lombok.Data;

import java.util.List;

/**
 * @author hood  2020/5/13
 */
@Data
public class AppDeviceMaintenanceRecord {
    private String id; // 测量字段id
    private String deviceId;
    private String isTask;
    private String latitude;
    private String longitude;
    private String measurementItemId;
    private String startTime;
    private String timer;
    private String userId;
    private String measurementTaskDetailId;
    private String value;
}
