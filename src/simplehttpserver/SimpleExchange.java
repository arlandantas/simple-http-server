/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplehttpserver;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author arlan
 */
public class SimpleExchange {
    
    private final HttpExchange exchange;
    private int code = 200;
    private final String regex_rota;
    private final HashMap<String, Object> inputs = new HashMap<>();
    
    public SimpleExchange (HttpExchange e) {
        this.exchange = e;
        this.regex_rota = (String) e.getAttribute("regex_rota");
        this.fillInputs();
    }
    
    public final void fillInputs () {
        try {
            if (exchange.getRequestURI().getQuery() != null) {
                String[] query_parts = exchange.getRequestURI().getQuery().split("(\\?|\\&)");
                for (String query_part : query_parts) {
                    if ("".equals(query_part)) continue;
                    String[] parts =  query_part.split("\\=", 2);
                    this.inputs.put(parts[0], parts.length > 1 ? parts[1] : "1");
                }
            }
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStream is = exchange.getRequestBody();
                String body = new String(is.readAllBytes());
                if (body.length() > 0) {
                    String[] type = exchange.getRequestHeaders().getFirst("Content-Type").split(";");
                    HashMap<String, String> detalhes = new HashMap<>();
                    for (String p : type) {
                        String[] pp = p.trim().split("=", 2);
                        detalhes.put(pp[0], pp.length > 1 ? pp[1] : "1");
                    }

                    switch (type[0]) {
                        case "application/json":
                            if (!body.equals("null")) {
                                JSONObject obj = new JSONObject(body);
                                for (Iterator iterator = obj.keys(); iterator.hasNext();) {
                                    String key = (String) iterator.next();
                                    this.inputs.put(key, obj.get(key));
                                }
                            }
                            break;
                        case "application/x-www-form-urlencoded":
                            String[] body_parts = body.split("(\\?|\\&)");
                            for (String body_part : body_parts) {
                                if ("".equals(body_part)) continue;
                                String[] parts =  body_part.split("\\=", 2);
                                this.inputs.put(parts[0], parts.length > 1 ? parts[1] : "1");
                            }
                            break;
                        case "multipart/form-data":
                        default:
                            this.sendResponse("Media Type not supported yet", 415);
                            break;
                    }
                }
            }
        } catch (IOException | JSONException ex) {
            Logger.getLogger(SimpleExchange.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public HashMap<String, Object> getInputs () {
        return this.inputs;
    }
    
    public Object getInput (String field) {
        return this.inputs.get(field);
    }
    
    public String getRouteParam (int group_index) {
        Matcher m = Pattern.compile(this.regex_rota, Pattern.CASE_INSENSITIVE)
                .matcher(this.exchange.getRequestURI().toString());
        return m.matches() && m.groupCount() > group_index+1 ? m.group(group_index+1) : null;
    }
    
    public String getRouteParam (String group_name) {
        Matcher m = Pattern.compile(this.regex_rota, Pattern.CASE_INSENSITIVE)
                .matcher(this.exchange.getRequestURI().toString());
        return m.matches() ? m.group(group_name) : null;
    }
    
    public HttpExchange getExchange() {
        return this.exchange;
    }
    
    public void setCode (int code) {
        this.code = code;
    }
    
    public void setHeader (String key, String value) {
        this.exchange.getResponseHeaders().add(key, value);
    }
    
    public void sendJSONResponse (Object obj) throws IOException {
        JSONObject jobj = new JSONObject(obj);
        this.setHeader("Content-Type", "application/json; charset=UTF-8");
        sendResponse(jobj.toString());
    }
    
    public void sendJSONResponse (JSONObject obj) throws IOException {
        this.setHeader("Content-Type", "application/json; charset=UTF-8");
        sendResponse(obj.toString());
    }
    
    public void sendJSONResponse (JSONArray obj) throws IOException {
        this.setHeader("Content-Type", "application/json; charset=UTF-8");
        sendResponse(obj.toString());
    }
    
    public void sendResponse (String response) throws IOException {
        this.sendResponse(response, this.code);
    }
    
    public void sendResponse (String response, String ContentType) throws IOException {
        this.setHeader("Content-Type", ContentType);
        this.sendResponse(response, this.code);
    }
    
    public void sendResponse (String response, int responseCode) throws IOException {
        if (!this.exchange.getResponseHeaders().keySet().contains("Content-Type")) {
            this.setHeader("Content-Type", "text/plain; charset=UTF-8");
        }
        this.exchange.sendResponseHeaders(responseCode, response.getBytes().length);
        OutputStream out = this.exchange.getResponseBody();
        out.write(response.getBytes());
        this.exchange.close();
    }
}
