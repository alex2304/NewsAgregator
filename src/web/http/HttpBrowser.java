/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web.http;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;

/**
 *
 * @author Leha
 */
public class HttpBrowser {
    private static final String userAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)";
    private static final long keepAliveTime = 5000;
    private final CloseableHttpClient httpClient;
    private URI currentURI;
    private HttpGet httpGet;
    private HttpPost httpPost;
    private CloseableHttpResponse httpResponse;
    
    public HttpBrowser(String uri){
        try {
            currentURI = (uri == null) ? null: new URI(uri);
        } catch (URISyntaxException exc){
            System.err.println(exc.getMessage());
            currentURI = null;
        }
        initializeGetPost();
        httpResponse = null;
        httpClient = HttpClients.custom().setKeepAliveStrategy(new KeepAliveStrategy(keepAliveTime)).setUserAgent(userAgent).build();
    }
    
    public String sendGetRequest(String headers){
        return null;
    }
    
    public String sendGetRequest(String headers, String host){
        return null;
    }
    
    public String sendPostRequest(String headers){
        return null;
    }
    
    public String sendPostRequest(String headers, String host){
        return null;
    }
    
    private boolean initializeGetPost(){
        if (getCurrentURI() == null){
            httpGet = null;
            httpPost = null;
            return false;
        } else {
            httpGet = new HttpGet(getCurrentURI());
            httpPost = new HttpPost(getCurrentURI());
        }
        
        return true;
    }

    /**
     * @return the connectionTimeOut
     */
    public static long getKeepAliveTime() {
        return keepAliveTime;
    }

    /**
     * @return the currentURI
     */
    public String getCurrentURI() {
        return currentURI.toASCIIString();
    }

    /**
     * @param currentURI the currentURI to set
     */
    public boolean setCurrentURI(String uri) {
        try {
            if (uri == null) throw new URISyntaxException("Null pointer exception", "String uri was null");
            this.currentURI = new URI(uri);
        } catch (URISyntaxException exc){
            System.err.println(exc.getMessage());
            return false;
        }
        return true;
    }
    
    
}   
