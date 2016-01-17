package com.neutrine.twitteranalyser;

/**
 * Created by lpicanco on 16/01/16.
 */
public interface MessageProcessor<T> {
    void process(T message);
    void close();
}
