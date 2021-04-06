package com.yingda.lkj.service.backstage.websocket;

import com.alibaba.fastjson.JSONObject;

public interface WebsocketService {
    /**
     * 大屏设置的方法
     * @param userId  登陆人id
     * @param constructionStep  方案阶段
     * @param warnStatus        风险类别
     * @param dianwuDuanId      设置的电务段
     * @param workshopId        车间
     * @param railwayLineId     线路
     * @throws Exception
     */
    void settingBigScreen(String userId,String constructionStep, String warnStatus, String dianwuDuanId, String workshopId, String railwayLineId) throws Exception;

    /**
     * 推送大屏信息，当有方案或预警或日计划变动的时候调用的方法
     * @throws Exception
     * @return
     */
    JSONObject sendBigScreen(String dianwuDuanIdFrom, String workshopIdFrom) throws Exception;
}
