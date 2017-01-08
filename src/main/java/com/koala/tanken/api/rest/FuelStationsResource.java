package com.koala.tanken.api.rest;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koala.tanken.api.controller.FuelStationsController;
import com.koala.tanken.api.model.Input;
import com.koala.tanken.api.util.JacksonConverterException;
import com.koala.tanken.api.util.JacksonConverterImpl;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

        String result = response.toString();
        //result = StringUtils.remove(result, "\\\"");
        result = StringUtils.replace(result, "\\\"", "\"");
        result = StringUtils.replace(result, "\"{", "{");
        result = StringUtils.replace(result, "}\"", "}");
        result = StringUtils.replace(result, "\"[", "[");
        result = StringUtils.replace(result, "]\"", "]");

        ObjectMapper mapper = new ObjectMapper();
        Object json = null;
        String indented = result;
        try {
            json = mapper.readValue(result, Object.class);
            indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (IOException e) {
            log.error("Error indenting json response." + e);
        }

        return indented;
	}
}
