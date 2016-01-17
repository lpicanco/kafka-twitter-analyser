package com.neutrine.twitteranalyser;

import com.neutrine.twitteranalyser.consumer.ConsoleConsumer;
import com.neutrine.twitteranalyser.consumer.KafkaConsumer;
import com.neutrine.twitteranalyser.producer.TwitterProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TwitterKafkaAnalyserApplication implements CommandLineRunner {

    @Autowired
    private TwitterProducer twitterProducer;

    @Autowired
    private ConsoleConsumer consoleMessageProcessor;

    @Autowired
    private KafkaConsumer kafkaConsumer;

    public static void main(String[] args) {
        SpringApplication.run(TwitterKafkaAnalyserApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        twitterProducer.execute(kafkaConsumer);
    }
}
