package com.yingda.lkj.beans.entity.backstage.measurement;

import com.yingda.lkj.beans.entity.system.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>测量任务执行人员表</p>
 * <p>可以执行任务(measurementTask)的人，不是已提交的人，已提交的人记录在measurementTaskDetail.uploadorNames中</p>
 *
 * @author hood  2020/3/23
 */
@Entity
@Table(name = "measurement_task_execute_user")
public class MeasurementTaskExecuteUser {

    private String id;
    private String measurementTaskId; // 测量任务id
    private String executeUserId; // 执行人id
    private String executorName; // 执行人名
    private Timestamp addTime;

    @Deprecated
    private String executeUserName; // 执行人姓名

    public MeasurementTaskExecuteUser() {
    }

    public MeasurementTaskExecuteUser(String measurementTaskId, User user) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        this.id = UUID.randomUUID().toString();
        this.measurementTaskId = measurementTaskId;
        this.executeUserId = user.getId();
        this.executorName = user.getDisplayName();
        this.addTime = current;
    }

    @Id
    @Column(name = "id", nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "execute_user_id", nullable = false, length = 36)
    public String getExecuteUserId() {
        return executeUserId;
    }

    public void setExecuteUserId(String executeUserId) {
        this.executeUserId = executeUserId;
    }

    @Basic
    @Column(name = "add_time", nullable = true)
    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    @Basic
    @Column(name = "measurement_task_id", nullable = false, length = 36)
    public String getMeasurementTaskId() {
        return measurementTaskId;
    }

    public void setMeasurementTaskId(String measurementTaskId) {
        this.measurementTaskId = measurementTaskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementTaskExecuteUser that = (MeasurementTaskExecuteUser) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(measurementTaskId, that.measurementTaskId) &&
                Objects.equals(executeUserId, that.executeUserId) &&
                Objects.equals(addTime, that.addTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, measurementTaskId, executeUserId, addTime);
    }

    @Transient
    public String getExecuteUserName() {
        return executeUserName;
    }

    public void setExecuteUserName(String executeUserName) {
        this.executeUserName = executeUserName;
    }

    @Basic
    @Column(name = "executor_name", nullable = false, length = 255)
    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }
}
