package com.yingda.lkj.beans.enums.dataversion;

import com.yingda.lkj.beans.entity.backstage.dataversion.DataApproveFlow;
import com.yingda.lkj.beans.entity.backstage.lkj.LkjDataLine;
import com.yingda.lkj.beans.entity.backstage.lkj.lkjextends.*;
import com.yingda.lkj.service.backstage.approvedataline.Lkj16Service;
import com.yingda.lkj.service.backstage.approvedataline.Lkj18Service;
import com.yingda.lkj.service.backstage.approvedataline.Lkj20Service;
import com.yingda.lkj.service.backstage.lkjdataline.LkjDataLineService;
import com.yingda.lkj.utils.SpringContextUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author hood  2020/5/26
 */
public enum ApproveDataType {
    LKJ14("14", "LKJ-14表", LkjDataLine.class,
            "forward:/backstage/lkjVersion",
            "forward:/backstage/lkjApproveSubmit/importPage",
            "forward:/backstage/lkjApproveUpdate/lkjApproveFlowDetail") {
        @Override
        public void createDataLines(DataApproveFlow dataApproveFlow, List<?> rawDataLines) {
            LkjDataLineService lkjDataLineService = (LkjDataLineService) SpringContextUtil.getBean("lkjDataLineService");
            List<LkjDataLine> rawLkjDataLines = new ArrayList<>();
            for (Object rawDataLine : rawDataLines)
                rawLkjDataLines.add((LkjDataLine) rawDataLine);
            lkjDataLineService.createLkjDataLine(dataApproveFlow, rawLkjDataLines);
        }

        @Override
        public void completeDataLines(DataApproveFlow dataApproveFlow) {
            LkjDataLineService lkjDataLineService = (LkjDataLineService) SpringContextUtil.getBean("lkjDataLineService");
            lkjDataLineService.completeLkjDataLine(dataApproveFlow);
        }

        @Override
        public void refuseDataLines(DataApproveFlow dataApproveFlow) {
            LkjDataLineService lkjDataLineService = (LkjDataLineService) SpringContextUtil.getBean("lkjDataLineService");
            lkjDataLineService.refuseLkjDataLines(dataApproveFlow);
        }
    },
    LKJ16("16", "LKJ-16表", Lkj16.class,
            "forward:/backstage/lkj16?pageSize=20",
            "forward:/backstage/lkj16/info",
            "forward:/backstage/lkj16/approveFlowInfo") {
        @Override
        public void createDataLines(DataApproveFlow dataApproveFlow, List<?> rawDataLines) {
            Lkj16Service lkj16Service = (Lkj16Service) SpringContextUtil.getBean("lkj16Service");

            List<Lkj16> rawLkj16s = new ArrayList<>();
            for (Object rawDataLine : rawDataLines)
                rawLkj16s.add((Lkj16) rawDataLine);

            lkj16Service.createLkjDataLine(dataApproveFlow, rawLkj16s);
        }

        @Override
        public void completeDataLines(DataApproveFlow dataApproveFlow) {
            Lkj16Service lkj16Service = (Lkj16Service) SpringContextUtil.getBean("lkj16Service");
            lkj16Service.completeLkjDataLine(dataApproveFlow);
        }

        @Override
        public void refuseDataLines(DataApproveFlow dataApproveFlow) {

        }
    },
    LKJ18("18", "LKJ-18表", Lkj18.class,
            "forward:/backstage/lkj18?pageSize=20",
            "forward:/backstage/lkj18/info",
            "forward:/backstage/lkj18/approveFlowInfo") {
        @Override
        public void createDataLines(DataApproveFlow dataApproveFlow, List<?> rawDataLines) {
            Lkj18Service lkj18Service = (Lkj18Service) SpringContextUtil.getBean("lkj18Service");

            List<Lkj18> rawLkj18s = new ArrayList<>();
            for (Object rawDataLine : rawDataLines)
                rawLkj18s.add((Lkj18) rawDataLine);

            lkj18Service.createLkjDataLine(dataApproveFlow, rawLkj18s);
        }

        @Override
        public void completeDataLines(DataApproveFlow dataApproveFlow) {
            Lkj18Service lkj18Service = (Lkj18Service) SpringContextUtil.getBean("lkj18Service");
            lkj18Service.completeLkjDataLine(dataApproveFlow);
        }

        @Override
        public void refuseDataLines(DataApproveFlow dataApproveFlow) {

        }
    },
    LKJ20("20", "LKJ-20表",
            Lkj20.class,
            "forward:/backstage/lkj20?pageSize=20",
            "forward:/backstage/lkj20/info",
            "forward:/backstage/lkj20/approveFlowInfo") {
        @Override
        public void createDataLines(DataApproveFlow dataApproveFlow, List<?> rawDataLines) {
            Lkj20Service lkj20Service = (Lkj20Service) SpringContextUtil.getBean("lkj20Service");

            List<Lkj20> rawLkj20s = new ArrayList<>();
            for (Object rawDataLine : rawDataLines)
                rawLkj20s.add((Lkj20) rawDataLine);

            lkj20Service.createLkjDataLine(dataApproveFlow, rawLkj20s);
        }

        @Override
        public void completeDataLines(DataApproveFlow dataApproveFlow) {
            Lkj20Service lkj20Service = (Lkj20Service) SpringContextUtil.getBean("lkj20Service");
            lkj20Service.completeLkjDataLine(dataApproveFlow);
        }

        @Override
        public void refuseDataLines(DataApproveFlow dataApproveFlow) {

        }
    }
//    ,
//    LKJ21("21", "LKJ-21表",
//            Lkj21.class,
//            "forward:/backstage/lkj21?pageSize=20",
//            "forward:/backstage/lkj21/info",
//            "forward:/backstage/lkj21/approveFlowInfo") {
//        @Override
//        public void createDataLines(DataApproveFlow dataApproveFlow, List<?> rawDataLines) {
//
//        }
//
//        @Override
//        public void completeDataLines(DataApproveFlow dataApproveFlow) {
//
//        }
//
//        @Override
//        public void refuseDataLines(DataApproveFlow dataApproveFlow) {
//
//        }
//    }
    ;

