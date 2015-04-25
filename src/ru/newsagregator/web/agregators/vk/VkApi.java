/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.agregators.vk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;

/**
 *
 * @author Leha
 */
public class VkApi {
    
    private final String userId, accessToken, version;
    private String templateString;
    private Formatter queryFormatter;
    
    public VkApi(String accessToken, String userId, String version){
        this.userId = userId;
        this.version = version;
        this.accessToken = accessToken;
        queryFormatter = new Formatter();
        makeTemplateString();
    }
    
    /**
     * Создаёт шаблонную строку запроса.
    */
    private void makeTemplateString() {
        this.templateString = VkApiMethods.API_URL + "%s?" + VkApiMethods.ACCESS_TOKEN + "=" + accessToken + "%s";
    }
    
    /**
     * Создаёт строку запроса для указанного метода и добавляет к ней указанные параметры.
     * @param methodName имя метода, к которому планируется обратиться
     * @param paramsList список параметров в нужном порядке
     * @return строка запроса
     */
    protected String makeQueryString(String methodName, List<VkApiParam> paramsList){
        String paramsStr = "";
        List<String> paramsValues = new ArrayList<String>();
        queryFormatter = new Formatter();
        Formatter paramsFormatter = new Formatter();
        paramsStr += "&user_id=%s";
        paramsValues.add(userId);
        paramsStr += "&v=%s";
        paramsValues.add(version);
        for (VkApiParam p : paramsList) {
            paramsStr += p.getName();
            paramsValues.add(p.getValue());
        }
        paramsFormatter.format(paramsStr, paramsValues.toArray());
        return queryFormatter.format(templateString, methodName, paramsFormatter.toString()).toString();
    }    
    
}
