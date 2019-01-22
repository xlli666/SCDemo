package com.cloud.demo.es.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class GoodsListener {

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "dc.create.index.queue", durable = "true"),
                    exchange = @Exchange(value = "dc.item.exchange",
                            ignoreDeclarationExceptions = "true",
                            type = ExchangeTypes.TOPIC),
                    key = {"item.*"}))
    public void listenerCreate(Long id) {
        if (id == null) {
            return;
        }
        System.out.println(id);
    }
}
