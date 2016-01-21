package com.neutrine.twitteranalyser.storm.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class WordCounterBolt extends BaseBasicBolt {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static Map<String, Long> wordCountList = new HashMap<>();

    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        String text = tuple.getString(0);

        long count = wordCountList.getOrDefault(text, 0L) + 1;
        wordCountList.put(text, count);

        log.debug("Word: " + text + " - Count: " + count);
        collector.emit(new Values(text, count));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("word", "count"));
    }
}
