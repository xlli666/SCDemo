package com.cloud.demo.item.pojo;

public class SpuDetail {
    private Long spuId;// 对应的SPU的id
    private String description;// 商品描述
    private String specifications;// 商品的全局规格属性
    private String specTemplate;// 商品特殊规格的名称及可选值模板
    //private String packingList;// 包装清单
    //private String afterService;// 售后服务

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getSpecTemplate() {
        return specTemplate;
    }

    public void setSpecTemplate(String specTemplate) {
        this.specTemplate = specTemplate;
    }
}
