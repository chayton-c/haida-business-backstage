package com.yingda.lkj.controller.backstage;

import com.yingda.lkj.beans.Constant;
import com.yingda.lkj.beans.entity.backstage.constructioncontrolplan.ConstructionControlPlan;
import com.yingda.lkj.beans.entity.backstage.constructioncontrolplan.ConstructionControlPlanPoint;
import com.yingda.lkj.beans.entity.backstage.constructioncontrolplan.ConstructionDailyPlan;
import com.yingda.lkj.beans.entity.backstage.constructioncontrolplan.ConstructionFormalPlan;
import com.yingda.lkj.beans.entity.backstage.equipment.Equipment;
import com.yingda.lkj.beans.entity.backstage.line.*;
import com.yingda.lkj.beans.entity.backstage.location.Location;
import com.yingda.lkj.beans.entity.backstage.opc.Opc;
import com.yingda.lkj.beans.entity.backstage.opc.OpcMark;
import com.yingda.lkj.beans.entity.backstage.opc.OpcMarkType;
import com.yingda.lkj.beans.entity.backstage.organization.Organization;
import com.yingda.lkj.beans.enums.constructioncontrolplan.WarnLevel;
import com.yingda.lkj.beans.exception.CustomException;
import com.yingda.lkj.beans.pojo.constructioncontrolplan.AppConstructionControlPlanPoint;
import com.yingda.lkj.beans.pojo.constructioncontrolplan.AppConstructionControlPlanPointInfos;
import com.yingda.lkj.beans.system.Json;
import com.yingda.lkj.beans.system.JsonMessage;
import com.yingda.lkj.controller.BaseController;
import com.yingda.lkj.service.backstage.constructioncontrolplan.*;
import com.yingda.lkj.service.backstage.equipment.EquipmentService;
import com.yingda.lkj.service.backstage.line.KilometerMarkService;
import com.yingda.lkj.service.backstage.line.RailwayLineSectionService;
import com.yingda.lkj.service.backstage.line.RailwayLineService;
import com.yingda.lkj.service.backstage.line.StationService;
import com.yingda.lkj.service.backstage.location.LocationService;
import com.yingda.lkj.service.backstage.opc.OpcMarkService;
import com.yingda.lkj.service.backstage.opc.OpcMarkTypeService;
import com.yingda.lkj.service.backstage.opc.OpcService;
import com.yingda.lkj.service.backstage.opc.OpcTypeService;
import com.yingda.lkj.service.backstage.organization.OrganizationClientService;
import com.yingda.lkj.service.base.BaseService;
import com.yingda.lkj.service.system.UserService;
import com.yingda.lkj.utils.JsonUtils;
import com.yingda.lkj.utils.StreamUtil;
import com.yingda.lkj.utils.StringUtils;
import com.yingda.lkj.utils.date.DateUtil;
import com.yingda.lkj.utils.pojo.PojoUtils;
import com.yingda.lkj.utils.position.CoordinateTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/backstage/constructionControlPlan")
@RestController
public class ConstructionControlPlanController extends BaseController {

    @Autowired
    private BaseService<ConstructionControlPlan> constructionControlPlanBaseService;
    @Autowired
    private ConstructionDailyPlanService constructionDailyPlanService;
    @Autowired
    private ConstructionControlPlanService constructionControlPlanService;
    @Autowired
    private UserService userService;
    @Autowired
    private BaseService<Location> locationBaseService;
    @Autowired
    private BaseService<ConstructionControlPlanPoint> constructionControlPlanPointBaseService;
    @Autowired
    private OrganizationClientService organizationClientService;
    @Autowired
    private StationService stationService;
    @Autowired
    private RailwayLineService railwayLineService;
    @Autowired
    private OpcService opcService;
    @Autowired
    private KilometerMarkService kilometerMarkService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private BaseService<StationRailwayLine> stationRailwayLineBaseService;
    @Autowired
    private OpcMarkService opcMarkService;
    @Autowired
    private OpcMarkTypeService opcMarkTypeService;
    @Autowired
    private OpcTypeService opcTypeService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private ConstructionControlPlanPointService constructionControlPlanPointService;
    @Autowired
    private RailwayLineSectionService railwayLineSectionService;
    @Autowired
    private ConstructionFormalPlanService constructionFormalPlanService;
    @Autowired
    private ConstructionCoordinatePlanUploadService constructionCoordinatePlanUploadService;

