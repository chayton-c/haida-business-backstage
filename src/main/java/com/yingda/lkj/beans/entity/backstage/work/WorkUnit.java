package com.yingda.lkj.beans.entity.backstage.work;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "work_unit", schema = "opc_measurement", catalog = "")
public class WorkUnit {
    private String id;
    private String unitName;

    @Id
    @Column(name = "id", nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "unit_name", nullable = false, length = 30)
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkUnit workUnit = (WorkUnit) o;
        return Objects.equals(id, workUnit.id) &&
                Objects.equals(unitName, workUnit.unitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, unitName);
    }
}
