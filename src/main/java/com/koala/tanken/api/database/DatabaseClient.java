package com.koala.tanken.api.database;

import com.amazonaws.geo.GeoDataManager;
import com.amazonaws.geo.GeoDataManagerConfiguration;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class DatabaseClient {

    private static AmazonDynamoDBClient amazonDynamoDBClient;

    static {
        amazonDynamoDBClient = new AmazonDynamoDBClient();
        amazonDynamoDBClient.setRegion(Region.getRegion(Regions.US_WEST_2));
        //amazonDynamoDBClient.setEndpoint("http://localhost:8000");
    }

    public static GeoDataManager setConfiguration(String tableName) {
        GeoDataManagerConfiguration config = new GeoDataManagerConfiguration(amazonDynamoDBClient, tableName)
                .withHashKeyAttributeName("stationHashKey")
                .withRangeKeyAttributeName("stationId")
                .withGeohashAttributeName("stationGeohash")
                .withGeoJsonAttributeName("stationGeoJson")
                .withGeohashIndexName("station-geohash-index")
                .withHashKeyLength(6);
        return new GeoDataManager(config);
    }
}
