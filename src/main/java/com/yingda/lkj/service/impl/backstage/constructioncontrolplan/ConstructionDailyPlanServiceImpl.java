package com.yingda.lkj.service.impl.backstage.constructioncontrolplan;

import com.yingda.lkj.beans.entity.backstage.constructioncontrolplan.ConstructionControlPlan;
import com.yingda.lkj.beans.entity.backstage.constructioncontrolplan.ConstructionDailyPlan;
import com.yingda.lkj.beans.exception.CustomException;
import com.yingda.lkj.beans.system.JsonMessage;
import com.yingda.lkj.dao.BaseDao;
import com.yingda.lkj.service.backstage.constructioncontrolplan.ConstructionControlPlanEquipmentService;
import com.yingda.lkj.service.backstage.constructioncontrolplan.ConstructionControlPlanService;
import com.yingda.lkj.service.backstage.constructioncontrolplan.ConstructionDailyPlanService;
import com.yingda.lkj.service.backstage.constructioncontrolplan.WarningInfoService;
import com.yingda.lkj.utils.CacheUtil;
import com.yingda.lkj.utils.StringUtils;
import com.yingda.lkj.utils.date.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.*;

@Service("constructionDailyPlanService")
public class ConstructionDailyPlanServiceImpl implements ConstructionDailyPlanService {

    @Autowired
    private BaseDao<ConstructionDailyPlan> constructionDailyPlanBaseDao;
    @Autowired
    private ConstructionControlPlanService constructionControlPlanService;
    @Autowired
    private BaseDao<ConstructionControlPlan> constructionControlPlanBaseDao;
    @Autowired
    private WarningInfoService warningInfoService;
    @Autowired
    private ConstructionControlPlanEquipmentService constructionControlPlanEquipmentService;

    @Override
    public String createCode() {
        return CodeService.getACodeOfToday();
    }

    @Override
    public ConstructionDailyPlan getById(String id) {
        return constructionDailyPlanBaseDao.get(ConstructionDailyPlan.class, id);
    }

    @Override
    public List<ConstructionDailyPlan> getByConstructionControlPlan(String constructionControlPlanId) {
        return constructionDailyPlanBaseDao.find(
                "from ConstructionDailyPlan where constructionControlPlanId = :constructionControlPlanId",
                Map.of("constructionControlPlanId", constructionControlPlanId)
        );
    }

    @Override
    public void saveOrUpdate(ConstructionDailyPlan pageConstructionDailyPlan) throws CustomException {
        // 公里标校验：
        ConstructionControlPlan constructionControlPlan =
                constructionControlPlanService.getById(pageConstructionDailyPlan.getConstructionControlPlanId());
        checkData(constructionControlPlan, pageConstructionDailyPlan);

        ConstructionDailyPlan constructionDailyPlan = new ConstructionDailyPlan();
        BeanUtils.copyProperties(pageConstructionDailyPlan, constructionDailyPlan, "addTime");
        if (StringUtils.isEmpty(constructionDailyPlan.getId()))
            constructionDailyPlan.setId(UUID.randomUUID().toString());
        if (constructionDailyPlan.getAddTime() == null)
            constructionDailyPlan.setAddTime(new Timestamp(System.currentTimeMillis()));

        constructionDailyPlanBaseDao.saveOrUpdate(constructionDailyPlan);
    }

    @Override
    public void checkFinishedStatus() {
        Timestamp current = new Timestamp(System.currentTimeMillis());
        // 所有未关闭的日计划
        List<ConstructionDailyPlan> notClosed = getNotClosed();
        for (ConstructionDailyPlan constructionDailyPlan : notClosed) {
            if (constructionDailyPlan.getStartTime().before(current))
                start(constructionDailyPlan);
        }
    }

    @Override
    public void closeByConstructionControlPlanId(String constructionControlPlanId) {
//        Timestamp current = new Timestamp(System.currentTimeMillis());
//        for (ConstructionDailyPlan constructionDailyPlan : getByConstructionControlPlan(constructionControlPlanId))
//            if (constructionDailyPlan.getEndTime().after(current))
//                close(constructionDailyPlan);
    }

