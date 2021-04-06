package com.yingda.lkj.beans.entity.backstage.measurement;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * 测量值
 *
 * @author hood  2020/3/11
 */
@Entity
@Table(name = "measurement_item_field_value", schema = "illustrious", catalog = "")
public class MeasurementItemFieldValue {
    // valueType字段
    public static final byte NUMBER = 0; // 数值
    public static final byte STRING = 1; // 字符
    public static final byte IMAGE = 2; // 图片
    public static final byte VIDEO = 3; // 视频
    public static final byte AUDIO = 4; // 音频

    // abnormal字段
    public static final byte ABNORMAL = 1;
    public static final byte NORMAL = 0;
    public static final byte NO_DATA = 2; // 没有数据

    private String id;
    private String measurementItemFieldId; // 测量字段id
    private String measurementTaskDetailId; // 测量子任务id
    private String measurementTemplateId; // 使用的测量模板id
    private String deviceMeasurementItemId; // 设备测量字段id
    private String deviceId;
    private String value; // 数据的值
    private byte valueType; // 数据类型
    private byte abnormal; // 数据是否异常
    private Timestamp addTime;
    private Timestamp updateTime;
    private Double currentMaxValue; // 提交时的数据最大值
    private Double currentMinValue; // 提交时的数据最小值

    // page fields
    private String measurementItemId; // 测量项id
    private String measurementItemName; // 测量项名
    private String measurementItemFieldName; // 测量字段名
    private String url;

    public MeasurementItemFieldValue() {
    }

    public MeasurementItemFieldValue(MeasurementItemField measurementItemField, MeasurementTaskDetail measurementTaskDetail,
                                     String value, MeasurementUnit measurementUnit) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        this.id = UUID.randomUUID().toString();
        this.deviceId = measurementTaskDetail.getDeviceId();
        this.measurementItemFieldId = measurementItemField.getId();
        this.measurementTaskDetailId = measurementTaskDetail.getId();
        this.measurementTemplateId = measurementTaskDetail.getMeasurementTemplateId();
        this.addTime = current;
        this.updateTime = current;
        this.value = value;
        this.currentMaxValue = measurementItemField.getMaxValue();
        this.currentMinValue = measurementItemField.getMinValue();
        this.deviceMeasurementItemId = measurementItemField.getDeviceMeasurementItemId();
        this.valueType = measurementUnit.getValueType();
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
    @Column(name = "measurement_item_field_id", nullable = false, length = 36)
    public String getMeasurementItemFieldId() {
        return measurementItemFieldId;
    }

    public void setMeasurementItemFieldId(String measurementItemFieldId) {
        this.measurementItemFieldId = measurementItemFieldId;
    }

    @Basic
    @Column(name = "measurement_task_detail_id", nullable = false, length = 36)
    public String getMeasurementTaskDetailId() {
        return measurementTaskDetailId;
    }

    public void setMeasurementTaskDetailId(String measurementTaskDetailId) {
        this.measurementTaskDetailId = measurementTaskDetailId;
    }

    @Basic
    @Column(name = "abnormal", nullable = false)
    public byte getAbnormal() {
        return abnormal;
    }

    public void setAbnormal(byte abnormal) {
        this.abnormal = abnormal;
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
    @Column(name = "current_max_value", nullable = true, precision = 0)
    public Double getCurrentMaxValue() {
        return currentMaxValue;
    }

    public void setCurrentMaxValue(Double currentMaxValue) {
        this.currentMaxValue = currentMaxValue;
    }

    @Basic
    @Column(name = "current_min_value", nullable = true, precision = 0)
    public Double getCurrentMinValue() {
        return currentMinValue;
    }

    public void setCurrentMinValue(Double currentMinValue) {
        this.currentMinValue = currentMinValue;
    }

    @Basic
    @Column(name = "value", nullable = false, length = 255)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "measurement_template_id", nullable = false, length = 36)
    public String getMeasurementTemplateId() {
        return measurementTemplateId;
    }

    public void setMeasurementTemplateId(String measurementTemplateId) {
        this.measurementTemplateId = measurementTemplateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementItemFieldValue that = (MeasurementItemFieldValue) o;
        return abnormal == that.abnormal &&
                Objects.equals(id, that.id) &&
                Objects.equals(measurementItemFieldId, that.measurementItemFieldId) &&
                Objects.equals(measurementTaskDetailId, that.measurementTaskDetailId) &&
                Objects.equals(measurementTemplateId, that.measurementTemplateId) &&
                Objects.equals(value, that.value) &&
                Objects.equals(addTime, that.addTime) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(currentMaxValue, that.currentMaxValue) &&
                Objects.equals(currentMinValue, that.currentMinValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, measurementItemFieldId, measurementTaskDetailId, measurementTemplateId, value, abnormal, addTime, updateTime, currentMaxValue, currentMinValue);
    }

    @Basic
    @Column(name = "device_measurement_item_id", nullable = true, length = 36)
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

    @Basic
    @Column(name = "value_type", nullable = false)
    public byte getValueType() {
        return valueType;
    }

    public void setValueType(byte valueType) {
        this.valueType = valueType;
    }

    @Transient
    public String getMeasurementItemFieldName() {
        return measurementItemFieldName;
    }

    public void setMeasurementItemFieldName(String measurementItemFieldName) {
        this.measurementItemFieldName = measurementItemFieldName;
    }

    @Transient
    public String getMeasurementItemName() {
        return measurementItemName;
    }

    public void setMeasurementItemName(String measurementItemName) {
        this.measurementItemName = measurementItemName;
    }

    @Transient
    public String getMeasurementItemId() {
        return measurementItemId;
    }

    public void setMeasurementItemId(String measurementItemId) {
        this.measurementItemId = measurementItemId;
    }

    @Transient
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