    public static final String INFO_PAGE_ROUTING_URL = "/backstage/approveData/infoPageRouting";
    public static final String VERSION_PAGE_ROUTING_URL = "/backstage/approveData/versionRouting";

    private String dataTypeId;
    private String dataTypeName;
    private Class<?> dataClass;
    private String versionRouting; // 版本管理页
    private String infoPageRouting; // 信息详情页
    private String approveFlowInfoRouting; // 审批详情页

    ApproveDataType(String dataTypeId, String dataTypeName,
                    Class<?> dataClass,
                    String versionRouting, String infoPageRouting, String approveFlowInfoRouting) {
        this.dataTypeId = dataTypeId;
        this.dataTypeName = dataTypeName;
        this.dataClass = dataClass;
        this.versionRouting = versionRouting;
        this.infoPageRouting = infoPageRouting;
        this.approveFlowInfoRouting = approveFlowInfoRouting;
    }

    /**
     * 生成审批数据
     */
    public abstract void createDataLines(DataApproveFlow dataApproveFlow, List<?> rawDataLines);

    /**
     * 提交审批流下的数据为已完成
     */
    public abstract void completeDataLines(DataApproveFlow dataApproveFlow);

    /**
     * 拒绝审批数据
     */
    public abstract void refuseDataLines(DataApproveFlow dataApproveFlow);

    public static ApproveDataType getById(String dataTypeId) {
        return Arrays.stream(values()).filter(x -> dataTypeId.equals(x.getDataTypeId())).reduce(null, (x, y) -> y);
    }

    public static ApproveDataType getByName(String dataTypeName) {
        return Arrays.stream(values()).filter(x -> dataTypeName.equals(x.getDataTypeName())).reduce(null, (x, y) -> y);
    }

    public static void setComponentsAttributes(HttpServletRequest req, Map<String, Object> attributes, String routingUrl) {
        String dataTypeId = req.getParameter("dataTypeId");

        req.setAttribute("dataTypeId", dataTypeId);
        req.setAttribute("routingUrl", routingUrl);
        req.setAttribute("approveDataTypes", ApproveDataType.values());
    }

    public String getDataTypeId() {
        return dataTypeId;
    }

    public String getDataTypeName() {
        return dataTypeName;
    }

    public Class<?> getDataClass() {
        return dataClass;
    }

    public String getApproveFlowInfoRouting() {
        return approveFlowInfoRouting;
    }

    public String getVersionRouting() {
        return versionRouting;
    }

    public String getInfoPageRouting() {
        return infoPageRouting;
    }
}
