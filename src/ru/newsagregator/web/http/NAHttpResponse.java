/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.http;

import java.util.List;

/**
 * Объект класса является "пустым" (isEmpty() == true), если поле responseHeaders имеет значение null.
 * hasContent() == true, если поля responseHeaders и responseContent не равны null.
 * @author Leha
 */
public final class NAHttpResponse {
    private long contentLength;
    private int responseCode;
    private List<NAHttpHeader> responseHeaders;
    private String responseContet;
    private boolean empty, content;
    
    public NAHttpResponse(){
        this.responseCode = 0;
        this.contentLength = 0;
        this.responseContet = null;
        this.responseHeaders = null;
        setEmpty(true);
        setHasContent(false);
    }
    
    protected NAHttpResponse(final int responseCode, final List<NAHttpHeader> responseHeaders, final String responseContent, final long contentLength){
        this.responseCode = responseCode;
        this.responseHeaders = responseHeaders;
        this.responseContet = responseContent;
        this.contentLength = contentLength;
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
    
    
    
}
