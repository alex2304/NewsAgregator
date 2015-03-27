/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

/**
 *
 * @author Leha
 */
public class NACookieStore extends BasicCookieStore{
    private String storePath;
    private File store;
    
    public NACookieStore(){
        super();
    }
    
    public NACookieStore(String storePath) throws IOException{
        super();
        this.storePath = storePath;
        store = new File(storePath);
        boolean storeExists = store.exists();
        Cookie[] readed = null;
        if (storeExists) readed = readFromFile(); else store.createNewFile();
        if (readed != null) super.addCookies(readed);
        //BasicClientCookie c = new BasicClientCookie(storePath, storePath);
    }
    
    private Cookie[] readFromFile() throws IOException{
        if (store == null) throw new IOException("Bad store path"); //эта строчка не нужна, ну да ладно :)
        if (!store.canRead() || !store.canWrite()) throw new IOException("Bad store path");
        
        
        StringBuilder sb = new StringBuilder();
        BufferedReader bufReader;
        InputStreamReader is = new InputStreamReader(new FileInputStream(store), Charset.forName("Windows-1251"));
        bufReader = new BufferedReader(is);
        String tmp = null;
        int count = 0;
        while ((tmp = bufReader.readLine()) != null){
            sb.append(tmp);
            count++;
        }
        try {
            Cookie[] result = new Cookie[count];
            //parse strings to List<Cookie>
            return result;
        } catch (Exception e){
            throw new IOException("Wrong file");
        }
    }
    
    public void saveToFile() throws IOException{
        if (store == null) throw new IOException("Bad store path"); //эта строчка не нужна, ну да ладно :)
        if (!store.canRead() || !store.canWrite()) throw new IOException("Bad store path");
        
        BufferedWriter bufWriter;
        OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(store), Charset.forName("Windows-1251"));
        bufWriter = new BufferedWriter(os);
        for (Cookie curr: super.getCookies()){
            bufWriter.append(((BasicClientCookie)curr).toString());
            //исключить повторяющиеся куки
        }
        bufWriter.flush();
        bufWriter.close();
    }
}
