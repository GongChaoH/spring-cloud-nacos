package com.example.nacosdocumentrelated;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@NacosPropertySource(dataId = "nacos-document-related.properties", type = ConfigType.PROPERTIES, autoRefreshed = true, properties = @NacosProperties(namespace = "b258991f-3fb4-43b8-9dd1-0c6c04620f03"))
public class NacosDocumentRelatedApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosDocumentRelatedApplication.class, args);
    }

}
