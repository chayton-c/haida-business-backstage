package com.yingda.lkj.controller.app;

import com.yingda.lkj.controller.BaseController;
import com.yingda.lkj.service.backstage.websocket.WebsocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/app/test_socket")
public class WebSocketController extends BaseController {
    @Autowired
    private WebsocketService websocketService;

    //http://localhost:9000/app/test_socket/settingBigScreen?userId=35&constructionStep=123455445454&warnStatus=1&dianwuDuanId=1&workshopId=1&railwayLineId=1
    @RequestMapping(value = "/settingBigScreen")
    @ResponseBody
    public void settingBigScreen(String userId,String constructionStep,String warnStatus,String dianwuDuanId, String workshopId ,String railwayLineId ) throws Exception {
//        String dianwuDuanIdFrom = "1";
//        String workshopIdFrom = "1";
//        String constructionStep = "123455445454";//方案阶段
//        String warnStatus = "1"; //风险类别
//        String dianwuDuanId = "1"; //所属电务段 为0时表示电务处
//        String workshopId = "1"; //所属车间 为0时显示电务段
//        String railwayLineId = "1"; //线路
        websocketService.settingBigScreen(userId,constructionStep, warnStatus, dianwuDuanId, workshopId, railwayLineId);
    }
    //http://localhost:9000/app/test_socket/sendBigScreen?dianwuDuanIdFrom=1&workshopIdFrom=1
    @RequestMapping(value = "/sendBigScreen")
    @ResponseBody
    public void sendBigScreen(String dianwuDuanIdFrom,String workshopIdFrom) throws Exception {
//        String dianwuDuanIdFrom = "1";
//        String workshopIdFrom = "1";
        websocketService.sendBigScreen(dianwuDuanIdFrom, workshopIdFrom);
    }


}