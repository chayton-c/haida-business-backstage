package com.yingda.lkj.beans.pojo.app;

import com.yingda.lkj.beans.entity.backstage.line.Station;
import com.yingda.lkj.beans.entity.backstage.organization.Organization;
import lombok.Data;

/**
 * @author hood  2020/4/13
 */
@Data
public class AppWorkshop {
    private String workshopId;
    private String workshopName;

    public AppWorkshop() {
    }

    public AppWorkshop(Organization workshop) {
        this.workshopId = workshop.getId();
        this.workshopName = workshop.getName();
    }
}
