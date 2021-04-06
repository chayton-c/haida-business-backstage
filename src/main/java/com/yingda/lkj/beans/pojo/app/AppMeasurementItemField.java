package com.yingda.lkj.beans.pojo.app;

import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementItemField;
import com.yingda.lkj.utils.StringUtils;
import lombok.Data;

import java.util.UUID;

/**
 * @author hood  2020/4/14
 */
@Data
public class AppMeasurementItemField {
    private String measurementItemFieldId;
    private String measurementItemFieldName;
    private String measurementUnitId;
    private double maxValue;
    private double minValue;
    private String correctValue;
    private String groupId;
    private String groupName;
    private String measurementItemId;
    private String measurementItemName;
    private int seq;

    public AppMeasurementItemField(MeasurementItemField measurementItemField) {
        this.measurementItemFieldId = measurementItemField.getId();
        this.measurementItemFieldName = measurementItemField.getName();
        this.measurementUnitId = measurementItemField.getMeasurementUnitId();
        this.maxValue = measurementItemField.getMaxValue();
        this.minValue = measurementItemField.getMinValue();
        String correctValue = measurementItemField.getCorrectValue();
        this.correctValue = StringUtils.isEmpty(correctValue) ? "" : correctValue;

        String groupId = measurementItemField.getGroupId();
        this.groupId = StringUtils.isEmpty(groupId) ? UUID.randomUUID().toString() : groupId;

        this.groupName = measurementItemField.getGroupName();
        this.measurementItemId = measurementItemField.getMeasurementItemId();
    }

    public AppMeasurementItemField() {
    }
}
