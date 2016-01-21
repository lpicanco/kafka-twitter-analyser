package com.neutrine.twitteranalyser.storm.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lpicanco on 1/20/16.
 */
public class WordFilterBolt extends BaseBasicBolt {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        String text = tuple.getString(0);

        log.info("Filtering: " + text);

        collector.emit(new Values(text));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
