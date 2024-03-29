package com.yingda.lkj.controller.system;

import com.alibaba.druid.support.json.JSONUtils;
import com.yingda.lkj.beans.entity.backstage.line.StationRailwayLine;
import com.yingda.lkj.beans.entity.backstage.location.Location;
import com.yingda.lkj.beans.entity.backstage.opc.OpcMark;
import com.yingda.lkj.beans.entity.backstage.opc.OpcMarkType;
import com.yingda.lkj.beans.system.Json;
import com.yingda.lkj.beans.system.JsonMessage;
import com.yingda.lkj.controller.BaseController;
import com.yingda.lkj.service.backstage.constructioncontrolplan.ConstructionControlPlanService;
import com.yingda.lkj.service.backstage.constructioncontrolplan.ConstructionDailyPlanService;
import com.yingda.lkj.service.backstage.equipment.EquipmentService;
import com.yingda.lkj.service.backstage.line.RailwayLineService;
import com.yingda.lkj.service.backstage.line.StationRailwayLineService;
import com.yingda.lkj.service.backstage.line.StationService;
import com.yingda.lkj.service.backstage.location.LocationService;
import com.yingda.lkj.service.backstage.opc.OpcMarkTypeService;
import com.yingda.lkj.service.backstage.opc.OpcService;
import com.yingda.lkj.service.backstage.opc.OpcTypeService;
import com.yingda.lkj.service.backstage.organization.OrganizationClientService;
import com.yingda.lkj.service.backstage.sms.SmsService;
import com.yingda.lkj.service.base.BaseService;
import com.yingda.lkj.service.system.AuthService;
import com.yingda.lkj.service.system.MenuService;
import com.yingda.lkj.service.system.RoleService;
import com.yingda.lkj.service.system.UserService;
import com.yingda.lkj.utils.JsonUtils;
import com.yingda.lkj.utils.SpringContextUtil;
import com.yingda.lkj.utils.date.DateUtil;
import com.yingda.lkj.utils.location.LocationUtil;
import com.yingda.lkj.utils.wechat.enterprise.EnterpriseWeChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author hood  2020/5/18
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {
    @Autowired
    private BaseService<OpcMarkType> opcMarkTypeBaseService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private OrganizationClientService organizationClientService;
    @Autowired
    private StationRailwayLineService stationRailwayLineService;
    @Autowired
    private BaseService<OpcMark> opcMarkBaseService;
    @Autowired
    private BaseService<StationRailwayLine> stationRailwayLineBaseService;
    @Autowired
    private StationService stationService;
    @Autowired
    private RailwayLineService railwayLineService;
    @Autowired
    private OpcMarkTypeService opcMarkTypeService;
    @Autowired
    private OpcTypeService opcTypeService;
    @Autowired
    private ConstructionControlPlanService constructionControlPlanService;
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private BaseService<Location> locationBaseService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;

    @RequestMapping("/initMenu")
    @ResponseBody
    public Json initMenu() {
        menuService.refresh();
        return new Json(JsonMessage.SUCCESS);
    }


    @RequestMapping("/a")
    @ResponseBody
    public Json a() throws Exception {
        String latitude = req.getParameter("latitude");
        String longitude = req.getParameter("longitude");
        if (latitude == null) latitude = "44.7391";
        if (longitude == null) longitude = "122.6821";

        ConstructionDailyPlanService constructionDailyPlanService = (ConstructionDailyPlanService) SpringContextUtil.getBean(
                "constructionDailyPlanService");
        constructionDailyPlanService.checkFinishedStatus();

        EquipmentService equipmentService = (EquipmentService) SpringContextUtil.getBean("equipmentService");
        equipmentService.recordLocation("193048564711", Double.parseDouble(longitude), Double.parseDouble(latitude));
//        ConstructionControlPlanService constructionControlPlanService = (ConstructionControlPlanService) SpringContextUtil.getBean
//        ("constructionControlPlanService");
//        constructionControlPlanService.checkClose();
        return new Json(JsonMessage.SUCCESS);
    }

    @RequestMapping("/importOpcMark")
    @ResponseBody
    public Json importOpcMark() throws IOException {
        File file = new File("C://Users/goubi/Desktop/opcmark.gpx");
        FileInputStream fileInputStream = new FileInputStream(file);
        String s1 = new String(fileInputStream.readAllBytes(), StandardCharsets.UTF_8);
        String[] split = s1.split("\\r\\n");

        String opcId = "e26edcea-adea-48f8-9ef3-b863f8656c6e";

        List<Location> locations = new ArrayList<>();
        List<OpcMark> opcMarks = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            if (i % 5 != 0) continue;
            String[] split1 = s.split("\"");
            String latStr = split1[1];
            String lngStr = split1[3];

            OpcMark opcMark = new OpcMark();
            opcMark.setId(UUID.randomUUID().toString());
            opcMark.setCode(i + "");
            opcMark.setName("电缆警示标");
            opcMark.setOpcId(opcId);
            opcMark.setOpcMarkTypeId("0d65bfc5-b386-43f6-aaf3-38dce1be2c01");
            double kilometer = i * 2d;
            opcMark.setKilometerMark(kilometer > 0 ? kilometer : 0);
            opcMark.setAddTime(current());

            opcMarks.add(opcMark);
            locations.add(new Location(opcMark.getId(), Double.parseDouble(lngStr), Double.parseDouble(latStr), 0, Location.OPC_MARK));
        }

        locationBaseService.bulkInsert(locations);
        opcMarkBaseService.bulkInsert(opcMarks);

        return new Json(JsonMessage.SUCCESS);
    }

    @RequestMapping("/importOpc")
    @ResponseBody
    public Json importOpc() throws Exception {
        File file = new File("C://Users/goubi/Desktop/opc.gpx");
        FileInputStream fileInputStream = new FileInputStream(file);
        String s1 = new String(fileInputStream.readAllBytes(), StandardCharsets.UTF_8);

        String dataId = UUID.randomUUID().toString();
        List<Location> locations = new ArrayList<>();
        String[] split = s1.split("\\r\\n");
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            if (i % 4 != 0) continue;
            String[] split1 = s.split("\"");
            String latStr = split1[1];
            String lngStr = split1[3];

            locations.add(new Location(dataId, Double.parseDouble(lngStr), Double.parseDouble(latStr), i, Location.OPC));
        }

        locationBaseService.bulkInsert(locations);

        return new Json(JsonMessage.SUCCESS);
    }

    @RequestMapping("/importRailwayLine")
    @ResponseBody
    public Json importRailwayLine() throws Exception {
        File file = new File("C://Users/goubi/Desktop/新建文件夹 (3)/黎明-平房.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        String s1 = new String(fileInputStream.readAllBytes(), StandardCharsets.UTF_8);

        String dataId = UUID.randomUUID().toString();
        List<Location> locations = new ArrayList<>();
        String[] split = s1.split("\\r\\n");
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            if (i % 4 != 0) continue;
            String[] split1 = s.split("\"");
            String latStr = split1[1];
            String lngStr = split1[3];

            locations.add(new Location(dataId, Double.parseDouble(lngStr), Double.parseDouble(latStr), i, Location.RAILWAY_LINE));
        }

        locationBaseService.bulkInsert(locations);

        return new Json(JsonMessage.SUCCESS);
    }

    @RequestMapping("/nagato")
    @ResponseBody
    public Map<String, Object> nagato() throws Exception {
        Map<String, Object> attributes = new HashMap<>();

        return attributes;
    }

    @RequestMapping("/set")
    @ResponseBody
    public String HelloSpring(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return String.format("redis set成功！key=%s,value=%s", key, value);
    }

    @RequestMapping("/get")
    @ResponseBody
    public String HelloSpring(String key) {
        String value = (String) redisTemplate.opsForValue().get(key);
        return "redis get结果 value=" + value;
    }

    @RequestMapping("/akagi")
    @ResponseBody
    public Json akagi() throws Exception {
        List<Location> locations = locationBaseService.find(
                "from Location where type = 4 order by seq"
        );
        for (int i = 0; i < locations.size() - 1; i++) {
            Location location = locations.get(i);
            Location location1 = locations.get(i + 1);
            double distance = LocationUtil.getDistance(location1, location);
            System.out.println(distance);
        }
        return new Json(JsonMessage.SUCCESS);
    }

    @Autowired
    private SmsService smsService;
    @Autowired
    private OpcService opcService;
    @Autowired
    private EquipmentService equipmentService;

    @RequestMapping("/tttt")
    public Json texttttt() throws Exception {
        File file = new File("C://Users/goubi/Desktop/新建文本文档.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        String s1 = new String(fileInputStream.readAllBytes(), StandardCharsets.UTF_8);
        String[] split = s1.split("\\n");
        List<Location> locations = new ArrayList<>();
        int line = 0;
        for (String s : split) {
            String[] split1 = s.split("\\s+");
            System.out.println(line++);
            Location location = new Location();
            location.setId(split1[0]);
            location.setDataId(split1[1]);
            location.setType(Byte.parseByte(split1[2]));
            location.setLongitude(Double.parseDouble(split1[3]));
            location.setLatitude(Double.parseDouble(split1[4]));
            location.setAltitude(Double.parseDouble(split1[5]));
            location.setAddTime(DateUtil.toTimestamp(split1[6] + " " + split1[7], "yyyy-MM-dd HH:mm:ss"));
            locations.add(location);
        }
        locationBaseService.bulkInsert(locations);
        return new Json(JsonMessage.SUCCESS);
    }

    public static void main(String[] args) throws IOException {
    }

}
