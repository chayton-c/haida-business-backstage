package com.yingda.lkj.beans.entity.backstage.device;

import com.yingda.lkj.beans.Constant;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * 设备扩展字段
 *
 * @author hood  2019/12/30
 */
@Entity
@Table(name = "device_extend_field", schema = "illustrious", catalog = "")
public class DeviceExtendField {

    // appendOnForm 字段
    public static byte NOT_APPEND_ON_FORM = 0;
    public static byte APPEND_ON_FORM = 1;

    private String id;
    private String name; // 字段名
    private String description; // 暂时不用，预留字段
    private String deviceTypeId; // 设备类型id
    private byte appendOnForm; // 该字段是否需要展示在表单上
    private int seq;
    private byte hide;
    private Timestamp addTime;


    // page fields
    private String value;


    public DeviceExtendField() {
    }

    public DeviceExtendField(String name, String deviceTypeId, byte appendOnForm, int seq) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.deviceTypeId = deviceTypeId;
        this.appendOnForm = appendOnForm;
        this.seq = seq;
        this.addTime = new Timestamp(System.currentTimeMillis());
        this.hide = Constant.SHOW;
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
    @Column(name = "description", length = 255, nullable = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    @Column(name = "append_on_form", nullable = false)
    public byte getAppendOnForm() {
        return appendOnForm;
    }

    public void setAppendOnForm(byte appendOnForm) {
        this.appendOnForm = appendOnForm;
    }

    @Transient
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "seq", nullable = false)
    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceExtendField that = (DeviceExtendField) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
}
