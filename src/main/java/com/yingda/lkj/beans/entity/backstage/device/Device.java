package com.yingda.lkj.beans.entity.backstage.device;

import com.yingda.lkj.beans.Constant;
import com.yingda.lkj.utils.StringUtils;
import org.apache.poi.util.StringUtil;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 设备(信号机，转辙机。。。)
 *
 * @author hood  2019/12/30
 */
@Entity
@Table(name = "device")
public class Device {

    // errorState字段
    public static final byte NOT_CHECKED = 0; // 未检修
    public static final byte NORMAL = 1; // 正常
    public static final byte ERROR = 2; // 异常

    // spare字段，使用/备用
    public static final byte USING = 0;
    public static final byte SPARE = 1;

    // hasNFC字段
    public static final byte NO_NFC = 0;
    public static final byte HAS_NFC = 1;

    // outdoor字段，室内/室外
    public static final byte INDOOR = 0;
    public static final byte OUTDOOR = 1;

    private String id;
    private String stationId; // 所属车站
    private String deviceTypeId; // 设备类型id
    private String deviceSubTypeId; // 子类型id
    private String manufacturerName; // 生产厂家
    private String deviceModel; // 设备型号
    private String exFactorySeriesNumber; // 厂内编号
    private Byte outdoor; // 室内/室外
    private Byte hasNFC; // 是否写入过nfc
    private String name;
    private String code;
    private byte spare; // 使用/备用
    private byte errorState; // 检修状态
    private String positionInfo; // 位置信息
    private byte hide;
    private Timestamp checkTime; // 上次检修日期，没有填null
    private Timestamp deployTime; // 上道时间
    private Timestamp addTime;
    private Timestamp updateTime;
    private String remark;

    // page field
    private List<DeviceExtendValues> extendValues;
    private String railwayLineNames;
    private String stationName;
    private String workshopName;
    private String workAreaName;
    private String deviceTypeName;
    private String deviceSubTypeName;
    private String deviceTypeIdOrDeviceSubTypeId;

    public Device() {
    }

    public Device(Device pageDevice) {
        this.id = UUID.randomUUID().toString();

        this.stationId = pageDevice.getStationId();
        this.deviceTypeId = pageDevice.getDeviceTypeId();
        if (StringUtils.isNotEmpty(pageDevice.getDeviceSubTypeId()))
            this.deviceSubTypeId = pageDevice.getDeviceSubTypeId();
        this.deviceModel = pageDevice.getDeviceModel();
        this.remark = pageDevice.getRemark();
        this.name = pageDevice.getName();
        this.code = pageDevice.getCode();

        this.hasNFC = Constant.FALSE;
        this.errorState = Constant.FALSE;
        this.hide = Constant.SHOW;
        this.addTime = new Timestamp(System.currentTimeMillis());
        this.checkTime = this.addTime;
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
    @Column(name = "station_id", nullable = false, length = 36)
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "spare", nullable = false)
    public byte getSpare() {
        return spare;
    }

    public void setSpare(byte spare) {
        this.spare = spare;
    }

    @Basic
    @Column(name = "deploy_time", nullable = true)
    public Timestamp getDeployTime() {
        return deployTime;
    }

    public void setDeployTime(Timestamp deployTime) {
        this.deployTime = deployTime;
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
    @Column(name = "device_type_id", nullable = false, length = 36)
    public String getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(String deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    @Transient
    public List<DeviceExtendValues> getExtendValues() {
        return extendValues;
    }

    public void setExtendValues(List<DeviceExtendValues> extendValues) {
        this.extendValues = extendValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return spare == device.spare &&
                Objects.equals(id, device.id) &&
                Objects.equals(stationId, device.stationId) &&
                Objects.equals(deployTime, device.deployTime) &&
                Objects.equals(addTime, device.addTime) &&
                Objects.equals(updateTime, device.updateTime) &&
                Objects.equals(deviceTypeId, device.deviceTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stationId, deviceTypeId, spare, deployTime, addTime, updateTime);
    }

    @Basic
    @Column(name = "code", nullable = false, length = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "manufacturer_name", nullable = true, length = 255)
    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    @Basic
    @Column(name = "device_model", nullable = true, length = 50)
    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    @Basic
    @Column(name = "ex_factory_series_number", nullable = true, length = 50)
    public String getExFactorySeriesNumber() {
        return exFactorySeriesNumber;
    }

    public void setExFactorySeriesNumber(String exFactorySeriesNumber) {
        this.exFactorySeriesNumber = exFactorySeriesNumber;
    }

    @Basic
    @Column(name = "outdoor", nullable = true)
    public Byte getOutdoor() {
        return outdoor;
    }

    public void setOutdoor(Byte outdoor) {
        this.outdoor = outdoor;
    }

    @Basic
    @Column(name = "has_nfc", nullable = true)
    public Byte getHasNFC() {
        return hasNFC;
    }

    public void setHasNFC(Byte hasNFC) {
        this.hasNFC = hasNFC;
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
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Transient
    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    @Transient
    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    @Basic
    @Column(name = "error_state", nullable = false)
    public byte getErrorState() {
        return errorState;
    }

    public void setErrorState(byte errorState) {
        this.errorState = errorState;
    }

    @Basic
    @Column(name = "check_time", nullable = true)
    public Timestamp getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Timestamp checkTime) {
        this.checkTime = checkTime;
    }

    @Basic
    @Column(name = "position_info", nullable = true, length = 255)
    public String getPositionInfo() {
        return positionInfo;
    }

    public void setPositionInfo(String positionInfo) {
        this.positionInfo = positionInfo;
    }

    @Basic
    @Column(name = "device_sub_type_id", nullable = true, length = 36)
    public String getDeviceSubTypeId() {
        return deviceSubTypeId;
    }

    public void setDeviceSubTypeId(String deviceSubTypeId) {
        this.deviceSubTypeId = deviceSubTypeId;
    }

    @Basic
    @Column(name = "hide", nullable = false)
    public byte getHide() {
        return hide;
    }

    public void setHide(byte hide) {
        this.hide = hide;
    }

    @Transient
    public String getDeviceSubTypeName() {
        return deviceSubTypeName;
    }

    public void setDeviceSubTypeName(String deviceSubTypeName) {
        this.deviceSubTypeName = deviceSubTypeName;
    }

    @Transient
    public String getWorkAreaName() {
        return workAreaName;
    }

    public void setWorkAreaName(String workAreaName) {
        this.workAreaName = workAreaName;
    }

    @Transient
    public String getDeviceTypeIdOrDeviceSubTypeId() {
        return deviceTypeIdOrDeviceSubTypeId;
    }

    public void setDeviceTypeIdOrDeviceSubTypeId(String deviceTypeIdOrDeviceSubTypeId) {
        this.deviceTypeIdOrDeviceSubTypeId = deviceTypeIdOrDeviceSubTypeId;
    }

    @Transient
    public String getRailwayLineNames() {
        return railwayLineNames;
    }

    public void setRailwayLineNames(String railwayLineNames) {
        this.railwayLineNames = railwayLineNames;
    }
}
