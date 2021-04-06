package com.yingda.lkj.beans.entity.backstage.lkj;

import com.yingda.lkj.beans.entity.backstage.dataversion.DataApproveFlow;
import com.yingda.lkj.beans.pojo.approvedata.VersionData;
import com.yingda.lkj.beans.pojo.device.Semaphore;
import com.yingda.lkj.utils.StringUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * lkj数据
 *
 * @author hood  2019/12/30
 */
@Entity
@Table(name = "lkj_data_line", schema = "illustrious", catalog = "")
public class LkjDataLine extends VersionData {

    // approveStatus字段
    public static final byte PENDING_SUBMIT = -1; // 待提交的
    public static final byte PENDING_REVIEW = 0; // 待审核的
    public static final byte APPROVED = 1; // 通过
    public static final byte FAILED = 2; // 未通过

    // outdated字段，使用中 / 过期
    public static final byte USING = 0;
    public static final byte OUTDATED = 1;

    // downriver字段
    public static final byte UPRIVER = 0;
    public static final byte DOWNRIVER = 1;

    // retrograde字段
    public static final byte FORWARD = 0;
    public static final byte RETROGRADE = 1;

    // downriver字段和retrograde字段通用：半自闭区间，不区分正向逆向和上行下行
    public static final byte SEME_CLOSED_INTERVAL = 2;

    // readonly字段
    public static final byte READ_ONLY = 0;
    public static final byte EDITABLE = 1;

    // tableType字段
    public static final Byte TABLE_1 = 1;
    public static final Byte TABLE_2 = 2;
    public static final Byte TABLE_3 = 3;
    public static final Byte TABLE_4 = 4;

    private String id;
    private String fragmentId; // 所在区间id
    private String leftDeviceId; // 起始设备id
    private String rightDeviceId; // 连接设备id
    private String lkjTaskId; // 任务id
    private String lkjGroupId; // lkj组别id,lkjGroup相同的lkjDataLine数据在同一组中
    private String dataVersionId; // 版本id
    private Double dataVersionNumber; // 版本号
    private double distance; // 距离
    private String uniqueCode; // 唯一码，每一组lkj数据有同一个唯一码，用于查询历史数据，由downriver、retrograde、leftDeviceId、rightDeviceId组成
    private Byte readonly; // app上的对应进路，是否不可编辑
    private byte downriver; // 下行线还是上行线
    private byte retrograde; // 正向还是逆向
    private byte approveStatus; // 审核状态
    private byte outdated; // 是否过期
    private Byte tableType; // 哈站字段：表一，表二，表三，表四
    private Timestamp addTime;
    private Timestamp updateTime;
    private String dataApproveFlowId; // 信号机审批流程id
    private Integer seq; // 插入顺序

    // page field
    private String fragmentName;
    private Semaphore leftSemaphore;
    private Semaphore rightSemaphore;
    private String lkjGroupName;
    private String previousLkjDataLineId;
    private String nextLkjDataLineId;
    private String operation; // 版本比对使用，表示执行了那种操作
    private double previousDistance; // 版本比对使用，对于重新测量操作，记录旧版本的测量距离

    public LkjDataLine() {
    }

    /**
     * 直接导入生成lkj
     */
    public LkjDataLine(LkjDataLine rawLkjDataLine, DataApproveFlow dataApproveFlow, String uniqueCode) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        this.id = UUID.randomUUID().toString();
        this.fragmentId = rawLkjDataLine.getFragmentId();
        this.leftDeviceId = rawLkjDataLine.getLeftDeviceId();
        this.rightDeviceId = rawLkjDataLine.getRightDeviceId();
        this.distance = rawLkjDataLine.getDistance();
        this.uniqueCode = uniqueCode;
        this.downriver = rawLkjDataLine.getDownriver();
        this.retrograde = rawLkjDataLine.getRetrograde();
        this.seq = rawLkjDataLine.getSeq();
        this.tableType = rawLkjDataLine.getTableType();
        this.approveStatus = PENDING_REVIEW;
        this.outdated = USING;

        this.addTime = current;
        this.updateTime = current;

