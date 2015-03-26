/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import web.http.NAHttpBrowser;
import web.http.NAHttpHeader;
import web.http.NAHttpResponse;

/**
 *
 * @author Leha
 */
public class NewsAgregator {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        NAHttpBrowser browser = new NAHttpBrowser(null, true);        
        String host = "https://oauth.vk.com/authorize?client_id=4812992&"
                + "scope=124&redirect_uri="+URLEncoder.encode("https://oauth.vk.com/blank.html", "CP1251")
                + "&display=popup&v=5.29&response_type=token"; 
        browser.setCurrentURI(host); //устанавливаем url
        
        NAHttpResponse response = browser.sendGetRequest(); //4812992 - appId
        if (response != null){ //входные параметры запроса корректны
            if (!response.isEmpty()){ //удалось выполнить запрос
                List<NAHttpHeader> responseHeaders = response.getResponseHeaders();
                for (NAHttpHeader curr: responseHeaders){
                    System.out.println(curr.toString());
                }
                if (response.hasContent()){ //есть контент ответа
                    System.out.println("\n"+response.getResponseContet());
                }
            }
        }
    }
    
}
