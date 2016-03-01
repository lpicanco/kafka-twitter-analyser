package com.neutrine.twitteranalyser.adapter;

import com.google.common.collect.Lists;
import com.neutrine.twitteranalyser.Configuration;
import com.neutrine.twitteranalyser.producer.MessageProducer;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by lpicanco on 16/01/16.
 */
@Service
public class TwitterAdapter {
    @Autowired
    private Configuration config;

    public void execute(MessageProducer<String> messageProducer) {
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();

        String topic = "oscar"; // trump, oscar, hillary
        endpoint.trackTerms(Lists.newArrayList(topic));


        Authentication auth = new OAuth1(config.getConsumerKey(), config.getConsumerSecret(), config.getToken(),
                config.getTokenSecret());

        Client client = new ClientBuilder().hosts(Constants.STREAM_HOST)
                .endpoint(endpoint).authentication(auth)
                .processor(new StringDelimitedProcessor(queue)).build();

        client.connect();
        while (!client.isDone()) {
            try {
                String query = "{ \n" +
                        "  \"query\":\"%s\", \n" +
                        "  \"payload\": %s\n" +
                        "}";

                String json = String.format(query, topic, queue.take());
                messageProducer.process(config.getKafkaTopic(), json);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        client.stop();
        messageProducer.close();
    }
}
