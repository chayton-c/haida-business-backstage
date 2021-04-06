package com.yingda.lkj.beans.pojo.measurement;

import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementItem;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementItemField;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementItemFieldValue;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementUnit;
import com.yingda.lkj.utils.StringUtils;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

/**
 * @author hood  2020/3/18
 */
@Data
public class MeasurementItemFieldPojo {

    private final static String SELECT_VALUE_MAIN_FUNCTION_CODE = "0xA1";

    private String id;
    private String measurementItemFieldValueId; // 测量字段的值id
    private String measurementItemId; // 测量项(子模板id)
    private String measurementItemName; // 测量项名称
    private String name; // 测量字段名
    private String unitName; // 测量字段的单位名称(毫安，伏特)
    private String value;
    private String url;
    private byte valueType;
    private Double currentMaxValue; // 当前最大值(用于判断数据异常)
    private Double currentMinValue; // 当前最小值
    private String description;
    private String remark;
    private byte abnormal; // 数据是否异常
    private byte isSelectValue; // 该字段是否为下拉选
    private List<String> options; // 如果是下拉选，待选的选项
    private Timestamp addTime;


    public MeasurementItemFieldPojo(MeasurementItemField measurementItemField, MeasurementItem measurementItem
            , MeasurementItemFieldValue measurementItemFieldValue, MeasurementUnit measurementUnit) {
        this.id = measurementItemField.getId();
        this.measurementItemId = measurementItem.getId();
        this.measurementItemName = measurementItem.getName();
        this.name = measurementItemField.getName();
        this.unitName = measurementItemField.getUnitName();
        this.description = measurementItemField.getDescription();
        this.remark = measurementItemField.getRemark();

        this.isSelectValue = 0;
        if (measurementItemFieldValue != null) {
            this.measurementItemFieldValueId = measurementItemFieldValue.getId();
            this.value = measurementItemFieldValue.getValue();
            this.currentMaxValue = measurementItemFieldValue.getCurrentMaxValue();
            this.currentMinValue = measurementItemFieldValue.getCurrentMinValue();
            this.abnormal = measurementItemFieldValue.getAbnormal();
            this.addTime = measurementItemFieldValue.getAddTime();
            this.valueType = measurementItemFieldValue.getValueType();
            this.url = measurementItemFieldValue.getUrl();
        } else {
            this.measurementItemFieldValueId = null;
            this.value = null;
            this.url = null;
            this.currentMaxValue = measurementItemField.getMaxValue();
            this.currentMinValue = measurementItemField.getMinValue();
            this.abnormal = MeasurementItemFieldValue.NO_DATA;
            this.addTime = null;
            this.valueType = 0;
        }

        if (measurementUnit != null && SELECT_VALUE_MAIN_FUNCTION_CODE.equals(measurementUnit.getMainFunctionCode())) {
            this.isSelectValue = 1;
            String subFunctionCode = measurementUnit.getSubFunctionCode();
            if (StringUtils.isNotEmpty(subFunctionCode))
                this.options = Arrays.asList(subFunctionCode.split(","));
        }
    }
}
