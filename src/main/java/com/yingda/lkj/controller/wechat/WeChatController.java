package com.yingda.lkj.controller.wechat;

import com.yingda.lkj.beans.system.Json;
import com.yingda.lkj.beans.system.JsonMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weChat")
public class WeChatController {

    @RequestMapping("")
    public Json check() {
        return new Json(JsonMessage.SUCCESS);
    }

}
