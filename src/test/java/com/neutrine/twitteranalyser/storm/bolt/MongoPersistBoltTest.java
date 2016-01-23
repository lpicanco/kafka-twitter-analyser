package com.neutrine.twitteranalyser.storm.bolt;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import org.junit.Ignore;
import org.junit.Test;

import java.net.UnknownHostException;

import static org.junit.Assert.*;

/**
 * Created by lpicanco on 22/01/16.
 */
@Ignore
public class MongoPersistBoltTest {

    @Test
    public void test() throws UnknownHostException {
        //MongoCredential credential = MongoCredential.createCredential("admin", null, "admin".toCharArray());
        MongoClient mongoClient = new MongoClient("taurus.lan.luizpicanco.com", 27017);
        DBCollection dbCollection = mongoClient.getDB("twitter").getCollection("word_count");


        BasicDBObject doc = new BasicDBObject()
                .append("word", "teste")
                .append("count", "0");

        dbCollection.insert(doc);


    }
}