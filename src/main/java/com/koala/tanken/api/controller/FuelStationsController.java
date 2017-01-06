package com.koala.tanken.api.controller;

import com.amazonaws.geo.GeoDataManager;
import com.amazonaws.geo.model.GeoPoint;
import com.amazonaws.geo.model.QueryRadiusRequest;
import com.amazonaws.geo.model.QueryRadiusResult;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.List;
import java.util.Map;

import static com.koala.tanken.api.database.DatabaseClient.setConfiguration;

public class FuelStationsController {

    private static GeoDataManager geoDataManager;

    static {
        final String stationsTableName = "Stations";
        geoDataManager = setConfiguration(stationsTableName);
    }

    /**
     * Returns the stations within a set distance of the set coordinates
     */
    public List<Map<String, AttributeValue>> findStations(String point, String radiusInKM) {

        String[] location = point.split(",");
        GeoPoint centerPoint = new GeoPoint(Double.parseDouble(location[0]), Double.parseDouble(location[1]));

        QueryRadiusRequest queryRadiusRequest =
                new QueryRadiusRequest(centerPoint, Double.parseDouble(radiusInKM) * 1000);
        QueryRadiusResult queryRadiusResult = geoDataManager.queryRadius(queryRadiusRequest);

        return queryRadiusResult.getItem();
    }

}
