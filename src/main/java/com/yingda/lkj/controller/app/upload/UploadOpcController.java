package com.yingda.lkj.controller.app.upload;

import com.yingda.lkj.beans.entity.backstage.location.Location;
import com.yingda.lkj.beans.entity.backstage.opc.Opc;
import com.yingda.lkj.beans.entity.backstage.opc.OpcMark;
import com.yingda.lkj.beans.system.Json;
import com.yingda.lkj.beans.system.JsonMessage;
import com.yingda.lkj.controller.BaseController;
import com.yingda.lkj.service.base.BaseService;
import com.yingda.lkj.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/upload/opc")
public class UploadOpcController extends BaseController {

    @Autowired
    private BaseService<Opc> opcBaseService;
    @Autowired
    private BaseService<OpcMark> opcMarkBaseService;
    @Autowired
    private BaseService<Location> locationBaseService;

    @RequestMapping("")
    public Json importOpc() {
        String opcsStr = req.getParameter("opcs");
        String opcMarksStr = req.getParameter("opcMarks");
        String locationsStr = req.getParameter("locations");

        List<Opc> opcs = JsonUtils.parseList(opcsStr, Opc.class);
        opcs.forEach(x -> x.setAddTime(current()));
        opcBaseService.bulkInsert(opcs);

        List<OpcMark> opcMarks = JsonUtils.parseList(opcMarksStr, OpcMark.class);
        opcMarks.forEach(x -> x.setAddTime(current()));
        opcMarkBaseService.bulkInsert(opcMarks);

        List<Location> locations = JsonUtils.parseList(locationsStr, Location.class);
        locations.forEach(x -> x.setAddTime(current()));
        locationBaseService.bulkInsert(locations);

        return new Json(JsonMessage.SUCCESS);
    }

}
