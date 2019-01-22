package com.cloud.demo.es.feign;

import com.cloud.demo.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(value = "item-service")
@Component("categoryClient")
public interface CategoryClient extends CategoryApi {
}