    private ConstructionControlPlan pageConstructionControlPlan;

    @RequestMapping("/getList")
    public Json getList() throws Exception {
        Map<String, Object> attributes = new HashMap<>();

        String nameOrCode = req.getParameter("nameOrCode");
        String signInStatus = req.getParameter("signInStatus");
        String approveStatus = req.getParameter("approveStatus");
        String codeOrConstructionProjectInfo = req.getParameter("codeOrConstructionProjectInfo");
        Timestamp startTime = pageConstructionControlPlan.getStartTime();
        Timestamp endTime = pageConstructionControlPlan.getEndTime();
        String processStatusStrArr = req.getParameter("processStatusStr");
        String investigationProgressStatusStr = req.getParameter("investigationProgressStatusStr");
        String planStatusStr = req.getParameter("planStatusStr");
        String warnStatusStr = req.getParameter("warnStatusStr");

        Map<String, Object> params = new HashMap<>();

        String sql = """
                SELECT
                    plan.id AS id,
                    plan.`name` AS `name`,
                    plan.`code` AS `code`,
                    plan.railway_line_status AS railwayLineStatus,
                    plan.process_status AS processStatus,
                    plan.plan_status AS planStatus,
                    plan.construction_project_info AS constructionProjectInfo,
                    plan.start_time AS startTime,
                    plan.end_time AS endTime,
                    plan.start_hour AS startHour,
                    plan.end_hour AS endHour,
                    plan.start_kilometer AS startKilometer,
                    plan.end_kilometer AS endKilometer,
                    plan.start_distance_from_railway AS startDistanceFromRailway,
                    plan.end_distance_from_railway AS endDistanceFromRailway,
                    plan.execute_organization_id AS executeOrganizationId,
                    plan.investigation_progress_status AS investigationProgressStatus,
                    plan.has_uploaded_cooperative_scheme AS hasUploadedCooperativeScheme,
                    plan.has_uploaded_safety_protocol AS hasUploadedSafetyProtocol,
                    plan.equipment_bind_count AS equipmentBindCount,
                    plan.equipment_release_count AS equipmentReleaseCount,
                    plan.warn_status AS warnStatus,
                    startStation.`name` AS startStationName,
                    endStation.`name` AS endStationName,
                    railwayLine.`name` AS railwayLineName,
                    signInUser.display_name AS signInUserDisplayName,
                    executor.display_name AS executorDisplayName,
                    workshop.name AS workshopName
                FROM
                    construction_control_plan plan
                    LEFT JOIN station AS startStation ON plan.start_station_id = startStation.id
                    LEFT JOIN station AS endStation ON plan.end_station_id = endStation.id
                    LEFT JOIN railway_line AS railwayLine ON plan.railway_line_id = railwayLine.id
                    LEFT JOIN `user` AS signInUser ON plan.sign_in_user_id = signInUser.id
                    LEFT JOIN `user` AS executor ON plan.execute_user_id = executor.id
                    LEFT JOIN organization AS workshop ON plan.workshop_id = workshop.id
                WHERE
                    1 = 1
                """;

        if (StringUtils.isNotEmpty(nameOrCode)) {
            sql += "AND (plan.name like :nameOrCode or plan.code like :nameOrCode)\n";
            params.put("nameOrCode", "%" + nameOrCode + "%");
        }
        if (StringUtils.isNotEmpty(signInStatus)) {
            sql += "AND plan.sign_in_status = :signInStatus\n";
            params.put("signInStatus", Byte.valueOf(signInStatus));
        }
        if (StringUtils.isNotEmpty(approveStatus)) {
            sql += "AND plan.approve_status = :approveStatus\n";
            params.put("approveStatus", Byte.valueOf(approveStatus));
        }
        if (StringUtils.isNotEmpty(codeOrConstructionProjectInfo)) {
            sql += "AND (plan.code like :codeOrConstructionProjectInfo or plan.construction_project_info like " +
                    ":codeOrConstructionProjectInfo)\n";
            params.put("codeOrConstructionProjectInfo", "%" + codeOrConstructionProjectInfo + "%");
        }
        if (startTime != null) {
            String startTimeStr = DateUtil.format(startTime, "yyyy-MM-dd");
            sql += "AND plan.end_time >= :startTime\n";
            params.put("startTime", startTimeStr);
        }
        if (endTime != null) {
            endTime = new Timestamp(endTime.getTime() + Constant.ONE_DAY_MILLIONS);
            String endTimeStr = DateUtil.format(endTime, "yyyy-MM-dd");
            sql += "AND plan.start_time <= :endTime\n";
            params.put("endTime", endTimeStr);
        }
        if (StringUtils.isNotEmpty(processStatusStrArr)) {
            sql += "AND plan.process_status = :processStatus\n";
            params.put("processStatus", Byte.parseByte(processStatusStrArr));
        }
        if (StringUtils.isNotEmpty(investigationProgressStatusStr)) {
            sql += "AND plan.investigation_progress_status = :investigationProgressStatus\n";
            params.put("investigationProgressStatus", Byte.valueOf(investigationProgressStatusStr));
        }
        if (StringUtils.isNotEmpty(planStatusStr)) {
            sql += "AND plan.plan_status = :planStatus\n";
            params.put("planStatus", Byte.valueOf(planStatusStr));
        }
        if (StringUtils.isNotEmpty(warnStatusStr)) {
            sql += "AND plan.warn_status = :warnStatus\n";
            params.put("warnStatus", Byte.valueOf(warnStatusStr));
        }
        sql += createDataAuthSql("plan", Organization.WORKSHOP, params);

        sql += "ORDER BY plan.add_time desc";
        List<ConstructionControlPlan> constructionControlPlans = constructionControlPlanBaseService.findSQL(
                sql, params, ConstructionControlPlan.class, page.getCurrentPage(), page.getPageSize()
        );
        for (ConstructionControlPlan constructionControlPlan : constructionControlPlans) {
            List<ConstructionFormalPlan> constructionFormalPlans =
                    constructionFormalPlanService.getByConstructionControlPlanId(constructionControlPlan.getId());
            if (constructionFormalPlans.size() > 0)
                constructionControlPlan.setConstructionFormalPlanCodes(constructionFormalPlans.get(constructionFormalPlans.size() - 1).getPlanCode());
            constructionControlPlan.setWarnLevel(WarnLevel.getByLevel(constructionControlPlan.getWarnStatus()));
        }

        attributes.put("workshops", organizationClientService.getWorkshops());
        attributes.put("constructionControlPlans", constructionControlPlans);
        setObjectNum(sql, params);
        attributes.put("page", page);
        return new Json(JsonMessage.SUCCESS, attributes);
    }

