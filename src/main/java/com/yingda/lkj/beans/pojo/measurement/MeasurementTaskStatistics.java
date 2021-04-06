package com.yingda.lkj.beans.pojo.measurement;

import com.yingda.lkj.beans.entity.backstage.measurement.MeasurementTaskDetail;
import lombok.Data;

import java.util.List;

/**
 * @author hood  2020/6/16
 */
@Data
public class MeasurementTaskStatistics {
    private int total = 0;
    private int notChecked = 0;
    private int errorStatusNormal = 0;
    private int error = 0;
    private int omission = 0;

    public MeasurementTaskStatistics(List<MeasurementTaskDetail> measurementTaskDetails) {
        this.total = measurementTaskDetails.size();
        for (MeasurementTaskDetail measurementTaskDetail : measurementTaskDetails) {
            byte errorStatus = measurementTaskDetail.getErrorStatus();
            switch (errorStatus) {
                case MeasurementTaskDetail.NOT_CHECKED:
                    this.notChecked++;
                    break;
                case MeasurementTaskDetail.ERROR_STATUS_NORMAL:
                    this.errorStatusNormal++;
                    break;
                case MeasurementTaskDetail.ERROR:
                    this.error++;
                    break;
                case MeasurementTaskDetail.OMISSION:
                    this.omission++;
                    break;
            }
        }
    }
}
