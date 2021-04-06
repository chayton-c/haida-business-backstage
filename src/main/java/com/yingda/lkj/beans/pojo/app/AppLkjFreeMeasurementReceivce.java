package com.yingda.lkj.beans.pojo.app;

import lombok.Data;

import java.util.List;

/**
 * 自由测量数据上传
 *
 * @author hood  2020/4/26
 */
@Data
public class AppLkjFreeMeasurementReceivce {
    private long complete_time;
    private double distance;
    private List<AppLkjFreeNodeReceive> deviceList;
}
