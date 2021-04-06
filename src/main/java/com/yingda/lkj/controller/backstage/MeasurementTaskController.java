package com.yingda.lkj.controller.backstage;

import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementTask;
import com.yingda.lkj.beans.exception.CustomException;
import com.yingda.lkj.beans.system.Json;
import com.yingda.lkj.beans.system.JsonMessage;
import com.yingda.lkj.controller.BaseController;
import com.yingda.lkj.service.backstage.measurement.MeasurementTaskService;
import com.yingda.lkj.service.base.BaseService;
import com.yingda.lkj.utils.StringUtils;
import com.yingda.lkj.utils.hql.HqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author hood  2020/12/9
 */
@RequestMapping("/backstage/measurementTask")
@Controller
public class MeasurementTaskController extends BaseController {
    @Autowired
    private MeasurementTaskService measurementTaskService;
    @Autowired
    private BaseService<MeasurementTask> measurementTaskBaseService;

    private MeasurementTask pageMeasurementTask;

    @RequestMapping("")
    @ResponseBody
    public Json list() throws Exception {
        Map<String, Object> attributes = new HashMap<>();

        Timestamp startTime = pageMeasurementTask.getStartTime();
        Timestamp endTime = pageMeasurementTask.getEndTime();
        String name = pageMeasurementTask.getName();
        String executorNames = req.getParameter("executorNames");

        String sql = """
                SELECT
                	measurementTask.id AS id,
                	measurementTask.`name` AS `name`,
                	measurementTask.execute_time AS executeTime,
                	measurementTask.repair_class AS repairClass,
                	measurementTask.finished_status AS finishedStatus,
                	measurementTask.remark AS remark,
                	measurementTask.start_time AS startTime,
                	measurementTask.end_time AS endTime,
                	taskExecutors.executorNames AS executorNames
                FROM
                	measurement_task AS measurementTask
                	# 关联任务执行人表
                	LEFT JOIN ( 
                	    SELECT 
                	        GROUP_CONCAT( executor_name ) AS executorNames, 
                	        measurement_task_id AS measurementTaskId 
                	    FROM measurement_task_execute_user 
                	    GROUP BY measurement_task_id 
                	) AS taskExecutors ON taskExecutors.measurementTaskId = measurementTask.id
                WHERE
                    1 = 1
                """;

        Map<String, Object> params = new HashMap<>();
        if (startTime != null) {
            sql += "AND measurementTask.start_time > :startTime\n";
            params.put("startTime", startTime);
        }
        if (endTime != null) {
            sql += "AND measurementTask.end_time < :endTime\n";
            params.put("endTime", endTime);
        }
        if (StringUtils.isNotEmpty(name)) {
            sql += "AND measurementTask.name LIKE :name\n";
            params.put("name", "%" + name + "%");
        }
        if (StringUtils.isNotEmpty(executorNames)) {
            sql += "AND taskExecutors.executorNames LIKE :executorNames";
            params.put("executorNames", "%" + executorNames + "%");
        }

        List<MeasurementTask> measurementTasks = measurementTaskBaseService.findSQL(
                sql, params, MeasurementTask.class, page.getCurrentPage(), page.getPageSize()
        );

        List<BigInteger> count = bigIntegerBaseService.findSQL(HqlUtils.getCountSql(sql), params);
        page.setDataTotal(count);

        attributes.put("measurementTasks", measurementTasks);
        attributes.put("page", page);

        return new Json(JsonMessage.SUCCESS, attributes);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Json delete() throws CustomException {
        String id = pageMeasurementTask.getId();
        measurementTaskService.delete(id);
        return new Json(JsonMessage.SUCCESS);
    }

    @ModelAttribute
    public void setPageMeasurementTask(MeasurementTask pageMeasurementTask) {
        this.pageMeasurementTask = pageMeasurementTask;
    }
}
