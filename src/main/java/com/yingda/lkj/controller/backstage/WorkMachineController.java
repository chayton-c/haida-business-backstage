package com.yingda.lkj.controller.backstage;

import com.yingda.lkj.beans.entity.backstage.work.WorkMachine;
import com.yingda.lkj.beans.system.Json;
import com.yingda.lkj.beans.system.JsonMessage;
import com.yingda.lkj.controller.BaseController;
import com.yingda.lkj.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * 工程机械控制器
 */
@RequestMapping("/backstage/workMachine")
@RestController
public class WorkMachineController extends BaseController {
    @Autowired
    private BaseService<WorkMachine> workMachineBaseService;

    private WorkMachine pageWorkMachine;
    /**
     * 查询工程机械实体
     * @return
     */
    @RequestMapping("/sel")
    public Json sel() throws Exception{
        String sql = """
                SELECT id as id,mach_name as machName FROM work_machine
                """;
        List<WorkMachine> list = workMachineBaseService.findSQL(
            sql, null, WorkMachine.class
        );
        return new Json(JsonMessage.SUCCESS, list);
    }

    @RequestMapping("/saveOrUpdate")
    public Json saveOrUpdate(HttpServletRequest req) throws Exception{
        if(req.getParameter("id")==null){
            pageWorkMachine.setId(UUID.randomUUID().toString());
        }
        workMachineBaseService.saveOrUpdate(pageWorkMachine);
        return new Json(JsonMessage.SUCCESS);
    }

    @ModelAttribute
    public void setPageWorkMachine(WorkMachine pageWorkMachine) {
        this.pageWorkMachine = pageWorkMachine;
    }
}
