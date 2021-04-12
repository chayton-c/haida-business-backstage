package com.yingda.lkj.controller;

import com.yingda.lkj.beans.system.Json;
import com.yingda.lkj.beans.system.JsonMessage;
import com.yingda.lkj.utils.wechat.enterprise.EnterpriseWeChatClient;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/test/enterpriseWeChat")
@RestController
public class TestEnterpriseWechatController extends BaseController{

    @RequestMapping("/sendMessage")
    public Json sendMessage() throws IOException {
        String message = req.getParameter("message");
        EnterpriseWeChatClient.sendMessage(message);
        return new Json(JsonMessage.SUCCESS);
    }

    @RequestMapping("/sendLink")
    public Json sendLink() {
        EnterpriseWeChatClient.sendLink();
        return new Json(JsonMessage.SUCCESS);
    }

    @RequestMapping("/uploadExcel")
    public Json uploadExcel() throws IOException {
        String mediaId = EnterpriseWeChatClient.uploadFile();
        EnterpriseWeChatClient.sendFile(mediaId);
        return new Json(JsonMessage.SUCCESS, mediaId);
    }

    @RequestMapping("/sendMarkDown")
    public Json sendMarkDown() {
        EnterpriseWeChatClient.sendMarkDown();
        return new Json(JsonMessage.SUCCESS);
    }

}