    @RequestMapping("/info")
    public Json info() {
        Map<String, Object> attributes = new HashMap<>();

        String id = pageConstructionControlPlan.getId();
        ConstructionControlPlan constructionControlPlan = StringUtils.isEmpty(id) ?
                new ConstructionControlPlan() : constructionControlPlanService.getById(id);

        constructionControlPlanService.fillExcentInfo(constructionControlPlan);

        if (StringUtils.isEmpty(id))
            constructionControlPlan.setCode(constructionControlPlanService.getNextCode());

        attributes.put("railwayLines", railwayLineService.getAll());
        attributes.put("stations", stationService.getAll());
        attributes.put("users", userService.getAll());
        attributes.put("constructionControlPlan", constructionControlPlan);

        if (StringUtils.isNotEmpty(id)) {
            List<Equipment> equipments = equipmentService.getByConstructionControlPlanId(id);
            attributes.put("equipments", equipments);
        }

        return new Json(JsonMessage.SUCCESS, attributes);
    }

    @RequestMapping("/saveOrUpdate")
    public Json saveOrUpdate() throws CustomException {
        checkParametersWithErrorMsg(
                "startTime", "施工日期及时间",
                "endTime", "施工日期及时间",
                "constructionPeriod", "周期",
                "startStationId", "施工地点",
                "endStationId", "施工地点",
                "startKilometer", "施工里程",
                "endKilometer", "施工里程",
                "signInStationId", "签入站"
        );
        String workshopId = getUser().getWorkshopId();
        if (StringUtils.isEmpty(workshopId))
            throw new CustomException(new Json(JsonMessage.NEED_TO_AUTH, "当前用户没有添加方案的权限，请联系管理员"));
        Timestamp startTime = pageConstructionControlPlan.getStartTime();
        Timestamp endTime = pageConstructionControlPlan.getEndTime();

        startTime = DateUtil.toTimestamp(DateUtil.format(startTime, "yyyy-MM-dd 00:00:00"), "yyyy-MM-dd HH:mm:ss");
        endTime = DateUtil.toTimestamp(DateUtil.format(endTime, "yyyy-MM-dd 23:59:59"), "yyyy-MM-dd HH:mm:ss");

        pageConstructionControlPlan.setStartTime(startTime);
        pageConstructionControlPlan.setEndTime(endTime);

        pageConstructionControlPlan.setExecuteOrganizationId(getUserLowestLevelOrganizationId());
        pageConstructionControlPlan.setWorkshopId(workshopId);
        constructionControlPlanService.saveOrUpdate(pageConstructionControlPlan);

        return new Json(JsonMessage.SUCCESS);
    }

