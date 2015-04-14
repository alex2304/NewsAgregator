/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.http.cookie;

import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.cookie.NetscapeDraftSpec;
import org.apache.http.protocol.HttpContext;

/**
 *
 * @author Leha
 */
public class NACookieSpecProvider implements CookieSpecProvider{

    public static String PROVIDER_NAME = "vk";
    
    @Override
    public CookieSpec create(HttpContext hc) {
        return new NetscapeDraftSpec(NACookieParser.datepatterns);
    }
    
}
