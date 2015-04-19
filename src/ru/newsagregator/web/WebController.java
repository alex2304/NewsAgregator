/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web;

import ru.newsagregator.web.agregators.AgregatorsController;
import ru.newsagregator.web.auth.Authorization;

/**
 * Cвоеобразный "барьер" между веб-частью (отправка-принятие запросов, атворизация, получение новостей из всех соц. сетей, и т.д)
 * и клиент-частью приложения.
 * Предоставляет функции авторизации, получения новостей в СТАНДАРТИЗИРОВАННОМ виде.
 * @author Leha
 */
public class WebController {
    private AgregatorsController agregatorsController; //контроллер, работающий со всеми агрегаторами
    private Authorization authorization; //класс авторизации во всех сетях
}
