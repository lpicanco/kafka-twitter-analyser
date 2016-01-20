package com.neutrine.twitteranalyser;

import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.AuthorizationException;
import backtype.storm.generated.InvalidTopologyException;
import com.neutrine.twitteranalyser.adapter.TwitterAdapter;
import com.neutrine.twitteranalyser.consumer.KafkaConsumer;
import com.neutrine.twitteranalyser.producer.KafkaProducer;
import com.neutrine.twitteranalyser.storm.WordCountTopology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TwitterKafkaAnalyserApplication implements CommandLineRunner {

    @Autowired
    private TwitterAdapter twitterAdapter;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private WordCountTopology wordCountTopology;


    public static void main(String[] args) {
        SpringApplication.run(TwitterKafkaAnalyserApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    wordCountTopology.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        twitterAdapter.execute(kafkaProducer);
    }
}
