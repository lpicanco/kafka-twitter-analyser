package com.neutrine.twitteranalyser.consumer;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

/**
 * Created by lpicanco on 19/01/16.
 */
public abstract class BaseConsumer implements MessageChannel {


    @Override
    public boolean send(Message<?> message) {
        process(message);
        return true;
    }

    @Override
    public boolean send(Message<?> message, long l) {
        process(message);
        return true;
    }

    protected void log(String text) {
        System.out.println(text);
    }


    protected abstract void process(Message<?> message);
}
