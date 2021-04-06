package com.yingda.lkj.beans.entity.backstage.measurement;

import com.yingda.lkj.beans.entity.backstage.device.Device;
import com.yingda.lkj.beans.entity.system.User;
import com.yingda.lkj.beans.pojo.app.etms.freeetms.AppFreeDeviceMaintenanceRecord;
import com.yingda.lkj.utils.date.DateUtil;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * 设备自由测量记录
 *
 * @author hood  2020/6/10
 */
@Entity
@Table(name = "free_device_maintenance_record", schema = "illustrious", catalog = "")
public class FreeDeviceMaintenanceRecord {
    private String id;
    private String name;
    private String userId; // 测量人
    private String deviceId;
    private String sectionId;
    private String stationId;
    private Double longitude; // 测量位置经度
    private Double latitude; // 纬度
    private Timestamp measurementStartTime; // 开始测量时间
    private Timestamp measurementEndTime; // 测量完成时间
    private Timestamp addTime;

    public FreeDeviceMaintenanceRecord() {
    }

    public FreeDeviceMaintenanceRecord(AppFreeDeviceMaintenanceRecord appFreeDeviceMaintenanceRecord, User user, Device device) {
        String currentTimeFormat = DateUtil.format(new Timestamp(appFreeDeviceMaintenanceRecord.getStartTime()), "yyyy-MM-dd HH:mm:ss");
        String generatedName = currentTimeFormat + "自由测量";

        this.id = UUID.randomUUID().toString();
        this.userId = user.getId();
        this.sectionId = user.getSectionId();
        this.stationId = device.getStationId();
        // 自动生成名称
        this.name = generatedName;
        this.deviceId = appFreeDeviceMaintenanceRecord.getDeviceId();
        this.longitude = appFreeDeviceMaintenanceRecord.getLon();
        this.latitude = appFreeDeviceMaintenanceRecord.getLat();
        this.measurementStartTime = new Timestamp(appFreeDeviceMaintenanceRecord.getStartTime());
        this.measurementEndTime = new Timestamp(appFreeDeviceMaintenanceRecord.getTimer());
        this.addTime = new Timestamp(System.currentTimeMillis());
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
    @Column(name = "device_id", nullable = false, length = 36)
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Basic
    @Column(name = "longitude", nullable = true, precision = 0)
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "latitude", nullable = true, precision = 0)
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "measurement_start_time", nullable = true)
    public Timestamp getMeasurementStartTime() {
        return measurementStartTime;
    }

    public void setMeasurementStartTime(Timestamp measurementStartTime) {
        this.measurementStartTime = measurementStartTime;
    }

    @Basic
    @Column(name = "measurement_end_time", nullable = true)
    public Timestamp getMeasurementEndTime() {
        return measurementEndTime;
    }

    public void setMeasurementEndTime(Timestamp measurementEndTime) {
        this.measurementEndTime = measurementEndTime;
    }

    @Basic
    @Column(name = "add_time", nullable = false)
    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FreeDeviceMaintenanceRecord that = (FreeDeviceMaintenanceRecord) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(deviceId, that.deviceId) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(measurementStartTime, that.measurementStartTime) &&
                Objects.equals(measurementEndTime, that.measurementEndTime) &&
                Objects.equals(addTime, that.addTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, deviceId, longitude, latitude, measurementStartTime, measurementEndTime, addTime);
    }

    @Basic
    @Column(name = "user_id", nullable = false, length = 36)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
