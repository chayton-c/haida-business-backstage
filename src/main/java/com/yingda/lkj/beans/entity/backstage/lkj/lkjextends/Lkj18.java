package com.yingda.lkj.beans.entity.backstage.lkj.lkjextends;

import com.yingda.lkj.beans.entity.backstage.dataversion.DataApproveFlow;
import com.yingda.lkj.beans.pojo.approvedata.VersionData;
import com.yingda.lkj.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * @author hood  2020/4/29
 */
@Entity
@Table(name = "lkj_18", schema = "illustrious", catalog = "")
public class Lkj18 extends VersionData {
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
    private String deviceId;
    private String turnoutFrogNumber; // 道岔辙叉号
    private String remark;
    private Timestamp addTime;
    private Timestamp updateTime;
    private String dataVersionId; // 版本id
    private Double dataVersionNumber; // 版本号
    private byte approveStatus; // 审核状态
    private String uniqueKey; // 唯一键
    //page file
    private String bureauName;
    private String bureauCode;
    private String railwayLineName;
    private String railwayLineCode;
    private String stationName;
    private String stationCode;
    private String deviceCode;
    private String deviceTypeId;
    private String semaphoreType; // 信号机类型
    private String kilometerMark; // 信号机位置(公里标)
    private String trackCircuitStandard; // 轨道电路制式
    private String dataApproveFlowId;
    private byte outdated;
    private String operation;

    public Lkj18() {
    }

    public Lkj18(Lkj18 rawLkj18, DataApproveFlow dataApproveFlow) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        this.id = UUID.randomUUID().toString();

        this.dataApproveFlowId = dataApproveFlow.getId();

        this.addTime = current;
        this.updateTime = current;
        this.approveStatus = PENDING_REVIEW;
        this.outdated = OUTDATED;

        this.bureauId = rawLkj18.getBureauId();
        this.railwayLineId = rawLkj18.getRailwayLineId();
        this.downriver = rawLkj18.getDownriver();
        this.stationId = rawLkj18.getStationId();
        this.deviceId = rawLkj18.getDeviceId();
        this.trackCircuitStandard = rawLkj18.getTrackCircuitStandard();
        this.turnoutFrogNumber = rawLkj18.getTurnoutFrogNumber();
        this.remark = rawLkj18.getRemark();

        String uniqueKey = rawLkj18.getUniqueKey();
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
    @Column(name = "device_id", nullable = false, length = 36)
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Basic
    @Column(name = "turnout_frog_number", nullable = false, length = 255)
    public String getTurnoutFrogNumber() {
        return turnoutFrogNumber;
    }

    public void setTurnoutFrogNumber(String turnoutFrogNumber) {
        this.turnoutFrogNumber = turnoutFrogNumber;
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
    public String getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(String deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
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
    public String getSemaphoreType() {
        return semaphoreType;
    }

    public void setSemaphoreType(String semaphoreType) {
        this.semaphoreType = semaphoreType;
    }

    @Transient
    public String getKilometerMark() {
        return kilometerMark;
    }

    public void setKilometerMark(String kilometerMark) {
        this.kilometerMark = kilometerMark;
    }

    @Transient
    public String getTrackCircuitStandard() {
        return trackCircuitStandard;
    }

    public void setTrackCircuitStandard(String trackCircuitStandard) {
        this.trackCircuitStandard = trackCircuitStandard;
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

    @Transient
    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lkj18 lkj18 = (Lkj18) o;
        return Objects.equals(id, lkj18.id) &&
                Objects.equals(bureauId, lkj18.bureauId) &&
                Objects.equals(railwayLineId, lkj18.railwayLineId) &&
                Objects.equals(downriver, lkj18.downriver) &&
                Objects.equals(stationId, lkj18.stationId) &&
                Objects.equals(deviceId, lkj18.deviceId) &&
                Objects.equals(turnoutFrogNumber, lkj18.turnoutFrogNumber) &&
                Objects.equals(remark, lkj18.remark) &&
                Objects.equals(addTime, lkj18.addTime) &&
                Objects.equals(updateTime, lkj18.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bureauId, railwayLineId, downriver, stationId, deviceId, turnoutFrogNumber, remark, addTime, updateTime);
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
