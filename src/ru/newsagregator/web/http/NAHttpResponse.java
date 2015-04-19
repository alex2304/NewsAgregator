/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.http;

import ru.newsagregator.web.http.headers.NAHttpHeader;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;

/**
 * Объект класса является "пустым" (isEmpty() == true), если поле responseHeaders имеет значение null.
 * hasContent() == true, если поля responseHeaders и responseContent не равны null.
 * @author Leha
 */
public final class NAHttpResponse {
    private long contentLength;
    private int responseCode;
    private List<NAHttpHeader> responseHeaders;
    private String finalLocation;
    private String responseContet;
    private boolean empty, content;
    
    /**
     * Возвращает контент ответа либо null, если контент отсутствует.
     * @param response ответ, из которого требуется извлечь контент
     * @return null, если контент отсутствует, в противном случае строка, представляющая собой контент ответа.
     */
    public static String resolveResponseContent(NAHttpResponse response){
        String responseContent = null;
        if (response != null){
            if (!response.isEmpty()){
                if (response.hasContent()){
                    responseContent = response.getResponseContet();
                }
            }
        }
        return responseContent;
    }
    
    public NAHttpResponse(){
        this.responseCode = 0;
        this.contentLength = 0;
        this.responseContet = null;
        this.responseHeaders = null;
        this.finalLocation = null;
        setEmpty(true);
        setHasContent(false);
    }
    
    protected NAHttpResponse(final int responseCode, final List<NAHttpHeader> responseHeaders, final String responseContent, final long contentLength, final String finalLocation){
        this.responseCode = responseCode;
        this.responseHeaders = responseHeaders;
        this.responseContet = responseContent;
        this.contentLength = contentLength;
        this.finalLocation = finalLocation;
        if (responseHeaders != null) setEmpty(false); else setEmpty(true);
        if (responseHeaders != null && responseContent != null) setHasContent(true); else setHasContent(false);
    }

    /**
     * @return the responseCode
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * @return the responseHeaders
     */
    public List<NAHttpHeader> getResponseHeaders() {
        return responseHeaders;
    }

    /**
     * @return the responseContet
     */
    public String getResponseContet() {
        return responseContet;
    }
    
    /**
     * @return the contentLength
     */
    public long getContentLength() {
        return contentLength;
    }
    
    private void setHasContent(boolean param){
        this.content = param;
    }
    
    /**
     * 
     * @return content contents
     */
    public boolean hasContent(){
        return content;
    }
    
    /**
     * 
     * @return the empty
     */
    public boolean isEmpty(){
        return empty;
    }
    
    private void setEmpty(boolean param){
        this.empty = param;
    }

    /**
     * @param responseCode the responseCode to set
     */
    protected void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * @param responseHeaders the responseHeaders to set
     */
    protected void setResponseHeaders(List<NAHttpHeader> responseHeaders) {
        this.responseHeaders = responseHeaders;
        if (responseHeaders != null) setEmpty(false); else setEmpty(true);
    }

    /**
     * @param responseContet the responseContet to set
     */
    protected void setResponseContet(String responseContet) {
        this.responseContet = responseContet;
        if (responseContet != null && responseHeaders != null) setHasContent(true); else setHasContent(false);
    }

    /**
     * @param contentLength the contentLength to set
     */
    protected void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    /**
     * @return the finalLocation
     */
    public String getFinalLocation() {
        return finalLocation;
    }

    /**
     * @param finalLocation the finalLocation to set
     */
    protected void setFinalLocation(String finalLocation) {
        this.finalLocation = finalLocation;
    }

    /**
     * Возвращает объект NAHttpHeader, характеризующий http заголовок с именем name
     * @param name имя http заголовка (в любом регистре)
     * @return null, если такого заголовка нет либо список заголовков пуст. в противном случае - объект заголовка
     */
    public NAHttpHeader getHeaderByName(String name) {
        if (this.isEmpty()) return null;
        for (NAHttpHeader curr: this.responseHeaders){
            if (curr.getName().equalsIgnoreCase(name.toLowerCase())) return curr;
        }
        return null;
    }

    /**
     * Получает заголовок "Location" и возвращает его в строковом представлении
     * @return http ссылка, указанная в Location: ...
     */
    public String getLocationHeader() {
        NAHttpHeader location = this.getHeaderByName("location");
        if (location == null) return null;
        try { //null exception can occur
            BasicNameValuePair locationValue = (BasicNameValuePair)location.getParameters().get(location.getParameters().size() - 1);
            String result = "";
            result += locationValue.getName() + "=" + locationValue.getValue();
            return result;
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
        
    }
    
    
    
}
