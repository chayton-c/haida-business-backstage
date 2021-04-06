package com.yingda.lkj.beans.entity.backstage.measurement;

import com.yingda.lkj.beans.pojo.utils.UnitPojo;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 设备维护参数，用于比较设备属性，由自由测量etms数据和任务etms数据组成
 *
 * @author hood  2020/6/10
 */
@Entity
@Table(name = "device_maintenance_parameter", schema = "illustrious", catalog = "")
public class DeviceMaintenanceParameter {

    private static final byte ETMS_TASK = 0;
    private static final byte ETMS_FREE = 1;

    private String id;
    private String sourceDataId; // 来源数据id(free_device_maintenance_record.id或measurement_item_field_value.id)
    private byte sourceDataType; // 来源数据类型，分为etms任务测量和etms自由测量
    private String deviceId;
    private String deviceMeasurementItemId; // 设备测量字段id
    private String executeUserNames; // 执行人姓名
    private double value;
    private String measurementUnitId;
    private String unitName;
    private Timestamp measurementTime;
    private Timestamp addTime;

    // pageFields
    private String deviceName;
    private String deviceMeasurementItemName;

    public DeviceMaintenanceParameter() {
    }

    public DeviceMaintenanceParameter(MeasurementItemFieldValue measurementItemFieldValue, UnitPojo unitPojo, String measurementUnitId, String executeUserNames) {
        this.id = UUID.randomUUID().toString();
        this.addTime = new Timestamp(System.currentTimeMillis());

        this.deviceMeasurementItemId = measurementItemFieldValue.getDeviceMeasurementItemId();
        this.deviceId = measurementItemFieldValue.getDeviceId();
        this.sourceDataId = measurementItemFieldValue.getId();
        this.sourceDataType = ETMS_TASK;
        this.executeUserNames = executeUserNames;
        this.value = unitPojo.getValue();
        this.unitName = unitPojo.getUnitName();
        this.measurementUnitId = measurementUnitId;
        this.measurementTime = measurementItemFieldValue.getAddTime();
    }

    public DeviceMaintenanceParameter(FreeDeviceMaintenanceRecord freeDeviceMaintenanceRecord, String measurementUnitId, UnitPojo unitPojo
            , String executeUserNames, String deviceMeasurementItemId) {
        this.id = UUID.randomUUID().toString();
        this.addTime = new Timestamp(System.currentTimeMillis());

        this.deviceMeasurementItemId = deviceMeasurementItemId;
        this.sourceDataId = freeDeviceMaintenanceRecord.getId();
        this.deviceId = freeDeviceMaintenanceRecord.getDeviceId();
        this.sourceDataType = ETMS_FREE;
        this.executeUserNames = executeUserNames;
        this.value = unitPojo.getValue();
        this.unitName = unitPojo.getUnitName();
        this.measurementUnitId = measurementUnitId;
        this.measurementTime = freeDeviceMaintenanceRecord.getMeasurementEndTime();
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
    @Column(name = "value", nullable = false, precision = 0)
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Basic
    @Column(name = "measurement_unit_id", nullable = false, length = 36)
    public String getMeasurementUnitId() {
        return measurementUnitId;
    }

    public void setMeasurementUnitId(String measurementUnitId) {
        this.measurementUnitId = measurementUnitId;
    }

    @Basic
    @Column(name = "unit_name", nullable = false, length = 255)
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Basic
    @Column(name = "measurement_time", nullable = true)
    public Timestamp getMeasurementTime() {
        return measurementTime;
    }

    public void setMeasurementTime(Timestamp measurementTime) {
        this.measurementTime = measurementTime;
    }

    @Basic
    @Column(name = "add_time", nullable = true)
    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceMaintenanceParameter that = (DeviceMaintenanceParameter) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(value, that.value) &&
                Objects.equals(measurementUnitId, that.measurementUnitId) &&
                Objects.equals(unitName, that.unitName) &&
                Objects.equals(measurementTime, that.measurementTime) &&
                Objects.equals(addTime, that.addTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, measurementUnitId, unitName, measurementTime, addTime);
    }

    @Basic
    @Column(name = "source_data_id", nullable = false, length = 36)
    public String getSourceDataId() {
        return sourceDataId;
    }

    public void setSourceDataId(String sourceDataId) {
        this.sourceDataId = sourceDataId;
    }

    @Basic
    @Column(name = "source_data_type", nullable = false)
    public byte getSourceDataType() {
        return sourceDataType;
    }

    public void setSourceDataType(byte sourceDataType) {
        this.sourceDataType = sourceDataType;
    }

    @Basic
    @Column(name = "device_measurement_item_id", nullable = false, length = 36)
    public String getDeviceMeasurementItemId() {
        return deviceMeasurementItemId;
    }

    public void setDeviceMeasurementItemId(String deviceMeasurementItemId) {
        this.deviceMeasurementItemId = deviceMeasurementItemId;
    }

    @Basic
    @Column(name = "device_id", nullable = false, length = 36)
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Transient
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Transient
    public String getDeviceMeasurementItemName() {
        return deviceMeasurementItemName;
    }

    public void setDeviceMeasurementItemName(String deviceMeasurementItemName) {
        this.deviceMeasurementItemName = deviceMeasurementItemName;
    }

    @Basic
    @Column(name = "execute_user_names", nullable = false, length = 255)
    public String getExecuteUserNames() {
        return executeUserNames;
    }

    public void setExecuteUserNames(String executeUserNames) {
        this.executeUserNames = executeUserNames;
    }

}
