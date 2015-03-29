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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.newsagregator.web.auth.oauth.OAuthException;
import ru.newsagregator.web.auth.socials.VkAuthorization;
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
        /*NAHttpBrowser browser = new NAHttpBrowser(null, true); //4812992 - appId   
        NAHttpResponse response;
        String host_3 = "https://login.vk.com/?act=grant_access&" //забирать access_token из заголовка location этого парня
                + "client_id=4812992&"
                + "settings=124&"
                + "redirect_uri=https%3A%2F%2Foauth.vk.com%2Fblank.html&"
                + "response_type=token&"
                + "direct_hash=4b814296a1319b4422&"
                + "token_type=0&"
                + "v=5.29&"
                + "state=&"
                + "display=popup&"
                + "ip_h=902335c18b5e703797&"
                + "hash=f99465bbf5a12306e2&"
                + "https=1";
        String host_2 = "https://oauth.vk.com/authorize?client_id=4812992&" //хеш берётся из Location предыдущего запроса
                + "redirect_uri=https%3A%2F%2Foauth.vk.com%2Fblank.html&"
                + "response_type=token&"
                + "scope=124&"
                + "v=5.29&"
                + "state=&"
                + "display=popup&"
                + "__q_hash=cb15c0d484bf9fa729418287affffcf5";
        String host_1 = "https://login.vk.com/?act=login&soft=1"; //дополнительно сюда нужно передать параметры форм
        String host_0 = "https://oauth.vk.com/authorize?client_id=4812992&" //изначально перенаправляет на страницу авторизации
                + "scope=124&redirect_uri="+URLEncoder.encode("https://oauth.vk.com/blank.html", "CP1251")
                + "&display=popup&v=5.29&response_type=token"; 
        browser.setCurrentURI(host_0); //устанавливаем url
        
        for (int i = 0; i < 1; i++){ //делаем запрос n раз подряд
            response = browser.sendGetRequest(); //отправка гет-запроса
            doAfterRequest(browser, response); 
        } 
        
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("ip_h", "902335c18b5e703797"));
        params.add(new BasicNameValuePair("_origin", "https://oauth.vk.com"));
        params.add(new BasicNameValuePair("to", "aHR0cHM6Ly9vYXV0aC52ay5jb20vYXV0aG9"
                + "yaXplP2NsaWVudF9pZD00ODEyOTkyJnJlZGlyZWN0X3VyaT1odHRwcyUzQSUyRiUy"
                + "Rm9hdXRoLnZrLmNvbSUyRmJsYW5rLmh0bWwmcmVzcG9uc2VfdHlwZT10b2tlbiZzY2"
                + "9wZT0xMjQmdj01LjI5JnN0YXRlPSZkaXNwbGF5PXBvcHVw"));
        params.add(new BasicNameValuePair("expire", "0"));
        params.add(new BasicNameValuePair("email", "dragon-dex@yandex.ru"));
        params.add(new BasicNameValuePair("pass", "")); //подставить сюда пароль
        
        browser.setCurrentURI("https://login.vk.com/?act=login&soft=1");
        browser.setStoreFormParams(false);
        browser.setFormParams(params); 
        response = browser.sendPostRequest();
        doAfterRequest(browser, response); 
        
        String locationValue = response.getLocationHeader();
        browser.setCurrentURI(locationValue);
        response = browser.sendGetRequest(); 
        System.out.println(response.getFinalLocation()); */
        try {
            VkAuthorization vkAuthorize = new VkAuthorization("oauth.vk.com/authorize", "4812992", "124", "https://oauth.vk.com/blank.html", null, null, null);
            String accessToken = vkAuthorize.performAuthorization("dragon-dex@yandex.ru", ""); //подставить данные
            System.out.println(accessToken);
        } catch (OAuthException e){
            System.err.println(e.getReason());
        }
        
    }
    
    public static void doAfterRequest(NAHttpBrowser browser, NAHttpResponse response){
        System.out.println("[");
        for (NameValuePair cookie: browser.getAllCookies()){ //проверяем считывание куков из файла
            BasicNameValuePair curr = (BasicNameValuePair)cookie;
            System.out.println(" " + curr.toString() + " ");
        }
        System.out.println("]");

        if (response != null){ //входные параметры запроса корректны
            if (!response.isEmpty()){ //удалось выполнить запрос
                List<NAHttpHeader> responseHeaders = response.getResponseHeaders();
                for (NAHttpHeader curr: responseHeaders){ //вывод заголовков ответа
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
