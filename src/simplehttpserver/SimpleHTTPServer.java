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
    
    public static final int POST = 1, GET = 2, PUT = 4, DELETE = 8, HEAD = 16,
            CONNECT = 32, OPTIONS = 64, TRACE = 128, PATCH = 256, ALL = 512;
    
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
    
    public void addRoute(String regex_route, SimpleRunnable handler) {
        this.handler.addRoute(regex_route, "*", handler);
    }
    
    private void addRoute(String regex_route, String method, SimpleRunnable handler) {
        this.handler.addRoute(regex_route, method, handler);
    }
    
    public void addRoute(String regex_route, int methods, SimpleRunnable handler) {
        // POST = 1, GET = 2, PUT = 4, DELETE = 8, HEAD = 16, CONNECT = 32, OPTIONS = 64, TRACE = 128, PATCH = 256
        if (methods >= SimpleHTTPServer.ALL) {
            this.addRoute(regex_route, "*", handler);
            methods -= SimpleHTTPServer.ALL;
        }
        if (methods >= SimpleHTTPServer.PATCH) {
            this.addRoute(regex_route, "PATCH", handler);
            methods -= SimpleHTTPServer.PATCH;
        }
        if (methods >= SimpleHTTPServer.TRACE) {
            this.addRoute(regex_route, "TRACE", handler);
            methods -= SimpleHTTPServer.TRACE;
        }
        if (methods >= SimpleHTTPServer.OPTIONS) {
            this.addRoute(regex_route, "OPTIONS", handler);
            methods -= SimpleHTTPServer.OPTIONS;
        }
        if (methods >= SimpleHTTPServer.CONNECT) {
            this.addRoute(regex_route, "CONNECT", handler);
            methods -= SimpleHTTPServer.CONNECT;
        }
        if (methods >= SimpleHTTPServer.HEAD) {
            this.addRoute(regex_route, "HEAD", handler);
            methods -= SimpleHTTPServer.HEAD;
        }
        if (methods >= SimpleHTTPServer.DELETE) {
            this.addRoute(regex_route, "DELETE", handler);
            methods -= SimpleHTTPServer.DELETE;
        }
        if (methods >= SimpleHTTPServer.PUT) {
            this.addRoute(regex_route, "PUT", handler);
            methods -= SimpleHTTPServer.PUT;
        }
        if (methods >= SimpleHTTPServer.GET) {
            this.addRoute(regex_route, "GET", handler);
            methods -= SimpleHTTPServer.GET;
        }
        if (methods >= SimpleHTTPServer.POST) {
            this.addRoute(regex_route, "POST", handler);
            methods -= SimpleHTTPServer.POST;
        }
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
