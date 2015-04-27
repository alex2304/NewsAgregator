/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.auth.socials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.newsagregator.main.Tester;
import ru.newsagregator.web.auth.oauth.OAuthImpl;
import ru.newsagregator.web.formParser.NAFormParser;

/**
 *
 * @author Leha
 */
public class VkAuthorization extends OAuthImpl{

    private static final String OAuthURI = "oauth.vk.com/authorize", applicationId = "4812992", //параметры для вк. можно будет перебросить в конфиг
            scope = "124", redirectURI = "https://oauth.vk.com/blank.html", display = "popup",
            version = "5.29", responseType = "token";
    
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
        Tester.testAfterRequest(browser, response);
        if (response != null){
            if (!response.isEmpty()){
             
            }
        }
        browser.setCurrentURI("https://login.vk.com/?act=login&soft=1");
        try {
            browser.setFormParams(getAuthFormParams(email, password, response.getResponseContet()));
        } catch (IOException ex) {
            Logger.getLogger(VkAuthorization.class.getName()).log(Level.SEVERE, null, ex);
        }
        response = browser.sendPostRequest();
        Tester.testAfterRequest(browser, response);
        String locationValue = response.getLocationHeader();
        browser.setCurrentURI(locationValue);
        response = browser.sendGetRequest();
        Tester.testAfterRequest(browser, response); 
        return response.getFinalLocation(); 
    }

    @Override
    public Boolean isAuthorized(String accessToken) {
        return null;
    }
    
    private List<NameValuePair> getAuthFormParams(String email, String password, String page) throws IOException{
        List<NameValuePair> params = new ArrayList<>();
        NAFormParser pPage = new NAFormParser(page); 
        params=pPage.getInputFormParams(email, password);
        return params;
        
    }
    
}
