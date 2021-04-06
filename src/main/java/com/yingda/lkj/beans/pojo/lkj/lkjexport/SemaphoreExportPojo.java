package com.yingda.lkj.beans.pojo.lkj.lkjexport;

import lombok.Data;

import java.util.Map;

/**
 * @author hood  2020/2/12
 */
@Data
public class SemaphoreExportPojo {
    private String bureauName; // 局名
    private String bureauCode; // 局编号
    private String lineName; // 线名
    private String lineCode; // 线编号
    private byte downriver; // 下行线还是上行线
    private String stationName; // 车站名
    private String code;
    private String distance;
    private String semaphoreType;
    private Map<String, String> extendFieldValues;

    private byte retrograde; // 正向还是逆向
}
