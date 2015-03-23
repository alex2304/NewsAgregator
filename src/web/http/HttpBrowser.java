/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web.http;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.Consts;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author Leha
 */
public class HttpBrowser {
    private static final String userAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)";
    private static final long keepAliveTime = 5000;
    
    private final CloseableHttpClient httpClient; //Клиент со множеством параметров. httpResponse = htpClient.execute(HttpGet/Post http) - для отправки запроса на сервер и получения ответа
    private URI currentURI; //текущий адрес, по которому будут выполняться запросы
    private HttpGet httpGet; //объект для выполнения get-запросов. hhtpGet = new HttpGet(URI uri) - для "нацеливания" на определённый адрес.
    private HttpPost httpPost; //объект для выполнения post-запросов. hhtpPost = new HttpPost(URI uri) - для "нацеливания" на определённый адрес.
    private CloseableHttpResponse httpResponse; //объект для получения результата запроса.
    private UrlEncodedFormEntity formEntity; //используется для иммитации отправки данных из html-форм. хранит параметры форм, после каждого запроса очищается
    
    /**
     * 
     * @param uri адрес, по которому впоследствии будут производиться запросы. если не нужно - ставить null
     */
    public HttpBrowser(String uri){
        try {
            currentURI = (uri == null) ? null: new URI(uri);
        } catch (URISyntaxException exc){
            System.err.println(exc.getMessage());
            currentURI = null;
        }
        initializeGetPost();
        httpResponse = null;
        formEntity = null;
        httpClient = HttpClients.custom()
                .setKeepAliveStrategy(new KeepAliveStrategy(keepAliveTime))
                .setUserAgent(userAgent)
                .setDefaultHeaders(HttpHeaders.getDefaultHeadersList())
                .build();
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
    
    /**
     * Инициализирует HttpGet и HttpPost в зависимости от текущего значения URI
     */
    private void initializeGetPost(){
        if (getCurrentURI() == null){
            httpGet = null;
            httpPost = null;
        } else {
            httpGet = new HttpGet(getCurrentURI());
            httpPost = new HttpPost(getCurrentURI());
        }
    }
    
    /**
     * 
     * @param iterator итератор списка HTTP заголовков с конкретным именем
     * @return список параметров для заданного HTTP заголовка 
     */
    private List<NameValuePair> parseHeaderParameters(HeaderElementIterator iterator){
        List<NameValuePair> result = new ArrayList<NameValuePair>();
        if (iterator == null) return result;
        while (iterator.hasNext()){
            HeaderElement elem = iterator.nextElement();
            result.add(new BasicNameValuePair(elem.getName(), elem.getValue()));
            result.addAll(Arrays.asList(elem.getParameters()));
        }
        return result;
    }
    
    /**
     * 
     * @param params список параметров, которые надо передать на сервер "из формы"
     * @return true если входной параметр корректен
     */
    public boolean setFormParams(List<NameValuePair> params){
        if (params == null) return false;
        if (params.isEmpty()) return false;
        formEntity = new UrlEncodedFormEntity(params, Consts.UTF_8);
        return true;
    }
    
    

    /**
     * @return the keep-alive time
     */
    public static long getKeepAliveTime() {
        return keepAliveTime;
    }

    /**
     * @return the currentURI in string representation
     */
    public String getCurrentURI() {
        return currentURI.toASCIIString();
    }

    /**
     * @param uri the currentURI to set
     * @return false if uri was incorrect
     */
    public boolean setCurrentURI(String uri) {
        try {
            if (uri == null) throw new URISyntaxException("Null pointer exception", "String uri was null");
            this.currentURI = new URI(uri);
            initializeGetPost();
        } catch (URISyntaxException exc){
            System.err.println(exc.getMessage());
            return false;
        }
        return true;
    }
    
    
}   
