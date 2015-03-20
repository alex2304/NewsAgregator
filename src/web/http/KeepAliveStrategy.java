/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web.http;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.protocol.HttpContext;

/**
 *
 * @author Leha
 */
public class KeepAliveStrategy extends DefaultConnectionKeepAliveStrategy{
    
    private final long keepAliveTime;
    
    @Override
    public long getKeepAliveDuration(HttpResponse response, HttpContext context){
        long keepAliveTime = super.getKeepAliveDuration(response, context);
        if (keepAliveTime == -1){ //keep-alive value hasn't been excplicitly set by the server
            return this.keepAliveTime;
        }
        return keepAliveTime;
    }
    
    public KeepAliveStrategy(long keepAliveTime){
        this.keepAliveTime = keepAliveTime;
    }
}
