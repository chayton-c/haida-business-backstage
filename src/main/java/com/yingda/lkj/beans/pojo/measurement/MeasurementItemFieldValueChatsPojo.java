package com.yingda.lkj.beans.pojo.measurement;

import com.yingda.lkj.beans.entity.backstage.measurement.DeviceMaintenanceParameter;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementItemFieldValue;
import com.yingda.lkj.utils.date.DateUtil;
import lombok.Data;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hood  2020/6/25
 */
@Data
public class MeasurementItemFieldValueChatsPojo {
    private String name;
    private String type = "line";
    private Map<String, Object>[] data;

    public MeasurementItemFieldValueChatsPojo() {
    }

    public MeasurementItemFieldValueChatsPojo(List<MeasurementItemFieldValue> measurementItemFieldValues) {
        MeasurementItemFieldValue measurementItemFieldValue = measurementItemFieldValues.get(0);
        this.name = measurementItemFieldValue.getMeasurementItemFieldName();

//        this.data = new Object[measurementItemFieldValues.size()];
        this.data = new HashMap[measurementItemFieldValues.size()];

        measurementItemFieldValues.sort(Comparator.comparingLong(x -> x.getAddTime().getTime()));

        for (int i = 0; i < measurementItemFieldValues.size(); i++) {
            MeasurementItemFieldValue item = measurementItemFieldValues.get(i);
            data[i] = new HashMap<>();
            data[i].put("value", new Object[]{DateUtil.format(item.getAddTime(), "yyyy-MM-dd HH:mm:ss"), item.getValue()});
            data[i].put("itemName", item.getMeasurementItemName());
        }
    }
}
