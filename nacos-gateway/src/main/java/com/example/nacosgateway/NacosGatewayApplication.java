package com.example.nacosgateway;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import javax.xml.stream.events.Namespace;


@EnableDiscoveryClient
@SpringBootApplication
@NacosPropertySource(dataId = "gateway-application.yml", autoRefreshed = true, type = ConfigType.YAML, properties = @NacosProperties(namespace = "b258991f-3fb4-43b8-9dd1-0c6c04620f03"))
@NacosPropertySource(dataId = "gateway-bootstrap.yml", autoRefreshed = true, type = ConfigType.YAML, properties = @NacosProperties(namespace = "b258991f-3fb4-43b8-9dd1-0c6c04620f03"))
@RefreshScope
public class NacosGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosGatewayApplication.class, args);
    }

}
