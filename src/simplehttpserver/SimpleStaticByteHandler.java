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
public class SimpleStaticByteHandler implements HttpHandler {
    
    private final byte[] data;
    private final String contentType;
    
    public SimpleStaticByteHandler (byte[] data) {
        this.data = data;
        this.contentType = null;
    }
    
    public SimpleStaticByteHandler (byte[] data, String contentType) {
        this.data = data;
        this.contentType = contentType;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (this.contentType != null) {
            exchange.getResponseHeaders().add("Content-Type", contentType);
        }
        exchange.sendResponseHeaders(200, this.data.length);
        exchange.getResponseBody().write(this.data);
        exchange.close();
    }
}
