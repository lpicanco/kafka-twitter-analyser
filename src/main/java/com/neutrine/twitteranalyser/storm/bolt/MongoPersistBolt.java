package com.neutrine.twitteranalyser.storm.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

/**
 * Created by lpicanco on 1/20/16.
 */
public class MongoPersistBolt extends BaseBasicBolt {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static DBCollection dbCollection;

    public MongoPersistBolt() throws UnknownHostException {
        MongoClient mongoClient = new MongoClient("taurus.lan.luizpicanco.com", 27017);
        dbCollection = mongoClient.getDB("twitter").getCollection("word_count");
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        String text = tuple.getString(0);
        Long count = tuple.getLong(1);

        BasicDBObject doc = new BasicDBObject()
                .append("word", text)
                .append("count", count);

        log.debug("Inserting: " + doc.toString());

        BasicDBObject searchQuery = new BasicDBObject().append("word", text);
        dbCollection.update(searchQuery, doc, true, true);

        collector.emit(new Values(text));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("word"));
    }
}
