/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import ru.newsagregator.web.http.NAHttpBrowser;
import ru.newsagregator.web.http.NAHttpHeader;
import ru.newsagregator.web.http.NAHttpResponse;

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
        
        for (int i = 0; i < 2; i++){
        NAHttpResponse response = browser.sendGetRequest("http://vk.com"); //4812992 - appId
        for (Cookie c: browser.getAllCookies()){
            BasicClientCookie curr = (BasicClientCookie)c;
            System.out.println(curr.toString());
        }
        if (response != null){ //входные параметры запроса корректны
            if (!response.isEmpty()){ //удалось выполнить запрос
                List<NAHttpHeader> responseHeaders = response.getResponseHeaders();
                for (NAHttpHeader curr: responseHeaders){
                    System.out.println(curr.toString());
                }
                if (response.hasContent()){ //есть контент ответа
                    System.out.println("\n"+response.getResponseContet()); //вывод контента в поток вывода
                    
                    File file = new File("test.html"); //вывод контента в html файл для удобной проверки
                    try {
                        if(file.exists()){
                            file.delete();
                        }
                        file.createNewFile();
                        BufferedWriter bufWriter;
                        OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(file), Charset.forName("Windows-1251"));
                        bufWriter = new BufferedWriter(os);
                        bufWriter.append(response.getResponseContet());
                        bufWriter.flush();
                        bufWriter.close();
                    } catch(IOException e) {
                        System.err.println(Arrays.toString(e.getStackTrace()));
                    }
                }
            }
        }
        }
    }
    
}
