package com.cloud.demo.item.service.service.impl;

import com.cloud.demo.item.pojo.Category;
import com.cloud.demo.item.service.dao.CategoryDao;
import com.cloud.demo.item.service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    @Autowired
    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<String> queryNameByIds(List<Long> ids) {
        return categoryDao.selectByIdList(ids).stream().map(Category::getName).collect(Collectors.toList());
    }
}
