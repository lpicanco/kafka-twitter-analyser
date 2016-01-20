package com.neutrine.twitteranalyser.producer;

import org.springframework.stereotype.Service;

/**
 * Created by lpicanco on 16/01/16.
 */
@Service
public class ConsoleProducer implements MessageProducer<String> {
    @Override
    public void process(String topic, String message) {
        System.out.println("Message: " + message);
    }

    @Override
    public void close() {
        System.out.println("Closed");
    }
}
