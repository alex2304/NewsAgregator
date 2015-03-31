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
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.newsagregator.web.http.NAHttpBrowser;
import ru.newsagregator.web.http.NAHttpHeader;
import ru.newsagregator.web.http.NAHttpResponse;

/**
 *
 * @author Leha
 */
public class Tester {
    
    public static void testAfterRequest(NAHttpBrowser browser, NAHttpResponse response){
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
