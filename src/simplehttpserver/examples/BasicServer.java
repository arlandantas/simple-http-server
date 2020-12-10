/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplehttpserver.examples;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import simplehttpserver.*;
import simplehttpserver.examples.handlers.Handler01;

/**
 *
 * @author arlan
 */
public class BasicServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            SimpleHTTPServer server = new SimpleHTTPServer(8000);
            server.addRoute("/", new SimpleRedirectHandler("/index.html"));
            server.addRoute("/", new Handler01());
            server.start();
        } catch (IOException ex) {
            Logger.getLogger(BasicServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BasicServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
