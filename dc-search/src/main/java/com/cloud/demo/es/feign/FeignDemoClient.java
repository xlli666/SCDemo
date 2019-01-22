package com.cloud.demo.es.feign;

import com.cloud.demo.item.api.FeignDemoApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(value = "item-service")
@Component("feignDemoClient") // 只用于@Autowired时不被工具标红提示，可以删除
public interface FeignDemoClient extends FeignDemoApi {
}
