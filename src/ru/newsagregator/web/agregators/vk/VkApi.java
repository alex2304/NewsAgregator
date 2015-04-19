/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.agregators.vk;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

/**
 *
 * @author Leha
 */
public class VkApi {
    
    private final String userId, accessToken;
    private String templateString;
    private Formatter queryFormatter;
    
    public VkApi(String accessToken, String userId){
        this.userId = userId;
        this.accessToken = accessToken;
        queryFormatter = new Formatter();
        makeTemplateString();
    }
    
    /**
     * Возвращает строку для запроса групп, на которые подписан пользователь.
     * @param paramsList список параметров
     * @return строка запроса
     */
    protected String groupsGet(List<VkApiParam> paramsList){
        String paramsString = "";
        List<String> paramsValues = new ArrayList<String>();
        queryFormatter = new Formatter();
        Formatter paramsFormatter = new Formatter();
        paramsString += "&user_id=%s";
        paramsValues.add(userId);
        for (VkApiParam p : paramsList) {
            paramsString += p.getName();
            paramsValues.add(p.getValue());
        }
        paramsFormatter.format(paramsString, paramsValues.toArray());
        queryFormatter.format(templateString, VkApiMethods.GROUPS_GET, paramsFormatter.toString());
        return queryFormatter.toString();
    }

    private void makeTemplateString() {
        this.templateString = VkApiMethods.API_URL + "%s?" + VkApiMethods.ACCESS_TOKEN + "=" + accessToken + "%s";
    }
}
