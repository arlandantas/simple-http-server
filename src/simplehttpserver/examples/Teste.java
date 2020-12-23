/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplehttpserver.examples;

import simplehttpserver.SimpleRouterHandler;

/**
 *
 * @author arlan
 */
public class Teste {
    public static void main (String[] args) {
        RunnableHandler rh = new RunnableHandler(null) {
            @Override
            public void handle(SimpleRouterHandler srh) {
                System.out.println("srh: "+srh);
            }
        };
        
        rh.run();
    }
}

class RunnableHandler implements Runnable {
    final SimpleRouterHandler handler;

    public RunnableHandler (SimpleRouterHandler srh) {
        this.handler = srh;
    }

    public void handle (SimpleRouterHandler srh) {}

    @Override
    public void run() {
        this.handle(handler);
    }
}