    @RequestMapping("/saveOrUpdateThenExecute")
    public Json saveOrUpdateThenExecute() throws CustomException {
        checkParametersWithErrorMsg(
                "startTime", "施工日期及时间",
                "endTime", "施工日期及时间",
                "constructionPeriod", "周期",
                "startStationId", "施工地点",
                "endStationId", "施工地点",
                "startKilometer", "施工里程",
                "endKilometer", "施工里程"
        );
        String workshopId = getUser().getWorkshopId();
        if (StringUtils.isEmpty(workshopId))
            throw new CustomException(new Json(JsonMessage.NEED_TO_AUTH, "当前用户没有添加方案的权限，请联系管理员"));

        pageConstructionControlPlan.setExecuteOrganizationId(getUserLowestLevelOrganizationId());
        pageConstructionControlPlan.setWorkshopId(workshopId);
        ConstructionControlPlan constructionControlPlan = constructionControlPlanService.saveOrUpdate(pageConstructionControlPlan);
        constructionControlPlanService.submit(constructionControlPlan.getId());

        return new Json(JsonMessage.SUCCESS);
    }

    @RequestMapping("/importConstructionControlPlanPoint")
    public Json importConstructionControlPlanPoint(MultipartFile file) throws Exception {
        String jsonStr = new String(file.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        AppConstructionControlPlanPointInfos parse = JsonUtils.parse(jsonStr, AppConstructionControlPlanPointInfos.class);

        List<AppConstructionControlPlanPoint> appConstructionControlPlanPoints = parse.getConstructionControlPlanPoints();
        List<ConstructionControlPlanPoint> constructionControlPlanPoints = StreamUtil.getList(appConstructionControlPlanPoints,
                ConstructionControlPlanPoint::new);
        constructionControlPlanPointBaseService.bulkInsert(constructionControlPlanPoints);

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

    /**
     * 加载地图预览
     */
    @RequestMapping("/initLocationsByConstructionControlPlanId")
    public Json initLocationsByConstructionControlPlanId() throws Exception {
        long time = System.currentTimeMillis();
        Map<String, Object> attributes = new HashMap<>();
        String constructionControlPlanId = req.getParameter("constructionControlPlanId");
        String constructionDailyPlanId = req.getParameter("constructionDailyPlanId");

        ConstructionControlPlan constructionControlPlan = constructionControlPlanService.getById(constructionControlPlanId);
        ConstructionDailyPlan constructionDailyPlan = constructionDailyPlanService.getById(constructionDailyPlanId);
        if (constructionControlPlan == null && constructionDailyPlan != null)
            constructionControlPlan = constructionControlPlanService.getById(constructionDailyPlan.getConstructionControlPlanId());

        String stationId = req.getParameter("stationId");
        String signInStationId;
        if (StringUtils.isEmpty(stationId)) {
            assert constructionControlPlan != null;
            signInStationId = constructionControlPlan.getStartStationId();
        } else {
            signInStationId = stationId;
        }

        // 完整铁路线路
        List<RailwayLineSection> rawRailwayLineSections = railwayLineSectionService.getByStationId(signInStationId);
        locationService.fillLocations(rawRailwayLineSections);
        // 方案铁路线路
        List<RailwayLineSection> controlPlanRailwayLineSections = new ArrayList<>();
        if (constructionControlPlan != null) {
            controlPlanRailwayLineSections = railwayLineSectionService.filterByStartStationIdAndEndStationId(
                    rawRailwayLineSections, constructionControlPlan.getStartStationId(), constructionControlPlan.getEndStationId()
            );
            controlPlanRailwayLineSections = PojoUtils.copyPojoList(controlPlanRailwayLineSections, RailwayLineSection.class);
            for (RailwayLineSection railwayLineSection : controlPlanRailwayLineSections) // 手动深拷贝，目的是使controlPlanRailwayLineSections中的location与rawRailwayLineSections中的location指向不同地址
                railwayLineSection.setLocations(PojoUtils.copyPojoList(railwayLineSection.getLocations(), Location.class));
        }
        // 日计划铁路线路
        List<RailwayLineSection> dailyPlanRailwayLineSections = PojoUtils.copyPojoList(controlPlanRailwayLineSections, RailwayLineSection.class);
        for (RailwayLineSection railwayLineSection : dailyPlanRailwayLineSections)
            railwayLineSection.setLocations(PojoUtils.copyPojoList(railwayLineSection.getLocations(), Location.class));

        // 查询公里标
        List<KilometerMark> kilometerMarks = kilometerMarkService.getByRailwayLineSections(rawRailwayLineSections);
        locationService.fillLocations(kilometerMarks);

        // 根据方案的公里标截取线路
        if (constructionControlPlan != null) {
            List<KilometerMark> temporaryKilometerMarks = locationService.fillRailwayLineSectionsWithLocationsByPlan(
                    controlPlanRailwayLineSections, constructionControlPlan.getStartKilometer(), constructionControlPlan.getEndKilometer()
            );
            kilometerMarks.addAll(temporaryKilometerMarks);
        }
        // 根据日计划的公里标截取线路
        if (constructionDailyPlan != null) {
            List<KilometerMark> temporaryKilometerMarks = locationService.fillRailwayLineSectionsWithLocationsByPlan(
                    dailyPlanRailwayLineSections, constructionDailyPlan.getStartKilometer(), constructionDailyPlan.getEndKilometer()
            );
            kilometerMarks.addAll(temporaryKilometerMarks);
        }

        // 完整光电缆
        List<Opc> rawOpcs = opcService.getLineDataByStationId(signInStationId);

        if (rawOpcs == null)
            throw new CustomException(JsonMessage.SUCCESS, "该区间尚未上传数据");
        locationService.fillLocations(rawOpcs);
        // 方案或日计划截取后的光缆
        List<Opc> planOpcs = PojoUtils.copyPojoList(rawOpcs, Opc.class);

        // 查询光电缆标识
        List<OpcMark> opcMarks = opcMarkService.getByOpcs(rawOpcs);
        locationService.fillLocations(opcMarks);
        // 根据光缆标识和计划截取光缆
        Map<String, List<OpcMark>> opcMarkMap = opcMarks.stream().collect(Collectors.groupingBy(OpcMark::getOpcId));
        for (Opc opc : planOpcs) {
            List<OpcMark> opcMarkByOpc = opcMarkMap.get(opc.getId());
            if (constructionControlPlan != null && constructionDailyPlan == null)
                opcService.cutOpcLocationsByDistance(opc, opcMarkByOpc, constructionControlPlan.getStartKilometer(),
                        constructionControlPlan.getEndKilometer());
            if (constructionDailyPlan != null)
                opcService.cutOpcLocationsByDistance(opc, opcMarkByOpc, constructionDailyPlan.getStartKilometer(),
                        constructionDailyPlan.getEndKilometer());
        }

        // 查询施工点
        if (constructionControlPlan != null) {
            List<ConstructionControlPlanPoint> constructionControlPlanPoints =
                    constructionControlPlanPointService.getByPlanId(constructionControlPlan.getId());
            locationService.fillLocations(constructionControlPlanPoints);
            attributes.put("constructionControlPlanPoints", constructionControlPlanPoints);
        }

        attributes.put("constructionControlPlan", constructionControlPlan);
        if (constructionDailyPlan != null)
            attributes.put("constructionDailyPlan", constructionDailyPlan);
        attributes.put("opcMarkTypes", opcMarkTypeService.getAll());
        attributes.put("opcTypes", opcTypeService.getAll());
        attributes.put("opcMarks", opcMarks);
        attributes.put("rawOpcs", rawOpcs);
        attributes.put("planOpcs", planOpcs);
        attributes.put("rawRailwayLineSections", rawRailwayLineSections);
        attributes.put("dailyPlanRailwayLineSections", dailyPlanRailwayLineSections);
        attributes.put("controlPlanRailwayLineSections", controlPlanRailwayLineSections);
        attributes.put("kilometerMarks", kilometerMarks);

        System.out.println(System.currentTimeMillis() - time);
        return new Json(JsonMessage.SUCCESS, attributes);
    }

    @RequestMapping("/delete")
    public Json delete() {
        String id = req.getParameter("id");
        constructionControlPlanService.delete(id);

        return new Json(JsonMessage.SUCCESS);
    }

    /**
     * 提交方案
     */
    @RequestMapping("/submit")
    public Json submit() throws CustomException {
        constructionControlPlanService.submit(pageConstructionControlPlan.getId());
        return new Json(JsonMessage.SUCCESS);
    }

    /**
     * 安全会签
     */
    @RequestMapping("/safeCountersign")
    public Json approve() throws CustomException {
        constructionControlPlanService.safeCountersign(pageConstructionControlPlan.getId());
        return new Json(JsonMessage.SUCCESS);
    }

    /**
     * 驳回
     */
    @RequestMapping("/rejectPlan")
    public Json rejectPlan() throws Exception {
        String id = req.getParameter("id");
        ConstructionControlPlan constructionControlPlan = constructionControlPlanService.getById(id);

        // 删除配合方案/安全协议：planStatus-技术会签=配合方案；安全会签=安全协议
        constructionCoordinatePlanUploadService.deleteByConstructionControlPlan(constructionControlPlan);

        constructionControlPlan.setProcessStatus(ConstructionControlPlan.PENDING_SUBMIT);
        constructionControlPlan.setPlanStatus(ConstructionControlPlan.FIRST_DRAFT);
        constructionControlPlan.setInvestigationProgressStatus(ConstructionControlPlan.INVESTIGATION_NOT_OPENED);
        constructionControlPlan.setHasUploadedCooperativeScheme(Constant.FALSE);
        constructionControlPlan.setHasUploadedSafetyProtocol(Constant.FALSE);
        constructionControlPlanBaseService.saveOrUpdate(constructionControlPlan);


        return new Json(JsonMessage.SUCCESS);
    }

    /**
     * 确认关联
     */
    @RequestMapping("/confirmConnect")
    public Json confirmConnect() throws CustomException {
        String constructionControlPlanId = req.getParameter("constructionControlPlanId");
        String constructionFormalPlanId = req.getParameter("constructionFormalPlanId");

        constructionControlPlanService.relevanceFormalPlan(constructionControlPlanId, constructionFormalPlanId);

        return new Json(JsonMessage.SUCCESS);
    }

    /**
     * 重新调查
     *
     * @see com.yingda.lkj.beans.entity.backstage.constructioncontrolplan.ConstructionControlPlan PENDING_INVESTIGATE
     * 调查进度: investigationProgressStatus = 1
     */
    @RequestMapping("/reinvestigationPlan")
    public Json reinvestigationPlan() throws Exception {
        String id = req.getParameter("id");
        ConstructionControlPlan constructionControlPlan = constructionControlPlanService.getById(id);
//        constructionControlPlan.setProcessStatus(ConstructionControlPlan.SURVEYING);
        constructionControlPlan.setInvestigationProgressStatus(ConstructionControlPlan.PENDING_INVESTIGATE);
        constructionControlPlan.setUpdateTime(current());
        constructionControlPlanBaseService.saveOrUpdate(constructionControlPlan);
        return new Json(JsonMessage.SUCCESS);
    }

    /**
     * 补充配合方案
     *
     * @see com.yingda.lkj.beans.entity.backstage.constructioncontrolplan.ConstructionControlPlan PENDING_INVESTIGATE
     * 调查进度: investigationProgressStatus = 1
     */
    @RequestMapping("/supplementaryPlan")
    public Json supplementaryPlan() throws Exception {
        String id = req.getParameter("id");
        ConstructionControlPlan constructionControlPlan = constructionControlPlanService.getById(id);
        constructionControlPlan.setInvestigationProgressStatus(ConstructionControlPlan.INVESTIGATING);
        constructionControlPlan.setUpdateTime(current());
        constructionControlPlanBaseService.saveOrUpdate(constructionControlPlan);
        return new Json(JsonMessage.SUCCESS);
    }

    /**
     * 技术会签
     */
    @RequestMapping("/techCountersign")
    public Json techCountersign() throws Exception {
        String id = req.getParameter("id");
        ConstructionControlPlan constructionControlPlan = constructionControlPlanService.getById(id);

        constructionControlPlan.setProcessStatus(ConstructionControlPlan.PENDING_COUNTERSIGN);
        constructionControlPlan.setPlanStatus(ConstructionControlPlan.SAFE_COUNTERSIGN);
        constructionControlPlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));
//        constructionControlPlan.setProcessStatus(ConstructionControlPlan.PENDING_APPROVAL);
        constructionControlPlanBaseService.saveOrUpdate(constructionControlPlan);
        return new Json(JsonMessage.SUCCESS);
    }

