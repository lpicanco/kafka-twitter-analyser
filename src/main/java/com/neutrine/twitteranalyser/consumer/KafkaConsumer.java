package com.neutrine.twitteranalyser.consumer;

import com.neutrine.twitteranalyser.Configuration;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lpicanco on 19/01/16.
 */
@Service
public class KafkaConsumer {

    private ExecutorService executor;

    private Configuration config;

    private ConsumerConnector consumer;

    @Autowired
    public KafkaConsumer(Configuration config) {
        this.config = config;

        Properties properties = new Properties();
        properties.put("zookeeper.connect", config.getZookeeperConnect());
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        properties.put("group.id", "123");
        properties.put("client.id", "kafka-twitter-analyser-consumerr");
        ConsumerConfig consumerConfig = new ConsumerConfig(properties);
        consumer = Consumer.createJavaConsumerConnector(consumerConfig);
    }

    public void execute() {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(config.getKafkaTopic(), 1);
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(config.getKafkaTopic());

        System.out.println( "streams.size = " + streams.size() );

        executor = Executors.newFixedThreadPool(1);

        for (final KafkaStream stream: streams) {
            ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
            while(iterator.hasNext()) {
                System.out.println("Consumer: " + new String(iterator.next().message()));
            }
        }
    }

    public void close() {
        consumer.shutdown();
    }
}
