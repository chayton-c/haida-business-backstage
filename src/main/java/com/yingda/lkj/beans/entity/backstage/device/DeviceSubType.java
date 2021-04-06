package com.yingda.lkj.beans.entity.backstage.device;

import com.yingda.lkj.beans.Constant;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * 设备子类型，用于关联测量模板
 *
 * @author hood  2020/3/12
 */
@Entity
@Table(name = "device_sub_type", schema = "illustrious", catalog = "")
public class DeviceSubType {
    private String id;
    private String deviceTypeId; // 设备类型id
    private String name;
    private String description;
    private String remark;
    private Byte hide;
    private Timestamp addTime;
    private Timestamp updateTime;

    // page fields
    private String deviceTypeName;

    public DeviceSubType() {
    }

    public DeviceSubType(DeviceSubType pageDeviceSubType) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        this.id = UUID.randomUUID().toString();
        this.deviceTypeId = pageDeviceSubType.getDeviceTypeId();
        this.name = pageDeviceSubType.getName();
        this.description = pageDeviceSubType.getDescription();
        this.remark = pageDeviceSubType.getRemark();
        this.hide = Constant.SHOW;
        this.addTime = current;
        this.updateTime = current;
    }

    public DeviceSubType(String deviceTypeId, String name) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        this.id = UUID.randomUUID().toString();
        this.deviceTypeId = deviceTypeId;
        this.name = name;
        this.description = "";
        this.remark = "";
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
    @Column(name = "hide", nullable = true)
    public Byte getHide() {
        return hide;
    }

    public void setHide(Byte hide) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceSubType that = (DeviceSubType) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(deviceTypeId, that.deviceTypeId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(hide, that.hide) &&
                Objects.equals(addTime, that.addTime) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(deviceTypeName, that.deviceTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deviceTypeId, name, description, remark, hide, addTime, updateTime, deviceTypeName);
    }

    @Transient
    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }
}