    /**
     * 提交会签
     */
    @RequestMapping("/submitCountersign")
    public Json submitCountersign() throws Exception {
        String id = req.getParameter("id");
        ConstructionControlPlan constructionControlPlan = constructionControlPlanService.getById(id);
        constructionControlPlan.setProcessStatus(ConstructionControlPlan.PENDING_COUNTERSIGN);
        constructionControlPlan.setPlanStatus(ConstructionControlPlan.TECH_COUNTERSIGN);
        constructionControlPlan.setUpdateTime(current());
        constructionControlPlanBaseService.saveOrUpdate(constructionControlPlan);
        return new Json(JsonMessage.SUCCESS);
    }

    /**
     * 下发调查计划
     */
    @RequestMapping("/issueInvestigationTasks")
    public Json issueInvestigationTasks() {
        String id = req.getParameter("id");
        String investigationOrganizationId = req.getParameter("investigationOrganizationId");
        constructionControlPlanService.startInvestigate(id, investigationOrganizationId);
        return new Json(JsonMessage.SUCCESS);
    }

    /**
     * 关闭计划
     */
    @RequestMapping("/close")
    public Json close() throws Exception {
        String id = req.getParameter("id");
        constructionControlPlanService.close(id);
        return new Json(JsonMessage.SUCCESS);
    }

