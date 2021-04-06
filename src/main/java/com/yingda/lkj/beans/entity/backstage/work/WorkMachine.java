package com.yingda.lkj.beans.entity.backstage.work;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "work_machine", schema = "opc_measurement", catalog = "")
public class WorkMachine {
    private String id;
    private String machName;

    @Id
    @Column(name = "id", nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "mach_name", nullable = false, length = 30)
    public String getMachName() {
        return machName;
    }

    public void setMachName(String machName) {
        this.machName = machName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkMachine that = (WorkMachine) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(machName, that.machName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, machName);
    }
}
