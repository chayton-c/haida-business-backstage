package com.yingda.lkj.beans.pojo.device;

import com.yingda.lkj.beans.entity.backstage.device.Device;
import com.yingda.lkj.beans.entity.backstage.lkj.LkjDataLine;
import com.yingda.lkj.beans.entity.backstage.line.RailwayLine;
import com.yingda.lkj.beans.entity.backstage.line.Station;
import com.yingda.lkj.beans.entity.backstage.organization.Organization;
import com.yingda.lkj.utils.StringUtils;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author hood  2020/1/7
 */
@Data
public class Semaphore {

    // downriver字段
    public static final String UPRIVER = "上行";
    public static final String DOWNRIVER = "下行";

    // retrograde字段
    public static final String FORWARD = "正向";
    public static final String RETROGRADE = "逆向";

    // downriver字段和retrograde字段通用：半自闭区间，不区分正向逆向和上行下行
    public static final String SEME_CLOSED_INTERVAL = "半自闭";

    public static final String DOWNRIVER_TITLE = "行别";
    public static final String POSITION = "信号机位置(km)";
    public static final String SEMAPHORE_TYPE = "信号机类型";
    public static final String TRACK_SYSTEM = "轨道电路制式";
    public static final String CENTER_FREQUENCY = "中心频率";
    public static final String UM_71_TYPE = "UM71类别";
    public static final String OCCLUSION_METHOD = "闭塞方式";

    private String deviceId;
    private String lkjDataLineId;
    private String bureauName;
    private String bureauCode;
    private String railwayLineName;
    private String railwayLineCode;
    private String uniqueCode; // 唯一码，用于获取历史记录
    private String stationName;
    private String code;
    private String downriver; // 行别，上行还是下行
    private String retrograde; // 正向还是逆向
    private String position; // 信号机位置(km)
    private String semaphoreType; // 信号机类型
    private String trackSystem; // 轨道电路制式
    private String centerFrequency; // 中心频率
    private String UM71Type; // UM71类别
    private String occlusionMethod; // 闭塞方式
    private String remark; // 修改标注
    private String fragmentId; // 区间id
    private String fragmentName; // 区间名
    private String tableType;

    private List<String> distanceStyle;
    private List<String> lkjDataLineIds;
    private String distanceStr; // 间距
    private String flag; // 导出excel时作为标志位

    public Semaphore(Device device, LkjDataLine lkjDataLine, Organization bureau, RailwayLine railwayLine, Station station, Map<String, String> values) {
        this.deviceId = device.getId();
        this.lkjDataLineId = lkjDataLine.getId();

        String shortName = bureau.getShortName();
        this.bureauName = StringUtils.isNotEmpty(shortName) ? bureau.getShortName() : bureau.getName();
        this.bureauCode = bureau.getCode();
        this.railwayLineName = railwayLine.getName();
        this.railwayLineCode = railwayLine.getCode();

        Byte tableType = lkjDataLine.getTableType();
        if (tableType == null)
            this.tableType = "-";
        if (LkjDataLine.TABLE_1.equals(tableType))
            this.tableType = "表1";
        if (LkjDataLine.TABLE_2.equals(tableType))
            this.tableType = "表2";
        if (LkjDataLine.TABLE_3.equals(tableType))
            this.tableType = "表3";
        if (LkjDataLine.TABLE_4.equals(tableType))
            this.tableType = "表4";
        this.uniqueCode = lkjDataLine.getUniqueCode();
        this.stationName = station.getName();
        this.code = device.getCode();
        this.remark = device.getRemark();
        this.fragmentId = lkjDataLine.getFragmentId();
        this.fragmentName = lkjDataLine.getFragmentName();

        if (lkjDataLine.getDownriver() == LkjDataLine.UPRIVER)
            this.downriver = UPRIVER;
        if (lkjDataLine.getDownriver() == LkjDataLine.DOWNRIVER)
            this.downriver = DOWNRIVER;
        if (lkjDataLine.getDownriver() == LkjDataLine.SEME_CLOSED_INTERVAL)
            this.downriver = SEME_CLOSED_INTERVAL;
        if (lkjDataLine.getRetrograde() == LkjDataLine.FORWARD)
            this.retrograde = FORWARD;
        if (lkjDataLine.getRetrograde() == LkjDataLine.RETROGRADE)
            this.retrograde = RETROGRADE;
        if (lkjDataLine.getRetrograde() == LkjDataLine.SEME_CLOSED_INTERVAL)
            this.retrograde = SEME_CLOSED_INTERVAL;

        this.position = values.get(POSITION);
        this.semaphoreType = values.get(SEMAPHORE_TYPE);
        this.trackSystem = values.get(TRACK_SYSTEM);
        this.centerFrequency = values.get(CENTER_FREQUENCY);
        this.UM71Type = values.get(UM_71_TYPE);
        this.occlusionMethod = values.get(OCCLUSION_METHOD);

        double distance = lkjDataLine.getDistance();
        this.distanceStr = String.format("%.2f", distance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Semaphore semaphore = (Semaphore) o;
        return deviceId.equals(semaphore.deviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId);
    }
}
