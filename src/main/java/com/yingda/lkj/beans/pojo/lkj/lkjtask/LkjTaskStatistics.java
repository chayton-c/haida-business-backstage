package com.yingda.lkj.beans.pojo.lkj.lkjtask;

import lombok.Data;

/**
 * @author hood  2020/6/4
 */
@Data
public class LkjTaskStatistics {
    private int taskCount;
    private int pendHandleCount;
    private int pendingApprovalCount;
    private int completeCount;
    private int refusedCount;
    private int closedCount;

    private int lkjUpdateCount;
}
