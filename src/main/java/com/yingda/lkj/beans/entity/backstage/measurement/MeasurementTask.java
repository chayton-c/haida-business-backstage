package com.yingda.lkj.beans.entity.backstage.measurement;

import com.yingda.lkj.beans.entity.backstage.device.DeviceMaintenancePlan;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * 测量任务
 *
 * @author hood  2020/3/11
 */
@Entity
@Table(name = "measurement_task")
public class MeasurementTask {

    // finishedStatus字段
    public static final byte PENDING_SUBMIT = 0; // 待发起
    public static final byte PENDING_HANDLE = 1; // 待处理
    public static final byte COMPLETED = 2; // 完成
    public static final byte CLOSED = 3; // 已关闭
    public static final byte MISSED = 4; // 漏检

    // notCurrentExecuted字段
    public static final byte CURRENT_EXECUTED = 0;
    public static final byte NOT_CURRENT_EXECUTED = 1;

    private String id;
    private String deviceMaintenancePlanId; // 设备养护计划id(手动生成非计划生成时该字段为空)
    private String submitUserName; // 提交人
    private String name; // 测量任务名
    private Timestamp executeTime; // 提交时间
    private byte finishedStatus; // 完成状态
    private byte repairClass; //  // com.yingda.lkj.beans.enums.repairclass.RepairClass
    private String remark;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp addTime;
    private Timestamp updateTime;

    // page fields
    private String executorNames;
    private String railwayLineName;
    private String workshopName;
    private List<MeasurementTaskDetail> measurementTaskDetailList;
    private String executeTimeStr;
    // 甘特图
    private boolean virtual;
    private List<String> executeUserNames;
    private List<String> deviceNames;
    // end of 甘特图

    public MeasurementTask() {
    }

    public MeasurementTask(DeviceMaintenancePlan deviceMaintenancePlan, String taskName, Timestamp nextTaskStartTime, Timestamp nextTaskEndTime) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        this.id = UUID.randomUUID().toString();
        this.deviceMaintenancePlanId = deviceMaintenancePlan.getId();
        this.submitUserName = deviceMaintenancePlan.getSubmitUserName();
        this.executeTime = null;
        this.finishedStatus = MeasurementTask.PENDING_SUBMIT;
        this.repairClass = deviceMaintenancePlan.getRepairClass();

        this.name = taskName;

        this.startTime = nextTaskStartTime;
        this.endTime = nextTaskEndTime;

        this.addTime = current;
        this.updateTime = current;
    }

    public MeasurementTask(MeasurementTask pageMeasurementTask) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        this.id = UUID.randomUUID().toString();
        this.deviceMaintenancePlanId = pageMeasurementTask.getDeviceMaintenancePlanId();
        this.executeTime = null;
        this.addTime = current;
        this.updateTime = current;
        this.finishedStatus = MeasurementTask.PENDING_HANDLE;
        this.repairClass = pageMeasurementTask.getRepairClass();

        this.name = pageMeasurementTask.getName();
        this.remark = pageMeasurementTask.getRemark();

        this.startTime = pageMeasurementTask.getStartTime();
        this.endTime = pageMeasurementTask.getEndTime();
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

    public void setSubmitUserName(String submitUserName) {
        this.submitUserName = submitUserName;
    }

    @Basic
    @Column(name = "execute_time", nullable = true)
    public Timestamp getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Timestamp executeTime) {
        this.executeTime = executeTime;
    }

    @Basic
    @Column(name = "finished_status", nullable = false)
    public byte getFinishedStatus() {
        return finishedStatus;
    }

    public void setFinishedStatus(byte finishedStatus) {
        this.finishedStatus = finishedStatus;
    }

    @Basic
    @Column(name = "remark", nullable = true, length = 255)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    public String getExecutorNames() {
        return executorNames;
    }

    public void setExecutorNames(String executorNames) {
        this.executorNames = executorNames;
    }

    @Transient
    public String getRailwayLineName() {
        return railwayLineName;
    }

    public void setRailwayLineName(String railwayLineName) {
        this.railwayLineName = railwayLineName;
    }

    @Transient
    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    @Transient
    public boolean isVirtual() {
        return virtual;
    }

    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
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
    @Column(name = "end_time", nullable = true)
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "device_maintenance_plan_id", nullable = true, length = 36)
    public String getDeviceMaintenancePlanId() {
        return deviceMaintenancePlanId;
    }

    public void setDeviceMaintenancePlanId(String deviceMaintenancePlanId) {
        this.deviceMaintenancePlanId = deviceMaintenancePlanId;
    }

    @Transient
    public List<String> getExecuteUserNames() {
        return executeUserNames;
    }

    public void setExecuteUserNames(List<String> executeUserNames) {
        this.executeUserNames = executeUserNames;
    }

    @Transient
    public List<String> getDeviceNames() {
        return deviceNames;
    }

    public void setDeviceNames(List<String> deviceNames) {
        this.deviceNames = deviceNames;
    }

    @Basic
    @Column(name = "repair_class", nullable = false)
    public byte getRepairClass() {
        return repairClass;
    }

    public void setRepairClass(byte taskType) {
        this.repairClass = taskType;
    }

    @Transient
    public List<MeasurementTaskDetail> getMeasurementTaskDetailList() {
        return measurementTaskDetailList;
    }

    public void setMeasurementTaskDetailList(List<MeasurementTaskDetail> measurementTaskDetailList) {
        this.measurementTaskDetailList = measurementTaskDetailList;
    }

    @Transient
    public String getExecuteTimeStr() {
        return executeTimeStr;
    }

    public void setExecuteTimeStr(String executeTimeStr) {
        this.executeTimeStr = executeTimeStr;
    }
}
