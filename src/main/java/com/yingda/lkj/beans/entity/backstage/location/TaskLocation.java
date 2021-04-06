package com.yingda.lkj.beans.entity.backstage.location;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author hood  2020/5/30
 */
@Entity
@Table(name = "task_location", schema = "illustrious", catalog = "")
public class TaskLocation {

    // locationType类型
    private static final byte COORDINATE = 0; // 坐标

    private String id;
    private byte locationType; // 位置类型
    private String sectionId; // 站段id
    private String name;
    private Double x; // 图上坐标
    private Double y;
    private Double z;
    private String trackCircuitId; // 轨道电路id
    private String leftTrackCircuitId; // 左轨道电路id
    private String rightTrackCircuitId; // 右轨道电路id
    private Integer locationRow; // 排
    private Integer locationFrame; // 架
    private Integer locationGroup; // 组
    private String description;
    private String remark;
    private Timestamp addTime;
    private Timestamp updateTime;
    private String stationId;

    @Id
    @Column(name = "id", nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "location_type", nullable = false)
    public byte getLocationType() {
        return locationType;
    }

    public void setLocationType(byte locationType) {
        this.locationType = locationType;
    }

    @Basic
    @Column(name = "x", nullable = true, precision = 0)
    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    @Basic
    @Column(name = "y", nullable = true, precision = 0)
    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    @Basic
    @Column(name = "z", nullable = true, precision = 0)
    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    @Basic
    @Column(name = "track_circuit_id", nullable = true, length = 36)
    public String getTrackCircuitId() {
        return trackCircuitId;
    }

    public void setTrackCircuitId(String trackCircuitId) {
        this.trackCircuitId = trackCircuitId;
    }

    @Basic
    @Column(name = "left_track_circuit_id", nullable = true, length = 36)
    public String getLeftTrackCircuitId() {
        return leftTrackCircuitId;
    }

    public void setLeftTrackCircuitId(String leftTrackCircuitId) {
        this.leftTrackCircuitId = leftTrackCircuitId;
    }

    @Basic
    @Column(name = "right_track_circuit_id", nullable = true, length = 36)
    public String getRightTrackCircuitId() {
        return rightTrackCircuitId;
    }

    public void setRightTrackCircuitId(String rightTrackCircuitId) {
        this.rightTrackCircuitId = rightTrackCircuitId;
    }

    @Basic
    @Column(name = "location_row", nullable = true)
    public Integer getLocationRow() {
        return locationRow;
    }

    public void setLocationRow(Integer locationRow) {
        this.locationRow = locationRow;
    }

    @Basic
    @Column(name = "location_frame", nullable = true)
    public Integer getLocationFrame() {
        return locationFrame;
    }

    public void setLocationFrame(Integer locationFrame) {
        this.locationFrame = locationFrame;
    }

    @Basic
    @Column(name = "location_group", nullable = true)
    public Integer getLocationGroup() {
        return locationGroup;
    }

    public void setLocationGroup(Integer locationGroup) {
        this.locationGroup = locationGroup;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskLocation that = (TaskLocation) o;
        return locationType == that.locationType &&
                Objects.equals(id, that.id) &&
                Objects.equals(x, that.x) &&
                Objects.equals(y, that.y) &&
                Objects.equals(z, that.z) &&
                Objects.equals(trackCircuitId, that.trackCircuitId) &&
                Objects.equals(leftTrackCircuitId, that.leftTrackCircuitId) &&
                Objects.equals(rightTrackCircuitId, that.rightTrackCircuitId) &&
                Objects.equals(locationRow, that.locationRow) &&
                Objects.equals(locationFrame, that.locationFrame) &&
                Objects.equals(locationGroup, that.locationGroup) &&
                Objects.equals(description, that.description) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(addTime, that.addTime) &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, locationType, x, y, z, trackCircuitId, leftTrackCircuitId, rightTrackCircuitId, locationRow, locationFrame, locationGroup, description, remark, addTime, updateTime);
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
    @Column(name = "station_id", nullable = false, length = 36)
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
