/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.http.cookie;

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
    protected static final String[] datepatterns = {"EEE, dd MMM yyyy HH:mm:ss 'GMT'"};
    private String cookieStr;
    private SimpleDateFormat dateFormatter;
    
    /**
     * Объект класса может "упаковывать" Cookie -> String и "распаковывать" обратно String -> Cookie
     * @param cookieStr задаёт строку для парсинга (String -> Cookie)
     */
    public NACookieParser(String cookieStr){
        this.cookieStr = cookieStr;
        dateFormatter = new SimpleDateFormat(datepatterns[0]);
    }
    
    /**
     * 
     * @param cookieStr строка, в которой содержится cookie
     * @throws IOException если cookieStr == null 
     */
    public void setCookieStr(String cookieStr) throws IOException{
        if (cookieStr == null) { throw new IOException("Cookie parser: cookieStr is null."); }
        this.cookieStr = cookieStr;
    }
    
    /**
     * "Упаковывает" указанную в параметрах куку в строковое представление
     * @param name имя куки
     * @param value значение куки
     * @param domain домен куки
     * @param path путь куки
     * @param version версия куки
     * @param expires дата окончания действия куки
     * @return строковое представление куки из указанных параметров
     */
    public String cookieToString(String name, String value, String domain, String path, int version, Date expires){
        String result = "";
        result += (concatParameter(result, VERSION, Integer.toString(version)) + 
                concatParameter(result, NAME, name) + 
                concatParameter(result, VALUE, value) + 
                concatParameter(result, DOMAIN, domain) +
                concatParameter(result, PATH, path) + 
                concatParameter(result, EXPIRES, (expires == null) ? null: cookieDateToString(expires)));
        return result;
    }
    
    private String cookieDateToString(Date date){
        return dateFormatter.format(date);
    }
    
    /**
     * Склеивает параметр для куки в виде строки
     * @param result результат склейки
     * @param paramName имя параметра
     * @param paramValue значение параметра
     * @return к стркое result добавлен указанный параметр
     */
    private String concatParameter(String result, String paramName, String paramValue){
        return (result += "[" + paramName + ": " + paramValue + "]");
    }
    
    /**
     * Достаёт из строки значение параметра с именем paramName
     * @param paramName имя параметра
     * @return значение параметра с указанным именем
     * @throws IOException если не удаётся по заданному алгоритму получить параметр (строка имеет неверный формат)
     */
    private String parseCookieParamFromString(String paramName) throws IOException{
        try {
            return cookieStr.split(paramName)[1].split("]")[0].substring(2);
        } catch (Exception e){
            throw new IOException("Cookie parser: can't parse cookie param '" + paramName + "'.");
        }
    }
    
    /**
     * Применяется перед каждым вызовом парсинга строки. Проверяет, пустая ли она
     * @throws IOException если строка пустая
     */
    private void checkCookieStr() throws IOException{
        if (cookieStr == null) throw new IOException("Cookie parser: cookieStr is null.");
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters">
    /**
     * 
     * @return Имя куки из строки, хранящейся в поле класса
     * @throws IOException если строка в поле класса null либо имеет неверный формат
     */
     public String getCookieName() throws IOException{
        checkCookieStr();
        return parseCookieParamFromString(NAME);
    }
    
     /**
     * 
     * @return Значение куки из строки, хранящейся в поле класса
     * @throws IOException если строка в поле класса null либо имеет неверный формат
     */
    public String getCookieValue()throws IOException{
        checkCookieStr();
        return parseCookieParamFromString(VALUE);
    }
    
    /**
     * 
     * @return Версию куки из строки, хранящейся в поле класса
     * @throws IOException если строка в поле класса null либо имеет неверный формат
     */
    public int getCookieVersion() throws IOException{
        checkCookieStr();
        return Integer.parseInt(parseCookieParamFromString(VERSION));
    }
    
    /**
     * 
     * @return Домен куки из строки, хранящейся в поле класса
     * @throws IOException если строка в поле класса null либо имеет неверный формат
     */
    public String getCookieDomain() throws IOException{
       checkCookieStr();
        return parseCookieParamFromString(DOMAIN);
    }
    
    /**
     * 
     * @return Путь куки из строки, хранящейся в поле класса
     * @throws IOException если строка в поле класса null либо имеет неверный формат
     */
    public String getCookiePath() throws IOException{
        checkCookieStr();
        return parseCookieParamFromString(PATH);
    }
    
    /**
     * 
     * @return Дату истечения срока действия куки из строки, хранящейся в поле класса
     * @throws IOException если строка в поле класса null либо имеет неверный формат
     * @throws java.text.ParseException ошибка возникнет, если дата имеет неверный формат
     */
    public Date getCookieExpires() throws ParseException{
        String date = "null";
        try {
            checkCookieStr();
            date = parseCookieParamFromString(EXPIRES);
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
        try {
            return date.equalsIgnoreCase("null") ? null: dateFormatter.parse(date);
        } catch (ParseException e){
            System.err.println("Cookie parser: " + e.getLocalizedMessage());
            return null;
        } 
        
    }
    // </editor-fold>
}
