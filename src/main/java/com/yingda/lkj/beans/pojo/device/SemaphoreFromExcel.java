package com.yingda.lkj.beans.pojo.device;

import com.yingda.lkj.beans.entity.backstage.device.Device;
import com.yingda.lkj.beans.entity.backstage.device.DeviceType;
import com.yingda.lkj.beans.pojo.utils.ExcelRowInfo;
import com.yingda.lkj.utils.StringUtils;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author hood  2020/1/6
 */
@Data
public class SemaphoreFromExcel {
    private String name;
    private String code;
    private String railwayLineCode; // 线路码
    private String downriver;
    private String deviceTypeId = DeviceType.SEMAPHORE_ID; // 设备类型id(信号机)
    private Byte outdoor = Device.OUTDOOR; // 室内/室外
    private byte spare = Device.USING; // 使用/备用
    private String remark;
    private String stationName;
    private Map<String, String> extendFieldValues;
    private String distanceStr;
    private String fragmentName;
    private int excelLineNumber; // 对应excel的哪一行
    private String lkjFlag; // lkj链接标注位

    public static SemaphoreFromExcel getInstance(ExcelRowInfo excelRowInfo) {
        List<String> cells = excelRowInfo.getCells();

        String lineCode = cells.get(3); // 线编号
        String downriver = cells.get(4); // 行别
        String stationName = cells.get(5).trim(); // 车站名
        String code = StringUtils.toDBC(cells.get(6)); // 信号机编号
        String position = cells.get(8); // 信号机位置(km)
        String semaphoreType = cells.get(9); // 信号机类型
        String trackSystem = cells.get(10); // 轨道电路制式
        String centerFrequency = cells.get(11); // 中心频率
        String UM71Type = cells.get(12); // UM71类别
        String occlusionMethod = cells.get(13); // 闭塞方式
        String remark = cells.get(14); // 修改标注

        SemaphoreFromExcel semaphoreFromExcel = new SemaphoreFromExcel();

        semaphoreFromExcel.setExcelLineNumber(excelRowInfo.getLineIndex());
        semaphoreFromExcel.setName(stationName + code);
        semaphoreFromExcel.setCode(code);
        semaphoreFromExcel.setRailwayLineCode(lineCode);
        semaphoreFromExcel.setRemark(remark);
        semaphoreFromExcel.setStationName(stationName);

        Map<String, String> extendFieldValues = new HashMap<>();
        // excel导入信号机，时间有限只能这么解了
        extendFieldValues.put(Semaphore.DOWNRIVER_TITLE, downriver);
        extendFieldValues.put(Semaphore.POSITION, position);
        extendFieldValues.put(Semaphore.SEMAPHORE_TYPE, semaphoreType);
        extendFieldValues.put(Semaphore.TRACK_SYSTEM, trackSystem);
        extendFieldValues.put(Semaphore.CENTER_FREQUENCY, centerFrequency);
        extendFieldValues.put(Semaphore.UM_71_TYPE, UM71Type);
        extendFieldValues.put(Semaphore.OCCLUSION_METHOD, occlusionMethod);

        semaphoreFromExcel.setExtendFieldValues(extendFieldValues);
        return semaphoreFromExcel;
    }

    public static SemaphoreFromExcel getInstance4Yunnan(ExcelRowInfo excelRowInfo) {
        List<String> cells = excelRowInfo.getCells();

        String lineCode = cells.get(3); // 线编号
        String downriver = cells.get(4); // 行别
        String fragmentName = cells.get(5); // 区间名
        String stationName = cells.get(6).trim(); // 车站名
        String code = StringUtils.toDBC(cells.get(7)); // 信号机编号
        String distanceStr = cells.get(8); // 距离
        String position = cells.get(9); // 信号机位置(km)
        String semaphoreType = cells.get(10); // 信号机类型
        String trackSystem = cells.get(11); // 轨道电路制式
        String centerFrequency = cells.get(12); // 中心频率
        String UM71Type = cells.get(13); // UM71类别
        String occlusionMethod = cells.get(14); // 闭塞方式
        String remark = cells.get(15); // 修改标注
        String lkjFlag = cells.get(16); // lkj连接标注位

        SemaphoreFromExcel semaphoreFromExcel = new SemaphoreFromExcel();

        semaphoreFromExcel.setExcelLineNumber(excelRowInfo.getLineIndex());
        semaphoreFromExcel.setName(stationName + code);
        semaphoreFromExcel.setDownriver(downriver);
        semaphoreFromExcel.setFragmentName(fragmentName);
        semaphoreFromExcel.setDistanceStr(distanceStr);
        semaphoreFromExcel.setCode(code);
        semaphoreFromExcel.setRailwayLineCode(lineCode);
        semaphoreFromExcel.setRemark(remark);
        semaphoreFromExcel.setLkjFlag(lkjFlag);
        semaphoreFromExcel.setStationName(stationName);

        Map<String, String> extendFieldValues = new HashMap<>();
        // excel导入信号机，时间有限只能这么解了
        extendFieldValues.put(Semaphore.DOWNRIVER_TITLE, downriver);
        extendFieldValues.put(Semaphore.POSITION, position);
        extendFieldValues.put(Semaphore.SEMAPHORE_TYPE, semaphoreType);
        extendFieldValues.put(Semaphore.TRACK_SYSTEM, trackSystem);
        extendFieldValues.put(Semaphore.CENTER_FREQUENCY, centerFrequency);
        extendFieldValues.put(Semaphore.UM_71_TYPE, UM71Type);
        extendFieldValues.put(Semaphore.OCCLUSION_METHOD, occlusionMethod);

        semaphoreFromExcel.setExtendFieldValues(extendFieldValues);
        return semaphoreFromExcel;
    }

    private SemaphoreFromExcel() {
    }

    @Override
    public String toString() {
        return "SemaphoreFromExcel{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", railwayLineCode='" + railwayLineCode + '\'' +
                ", deviceTypeId='" + deviceTypeId + '\'' +
                ", outdoor=" + outdoor +
                ", spare=" + spare +
                ", remark='" + remark + '\'' +
                ", stationName='" + stationName + '\'' +
                ", extendFieldValues=" + extendFieldValues +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SemaphoreFromExcel that = (SemaphoreFromExcel) o;
        return code.equals(that.code) &&
                railwayLineCode.equals(that.railwayLineCode) &&
                stationName.equals(that.stationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, railwayLineCode, stationName);
    }
}
