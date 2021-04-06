package com.yingda.lkj;

import com.zhoyq.server.jt808.starter.EnableJt808Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@EnableAsync
@EnableWebSocket
@EnableJt808Server
@SpringBootApplication(scanBasePackages = {"org.jeecg.modules.jmreport","com.yingda"})
public class LkjApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LkjApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(LkjApplication.class);
    }
}
