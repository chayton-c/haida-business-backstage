package com.yingda.lkj.controller.backstage;

import com.yingda.lkj.beans.entity.backstage.device.DeviceMaintenancePlanDevice;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementItemField;
import com.yingda.lkj.beans.exception.CustomException;
import com.yingda.lkj.beans.system.Json;
import com.yingda.lkj.beans.system.JsonMessage;
import com.yingda.lkj.controller.BaseController;
import com.yingda.lkj.service.backstage.device.DeviceMaintenancePlanDeviceService;
import com.yingda.lkj.service.base.BaseService;
import com.yingda.lkj.utils.StringUtils;
import com.yingda.lkj.utils.hql.HqlUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author hood  2020/12/7
 */
@RequestMapping("/backstage/deviceMaintenancePlanDevice")
@Controller
public class DeviceMaintenancePlanDeviceController extends BaseController {

    @Autowired
    private DeviceMaintenancePlanDeviceService deviceMaintenancePlanDeviceService;
    @Autowired
    private BaseService<DeviceMaintenancePlanDevice> deviceMaintenancePlanDeviceBaseService;

    private DeviceMaintenancePlanDevice pageDeviceMaintenancePlanDevice;

    @RequestMapping("/getByDeviceMaintenancePlanId")
    @ResponseBody
    public Json getByDeviceMaintenancePlanId() throws Exception {
        Map<String, Object> attributes = new HashMap<>();

        String deviceMaintenancePlanId = req.getParameter("deviceMaintenancePlanId");

        String sql = """
                SELECT
                	deviceMaintenancePlanDevice.id AS id,
                	deviceMaintenancePlanDevice.data_type AS dataType,
                	deviceMaintenancePlanDevice.device_id AS deviceId,
                	deviceMaintenancePlanDevice.measurement_template_id AS measurementTemplateId,
                	deviceMaintenancePlanDevice.seq AS seq,
                	IF (device.id IS NULL, station.`name`, device.`name`) AS deviceName,
                	IF (device.id IS NULL, station.`code`, device.`code`) AS deviceCode,
                	measurement_template.`name` AS measurementTemplateName
                FROM
                	device_maintenance_plan_device AS deviceMaintenancePlanDevice
                	LEFT JOIN device AS device ON deviceMaintenancePlanDevice.device_id = device.id
                	LEFT JOIN station AS station ON deviceMaintenancePlanDevice.device_id = station.id
                	INNER JOIN measurement_template ON deviceMaintenancePlanDevice.measurement_template_id = measurement_template.id
                WHERE
                    deviceMaintenancePlanDevice.device_maintenance_plan_id = :deviceMaintenancePlanId
                """;

        Map<String, Object> params = new HashMap<>();
        params.put("deviceMaintenancePlanId", deviceMaintenancePlanId);

        sql += "ORDER BY deviceMaintenancePlanDevice.seq";

        List<DeviceMaintenancePlanDevice> deviceMaintenancePlanDevices = deviceMaintenancePlanDeviceBaseService.findSQL(
                sql, params, DeviceMaintenancePlanDevice.class, page.getCurrentPage(), page.getPageSize()
        );

        List<BigInteger> count = bigIntegerBaseService.findSQL(HqlUtils.getCountSql(sql), params);
        page.setDataTotal(count);
        attributes.put("deviceMaintenancePlanDevices", deviceMaintenancePlanDevices);
        attributes.put("page", page);

        return new Json(JsonMessage.SUCCESS, attributes);
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Json saveOrUpdate() throws Exception {
        Map<String, Object> attributes = new HashMap<>();

        if (StringUtils.isEmpty(pageDeviceMaintenancePlanDevice.getMeasurementTemplateId()))
            throw new CustomException(JsonMessage.DATA_NO_COMPLETE, "请选择巡检标准");

        DeviceMaintenancePlanDevice deviceMaintenancePlanDevice = new DeviceMaintenancePlanDevice();
        BeanUtils.copyProperties(pageDeviceMaintenancePlanDevice, deviceMaintenancePlanDevice, "addTime");
        if (deviceMaintenancePlanDevice.getAddTime() == null)
            deviceMaintenancePlanDevice.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        deviceMaintenancePlanDeviceBaseService.saveOrUpdate(deviceMaintenancePlanDevice);
        attributes.put("deviceMaintenancePlanDevice", deviceMaintenancePlanDevice);

        return new Json(JsonMessage.SUCCESS, attributes);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Json delete() throws Exception {
        String id = req.getParameter("id");
        deviceMaintenancePlanDeviceBaseService.executeHql(
                "delete from DeviceMaintenancePlanDevice where id = :id",
                Map.of("id", id)
        );

        return new Json(JsonMessage.SUCCESS);
    }


    @ModelAttribute
    public void setPageDeviceMaintenancePlanDevice(DeviceMaintenancePlanDevice pageDeviceMaintenancePlanDevice) {
        this.pageDeviceMaintenancePlanDevice = pageDeviceMaintenancePlanDevice;
    }
}
