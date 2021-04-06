package com.yingda.lkj.beans.pojo.measurement;

import com.yingda.lkj.beans.entity.backstage.device.Device;
import com.yingda.lkj.beans.entity.backstage.device.DeviceMeasurementItem;
import com.yingda.lkj.beans.entity.backstage.measurement.DeviceMaintenanceParameter;
import com.yingda.lkj.utils.date.DateUtil;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hood  2020/6/15
 */
@Data
public class DeviceMaintenanceParameterChatsPojo {
    private String name;
    private String type = "line";
    private Map<String, Object>[] data;

    public DeviceMaintenanceParameterChatsPojo(List<DeviceMaintenanceParameter> deviceMaintenanceParameters) {
        DeviceMaintenanceParameter deviceMaintenanceParameter = deviceMaintenanceParameters.get(0);
        this.name = deviceMaintenanceParameter.getDeviceMeasurementItemName();

//        this.data = new Object[deviceMaintenanceParameters.size()];
        this.data = new HashMap[deviceMaintenanceParameters.size()];

        deviceMaintenanceParameters.sort(Comparator.comparingLong(x -> x.getMeasurementTime().getTime()));

        for (int i = 0; i < deviceMaintenanceParameters.size(); i++) {
            DeviceMaintenanceParameter item = deviceMaintenanceParameters.get(i);
            data[i] = new HashMap<>();
            data[i].put("value", new Object[]{DateUtil.format(item.getMeasurementTime(), "yyyy-MM-dd HH:mm:ss"), item.getValue()});
            data[i].put("deviceName", deviceMaintenanceParameter.getDeviceName());
//            data[i].put("time", );
        }
    }
}
