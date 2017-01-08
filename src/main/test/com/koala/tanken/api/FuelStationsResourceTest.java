package com.koala.tanken.api;

import com.koala.tanken.api.model.Input;
import com.koala.tanken.api.rest.FuelStationsResource;

import org.junit.Assert;
import org.junit.Test;

public class FuelStationsResourceTest {

    @Test
    public void successRequest() {
        // setup
        FuelStationsResource resource = new FuelStationsResource();
        Input input = new Input();
        input.setLocation("52.091167,4.342671");
        input.setDistance("3");

        // execute
        String node = resource.handleRequest(input, null);

        // assert
        System.out.println(node);
        Assert.assertNotNull(node);
    }
}
