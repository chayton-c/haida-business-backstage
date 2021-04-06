package com.yingda.lkj.service.impl.backstage.location;

import com.yingda.lkj.beans.entity.backstage.line.KilometerMark;
import com.yingda.lkj.beans.entity.backstage.line.RailwayLineSection;
import com.yingda.lkj.beans.entity.backstage.location.Location;
import com.yingda.lkj.beans.exception.CustomException;
import com.yingda.lkj.beans.pojo.location.ContainsLocation;
import com.yingda.lkj.beans.system.Pair;
import com.yingda.lkj.dao.BaseDao;
import com.yingda.lkj.service.backstage.line.KilometerMarkService;
import com.yingda.lkj.service.backstage.location.LocationService;
import com.yingda.lkj.service.base.BaseService;
import com.yingda.lkj.utils.StreamUtil;
import com.yingda.lkj.utils.location.LocationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hood  2020/12/15
 */
@Service("locationService")
public class LocationServiceImpl implements LocationService {

    @Autowired
    private BaseDao<Location> locationBaseDao;
    @Autowired
    private BaseService<Location> locationBaseService;
    @Autowired
    private KilometerMarkService kilometerMarkService;


    @Override
    public Location create(double latitude, double longitude, String dataId, byte type) {
        Location location = new Location();
        location.setId(UUID.randomUUID().toString());
        location.setDataId(dataId);
        location.setType(type);
        location.setLongitude(longitude);
        location.setLatitude(latitude);
        location.setAddTime(new Timestamp(System.currentTimeMillis()));
        location.setSeq(0);
        locationBaseDao.saveOrUpdate(location);

        return location;
    }

    @Override
    public Location getById(String id) {
        return locationBaseDao.get(Location.class, id);
    }

    @Override
    public List<Location> getByDataId(String dataId) {
        return locationBaseDao.find(
                "FROM Location WHERE dataId = :dataId ORDER BY seq",
                Map.of("dataId", dataId)
        );
    }

    @Override
    public void delete(String id) {
        locationBaseDao.executeHql(
                "delete from Location where id = :id",
                Map.of("id", id)
        );
    }

    @Override
    public List<? extends ContainsLocation> fillLocations(List<? extends ContainsLocation> containLocationDatas) {
        return fillLocations(containLocationDatas, null, null);
    }

    @Override
    public List<? extends ContainsLocation> fillLocations(List<? extends ContainsLocation> containLocationDatas, Timestamp startTime, Timestamp endTime) {
        if (containLocationDatas.isEmpty())
            return containLocationDatas;

        List<String> dataIds = StreamUtil.getList(containLocationDatas, ContainsLocation::getId);

        Map<String, Object> params = new HashMap<>(Map.of("dataIds", dataIds));
        String hql = "FROM Location WHERE dataId IN :dataIds\n";

        if (startTime != null) {
            hql += "AND addTime >= :startTime\n";
            params.put("startTime", startTime);
        }
        if (endTime != null) {
            hql += "AND addTime <= :endTime\n";
            params.put("endTime", endTime);
        }
        hql += "ORDER BY seq, addTime";
        List<Location> locations = locationBaseDao.find(hql, params);

        Map<String, List<Location>> locationMap = locations.stream().collect(Collectors.groupingBy(Location::getDataId));

        for (ContainsLocation containLocationData : containLocationDatas) {
            List<Location> locationList = locationMap.get(containLocationData.getId());
            if (locationList != null)
                containLocationData.setLocations(locationList);
        }

        return containLocationDatas;
    }

    @Override
    public Location getLastLocation(ContainsLocation containsLocation) throws Exception {
        Map<String, Object> params = new HashMap<>();
        Map<String, String> conditions = new HashMap<>();
        params.put("dataId", containsLocation.getId());
        conditions.put("dataId", "=");

        List<Location> locations = locationBaseService.getObjcetPagination(Location.class, params, conditions, 1, 1, "order by seq desc, addTime desc");
        return locations.stream().reduce(null, (x, y) -> y);
    }

    @Override
    public List<Location> getLastLocation(ContainsLocation containsLocation, int count) throws Exception {
        Map<String, Object> params = new HashMap<>();
        Map<String, String> conditions = new HashMap<>();
        params.put("dataId", containsLocation.getId());
        conditions.put("dataId", "=");

        return locationBaseService.getObjcetPagination(Location.class, params, conditions, 1, count, "order by seq desc, addTime desc");
    }

    @Override
    public void deleteByDataId(String dataId) {
        locationBaseDao.executeHql(
                "delete from Location where dataId = :dataId",
                Map.of("dataId", dataId)
        );
    }

