package com.cloud.demo.item.pojo;

import java.util.List;

public class Goods extends Spu {
    private String cname;// 分类名称
    private String bname;// 品牌名称
    private SpuDetail spuDetail;// 商品详情
    private List<Sku> skus;// SKU列表

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }
}