    @Override
    public List<ConstructionDailyPlan> getProcessingPlansByIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) return new ArrayList<>();

        return constructionDailyPlanBaseDao.find(
                "from ConstructionDailyPlan where finishedStatus = :finishedStatus and id in :ids",
                Map.of("finishedStatus", ConstructionDailyPlan.PROCESSING, "ids", ids)
        );
    }

    @Deprecated
    private List<ConstructionDailyPlan> getAll() {
        return constructionDailyPlanBaseDao.find("from ConstructionDailyPlan");
    }

    //获取非 “结束” 状态的所有日计划
    private List<ConstructionDailyPlan> getNotClosed() {
        return constructionDailyPlanBaseDao.find(
                "from ConstructionDailyPlan where finishedStatus <> :finishedStatus",
                Map.of("finishedStatus", ConstructionDailyPlan.CLOSED)
        );
    }

    private void start(ConstructionDailyPlan constructionDailyPlan) {
        // 方案改为方案开始
        ConstructionControlPlan constructionControlPlan =
                constructionControlPlanService.getById(constructionDailyPlan.getConstructionControlPlanId());
        constructionControlPlan.setPlanStatus(ConstructionControlPlan.CONSTRUCTING);

        // 日计划实际开始时间拷贝start_time
        constructionDailyPlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        constructionDailyPlan.setFinishedStatus(ConstructionDailyPlan.PROCESSING);
        constructionDailyPlan.setActualStartTime(constructionDailyPlan.getStartTime());

        constructionDailyPlanBaseDao.saveOrUpdate(constructionDailyPlan);
        constructionControlPlanBaseDao.saveOrUpdate(constructionControlPlan);
    }

    /**
     * 实际（手动）开始日计划
     */
    public void actualStart(ConstructionDailyPlan constructionDailyPlan) {
        if (constructionDailyPlan != null) {
            ConstructionControlPlan constructionControlPlan = constructionControlPlanService.getById(constructionDailyPlan.getConstructionControlPlanId());

            // 方案改为施工中
            constructionControlPlan.setPlanStatus(ConstructionControlPlan.CONSTRUCTING);

            // 日计划实际开始时间设为当前时间
            constructionDailyPlan.setFinishedStatus(ConstructionDailyPlan.PROCESSING);
            constructionDailyPlan.setActualStartTime(new Timestamp(System.currentTimeMillis()));
            constructionDailyPlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));

            constructionControlPlanBaseDao.update(constructionControlPlan);
            constructionDailyPlanBaseDao.update(constructionDailyPlan);
        }
    }

    /**
     * 实际（手动）结束日计划
     */
    public void actualClose(ConstructionDailyPlan constructionDailyPlan) {
        if (constructionDailyPlan != null) {
            ConstructionControlPlan constructionControlPlan =
                    constructionControlPlanService.getById(constructionDailyPlan.getConstructionControlPlanId());

            // 方案改为“方案开始”
            constructionControlPlan.setPlanStatus(ConstructionControlPlan.FORMAL_START);

            // 日计划实际结束时间设为当前时间
            constructionDailyPlan.setFinishedStatus(ConstructionDailyPlan.CLOSED);
            constructionDailyPlan.setActualEndTime(new Timestamp(System.currentTimeMillis()));
            constructionDailyPlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));

            constructionControlPlanBaseDao.update(constructionControlPlan);
            constructionDailyPlanBaseDao.update(constructionDailyPlan);
        }
    }

    /**
     * 关闭日计划
     */
    private void close(ConstructionDailyPlan constructionDailyPlan) {
        constructionDailyPlan.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        constructionDailyPlan.setFinishedStatus(ConstructionDailyPlan.CLOSED);
        constructionDailyPlanBaseDao.saveOrUpdate(constructionDailyPlan);
    }

    private void checkData(ConstructionControlPlan constructionControlPlan, ConstructionDailyPlan constructionDailyPlan) throws CustomException {
        // 方案的起止公里标
        double constructionControlPlanStartKilometer = constructionControlPlan.getStartKilometer();
        double constructionControlPlanEndKilometer = constructionControlPlan.getEndKilometer();

        // 日计划的起止公里标
        double constructionDailyPlanStartKilometer = constructionDailyPlan.getStartKilometer();
        double constructionDailyPlanEndKilometer = constructionDailyPlan.getEndKilometer();

        if (constructionControlPlanStartKilometer > constructionDailyPlanStartKilometer)
            throw new CustomException(JsonMessage.PARAM_INVALID, String.format("日计划起始公里标小于方案起始公里标，方案起始公里标%.0f",
                    constructionControlPlanStartKilometer));

        if (constructionControlPlanEndKilometer < constructionDailyPlanEndKilometer)
            throw new CustomException(JsonMessage.PARAM_INVALID, String.format("日计划结束公里标大于方案结束公里标，方案结束公里标%.0f",
                    constructionControlPlanEndKilometer));

        if (constructionDailyPlanStartKilometer >= constructionDailyPlanEndKilometer)
            throw new CustomException(JsonMessage.PARAM_INVALID, "日计划起始公里标大于日计划结束公里标，方案结束公里标");

        // 方案的起止时间
        Timestamp constructionControlPlanStartTime = constructionControlPlan.getStartTime();
        Timestamp constructionControlPlanEndTime = constructionControlPlan.getEndTime();

        // 日计划的起止时间
        Timestamp constructionDailyPlanStartTime = constructionDailyPlan.getStartTime();
        Timestamp constructionDailyPlanEndTime = constructionDailyPlan.getEndTime();

        Timestamp current = new Timestamp(System.currentTimeMillis());
        if (constructionDailyPlanStartTime.after(constructionDailyPlanEndTime))
            throw new CustomException(JsonMessage.PARAM_INVALID, "开始时间在结束时间之前");

        if (constructionControlPlanStartTime.after(constructionDailyPlanStartTime))
            throw new CustomException(JsonMessage.PARAM_INVALID, String.format("日计划开始时间在方案开始时间之前，方案开始时间%tF",
                    new Date(constructionControlPlanStartTime.getTime())));

        if (constructionControlPlanEndTime.before(constructionDailyPlanEndTime))
            throw new CustomException(JsonMessage.PARAM_INVALID, String.format("日计划结束时间在方案结束时间之后，方案结束时间%tF",
                    new Date(constructionControlPlanEndTime.getTime())));

        if (current.after(constructionDailyPlanStartTime))
            throw new CustomException(JsonMessage.PARAM_INVALID, "日计划开始时间早于当前时间");
    }


    private static class CodeService {
        private static final Map<String, Object> CODES_MAP = CacheUtil.createMap("constructionDailyPlanCode");

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
