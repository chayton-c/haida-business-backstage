package com.yingda.lkj.beans.pojo.measurement;

import lombok.Data;

import java.util.List;

/**
 * @author hood  2020/3/30
 */
@Data
public class MeasurementResultPojo {


    private String measurementTaskDetailId; // 测量结果id
    private String measurementTaskId; // 测量任务id
    private String measurementTaskName; // 测量任务名称
    private byte abnormal; // 是否异常
    private String executeUserName; // 执行人
    private List<String> values; // 测量结果值
}
