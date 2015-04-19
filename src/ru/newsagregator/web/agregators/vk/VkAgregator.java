/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.agregators.vk;

import java.util.ArrayList;
import java.util.List;
import ru.newsagregator.web.http.NAHttpBrowser;
import ru.newsagregator.web.http.NAHttpResponse;

/**
 * Класс для агрегации чего-либо из вк.
 * Параметры для агрегации передаются в с
 * @author Leha
 */
public class VkAgregator {
    private VkApi vkApi;
    private VkApiError lastError;
    private NAHttpBrowser browser;
    
    public VkAgregator(String accessToken, String userId){
        this.vkApi = new VkApi(accessToken, userId);
        this.browser = new NAHttpBrowser(null, false);
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
        List<VkApiParam> paramsList = new ArrayList<VkApiParam>();
        if (params != null)
            for (int i = 0; i < params.length; i++){
                if (params[i] != null)
                    paramsList.add(new VkApiParam(VkApiMethods.GROUPS_GET_PARAMS[i], params[i]));
            }
        String uri = vkApi.groupsGet(paramsList);
        return NAHttpResponse.resolveResponseContent(browser.sendGetRequest(uri));
    }
    
    
}
