package com.yingda.lkj.beans.entity.backstage.device;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * 设备扩展数据
 *
 * @author hood  2019/12/30
 */
@Entity
@Table(name = "device_extend_values", schema = "illustrious", catalog = "")
public class DeviceExtendValues {
    private String id;
    private String deviceFieldId; // 对应的扩展字段
    private String deviceTypeId; // 设备类型id
    private String deviceId; // 设备id
    private String fieldValue; // 数据的值
    private Timestamp addTime;
    private Timestamp updateTime;

    // pageFields
    private String fieldName;

    public DeviceExtendValues() {
    }

    public DeviceExtendValues(Device device, String deviceFieldId, String fieldValue) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        this.id = UUID.randomUUID().toString();
        this.deviceTypeId = device.getDeviceTypeId();
        this.deviceId = device.getId();

        this.deviceFieldId = deviceFieldId;
        this.fieldValue = fieldValue;

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
    @Column(name = "device_field_id", nullable = false, length = 36)
    public String getDeviceFieldId() {
        return deviceFieldId;
    }

    public void setDeviceFieldId(String deviceFieldId) {
        this.deviceFieldId = deviceFieldId;
    }

    @Basic
    @Column(name = "device_type_id", nullable = false, length = 36)
    public String getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(String deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
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
    @Column(name = "field_value", nullable = true, length = 255)
    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceExtendValues that = (DeviceExtendValues) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(deviceFieldId, that.deviceFieldId) &&
                Objects.equals(deviceTypeId, that.deviceTypeId) &&
                Objects.equals(deviceId, that.deviceId) &&
                Objects.equals(fieldValue, that.fieldValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deviceFieldId, deviceTypeId, deviceId, fieldValue);
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

    @Transient
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
