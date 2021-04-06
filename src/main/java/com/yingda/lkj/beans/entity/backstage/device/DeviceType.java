package com.yingda.lkj.beans.entity.backstage.device;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 设备类型
 *
 * @author hood  2019/12/30
 */
@Entity
@Table(name = "device_type", schema = "illustrious", catalog = "")
public class DeviceType {

    // 信号机的对应的设备类型id
    public static final String SEMAPHORE_ID = "78a68ad2-8760-48af-bb5c-a78141ea8d53";


    // expired字段，使用中 / 过期
    public static final byte USING = 0;
    public static final byte EXPIRED = 1;

    private String id;
    private String name;
    private byte expired;
    private int seq;
    private Timestamp addTime;
    private Timestamp updateTime;

    // page filed
    private String extendFiledInfo;
    private boolean selected = false;
    private String deviceSubTypeInfo;
    private List<DeviceSubType> deviceSubTypeList;

    public DeviceType() {
    }

    public DeviceType(DeviceType pageDeviceType) {
        this.id = UUID.randomUUID().toString();
        this.name = pageDeviceType.name;
        this.expired = USING;
        this.seq = 1;
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
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "expired", nullable = false)
    public byte getExpired() {
        return expired;
    }

    public void setExpired(byte expired) {
        this.expired = expired;
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
    public String getExtendFiledInfo() {
        return extendFiledInfo;
    }

    public void setExtendFiledInfo(String extendFiledInfo) {
        this.extendFiledInfo = extendFiledInfo;
    }

    @Transient
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Transient
    public String getDeviceSubTypeInfo() {
        return deviceSubTypeInfo;
    }

    public void setDeviceSubTypeInfo(String deviceSubTypeInfo) {
        this.deviceSubTypeInfo = deviceSubTypeInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceType that = (DeviceType) o;
        return expired == that.expired &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(addTime, that.addTime) &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, expired, addTime, updateTime);
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
    public List<DeviceSubType> getDeviceSubTypeList() {
        return deviceSubTypeList;
    }

    public void setDeviceSubTypeList(List<DeviceSubType> deviceSubTypeList) {
        this.deviceSubTypeList = deviceSubTypeList;
    }
}
