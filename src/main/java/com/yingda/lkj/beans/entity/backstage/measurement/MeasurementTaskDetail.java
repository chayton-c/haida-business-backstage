package com.yingda.lkj.beans.entity.backstage.measurement;

import com.yingda.lkj.beans.entity.backstage.device.DeviceMaintenancePlanDevice;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * 测量结果
 *
 * @author hood  2020/3/11
 */
@Entity
@Table(name = "measurement_task_detail", schema = "power_plant_etms", catalog = "")
public class MeasurementTaskDetail {

    // errorStatus
    public static final byte NOT_CHECKED = 0; // 未检修
    public static final byte ERROR_STATUS_NORMAL = 1; // 正常
    public static final byte ERROR = 2; // 异常
    public static final byte OMISSION = 3; // 漏检

    // finishedStatus字段
    public static final byte PENDING_SUBMIT = 0; // 待发起
    public static final byte PENDING_HANDLE = 1; // 待处理
    public static final byte COMPLETED = 2; // 完成
    public static final byte CLOSED = 3; // 已关闭
    public static final byte MISSED = 4; // 漏检

    // abnormal字段
    public static final byte ABNORMAL = 1;
    public static final byte NORMAL = 0;
    public static final byte NO_DATA = 2; // 没有数据

    private String id;
    private String measurementTemplateId; // 测量模板id
    private String measurementTaskId; // 测量任务id
    private String uploadorNames; // 上传任务的人(多个用逗号隔开)
    private String deviceId; // 设备id
    private byte dataType; // 数据类型
    private byte abnormal; // 是否包含异常数据
    private byte finishedStatus; // 完成状态
    private byte errorStatus; // 任务结果
    private int seq; // 执行顺序
    private Timestamp executeTime; // 提交时间
    private Timestamp addTime;
    private Timestamp updateTime;

    // page fields
    private String measurementTemplateName; // 测量模板名称
    private String deviceName; // 设备名称
    private String deviceCode; // 设备名称
    private String railwayLineName;
    private String stationId; // 设备所在车站id
    private String stationName;// 设备所在车站name
    private String executeUserNames; // 执行人
    private String startTime;
    private String endTime;
    private String measurementItemName; // 测量项名称，设备历史记录页使用
    private List<MeasurementItemFieldValue> measurementItemFieldValues; // 测量值

    public MeasurementTaskDetail() {
    }

