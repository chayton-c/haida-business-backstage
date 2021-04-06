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
@Table(name = "lkj_20", schema = "illustrious", catalog = "")
public class Lkj20 extends VersionData {
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
    private String nameForLkj; // lkj数据车站名
    private String customStationCode; // 自定义车站编号
    private String description; // 路径说明
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

    public Lkj20(){}

    public Lkj20(Lkj20 rawLkj20, DataApproveFlow dataApproveFlow){
        Timestamp current = new Timestamp(System.currentTimeMillis());

        this.id = UUID.randomUUID().toString();

        this.dataApproveFlowId = dataApproveFlow.getId();

        this.bureauId = rawLkj20.bureauId;
        this.railwayLineId = rawLkj20.railwayLineId;
        this.stationId = rawLkj20.stationId;
        this.downriver = rawLkj20.downriver;
        this.customStationCode = rawLkj20.customStationCode;
        this.description = rawLkj20.description;
        this.remark = rawLkj20.remark;
        this.nameForLkj = rawLkj20.nameForLkj;

        this.addTime = current;
        this.updateTime = current;
        this.approveStatus = PENDING_REVIEW;
        this.outdated = OUTDATED;

        String uniqueKey = rawLkj20.getUniqueKey();
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
    @Column(name = "name_for_lkj", nullable = false, length = 255)
    public String getNameForLkj() {
        return nameForLkj;
    }

    public void setNameForLkj(String nameForLkj) {
        this.nameForLkj = nameForLkj;
    }

    @Basic
    @Column(name = "custom_station_code", nullable = false, length = 255)
    public String getCustomStationCode() {
        return customStationCode;
    }

    public void setCustomStationCode(String customStationCode) {
        this.customStationCode = customStationCode;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        Lkj20 lkj20 = (Lkj20) o;
        return Objects.equals(id, lkj20.id) &&
                Objects.equals(bureauId, lkj20.bureauId) &&
                Objects.equals(railwayLineId, lkj20.railwayLineId) &&
                Objects.equals(downriver, lkj20.downriver) &&
                Objects.equals(stationId, lkj20.stationId) &&
                Objects.equals(nameForLkj, lkj20.nameForLkj) &&
                Objects.equals(customStationCode, lkj20.customStationCode) &&
                Objects.equals(description, lkj20.description) &&
                Objects.equals(remark, lkj20.remark) &&
                Objects.equals(addTime, lkj20.addTime) &&
                Objects.equals(updateTime, lkj20.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bureauId, railwayLineId, downriver, stationId, nameForLkj, customStationCode, description, remark, addTime, updateTime);
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
