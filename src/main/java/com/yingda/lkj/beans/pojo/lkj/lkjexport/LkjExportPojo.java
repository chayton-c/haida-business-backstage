package com.yingda.lkj.beans.pojo.lkj.lkjexport;


import lombok.Data;

/**
 * @author hood  2020/2/12
 */
@Data
public class LkjExportPojo {
    private SemaphoreExportPojo upSemaphore;
    private Double distance;
    private SemaphoreExportPojo downSemaphore;
}