    /**
     * 重新关联
     */
    @RequestMapping("/reconnect")
    public Json reconnect() throws Exception {
        String id = req.getParameter("id");
        ConstructionControlPlan constructionControlPlan = constructionControlPlanService.getById(id);
        constructionControlPlan.setProcessStatus(ConstructionControlPlan.PENDING_RELEVANCE);
        constructionControlPlan.setUpdateTime(current());
        constructionControlPlanBaseService.saveOrUpdate(constructionControlPlan);
        return new Json(JsonMessage.SUCCESS);
    }

    @RequestMapping("/issuePlanJson")
    public void issuePlanJson() throws Exception {
        Map<String, Object> attributes = new HashMap<>();


        List<RailwayLine> railwayLines = railwayLineService.getAll();
        List<Station> stations = stationService.getAll();
        List<StationRailwayLine> stationRailwayLines = stationRailwayLineBaseService.find("from StationRailwayLine");
        List<OpcMarkType> opcMarkTypes = opcMarkTypeService.getAll();

        attributes.put("constructionControlPlans", constructionControlPlanService.getAll());
        attributes.put("users", userService.getAll());
        attributes.put("railwayLines", railwayLines);
        attributes.put("stations", stations);
        attributes.put("stationRailwayLines", stationRailwayLines);
        attributes.put("opcTypes", opcTypeService.getAll());
        attributes.put("opcMarkTypes", opcMarkTypes);

        MultipartFile file = new MockMultipartFile("线路模板", attributes.toString().getBytes());
        export(file);
//        return new Json(JsonMessage.SUCCESS, attributes);
    }

