package com.yingda.lkj.beans.enums.repairclass;

import com.yingda.lkj.utils.pojo.PojoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hood  2020/11/25
 */
public enum RepairClass {
    INSPECTION_WORK(0, "巡检作业"),
    MAINTENANCE_WORK(1, "保养作业"),
    RECONDITIONING_WORK(2, "维修作业"),
    EXAMINE_WORK(3, "检验作业");

    /**
     * 任务类型和巡检标准类型
     */
    private byte type;
    private String name;

    private static final Logger LOGGER = LoggerFactory.getLogger(RepairClass.class);

    public static RepairClass getByType(byte type) {
        RepairClass reduce = Arrays.stream(values()).filter(x -> x.getType() == type).reduce(null, (x, y) -> y);
        if (reduce == null) {
            LOGGER.error(String.format("找不到type = %d的repairClass 返回%s类型", type, INSPECTION_WORK.name));
            return INSPECTION_WORK;
        }

        return reduce;
    }

    /**
     * <p>枚举类不能封装成json，转成map</p>
     * <p>注意合成类型字段不会放到这个map中</p>
     */
    public static List<Map<String, Object>> showdown() {
        return Arrays.stream(values()).map(x -> PojoUtils.toMap(RepairClass.class, x)).collect(Collectors.toList());
    }

    RepairClass(int type, String name) {
        this.type = (byte) type;
        this.name = name;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
