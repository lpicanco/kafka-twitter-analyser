package com.neutrine.twitteranalyser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

/**
 * Created by lpicanco on 19/01/16.
 */
@Component
@EnableAutoConfiguration
public class Configuration {
    @Value("${twitter.consumerKey}")
    private String consumerKey;

    @Value("${twitter.consumerSecret}")
    private String consumerSecret;

    @Value("${twitter.token}")
    private String token;

    @Value("${twitter.tokenSecret}")
    private String tokenSecret;

    @Value("${kafka.topic}")
    private String kafkaTopic;

    @Value("${kafka.twitter.word.topic}")
    private String kafkaTwitterWordTopic;


    @Value("${zookeeper.connect}")
    private String zookeeperConnect;

    @Value("${kafka.broker.list}")
    private String kafkaBrokerList;

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public String getKafkaTopic() {
        return kafkaTopic;
    }

    public void setKafkaTopic(String kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
    }
    public String getKafkaBrokerList() {
        return kafkaBrokerList;
    }

    public void setKafkaBrokerList(String kafkaBrokerList) {
        this.kafkaBrokerList = kafkaBrokerList;
    }

    public String getZookeeperConnect() {
        return zookeeperConnect;
    }

    public void setZookeeperConnect(String zookeeperConnect) {
        this.zookeeperConnect = zookeeperConnect;
    }

    public String getKafkaTwitterWordTopic() {
        return kafkaTwitterWordTopic;
    }

    public void setKafkaTwitterWordTopic(String kafkaTwitterWordTopic) {
        this.kafkaTwitterWordTopic = kafkaTwitterWordTopic;
    }
}
