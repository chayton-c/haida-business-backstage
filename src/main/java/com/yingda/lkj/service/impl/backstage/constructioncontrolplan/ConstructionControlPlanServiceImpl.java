package com.yingda.lkj.service.impl.backstage.constructioncontrolplan;

import com.yingda.lkj.beans.Constant;
import com.yingda.lkj.beans.entity.backstage.constructioncontrolplan.ConstructionControlPlan;
import com.yingda.lkj.beans.entity.backstage.constructioncontrolplan.ConstructionControlPlanEquipment;
import com.yingda.lkj.beans.enums.constructioncontrolplan.WarnLevel;
import com.yingda.lkj.beans.exception.CustomException;
import com.yingda.lkj.beans.system.JsonMessage;
import com.yingda.lkj.dao.BaseDao;
import com.yingda.lkj.service.backstage.constructioncontrolplan.ConstructionControlPlanEquipmentService;
import com.yingda.lkj.service.backstage.constructioncontrolplan.ConstructionControlPlanService;
import com.yingda.lkj.service.backstage.constructioncontrolplan.ConstructionDailyPlanService;
import com.yingda.lkj.service.backstage.constructioncontrolplan.ConstructionFormalPlanService;
import com.yingda.lkj.service.backstage.line.RailwayLineService;
import com.yingda.lkj.service.backstage.line.StationService;
import com.yingda.lkj.service.backstage.organization.OrganizationClientService;
import com.yingda.lkj.utils.CacheUtil;
import com.yingda.lkj.utils.StreamUtil;
import com.yingda.lkj.utils.StringUtils;
import com.yingda.lkj.utils.date.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Service("constructionControlPlanService")
public class ConstructionControlPlanServiceImpl implements ConstructionControlPlanService {

    @Autowired
    private BaseDao<ConstructionControlPlan> constructionControlPlanBaseDao;
    @Autowired
    private ConstructionFormalPlanService constructionFormalPlanService;
    @Autowired
    private ConstructionDailyPlanService constructionDailyPlanService;
    @Autowired
    private StationService stationService;
    @Autowired
    private RailwayLineService railwayLineService;
    @Autowired
    private OrganizationClientService organizationClientService;
    @Autowired
    private ConstructionControlPlanEquipmentService constructionControlPlanEquipmentService;


    @Override
    public ConstructionControlPlan getById(String id) {
        if (StringUtils.isEmpty(id)) return null;

        return constructionControlPlanBaseDao.get(
                "from ConstructionControlPlan where id = :id",
                Map.of("id", id)
        );
    }

    @Override
    public ConstructionControlPlan saveOrUpdate(ConstructionControlPlan pageConstructionControlPlan) {
        Timestamp current = new Timestamp(System.currentTimeMillis());

        String id = pageConstructionControlPlan.getId();
        if (StringUtils.isEmpty(id))
            pageConstructionControlPlan.setWarnStatus((byte) WarnLevel.LEVEL5.level);

        ConstructionControlPlan constructionControlPlan = StringUtils.isNotEmpty(id) ? getById(id) : new ConstructionControlPlan(UUID.randomUUID().toString());
        BeanUtils.copyProperties(pageConstructionControlPlan, constructionControlPlan, "id", "addTime", "updateTime", "signInStatus", "approveStatus");
        if (constructionControlPlan.getAddTime() == null)
            constructionControlPlan.setAddTime(new Timestamp(System.currentTimeMillis()));
        constructionControlPlan.setUpdateTime(current);

        constructionControlPlanBaseDao.saveOrUpdate(constructionControlPlan);

        return constructionControlPlan;
    }

    @Deprecated
    @Override
    public List<ConstructionControlPlan> getAll() {
        return constructionControlPlanBaseDao.find(
                "from ConstructionControlPlan"
        );
    }

    @Override
    public void submit(String constructionControlPlanId) throws CustomException {
        ConstructionControlPlan constructionControlPlan = getById(constructionControlPlanId);

//        constructionControlPlan.setProcessStatus(ConstructionControlPlan.SUBMITTED);
        constructionControlPlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        constructionControlPlanBaseDao.saveOrUpdate(constructionControlPlan);
    }

