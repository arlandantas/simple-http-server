/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplehttpserver.examples.handlers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import simplehttpserver.SimpleExchange;
import simplehttpserver.SimpleHandler;

/**
 *
 * @author arlan
 */
public class BasicHandler extends SimpleHandler {
    @Override
    public void onGET(SimpleExchange e) throws IOException {
        e.sendResponse("That is a GET request!");
    }

    @Override
    public void onPOST(SimpleExchange e) throws IOException {
//        String type = e.getExchange().getRequestHeaders().getFirst("Content-Type");
//        String ret = type+"\n$$$\n";
//        for (String k : e.getInputs().keySet()) {
//            ret += k+": "+e.getInput(k)+"\n";
//        }
//        e.sendResponse(ret);
//        try {
//            OutputStream os = new FileOutputStream("/home/arlan/upload.pdf");
//            byte[] data = (byte[]) e.getInput("File1");
//            os.write(data);
//            os.close();
////            FileWriter myWriter = new FileWriter("/home/arlan/upload.docx");
////            myWriter.write("lala);
////            myWriter.close();
//            System.out.println("Successfully wrote to the file.");
//        } catch (IOException ex) {
//            System.out.println("An error occurred.");
//            ex.printStackTrace();
//        }
        e.sendResponse(e.getRawBody());
    }

    @Override
    public void onPUT(SimpleExchange e) throws IOException {
        e.sendResponse("That is a PUT request!");
    }

    @Override
    public void onDELETE(SimpleExchange e) throws IOException {
        e.sendResponse("That is a DELETE request!");
    }
    
}
