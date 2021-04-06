package com.yingda.lkj.beans.entity.backstage.device;

import com.yingda.lkj.beans.Constant;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * 设备养护计划，设备或位置扩展表
 *
 * @author hood  2020/3/30
 */
@Entity
@Table(name = "device_maintenance_plan_device", schema = "power_plant_etms", catalog = "")
public class DeviceMaintenancePlanDevice {

    private static final byte DEVICE = 0;
    private static final byte STATION = 1;

    private String id;
    private String deviceId; // 设备id或位置id 我总不能写deviceIdOrStationId吧
    private String deviceMaintenancePlanId; // 养护计划id
    private String measurementTemplateId; // 测量模板id
    private byte hide;
    private int seq;
    private Timestamp addTime;
    private Timestamp updateTime;
    private byte dataType;

    // pageField
    private String deviceSubTypeId;
    private String deviceName;
    private String deviceCode;
    private String railwayLineName;
    private String stationName;
    private String deviceTypeName;
    private String deviceSubTypeName;
    private String measurementTemplateName;

    public DeviceMaintenancePlanDevice() {
    }

    public DeviceMaintenancePlanDevice(DeviceMaintenancePlanDevice pageDeviceMaintenancePlanDevice) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        this.id = pageDeviceMaintenancePlanDevice.id;
        this.deviceId = pageDeviceMaintenancePlanDevice.deviceId;
        this.deviceMaintenancePlanId = pageDeviceMaintenancePlanDevice.deviceMaintenancePlanId;
        this.measurementTemplateId = pageDeviceMaintenancePlanDevice.measurementTemplateId;
        this.hide = Constant.SHOW;
        this.seq = pageDeviceMaintenancePlanDevice.seq;
        this.addTime = current;
        this.updateTime = current;
        this.dataType = pageDeviceMaintenancePlanDevice.dataType;
    }

    public DeviceMaintenancePlanDevice(String deviceId, String deviceMaintenancePlanId, int seq) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        this.id = UUID.randomUUID().toString();

        this.deviceId = deviceId;
        this.deviceMaintenancePlanId = deviceMaintenancePlanId;
        this.seq = seq;

        this.hide = Constant.SHOW;
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
    @Column(name = "device_id", nullable = false, length = 36)
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Basic
    @Column(name = "device_maintenance_plan_id", nullable = false, length = 36)
    public String getDeviceMaintenancePlanId() {
        return deviceMaintenancePlanId;
    }

    public void setDeviceMaintenancePlanId(String deviceMaintenancePlanId) {
        this.deviceMaintenancePlanId = deviceMaintenancePlanId;
    }

    @Basic
    @Column(name = "hide", nullable = false)
    public byte getHide() {
        return hide;
    }

    public void setHide(byte hide) {
        this.hide = hide;
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
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceMaintenancePlanDevice that = (DeviceMaintenancePlanDevice) o;
        return hide == that.hide &&
                seq == that.seq &&
                Objects.equals(id, that.id) &&
                Objects.equals(deviceId, that.deviceId) &&
                Objects.equals(deviceMaintenancePlanId, that.deviceMaintenancePlanId) &&
                Objects.equals(measurementTemplateId, that.measurementTemplateId) &&
                Objects.equals(addTime, that.addTime) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(deviceName, that.deviceName) &&
                Objects.equals(deviceCode, that.deviceCode) &&
                Objects.equals(railwayLineName, that.railwayLineName) &&
                Objects.equals(stationName, that.stationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deviceId, deviceMaintenancePlanId, measurementTemplateId, hide, seq, addTime, updateTime, deviceName, deviceCode, railwayLineName, stationName);
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
    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    @Transient
    public String getRailwayLineName() {
        return railwayLineName;
    }

    public void setRailwayLineName(String railwayLineName) {
        this.railwayLineName = railwayLineName;
    }

    @Transient
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Basic
    @Column(name = "measurement_template_id", length = 36, nullable = true)
    public String getMeasurementTemplateId() {
        return measurementTemplateId;
    }

    public void setMeasurementTemplateId(String measurementTemplateId) {
        this.measurementTemplateId = measurementTemplateId;
    }

    @Transient
    public String getDeviceSubTypeId() {
        return deviceSubTypeId;
    }

    public void setDeviceSubTypeId(String deviceSubTypeId) {
        this.deviceSubTypeId = deviceSubTypeId;
    }

    @Transient
    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    @Transient
    public String getDeviceSubTypeName() {
        return deviceSubTypeName;
    }

    public void setDeviceSubTypeName(String deviceSubTypeName) {
        this.deviceSubTypeName = deviceSubTypeName;
    }

    @Transient
    public String getMeasurementTemplateName() {
        return measurementTemplateName;
    }

    public void setMeasurementTemplateName(String measurementTemplateName) {
        this.measurementTemplateName = measurementTemplateName;
    }

    @Basic
    @Column(name = "data_type", nullable = false)
    public Byte getDataType() {
        return dataType;
    }

    public void setDataType(Byte dataType) {
        this.dataType = dataType;
    }
}
