package com.neutrine.twitteranalyser.producer;

import com.neutrine.twitteranalyser.Configuration;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * Created by lpicanco on 16/01/16.
 */
@Service
@Scope("prototype")
public class KafkaProducer implements MessageProducer<String> {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Configuration config;

    private kafka.javaapi.producer.Producer<String, String> producer;

    @Autowired
    public KafkaProducer(Configuration config) {
        this.config = config;

        Properties properties = new Properties();
        properties.put("metadata.broker.list", config.getKafkaBrokerList());
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        properties.put("client.id", "kafka-twitter-analyser-producer" + System.currentTimeMillis());
        ProducerConfig producerConfig = new ProducerConfig(properties);
        producer = new kafka.javaapi.producer.Producer<>(producerConfig);
    }

    @Override
    public void process(String topic, String messageToProcess) {
        log.debug("Processing: " + messageToProcess);
        KeyedMessage<String, String> message = new KeyedMessage<>(topic, messageToProcess);
        producer.send(message);
    }

    @Override
    public void close() {
        producer.close();
    }
}
