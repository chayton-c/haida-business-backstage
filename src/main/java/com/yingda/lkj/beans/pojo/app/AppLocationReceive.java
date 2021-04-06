package com.yingda.lkj.beans.pojo.app;

import lombok.Data;

/**
 * @author hood  2020/4/26
 */
@Data
public class AppLocationReceive {
    private double altitude; // 海拔高度
    private double horizontal; // 经度偏移
    private double lat; // 纬度
    private double latOffset; // 纬度偏移
    private double lon; // 经度
}
