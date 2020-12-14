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
public class BasicHandler extends SimpleHandler {
    @Override
    public void onGET(SimpleExchange e) throws IOException {
        e.sendResponse("That is a GET request!");
    }

    @Override
    public void onPOST(SimpleExchange e) throws IOException {
//        String type = e.getExchange().getRequestHeaders().getFirst("Content-Type");
//        String ret = type+"\n$$$\n";
//        for (String k : e.getInputs().keySet()) {
//            ret += k+": "+e.getInput(k)+"\n";
//        }
//        e.sendResponse(ret);
        e.sendResponse(e.getRawBody());
    }

    @Override
    public void onPUT(SimpleExchange e) throws IOException {
        e.sendResponse("That is a PUT request!");
    }

    @Override
    public void onDELETE(SimpleExchange e) throws IOException {
        e.sendResponse("That is a DELETE request!");
    }
    
}
