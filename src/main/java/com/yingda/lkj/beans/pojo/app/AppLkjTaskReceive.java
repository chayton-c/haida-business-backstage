package com.yingda.lkj.beans.pojo.app;

import lombok.Data;

import java.util.List;

/**
 * @author hood  2020/4/26
 */
@Data
public class AppLkjTaskReceive {
    private String id;
    private List<AppLkjDataLineReceive> lkjDataLines;
}
