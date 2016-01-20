package com.neutrine.twitteranalyser.consumer;

import com.google.gson.*;
import com.neutrine.twitteranalyser.Configuration;
import com.neutrine.twitteranalyser.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.json.GsonFactoryBean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by lpicanco on 20/01/16.
 */
@Component
public class TwittConsumer extends BaseConsumer {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private Configuration config;

    @Override
    protected void process(Message<?> message) {
        String json = new String((byte[]) message.getPayload());
        JsonObject jsonObj = new JsonParser().parse(json).getAsJsonObject();

        String twitt = jsonObj.get("text").getAsString();

        Arrays.stream(twitt.split(" ")).forEach((word) ->
                        kafkaProducer.process(config.getKafkaTwitterWordTopic(), word.trim())
        );

        log.info("TwitterConsumer[text]: )" + twitt);
    }
}
