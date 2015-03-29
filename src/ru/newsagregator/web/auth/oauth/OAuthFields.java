/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.auth.oauth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Leha
 */
public class OAuthFields {
    protected static final String CLIENT_ID = "client_id", SCOPE = "scope", REDIRECT_URI = "redirect_uri",
            DISPLAY = "display", VERSION = "v", RESPONSE_TYPE = "response_type";
    
    /**
     * Возвращает список имён параметров, используемых в запросе на авторизацию.
     * @return список в следующем порядке: 
     * CLIENT_ID
     * SCOPE
     * REDIRECT_URI
     * DISPLAY
     * VERSION
     * RESPONSE_TYPE
     */
    protected static List<String> getFieldsList(){
        List<String> result = new ArrayList<String>();
        result.add(CLIENT_ID);
        result.add(SCOPE);
        result.add(REDIRECT_URI);
        result.add(DISPLAY);
        result.add(VERSION);
        result.add(RESPONSE_TYPE);
        return result;
    }
    
    /**
     * 
     * @param clientId
     * @param scope
     * @param redirectURI
     * @param display
     * @param version
     * @param responseType
     * @return
     * @throws UnsupportedEncodingException 
     */
    protected static String createParamString(String clientId, String scope, String redirectURI, String display, String version, String responseType) throws UnsupportedEncodingException{
        String result = "";
        result += (CLIENT_ID + "=" + clientId + "&" +
                SCOPE + "=" + scope + "&" +
                REDIRECT_URI + "=" + URLEncoder.encode(redirectURI, "CP1251") + "&" +
                DISPLAY + "=" + display + "&" +
                VERSION + "=" + version + "&" +
                RESPONSE_TYPE + "=" + responseType);
        return result;
    }
}
