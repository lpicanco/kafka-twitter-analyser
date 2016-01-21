package com.neutrine.twitteranalyser.consumer;

import com.neutrine.twitteranalyser.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.kafka.core.ConnectionFactory;
import org.springframework.integration.kafka.core.DefaultConnectionFactory;
import org.springframework.integration.kafka.core.ZookeeperConfiguration;
import org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter;
import org.springframework.integration.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.integration.kafka.support.ZookeeperConnect;
import org.springframework.stereotype.Component;

/**
 * Created by lpicanco on 19/01/16.
 */
@Component
public class MessageDrivenKafkaConsumer {

    @Autowired
    private TwittConsumer twitterConsumer;

    @Autowired
    private Configuration config;

    @Bean
    public org.springframework.integration.kafka.core.Configuration zkConfiguration() {
        return new ZookeeperConfiguration(new ZookeeperConnect(config.getZookeeperConnect()));
    }

    @Bean
    public ConnectionFactory kafkaConnectionFactory() {
        return new DefaultConnectionFactory(zkConfiguration());
    }

    @Bean
    public MessageProducer kafkaMessageDrivenChannelAdapter() {
        KafkaMessageDrivenChannelAdapter adapter = new KafkaMessageDrivenChannelAdapter(
                new KafkaMessageListenerContainer(kafkaConnectionFactory(), "twitter")
        );
        adapter.setOutputChannel(twitterConsumer);
        return adapter;
    }
}
