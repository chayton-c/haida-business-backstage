package com.yingda.lkj.beans.enums.devicemaintenance;

import com.yingda.lkj.beans.entity.backstage.device.DeviceMaintenancePlan;
import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementTask;
import com.yingda.lkj.beans.exception.CustomException;
import com.yingda.lkj.beans.system.JsonMessage;
import com.yingda.lkj.service.backstage.measurement.MeasurementTaskService;
import com.yingda.lkj.utils.SpringContextUtil;
import com.yingda.lkj.utils.StringUtils;
import com.yingda.lkj.utils.date.DateUtil;
import com.yingda.lkj.utils.pojo.PojoUtils;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hood  2020/4/2
 */
public enum DeviceMaintenancePlanStrategy {
    DESIGNATED_HOUR_OF_THE_DAY(0, "每天指定小时") {
        @Override
        public void checkPlanDataComplete(DeviceMaintenancePlan deviceMaintenancePlan) throws CustomException {
            if (StringUtils.isEmpty(deviceMaintenancePlan.getExecutionDate()))
                throw new CustomException(JsonMessage.DATA_NO_COMPLETE, "执行时间不能为空");
        }

        @Override
        public MeasurementTask createNextTask(DeviceMaintenancePlan deviceMaintenancePlan, Timestamp baseTime) {
            // 如果计划使用的策略是每天指定小时，那么executionDates格式为 HH:mm, HH:mm ...
            List<Timestamp> executionDates = new ArrayList<>();
            for (String dateStr : deviceMaintenancePlan.getExecutionDate().split(","))
                executionDates.add(DateUtil.toTimestamp(dateStr, "HH:mm"));
            executionDates = executionDates.stream().sorted().collect(Collectors.toList());

            // 该字段表示下次任务的执行时间
            Timestamp nextTaskStartTime;

            // 剩下的情况是nextTaskStartTime为空(新生成的计划，还未下发任务)，或者是nextTaskStartTime在baseTime之后(上次下发的任务已经可以执行了，需要下发下一个任务)
            nextTaskStartTime = getNextTaskStartTime(baseTime, executionDates);

            // 根据下次任务时间校验能否生成工单:查询在当前时间以后一天的计划，如果这些计划中没有执行时间等于nextTaskStartTime的，则可以生成
            MeasurementTaskService measurementTaskService = (MeasurementTaskService) SpringContextUtil.getBean("measurementTaskService");
            List<MeasurementTask> measurementTasks = measurementTaskService.getByDeviceMaintenancePlan(
                    List.of(deviceMaintenancePlan), baseTime, new Timestamp(baseTime.getTime() + ONE_DAY_MILLIONS));
            if (measurementTasks.stream().anyMatch(x -> x.getStartTime().equals(nextTaskStartTime)))
                return null;

            String taskName = deviceMaintenancePlan.getName() + DateUtil.format(nextTaskStartTime, " HH:mm:00") + "任务";
            assert nextTaskStartTime != null;
            // 当前策略中，duration的工时表示小时
            Timestamp nextTaskEndTime = new Timestamp(nextTaskStartTime.getTime() + deviceMaintenancePlan.getDuration() * 3600L * 1000);
            return new MeasurementTask(deviceMaintenancePlan, taskName, nextTaskStartTime, nextTaskEndTime);
        }

        private Timestamp getNextTaskStartTime(Timestamp baseTime, List<Timestamp> executionDates) {
            Timestamp result;

            // 转成1970年1月1日的时间，因为只比较小时和分钟
            Timestamp baseTimeHours = DateUtil.toTimestamp(DateUtil.format(baseTime, "HH:mm"), "HH:mm");

            Timestamp resultHours = executionDates.stream().filter(x -> x.equals(baseTime)).reduce(null, (x, y) -> y);
            // 当前时间刚好等于其中一个设定时间
            if (resultHours != null)
                return DateUtil.toTimestamp(DateUtil.format(baseTime, "yyyy-MM-dd ") + DateUtil.format(resultHours, "HH:mm"), "yyyy-MM-dd HH:mm");

            // 不等于的话，插入设定时间list并排序后下一个节点就是任务时间
            executionDates.add(baseTimeHours);
            executionDates = executionDates.stream().sorted(Timestamp::compareTo).collect(Collectors.toList());

            int index = executionDates.indexOf(baseTimeHours);
            boolean lastNode = index == executionDates.size() - 1;
            resultHours = lastNode ? executionDates.get(0) : executionDates.get(index + 1);

            result = DateUtil.toTimestamp(DateUtil.format(baseTime, "yyyy-MM-dd ") + DateUtil.format(resultHours, "HH:mm"), "yyyy-MM-dd HH:mm");
            if (!lastNode)
                return result;

            // 当前时间插入设定时间后在队尾时，下次任务时间在明天
            Calendar calendar = Calendar.getInstance();
            assert result != null;
            calendar.setTime(new Date(result.getTime()));
            calendar.add(Calendar.DATE, 1);
            return new Timestamp(calendar.getTimeInMillis());
        }
    },
    EVENY_FEW_DAYS(1, "每隔几天") {
        @Override
        public void checkPlanDataComplete(DeviceMaintenancePlan deviceMaintenancePlan) throws CustomException {

        }

        @Override
        public MeasurementTask createNextTask(DeviceMaintenancePlan deviceMaintenancePlan, Timestamp baseTime) {
            return null;
        }
    };

