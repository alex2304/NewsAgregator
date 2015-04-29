/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.main;

import java.io.IOException;
import ru.newsagregator.web.agregators.vk.VkAgregator;
import ru.newsagregator.web.auth.Authorization;

/**
 *
 * @author Leha
 */
public class NewsAgregator {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        Authorization autorization = new Authorization();
        String req = autorization.getVkAccessToken("xd720p.infoshell@gmail.com", "testinfoshell404");
        System.out.println(req);
        /*String accessToken = "24a5dfdb9c1f665832a8fda4fe5712e92e788dca420d38743a890560b242b6152000aa08f8b1501738bec";//req.split("access_token=")[1].split("&")[0];
        String userId = "280019163";//req.split("user_id=")[1];
        Thread.sleep(100);
        VkAgregator vk = new VkAgregator(accessToken, userId);
        String[] params = {"5", null, null, "1", "description,members_count"};
        System.out.println(vk.getGroups(params));*/
    }
    
}
