/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplehttpserver;

import java.util.concurrent.Executor;

/**
 *
 * @author arlan
 */
public class SimpleExecutor implements Executor {

    @Override
    public void execute(Runnable r) {
        new Thread(r).start();
    }
    
}
