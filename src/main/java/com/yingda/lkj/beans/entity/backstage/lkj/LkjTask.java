package com.yingda.lkj.beans.entity.backstage.lkj;

import com.yingda.lkj.beans.entity.system.User;
import com.yingda.lkj.utils.StringUtils;
import com.yingda.lkj.utils.date.DateUtil;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * @author hood  2020/2/21
 */
@Entity
@Table(name = "lkj_task", schema = "illustrious")
public class LkjTask {

    // finishedStatus字段
    public static final byte PENDING_HANDLE = 0; // 待处理
    public static final byte PENDING_APPROVAL = 1; // 待审批(已经提交审批了，但是没批下来)
    public static final byte COMPLETED = 2; // 完成
    public static final byte REFUSED = 3; // 未通过
    public static final byte CLOSED = 4; // 已关闭

    private String id;
    private String sectionId; // 所在站段
    private String submitUserId; // 提交人(一般是段长)
    private String executeUserId; // 执行人(干活的)
    private byte finishedStatus; // 完成状态
    private String name;
    private String remark;
    private Timestamp addTime;
    private Timestamp updateTime;

    // page fields
    private String railwayLineId;
    private String fragmentId;
    private String executeUserName; // 执行人姓名
    private String executeUserWorkAreaName; // 执行人工区名

    public LkjTask() {
    }

    public LkjTask(LkjTask rawLkjTask, User submitter, User executer) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        String rawId = rawLkjTask.getId();
        this.id = StringUtils.isNotEmpty(rawId) ? rawId : UUID.randomUUID().toString();
        this.sectionId = submitter.getSectionId();
        this.submitUserId = submitter.getId();
        this.executeUserId = executer.getId();
        this.finishedStatus = PENDING_HANDLE;

        if (StringUtils.isEmpty(rawLkjTask.getName())) // 如无name，自动生成
            rawLkjTask.setName(executer.getDisplayName() + " lkj测量任务 " + DateUtil.format(current, "yyyy-MM-dd"));

        this.name = rawLkjTask.getName();
        this.remark = rawLkjTask.getRemark();
        this.addTime = current;
        this.updateTime = current;
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
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "update_time", nullable = true)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "section_id", nullable = false, length = 36)
    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    @Basic
    @Column(name = "submit_user_id", nullable = false, length = 36)
    public String getSubmitUserId() {
        return submitUserId;
    }

    public void setSubmitUserId(String submitUserId) {
        this.submitUserId = submitUserId;
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
    @Column(name = "finished_status", nullable = false)
    public byte getFinishedStatus() {
        return finishedStatus;
    }

    public void setFinishedStatus(byte finishedStatus) {
        this.finishedStatus = finishedStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LkjTask lkjTask = (LkjTask) o;
        return finishedStatus == lkjTask.finishedStatus &&
                Objects.equals(id, lkjTask.id) &&
                Objects.equals(sectionId, lkjTask.sectionId) &&
                Objects.equals(submitUserId, lkjTask.submitUserId) &&
                Objects.equals(executeUserId, lkjTask.executeUserId) &&
                Objects.equals(name, lkjTask.name) &&
                Objects.equals(addTime, lkjTask.addTime) &&
                Objects.equals(updateTime, lkjTask.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, submitUserId, executeUserId, sectionId, finishedStatus, name, addTime, updateTime);
    }

    @Basic
    @Column(name = "remark", nullable = true, length = 255)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Transient
    public String getFragmentId() {
        return fragmentId;
    }

    public void setFragmentId(String fragmentId) {
        this.fragmentId = fragmentId;
    }

    @Transient
    public String getRailwayLineId() {
        return railwayLineId;
    }

    public void setRailwayLineId(String railwayLineId) {
        this.railwayLineId = railwayLineId;
    }

    @Transient
    public String getExecuteUserName() {
        return executeUserName;
    }

    public void setExecuteUserName(String executeUserName) {
        this.executeUserName = executeUserName;
    }

    @Transient
    public String getExecuteUserWorkAreaName() {
        return executeUserWorkAreaName;
    }

    public void setExecuteUserWorkAreaName(String executeUserWorkAreaName) {
        this.executeUserWorkAreaName = executeUserWorkAreaName;
    }
}
