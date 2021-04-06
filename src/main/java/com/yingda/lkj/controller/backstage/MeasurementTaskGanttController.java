package com.yingda.lkj.controller.backstage;

import com.yingda.lkj.beans.entity.backstage.device.DeviceMaintenancePlan;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementTask;
import com.yingda.lkj.beans.pojo.measurement.MeasurementTaskGanttPojo;
import com.yingda.lkj.controller.BaseController;
import com.yingda.lkj.service.backstage.device.DeviceMaintenancePlanService;
import com.yingda.lkj.service.backstage.measurement.MeasurementTaskService;
import com.yingda.lkj.utils.StringUtils;
import com.yingda.lkj.utils.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hood  2020/5/8
 */
@Controller
@RequestMapping("/backstage/measurementTaskGantt")
public class MeasurementTaskGanttController extends BaseController {

    @Autowired
    private DeviceMaintenancePlanService deviceMaintenancePlanService;
    @Autowired
    private MeasurementTaskService measurementTaskService;
}
