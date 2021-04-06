package com.yingda.lkj.beans.pojo.app;

import lombok.Data;

import java.util.List;

/**
 * 自由测量任务上传
 *
 * @author hood  2020/4/26
 */
@Data
public class AppLkjFreeTaskReceive {
    private String userId;
    private List<AppLkjFreeMeasurementReceivce> lkjDataLines;
}
