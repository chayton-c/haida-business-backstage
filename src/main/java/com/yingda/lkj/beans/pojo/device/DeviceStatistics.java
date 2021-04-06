package com.yingda.lkj.beans.pojo.device;

import com.yingda.lkj.beans.entity.backstage.device.Device;
import lombok.Data;

import java.util.List;

/**
 * @author hood  2020/6/16
 */
@Data
public class DeviceStatistics {
    public static final byte NOT_CHECKED = 0; // 未检修
    public static final byte NORMAL = 1; // 正常
    public static final byte ERROR = 2; // 异常

    private int total;
    private int notChecked = 0;
    private int normal = 0;
    private int error = 0;

    public DeviceStatistics(List<Device> devices) {
        total = devices.size();
        for (Device device : devices) {
            byte errorState = device.getErrorState();
            switch (errorState) {
                case Device.NOT_CHECKED:
                    notChecked++;
                    break;
                case Device.NORMAL:
                    normal++;
                    break;
                case Device.ERROR:
                    error++;
                    break;
            }
        }
    }
}
