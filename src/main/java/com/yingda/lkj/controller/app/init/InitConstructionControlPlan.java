package com.yingda.lkj.controller.app.init;

import com.yingda.lkj.beans.entity.backstage.constructioncontrolplan.ConstructionControlPlan;
import com.yingda.lkj.beans.entity.system.User;
import com.yingda.lkj.beans.system.Json;
import com.yingda.lkj.beans.system.JsonMessage;
import com.yingda.lkj.controller.BaseController;
import com.yingda.lkj.service.backstage.constructioncontrolplan.ConstructionControlPlanService;
import com.yingda.lkj.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/init/constructionControlPlan")
public class InitConstructionControlPlan extends BaseController {
    @Autowired
    private ConstructionControlPlanService constructionControlPlanService;
    @Autowired
    private UserService userService;

    @RequestMapping("")
    public Json getList() {
        Map<String, Object> attributes = new HashMap<>();

        List<ConstructionControlPlan> constructionControlPlans = constructionControlPlanService.issuePendingInvestigatePlans2App();
        List<User> users = userService.getAll();
        constructionControlPlans.forEach(x -> x.setExecutors(users));

        attributes.put("constructionControlPlans", constructionControlPlans);

        return new Json(JsonMessage.SUCCESS, attributes);
    }
}
