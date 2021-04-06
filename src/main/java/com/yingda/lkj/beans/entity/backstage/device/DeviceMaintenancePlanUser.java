package com.yingda.lkj.beans.entity.backstage.device;

import com.yingda.lkj.beans.Constant;
import com.yingda.lkj.beans.entity.system.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * 设备养护计划，执行人(用户)扩展表
 *
 * @author hood  2020/4/2
 */
@Entity
@Table(name = "device_maintenance_plan_user", schema = "illustrious", catalog = "")
public class DeviceMaintenancePlanUser {
    private String id;
    private String executeUserId; // 执行人id
    private String deviceMaintenancePlanId; // 对应养护计划id
    private String executeUserDisplayName; // 执行人显示名称
    private String executeUserName; // 执行人账号名
    private byte hide;
    private Timestamp addTime;
    private Timestamp updateTime;

    public DeviceMaintenancePlanUser() {
    }

    public DeviceMaintenancePlanUser(User executeUser, String deviceMaintenancePlanId) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        this.id = UUID.randomUUID().toString();

        this.deviceMaintenancePlanId = deviceMaintenancePlanId;

        this.executeUserId = executeUser.getId();
        this.executeUserDisplayName = executeUser.getDisplayName();
        this.executeUserName = executeUser.getUserName();

        this.hide = Constant.SHOW;

        this.addTime = timestamp;
        this.updateTime = timestamp;
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
    @Column(name = "device_maintenance_plan_id", nullable = false, length = 36)
    public String getDeviceMaintenancePlanId() {
        return deviceMaintenancePlanId;
    }

    public void setDeviceMaintenancePlanId(String deviceMaintenancePlanId) {
        this.deviceMaintenancePlanId = deviceMaintenancePlanId;
    }

    @Basic
    @Column(name = "hide", nullable = false)
    public byte getHide() {
        return hide;
    }

    public void setHide(byte hide) {
        this.hide = hide;
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
        DeviceMaintenancePlanUser that = (DeviceMaintenancePlanUser) o;
        return hide == that.hide &&
                Objects.equals(id, that.id) &&
                Objects.equals(executeUserId, that.executeUserId) &&
                Objects.equals(deviceMaintenancePlanId, that.deviceMaintenancePlanId) &&
                Objects.equals(addTime, that.addTime) &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, executeUserId, deviceMaintenancePlanId, hide, addTime, updateTime);
    }

    @Basic
    @Column(name = "execute_user_display_name", nullable = false, length = 36)
    public String getExecuteUserDisplayName() {
        return executeUserDisplayName;
    }

    public void setExecuteUserDisplayName(String executeUserDisplayName) {
        this.executeUserDisplayName = executeUserDisplayName;
    }

    @Basic
    @Column(name = "execute_user_name", nullable = false, length = 36)
    public String getExecuteUserName() {
        return executeUserName;
    }

    public void setExecuteUserName(String executeUserName) {
        this.executeUserName = executeUserName;
    }
}
