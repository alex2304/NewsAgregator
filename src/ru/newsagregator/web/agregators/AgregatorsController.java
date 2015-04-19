/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.agregators;

import ru.newsagregator.web.agregators.vk.VkAgregator;

/**
 * Класс контролирует и синхронизирует работу всех агрегаторов.
 * Преобразует новости из сетей в стандартизованный вид
 * @author Leha
 */
public class AgregatorsController {
    private VkAgregator vkAgregator; //агрегатор новостей из ВК
    private boolean agregateVk, agregateInstagramm;
    //private InstagrammAgregator instagrammAgregator; //агрегатор новостей из инстаграмма
    
    public AgregatorsController(String vkAccessToken, String vkUserId, String instagrammAccessToken){
        if (vkAccessToken != null && vkUserId != null){
            vkAgregator = new VkAgregator(vkAccessToken, vkUserId);
            agregateVk = true;
        } else agregateVk = false;
        if (instagrammAccessToken != null){
            /*instagrammAgregator = new InstagrammAgregator(instagrammAccessToken);
            agregateInstagramm = true; */
        } else agregateInstagramm = false; 
    }

    /**
     * @return Может ли класс агрегировать из вк
     */
    public boolean canAgregateVk() {
        return agregateVk;
    }

    /**
     * @return Может ли класс агрегировать из инстаграмма
     */
    public boolean canAgregateInstagramm() {
        return agregateInstagramm;
    }
    
    public String getGroups(){
        return null;
    }
}
