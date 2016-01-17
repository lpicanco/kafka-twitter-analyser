package com.neutrine.twitteranalyser.consumer;

import com.neutrine.twitteranalyser.MessageProcessor;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;

/**
 * Created by lpicanco on 16/01/16.
 */
@Service
public class KafkaConsumer implements MessageProcessor<String> {
    private static final String topic = "twitter";

    private kafka.javaapi.producer.Producer<String, String> producer;

    public KafkaConsumer() {
        Properties properties = new Properties();
        properties.put("metadata.broker.list", "localhost:9092");
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        properties.put("client.id", "kafka-twitter-analyser");
        ProducerConfig producerConfig = new ProducerConfig(properties);
        producer = new kafka.javaapi.producer.Producer<>(producerConfig);
    }

    @Override
    public void process(String messageToProcess) {
        System.out.println("Processing: " + messageToProcess);
        KeyedMessage<String, String> message = new KeyedMessage<>(topic, messageToProcess);
        producer.send(message);
    }

    @Override
    public void close() {
        producer.close();
    }
}
