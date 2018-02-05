package io.renren.zhuoyue.config.mq;

import cn.hutool.core.lang.Console;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener
public class Consumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumeMessage(String message) {
        Console.log("consume message {}", message);
    }
}
