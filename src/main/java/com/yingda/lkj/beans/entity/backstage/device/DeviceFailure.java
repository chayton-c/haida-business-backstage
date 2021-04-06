package com.yingda.lkj.beans.entity.backstage.device;

import com.yingda.lkj.beans.entity.system.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * @author hood  2020/8/5
 */
@Entity
@Table(name = "device_failure", schema = "power_plant_etms", catalog = "")
public class DeviceFailure {

    public static final byte UNTREATED = 0;
    public static final byte PROCESSING = 1;
    public static final byte PROCESSED = 2;

    private String id;
    private String userId;
    private String deviceId;
    private String executorTel; // 联系电话
    private String failureMessage; // 故障信息
    private String remark;
    private String images;
    private byte processingStatus;
    private Timestamp failureTime;
    private Timestamp executeTime;
    private Timestamp addTime;
    private User user;
    private Device device;

    public DeviceFailure() {
    }

    public DeviceFailure(User user, Device device, String executorTel, String failureMessage, String remark,
                         String images, Timestamp failureTime) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        this.id = UUID.randomUUID().toString();
        this.executorTel = executorTel;
        this.failureMessage = failureMessage;
        this.remark = remark;
        this.images = images;

        this.processingStatus = UNTREATED;
        this.failureTime = failureTime;
        this.executeTime = current;
        this.addTime = current;

        this.user = user;
        this.device = device;
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
    @Column(name = "user_id", nullable = false, length = 36, updatable = false, insertable = false)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "device_id", nullable = false, length = 36, updatable = false, insertable = false)
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Basic
    @Column(name = "failure_message", nullable = true, length = 500)
    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    @Basic
    @Column(name = "remark", nullable = true, length = 500)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String deviceLocation) {
        this.remark = deviceLocation;
    }

    @Basic
    @Column(name = "images", nullable = true, length = 3000)
    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceFailure that = (DeviceFailure) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(deviceId, that.deviceId) &&
                Objects.equals(failureMessage, that.failureMessage) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(images, that.images) &&
                Objects.equals(executeTime, that.executeTime) &&
                Objects.equals(addTime, that.addTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, deviceId, failureMessage, remark, images, executeTime, addTime);
    }

    @Basic
    @Column(name = "executor_tel", nullable = true, length = 255)
    public String getExecutorTel() {
        return executorTel;
    }

    public void setExecutorTel(String executorTel) {
        this.executorTel = executorTel;
    }

    @Basic
    @Column(name = "failure_time", nullable = true)
    public Timestamp getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(Timestamp failureTime) {
        this.failureTime = failureTime;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User userByUserId) {
        this.user = userByUserId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id", referencedColumnName = "id", nullable = false)
    public Device getDevice() {
        return device;
    }

    public void setDevice(Device deviceByDeviceId) {
        this.device = deviceByDeviceId;
    }

    @Basic
    @Column(name = "processing_status", nullable = false)
    public byte getProcessingStatus() {
        return processingStatus;
    }

    public void setProcessingStatus(byte processingStatus) {
        this.processingStatus = processingStatus;
    }
}