    /**
     * 统计方案数量
     */
    @RequestMapping("/countPlanTotals")
    public Json countPlanTotals() throws Exception {
        Map<String, Object> attributes = new HashMap<>();

        String sql = """
                SELECT
                	process_status AS processStatus,
                	plan_status AS planStatus,
                	investigation_progress_status AS investigationProgressStatus 
                FROM
                	construction_control_plan 
                WHERE 
                    1 = 1
                """;

        Map<String, Object> params = new HashMap<>();
        sql += createDataAuthSql("construction_control_plan", Organization.WORKSHOP, params);

        List<ConstructionControlPlan> constructionControlPlans = constructionControlPlanBaseService.findSQL(sql, params, ConstructionControlPlan.class);

        attributes.put("constructionControlPlans", constructionControlPlans);
        return new Json(JsonMessage.SUCCESS, attributes);
    }

    /**
     * 用于首页根据方案等级、当前年、上月统计方案数量
     * 仅返回数据包，前端处理结果
     */
    @RequestMapping("/countPlanByWarnStatusAndDate")
    public Json countPlanByWarnStatusAndDate() throws Exception {
        Map<String, Object> attributes = new HashMap<>();
        String sql = """
                SELECT
                	process_status AS processStatus,
                	warn_status AS warnStatus,
                	investigation_progress_status AS investigationProgressStatus,
                	start_time AS startTime,
                	approval_time AS approvalTime
                FROM
                	construction_control_plan 
                WHERE
                    1 = 1 
                """;

        Map<String, Object> params = new HashMap<>();
        sql += createDataAuthSql("construction_control_plan", Organization.WORKSHOP, params);

        List<ConstructionControlPlan> countControlPlans = constructionControlPlanBaseService
                .findSQL(sql, params, ConstructionControlPlan.class);

        attributes.put("countControlPlans", countControlPlans);
        return new Json(JsonMessage.SUCCESS, attributes);
    }

    @ModelAttribute
    public void setPageConstructionControlPlan(ConstructionControlPlan pageConstructionControlPlan) {
        this.pageConstructionControlPlan = pageConstructionControlPlan;
    }

}
