package com.yingda.lkj.beans.pojo.measurement;

import com.yingda.lkj.beans.entity.backstage.lkj.LkjTask;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementTask;
import lombok.Data;

import java.util.List;

/**
 * @author hood  2020/6/3
 */
@Data
public class UserMeasurementTaskDetail {
    private List<MeasurementTask> totalTasks; // 所有任务

    private List<MeasurementTask> pendingHandleTasks; // 未处理任务

    private List<MeasurementTask> completeTasks; // 完成任务

    private List<MeasurementTask> refusedTasks; // 已拒绝任务

    private List<MeasurementTask> closedTasks; // 已关闭任务
}
