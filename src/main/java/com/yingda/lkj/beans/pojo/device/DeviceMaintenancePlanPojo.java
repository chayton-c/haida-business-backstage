package com.yingda.lkj.beans.pojo.device;

import lombok.Data;

import java.sql.Timestamp;
import java.util.*;

/**
 * 设备维护计划页前端pojo
 *
 * @author hood  2020/4/2
 */
@Data
public class DeviceMaintenancePlanPojo {

    private String id;
    private String workshopId; // 所属车间
    private String workshopName; // 所属车间名称
    private String planName; // 计划名称
    private String submitUserName; // 提交人
    private String executeUserNames; // 执行人(多执行人用逗号隔开)
    private byte executionStrategy; // 策略类型
    private int executionCycle; // 策略执行周期
    private String executionStrategyName; // 策略名
    private Timestamp executeTime; // 执行时间
    private byte executeStatus; // 计划进行状态
    private String executeStatusName; // 计划进行状态名称
    private String deviceTypeName; // 设备类型
    private String measurementTemplateName; // 测量模板
    private String deviceNames;

}
