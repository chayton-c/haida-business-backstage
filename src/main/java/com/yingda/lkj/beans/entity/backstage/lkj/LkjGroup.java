package com.yingda.lkj.beans.entity.backstage.lkj;

import com.yingda.lkj.beans.entity.backstage.line.Fragment;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 *
 *
 * @author hood  2020/3/21
 */
@Entity
@Table(name = "lkj_group", schema = "illustrious", catalog = "")
public class LkjGroup {
    private String id;
    private String name;
    private Timestamp addTime;
    private Timestamp updateTime;
    private String fragmentId;
    private String uniqueCode;
    private byte downriver;
    private byte retrograde;
    private int code;

    public LkjGroup() {
    }

    public LkjGroup(Fragment fragment, int code, byte downriver, byte retrograde, String uniqueCode) {
        this.id = UUID.randomUUID().toString();
        this.name = fragment.getName() + "-" + code;
        this.code = code;
        this.fragmentId = fragment.getId();
        this.downriver = downriver;
        this.retrograde = retrograde;
        this.uniqueCode = uniqueCode;

        Timestamp current = new Timestamp(System.currentTimeMillis());
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
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "fragment_id", nullable = false, length = 36)
    public String getFragmentId() {
        return fragmentId;
    }

    public void setFragmentId(String fragmentId) {
        this.fragmentId = fragmentId;
    }

    @Basic
    @Column(name = "downriver", nullable = false)
    public byte getDownriver() {
        return downriver;
    }

    public void setDownriver(byte downriver) {
        this.downriver = downriver;
    }

    @Basic
    @Column(name = "retrograde", nullable = false)
    public byte getRetrograde() {
        return retrograde;
    }

    public void setRetrograde(byte retrograde) {
        this.retrograde = retrograde;
    }

    @Basic
    @Column(name = "code", nullable = false)
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Basic
    @Column(name = "unique_code", nullable = false, length = -1)
    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LkjGroup lkjGroup = (LkjGroup) o;
        return downriver == lkjGroup.downriver &&
                retrograde == lkjGroup.retrograde &&
                code == lkjGroup.code &&
                Objects.equals(id, lkjGroup.id) &&
                Objects.equals(name, lkjGroup.name) &&
                Objects.equals(addTime, lkjGroup.addTime) &&
                Objects.equals(updateTime, lkjGroup.updateTime) &&
                Objects.equals(fragmentId, lkjGroup.fragmentId) &&
                Objects.equals(uniqueCode, lkjGroup.uniqueCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, addTime, updateTime, fragmentId, uniqueCode, downriver, retrograde, code);
    }
}
