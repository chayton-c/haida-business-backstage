package com.yingda.lkj.beans.entity.backstage.lkj;

import com.yingda.lkj.beans.pojo.app.AppLkjFreeNodeReceive;
import com.yingda.lkj.beans.pojo.app.AppLocationReceive;
import com.yingda.lkj.utils.position.CoordinateTransform;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * @author hood  2020/4/26
 */
@Entity
@Table(name = "lkj_free_measurement_location", schema = "illustrious", catalog = "")
public class LkjFreeMeasurementLocation {
    private String id;
    private String pointName;
    private String lkjFreeMeasurementId;
    private Double longitude;
    private Double longitudeOffset;
    private Double latitude;
    private Double latitudeOffset;
    private Double altitude;
    private Double altitudeOffset;
    private Timestamp executeTime;
    private Timestamp addTime;
    private Timestamp updateTime;

    public LkjFreeMeasurementLocation() {
    }

    public LkjFreeMeasurementLocation(AppLkjFreeNodeReceive appLkjFreeNodeReceive, String lkjFreeMeasurementId) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        this.id = UUID.randomUUID().toString();
        this.pointName = appLkjFreeNodeReceive.getLineName();
        this.lkjFreeMeasurementId = lkjFreeMeasurementId;

        AppLocationReceive positionInfo = appLkjFreeNodeReceive.getLocationEnd();
        if (positionInfo != null) {
            double lon = positionInfo.getLon();
            double lat = positionInfo.getLat();

            double[] lonlat = CoordinateTransform.gpsToBaidu(lon, lat);

            this.longitude = lonlat[0];
            this.latitude = lonlat[1];

            this.longitudeOffset = positionInfo.getHorizontal();
            this.latitudeOffset = positionInfo.getLatOffset();
            this.altitude = positionInfo.getAltitude();
            this.altitudeOffset = 0d;
            // 如果没有经纬度，肯定也没有measure_time
            this.executeTime = new Timestamp(appLkjFreeNodeReceive.getMeasure_time());
        }

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
    @Column(name = "point_name", nullable = true, length = 255)
    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
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
    @Column(name = "longitude_offset", nullable = true, precision = 0)
    public Double getLongitudeOffset() {
        return longitudeOffset;
    }

    public void setLongitudeOffset(Double longitudeOffset) {
        this.longitudeOffset = longitudeOffset;
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
    @Column(name = "latitude_offset", nullable = true, precision = 0)
    public Double getLatitudeOffset() {
        return latitudeOffset;
    }

    public void setLatitudeOffset(Double latitudeOffset) {
        this.latitudeOffset = latitudeOffset;
    }

    @Basic
    @Column(name = "altitude", nullable = true, precision = 0)
    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    @Basic
    @Column(name = "altitude_offset", nullable = true, precision = 0)
    public Double getAltitudeOffset() {
        return altitudeOffset;
    }

    public void setAltitudeOffset(Double altitudeOffset) {
        this.altitudeOffset = altitudeOffset;
    }

    @Basic
    @Column(name = "execute_time", nullable = true)
    public Timestamp getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Timestamp executeTime) {
        this.executeTime = executeTime;
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
    @Column(name = "lkj_free_measurement_id", nullable = false, length = 36)
    public String getLkjFreeMeasurementId() {
        return lkjFreeMeasurementId;
    }

    public void setLkjFreeMeasurementId(String lkjFreeMeasurementId) {
        this.lkjFreeMeasurementId = lkjFreeMeasurementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LkjFreeMeasurementLocation that = (LkjFreeMeasurementLocation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(pointName, that.pointName) &&
                Objects.equals(lkjFreeMeasurementId, that.lkjFreeMeasurementId) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(longitudeOffset, that.longitudeOffset) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(latitudeOffset, that.latitudeOffset) &&
                Objects.equals(altitude, that.altitude) &&
                Objects.equals(altitudeOffset, that.altitudeOffset) &&
                Objects.equals(executeTime, that.executeTime) &&
                Objects.equals(addTime, that.addTime) &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pointName, lkjFreeMeasurementId, longitude, longitudeOffset, latitude, latitudeOffset, altitude, altitudeOffset, executeTime, addTime, updateTime);
    }
}
