package com.yingda.lkj.beans.pojo.measurementitem;

import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementItem;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementItemField;
import com.yingda.lkj.beans.pojo.line.LineNodePojo;

import java.util.List;

/**
 * @author hood  2020/11/25
 */
public class MeasurementItemNode {

    private String id;
    private String name;
    private String description;
    private String unitName; // 测量字段的单位名称(毫安，伏特)
    private String measurementUnitId; // 测试单位id
    private String measurementItemId; // 测量项id
    private Double maxValue; // 数据最大值
    private Double minValue; // 数据最小值
    private String correctValue; // 数据正确值(选项或文本输入的值，以此来判断)
    private int manHour; // 工时
    private String remark;
    private List<MeasurementItemNode> measurementItemNodes;


    public MeasurementItemNode(MeasurementItem measurementItem, List<MeasurementItemField> measurementItemFields) {
        this.name = measurementItem.getName();
        this.description = measurementItem.getDescription();
    }

    public MeasurementItemNode(MeasurementItemField measurementItemField) {
        this.name = name;
    }

}
