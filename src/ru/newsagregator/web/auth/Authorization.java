/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.auth;

import ru.newsagregator.web.auth.oauth.OAuthException;
import ru.newsagregator.web.auth.socials.VkAuthorization;

/**
 *
 * @author Leha
 */
public class Authorization {
    private VkAuthorization vkAutorization; 
    //private instagrammAutorization instagrammAutorization; //for example
    
    public Authorization(){
        vkAutorization = new VkAuthorization();
    }
    
    public String getVkAccessToken(String email, String password){
        String accessToken = vkAutorization.performAuthorization(email, password);
        return accessToken;
    }
    
    /**
     * Проверяет, является ли переданный ключ актуальным.
     * @param accessToken ключ для проверки
     * @return true если ключ актуален, false в противном случае
     */
    public boolean isAuthorizedVk(String accessToken){
        return vkAutorization.isAuthorized(accessToken);
    }
    
    /*public String getInstagrammAccessToken(String email, String password){
        
    } */
}
