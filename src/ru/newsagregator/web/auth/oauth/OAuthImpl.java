/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.auth.oauth;

import java.io.UnsupportedEncodingException;
import java.util.List;
import ru.newsagregator.web.http.NAHttpBrowser;
import ru.newsagregator.web.http.NAHttpResponse;

/**
 *
 * @author xd720p
 */
public abstract class OAuthImpl implements OAuth{

    //Все поля и методы, общие для ВСЕХ классов авторизации (и для вк, и для инстаграмма, и т.д.) помещаем сюда
    private final String applicationId, scope, redirectURI, display, version, responseType; //параметры строки авторизации
    private final String oAuthURI; //домен для авторизации
    private String accessToken; //поле для ключа
    protected NAHttpBrowser browser; //браузер
    protected NAHttpResponse response; //http ответ
    protected OAuthException currException;
    
    /**
     * Объект класса будет создан успешно, если обязательные параметры не null. Иначе - исключение OAuthException.
     * @param OAuthURI (обязательный) адрес для отправки OAuth запроса (в формате "oauth.vk.com/authorization", без указания протокола и указания параметров)
     * @param applicationId (обязательный) уникальный номер приложения в данной соц. сети
     * @param scope (обязательный) набор разрешений в данной соц. сети
     * @param redirectURI (обязательный) адрес отправки токена
     * @param display тип отображения окна авторизации
     * @param version версия API
     * @param responseType тип ответа: code или token
     */
    protected OAuthImpl(String OAuthURI, String applicationId, String scope, String redirectURI, String display, String version, String responseType){
        if (OAuthURI == null || applicationId == null || scope == null || redirectURI == null) currException =  new OAuthException("Incorrect input parameters");
        this.oAuthURI = OAuthURI;
        this.applicationId = applicationId;
        this.scope = scope;
        this.redirectURI = redirectURI;
        this.display = display;
        this.version = version;
        this.responseType = responseType;
        try {
            browser = new NAHttpBrowser(getRequestURLFromParams(), false);
        } catch (UnsupportedEncodingException e){
            currException = new OAuthException("Can't encode redirect url");
        }
        if (browser.getCurrentURI() == null) currException = new OAuthException("Incorrect url for performing OAuth autorization");
    }
    
    protected String getAccessToken(){
        return this.accessToken;
    }
    
    protected String getRequestURLFromParams() throws UnsupportedEncodingException{
        String result = "https://";
        result += oAuthURI + "?" + OAuthFields.createParamString(applicationId, scope, redirectURI, display, version, responseType);
        return result;
    }
    
    protected OAuthException checkTroubles(){
        return currException;
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
    protected String sendOAuthRequest(String socURI, String clientID, String redirectURI, String display, 
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
    @Override
    public abstract Boolean isAuthorized(String accessToken);
    
    @Override
    public abstract String performAuthorization(String email, String password);
    
}
