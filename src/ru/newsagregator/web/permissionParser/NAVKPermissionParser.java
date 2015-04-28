/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.newsagregator.web.permissionParser;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author Пётр
 */
public class NAVKPermissionParser {
    private Document permissionPage;
    private Elements permissionButton;
    
    public NAVKPermissionParser(String page) {
        permissionPage=Jsoup.parse(page);
        permissionButton=null;
    }
    /**
     * 
     * @return возвращает ture, если мы находимся на странице подвтерждения доступа к аккаунту
     * и false, если мы на другой странице
     */
    public boolean isPermissionPage(Elements permissionButt) {
       if (permissionButt!=null){
           for (int i=0; i<permissionButt.size(); i++) {
               if (permissionButt.eq(i).attr("id").equals("install_allow")) return true;
           }
           return false;
       } else return false; 
    }
    /**
     * Даная функция возвращает строку подтверждения, если мы находимся на странице получения доступа, иначе возвращает false
     * @return 
     */
    public String getAccess() {
        setPermissionButton(getPermissionPage().select("button"));
        if (isPermissionPage(permissionButton)) {
            return "https://login.vk.com/?act=grant_access&client_id=4812992&settings=124&redirect_uri=https%3A%2F%2Foauth.vk.com%2Fblank.html&response_type=token&direct_hash=dc780ffdfc27a87de0&token_type=0&v=5.29&state=&display=popup&ip_h=5a3db734e0163200f2&hash=e1dd13fca0a21013f6&https=1";
        } else {
            return null;  
        }
    }
    
    /**
     * @return the permissionPage
     */
    public Document getPermissionPage() {
        return permissionPage;
    }

    /**
     * @param permissionPage the permissionPage to set
     */
    public void setPermissionPage(Document permissionPage) {
        this.permissionPage = permissionPage;
    }

    /**
     * @param permissionButton the permissionButton to set
     */
    public void setPermissionButton(Elements permissionButton) {
        this.permissionButton = permissionButton;
    }

    /**
     * @return the permissionButton
     */
    public Elements getPermissionButton() {
        return permissionButton;
    }
}
