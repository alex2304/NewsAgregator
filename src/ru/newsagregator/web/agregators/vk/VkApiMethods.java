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
    
    protected static final String API_URL = "https://api.vk.com/method/";
    protected static final String ACCESS_TOKEN = "access_token";
    
    protected static final String GROUPS_GET = "groups.get",
                                  FRIENDS_GET = "friends.get",
                                  NEWS_GET = "newsfeed.get";
    
    protected static final String[] GROUPS_GET_PARAMS = {"&count=%s", "&offset=%s", "&filter=%s", "&extended=%s", "&fields=%s"};
    protected static final String[] FRIENDS_GET_PARAMS = {"&order=%s", "&list_id=%s", "&count=%s", "&offset=%s", "&fields=%s", "&name_case=%s"};
    protected static final String[] NEWS_GET_PARAMS = {"&filters=%s", "&return_banned=%s", "&start_time=%s", "&end_time=%s", "&max_photos=%s", "&source_ids=%s", "&start_from=%s", "&count=%s", "&fields=%s"};
}
