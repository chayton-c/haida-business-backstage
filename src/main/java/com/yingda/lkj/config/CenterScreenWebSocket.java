package com.yingda.lkj.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yingda.lkj.beans.system.Json;
import com.yingda.lkj.beans.system.JsonMessage;
import com.yingda.lkj.config.websocketPage.BaseSession;
import com.yingda.lkj.service.backstage.constructioncontrolplan.WarningInfoService;
import com.yingda.lkj.utils.SpringBeanUtil;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * websocket 网页端操作类
 */
@ServerEndpoint(value = "/ws")
//,encoders = { WebSocketEncoder.class }
@Component
public class CenterScreenWebSocket {
    private static final Map<String, BaseSession> sessionMap = new HashMap<>();

    private WarningInfoService warningInfoService;


    @OnOpen
    public void onOpen(Session session) throws Exception {
        if (warningInfoService == null) {
            warningInfoService = SpringBeanUtil.getBean(WarningInfoService.class);
        }
        BaseSession baseSession = new BaseSession();
        baseSession.setSession(session);
        sessionMap.put(session.getId(), baseSession);
        sendMessageByUser(session.getId(), session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        sessionMap.keySet().removeIf(key -> key.equals(session.getId()));
    }

    // 登录 {sendType:0,userSendType:1,dianwuduanId:1,workshopId:1,userId:35}
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息：" + message);
        JSONObject messJson = JSONObject.parseObject(message);
        BaseSession baseSession = JSON.toJavaObject(messJson, BaseSession.class);
        if (baseSession.getSendType() == 0) {
            sessionMap.get(session.getId()).setUserSendType(Integer.parseInt(messJson.get("userSendType").toString()));
            sessionMap.get(session.getId()).setDianwuduanId(messJson.get("dianwuduanId").toString());
            sessionMap.get(session.getId()).setWorkshopId(messJson.get("workshopId").toString());
            sessionMap.get(session.getId()).setUserId(messJson.get("userId").toString());
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    //   下面是自定义的一些方法
    public void sendMessageByUser(String sessionId, String message) throws Exception {
//        Map<String, Object> attributes = new HashMap<>();
//        List<WarningInfo> warningInfos = warningInfoService.sendWarningInfoToWebsocket();
//        attributes.put("warningInfos", warningInfos);
//        this.session.getBasicRemote().sendText(String.valueOf(new Json(JsonMessage.SUCCESS,attributes)));

        sessionMap.forEach((k, v) -> {
            if (v.getSession().getId().equals(sessionId)) {
                sendMess(message, v);
            }
        });
    }

    public static void sendMessageBySendType(Integer userSendType, JSONObject fromJson, String message) throws Exception {
        sessionMap.forEach((k, v) -> {
//            System.out.println(v.getUserSendType());
//            System.out.println(userSendType);
            if (v.getUserSendType().equals(userSendType)) {
                JSONObject messJson = JSONObject.parseObject(message);
                if (userSendType == 1) {
                    Integer execString = Integer.parseInt(messJson.get("exec").toString());
                    if (execString == 1) {
                        execSetting(message, v, fromJson);
                    } else if (execString == 2) {
                        execSend(message, v, fromJson);
                    }
                }
            }
        });
    }

    private static void execSetting(String message, BaseSession v, JSONObject fromJson) {
        String userId = (String) fromJson.get("userId");
        if (userId.equals(v.getUserId())) {
            sendMess(message, v);
        }
    }

    private static void execSend(String message, BaseSession v, JSONObject fromJson) {
        String dianwuduanId = (String) fromJson.get("dianwuDuanId");
        String workshopId = (String) fromJson.get("workshopId");
        //处
        if (v.getDianwuduanId().equals("0")) {
            sendMess(message, v);
        } else {
            //段
            if (v.getWorkshopId().equals("0")) {
                if (v.getDianwuduanId().equals(dianwuduanId)) {
                    sendMess(message, v);
                }
            } else {
                if (v.getDianwuduanId().equals(dianwuduanId) && v.getWorkshopId().equals(workshopId)) {
                    sendMess(message, v);
                }
            }
        }
    }


    private static void sendMess(String message, BaseSession v) {
        try {
            v.getSession().getBasicRemote().sendText(String.valueOf(new Json(JsonMessage.SUCCESS, message)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


