/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.auth.oauth;

/**
 *
 * @author Leha
 */
public class OAuthException extends Exception{
    private static final String defaultReason = "OAuth exception: ";
    private final String reason;
    
    public OAuthException(String reason){
        super(reason);
        this.reason = defaultReason + ( (reason == null) ? "unknow reason.": reason);
    }
    
    public String getReason(){
        return reason;
    }
}
