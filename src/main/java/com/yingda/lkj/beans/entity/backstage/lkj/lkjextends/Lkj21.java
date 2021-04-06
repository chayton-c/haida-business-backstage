package com.yingda.lkj.beans.entity.backstage.lkj.lkjextends;

import com.yingda.lkj.beans.pojo.approvedata.VersionData;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author hood  2020/4/29
 */
@Entity
@Table(name = "lkj_21", schema = "illustrious", catalog = "")
public class Lkj21 extends VersionData {
    private String id;
    private String bureauId;
    private String railwayLineId;
    private String downriver;
    private String stationId;
    private String deviceIdForInAndOutStations; // 进出站信号机
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
    private String dataApproveFlowId;
    private byte outdated;

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
    @Column(name = "device_id_for_in_and_out_stations", nullable = false, length = 36)
    public String getDeviceIdForInAndOutStations() {
        return deviceIdForInAndOutStations;
    }

    public void setDeviceIdForInAndOutStations(String deviceIdForInAndOutStations) {
        this.deviceIdForInAndOutStations = deviceIdForInAndOutStations;
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
    @Column(name = "add_time", nullable = true)
    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
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
        Lkj21 lkj21 = (Lkj21) o;
        return Objects.equals(id, lkj21.id) &&
                Objects.equals(bureauId, lkj21.bureauId) &&
                Objects.equals(railwayLineId, lkj21.railwayLineId) &&
                Objects.equals(downriver, lkj21.downriver) &&
                Objects.equals(stationId, lkj21.stationId) &&
                Objects.equals(deviceIdForInAndOutStations, lkj21.deviceIdForInAndOutStations) &&
                Objects.equals(updateTime, lkj21.updateTime) &&
                Objects.equals(addTime, lkj21.addTime) &&
                Objects.equals(remark, lkj21.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bureauId, railwayLineId, downriver, stationId, deviceIdForInAndOutStations, remark, addTime, updateTime);
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
}
