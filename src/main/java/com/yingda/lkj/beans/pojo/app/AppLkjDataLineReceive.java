package com.yingda.lkj.beans.pojo.app;

import lombok.Data;

import java.util.List;

/**
 * 从app接受lkj数据
 *
 * @author hood  2020/4/26
 */
@Data
public class AppLkjDataLineReceive {
    private String id;
    private double distance;

    private List<AppLkjNodeReceive> deviceList;
}
