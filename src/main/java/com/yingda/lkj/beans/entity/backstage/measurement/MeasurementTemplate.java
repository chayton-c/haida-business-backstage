package com.yingda.lkj.beans.entity.backstage.measurement;

import com.yingda.lkj.beans.Constant;
import com.yingda.lkj.utils.StringUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * 测量模板，如：电气特性模板
 *
 * @author hood  2020/3/11
 */
@Entity
@Table(name = "measurement_template", schema = "power_plant_etms", catalog = "")
public class MeasurementTemplate {

    // type字段
    public static final byte DEVICE = 0; // 设备巡检模板
    public static final byte STATION = 1; // 车站巡检模板

    private String id;
    private String deviceTypeId; // 设备类型id (type == DEVICE时使用)
    private String deviceSubTypeId; // 设备子类型id (type == DEVICE时使用)
    private String stationId; // 车站id(type == STATION时使用)
    private String name; // 测量模板名称
    private byte hide;
    private String description;
    private String remark;
    private byte repairClass; // com.yingda.lkj.beans.enums.repairclass.RepairClass
    private byte type; // 模板类型字段
    private Timestamp addTime;
    private Timestamp updateTime;

    // page fields
    private String deviceTypeName;
    private String deviceSubTypeName;
    private String stationName;
    private String repairClassName;

    public MeasurementTemplate() {
    }

    public MeasurementTemplate(MeasurementTemplate pageMeasurementTemplate) {
        this.id = UUID.randomUUID().toString();
        if (StringUtils.isNotEmpty(pageMeasurementTemplate.deviceTypeId))
            this.deviceTypeId = pageMeasurementTemplate.deviceTypeId;
        if (StringUtils.isNotEmpty(pageMeasurementTemplate.deviceSubTypeId))
            this.deviceSubTypeId = pageMeasurementTemplate.deviceSubTypeId;
        if (StringUtils.isNotEmpty(pageMeasurementTemplate.stationId))
            this.stationId = pageMeasurementTemplate.stationId;
        this.name = pageMeasurementTemplate.name;
        this.hide = Constant.SHOW;
        this.description = pageMeasurementTemplate.description;
        this.remark = pageMeasurementTemplate.remark;
        this.repairClass = pageMeasurementTemplate.repairClass;
        this.type = pageMeasurementTemplate.type;
        this.addTime = new Timestamp(System.currentTimeMillis());
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
    @Column(name = "device_type_id", nullable = true, length = 36)
    public String getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(String deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    @Basic
    @Column(name = "device_sub_type_id", nullable = true, length = 36)
    public String getDeviceSubTypeId() {
        return deviceSubTypeId;
    }

    public void setDeviceSubTypeId(String deviceSubTypeId) {
        this.deviceSubTypeId = deviceSubTypeId;
    }

    @Basic
    @Column(name = "station_id", nullable = true, length = 36)
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
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
    @Column(name = "hide", nullable = false)
    public byte getHide() {
        return hide;
    }

    public void setHide(byte hide) {
        this.hide = hide;
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
    @Column(name = "repair_class", nullable = false)
    public byte getRepairClass() {
        return repairClass;
    }

    public void setRepairClass(byte repairClass) {
        this.repairClass = repairClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementTemplate that = (MeasurementTemplate) o;
        return hide == that.hide &&
                repairClass == that.repairClass &&
                Objects.equals(id, that.id) &&
                Objects.equals(deviceSubTypeId, that.deviceSubTypeId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(addTime, that.addTime) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(deviceTypeId, that.deviceTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deviceSubTypeId, name, hide, description, remark, addTime, updateTime, repairClass, deviceTypeId);
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
    public String getRepairClassName() {
        return repairClassName;
    }

    public void setRepairClassName(String repairClassName) {
        this.repairClassName = repairClassName;
    }

    @Transient
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
