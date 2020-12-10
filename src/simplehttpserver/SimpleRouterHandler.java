/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplehttpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arlan
 */
public class SimpleRouterHandler implements HttpHandler {
    
    private final HashMap<String, HttpHandler> rotas = new HashMap<>();
    
    public void addRoute (String rota, HttpHandler handler) {
        this.rotas.put(rota, handler);
    }
    
    public String[] getRoutes () {
        System.out.println(this.rotas.keySet());
        return null;
        
//        return (String[]) this.rotas.keySet().toArray();
    }
    
    @Override
    public void handle(HttpExchange e) {
        try {
            String uri = e.getRequestURI().getPath();
            for (String rota : rotas.keySet()) {
                String regex = "^"+rota+(rota.endsWith("/") ? "" : "\\/?")+"$";
                if (uri.matches(regex)) {
                    e.setAttribute("regex_rota", regex);
                    rotas.get(rota).handle(e);
                    return;
                }
            }
            System.out.println("Rota não encontrada: "+uri);
            SimpleExchange ce = new SimpleExchange(e);
            ce.sendResponse("URL não encontrada!", 404);
        } catch (IOException ex) {
            Logger.getLogger(SimpleRouterHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
