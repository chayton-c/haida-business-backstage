package com.yingda.lkj.beans.pojo.app;

import lombok.Data;

/**
 * @author hood  2020/4/26
 */
@Data
public class AppLkjNodeReceive {
    private String lineName; // 线路名称
    private String name; // 节点名称
    private long measure_time; // 测量该节点时的时间戳
    private AppLocationReceive locationEnd;
}
