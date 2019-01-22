package com.cloud.demo.sms.listener;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.cloud.demo.sms.property.SmsProperties;
import com.cloud.demo.sms.util.SmsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@EnableConfigurationProperties(SmsProperties.class)
public class SmsListener {

    private final SmsUtils smsUtils;

    private final SmsProperties prop;

    @Autowired
    public SmsListener(SmsUtils smsUtils, SmsProperties prop) {
        this.smsUtils = smsUtils;
        this.prop = prop;
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "dc.sms.queue", durable = "true"),
                    exchange = @Exchange(value = "dc.sms.exchange",
                            ignoreDeclarationExceptions = "true"),
                    key = {"sms.verify.code"}))
    public void listenSms(Map<String, String> msg) throws Exception {
        if (msg == null || msg.size() <= 0) {
            // 放弃处理
            return;
        }
        String phone = msg.get("phone");
        String code = msg.get("code");

        if (StringUtils.isBlank(phone) || StringUtils.isBlank(code)) {
            // 放弃处理
            return;
        }
        // 发送消息
        SendSmsResponse resp = this.smsUtils.sendSms(phone, code,
                prop.getSignName(),
                prop.getVerifyCodeTemplate());
        if (!"OK".equals(resp.getMessage())){
            // 发送失败
            throw new RuntimeException();
        }
    }

}