        if (StringUtils.isNotEmpty(rawLkjDataLine.getLkjTaskId()))
            this.lkjTaskId = rawLkjDataLine.getLkjTaskId();
        this.dataApproveFlowId = dataApproveFlow.getId();
    }

    /**
     * 导入excel通过任务生成lkj
     */
    public LkjDataLine(LkjDataLine rawLkjDataLine, LkjTask lkjTask, String uniqueCode) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        this.id = UUID.randomUUID().toString();
        this.fragmentId = rawLkjDataLine.getFragmentId();
        this.leftDeviceId = rawLkjDataLine.getLeftDeviceId();
        this.rightDeviceId = rawLkjDataLine.getRightDeviceId();
        this.distance = rawLkjDataLine.getDistance();
        this.uniqueCode = uniqueCode;
        this.downriver = rawLkjDataLine.getDownriver();
        this.retrograde = rawLkjDataLine.getRetrograde();
        this.seq = rawLkjDataLine.getSeq();
        this.approveStatus = PENDING_REVIEW;
        this.outdated = USING;

        this.addTime = current;
        this.updateTime = current;
        this.lkjTaskId = lkjTask.getId();
    }

    /**
     * 编辑任务(界面上)生成lkj
     */
    public LkjDataLine(LkjDataLine original, String lkjTaskId) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        this.id = UUID.randomUUID().toString();
        this.fragmentId = original.getFragmentId();
        this.leftDeviceId = original.getLeftDeviceId();
        this.rightDeviceId = original.getRightDeviceId();
        this.distance = original.getDistance();
        this.uniqueCode = original.getUniqueCode();
        this.downriver = original.getDownriver();
        this.retrograde = original.getRetrograde();
        this.lkjGroupId = original.getLkjGroupId();
        this.readonly = EDITABLE;
        this.seq = original.getSeq();
        this.approveStatus = PENDING_REVIEW;
        this.outdated = USING;

        this.addTime = current;
        this.updateTime = current;
        this.lkjTaskId = lkjTaskId;
    }

    /**
     * 提供唯一键，唯一键相同的多组数据表示这些数据是对于同一个数据的不同历史版本
     */
    @Override
    @Transient
    public String getUniqueKey() {
        return getLeftDeviceId() + getRightDeviceId() + getUniqueCode() + getDownriver() + getRetrograde();
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
    @Column(name = "fragment_id", nullable = false, length = 36)
    public String getFragmentId() {
        return fragmentId;
    }

    public void setFragmentId(String fragmentId) {
        this.fragmentId = fragmentId;
    }

    @Basic
    @Column(name = "left_device_id", nullable = false, length = 36)
    public String getLeftDeviceId() {
        return leftDeviceId;
    }

    public void setLeftDeviceId(String leftDeviceId) {
        this.leftDeviceId = leftDeviceId;
    }

    @Basic
    @Column(name = "right_device_id", nullable = false, length = 36)
    public String getRightDeviceId() {
        return rightDeviceId;
    }

    public void setRightDeviceId(String rightDeviceId) {
        this.rightDeviceId = rightDeviceId;
    }

    @Basic
    @Column(name = "distance", nullable = false, precision = 0)
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Basic
    @Column(name = "unique_code", nullable = false, length = 3000)
    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    @Basic
    @Column(name = "downriver", nullable = false)
    public byte getDownriver() {
        return downriver;
    }

    public void setDownriver(byte downriver) {
        this.downriver = downriver;
    }

    @Basic
    @Column(name = "retrograde", nullable = false)
    public byte getRetrograde() {
        return retrograde;
    }

    public void setRetrograde(byte retrograde) {
        this.retrograde = retrograde;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LkjDataLine that = (LkjDataLine) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Basic
    @Column(name = "approve_status", nullable = false)
    public byte getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(byte approveStatus) {
        this.approveStatus = approveStatus;
    }

    @Basic
    @Column(name = "outdated", nullable = false)
    public byte getOutdated() {
        return outdated;
    }

    public void setOutdated(byte outdated) {
        this.outdated = outdated;
    }

    @Basic
    @Column(name = "seq", nullable = true)
    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @Transient
    public String getFragmentName() {
        return fragmentName;
    }

    public void setFragmentName(String fragmentName) {
        this.fragmentName = fragmentName;
    }

    @Basic
    @Column(name = "lkj_task_id", nullable = true, length = 36)
    public String getLkjTaskId() {
        return lkjTaskId;
    }

    public void setLkjTaskId(String lkjTaskId) {
        this.lkjTaskId = lkjTaskId;
    }

    @Basic
    @Column(name = "lkj_group_id", nullable = true, length = 36)
    public String getLkjGroupId() {
        return lkjGroupId;
    }

    public void setLkjGroupId(String lkjGroupId) {
        this.lkjGroupId = lkjGroupId;
    }

    @Transient
    public Semaphore getLeftSemaphore() {
        return leftSemaphore;
    }

    public void setLeftSemaphore(Semaphore leftSemaphore) {
        this.leftSemaphore = leftSemaphore;
    }

    @Transient
    public Semaphore getRightSemaphore() {
        return rightSemaphore;
    }

    public void setRightSemaphore(Semaphore rightSemaphore) {
        this.rightSemaphore = rightSemaphore;
    }

    @Transient
    public String getLkjGroupName() {
        return lkjGroupName;
    }

    public void setLkjGroupName(String lkjGroupName) {
        this.lkjGroupName = lkjGroupName;
    }

    @Transient
    public String getPreviousLkjDataLineId() {
        return previousLkjDataLineId;
    }

    public void setPreviousLkjDataLineId(String previousLkjDataLineId) {
        this.previousLkjDataLineId = previousLkjDataLineId;
    }

    @Transient
    public String getNextLkjDataLineId() {
        return nextLkjDataLineId;
    }

    public void setNextLkjDataLineId(String nextLkjDataLineId) {
        this.nextLkjDataLineId = nextLkjDataLineId;
    }

    @Transient
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Transient
    public double getPreviousDistance() {
        return previousDistance;
    }

    public void setPreviousDistance(double previousDistance) {
        this.previousDistance = previousDistance;
    }

    @Basic
    @Column(name = "readonly", nullable = true)
    public Byte getReadonly() {
        return readonly;
    }

    public void setReadonly(Byte readonly) {
        this.readonly = readonly;
    }

    @Basic
    @Column(name = "data_approve_flow_id", nullable = true, length = 36)
    public String getDataApproveFlowId() {
        return dataApproveFlowId;
    }

    public void setDataApproveFlowId(String dataApproveFlowId) {
        this.dataApproveFlowId = dataApproveFlowId;
    }

    @Basic
    @Column(name = "data_version_id", nullable = true, length = 36)
    public String getDataVersionId() {
        return dataVersionId;
    }

    public void setDataVersionId(String dataVersionId) {
        this.dataVersionId = dataVersionId;
    }

    @Basic
    @Column(name = "data_version_number", nullable = true, precision = 0)
    public Double getDataVersionNumber() {
        return dataVersionNumber;
    }

    public void setDataVersionNumber(Double dataVersionNumber) {
        this.dataVersionNumber = dataVersionNumber;
    }

    @Basic
    @Column(name = "table_type", nullable = true)
    public Byte getTableType() {
        return tableType;
    }

    public void setTableType(Byte tableType) {
        this.tableType = tableType;
    }
}
