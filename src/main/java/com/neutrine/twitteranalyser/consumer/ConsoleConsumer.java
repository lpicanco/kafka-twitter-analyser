package com.neutrine.twitteranalyser.consumer;

import com.neutrine.twitteranalyser.MessageProcessor;
import org.springframework.stereotype.Service;

/**
 * Created by lpicanco on 16/01/16.
 */
@Service
public class ConsoleConsumer implements MessageProcessor<String> {
    @Override
    public void process(String message) {
        System.out.println("Message: " + message);
    }

    @Override
    public void close() {
        System.out.println("Closed");
    }
}
