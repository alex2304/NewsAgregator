/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.auth.oauth;

/**
 *
 * @author Leha
 */
public interface OAuth {
    String performAuthorization(String login, String password);
    Boolean isAuthorized(String accessToken);
}
