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
public class RouteParamHandler extends SimpleHandler {

    @Override
    public void onGET(SimpleExchange e) throws IOException {
        // The couting of route params starts at 0
        String id = e.getRouteParam(0);
        e.sendResponse("Parsed id: "+id);
    }
    
}
