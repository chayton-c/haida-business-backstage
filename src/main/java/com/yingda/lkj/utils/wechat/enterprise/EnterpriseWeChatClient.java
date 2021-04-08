package com.yingda.lkj.utils.wechat.enterprise;

import com.yingda.lkj.beans.system.Json;
import com.yingda.lkj.beans.system.JsonMessage;
import com.yingda.lkj.controller.system.TestController;
import com.yingda.lkj.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public class EnterpriseWeChatClient {

    private static String accessToken = "";

    public static void getAccessToken() {
        try {
            URL url = new URL("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=wwe591ecb89c96c931&corpsecret" +
                    "=IfyZyG7MAu3CCUVMHOgiWDMwaNhvbFVBblrTsgsj41Y");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            String input = new String(con.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            WeChatMessage parse = JsonUtils.parse(input, WeChatMessage.class);
            String access_token = parse.getAccess_token();
            accessToken = access_token;
            System.out.println(access_token);
        } catch (Exception e) {
            log.error("EnterpriseWeChatClient.getAccessToken()", e);
        }
    }

    public static void sendMessage(String message) {
        try {
            getAccessToken();
            URL url = new URL("https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + accessToken);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.getOutputStream().write(String.format(
                """
                {
                   "touser" : "SunZan",
                   "msgtype" : "text",
                   "agentid" : 1000002,
                   "text" : {
                       "content" : "%s"
                   },
                   "safe":0,
                }
                """, message).getBytes());

            String input = new String(con.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println(input);
        } catch (Exception e) {
            log.error("EnterpriseWeChatClient.sendMessage()", e);
        }
    }

    public static String uploadFile() {
        getAccessToken();
        String filePath = "C://Users/goubi/Desktop/bd61fb2c0f03973c046a109db103877.png";
        String fileName = Arrays.stream(filePath.split("/")).reduce("", (x, y) -> y);
        System.out.println(fileName);
        File file = new File(filePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setContentLength(file.length());
        headers.setContentDispositionFormData("media", file.getName());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("content-type", "application/octet-stream");
        body.add("filename", fileName);
        body.add("name", "media");
        body.add("filelength", file.length());
        body.add("file", new FileSystemResource(file.getPath()));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String serverUrl = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=" + accessToken + "&type=file";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
        System.out.println(response);
        WeChatMessage weChatMessage = JsonUtils.parse(response.getBody(), WeChatMessage.class);
        return weChatMessage.getMedia_id();
    }

    public static void sendFile(String mediaId) {
        try {
            getAccessToken();
            URL url = new URL("https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + accessToken);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.getOutputStream().write(String.format("""
                    {
                       "touser" : "SunZan",
                       "msgtype" : "file",
                       "agentid" : 1000002,
                       "file" : {
                        "media_id" : "%s"
                       },
                       "safe":0,
                    }
                    """, mediaId).getBytes());

            String input = new String(con.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("EnterpriseWeChatClient.sendMessage()", e);
        }
    }

    private static class WeChatMessage {
        private String access_token;
        private String media_id;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getMedia_id() {
            return media_id;
        }

        public void setMedia_id(String media_id) {
            this.media_id = media_id;
        }
    }
}