    private static final long ONE_DAY_MILLIONS = 86400000L;
    private static final long ONE_HOUR_MILLIONS = 1000L * 60 * 60;

    private final byte strategy;
    private final String strategyName;

    DeviceMaintenancePlanStrategy(int strategy, String strategyName) {
        this.strategy = (byte) strategy;
        this.strategyName = strategyName;
    }

    /**
     * <p>枚举类不能封装成json，转成map</p>
     * <p>注意合成类型字段不会放到这个map中</p>
     */
    public static List<Map<String, Object>> showdown() {
        return Arrays.stream(values()).map(x -> PojoUtils.toMap(DeviceMaintenancePlanStrategy.class, x)).collect(Collectors.toList());
    }

    public static DeviceMaintenancePlanStrategy getStrategy(byte strategyCode) {
        return Arrays.stream(values()).filter(x -> strategyCode == x.getStrategy()).reduce(null, (x, y) -> y);
    }

    /**
     * 定时器生成下一个计划
     */
    public MeasurementTask createNextTask(DeviceMaintenancePlan deviceMaintenancePlan) {
        return createNextTask(deviceMaintenancePlan, new Timestamp(System.currentTimeMillis()));
    }

    /**
     * 校验计划数据完整性(只校验和deviceMaintenancePlan.executionStrategy有关的字段)
     * @throws CustomException 不完整抛异常，完整return
     */
    public abstract void checkPlanDataComplete(DeviceMaintenancePlan deviceMaintenancePlan) throws CustomException;

    /**
     * 尝试生成下一个任务，不满足条件返回null
     * @param baseTime 一般为当前时间，在甘特图中可指定时间
     */
    public abstract MeasurementTask createNextTask(DeviceMaintenancePlan deviceMaintenancePlan, Timestamp baseTime);

    public static DeviceMaintenancePlanStrategy getStrategy(DeviceMaintenancePlan deviceMaintenancePlan) {
        return getStrategy(deviceMaintenancePlan.getExecutionStrategy());
    }

    /**
     * 执行策略类型换策略名称
     */
    public static String getName(byte strategyCode) {
        return getStrategy(strategyCode).getStrategyName();
    }

    public String getStrategyName() {
        return strategyName;
    }

    public byte getStrategy() {
        return strategy;
    }

    public static void main(String[] args) {

    }
}
