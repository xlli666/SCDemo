package com.cloud.demo.item.service.service;

import java.util.List;

public interface CategoryService {
    List<String> queryNameByIds(List<Long> ids);
}
