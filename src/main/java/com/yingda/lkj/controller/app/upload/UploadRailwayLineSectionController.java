package com.yingda.lkj.controller.app.upload;

import com.yingda.lkj.beans.entity.backstage.line.KilometerMark;
import com.yingda.lkj.beans.entity.backstage.line.RailwayLineSection;
import com.yingda.lkj.beans.entity.backstage.line.StationRailwayLine;
import com.yingda.lkj.beans.entity.backstage.location.Location;
import com.yingda.lkj.beans.exception.CustomException;
import com.yingda.lkj.beans.pojo.app.AppLocation;
import com.yingda.lkj.beans.pojo.app.AppRailwayLineSection;
import com.yingda.lkj.beans.pojo.app.AppRailwayLineSectionInfo;
import com.yingda.lkj.beans.system.Json;
import com.yingda.lkj.beans.system.JsonMessage;
import com.yingda.lkj.controller.BaseController;
import com.yingda.lkj.dao.BaseDao;
import com.yingda.lkj.service.backstage.line.KilometerMarkService;
import com.yingda.lkj.service.backstage.line.RailwayLineSectionService;
import com.yingda.lkj.service.backstage.line.StationRailwayLineService;
import com.yingda.lkj.service.backstage.line.StationService;
import com.yingda.lkj.service.backstage.location.LocationService;
import com.yingda.lkj.service.base.BaseService;
import com.yingda.lkj.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app/upload/railwayLineSection")
public class UploadRailwayLineSectionController extends BaseController {

    @Autowired
    private BaseService<RailwayLineSection> railwayLineSectionBaseService;
    @Autowired
    private BaseService<Location> locationBaseService;
    @Autowired
    private BaseDao<Location> locationBaseDao;
    @Autowired
    private BaseService<KilometerMark> kilometerMarkBaseService;
    @Autowired
    private RailwayLineSectionService railwayLineSectionService;
    @Autowired
    private StationRailwayLineService stationRailwayLineService;
    @Autowired
    private StationService stationService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private KilometerMarkService kilometerMarkService;

    @RequestMapping("")
    public Json uploadRailwayLineSections() throws Exception {
        long time = current().getTime();
        String jsonStr = new String(req.getInputStream().readAllBytes());

        AppRailwayLineSectionInfo railwayLineSectionInfo = JsonUtils.parse(jsonStr, AppRailwayLineSectionInfo.class);

        List<AppRailwayLineSection> appRailwayLineSections = railwayLineSectionInfo.getRailwayLineSections();
        if (appRailwayLineSections.isEmpty())
            throw new CustomException(JsonMessage.DATA_NO_COMPLETE, "railwayLineSections为空");

        for (AppRailwayLineSection appRailwayLineSection : appRailwayLineSections) {
            // 删除旧数据
            RailwayLineSection originalRailwayLineSection = railwayLineSectionService.getByStationIdAndRailwayLineId(appRailwayLineSection.getLeftStationId(), appRailwayLineSection.getRightStationId(), appRailwayLineSection.getRailwayLineId());
            if (originalRailwayLineSection != null) {
                List<KilometerMark> kilometerMarks = kilometerMarkService.getByRailwayLineSectionId(originalRailwayLineSection.getId());
                for (KilometerMark kilometerMark : kilometerMarks)
                    kilometerMarkBaseService.delete(kilometerMark);
                locationBaseService.executeHql("delete from Location where dataId = :dataId", Map.of("dataId", originalRailwayLineSection.getId()));
                railwayLineSectionBaseService.delete(originalRailwayLineSection);
            }

            RailwayLineSection railwayLineSection = AppRailwayLineSection.transform2RailwayLineSection(appRailwayLineSection);
            // 查询上行下行
            StationRailwayLine leftStationRailwayLines = stationRailwayLineService.getByRailwayLineIdAndStationId(
                    railwayLineSection.getRailwayLineId(), railwayLineSection.getLeftStationId()
            );
            StationRailwayLine rightStationRailwayLines = stationRailwayLineService.getByRailwayLineIdAndStationId(
                    railwayLineSection.getRailwayLineId(), railwayLineSection.getRightStationId()
            );
            if (leftStationRailwayLines.getSeq() <= rightStationRailwayLines.getSeq())
                railwayLineSection.setDownriver(RailwayLineSection.DOWNRIVER);
            if (leftStationRailwayLines.getSeq() > rightStationRailwayLines.getSeq())
                railwayLineSection.setDownriver(RailwayLineSection.UPRIVER);


            railwayLineSectionBaseService.saveOrUpdate(railwayLineSection);
        }

        List<AppLocation> appLocations = railwayLineSectionInfo.getLocations();

        List<AppLocation> kilometerMarkLocations = appLocations.stream().filter(x -> x.getType() == Location.KILOMETER_MARKS).collect(Collectors.toList());
        List<AppLocation> railwayLineSectionLocations = appLocations.stream().filter(x -> x.getType() == Location.RAILWAY_LINE).collect(Collectors.toList());

        List<Location> locations = railwayLineSectionLocations.stream().map(AppLocation::createLocation).collect(Collectors.toList());
        locationService.bulkInsert(locations);

        return new Json(JsonMessage.SUCCESS);
    }

}
