package com.neutrine.twitteranalyser.producer;

import com.neutrine.twitteranalyser.MessageProcessor;
import org.springframework.stereotype.Service;

/**
 * Created by lpicanco on 16/01/16.
 */
@Service
public class ConsoleProducer implements MessageProcessor<String> {
    @Override
    public void process(String message) {
        System.out.println("Message: " + message);
    }

    @Override
    public void close() {
        System.out.println("Closed");
    }
}
