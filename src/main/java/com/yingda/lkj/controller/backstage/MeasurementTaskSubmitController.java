package com.yingda.lkj.controller.backstage;

import com.yingda.lkj.beans.entity.backstage.device.Device;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementTask;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementTaskDetail;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementTaskExecuteUser;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementTemplate;
import com.yingda.lkj.beans.entity.backstage.organization.Organization;
import com.yingda.lkj.beans.entity.system.User;
import com.yingda.lkj.beans.system.Json;
import com.yingda.lkj.beans.system.JsonMessage;
import com.yingda.lkj.controller.BaseController;
import com.yingda.lkj.service.backstage.measurement.MeasurementTaskService;
import com.yingda.lkj.service.backstage.organization.OrganizationClientService;
import com.yingda.lkj.service.base.BaseService;
import com.yingda.lkj.utils.RequestUtil;
import com.yingda.lkj.utils.StreamUtil;
import com.yingda.lkj.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @author hood  2020/3/11
 */
@Controller
@RequestMapping("/backstage/measurementTaskSubmit")
public class MeasurementTaskSubmitController extends BaseController {

    @Autowired
    private BaseService<MeasurementTask> measurementTaskBaseService;
    @Autowired
    private BaseService<MeasurementTaskDetail> measurementTaskDetailBaseService;
    @Autowired
    private BaseService<User> userBaseService;
    @Autowired
    private BaseService<Device> deviceBaseService;
    @Autowired
    private MeasurementTaskService measurementTaskService;
    @Autowired
    private BaseService<MeasurementTemplate> measurementTemplateBaseService;
    @Autowired
    private BaseService<MeasurementTaskExecuteUser> measurementTaskExecuteUserBaseService;
    @Autowired
    private OrganizationClientService organizationClientService;

    private MeasurementTask pageMeasurementTask;

    @RequestMapping("")
    public ModelAndView getList() throws Exception {
        return null;
    }

    @RequestMapping("/infoPage")
    public ModelAndView infoPage(String measurementTaskId) throws Exception {
        User user = RequestUtil.getUser(req);

        String repairClass = req.getParameter("repairClass");
        String sectionId = user.getSectionId();

        List<User> users = userBaseService.find("from User where sectionId = :sectionId", Map.of("sectionId", sectionId));
        List<Organization> workAreas = organizationClientService.getWorkAreasBySectionId(sectionId);

        MeasurementTask measurementTask = new MeasurementTask();
        if (StringUtils.isNotEmpty(measurementTaskId)) {
            measurementTask = measurementTaskBaseService.get(MeasurementTask.class, measurementTaskId);
            List<MeasurementTaskExecuteUser> measurementTaskExecuteUsers = measurementTaskExecuteUserBaseService.find(
                    "from MeasurementTaskExecuteUser where measurementTaskId = :measurementTaskId",
                    Map.of("measurementTaskId", measurementTask.getId())
            );
            List<String> executeUserIds = StreamUtil.getList(measurementTaskExecuteUsers, MeasurementTaskExecuteUser::getExecuteUserId);
            users.forEach(x -> x.setPassword(null));
            return new ModelAndView("/backstage/measurementtasksubmit/measurement-task-info",
                    Map.of("users", users, "workAreas", workAreas, "measurementTask", measurementTask,
                            "executeUserIds", executeUserIds, "repairClass", repairClass));
        } else {
//            measurementTask.setNotCurrentExecuted(MeasurementTask.CURRENT_EXECUTED);
        }

        users.forEach(x -> x.setPassword(null));

        return new ModelAndView("/backstage/measurementtasksubmit/measurement-task-info",
                Map.of("users", users, "workAreas", workAreas, "measurementTask", measurementTask, "repairClass", repairClass));
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Json saveOrUpdate() {

        return new Json(JsonMessage.SUCCESS);
    }

    @RequestMapping("/close")
    @ResponseBody
    public Json close(String measurementTaskId) {
        measurementTaskService.closeTask(measurementTaskId);
        return new Json(JsonMessage.SUCCESS);
    }

    @RequestMapping("/submit")
    @ResponseBody
    public Json submit(String measurementTaskId) {
        measurementTaskService.submitTask(measurementTaskId);
        return new Json(JsonMessage.SUCCESS);
    }

    @ModelAttribute
    public void setPageMeasurementTask(MeasurementTask pageMeasurementTask) {
        this.pageMeasurementTask = pageMeasurementTask;
    }

}
