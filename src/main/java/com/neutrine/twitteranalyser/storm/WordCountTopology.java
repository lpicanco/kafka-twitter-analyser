package com.neutrine.twitteranalyser.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.AuthorizationException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import com.neutrine.twitteranalyser.Configuration;
import com.neutrine.twitteranalyser.storm.bolt.WordFilterBolt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;

import java.util.Map;

/**
 * Created by lpicanco on 1/20/16.
 */
@Component
public class WordCountTopology {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Configuration config;

    public void execute() throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {

        //StormSubmitter.submitTopology("word-count-analysis", createConfig(), createTopology());
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("word-count-analysis", createConfig(), createTopology());
    }

    private StormTopology createTopology() {
        SpoutConfig kafkaConf = new SpoutConfig(new ZkHosts(config.getZookeeperConnect()),
                config.getKafkaTwitterWordTopic(), "/kafka", "KafkaSpoutTwitterWord");

        kafkaConf.scheme = new SchemeAsMultiScheme(new StringScheme());
        TopologyBuilder topology = new TopologyBuilder();

        topology.setSpout("kafka_spout", new KafkaSpout(kafkaConf), 1);

        topology.setBolt("word_filter", new WordFilterBolt(), 1)
                .shuffleGrouping("kafka_spout");

        return topology.createTopology();
    }

    private Config createConfig() {
        Config conf = new Config();
        conf.setDebug(true);
        conf.setNumWorkers(1);
        return conf;
    }
}