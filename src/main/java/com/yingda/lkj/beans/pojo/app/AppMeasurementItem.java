package com.yingda.lkj.beans.pojo.app;

import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementItem;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementTemplate;
import lombok.Data;

/**
 * @author hood  2020/4/14
 */
@Data
public class AppMeasurementItem {
    private String measurementItemId;
    private String name;
    private String measurementItemFieldIds;
    private String measurementTemplateId;

    public AppMeasurementItem() {
    }

    public AppMeasurementItem(MeasurementItem measurementItem, MeasurementTemplate measurementTemplate, String measurementItemFieldIds) {
        this.measurementTemplateId = measurementTemplate.getId();
        this.measurementItemId = measurementItem.getId();
        this.name = String.format("%s (%s)", measurementTemplate.getName(), measurementItem.getName());
        this.measurementItemFieldIds = measurementItemFieldIds;
    }

    public AppMeasurementItem(String measurementItemId, String name, String measurementItemFieldIds) {
        this.measurementItemId = measurementItemId;
        this.name = name;
        this.measurementItemFieldIds = measurementItemFieldIds;
    }
}
