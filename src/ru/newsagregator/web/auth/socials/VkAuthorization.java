/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.auth.socials;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ru.newsagregator.main.NewsAgregator;
import ru.newsagregator.web.auth.oauth.OAuthImpl;

/**
 *
 * @author Leha
 */
public class VkAuthorization extends OAuthImpl{

    private static final String OAuthURI = "oauth.vk.com/authorize", applicationId = "4812992", //параметры для вк. можно будет перебросить в конфиг
            scope = "124", redirectURI = "https://oauth.vk.com/blank.html", display = "popup",
            version = "5.29", responseType = "token";
    private String email, password;
    
    public VkAuthorization(){
        super(OAuthURI, applicationId, scope, redirectURI, display, version, responseType);
        if (currException != null) System.err.println(currException.getReason());
    }
    
    /**
     * Возвращает AccessToken, если это возможно. 
     * @param email
     * @param password
     * @return AccessToken 
     */
    @Override
    public String performAuthorization(String email, String password) {
        response = browser.sendGetRequest();
        NewsAgregator.doAfterRequest(browser, response);
        if (response != null){
            if (response.isEmpty()){
                
            }
        }
        browser.setCurrentURI("https://login.vk.com/?act=login&soft=1");
        browser.setFormParams(getAuthFormParams(email, password, response.getResponseContet()));
        response = browser.sendPostRequest();
        NewsAgregator.doAfterRequest(browser, response);
        String locationValue = response.getLocationHeader();
        browser.setCurrentURI(locationValue);
        response = browser.sendGetRequest();
        NewsAgregator.doAfterRequest(browser, response);
        
        return response.getFinalLocation();
    }

    @Override
    public Boolean isAuthorized(String accessToken) {
        return null;
    }
    
    private List<NameValuePair> getAuthFormParams(String email, String password, String page){
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("ip_h", "902335c18b5e703797"));
        params.add(new BasicNameValuePair("_origin", "https://oauth.vk.com"));
        params.add(new BasicNameValuePair("to", "aHR0cHM6Ly9vYXV0aC52ay5jb20vYXV0aG9"
                + "yaXplP2NsaWVudF9pZD00ODEyOTkyJnJlZGlyZWN0X3VyaT1odHRwcyUzQSUyRiUy"
                + "Rm9hdXRoLnZrLmNvbSUyRmJsYW5rLmh0bWwmcmVzcG9uc2VfdHlwZT10b2tlbiZzY2"
                + "9wZT0xMjQmdj01LjI5JnN0YXRlPSZkaXNwbGF5PXBvcHVw"));
        params.add(new BasicNameValuePair("expire", "0"));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("pass", password)); //подставить сюда пароль
        return params;
        
    }
    
}
