package com.yingda.lkj.beans.pojo.app;

import lombok.Data;

import java.util.List;

/**
 * 自由测量数据，节点上传
 *
 * @author hood  2020/4/26
 */
@Data
public class AppLkjFreeNodeReceive {
    private String lineName; // 这里app写错了，lineName表示节点名称
    private long measure_time;
    private AppLocationReceive locationEnd;
}