    @Override
    public void safeCountersign(String constructionControlPlanId) throws CustomException {
        ConstructionControlPlan constructionControlPlan = getById(constructionControlPlanId);
        if (constructionControlPlan.getHasUploadedSafetyProtocol() == Constant.FALSE)
            throw new CustomException(JsonMessage.DATA_NO_COMPLETE, "未上传安全协议");

        constructionControlPlan.setApproveStatus(ConstructionControlPlan.APPROVED);
        constructionControlPlan.setProcessStatus(ConstructionControlPlan.PENDING_RELEVANCE);
        constructionControlPlan.setPlanStatus(ConstructionControlPlan.COUNTERSIGNED);
        constructionControlPlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        constructionControlPlanBaseDao.saveOrUpdate(constructionControlPlan);
    }

    @Override
    public String getNextCode() {
        return CodeService.getACodeOfToday();
    }

    @Override
    public void delete(String constructionControlPlanId) {
        constructionControlPlanBaseDao.executeHql(
                "delete from ConstructionControlPlan where id = :constructionControlPlanId",
                Map.of("constructionControlPlanId", constructionControlPlanId)
        );
    }

    @Override
    public List<ConstructionControlPlan> findByStationId(String stationId) {
        return constructionControlPlanBaseDao.find(
                "from ConstructionControlPlan where startStationId = :stationId or endStationId = :stationId or signInStationId = :stationId",
                Map.of("stationId", stationId)
        );
    }

    @Override
    public List<ConstructionControlPlan> issuePendingInvestigatePlans2App() {
        // 待调查的计划
        List<ConstructionControlPlan> pendingInvestigatePlans = constructionControlPlanBaseDao.find(
                """
                FROM ConstructionControlPlan 
                WHERE 
                    (investigationProgressStatus = :pendingInvestigate or investigationProgressStatus = :investigating)
                AND processStatus != :CLOSED
                """,
                Map.of(
                        "pendingInvestigate", ConstructionControlPlan.PENDING_INVESTIGATE,
                        "investigating", ConstructionControlPlan.INVESTIGATING,
                        "CLOSED", ConstructionControlPlan.CLOSED
                )
        );
        pendingInvestigatePlans.forEach(x -> x.setInvestigationProgressStatus(ConstructionControlPlan.INVESTIGATING));
        constructionControlPlanBaseDao.bulkInsert(pendingInvestigatePlans);

        return pendingInvestigatePlans;
    }

    @Override
    public void startInvestigate(String id, String investigationOrganizationId) {
        // 待调查的计划
        ConstructionControlPlan constructionControlPlan = constructionControlPlanBaseDao.get(ConstructionControlPlan.class, id);
        constructionControlPlan.setInvestigationProgressStatus(ConstructionControlPlan.INVESTIGATING);
//        constructionControlPlan.setProcessStatus(ConstructionControlPlan.SURVEYING);
        // 现场调查部门
        constructionControlPlan.setInvestigationOrganizationId(investigationOrganizationId);
        constructionControlPlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        constructionControlPlanBaseDao.saveOrUpdate(constructionControlPlan);
    }

    @Override
    public void finishedInvestigate(List<String> ids) {
        List<ConstructionControlPlan> constructionControlPlans = getByIds(ids);
        for (ConstructionControlPlan constructionControlPlan : constructionControlPlans) {
            constructionControlPlan.setInvestigationProgressStatus(ConstructionControlPlan.INVESTIGATED);
            constructionControlPlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        }

        constructionControlPlanBaseDao.bulkInsert(constructionControlPlans);
    }

    @Override
    public void close(String id) {
        ConstructionControlPlan constructionControlPlan = getById(id);
        constructionControlPlan.setPlanStatus(ConstructionControlPlan.MANUALLY_CLOSED);
        constructionControlPlan.setProcessStatus(ConstructionControlPlan.CLOSED);
        constructionControlPlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        constructionControlPlanBaseDao.saveOrUpdate(constructionControlPlan);

//        constructionDailyPlanService.closeByConstructionControlPlanId(id);
    }

