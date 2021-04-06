package com.yingda.lkj.beans.entity.backstage.device;

import com.yingda.lkj.beans.Constant;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementUnit;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "device_measurement_item", schema = "illustrious", catalog = "")
public class DeviceMeasurementItem {
    private String id;
    private String deviceTypeId;// 设备类型id
    private String measurementUnitId; // 测量类型id
    private String name;
    private int seq;
    private String description;
    private Timestamp addTime;
    private byte hide;

    // page field
    private MeasurementUnit measurementUnit;

    public DeviceMeasurementItem(DeviceMeasurementItem pageDeviceMeasurementItem) {
        this.id = UUID.randomUUID().toString();

        this.name = pageDeviceMeasurementItem.getName();
        this.deviceTypeId = pageDeviceMeasurementItem.getDeviceTypeId();
        this.measurementUnitId = pageDeviceMeasurementItem.getMeasurementUnitId();

        this.description = pageDeviceMeasurementItem.getDescription();
        this.hide = Constant.SHOW;
        this.seq = 0;
        this.addTime = new Timestamp(System.currentTimeMillis());
    }

    public DeviceMeasurementItem() {
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
    @Column(name = "device_type_id", nullable = false, length = 36)
    public String getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(String deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
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
    @Column(name = "seq", nullable = false)
    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    @Column(name = "hide", nullable = false)
    public byte getHide() {
        return hide;
    }

    public void setHide(byte hide) {
        this.hide = hide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceMeasurementItem that = (DeviceMeasurementItem) o;
        return seq == that.seq &&
                hide == that.hide &&
                Objects.equals(id, that.id) &&
                Objects.equals(deviceTypeId, that.deviceTypeId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(addTime, that.addTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deviceTypeId, name, seq, description, addTime, hide);
    }

    @Basic
    @Column(name = "measurement_unit_id", nullable = false, length = 36)
    public String getMeasurementUnitId() {
        return measurementUnitId;
    }

    public void setMeasurementUnitId(String measurementUnitId) {
        this.measurementUnitId = measurementUnitId;
    }

    @Transient
    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(MeasurementUnit measurementUnit) {
        this.measurementUnit = measurementUnit;
    }
}
