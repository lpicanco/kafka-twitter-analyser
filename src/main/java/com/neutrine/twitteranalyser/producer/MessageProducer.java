package com.neutrine.twitteranalyser.producer;

/**
 * Created by lpicanco on 16/01/16.
 */
public interface MessageProducer<T> {
    void process(String topic, T message);
    void close();
}
