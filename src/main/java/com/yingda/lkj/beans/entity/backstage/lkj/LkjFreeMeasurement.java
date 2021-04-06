package com.yingda.lkj.beans.entity.backstage.lkj;

import com.yingda.lkj.beans.pojo.app.AppLkjFreeMeasurementReceivce;
import com.yingda.lkj.beans.pojo.app.AppLkjFreeNodeReceive;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author hood  2020/4/26
 */
@Entity
@Table(name = "lkj_free_measurement", schema = "illustrious", catalog = "")
public class LkjFreeMeasurement {
    private String id;
    private String executeUserId;
    private double distance;
    private String pointNames;
    private Timestamp measureTime;
    private Timestamp addTime;
    private Timestamp updateTime;

    // pageField
    private List<AppLkjFreeNodeReceive> nodes;
    private String executeUserName;
    private String workshopName;

    public LkjFreeMeasurement() {
    }

    public LkjFreeMeasurement(AppLkjFreeMeasurementReceivce appLkjFreeMeasurementReceivce, String executeUserId, String pointNames) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        this.id = UUID.randomUUID().toString();
        this.executeUserId = executeUserId;
        this.distance = appLkjFreeMeasurementReceivce.getDistance();
        this.pointNames = pointNames;
        this.measureTime = new Timestamp(appLkjFreeMeasurementReceivce.getComplete_time());
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
    @Column(name = "execute_user_id", nullable = false, length = 36)
    public String getExecuteUserId() {
        return executeUserId;
    }

    public void setExecuteUserId(String executeUserId) {
        this.executeUserId = executeUserId;
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
    @Column(name = "point_names", nullable = false, length = 255)
    public String getPointNames() {
        return pointNames;
    }

    public void setPointNames(String pointNames) {
        this.pointNames = pointNames;
    }

    @Basic
    @Column(name = "measure_time", nullable = true)
    public Timestamp getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(Timestamp measureTime) {
        this.measureTime = measureTime;
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
        LkjFreeMeasurement that = (LkjFreeMeasurement) o;
        return Double.compare(that.distance, distance) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(executeUserId, that.executeUserId) &&
                Objects.equals(pointNames, that.pointNames) &&
                Objects.equals(measureTime, that.measureTime) &&
                Objects.equals(addTime, that.addTime) &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, executeUserId, distance, pointNames, measureTime, addTime, updateTime);
    }

    @Transient
    public List<AppLkjFreeNodeReceive> getNodes() {
        return nodes;
    }

    public void setNodes(List<AppLkjFreeNodeReceive> nodes) {
        this.nodes = nodes;
    }

    @Transient
    public String getExecuteUserName() {
        return executeUserName;
    }

    public void setExecuteUserName(String executeUserName) {
        this.executeUserName = executeUserName;
    }

    @Transient
    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }
}
