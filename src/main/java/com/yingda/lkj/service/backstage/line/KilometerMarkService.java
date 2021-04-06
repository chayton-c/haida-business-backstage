package com.yingda.lkj.service.backstage.line;

import com.yingda.lkj.beans.entity.backstage.line.KilometerMark;
import com.yingda.lkj.beans.entity.backstage.line.RailwayLineSection;
import com.yingda.lkj.beans.entity.backstage.location.Location;
import com.yingda.lkj.beans.exception.CustomException;
import com.yingda.lkj.beans.system.Pair;

import java.util.List;

public interface KilometerMarkService {

    KilometerMark getByRailwaylineSectionIdAndStationId(String railwayLineSectionId, String stationId);

    List<KilometerMark> getByRailwayLineSectionId(String railwayLineSectionId);

    List<KilometerMark> getByRailwayLineSections(List<RailwayLineSection> railwayLineSections);

    /**
     * 计算公里标
     * @param startStationId 起始车站，在这里添加0公里标
     * @param nextStationId 下一车站，用于表明方向
     * @param railwayLineId 添加公里标的线路
     */
    void calculateKilometerMarksInLine(String startStationId, String nextStationId, String railwayLineId) throws CustomException;

    Pair<KilometerMark, KilometerMark> createStartEndKilometerMark(RailwayLineSection railwayLineSection, String startStationId, String endStationId, Location startLocation, Location endLocation);

}