    @Override
    public List<KilometerMark> fillRailwayLineSectionsWithLocationsByPlan(List<RailwayLineSection> railwayLineSections, double startKilometer, double endKilometer) throws CustomException {
        List<KilometerMark> resultKilometerMarks = new ArrayList<>();

        for (RailwayLineSection railwayLineSection : railwayLineSections) {
            String leftStationId = railwayLineSection.getLeftStationId();
            String rightStationId = railwayLineSection.getRightStationId();
            KilometerMark leftStationKilometerMark = kilometerMarkService.getByRailwaylineSectionIdAndStationId(railwayLineSection.getId(), leftStationId);
            KilometerMark rightStationKilometerMark = kilometerMarkService.getByRailwaylineSectionIdAndStationId(railwayLineSection.getId(), rightStationId);

            if (leftStationKilometerMark == null) {
                kilometerMarkService.calculateKilometerMarksInLine(leftStationId, rightStationId, railwayLineSection.getRailwayLineId());
                leftStationKilometerMark = kilometerMarkService.getByRailwaylineSectionIdAndStationId(railwayLineSection.getId(), leftStationId);
                rightStationKilometerMark = kilometerMarkService.getByRailwaylineSectionIdAndStationId(railwayLineSection.getId(), rightStationId);
            }

            KilometerMark startKilometerMark = leftStationKilometerMark.getKilometer() < rightStationKilometerMark.getKilometer() ?
                    leftStationKilometerMark : rightStationKilometerMark;
            KilometerMark endKilometerMark = leftStationKilometerMark.getKilometer() > rightStationKilometerMark.getKilometer() ?
                    leftStationKilometerMark : rightStationKilometerMark;

            if (startKilometer < startKilometerMark.getKilometer() || endKilometer > endKilometerMark.getKilometer())
                continue;

            List<Location> locations = railwayLineSection.getLocations();
            if (!leftStationKilometerMark.equals(startKilometerMark))
                Collections.reverse(locations);


            Pair<List<KilometerMark>, List<Location>> pair = cutLocationsByDistance(startKilometerMark, locations, startKilometer, endKilometer);

            if (pair != null) {
                List<KilometerMark> kilometerMarks = pair.firstValue;
                List<Location> railwayLineSectionLocations = pair.secondValue;
                resultKilometerMarks.addAll(kilometerMarks);
                railwayLineSection.setLocations(railwayLineSectionLocations);
            }
        }

        return resultKilometerMarks;
    }

    private Pair<List<KilometerMark>, List<Location>> cutLocationsByDistance(
            KilometerMark leftStationKilometerMark, List<Location> locations, double startKilometer, double endKilometer)
    {
        KilometerMark startKilometerMark = null;
        KilometerMark endKilometerMark;
        List<Location> resultLocation = new ArrayList<>();
        List<KilometerMark> kilometerMarks = new ArrayList<>();

        double kilometer = leftStationKilometerMark.getKilometer();

        @SuppressWarnings("unused")
        double buffer = 0; // 用于调试
        for (int i = 0, locationsSize = locations.size(); i < locationsSize - 1; i++) {
            Location leftLocation = locations.get(i);
            Location rightLocation = locations.get(i + 1);

            if (startKilometerMark == null && kilometer >= startKilometer)
                startKilometerMark = new KilometerMark(startKilometer, List.of(leftLocation));

            double distance = LocationUtil.getDistance(leftLocation, rightLocation);
            kilometer += distance;
            buffer += distance;

//            if (buffer > 10)
//                kilometerMarks.add(new KilometerMark(kilometer, List.of(rightLocation)));

            if (startKilometerMark != null)
                resultLocation.add(leftLocation);

            if (startKilometerMark != null && kilometer >= endKilometer) {
                endKilometerMark = new KilometerMark(endKilometer, List.of(rightLocation));
                kilometerMarks.add(endKilometerMark);
                return new Pair<>(kilometerMarks, resultLocation);
            }
        }

        return null;
    }

    @Override
    public void saveOrUpdate(Location location) {
        locationBaseDao.saveOrUpdate(location);
    }

    @Override
    public void bulkInsert(List<Location> locations) throws SQLException {
        if (locations.isEmpty()) return;

        StringBuilder sqlBuilder = new StringBuilder("""
                INSERT INTO location ( id, data_id, type, longitude, latitude, add_time, seq )
                VALUES 
                """);
        for (Location location : locations) {
            sqlBuilder.append(String.format(
                """
                ('%s', '%s', %d, %f, %f, '%s', %d),
                """, location.getId(), location.getDataId(), location.getType(),
                    location.getLongitude(), location.getLatitude(),
                    location.getAddTime(), location.getSeq()));
        }
        String substring = sqlBuilder.substring(0, sqlBuilder.length() - 2);
        locationBaseDao.executeSql(substring);
    }

    @Override
    public Location saveOrUpdate(String dataId, double longitude, double latitude, byte dataType) {
        Location location = new Location(dataId, longitude, latitude, 0, dataType);
        locationBaseDao.saveOrUpdate(location);
        return location;
    }
}
