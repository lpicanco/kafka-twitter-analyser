package com.neutrine.twitteranalyser;

import com.neutrine.twitteranalyser.adapter.TwitterProducer;
import com.neutrine.twitteranalyser.consumer.KafkaConsumer;
import com.neutrine.twitteranalyser.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TwitterKafkaAnalyserApplication implements CommandLineRunner {

    @Autowired
    private TwitterProducer twitterProducer;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private KafkaConsumer kafkaConsumer;


    public static void main(String[] args) {
        SpringApplication.run(TwitterKafkaAnalyserApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //kafkaConsumer.execute();
            }
        }).start();

        twitterProducer.execute(kafkaProducer);
    }
}
