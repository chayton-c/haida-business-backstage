package com.yingda.lkj.beans.pojo.app;

import com.yingda.lkj.beans.entity.backstage.line.Station;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hood  2020/4/13
 */
@Data
public class AppStation {
    private String statoinId;
    private String stationName;

    public AppStation() {
    }

    public AppStation(Station station) {
        this.statoinId = station.getId();
        this.stationName = station.getName();
    }
}
