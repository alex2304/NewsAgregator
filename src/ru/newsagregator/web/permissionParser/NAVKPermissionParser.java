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
    private String loc;
    private Elements permissionButton;
    
    public NAVKPermissionParser(String page) {
        loc=page;
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
        permissionButton=permissionPage.select("button");
        if (isPermissionPage(permissionButton)) {
            String[]loca = loc.split("function");
            loc=loca[1];
            loca=loc.split("\"");
            loc=loca[1];
            return loc;
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
