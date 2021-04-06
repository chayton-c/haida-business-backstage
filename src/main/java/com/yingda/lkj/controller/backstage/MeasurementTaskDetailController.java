package com.yingda.lkj.controller.backstage;

import com.yingda.lkj.beans.Constant;
import com.yingda.lkj.beans.entity.backstage.device.Device;
import com.yingda.lkj.beans.entity.backstage.device.DeviceSubType;
import com.yingda.lkj.beans.entity.backstage.device.DeviceType;
import com.yingda.lkj.beans.entity.backstage.line.Station;
import com.yingda.lkj.beans.entity.backstage.measurement.*;
import com.yingda.lkj.beans.entity.system.User;
import com.yingda.lkj.beans.exception.CustomException;
import com.yingda.lkj.beans.pojo.measurement.MeasurementTemplatePojo;
import com.yingda.lkj.beans.system.Json;
import com.yingda.lkj.beans.system.JsonMessage;
import com.yingda.lkj.controller.BaseController;
import com.yingda.lkj.service.backstage.device.DeviceSubTypeService;
import com.yingda.lkj.service.backstage.device.DeviceTypeService;
import com.yingda.lkj.service.backstage.line.StationService;
import com.yingda.lkj.service.backstage.measurement.MeasurementItemFieldValueService;
import com.yingda.lkj.service.backstage.measurement.MeasurementTaskDetailService;
import com.yingda.lkj.service.backstage.measurement.MeasurementTemplateService;
import com.yingda.lkj.service.base.BaseService;
import com.yingda.lkj.utils.RequestUtil;
import com.yingda.lkj.utils.StreamUtil;
import com.yingda.lkj.utils.StringUtils;
import com.yingda.lkj.utils.hql.HqlUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 任务详情(子任务)
 *
 * @author hood  2020/3/17
 */
@Controller
@RequestMapping("/backstage/measurementTaskDetail")
public class MeasurementTaskDetailController extends BaseController {

    @Autowired
    private BaseService<MeasurementTaskDetail> measurementTaskDetailBaseService;

    @RequestMapping("/getByMeasurementTaskId")
    @ResponseBody
    public Json getByMeasurementTaskId() throws Exception {
        Map<String, Object> attributes = new HashMap<>();

        String measurementTaskId = req.getParameter("measurementTaskId");

        String sql = """
                SELECT
                	measurementTaskDetail.id AS id,
                	measurementTemplate.`name` AS measurementTemplateName,
                	measurementTaskDetail.uploador_names AS uploadorNames,
                	measurementTaskDetail.data_type AS dataType,
                	device.`name` AS deviceName,
                	device.`code` AS deviceCode,
                	measurementTaskDetail.abnormal AS abnormal,
                	measurementTaskDetail.finished_status AS finishedStatus,
                	measurementTaskDetail.error_status AS errorStatus,
                	measurementTaskDetail.execute_time AS executeTime,
                	measurementTaskDetail.seq AS seq\s
                FROM
                	measurement_task_detail AS measurementTaskDetail
                	INNER JOIN measurement_template AS measurementTemplate ON measurementTaskDetail.measurement_template_id = measurementTemplate.id
                	INNER JOIN device ON measurementTaskDetail.device_id = device.id\s
                WHERE
                	measurementTaskDetail.measurement_task_id = :measurementTaskId\s
                ORDER BY
                	seq
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("measurementTaskId", measurementTaskId);

        List<MeasurementTaskDetail> measurementTaskDetails = measurementTaskDetailBaseService.findSQL(
                sql, params, MeasurementTaskDetail.class, page.getCurrentPage(), page.getPageSize()
        );

        List<BigInteger> count = bigIntegerBaseService.findSQL(HqlUtils.getCountSql(sql), params);
        page.setDataTotal(count);

        attributes.put("page", page);
        attributes.put("measurementTaskDetails", measurementTaskDetails);
        System.out.println(measurementTaskDetails);

        return new Json(JsonMessage.SUCCESS, attributes);
    }



}