    public MeasurementTaskDetail(String measurementTaskId, DeviceMaintenancePlanDevice deviceMaintenancePlanDevice) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        this.id = UUID.randomUUID().toString();
        this.measurementTemplateId = deviceMaintenancePlanDevice.getMeasurementTemplateId();
        this.measurementTaskId = measurementTaskId;
        this.deviceId = deviceMaintenancePlanDevice.getDeviceId();
        this.dataType = deviceMaintenancePlanDevice.getDataType();
        this.abnormal = NO_DATA;
        this.finishedStatus = PENDING_SUBMIT;
        this.errorStatus = NOT_CHECKED;
        this.seq = deviceMaintenancePlanDevice.getSeq();
//        this.executeTime = executeTime;
        this.addTime = current;
        this.updateTime = current;
    }

    public MeasurementTaskDetail(MeasurementTaskDetail pageMeasurementTaskDetail) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        this.id = UUID.randomUUID().toString();

        this.measurementTemplateId = pageMeasurementTaskDetail.getMeasurementTemplateId();
        this.measurementTaskId = pageMeasurementTaskDetail.getMeasurementTaskId();
        this.deviceId = pageMeasurementTaskDetail.getDeviceId();

        this.abnormal = NORMAL;
        this.finishedStatus = PENDING_HANDLE;
        this.executeTime = null;
        this.addTime = current;
        this.updateTime = current;
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
    @Column(name = "measurement_template_id", nullable = false, length = 36)
    public String getMeasurementTemplateId() {
        return measurementTemplateId;
    }

    public void setMeasurementTemplateId(String measurementTemplateId) {
        this.measurementTemplateId = measurementTemplateId;
    }

    @Basic
    @Column(name = "measurement_task_id", nullable = false, length = 36)
    public String getMeasurementTaskId() {
        return measurementTaskId;
    }

    public void setMeasurementTaskId(String measurementTaskId) {
        this.measurementTaskId = measurementTaskId;
    }

    @Basic
    @Column(name = "device_id", nullable = false, length = 36)
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Basic
    @Column(name = "abnormal", nullable = false)
    public byte getAbnormal() {
        return abnormal;
    }

    public void setAbnormal(byte abnormal) {
        this.abnormal = abnormal;
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
    @Column(name = "execute_time", nullable = true)
    public Timestamp getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Timestamp executeTime) {
        this.executeTime = executeTime;
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
    @Column(name = "error_status", nullable = false)
    public byte getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(byte errorStatus) {
        this.errorStatus = errorStatus;
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

        MeasurementTaskDetail that = (MeasurementTaskDetail) o;

        if (abnormal != that.abnormal) return false;
        if (finishedStatus != that.finishedStatus) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (measurementTemplateId != null ? !measurementTemplateId.equals(that.measurementTemplateId) : that.measurementTemplateId != null)
            return false;
        if (measurementTaskId != null ? !measurementTaskId.equals(that.measurementTaskId) : that.measurementTaskId != null)
            return false;
        if (deviceId != null ? !deviceId.equals(that.deviceId) : that.deviceId != null) return false;
        if (executeTime != null ? !executeTime.equals(that.executeTime) : that.executeTime != null) return false;
        if (addTime != null ? !addTime.equals(that.addTime) : that.addTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (measurementTemplateId != null ? measurementTemplateId.hashCode() : 0);
        result = 31 * result + (measurementTaskId != null ? measurementTaskId.hashCode() : 0);
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        result = 31 * result + (int) abnormal;
        result = 31 * result + (int) finishedStatus;
        result = 31 * result + (executeTime != null ? executeTime.hashCode() : 0);
        result = 31 * result + (addTime != null ? addTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @Transient
    public String getMeasurementTemplateName() {
        return measurementTemplateName;
    }

    public void setMeasurementTemplateName(String measurementTemplateName) {
        this.measurementTemplateName = measurementTemplateName;
    }

    @Transient
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Transient
    public String getRailwayLineName() {
        return railwayLineName;
    }

    public void setRailwayLineName(String railwayLineName) {
        this.railwayLineName = railwayLineName;
    }

    @Transient
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Transient
    public List<MeasurementItemFieldValue> getMeasurementItemFieldValues() {
        return measurementItemFieldValues;
    }

    public void setMeasurementItemFieldValues(List<MeasurementItemFieldValue> measurementItemFieldValues) {
        this.measurementItemFieldValues = measurementItemFieldValues;
    }

    @Transient
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Transient
    public String getExecuteUserNames() {
        return executeUserNames;
    }

    public void setExecuteUserNames(String executeUserNames) {
        this.executeUserNames = executeUserNames;
    }

    @Transient
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Transient
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "seq", nullable = false)
    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Transient
    public String getMeasurementItemName() {
        return measurementItemName;
    }

    public void setMeasurementItemName(String measurementItemName) {
        this.measurementItemName = measurementItemName;
    }

    @Basic
    @Column(name = "uploador_names", nullable = true, length = 255)
    public String getUploadorNames() {
        return uploadorNames;
    }

    public void setUploadorNames(String uploadorNames) {
        this.uploadorNames = uploadorNames;
    }

    @Basic
    @Column(name = "data_type", nullable = false)
    public byte getDataType() {
        return dataType;
    }

    public void setDataType(byte dataType) {
        this.dataType = dataType;
    }

    @Transient
    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }
}
