/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplehttpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;

/**
 *
 * @author arlan
 */
public class SimpleHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        SimpleExchange e = new SimpleExchange(he);
        switch (he.getRequestMethod()) {
            case "GET":
                this.onGET(e);
                break;
            case "POST":
                this.onPOST(e);
                break;
            case "DELETE":
                this.onDELETE(e);
                break;
            case "PUT":
                this.onPUT(e);
                break;
        }
    }
    
    public void onGET (SimpleExchange e) throws IOException {
        e.sendResponse("Method not allowed", 405);
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public void onPOST (SimpleExchange e) throws IOException {
        e.sendResponse("Method not allowed", 405);
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public void onDELETE (SimpleExchange e) throws IOException {
        e.sendResponse("Method not allowed", 405);
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public void onPUT (SimpleExchange e) throws IOException {
        e.sendResponse("Method not allowed", 405);
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
