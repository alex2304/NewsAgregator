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
    //private InstagrammAgregator instagrammAgregator; //агрегатор новостей из инстаграмма
    private boolean agregateVk, agregateInstagramm;
    
    public AgregatorsController(String vkAccessToken, String vkUserId, String instagrammAccessToken){
        setVkAgregator(vkAccessToken, vkUserId);
        setInstagrammAgregator(instagrammAccessToken);
    }
    
    public AgregatorsController(String vkAccessToken, String vkUserId){
        setVkAgregator(vkAccessToken, vkUserId);
        agregateInstagramm = false;
    }
    
    public AgregatorsController(String instagrammAccessToken){
        agregateVk = false;
        setInstagrammAgregator(instagrammAccessToken);
    }
    
    public boolean setVkAgregator(String vkAccessToken, String vkUserId){
        if (vkAccessToken != null && vkUserId != null){
            vkAgregator = new VkAgregator(vkAccessToken, vkUserId);
            agregateVk = true;
        } else {
            agregateVk = false;
        }
        return agregateVk;
    }
    
    public boolean setInstagrammAgregator(String instagrammAccessToken){
        if (instagrammAccessToken != null){
            /*instagrammAgregator = new InstagrammAgregator(instagrammAccessToken); */
            agregateInstagramm = true;
        } else agregateInstagramm = false; 
        return agregateInstagramm;
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
    
}
