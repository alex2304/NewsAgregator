/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package networks.vk;

import web.authorization.AuthorizationImpl;

/**
 *
 * @author Leha
 */
public class VkAuthorization extends AuthorizationImpl{
    
    // <editor-fold defaultstate="collapsed" desc="Раскрыть, прочитать и удалить то, что внутри">
    
    //экземпляр класса AuthorizationImpl можно создать, только если имплементировать
    //абстрактный метод (это плюшка interface в Java).
    //Но так как AuthorizationImpl ещё и класс - наследнику этого класса (в данном случае это VkAuthorization) 
    //будут доступны все поля и остальные методы класса AuthorizationImpl
    static AuthorizationImpl gg = new AuthorizationImpl() { 

        @Override
        public String performAuthorization(String email, String password) {
            return null; //To change body of generated methods, choose Tools | Templates.
        }
    };
    public void test(){
        String g = gg.s; //поле s наследуется из класса AuthorizationImpl 
    }
    
    // </editor-fold>
    
    
    
    @Override
    public String performAuthorization(String email, String password) {
        return null;
    }
    
}
