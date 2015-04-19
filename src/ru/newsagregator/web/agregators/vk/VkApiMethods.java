/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.agregators.vk;

/**
 *
 * @author Leha
 */
public class VkApiMethods {
    //методы
    
    protected static final String GROUPS_GET = "groups.get",
                                  USERS_GET = "users.get";
    protected static final String API_URL = "https://api.vk.com/method/";
    protected static final String ACCESS_TOKEN = "access_token";
    
    protected static final String[] GROUPS_GET_PARAMS = {"&count=%s", "&offset=%s", "&filter=%s", "&extended=%s", "&fields=%s"};
}
