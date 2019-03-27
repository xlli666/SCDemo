package com.cloud.demo.gw.config;

import com.cloud.demo.common.utils.ConstantUtils;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class DocumentationConfig implements SwaggerResourcesProvider {

    private final RouteLocator routeLocator;

    public DocumentationConfig (RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<Route> routes = routeLocator.getRoutes();
        // 服务网关对应的服务启动，会出现重复的服务，如何去重？
        // 如何去掉不需要展示接口的服务？
        for (Route route : routes) {
            resources.add(swaggerResource(route.getId(),
                    route.getFullPath().replace("**", ConstantUtils.SWG_SUFFIX)));
        }
        return resources;
    }

    private SwaggerResource swaggerResource (String name, String location) {
        SwaggerResource resource = new SwaggerResource();
        resource.setName(name);
        resource.setLocation(location);
        resource.setSwaggerVersion(ConstantUtils.SWG_VERSION);
        return resource;
    }
}
