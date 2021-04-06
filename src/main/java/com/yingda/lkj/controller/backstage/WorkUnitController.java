package com.yingda.lkj.controller.backstage;

import com.yingda.lkj.beans.entity.backstage.work.WorkMachine;
import com.yingda.lkj.beans.entity.backstage.work.WorkUnit;
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
 * 建设单位控制器
 */
@RequestMapping("/backstage/workUnit")
@RestController
public class WorkUnitController extends BaseController {
    @Autowired
    private BaseService<WorkUnit> workUnitBaseService;

    private WorkUnit pageWorkUnit;
    /**
     * 查询建设单位实体
     * @return
     */
    @RequestMapping("/sel")
    public Json sel() throws Exception{
        String sql = """
                SELECT id as id,unit_name as machName FROM work_unit
                """;
        List<WorkUnit> list = workUnitBaseService.findSQL(
            sql, null, WorkUnit.class
        );
        return new Json(JsonMessage.SUCCESS, list);
    }

    @RequestMapping("/saveOrUpdate")
    public Json saveOrUpdate(HttpServletRequest req) throws Exception{
        if(req.getParameter("id")==null){
            pageWorkUnit.setId(UUID.randomUUID().toString());
        }
        workUnitBaseService.saveOrUpdate(pageWorkUnit);
        return new Json(JsonMessage.SUCCESS);
    }

    @ModelAttribute
    public void setPageWorkUnit(WorkUnit pageWorkUnit) {
        this.pageWorkUnit = pageWorkUnit;
    }
}
