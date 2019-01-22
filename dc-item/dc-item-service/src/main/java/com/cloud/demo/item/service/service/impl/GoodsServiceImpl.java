package com.cloud.demo.item.service.service.impl;

import com.cloud.demo.item.pojo.*;
import com.cloud.demo.item.service.dao.SkuDao;
import com.cloud.demo.item.service.dao.SpuDao;
import com.cloud.demo.item.service.dao.SpuDetailDao;
import com.cloud.demo.item.service.dao.StockDao;
import com.cloud.demo.item.service.service.GoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

    private Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

    private final SpuDao spuDao;

    private final SpuDetailDao spuDetailDao;

    private final SkuDao skuDao;

    private final StockDao stockDao;

    private final AmqpTemplate amqpTemplate;

    @Autowired
    public GoodsServiceImpl(SpuDao spuDao, SpuDetailDao spuDetailDao, SkuDao skuDao, StockDao stockDao, AmqpTemplate amqpTemplate) {
        this.spuDao = spuDao;
        this.spuDetailDao = spuDetailDao;
        this.skuDao = skuDao;
        this.stockDao = stockDao;
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public PageInfo<Goods> querySpuByPageAndSort(int pageNum, int pageSize, Boolean saleable, String keyWord) {
        // 1、查询SPU
        // 分页
        PageHelper.startPage(pageNum,pageSize);
        //构建查询条件
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }
        if (StringUtils.isNotBlank(keyWord)) {
            criteria.andLike("title", "%" + keyWord + "%");
        }
        List<Spu> spuList = spuDao.selectByExample(example);
        List<Goods> goodsList = spuList.stream().map(spu -> {
            // 2、把spu变为goods
            Goods goods = new Goods();
            // 属性拷贝
            BeanUtils.copyProperties(spu, goods);
            // 3、查询spu的商品分类名称
            // 暂略
            goods.setCname("分类名称测试");
            // 4、查询spu的品牌名称
            // 暂略
            goods.setBname("品牌名称测试");
            return goods;
        }).collect(Collectors.toList());
        return new PageInfo<>(goodsList);
    }

    @Override
    @Transactional
    public void saveGoods(Goods goods) {
        // 保存spu
        goods.setSaleable(true);
        goods.setValid(true);
        goods.setCreateTime(new Date());
        goods.setLastUpdateTime(goods.getCreateTime());
        spuDao.insert(goods);
        // 保存spu详情
        goods.getSpuDetail().setSpuId(goods.getId());
        spuDetailDao.insert(goods.getSpuDetail());
        // 保存sku和库存信息
        saveSkuAndStock(goods.getSkus(), goods.getId());
        // 发送消息
        sendMessage(goods.getId(), "insert");
    }

    @Override
    public SpuDetail querySpuDetailById(Long spuId) {
        return spuDetailDao.selectByPrimaryKey(spuId);
    }

    @Override
    public List<Sku> querySkuBySpuId(Long spuId) {
        // 查询sku
        Sku record = new Sku();
        record.setSpuId(spuId);
        List<Sku> skus = skuDao.select(record);
        for (Sku sku : skus) {
            // 同时查询出库存
            sku.setStock(stockDao.selectByPrimaryKey(sku.getId()).getStock());
        }
        return skus;
    }

    private void saveSkuAndStock(List<Sku> skus, Long spuId){
        for (Sku sku : skus) {
            if (sku.getEnable()){
                // 保存sku
                sku.setSpuId(spuId);
                sku.setCreateTime(new Date());
                sku.setLastUpdateTime(sku.getCreateTime());
                skuDao.insert(sku);
                // 保存库存信息
                Stock stock = new Stock();
                stock.setSkuId(sku.getId());
                stock.setStock(sku.getStock());
                stockDao.insert(stock);
            }
        }
    }

    private void sendMessage(Long id, String type){
        try {
            amqpTemplate.convertAndSend("item" + type, id);
        } catch (Exception e) {
            logger.error("{}商品消息发送异常，商品id：{}", type, id, e);
        }
    }
}
