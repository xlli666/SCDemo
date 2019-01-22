package com.cloud.demo.item.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/item/category")
public interface CategoryApi {
    @RequestMapping("/names")
    List<String> queryCategoryNamesByIds(@RequestParam("ids") List<Long> ids); // 该接口以及对应的controller有参数时必须添加@RequestParam注解
}
