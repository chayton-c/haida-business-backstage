package com.yingda.lkj.controller.backstage;

import com.yingda.lkj.beans.entity.backstage.location.Location;
import com.yingda.lkj.beans.entity.backstage.opc.Opc;
import com.yingda.lkj.beans.entity.backstage.opc.OpcMark;
import com.yingda.lkj.beans.exception.CustomException;
import com.yingda.lkj.beans.pojo.opc.AppOpcJson;
import com.yingda.lkj.beans.system.Json;
import com.yingda.lkj.beans.system.JsonMessage;
import com.yingda.lkj.controller.BaseController;
import com.yingda.lkj.service.backstage.location.LocationService;
import com.yingda.lkj.service.backstage.opc.OpcMarkService;
import com.yingda.lkj.service.backstage.opc.OpcMarkTypeService;
import com.yingda.lkj.service.backstage.opc.OpcService;
import com.yingda.lkj.service.backstage.opc.OpcTypeService;
import com.yingda.lkj.service.base.BaseService;
import com.yingda.lkj.utils.JsonUtils;
import com.yingda.lkj.utils.StringUtils;
import com.yingda.lkj.utils.position.CoordinateTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hood  2020/12/15
 */
@RequestMapping("/backstage/opc")
@ResponseBody
@Controller
public class OpcController extends BaseController {

    @Autowired
    private BaseService<Opc> opcBaseService;
    @Autowired
    private BaseService<OpcMark> opcMarkBaseService;
    @Autowired
    private BaseService<Location> locationBaseService;
    @Autowired
    private OpcService opcService;
    @Autowired
    private OpcMarkService opcMarkService;
    @Autowired
    private OpcTypeService opcTypeService;
    @Autowired
    private OpcMarkTypeService opcMarkTypeService;
    @Autowired
    private LocationService locationService;

    @RequestMapping("/initLocationsByStations")
    public Json initLocationsByStations() throws CustomException {
        Map<String, Object> attributes = new HashMap<>();
        String leftStationId = req.getParameter("leftStationId");
        String rightStationId = req.getParameter("rightStationId");

        String opcId = req.getParameter("opcId");
        String opcMarkTypeId = req.getParameter("opcMarkTypeId");
        String opcMarkName = req.getParameter("opcMarkName");

        List<Opc> opcs = StringUtils.isNotEmpty(leftStationId, rightStationId) ?
                opcService.getByStations(leftStationId, rightStationId) : List.of(opcService.getById(opcId));
        List<Opc> selectOpcs = opcs;
        if (opcs == null)
            throw new CustomException(JsonMessage.SUCCESS, "该区间尚未上传数据");
        if (StringUtils.isNotEmpty(opcId))
            opcs = opcs.stream().filter(x -> x.getId().equals(opcId)).collect(Collectors.toList());

        locationService.fillLocations(opcs);

        List<OpcMark> opcMarks = new ArrayList<>();
        for (Opc opc : opcs) {
            List<OpcMark> opcMarkList = opcMarkService.getByOpc(opc.getId());
            if (StringUtils.isNotEmpty(opcMarkTypeId))
                opcMarkList = opcMarkList.stream().filter(x -> x.getOpcMarkTypeId().equals(opcMarkTypeId)).collect(Collectors.toList());
            if (StringUtils.isNotEmpty(opcMarkName))
                opcMarkList = opcMarkList.stream().filter(x -> x.getName().contains(opcMarkName)).collect(Collectors.toList());
            opcMarks.addAll(opcMarkList);
        }
        locationService.fillLocations(opcMarks);

        attributes.put("opcMarkTypes", opcMarkTypeService.getAll());
        attributes.put("opcTypes", opcTypeService.getAll());
        attributes.put("opcMarks", opcMarks);
        attributes.put("opcs", opcs);
        attributes.put("selectOpcs", selectOpcs);

        return new Json(JsonMessage.SUCCESS, attributes);
    }

    @RequestMapping("/importOpc")
    public Json importOpc(MultipartFile file) throws Exception {
        String jsonStr = new String(file.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        AppOpcJson parse = JsonUtils.parse(jsonStr, AppOpcJson.class);

        List<Opc> opcs = parse.getOpcs();
        opcs.forEach(x -> x.setAddTime(current()));
        opcBaseService.bulkInsert(opcs);
        List<OpcMark> opcMarks = parse.getOpc_marks();
        opcMarks.forEach(x -> x.setAddTime(current()));
        opcMarkBaseService.bulkInsert(opcMarks);

        List<Location> location = parse.getLocations();
        location.forEach(x -> {
            double[] doubles = CoordinateTransform.gpsToBaidu(x.getLongitude(), x.getLatitude());
            x.setLongitude(doubles[0]);
            x.setLatitude(doubles[1]);
            x.setAddTime(current());
        });
        locationBaseService.bulkInsert(location);

        return new Json(JsonMessage.SUCCESS);
    }
}
