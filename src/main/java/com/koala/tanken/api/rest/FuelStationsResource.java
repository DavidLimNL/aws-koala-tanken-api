package com.koala.tanken.api.rest;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.koala.tanken.api.controller.FuelStationsController;
import com.koala.tanken.api.model.Input;
import com.koala.tanken.api.util.JacksonConverterException;
import com.koala.tanken.api.util.JacksonConverterImpl;

import java.util.List;
import java.util.Map;

public class FuelStationsResource implements RequestHandler<Input, String> {

    public String handleRequest(Input input, Context context) {

	    JsonNode response;

        FuelStationsController controller = new FuelStationsController();
        List<Map<String, AttributeValue>> itemList = controller.findStations(input.getLocation(), input.getDistance());

        try {
            response = new JacksonConverterImpl().itemListToJsonArray(itemList);
        } catch (JacksonConverterException e) {
            return null;
        }

		return response.toString();
	}
}
