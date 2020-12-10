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
public class SimpleRedirectHandler implements HttpHandler {

    String destiny = null;
    int redirect_code = 307;
    
    public SimpleRedirectHandler (String destiny) {
        this.destiny = destiny;
    }
    
    public SimpleRedirectHandler (String destiny, int code) {
        this.destiny = destiny;
        this.redirect_code = code;
    }
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        SimpleExchange ce = new SimpleExchange(exchange);
        ce.setHeader("Location", this.destiny);
        ce.sendResponse("", this.redirect_code);
    }
    
}
