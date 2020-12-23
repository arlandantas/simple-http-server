/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplehttpserver;

import java.io.IOException;

/**
 *
 * @author arlan
 */
public abstract class SimpleRunnable {
    
    public abstract void run (SimpleExchange e) throws IOException;
    
}
