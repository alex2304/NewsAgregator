/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.http;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Leha
 */
public class NACookieParser {
    
    protected static final String NAME = "name", VALUE = "value", SPLITTER = "@%",
            VERSION = "version", DOMAIN = "domain", EXPIRES = "expires", PATH = "path";
    private String cookieStr;
    private DateFormat dateFormatter;
    
    public NACookieParser(String cookieStr){
        this.cookieStr = cookieStr;
        dateFormatter = new SimpleDateFormat();
    }
    
    public void setCookieStr(String cookieStr) throws IOException{
        if (cookieStr == null) { throw new IOException("Cookie parser: cookieStr is null."); }
        this.cookieStr = cookieStr;
    }
    
    public String cookieToString(String name, String value, String domain, String path, int version, Date expires){
        String result = "";
        result += (concatParameter(result, VERSION, Integer.toString(version)) + 
                concatParameter(result, NAME, name) + 
                concatParameter(result, VALUE, value) + 
                concatParameter(result, DOMAIN, domain) +
                concatParameter(result, PATH, path) + 
                concatParameter(result, EXPIRES, (expires == null) ? null: expires.toString()));
        return result;
    }
    
    private String concatParameter(String result, String paramName, String paramValue){
        return (result += "[" + paramName + ": " + paramValue + "]");
    }
    
    private String parseCookieParamFromString(String paramName) throws IOException{
        try {
            return cookieStr.split(paramName)[1].split("]")[0].substring(2);
        } catch (Exception e){
            throw new IOException("Cookie parser: can't parse cookie param '" + paramName + "'.");
        }
    }
    
    private void checkCookieStr() throws IOException{
        if (cookieStr == null) throw new IOException("Cookie parser: cookieStr is null.");
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters">
     public String getCookieName() throws IOException{
        checkCookieStr();
        return parseCookieParamFromString(NAME);
    }
    
    public String getCookieValue()throws IOException{
        checkCookieStr();
        return parseCookieParamFromString(VALUE);
    }
    
    public int getCookieVersion() throws IOException{
        checkCookieStr();
        return Integer.parseInt(parseCookieParamFromString(VERSION));
    }
    
    public String getCookieDomain() throws IOException{
       checkCookieStr();
        return parseCookieParamFromString(DOMAIN);
    }
    
    public String getCookiePath() throws IOException{
        checkCookieStr();
        return parseCookieParamFromString(PATH);
    }
    
    public Date getCookieExpires() throws IOException, ParseException{
        checkCookieStr();
        return dateFormatter.parse(parseCookieParamFromString(EXPIRES));
    }
    // </editor-fold>
}
