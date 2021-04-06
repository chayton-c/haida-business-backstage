package com.yingda.lkj.beans.pojo.lkj;

import com.yingda.lkj.beans.pojo.device.SemaphoreFromExcel;
import lombok.Data;

/**
 * @author hood  2020/1/6
 */
@Data
public class LkjDataLineFromExcel {

    private SemaphoreFromExcel leftNode;
    private SemaphoreFromExcel rightNode;
    private double distance; // 距离
    private byte downriver; // 下行线还是上行线
    private byte retrograde; // 正向还是逆向
    private String fragmentId;
    private String uniqueCode;
    private Byte tableType;

    private int seq; // 插入顺序

    public LkjDataLineFromExcel() {
    }

    public LkjDataLineFromExcel(byte downriver, byte retrograde) {
        this.downriver = downriver;
        this.retrograde = retrograde;
    }
}
