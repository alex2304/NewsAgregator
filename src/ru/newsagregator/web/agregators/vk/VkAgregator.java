/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.agregators.vk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ru.newsagregator.web.http.NAHttpBrowser;
import ru.newsagregator.web.http.NAHttpResponse;

/**
 * Класс для агрегации чего-либо из вк.
 * Параметры для агрегации передаются в с
 * @author Leha
 */
public class VkAgregator {
    private final String apiVersion = "5.30";
    private final VkApi vkApi;
    private VkApiException lastError;
    private final NAHttpBrowser browser;
    
    public VkAgregator(String accessToken, String userId){
        this.vkApi = new VkApi(accessToken, userId, apiVersion);
        this.browser = new NAHttpBrowser(null, false);
    }
    
    /**
     * Формирует строку из указанных параметров для указанного метода. Отправляет запрос и возвращает ответ.
     * @param methodParams массив имён параметров
     * @param params массив значений параметров
     * @return 
     */
    private String executeRequest(String methodName, String[] paramNames, String[] paramValues){
        lastError = null;
        List<VkApiParam> paramsList = new ArrayList<VkApiParam>();
        if (paramValues != null)
            for (int i = 0; i < paramNames.length; i++){
                if (paramValues[i] != null)
                    paramsList.add(new VkApiParam(paramNames[i], paramValues[i]));
            }
        String uri = vkApi.makeQueryString(methodName, paramsList);
        return NAHttpResponse.resolveResponseContent(browser.sendGetRequest(uri));
    }
    
    /**
     * Возвращает список сообществ, на которые подписан пользователь.
     * Количество параметров: 5.
     * Описание параметров:
     * 1. Целое положительное число до 1000. Количество сообществ, которые необходимо вернуть.
     * 2. Целое положительное число. Смещение относительно начала списка подмножеств, с которого начинать выборку.
     * 3. Список слов через запятую. Фильтр групп, на которые подписан пользователь. Возможные значения: admin, editor, moder, groups, publics, events.
     * 4. Число 0 либо 1. Если 1 - будет возвращена подробная информация о группах.
     * 5. Список слов через запятую. Список дополнительных полей, которые необходимо вернуть. Возможные значения: city, country, place, description, wiki_page, members_count, counters, start_date, finish_date, can_post, can_see_all_posts, activity, status, contacts, links, fixed_post, verified, site, can_create_topic. 
     * @param params Список из 5 параметров, описанных выше. Если параметр не используется - поставить на его место null.
     * @return Строку ответа сервера API либо null, если отправка не удалась.
     */
    public String getGroups(String[] params){
            return executeRequest(VkApiMethods.GROUPS_GET, VkApiMethods.GROUPS_GET_PARAMS, params);
    }
    
    /**
     * Возвращает список друзей пользователя.
     * Количество параметров: 6
     * Описание параметров:
     * 1. Строка.
     * 2. Положительное число. 
     * 3. Положительное число.
     * 4. Положительное число.
     * 5. Список строк, разделённых запятой.
     * 6. Строка.
     * @param params Список из 6 параметров, описанных выше. Если параметр не используется - поставить на его место null.
     * @return Строку ответа сервера API либо null, если отправка не удалась.
     */
    public String getFriends(String[] params){
        return executeRequest(VkApiMethods.FRIENDS_GET, VkApiMethods.FRIENDS_GET_PARAMS, params);
    }
    
    /**
     * Возвращает список новостей, которые доступны пользователю.
     * Количество параметров: 9
     * Описание параметров:
     * 1. Список строк, разделённых через запятую.
     * 2. Число 0 либо 1. 
     * 3. Время в формате unixtime. 
     * 4. Время в формате unixtime.
     * 5. Положительное число.
     * 6. Строка.
     * 7. Строка.
     * 8. Положительное число.
     * 9. Список строк, разделённых запятой.
     * @param params Список из 9 параметров, описанных выше. Если параметр не используется - поставить на его место null.
     * @return Строку ответа сервера API либо null, если отправка не удалась.
     */
    public String getNews(String[] params){
        return executeRequest(VkApiMethods.NEWS_GET, VkApiMethods.NEWS_GET_PARAMS, params);
    }
    
}
