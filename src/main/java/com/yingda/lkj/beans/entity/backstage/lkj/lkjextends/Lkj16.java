package com.yingda.lkj.beans.entity.backstage.lkj.lkjextends;

import com.yingda.lkj.beans.entity.backstage.dataversion.DataApproveFlow;
import com.yingda.lkj.beans.pojo.approvedata.VersionData;
import com.yingda.lkj.utils.StringUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * @author hood  2020/4/29
 */
@Entity
@Table(name = "lkj_16", schema = "illustrious", catalog = "")
public class Lkj16 extends VersionData {
    // outdated字段，使用中 / 过期
    public static final byte USING = 0;
    public static final byte OUTDATED = 1;
    // approveStatus字段
    public static final byte PENDING_SUBMIT = -1; // 待提交的
    public static final byte PENDING_REVIEW = 0; // 待审核的
    public static final byte APPROVED = 1; // 通过
    public static final byte FAILED = 2; // 未通过

    private String id;
    private String bureauId;
    private String railwayLineId;
    private String downriver;
    private String stationId;
    private String trackCircuitStandard; // 轨道电路制式
    private String staionTrackNumber; // 股道号
    private String remark;
    private Timestamp addTime;
    private Timestamp updateTime;
    private String dataVersionId; // 版本id
    private Double dataVersionNumber; // 版本号
    private byte approveStatus; // 审核状态
    private String uniqueKey; // 唯一键
    private String dataApproveFlowId; // 对应审批数据
    private byte outdated; // 是否使用

    //page file
    private String bureauName;
    private String bureauCode;
    private String railwayLineName;
    private String railwayLineCode;
    private String stationName;
    private String stationCode;
    private String operation;

    public Lkj16() {
    }

    public Lkj16(Lkj16 rawLkj16, DataApproveFlow dataApproveFlow) {
        Timestamp current = new Timestamp(System.currentTimeMillis());
        this.id = UUID.randomUUID().toString();

        this.dataApproveFlowId = dataApproveFlow.getId();

        this.addTime = current;
        this.updateTime = current;
        this.approveStatus = PENDING_REVIEW;
        this.outdated = OUTDATED;

        this.bureauId = rawLkj16.getBureauId();
        this.railwayLineId = rawLkj16.getRailwayLineId();
        this.downriver = rawLkj16.getDownriver();
        this.stationId = rawLkj16.getStationId();
        this.trackCircuitStandard = rawLkj16.getTrackCircuitStandard();
        this.staionTrackNumber = rawLkj16.getStaionTrackNumber();
        this.remark = rawLkj16.getRemark();

        String uniqueKey = rawLkj16.getUniqueKey();
        uniqueKey = StringUtils.isEmpty(uniqueKey) ? UUID.randomUUID().toString() : uniqueKey;
        this.uniqueKey = uniqueKey;
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
    @Column(name = "bureau_id", nullable = false, length = 36)
    public String getBureauId() {
        return bureauId;
    }

    public void setBureauId(String bureauId) {
        this.bureauId = bureauId;
    }

    @Basic
    @Column(name = "railway_line_id", nullable = false, length = 36)
    public String getRailwayLineId() {
        return railwayLineId;
    }

    public void setRailwayLineId(String railwayLineId) {
        this.railwayLineId = railwayLineId;
    }

    @Basic
    @Column(name = "downriver", nullable = false, length = 36)
    public String getDownriver() {
        return downriver;
    }

    public void setDownriver(String downriver) {
        this.downriver = downriver;
    }

    @Basic
    @Column(name = "station_id", nullable = false, length = 36)
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Basic
    @Column(name = "track_circuit_standard", nullable = true, length = 255)
    public String getTrackCircuitStandard() {
        return trackCircuitStandard;
    }

    public void setTrackCircuitStandard(String trackCircuitStandard) {
        this.trackCircuitStandard = trackCircuitStandard;
    }

    @Basic
    @Column(name = "staion_track_number", nullable = true, length = 255)
    public String getStaionTrackNumber() {
        return staionTrackNumber;
    }

    public void setStaionTrackNumber(String staionTrackNumber) {
        this.staionTrackNumber = staionTrackNumber;
    }

    @Basic
    @Column(name = "remark", nullable = true, length = 255)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @Transient
    public String getBureauName() {
        return bureauName;
    }

    public void setBureauName(String bureauName) {
        this.bureauName = bureauName;
    }

    @Transient
    public String getBureauCode() {
        return bureauCode;
    }

    public void setBureauCode(String bureauCode) {
        this.bureauCode = bureauCode;
    }

    @Transient
    public String getRailwayLineName() {
        return railwayLineName;
    }

    public void setRailwayLineName(String railwayLineName) {
        this.railwayLineName = railwayLineName;
    }

    @Transient
    public String getRailwayLineCode() {
        return railwayLineCode;
    }

    public void setRailwayLineCode(String railwayLineCode) {
        this.railwayLineCode = railwayLineCode;
    }

    @Transient
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Transient
    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lkj16 lkj16 = (Lkj16) o;
        return Objects.equals(id, lkj16.id) &&
                Objects.equals(bureauId, lkj16.bureauId) &&
                Objects.equals(railwayLineId, lkj16.railwayLineId) &&
                Objects.equals(downriver, lkj16.downriver) &&
                Objects.equals(stationId, lkj16.stationId) &&
                Objects.equals(trackCircuitStandard, lkj16.trackCircuitStandard) &&
                Objects.equals(staionTrackNumber, lkj16.staionTrackNumber) &&
                Objects.equals(remark, lkj16.remark) &&
                Objects.equals(addTime, lkj16.addTime) &&
                Objects.equals(updateTime, lkj16.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bureauId, railwayLineId, downriver, stationId, trackCircuitStandard, staionTrackNumber, remark, addTime, updateTime);
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

    public void setDataVersionNumber(double dataVersionNumber) {
        this.dataVersionNumber = dataVersionNumber;
    }

    public void setDataVersionNumber(Double dataVersionNumber) {
        this.dataVersionNumber = dataVersionNumber;
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
    @Column(name = "unique_key", nullable = false, length = 36)
    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
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
    @Column(name = "outdated", nullable = false)
    public byte getOutdated() {
        return outdated;
    }

    public void setOutdated(byte outdated) {
        this.outdated = outdated;
    }

    @Transient
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
