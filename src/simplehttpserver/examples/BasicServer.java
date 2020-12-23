/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplehttpserver.examples;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import simplehttpserver.*;
import simplehttpserver.examples.handlers.BasicHandler;
import simplehttpserver.examples.handlers.NamedRouteParamHandler;
import simplehttpserver.examples.handlers.RouteParamHandler;

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
            // Initializes a new server at port 8000
            SimpleHTTPServer server = new SimpleHTTPServer(8000);
            
            // You can also parse a path that contains static files to be served
            // SimpleHTTPServer server = new SimpleHTTPServer(8000, "/var/www/myjavaproject");
            
            // Or serve static files from a zip file inside src
            // Parsing the relative zip path and the route to serve that content
            // The following example will serve the index.html inside the zip file on /index.html route
            server.addStaticZip("staticFiles.zip", "/");
            
            // Add routes, parsing the SimpleRunnable that will receive the requests on that route            
            server.addRoute("/", BasicServer.Runnable001);
            
            // You can define a specific HTTP method to be handled by this runnable
            server.addRoute("/OnlyGET", SimpleHTTPServer.GET, BasicServer.Runnable001);
            // A group of HTTP methods
            server.addRoute("/OnlyPOST", SimpleHTTPServer.POST | SimpleHTTPServer.GET, BasicServer.Runnable001);
            // Or retrieve all methods using one of the following forms
            server.addRoute("/ALL1", BasicServer.Runnable001);
            server.addRoute("/ALL2", SimpleHTTPServer.ALL, BasicServer.Runnable001);
            
            // Routes are interpreted as Regular Expressions
            // The following one will receive any request like: /123 or /1 or /12352344
            server.addRoute("/(\\d+)", BasicServer.Runnable001);
            // You can retrieve the value of route's regular expression groups
            server.addRoute("/param/(\\d+)/", BasicServer.GettingURLParam);
            // Or yet name these groups and receive its value through that name
            server.addRoute("/named/(?<id>\\d+)/", BasicServer.GettingURLParamByName);
            
            // Routes can also redirect to another one using the SimpleRedirectHandler
            server.addRoute("/number", new SimpleRedirectHandler("/123"));
            
            // Starts your server
            server.start();
        } catch (IOException ex) {
            Logger.getLogger(BasicServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BasicServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static SimpleRunnable Runnable001 = new SimpleRunnable() {
        @Override
        public void run(SimpleExchange e) {
            try {
                e.sendResponse("Responding the route "+e.getExchange().getRequestURI()+" on method "+e.getExchange().getRequestMethod());
            } catch (IOException ex) {
                e.getExchange().close();
                Logger.getLogger(BasicServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
    public static SimpleRunnable GettingURLParam = new SimpleRunnable() {
        @Override
        public void run(SimpleExchange e) {
            try {
                e.sendResponse("You wrote the number: "+e.getRouteParam(0));
            } catch (IOException ex) {
                e.getExchange().close();
                Logger.getLogger(BasicServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
    public static SimpleRunnable GettingURLParamByName = new SimpleRunnable() {
        @Override
        public void run(SimpleExchange e) {
            try {
                e.sendResponse("You wrote the id: "+e.getRouteParam("id"));
            } catch (IOException ex) {
                e.getExchange().close();
                Logger.getLogger(BasicServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
}