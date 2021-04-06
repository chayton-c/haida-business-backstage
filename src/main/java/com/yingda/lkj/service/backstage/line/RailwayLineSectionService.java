package com.yingda.lkj.service.backstage.line;

import com.yingda.lkj.beans.entity.backstage.line.RailwayLineSection;

import java.util.List;

public interface RailwayLineSectionService {

    List<RailwayLineSection> getByIds(List<String> ids);

    List<RailwayLineSection> getByStationId(String stationId) throws Exception;

    RailwayLineSection getByStationIdAndRailwayLineId(String leftStationId, String rightStationId, String railwayLineId);

    List<RailwayLineSection> filterByStartStationIdAndEndStationId(List<RailwayLineSection> railwayLineSections, String startStationId, String endStationId);
}
