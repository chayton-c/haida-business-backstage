package com.yingda.lkj.beans.pojo.app;

import com.yingda.lkj.beans.entity.backstage.line.RailwayLine;
import com.yingda.lkj.beans.entity.backstage.line.Station;
import lombok.Data;

/**
 * @author hood  2020/4/13
 */
@Data
public class AppRailwayLine {
    private String railwayLineId;
    private String railwayLineName;

    public AppRailwayLine() {
    }

    public AppRailwayLine(RailwayLine railwayLine) {
        this.railwayLineId = railwayLine.getId();
        this.railwayLineName = railwayLine.getName();
    }
}
