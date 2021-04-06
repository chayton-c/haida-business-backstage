package com.yingda.lkj.controller.backstage;

import com.yingda.lkj.beans.entity.backstage.line.RailwayLineSection;
import com.yingda.lkj.beans.entity.backstage.location.Location;
import com.yingda.lkj.beans.entity.backstage.opc.Opc;
import com.yingda.lkj.beans.system.Json;
import com.yingda.lkj.beans.system.JsonMessage;
import com.yingda.lkj.controller.BaseController;
import com.yingda.lkj.service.backstage.line.RailwayLineSectionService;
import com.yingda.lkj.service.backstage.location.LocationService;
import com.yingda.lkj.service.backstage.opc.OpcService;
import com.yingda.lkj.service.base.BaseService;
import com.yingda.lkj.utils.location.LocationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/backstage/location")
@ResponseBody
@Controller
public class LocationController extends BaseController {

    @Autowired
    private BaseService<Location> locationBaseService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private OpcService opcService;
    @Autowired
    private RailwayLineSectionService railwayLineSectionService;

    @RequestMapping("/getByDataId")
    public Json getByDataId() throws Exception {
        Map<String, Object> attributes = new HashMap<>();
        String dataId = req.getParameter("dataId");

        Map<String, Object> params = new HashMap<>();
        Map<String, String> conditions = new HashMap<>();

        params.put("dataId", dataId);
        conditions.put("dataId", "=");

        List<Location> locations = locationBaseService.getObjcetPagination(
                Location.class, params, conditions, page.getCurrentPage(), page.getPageSize(), "order by seq"
        );
        Long count = locationBaseService.getObjectNum(Location.class, params, conditions);
        page.setDataTotal(count);

        attributes.put("locations", locations);
        attributes.put("page", page);

        return new Json(JsonMessage.SUCCESS, attributes);
    }

    @RequestMapping("delete")
    public Json delete() {
        String id = req.getParameter("id");

        locationService.delete(id);

        return new Json(JsonMessage.SUCCESS);
    }

    @RequestMapping("getDistanceFromOpcOrRailwayLine")
    public Json getDistanceFromOpcOrRailwayLine() throws Exception {
        String locationId = req.getParameter("locationId");
        String areOpc = req.getParameter("areOpc");
        String areRailwayLineSection = req.getParameter("areRailwayLineSection");
        String rawOpcIds = req.getParameter("rawOpcIds");
        String rawRailwayLineSectionIds = req.getParameter("rawRailwayLineSectionIds");

        Location location = locationService.getById(locationId);

        // 光电缆上的点到铁路线的距离
        if (Boolean.parseBoolean(areOpc)) {
            List<RailwayLineSection> railwayLineSections = railwayLineSectionService.getByIds(Arrays.asList(rawRailwayLineSectionIds.split(",")));
            locationService.fillLocations(railwayLineSections);
            double shortestDistance = LocationUtil.pointToMultiLineShortestDistance(location, railwayLineSections);
            return new Json(JsonMessage.SUCCESS, String.format("距离铁路%.5f米", shortestDistance));
        }

        // 铁路上的点到光电缆的距离
        if (Boolean.parseBoolean(areRailwayLineSection)) {
            List<Opc> opcs = opcService.getByIds(Arrays.asList(rawOpcIds.split(",")));
            locationService.fillLocations(opcs);
            double shortestDistance = LocationUtil.pointToMultiLineShortestDistance(location, opcs);
            return new Json(JsonMessage.SUCCESS, String.format("距离光电缆%.5f米", shortestDistance));
        }

        return new Json(JsonMessage.SUCCESS);
    }

}
