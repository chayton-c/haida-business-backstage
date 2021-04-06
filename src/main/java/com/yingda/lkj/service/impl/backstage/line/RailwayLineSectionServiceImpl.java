package com.yingda.lkj.service.impl.backstage.line;

import com.yingda.lkj.beans.entity.backstage.line.RailwayLineSection;
import com.yingda.lkj.dao.BaseDao;
import com.yingda.lkj.service.backstage.line.RailwayLineSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("railwayLineSectionService")
public class RailwayLineSectionServiceImpl implements RailwayLineSectionService {

    @Autowired
    private BaseDao<RailwayLineSection> railwayLineSectionBaseDao;

    @Override
    public List<RailwayLineSection> getByIds(List<String> ids) {
        return railwayLineSectionBaseDao.find(
                "from RailwayLineSection where id in :ids",
                Map.of("ids", ids)
        );
    }

    @Override
    public List<RailwayLineSection> getByStationId(String stationId) throws Exception {
        return railwayLineSectionBaseDao.find(
                "from RailwayLineSection where leftStationId = :stationId or rightStationId = :stationId",
                Map.of("stationId", stationId)
        );
    }

    @Override
    public RailwayLineSection getByStationIdAndRailwayLineId(String leftStationId, String rightStationId, String railwayLineId) {
        String hql = "from RailwayLineSection where leftStationId = :leftStationId and rightStationId = :rightStationId and railwayLineId = :railwayLineId";
        return railwayLineSectionBaseDao.get(hql, Map.of("leftStationId", leftStationId, "rightStationId", rightStationId, "railwayLineId", railwayLineId));
    }

    @Override
    public List<RailwayLineSection> filterByStartStationIdAndEndStationId(List<RailwayLineSection> railwayLineSections, String startStationId, String endStationId) {
        return railwayLineSections.stream().filter(railwayLineSection -> {
            if (railwayLineSection.getDownriver() == RailwayLineSection.SINGLE_LINE) {
                if (railwayLineSection.getLeftStationId().equals(startStationId) && railwayLineSection.getRightStationId().equals(endStationId))
                    return true;
                if (railwayLineSection.getRightStationId().equals(startStationId) && railwayLineSection.getLeftStationId().equals(endStationId))
                    return true;
                return false;
            }

            if (railwayLineSection.getDownriver() == RailwayLineSection.DOWNRIVER)
                return railwayLineSection.getLeftStationId().equals(startStationId) && railwayLineSection.getRightStationId().equals(endStationId);

            if (railwayLineSection.getDownriver() == RailwayLineSection.UPRIVER)
                return railwayLineSection.getRightStationId().equals(startStationId) && railwayLineSection.getLeftStationId().equals(endStationId);

            return false;
        }).collect(Collectors.toList());
    }
}
