package com.yingda.lkj.beans.entity.backstage.measurement;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * 测量项即子模板，如：电气特性模板下的U灯
 *
 * @author hood  2020/3/11
 */
@Entity
@Table(name = "measurement_item", schema = "illustrious", catalog = "")
public class MeasurementItem {
    private String id;
    private String measurementTemplateId; // 测量模板id
    private String name;
    private String description;
    private byte hide;
    private String remark;
    private Timestamp addTime;
    private Timestamp updateTime;

    // pageField
    private List<MeasurementItemField> measurementItemFields;
    private int measurementItemFieldCount;

    @Id
    @Column(name = "id", nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "measurement_template_id", nullable = false, length = 36)
    public String getMeasurementTemplateId() {
        return measurementTemplateId;
    }

    public void setMeasurementTemplateId(String measurementTemplateId) {
        this.measurementTemplateId = measurementTemplateId;
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
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

        MeasurementItem that = (MeasurementItem) o;

        if (hide != that.hide) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (measurementTemplateId != null ? !measurementTemplateId.equals(that.measurementTemplateId) : that.measurementTemplateId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (addTime != null ? !addTime.equals(that.addTime) : that.addTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (measurementTemplateId != null ? measurementTemplateId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) hide;
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (addTime != null ? addTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @Transient
    public List<MeasurementItemField> getMeasurementItemFields() {
        return measurementItemFields;
    }

    public void setMeasurementItemFields(List<MeasurementItemField> measurementItemFields) {
        this.measurementItemFields = measurementItemFields;
    }

    @Transient
    public int getMeasurementItemFieldCount() {
        return measurementItemFieldCount;
    }

    public void setMeasurementItemFieldCount(int measurementItemFieldCount) {
        this.measurementItemFieldCount = measurementItemFieldCount;
    }
}
