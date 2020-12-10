/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplehttpserver.examples.handlers;

import java.io.IOException;
import simplehttpserver.SimpleExchange;
import simplehttpserver.SimpleHandler;

/**
 *
 * @author arlan
 */
public class NamedRouteParamHandler extends SimpleHandler {

    @Override
    public void onGET(SimpleExchange e) throws IOException {
        // You can retrieve the param with named group at router Regular Expression
        String id = e.getRouteParam("id");
        e.sendResponse("Parsed id: "+id);
    }
    
}
