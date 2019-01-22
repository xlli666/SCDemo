package com.cloud.demo.item.service.service;

import com.cloud.demo.item.pojo.Goods;
import com.cloud.demo.item.pojo.Sku;
import com.cloud.demo.item.pojo.SpuDetail;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface GoodsService {
    PageInfo<Goods> querySpuByPageAndSort(int pageNum, int pageSize, Boolean saleable, String keyWord);
    void saveGoods(Goods goods);
    SpuDetail querySpuDetailById(Long spuId);
    List<Sku> querySkuBySpuId(Long spuId);
}
