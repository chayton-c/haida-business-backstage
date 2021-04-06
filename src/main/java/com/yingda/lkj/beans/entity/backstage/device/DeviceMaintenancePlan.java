package com.yingda.lkj.beans.entity.backstage.device;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * 设备养护计划表
 *
 * 计划使用到的策略
 * @see com.yingda.lkj.beans.enums.devicemaintenance.DeviceMaintenancePlanStrategy
 * @author hood  2020/3/30
 */
@Entity
@Table(name = "device_maintenance_plan")
public class DeviceMaintenancePlan {

    // repairClass字段
    /** 日常养护 */
    public static final byte DAILY_MAINTENANCE = 0;
    /** 集中检修 */
    public static final byte CENTRALIZED_MAINTENANCE = 1;
    /** 集中整治 */
    public static final byte CENTRALIZED_REMEDIATION = 2;

    private String id;
    private String submitUserName; // 提交人
    private String name; // 计划名称
    private String code; // 计划编码
    private byte executionStrategy; // 执行策略，DeviceMaintenancePlanStrategy维护了执行策略
    // 执行周期(如execution_strategy = DeviceMaintenancePlanStrategy.PER_MONTH时表示每个月执行几次，= PER_FEW_DAYS时表示每隔多少天执行一次)
    private int executionCycle;
    // 执行日期(如execution_strategy = DeviceMaintenancePlanStrategy.DESIGNATED_DAY_OF_THE_YEAR表示本年的哪几天执行，= DESIGNATED_DAY_OF_THE_MONTH表示本月的哪几天执行)
    private String executionDate;
    private Integer duration; // 如该字段非空，任务期限(小时) 任务开始后，超过{duration}小时后，任务关闭，executeStatus修改为漏检
    private Timestamp previousCreateTaskDate; // 上次生成任务时间
    private Timestamp nextTaskStartTime; // 下次任务时间
    private byte stopped; // Constant.FALSE，使用中，Constant.TRUE已停用
    private byte repairClass; // 计划类型
    private Timestamp startTime;
    private Timestamp addTime;
    private Timestamp updateTime;

    // pageFields
    private String executorNames;
    private BigInteger deviceQuantity;

    /**
     * 1
     */
    public DeviceMaintenancePlan() {
    }

    public DeviceMaintenancePlan(DeviceMaintenancePlan pageDeviceMaintenancePlan) {
        this.id = UUID.randomUUID().toString();
        this.submitUserName = pageDeviceMaintenancePlan.submitUserName;
        this.name = pageDeviceMaintenancePlan.name;
        this.code = pageDeviceMaintenancePlan.code;
        this.executionStrategy = pageDeviceMaintenancePlan.executionStrategy;
        this.executionCycle = pageDeviceMaintenancePlan.executionCycle;
        this.executionDate = pageDeviceMaintenancePlan.executionDate;
        this.duration = pageDeviceMaintenancePlan.duration;
        this.stopped = pageDeviceMaintenancePlan.stopped;
        this.repairClass = pageDeviceMaintenancePlan.repairClass;
        this.startTime = pageDeviceMaintenancePlan.startTime;
        this.addTime = new Timestamp(System.currentTimeMillis());
//        this.previousCreateTaskDate = pageDeviceMaintenancePlan.
//        this.nextTaskDate = pageDeviceMaintenancePlan.
//        this.updateTime = t
    }

    @Id
    @Column(name = "id", nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "submit_user_name", nullable = false, length = 36)
    public String getSubmitUserName() {
        return submitUserName;
    }

    public void setSubmitUserName(String submitUserId) {
        this.submitUserName = submitUserId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "execution_strategy", nullable = false)
    public byte getExecutionStrategy() {
        return executionStrategy;
    }

    public void setExecutionStrategy(byte executionStrategy) {
        this.executionStrategy = executionStrategy;
    }

    @Basic
    @Column(name = "execution_cycle", nullable = false)
    public int getExecutionCycle() {
        return executionCycle;
    }

    public void setExecutionCycle(int executionCycle) {
        this.executionCycle = executionCycle;
    }

    @Basic
    @Column(name = "execution_date", nullable = true, length = 255)
    public String getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(String executionDate) {
        this.executionDate = executionDate;
    }

    @Basic
    @Column(name = "duration", nullable = true)
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "repair_class", nullable = false)
    public byte getRepairClass() {
        return repairClass;
    }

    public void setRepairClass(byte repairClass) {
        this.repairClass = repairClass;
    }

    @Basic
    @Column(name = "start_time", nullable = true)
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "stopped", nullable = false)
    public byte getStopped() {
        return stopped;
    }

    public void setStopped(byte stopped) {
        this.stopped = stopped;
    }

    @Basic
    @Column(name = "previous_create_task_date", nullable = true)
    public Timestamp getPreviousCreateTaskDate() {
        return previousCreateTaskDate;
    }

    public void setPreviousCreateTaskDate(Timestamp previousCreateTaskDate) {
        this.previousCreateTaskDate = previousCreateTaskDate;
    }

    @Basic
    @Column(name = "add_time", nullable = true)
    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    @Basic
    @Column(name = "update_time", nullable = true)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceMaintenancePlan that = (DeviceMaintenancePlan) o;
        return executionStrategy == that.executionStrategy &&
                executionCycle == that.executionCycle &&
                stopped == that.stopped &&
                repairClass == that.repairClass &&
               Objects.equals(id, that.id) &&
               Objects.equals(submitUserName, that.submitUserName) &&
               Objects.equals(name, that.name) &&
               Objects.equals(executionDate, that.executionDate) &&
               Objects.equals(duration, that.duration) &&
               Objects.equals(previousCreateTaskDate, that.previousCreateTaskDate) &&
               Objects.equals(startTime, that.startTime) &&
               Objects.equals(addTime, that.addTime) &&
               Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, submitUserName, name, executionStrategy, executionCycle, executionDate, duration, previousCreateTaskDate, stopped, repairClass, startTime, addTime, updateTime);
    }

    @Basic
    @Column(name = "next_task_start_time", nullable = true)
    public Timestamp getNextTaskStartTime() {
        return nextTaskStartTime;
    }

    public void setNextTaskStartTime(Timestamp nextTaskDate) {
        this.nextTaskStartTime = nextTaskDate;
    }

    @Basic
    @Column(name = "code", nullable = false, length = 255)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Transient
    public String getExecutorNames() {
        return executorNames;
    }

    public void setExecutorNames(String executorNames) {
        this.executorNames = executorNames;
    }

    @Transient
    public BigInteger getDeviceQuantity() {
        return deviceQuantity;
    }

    public void setDeviceQuantity(BigInteger deviceQuantity) {
        this.deviceQuantity = deviceQuantity;
    }
}

