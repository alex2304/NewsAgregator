/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.xd720p.mainAuthorization;

import web.http.HttpBrowser;

/**
 *
 * @author xd720p
 */
public abstract class OAuthImpl implements OAuth{

    //Все поля и методы, общие для ВСЕХ классов авторизации (и для вк, и для инстаграмма, и т.д.) помещаем сюда
    private HttpBrowser browser;
    private String accessToken, oAuthURI;
    
    public OAuthImpl(){
        browser = new HttpBrowser(null);
    }
    
    /**
    * 
    * Метод принимает на вход параметры необходимые для отправки OAuth запроса,
    * формирует запрос для конкретной соц. сети с указанными параметрами.
    * Если какие-либо параметры отсутствуют, то запрос формируется 
    * без данных параметров. Результатом функции является access_token, 
    * полученный при помощи протокола OAuth.
    * @param socURI адрес для отправки OAuth запроса
    * @param clientID уникальный номер приложения в данной соц. сети
    * @param redirectURI адрес отправки токена
    * @param display тип отображения окна авторизации
    * @param scope набор разрешений в данной соц. сети
    * @param responseType тип ответа: code или token
    * @return возвращается полученный access_token либо номер ошибки
    * @author xd720p
    */
    public String sendOAuthRequest(String socURI, String clientID, String redirectURI, String display, 
    String scope, String responseType) {
        //добавить проверку строк на null
        String uri=socURI+
        "client_id="+clientID+'&'+
        "redirect_uri="+redirectURI+'&'+
        "display="+display+'&'+
        "scope="+scope+'&'+
        "response_type"+responseType;
        
        browser.setCurrentURI(uri);
        
        return null;
    }
    
    /**
     * Данный метод проверяет является ли сохранённый access_token актуальным
     * На указанный api отправляется запрос(какой именно ещё не ясно) с access_token
     * и возвращается ответ. В случае, если токен не актуален, пользователю будет предложена
     * повторная авторизация
     * @param accessToken
     * @return нужно ли возвращать что-либо?
     * @author xd720p
     */
    public Boolean isAuthorized(String accessToken) {
        
        return null;
    }
    
}
