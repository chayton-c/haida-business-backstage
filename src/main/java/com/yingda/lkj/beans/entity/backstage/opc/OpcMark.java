package com.yingda.lkj.beans.entity.backstage.opc;

import com.yingda.lkj.beans.entity.backstage.location.Location;
import com.yingda.lkj.beans.pojo.location.ContainsLocation;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * @author hood  2020/12/14
 */
@Entity
@Table(name = "opc_mark", schema = "opc_measurement", catalog = "")
public class OpcMark implements ContainsLocation {
    private String id;
    private String opcId;
    private String opcMarkTypeId;
    private String nextOpcMarkId; // 类似链表，类型为桥梁隧道等时，当前标志点关联的下一个节点
    private Double kilometerMark; // 公里标(米)
    private String name;
    private String code;
    private int seq;
    private Timestamp addTime;
    private Timestamp updateTime;
    private String typeName;

    // pageFields
    private List<Location> locations;

    @Id
    @Column(name = "id", nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "opc_id", nullable = false, length = 36)
    public String getOpcId() {
        return opcId;
    }

    public void setOpcId(String opcId) {
        this.opcId = opcId;
    }

    @Basic
    @Column(name = "opc_mark_type_id", nullable = true, length = 36)
    public String getOpcMarkTypeId() {
        return opcMarkTypeId;
    }

    public void setOpcMarkTypeId(String opcMarkTypeId) {
        this.opcMarkTypeId = opcMarkTypeId;
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
    @Column(name = "code", nullable = true, length = 255)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        OpcMark opcMark = (OpcMark) o;
        return Objects.equals(id, opcMark.id) &&
                Objects.equals(opcId, opcMark.opcId) &&
                Objects.equals(opcMarkTypeId, opcMark.opcMarkTypeId) &&
                Objects.equals(name, opcMark.name) &&
                Objects.equals(code, opcMark.code) &&
                Objects.equals(addTime, opcMark.addTime) &&
                Objects.equals(updateTime, opcMark.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, opcId, opcMarkTypeId, name, code, addTime, updateTime);
    }

    @Transient
    @Override
    public List<Location> getLocations() {
        return locations;
    }

    @Override
    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    @Basic
    @Column(name = "next_opc_mark_id", nullable = true, length = 36)
    public String getNextOpcMarkId() {
        return nextOpcMarkId;
    }

    public void setNextOpcMarkId(String nextOpcMarkId) {
        this.nextOpcMarkId = nextOpcMarkId;
    }

    @Basic
    @Column(name = "seq", nullable = false)
    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Basic
    @Column(name = "kilometer_mark", nullable = true, precision = 0)
    public Double getKilometerMark() {
        return kilometerMark;
    }

    public void setKilometerMark(Double kilometerMark) {
        this.kilometerMark = kilometerMark;
    }

    @Transient
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
