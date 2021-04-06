package com.yingda.lkj.beans.pojo.app;

import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementUnit;
import lombok.Data;

/**
 * 测量项(测量单位)
 *
 * @author hood  2020/4/14
 */
@Data
public class AppMeasurementUnit {
    private String measurementUnitId;
    private String name;
    private String mainFunctionCode;
    private String subFunctionCode;
    private byte valueType;

    public AppMeasurementUnit() {
    }

    public AppMeasurementUnit(MeasurementUnit measurementUnit) {
        this.measurementUnitId = measurementUnit.getId();
        this.name = measurementUnit.getName();
        this.mainFunctionCode = measurementUnit.getMainFunctionCode();
        this.subFunctionCode = measurementUnit.getSubFunctionCode();
        this.valueType = measurementUnit.getValueType();
    }
}
