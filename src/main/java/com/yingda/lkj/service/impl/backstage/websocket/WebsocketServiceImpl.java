package com.yingda.lkj.service.impl.backstage.websocket;

import com.alibaba.fastjson.JSONObject;
import com.yingda.lkj.beans.entity.backstage.constructioncontrolplan.WarningInfo;
import com.yingda.lkj.config.CenterScreenWebSocket;
import com.yingda.lkj.service.backstage.constructioncontrolplan.WarningInfoService;
import com.yingda.lkj.service.backstage.websocket.WebsocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service("websocketService")
public class WebsocketServiceImpl implements WebsocketService {
    @Autowired
    private WarningInfoService warningInfoService;
    @Override
    public void settingBigScreen(String userId,String constructionStep,String warnStatus,String dianwuDuanId,String workshopId,String railwayLineId) throws Exception{
        JSONObject object = new JSONObject();
        object.put("sendType",1);
        object.put("exec","1");//大屏设置
        JSONObject objectFrom = new JSONObject();
        objectFrom.put("userId",userId);//来源用户id
        JSONObject objectSetting = new JSONObject();
        objectSetting.put("constructionStep",constructionStep);//方案阶段
        objectSetting.put("warnStatus",warnStatus); //风险类别
        objectSetting.put("dianwuDuanId",dianwuDuanId); //所属电务段 为0时表示电务处
        objectSetting.put("workshopId",workshopId); //所属车间 为0时显示电务段
        objectSetting.put("railwayLineId",railwayLineId); //线路
        object.put("data",objectSetting);
        CenterScreenWebSocket.sendMessageBySendType(1,objectFrom, object.toString());
    }

    @Override
    @ResponseBody
    public JSONObject sendBigScreen(String dianwuDuanIdFrom, String workshopIdFrom) throws Exception{
        JSONObject objectFrom = new JSONObject();
        objectFrom.put("dianwuDuanId",dianwuDuanIdFrom);//来源所属电务段 为0时表示电务处
        objectFrom.put("workshopId",workshopIdFrom);//来源车间 为0时显示电务段

        JSONObject attributes = new JSONObject();
        List<WarningInfo> warningInfos = warningInfoService.sendWarningInfoToWebsocket();
        attributes.put("warningInfos", warningInfos);
        attributes.put("sendType",1);
        attributes.put("exec","2");//大屏推送
        CenterScreenWebSocket.sendMessageBySendType(1, objectFrom,attributes.toString());
        return attributes;
    }
}
