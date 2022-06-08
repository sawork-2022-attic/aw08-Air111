package com.micropos.delivery;

import com.micropos.dto.CartDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Consumer;

public class CartChecker implements Consumer<CartDto> {

    public static final Logger log = LoggerFactory.getLogger(CartChecker.class);

    private StreamBridge streamBridge;

    @Autowired
    public void setStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void accept(CartDto cartDto) {
        log.info("Deliver cart with ID {}", cartDto.getId());
        streamBridge.send("item-approved", message(cartDto));
    }

    private static final <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val).build();
    }
}
