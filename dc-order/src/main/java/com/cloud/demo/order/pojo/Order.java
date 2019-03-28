package com.cloud.demo.order.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.List;

@Table(name = "tb_order")
public class Order {
    @Id
    private Long orderId;// id
    @NotNull
    private Long totalPay;// 总金额
    @NotNull
    private Long actualPay;// 实付金额
    @NotNull
    private Integer paymentType;// 支付类型，1、在线支付，2、货到付款

    private Long userId;// 用户id

    @Transient
    private List<OrderDetail> orderDetails;

    @Transient
    private Integer status;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(Long totalPay) {
        this.totalPay = totalPay;
    }

    public Long getActualPay() {
        return actualPay;
    }

    public void setActualPay(Long actualPay) {
        this.actualPay = actualPay;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
