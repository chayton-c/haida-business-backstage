package com.yingda.lkj.beans.entity.backstage.location;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * 位置信息
 * @author hood  2020/12/14
 */
@Entity
@Table(name = "location", schema = "opc_measurement")
public class Location {

    // type字段
    public static final byte OPC = 1; // 光电缆位置
    public static final byte OPC_MARK = 2; // 光电缆标识
    public static final byte CONSTRUCTION_CONTROL_PLAN_POINT = 3; // 方案施工点
    public static final byte RAILWAY_LINE = 4; // 铁路线
    public static final byte KILOMETER_MARKS = 5; // 公里标
    public static final byte EQUIPMENT = 6; // 设备

    private String id;
    private String dataId;
    private byte type;
    private double longitude;
    private double latitude;
    private Double altitude;
    private Timestamp addTime;
    @SerializedName("serialNumber")
    private int seq;

    public Location() {
    }

    public Location(String dataId, double longitude, double latitude, int seq, byte type) {
        this.id = UUID.randomUUID().toString();
        this.dataId = dataId;
        this.type = type;
        this.longitude = longitude;
        this.latitude = latitude;
        this.addTime = new Timestamp(System.currentTimeMillis());
        this.seq = seq;
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
    @Column(name = "data_id", nullable = false, length = 36)
    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    @Basic
    @Column(name = "longitude", nullable = false, precision = 0)
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "latitude", nullable = false, precision = 0)
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
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
    @Column(name = "add_time", nullable = true)
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
        Location location = (Location) o;
        return type == location.type &&
                Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Basic
    @Column(name = "seq", nullable = false)
    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
