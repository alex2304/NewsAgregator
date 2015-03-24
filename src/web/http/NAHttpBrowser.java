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
public class NAHttpBrowser {
    private static final String userAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)";
    private static final long keepAliveTime = 5000;
    
    private final CloseableHttpClient httpClient; //Клиент со множеством параметров. httpResponse = htpClient.execute(HttpGet/Post http) - для отправки запроса на сервер и получения ответа
    private URI currentURI; //текущий адрес, по которому будут выполняться запросы
    private HttpGet httpGet; //объект для выполнения get-запросов. hhtpGet = new HttpGet(URI uri) - для "нацеливания" на определённый адрес.
    private HttpPost httpPost; //объект для выполнения post-запросов. hhtpPost = new HttpPost(URI uri) - для "нацеливания" на определённый адрес.
    private CloseableHttpResponse httpResponse; //объект для получения результата запроса.
    private UrlEncodedFormEntity formEntity; //используется для иммитации отправки данных из html-форм. хранит параметры форм, после каждого запроса очищается
    private List<NameValuePair> customHeaders; 
    
    private boolean storeFormParams; //true если нужно хранить параметры форм после выполнения запроса, иначе false
    
    /**
     * 
     * @param uri адрес, по которому впоследствии будут производиться запросы. если не нужно - ставить null
     * @param storeFormParams хранить ли параметры форм для последующих запросов
     */
    public NAHttpBrowser(String uri, boolean storeFormParams){
        this.storeFormParams = storeFormParams;
        try {
            currentURI = ((uri == null) ? null: new URI(uri));
        } catch (URISyntaxException exc){
            System.err.println(exc.getMessage());
            currentURI = null;
        }
        customHeaders = new ArrayList<NameValuePair>();
        initializeGetPost();
        httpResponse = null;
        formEntity = null;
        httpClient = HttpClients.custom()
                .setKeepAliveStrategy(new NAKeepAliveStrategy(keepAliveTime))
                .setUserAgent(userAgent)
                .setDefaultHeaders(NAHttpHeaders.getDefaultHeadersList())
                .build();
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
     * @param reqType true означает, что будет выполнен get-запрос, а false - post
     * @param uri uri для запроса. если оно совпадает с uri в классе - передать сюда uri класса
     * @return false если uri NULL
     */
    private boolean applyParamsBeforeRequest(boolean reqType, URI uri){
        if (uri == null) return false;
        boolean isFormEntityEmpty = (formEntity == null) ? true: false;
        if  (reqType) {
            httpGet = new HttpGet(uri);
        } else {
            httpPost = new HttpPost(uri);
            if (!isFormEntityEmpty) httpPost.setEntity(formEntity);
        }
        for (NameValuePair header : customHeaders){
            BasicNameValuePair iter = (BasicNameValuePair) header;
            if (reqType) httpGet.addHeader(iter.getName(), iter.getValue()); 
            else httpPost.addHeader(iter.getName(), iter.getValue());
        }
        
        return true;
    }
    
    /**
     * Функция проверяет заголовок на наличие полей Name и Value, а так же на 
     * корректное содержимое этих полей (имя из списка всех возможных заголовков, 
     * значения должны быть без запрещённых символов)
     * @param header заголовок для валидации
     * @return true если заголовок валидный
     */
    private boolean validateHeader(NameValuePair header){
        BasicNameValuePair h = (BasicNameValuePair)header;
        if (h.getValue() == null || h.getName() == null) return false;
        
        //validate header (регулярные выражения, список заголовков)
        
        return true;    
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
     * 
     * @param params список параметров, которые надо передать на сервер "из формы"
     * @return true если входной параметр не NULL и не пуст, иначе false
     */
    public boolean setFormParams(List<NameValuePair> params){
        if (params == null) return false;
        if (params.isEmpty()) return false;
        formEntity = new UrlEncodedFormEntity(params, Consts.UTF_8);
        return true;
    }
    
    

    /**
     * @return параметр keep-alive, установленный в браузере (это время, которое будет поддерживаться соединение) в мс
     */
    public static long getKeepAliveTime() {
        return keepAliveTime;
    }

    /**
     * @return текущий URI в строковом представлении
     */
    public String getCurrentURI() {
        return currentURI.toASCIIString();
    }

    /**
     * @param uri "unified request information" - адрес хоста, к которому необходимо будет производить http запросы, в любом из поддерживаемых форматов (148.8.228.0, http://praviysector.info)
     * @return false если строка не является URI
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

    /**
     * @return текущее значение параметра storeFormParams (true - параметры сохраняются, false - очищаются после каждого post-запроса)
     */
    public boolean isStoreFormParams() {
        return storeFormParams;
    }

    /**
     * @param storeFormParams если установить true, браузер будет хранить параметры форм всё время, иначе только до следующего post-запроса
     */
    public void setStoreFormParams(boolean storeFormParams) {
        this.storeFormParams = storeFormParams;
    }

    /**
     * @return список http-заголовков, дополнительно добавляемый к каждому запросу
     */
    public List<NameValuePair> getCustomHeaders() {
        return customHeaders;
    }
    
    /**
     * Очищает список "пользовательских" http-заголовков
     */
    public void clearCustomHeaders(){
        customHeaders = new ArrayList<NameValuePair>();
    }
    
    /**
     * 
     * @param header заголовок для добавления
     * @return true, если заголовок корректен 
     */
    public boolean addCustomHeader(NameValuePair header){
        if (header == null) return false;
        if (header.getName() == null || header.getValue() == null) return false;
        customHeaders.add(header);
        return true;
    }
    
    /**
     * 
     * @param headers - список заголовков для добавления
     * @return список заголовков, которые не являются корректными. если все корректны - null
     * @throws NullPointerException - выбрасывает исключение, если headers или один из header'ов null.
     */
    public List<NameValuePair> addCustomHeaders(List<NameValuePair> headers) throws NullPointerException{
        if (headers == null) throw new NullPointerException("WebBrowser->addCustomHeaders: Parameter headers was null");
        List<NameValuePair> result = new ArrayList<NameValuePair>();
        for (NameValuePair header: headers){
            if (header == null) throw new NullPointerException("WebBrowser->addCustomHeaders: One of the headers was null.");
            BasicNameValuePair it = (BasicNameValuePair)header;
            if ( validateHeader(header) ) result.add(header); else customHeaders.add(header); //добавить проверку HEADER'ов!
        }
        return (result.isEmpty()) ? null: result;
    }
    
    
}   
