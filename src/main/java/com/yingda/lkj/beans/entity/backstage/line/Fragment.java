package com.yingda.lkj.beans.entity.backstage.line;

import com.yingda.lkj.beans.Constant;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * 区间, 每个铁路线(railway_line)下有多个区间
 *
 * @author hood  2019/12/30
 */
@Entity
@Table(name = "fragment")
public class Fragment {

    // fragmentType 0 : 站间， 1 : 站
    public static final byte BETWEEN_STATIONS = 0;
    public static final byte STATION = 1;

    private String id;
    private String workAreaId; // 所在工区
    private String railwayLineId; // 所属线路
    private byte fragmentType; // 区间类型
    private byte hide;
    private int seq;
    private Timestamp addTime;
    private Timestamp updateTime;
    private String name;
    private String code;

    public Fragment() {
    }

    public Fragment(byte fragmentType) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        this.id = UUID.randomUUID().toString();
        this.fragmentType = fragmentType;
        this.hide = Constant.SHOW;
        this.seq = 0;
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
    @Column(name = "work_area_id", nullable = false, length = 36)
    public String getWorkAreaId() {
        return workAreaId;
    }

    public void setWorkAreaId(String workAreaId) {
        this.workAreaId = workAreaId;
    }

    @Basic
    @Column(name = "railway_line_id", nullable = false, length = 36)
    public String getRailwayLineId() {
        return railwayLineId;
    }

    public void setRailwayLineId(String railwayLineId) {
        this.railwayLineId = railwayLineId;
    }

    @Basic
    @Column(name = "fragment_type", nullable = false)
    public byte getFragmentType() {
        return fragmentType;
    }

    public void setFragmentType(byte fragmentType) {
        this.fragmentType = fragmentType;
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
    @Column(name = "seq", nullable = false)
    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
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
        Fragment fragment = (Fragment) o;
        return fragmentType == fragment.fragmentType &&
                hide == fragment.hide &&
                Objects.equals(id, fragment.id) &&
                Objects.equals(workAreaId, fragment.workAreaId) &&
                Objects.equals(railwayLineId, fragment.railwayLineId) &&
                Objects.equals(addTime, fragment.addTime) &&
                Objects.equals(updateTime, fragment.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, workAreaId, railwayLineId, fragmentType, hide, addTime, updateTime);
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
    @Column(name = "code", nullable = false, length = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
