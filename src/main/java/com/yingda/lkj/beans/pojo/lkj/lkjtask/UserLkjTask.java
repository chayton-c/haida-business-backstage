package com.yingda.lkj.beans.pojo.lkj.lkjtask;

import com.yingda.lkj.beans.entity.backstage.lkj.LkjTask;
import lombok.Data;

import java.util.List;

/**
 * @author hood  2020/3/3
 */
@Data
public class UserLkjTask {
    private List<LkjTask> totalTasks; // 所有任务

    private List<LkjTask> pendingHandleTasks; // 未处理任务

    private List<LkjTask> completeTasks; // 完成任务

    private List<LkjTask> refusedTasks; // 已拒绝任务

    private List<LkjTask> closedTasks; // 已关闭任务
}
