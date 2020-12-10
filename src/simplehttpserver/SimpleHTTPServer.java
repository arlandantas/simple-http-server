/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplehttpserver;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author arlan
 */
public class SimpleHTTPServer {
    private final HttpServer server;
    private final int port;
    private final SimpleRouterHandler handler;
    
    public SimpleHTTPServer (int port) throws IOException {
        this.port = port;
        this.server = HttpServer.create(new InetSocketAddress(this.port), 0);
        this.server.setExecutor(new SimpleExecutor());
        this.handler = new SimpleRouterHandler();
        this.server.createContext("/", handler);
    }
    
    public SimpleHTTPServer (int port, String static_path) throws IOException {
        this.port = port;
        this.server = HttpServer.create(new InetSocketAddress(this.port), 0);
        this.server.setExecutor(new SimpleExecutor());
        this.handler = new SimpleRouterHandler();
        this.server.createContext("/", this.handler);
        
        File file_public = new File(static_path);
        loadStatic(file_public, this.server, "");
    }
    
    public SimpleHTTPServer (int port, URI static_path) throws IOException {
        this.port = port;
        this.server = HttpServer.create(new InetSocketAddress(this.port), 0);
        this.server.setExecutor(new SimpleExecutor());
        this.handler = new SimpleRouterHandler();
        this.server.createContext("/", this.handler);
        
        File file_public = new File(static_path);
        loadStatic(file_public, this.server, "");
    }
    
    public void addStaticZip (String zipfilepath, String serverpath) throws IOException {
        ZipInputStream zis = new ZipInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream(zipfilepath));
        ZipEntry ze;
        while ((ze = zis.getNextEntry()) != null) {
            if (!ze.isDirectory()) {
                byte[] data = zis.readAllBytes();
                String contentType = SimpleStaticFileHandler.identifyContentType(ze.getName());
                this.addRoute(serverpath+(serverpath.endsWith("/") ? "": "/")+ze.getName(), new SimpleStaticByteHandler(data, contentType));
            }
        }
    }
    
    public void addRoute(String regex_route, HttpHandler handler) {
        this.handler.addRoute(regex_route, handler);
    }
    
    public String[] getRoutes () {
        return this.handler.getRoutes();
    }
    
    public void start() {
        this.server.start();
        System.out.println("SimpleHTTPServer started at "+this.port+" port");
    }
    
    static void loadStatic (File file, HttpServer server, String path) {
        if (file.isDirectory()) {
            for(File f : file.listFiles()) {
                loadStatic(f, server, path+"/"+f.getName());
            }
        } else {
            server.createContext(path, new SimpleStaticFileHandler(file));
//            System.out.println("Loaded: "+path);
        }
    }
}
