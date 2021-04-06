package com.yingda.lkj.beans.pojo.app;

import com.yingda.lkj.beans.entity.backstage.device.Device;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementItemField;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementItemFieldValue;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementTask;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementTaskDetail;
import com.yingda.lkj.utils.date.DateUtil;
import lombok.Data;
import org.springframework.util.unit.DataUnit;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

/**
 * @author hood  2020/4/13
 */
@Data
public class AppMeasurementTaskDetail {
    private String measurementTaskId;
    private String measurementTemplateId;
    private String measurementTaskName;
    private String measurementTaskType;
    private String measurementTaskDetailId;
    private String name;
    private String deviceId;
    private String userIds;
    private String addTime;
    private String startTime; // 开始时间
    private String endTime; // 截止时间
    private String measurementItemIds; // 使用的模板下的测试项id
    private Timestamp measurementTaskStartTime;
    private Timestamp measurementTaskExecuteTime;
    private String deviceName;
    private String submitUserName;
    private String executeUserName;
    private String finishedStatus;
    private String errorStatus;
    private String deviceCode;
    private int itemFieldCount;
    private int itemFieldValueCount;
    private List<MeasurementItemField> measurementItemFields;
    private List<MeasurementItemFieldValue> measurementItemFieldValues;

    public AppMeasurementTaskDetail(MeasurementTask measurementTask, MeasurementTaskDetail measurementTaskDetail,
                                    String submitUserName, String executeUserNames, Device device) {
        this.measurementTaskId = measurementTask.getId();
        this.measurementTaskName = measurementTask.getName();
        this.deviceName = device.getName();

        Timestamp addTime = measurementTask.getAddTime();
        Timestamp startTime = measurementTask.getStartTime();
        Timestamp endTime = measurementTask.getEndTime();
        Timestamp executeTime = measurementTask.getExecuteTime();

        this.addTime = DateUtil.format(addTime, "yyyy-MM-dd");
        this.startTime = DateUtil.format(startTime, "yyyy-MM-dd");
        this.endTime = DateUtil.format(endTime, "yyyy-MM-dd");
        this.executeUserName = DateUtil.format(executeTime, "yyyy-MM-dd");
        this.executeUserName = executeUserNames;
        this.deviceCode = device.getCode();
        this.submitUserName = submitUserName;

        byte finishedStatus = measurementTaskDetail.getFinishedStatus();
        switch (finishedStatus) {
            case MeasurementTaskDetail.PENDING_SUBMIT:
                this.finishedStatus = "待发起";
                break;
            case MeasurementTaskDetail.PENDING_HANDLE:
                this.finishedStatus = "待处理";
                break;
            case MeasurementTaskDetail.COMPLETED:
                this.finishedStatus = "完成";
                break;
            case MeasurementTaskDetail.CLOSED:
                this.finishedStatus = "已关闭";
                break;
        }
        byte errorStatus = measurementTaskDetail.getErrorStatus();
        switch (errorStatus) {
            case MeasurementTaskDetail.NOT_CHECKED:
                this.errorStatus = "未检修";
                break;
            case MeasurementTaskDetail.ERROR_STATUS_NORMAL:
                this.errorStatus = "正常";
                break;
            case MeasurementTaskDetail.ERROR:
                this.errorStatus = "异常";
                break;
            case MeasurementTaskDetail.OMISSION:
                this.errorStatus = "漏检";
                break;
        }
    }

    public AppMeasurementTaskDetail() {
    }
}