    @Override
    public void relevanceFormalPlan(String constructionControlPlanId, String constructionFormalPlanId) throws CustomException {
        ConstructionControlPlan constructionControlPlan = getById(constructionControlPlanId);
        if (constructionControlPlan.getInvestigationProgressStatus() == ConstructionControlPlan.INVESTIGATING)
            throw new CustomException(JsonMessage.PARAM_INVALID, "该方案调查中，暂不可关联");

        constructionControlPlan.setProcessStatus(ConstructionControlPlan.RELEVANCEED);
        constructionControlPlan.setPlanStatus(ConstructionControlPlan.PENDING_START);
        constructionControlPlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        constructionControlPlanBaseDao.saveOrUpdate(constructionControlPlan);
        constructionFormalPlanService.relevancePlan(constructionControlPlanId, constructionFormalPlanId);
    }

    @Override
    public void fillExcentInfo(ConstructionControlPlan constructionControlPlan) {
        // 线路和车站名
        if (StringUtils.isNotEmpty(constructionControlPlan.getRailwayLineId()))
            constructionControlPlan.setRailwayLineName(railwayLineService.getById(constructionControlPlan.getRailwayLineId()).getName());
        if (StringUtils.isNotEmpty(constructionControlPlan.getStartStationId()))
            constructionControlPlan.setStartStationName(stationService.getById(constructionControlPlan.getStartStationId()).getName());
        if (StringUtils.isNotEmpty(constructionControlPlan.getEndStationId()))
            constructionControlPlan.setEndStationName(stationService.getById(constructionControlPlan.getEndStationId()).getName());
        // 调查部门
        if (StringUtils.isNotEmpty(constructionControlPlan.getInvestigationOrganizationId()))
            constructionControlPlan.setInvestigationOrganizationName(organizationClientService.getById(constructionControlPlan.getInvestigationOrganizationId()).getName());
    }

    @Override
    public void checkClose() {
        Timestamp current = new Timestamp(System.currentTimeMillis());
        List<ConstructionControlPlan> constructionControlPlans = getNotClosed();
        for (ConstructionControlPlan constructionControlPlan : constructionControlPlans) {
            if (constructionControlPlan.getEndTime().before(current))
                close(constructionControlPlan.getId());
        }
    }

    @Override
    public List<ConstructionControlPlan> getRelevancedConstructionControlPlanByEquipmentId(String equipmentId) {
        List<ConstructionControlPlanEquipment> constructionControlPlanEquipments = constructionControlPlanEquipmentService.getByEquipmentId(equipmentId);
        List<String> constructionControlPlanIds = StreamUtil.getList(constructionControlPlanEquipments,
                ConstructionControlPlanEquipment::getConstructionControlPlanId);
        return getByIds(constructionControlPlanIds).stream().filter(x -> x.getProcessStatus() == ConstructionControlPlan.RELEVANCEED).collect(Collectors.toList());
    }

    private List<ConstructionControlPlan> getByIds(List<String> ids) {
        return constructionControlPlanBaseDao.find(
                "from ConstructionControlPlan where id in :ids",
                Map.of("ids", ids)
        );
    }

    /**
     * 获取所有状态为方案提交，现场调查，补充调查，方案审核，方案审批，关联计划，方案开始，方案结束的方案（）
     */
    private List<ConstructionControlPlan> getNotClosed() {
        return constructionControlPlanBaseDao.find(
                "from ConstructionControlPlan where processStatus <> :CLOSED",
                Map.of("CLOSED", ConstructionControlPlan.CLOSED)
        );
    }

    private static class CodeService {
        private static final Map<String, Object> CODES_MAP = CacheUtil.createMap("constructionControlPlanCode");

        private static void initCodesMap(String key) {
            CODES_MAP.clear();
            List<String> codes = new LinkedList<>();
            for (int i = 1; i < 10000; i++) {
                String numberCodes = new DecimalFormat("0000").format(i);
                codes.add(numberCodes);
            }
            CODES_MAP.put(key, codes);
        }

        private static String getACodeOfToday() {
            String currentDate = DateUtil.format(new Timestamp(System.currentTimeMillis()), "yyMMdd");
            if (CODES_MAP.get(currentDate) == null) initCodesMap(currentDate);

            @SuppressWarnings("unchecked")
            List<String> codeOfToday = (List<String>) CODES_MAP.get(currentDate);
            String code = codeOfToday.get(0);
            codeOfToday.remove(0);

            return currentDate + code;
        }
    }
}
