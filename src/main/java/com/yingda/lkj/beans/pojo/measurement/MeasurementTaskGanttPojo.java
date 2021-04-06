package com.yingda.lkj.beans.pojo.measurement;

import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementTask;
import com.yingda.lkj.utils.date.DateUtil;
import lombok.Data;

import java.util.List;

/**
 * @author hood  2020/5/11
 */
@Data
public class MeasurementTaskGanttPojo {
    private int id;
    private String text;
    private String start_date;
    private String startTime;
    private String endTime;
    private String duration;
    private double progress;
    private boolean open;
    private List<String> users;
    private List<String> deviceNames;

    private static int count = 0;

    public static MeasurementTaskGanttPojo getInstance(MeasurementTask measurementTask) {
        MeasurementTaskGanttPojo measurementTaskGanttPojo = new MeasurementTaskGanttPojo();
        measurementTaskGanttPojo.setId(++count);
        measurementTaskGanttPojo.setText(measurementTask.getName());

        measurementTaskGanttPojo.setStart_date(DateUtil.format(measurementTask.getStartTime(), "dd-MM-yyyy"));
        measurementTaskGanttPojo.setStartTime(DateUtil.format(measurementTask.getStartTime(), "yyyy-MM-dd"));
        measurementTaskGanttPojo.setEndTime(DateUtil.format(measurementTask.getEndTime(), "yyyy-MM-dd"));
        int duration = (int) ((measurementTask.getEndTime().getTime() - measurementTask.getStartTime().getTime()) / 86400000L);

        measurementTaskGanttPojo.setDuration(duration + "");
        measurementTaskGanttPojo.setProgress(measurementTask.isVirtual() ? 0 : 1);
        measurementTaskGanttPojo.setOpen(false);
        measurementTaskGanttPojo.setUsers(measurementTask.getExecuteUserNames());
        measurementTaskGanttPojo.setDeviceNames(measurementTask.getDeviceNames());
        return measurementTaskGanttPojo;
    }
}
