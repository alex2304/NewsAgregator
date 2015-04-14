/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.agregators;

import ru.newsagregator.web.agregators.vk.VkAgregator;

/**
 * Класс контролирует и синхронизирует работу всех агрегаторов.
 * Предоставляет новости из сетей в нестандартизированном виде
 * @author Leha
 */
public class AgregatorsController {
    private VkAgregator vkAgregator; //агрегатор новостей из ВК
    //private InstagrammAgregator instagrammAgregator; //агрегатор новостей из инстаграмма
}
