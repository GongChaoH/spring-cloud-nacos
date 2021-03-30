package com.example.nacosgateway;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

@Slf4j
@Component
@NacosPropertySource(dataId = "nacos-gateway-json", autoRefreshed = true, type = ConfigType.JSON, properties = @NacosProperties(namespace = "b258991f-3fb4-43b8-9dd1-0c6c04620f03"))
public class NacosGatewayDefineConfig implements CommandLineRunner {
    private Logger log = LoggerFactory.getLogger(NacosGatewayDefineConfig.class);

    @Value("127.0.0.1:8848")
    String serverAddr;

    @Value("b258991f-3fb4-43b8-9dd1-0c6c04620f03")
    String namespace;

    @Value("nacos-gateway-json")
    private String dataId;

    @Value("DEFAULT_GROUP")
    private String group;

    @Autowired
    NacosDynamicRouteService nacosDynamicRouteService;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        addRouteNacosListen();
    }

    /**
     * 添加动态路由监听器
     */
    private void addRouteNacosListen() {
        try {
            Properties properties =new Properties();
            properties.put("serverAddr",serverAddr);
            properties.put("namespace",namespace);

            //获取配置
            ConfigService configService = NacosFactory.createConfigService(properties);

            String configInfo = configService.getConfig(dataId, group, 5000);
            log.info("从Nacos返回的配置：" + configInfo);
            getNacosDataRoutes(configInfo);
            //注册Nacos配置更新监听器，用于监听触发
            configService.addListener(dataId, group, new Listener()  {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("Nacos更新了！");
                    log.info("接收到数据:"+configInfo);
                    getNacosDataRoutes(configInfo);
                }
                @Override
                public Executor getExecutor() {
                    return null;
                }
            });

        } catch (NacosException e) {
            log.error("nacos-addListener-error", e);
            e.printStackTrace();
        }
    }

    /**
     * 从Nacos返回的配置
     * @param configInfo
     */
    private void getNacosDataRoutes(String configInfo) {
        List<RouteDefinition> list = JSON.parseArray(configInfo, RouteDefinition.class);
        list.stream().forEach(definition -> {
            log.info(""+JSON.toJSONString(definition));
            nacosDynamicRouteService.update(definition);
        });
    }
}
