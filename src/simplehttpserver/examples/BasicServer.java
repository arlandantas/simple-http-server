/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplehttpserver.examples;

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
            // Parsing the relative zip path and the path to serve content
            // The following example will serve the index.html inside the zip file on /index.html route
            server.addStaticZip("staticFiles.zip", "/");
            
            // Add routes, parsing the handler that will receive the requests on that route            
            server.addRoute("/", new BasicHandler());
            // Routes are interpreted as Regular Expressions
            // The following one will receive any request like: /123 or /1 or /12352344
            server.addRoute("/(\\d+)", new BasicHandler());
            // You can retrieve the value of route's regular expression groups on the handler
            server.addRoute("/param/(\\d+)/", new RouteParamHandler());
            // Or yet name these groups and receive its value through that name
            server.addRoute("/named/(?<id>\\d+)/", new NamedRouteParamHandler());
            
            // Routes can redirect to another one using the SimpleRedirectHandler
            server.addRoute("/number", new SimpleRedirectHandler("/123"));
            
            // Starts your server
            server.start();
        } catch (IOException ex) {
            Logger.getLogger(BasicServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BasicServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
