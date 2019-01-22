package com.cloud.demo.item.service.dao;

import com.cloud.demo.item.pojo.Category;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

@Component("categoryDao")
public interface CategoryDao extends Mapper<Category>,SelectByIdListMapper<Category,Long> {
}
