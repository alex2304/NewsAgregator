/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.http.cookie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Date;
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
    private int cookieCount;
    private NACookieParser cookieParser;
    
    /**
     * Вызывает конструктор родителя. При такой инициализации будут корректно работать только методы и поля родителя.
     */
    public NACookieStore(){
        super();
    }
    
    /**
     * Пытается подключиться к хранилищу, указанному в storePath,и прочитать оттуда куки, добавив их классу-родителю
     * @param storePath путь к хранилищу относительно корневой папки проекта
     * @throws IOException  если путь неккоректен / нет прав доступа / файл хранилища имеет неверный формат
     */
    public NACookieStore(String storePath) throws IOException{
        super();
        this.cookieParser = new NACookieParser(null);
        this.storePath = storePath;
        store = new File(storePath);
        boolean storeExists = store.exists();
        Cookie[] readed = null;
        if (storeExists) readed = readFromFile(); else store.createNewFile();
        if (readed != null) super.addCookies(readed);
    }
    
    /**
     * Пытается прочитать куки из хранилища
     * @return массив объектов класса BasicClientCookie
     * @throws IOException  если пути не существует / нет прав доступа / файл имеет неверный формат
     */
    private Cookie[] readFromFile() throws IOException{
        if (store == null) throw new IOException("Bad store path"); //эта строчка не нужна, ну да ладно :)
        if (!store.canRead() || !store.canWrite()) throw new IOException("Bad store path");
        
        StringBuilder sb = new StringBuilder();
        BufferedReader bufReader;
        InputStreamReader is = new InputStreamReader(new FileInputStream(store), Charset.forName("Windows-1251"));
        bufReader = new BufferedReader(is);
        String tmp = null;
        while ((tmp = bufReader.readLine()) != null){ //внимание! получаем информацию от пользователя!
            sb.append(tmp); 
        }
        tmp = sb.toString(); //строка, представл. собой всё содержимое файла
        if (tmp.isEmpty()) return null;
        try {
            //tmp = decode(tmp); расшифровка содержимого файла 
            //переделать этот блок, чтобы выбрасывались тематические исключения и устанавливалась хотя бы часть куков
            String[] cookies = tmp.split(NACookieParser.SPLITTER);
            Cookie[] result = new Cookie[cookies.length];
            for (int i = 0; i < cookies.length; i++){
                cookieParser.setCookieStr(cookies[i]);
                BasicClientCookie c = new BasicClientCookie(cookieParser.getCookieName(), cookieParser.getCookieValue());
                c.setPath(cookieParser.getCookiePath());
                c.setVersion(cookieParser.getCookieVersion());
                c.setDomain(cookieParser.getCookieDomain());
                c.setExpiryDate(cookieParser.getCookieExpires());
                result[i] = c;
            }
            return result;
        } catch (Exception e){
            throw new IOException("Wrong file");
        }
    }
    
    /**
     * Пытается записать свои куки в файл хранилища
     * @throws IOException если пути не существует / нет прав доступа / Путин краб
     */
    public void saveToFile() throws IOException{
        if (store == null) throw new IOException("Bad cookie store path"); 
        if (!store.canRead() || !store.canWrite()) throw new IOException("Bad cookie store path");
        
        int newCookieCount = super.getCookies().size();
        if (newCookieCount > cookieCount && newCookieCount > 0){
            String cookieStr = "";
            for (int i = 0; i < newCookieCount; i++){
                BasicClientCookie curr = (BasicClientCookie)super.getCookies().get(i);
                cookieStr += cookieParser.cookieToString(curr.getName(), curr.getValue(), curr.getDomain(), curr.getPath(), curr.getVersion(), curr.getExpiryDate());
                if (i != newCookieCount - 1) cookieStr += NACookieParser.SPLITTER;
                //исключить повторяющиеся куки
            }
            //cookieStr = encode(cookieStr);
            BufferedWriter bufWriter;
            OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(store), Charset.forName("Windows-1251"));
            bufWriter = new BufferedWriter(os);
            bufWriter.append(cookieStr);
            bufWriter.flush();
            bufWriter.close();
        }
        cookieCount = newCookieCount;
    }
    
}
