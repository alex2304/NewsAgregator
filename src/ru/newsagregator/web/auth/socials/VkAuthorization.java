/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.auth.socials;

import ru.newsagregator.web.auth.oauth.OAuthImpl;

/**
 *
 * @author Leha
 */
public class VkAuthorization extends OAuthImpl{
    
   
    
    
    
    @Override
    public String performAuthorization(String email, String password) {
        return null;
    }

    @Override
    public Boolean isAuthorized(String accessToken) {
        return null;
    }
    
}
