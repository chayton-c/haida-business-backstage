package com.yingda.lkj.beans.entity.backstage.measurement;

import com.yingda.lkj.beans.Constant;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * 测量项下需要测量的字段
 *
 * @author hood  2020/3/11
 */
@Entity
@Table(name = "measurement_item_field", schema = "power_plant_etms", catalog = "")
public class MeasurementItemField {

    private String id;
    private String name; // 测量字段名
    private String measurementUnitId; // 测试单位id
    private String measurementItemId; // 测量项id
    private String measurementTemplateId;
    private String deviceMeasurementItemId; // 设备测量字段id
    private Double maxValue; // 数据最大值
    private Double minValue; // 数据最小值
    private String correctValue; // 数据正确值(选项或文本输入的值，以此来判断)
    private int manHour; // 工时
    private byte hide;
    private String description;
    private String remark;
    private String groupId; // 分组id
    private String groupName; // 分组名
    private int seq; // 排序
    private Timestamp addTime;
    private Timestamp updateTime;

    // page fields
    private String unitName; // 测量字段的单位名称(毫安，伏特)
    private String fullName; // 前端显示的字段全名(把测试项的名字加到前面了),如：U灯——灯端电压
    private String measurementUnitName; // 对应测量单位名
    private List<Double> measurementItemFieldValues; // 测量数据折线图中，数据的值(看图就懂了)
    private String deviceMeasurementItemName; // 设备测量字段名
    private MeasurementUnit measurementUnit;
    private MeasurementItemFieldValue measurementItemFieldValue;

    // 作废的,写上不报错
    private String associationCode;

    public MeasurementItemField() {
    }

    public MeasurementItemField(MeasurementItemField pageMeasurementItemField) {
        this.id = UUID.randomUUID().toString();
        this.name = pageMeasurementItemField.name;
        this.measurementTemplateId = pageMeasurementItemField.measurementTemplateId;
        this.measurementUnitId = pageMeasurementItemField.measurementUnitId;
//        this.measurementItemId = pageMeasurementItemField;
//        this.deviceMeasurementItemId = deviceMeasurementItemId;
//        this.associationCode = associationCode;
        this.maxValue = pageMeasurementItemField.maxValue;
        this.minValue = pageMeasurementItemField.minValue;
        this.correctValue = pageMeasurementItemField.correctValue;
        this.manHour = pageMeasurementItemField.manHour;
        this.hide = Constant.SHOW;
        this.description = pageMeasurementItemField.description;
        this.remark = pageMeasurementItemField.remark;
//        this.groupId = groupId;
//        this.groupName = groupName;
        this.addTime = new Timestamp(System.currentTimeMillis());
//        this.updateTime = updateTime;
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
    @Column(name = "measurement_unit_id", nullable = false, length = 36)
    public String getMeasurementUnitId() {
        return measurementUnitId;
    }

    public void setMeasurementUnitId(String measurementUnitId) {
        this.measurementUnitId = measurementUnitId;
    }

    @Basic
    @Column(name = "measurement_item_id", nullable = true, length = 36)
    public String getMeasurementItemId() {
        return measurementItemId;
    }

    public void setMeasurementItemId(String measurementItemId) {
        this.measurementItemId = measurementItemId;
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

    @Transient
    public String getMeasurementUnitName() {
        return measurementUnitName;
    }

    public void setMeasurementUnitName(String measurementUnitName) {
        this.measurementUnitName = measurementUnitName;
    }

    @Basic
    @Column(name = "max_value", nullable = true, precision = 0)
    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    @Basic
    @Column(name = "min_value", nullable = true, precision = 0)
    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    @Basic
    @Column(name = "man_hour", nullable = false)
    public int getManHour() {
        return manHour;
    }

    public void setManHour(int manHour) {
        this.manHour = manHour;
    }

    @Transient
    public List<Double> getMeasurementItemFieldValues() {
        return measurementItemFieldValues;
    }

    public void setMeasurementItemFieldValues(List<Double> measurementItemFieldValues) {
        this.measurementItemFieldValues = measurementItemFieldValues;
    }

    @Transient
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Basic
    @Column(name = "group_id", nullable = true, length = 36)
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "group_name", nullable = true, length = 36)
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Basic
    @Column(name = "device_measurement_item_id", nullable = true, length = 36)
    public String getDeviceMeasurementItemId() {
        return deviceMeasurementItemId;
    }

    public void setDeviceMeasurementItemId(String deviceMeasurementItemId) {
        this.deviceMeasurementItemId = deviceMeasurementItemId;
    }

    @Transient
    public String getDeviceMeasurementItemName() {
        return deviceMeasurementItemName;
    }

    public void setDeviceMeasurementItemName(String deviceMeasurementItemName) {
        this.deviceMeasurementItemName = deviceMeasurementItemName;
    }

    @Basic
    @Column(name = "correct_value", nullable = false, length = 255)
    public String getCorrectValue() {
        return correctValue;
    }

    public void setCorrectValue(String correctValue) {
        this.correctValue = correctValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementItemField that = (MeasurementItemField) o;
        return manHour == that.manHour &&
                hide == that.hide &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(measurementUnitId, that.measurementUnitId) &&
                Objects.equals(measurementItemId, that.measurementItemId) &&
                Objects.equals(measurementTemplateId, that.measurementTemplateId) &&
                Objects.equals(deviceMeasurementItemId, that.deviceMeasurementItemId) &&
                Objects.equals(maxValue, that.maxValue) &&
                Objects.equals(minValue, that.minValue) &&
                Objects.equals(correctValue, that.correctValue) &&
                Objects.equals(description, that.description) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(groupId, that.groupId) &&
                Objects.equals(groupName, that.groupName) &&
                Objects.equals(addTime, that.addTime) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(unitName, that.unitName) &&
                Objects.equals(fullName, that.fullName) &&
                Objects.equals(measurementUnitName, that.measurementUnitName) &&
                Objects.equals(measurementItemFieldValues, that.measurementItemFieldValues) &&
                Objects.equals(deviceMeasurementItemName, that.deviceMeasurementItemName) &&
                Objects.equals(measurementUnit, that.measurementUnit) &&
                Objects.equals(measurementItemFieldValue, that.measurementItemFieldValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, measurementUnitId, measurementItemId, measurementTemplateId, deviceMeasurementItemId, maxValue,
                minValue, correctValue, manHour, hide, description, remark, groupId, groupName, addTime, updateTime, unitName, fullName, measurementUnitName, measurementItemFieldValues, deviceMeasurementItemName, measurementUnit, measurementItemFieldValue);
    }

    @Basic
    @Column(name = "measurement_template_id", nullable = false, length = 36)
    public String getMeasurementTemplateId() {
        return measurementTemplateId;
    }

    public void setMeasurementTemplateId(String measurementTemplateId) {
        this.measurementTemplateId = measurementTemplateId;
    }

    @Transient
    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(MeasurementUnit measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    @Transient
    public MeasurementItemFieldValue getMeasurementItemFieldValue() {
        return measurementItemFieldValue;
    }

    public void setMeasurementItemFieldValue(MeasurementItemFieldValue measurementItemFieldValue) {
        this.measurementItemFieldValue = measurementItemFieldValue;
    }

    @Transient
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Transient
    public String getAssociationCode() {
        return associationCode;
    }

    public void setAssociationCode(String associationCode) {
        this.associationCode = associationCode;
    }

    @Basic
    @Column(name = "seq", nullable = false)
    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
