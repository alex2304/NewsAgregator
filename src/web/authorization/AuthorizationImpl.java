/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web.authorization;

/**
 *
 * @author Leha
 */
public abstract class AuthorizationImpl implements Authorization{

    //Все поля и методы, общие для ВСЕХ классов авторизации (и для вк, и для инстаграмма, и т.д.) помещаем сюда
    
    // <editor-fold defaultstate="collapsed" desc="Раскрыть, прочитать и удалить то, что внутри">
    public String s;
    // </editor-fold>
    
    @Override
    public abstract String performAuthorization(String email, String password); 
    
    
}
