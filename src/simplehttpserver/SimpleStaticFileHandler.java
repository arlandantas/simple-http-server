/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplehttpserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

/**
 *
 * @author arlan
 */
public class SimpleStaticFileHandler implements HttpHandler {
    File file = null;
    String contentType = null;
    
    public SimpleStaticFileHandler (File f) {
        this.file = f;
        this.contentType = SimpleStaticFileHandler.identifyContentType(f.getName());
    }
    
    public static String identifyContentType (String filename) {
        int last_dot = filename.lastIndexOf(".");
        if (last_dot == -1) return null;
        String extension = filename.substring(last_dot);
        switch (extension) {
            case ".js": return "application/javascript";
            case ".css": return "text/css";
            case ".htm": return "text/html";
            case ".html": return "text/html";
            case ".ico": return "image/x-icon";
            case ".jpeg": return "image/jpeg";
            case ".jpg": return "image/jpeg";
            case ".png": return "image/png";
            case ".pdf": return "application/pdf";
        }
        return null;
    }

    @Override
    public void handle(HttpExchange e) throws IOException {
        Path p = this.file.toPath();
        Headers headers = e.getResponseHeaders();
        if (this.contentType != null) {
            headers.add("Content-Type", this.contentType);
        }
        e.sendResponseHeaders(200, Files.size(p));
        try (OutputStream os = e.getResponseBody()) {
            os.write(Files.readAllBytes(p));
        }
        e.close();
    }
    
}
