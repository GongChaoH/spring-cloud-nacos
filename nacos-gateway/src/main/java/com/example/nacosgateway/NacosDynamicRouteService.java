package com.example.nacosgateway;

import org.springframework.cloud.gateway.route.RouteDefinition;

/**
 * 更新内存中的路由信息
 */
public interface NacosDynamicRouteService {

    /**
     * 更新路由信息
     * @param gatewayDefine
     * @return
     * @throws Exception
     */
    String update(RouteDefinition gatewayDefine);
}
