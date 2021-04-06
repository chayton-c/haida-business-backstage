package com.yingda.lkj.service.impl.jt808;

import com.yingda.lkj.service.backstage.equipment.EquipmentService;
import com.yingda.lkj.utils.helper.DataHelper;
import com.zhoyq.server.jt808.starter.config.Const;
import com.zhoyq.server.jt808.starter.dto.SimAuthDto;
import com.zhoyq.server.jt808.starter.entity.*;
import com.zhoyq.server.jt808.starter.helper.ByteArrHelper;
import com.zhoyq.server.jt808.starter.service.DataService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class DeviceDataServiceImpl implements DataService {

    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    ByteArrHelper byteArrHelper;
    @Autowired
    DataHelper dataHelper;


    @Override
    public void terminalRsa(String sim, byte[] e, byte[] n) {

    }

    @Override
    public byte[] terminalRsa(String sim) {
        return new byte[0];
    }

    @Override
    public void terminalAnswer(String sim, int platformStreamNumber, String platformCommandId, String msgId, byte[] msgBody) {

    }

    @Override
    public void terminalHeartbeat(String sim) {
        log.info("{}, heartbeat", sim);
    }

    @Override
    public void terminalCancel(String sim) {
        log.info("{}, cancel", sim);

        try {

        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }

    @Override
    public String terminalRegister(String sim, int province, int city, String manufacturer, String deviceType, String deviceId, byte licenseColor, String registerLicense) {
        log.info("{}, register", sim);

        // 查询是否存在指定的设备
        List<String> devices = equipmentService.imeis();

        if (devices.indexOf(sim.substring(sim.length() -12)) == -1) {
            // 数据库无此终端
            return Const.TERMINAL_REG_NO_TERMINAL;
        }

        // 鉴权码 电话后七位
        String auth = sim.substring(sim.length() - 7);

        // 返回 电话后七位 鉴权
        return auth;
    }

    @Override
    public void terminalLocation(String sim, LocationInfo locationInfo, Integer mediaId) {
        log.info("{}, location", sim);
        // 获取标准信息 报警信息 状态信息 以及 附加信息
        // 附加信息
        List<LocationAttachInfo> attachInfo = locationInfo.getAttachInfo();

        for (LocationAttachInfo attach : attachInfo) {
            byte[] data = attach.getData();
            switch (attach.getId()) {
                case 0x01:
                    log.info("{}", (double)byteArrHelper.fourbyte2int(data) / 10D);
                    break;
                case 0x02:
                case 0x03:
                    log.info("{}", (double)byteArrHelper.twobyte2int(data) / 10D);
                    break;
                case 0x04:
                    log.info("{}", byteArrHelper.twobyte2int(data));
                    break;
                case 0x05:
                    log.info("{}", byteArrHelper.toHexString(data));
                    break;
                case 0x06:
                    log.info("{}", dataHelper.formatTracePartTemp(data));
                    break;
                case 0x11:
                    if(data.length == 1){
                        log.info("{}", (byte)0);
                    } else {
                        log.info("{}", data[0]);
                        log.info("{}",
                                byteArrHelper.fourbyte2int(new byte[]{data[1], data[2], data[3], data[4]}));
                    }
                    break;
                case 0x12:
                    log.info("{}", data[0]);
                    log.info("{}",
                            byteArrHelper.fourbyte2int(new byte[]{data[1], data[2], data[3], data[4]})
                    );
                    log.info("{}", data[5]);
                    break;
                case 0x13:
                    log.info("{}",
                            byteArrHelper.fourbyte2int(new byte[]{data[0], data[1], data[2], data[3]})
                    );
                    log.info("{}",
                            byteArrHelper.twobyte2int(new byte[]{data[4], data[5]})
                    );
                    log.info("{}", data[6]);
                    break;
                case 0x25:
                    log.info("{}", dataHelper.genTraceStatusExt(data));
                    break;
                case 0x2A:
                    log.info("{}", dataHelper.genIoStatus(data));
                    break;
                case 0x2B:
                    log.info("{}", byteArrHelper.twobyte2int(new byte[]{data[2], data[3]}));
                    log.info("{}", byteArrHelper.twobyte2int(new byte[]{data[0], data[1]}));
                    break;
                case 0x30:
                    log.info("{}", data[0]);
                    break;
                case 0x31:
                    log.info("{}", data[0]);
                    break;
                default:
            }
        }
        // 保存
        try {
            equipmentService.recordLocation(sim.substring(sim.length() - 12), locationInfo.getLatitude(), locationInfo.getLongitude());
        }catch (Exception e)
        {
            log.error(e.getMessage());
        }
    }

    @Override
    public void eventReport(String sim, byte eventReportAnswerId) {
        log.info("{}, report", sim);
    }

    @Override
    public void orderInfo(String sim, byte type) {
        log.info("{}, order info", sim);
    }

    @Override
    public void cancelOrderInfo(String sim, byte type) {
        log.info("{}, cancel order info", sim);
    }

    @Override
    public void eBill(String sim, byte[] data) {
        log.info("{}, bill", sim);
    }

    @Override
    public void driverInfo(String sim, DriverInfo driverInfo) {
        log.info("{}, driver info", sim);
    }

    @Override
    public void canData(String sim, CanDataInfo canDataInfo) {
        log.info("{}, can", sim);
    }

    @Override
    public void mediaInfo(String sim, MediaInfo mediaInfo) {
        log.info("{}, media info", sim);
    }

    @Override
    public void mediaPackage(String sim, byte[] mediaData, Integer mediaId) {
        log.info("{}, media package", sim);
    }

    @Override
    public void dataTransport(String sim, DataTransportInfo dataTransportInfo) {
        log.info("{}, data transport", sim);
    }

    @Override
    public void compressData(String sim, byte[] data) {
        log.info("{}, compress data", sim);
    }

    @Override
    public void terminalAuth(String phone, String authId, String imei, String softVersion) {

    }

    @Override
    public List<SimAuthDto> simAuth() {
        return null;
    }
}
