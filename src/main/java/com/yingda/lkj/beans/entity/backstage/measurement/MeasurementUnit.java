package com.yingda.lkj.beans.entity.backstage.measurement;

import com.yingda.lkj.beans.Constant;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * 测量单位，如：电压
 *
 * @author hood  2020/3/11
 */
@Entity
@Table(name = "measurement_unit", schema = "illustrious", catalog = "")
public class MeasurementUnit {
    // valueType字段
    public static final byte NUMBER = 0; // 数值
    public static final byte STRING = 1; // 字符
    public static final byte IMAGE = 2; // 图片
    public static final byte VIDEO = 3; // 视频
    public static final byte AUDIO = 4; // 音频

    private String id;
    private String groupName; // 分组名称
    private String name; // 名称,如：移频表载频测量（电压）
    private String unitName; // 单位名称,如：V
    private byte valueType; // 测量值类型
    private String description;
    private String mainFunctionCode; // 主功能码
    private String subFunctionCode; // 子功能码
    private byte hide;
    private String remark;
    private Timestamp addTime;
    private Timestamp updateTime;

    // pageField


    public MeasurementUnit() {
    }

    public MeasurementUnit(MeasurementUnit pageMeasurementUnit) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        this.id = UUID.randomUUID().toString();

        this.hide = pageMeasurementUnit.getHide();
        this.groupName = pageMeasurementUnit.getGroupName();
        this.name = pageMeasurementUnit.getName();
        this.unitName = pageMeasurementUnit.getUnitName();
        this.valueType= pageMeasurementUnit.getValueType();
        this.description = pageMeasurementUnit.getDescription();
        this.mainFunctionCode = pageMeasurementUnit.getMainFunctionCode();
        this.subFunctionCode = pageMeasurementUnit.getSubFunctionCode();
        this.remark = pageMeasurementUnit.getRemark();
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
    @Column(name = "main_function_code", nullable = false, length = 255)
    public String getMainFunctionCode() {
        return mainFunctionCode;
    }

    public void setMainFunctionCode(String mainFunctionCode) {
        this.mainFunctionCode = mainFunctionCode;
    }

    @Basic
    @Column(name = "sub_function_code", nullable = false, length = 255)
    public String getSubFunctionCode() {
        return subFunctionCode;
    }

    public void setSubFunctionCode(String subFunctionCode) {
        this.subFunctionCode = subFunctionCode;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeasurementUnit that = (MeasurementUnit) o;

        if (hide != that.hide) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (mainFunctionCode != null ? !mainFunctionCode.equals(that.mainFunctionCode) : that.mainFunctionCode != null) return false;
        if (subFunctionCode != null ? !subFunctionCode.equals(that.subFunctionCode) : that.subFunctionCode != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (addTime != null ? !addTime.equals(that.addTime) : that.addTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (mainFunctionCode != null ? mainFunctionCode.hashCode() : 0);
        result = 31 * result + (subFunctionCode != null ? subFunctionCode.hashCode() : 0);
        result = 31 * result + (int) hide;
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (addTime != null ? addTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "group_name", nullable = false, length = 255)
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
    @Column(name = "value_type", nullable = false)
    public byte getValueType() {
        return valueType;
    }

    public void setValueType(byte valueType) {
        this.valueType = valueType;
    }
}
