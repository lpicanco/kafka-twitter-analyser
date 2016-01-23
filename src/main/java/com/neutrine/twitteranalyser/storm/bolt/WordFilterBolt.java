package com.neutrine.twitteranalyser.storm.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import org.apache.commons.lang.StringUtils;
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

        if (StringUtils.startsWith(text, "@") || StringUtils.trim(text).length() <= 4) {
            log.debug("Filtering: " + text);
        } else {
            collector.emit(new Values(text));
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("word"));
    }
}
