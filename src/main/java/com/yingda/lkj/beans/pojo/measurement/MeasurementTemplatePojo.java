package com.yingda.lkj.beans.pojo.measurement;

import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementItem;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementTemplate;
import com.yingda.lkj.beans.enums.repairclass.RepairClass;
import lombok.Data;

/**
 * 封装测量模板(MeasurementTemplate)和测量项(MeasurementItem)
 *
 * @author hood  2020/3/16
 */
@Data
public class MeasurementTemplatePojo {
    private static final String ROOT_ID = "0";

    private String id;
    private String parentId;
    private int level;
    private String description;
    private String name;
    private String addTime;
    private String repairClass; // 修程

    public MeasurementTemplatePojo() {
    }

    public MeasurementTemplatePojo(MeasurementTemplate measurementTemplate) {
        this.id = measurementTemplate.getId();
        this.parentId = ROOT_ID;
        this.level = 1;
        this.description = measurementTemplate.getDescription();
        this.name = measurementTemplate.getName();
        this.addTime = measurementTemplate.getAddTime().toString();

        this.repairClass = RepairClass.getByType(measurementTemplate.getRepairClass()).getName();
    }

    public MeasurementTemplatePojo(MeasurementItem measurementItem, MeasurementTemplate measurementTemplate) {
        this.id = measurementItem.getId();
        this.parentId = measurementTemplate.getId();
        this.level = 2;
        this.description = measurementItem.getDescription();
        this.name = measurementItem.getName();
        this.addTime = measurementItem.getAddTime().toString();
        this.repairClass = "";
    }
}
