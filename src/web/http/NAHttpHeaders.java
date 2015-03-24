/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web.http;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author Leha
 */
public class NAHttpHeaders {
    protected static final String[][] defaultHeaders = {{"Connection", "keep-alive"}, {"User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36"}};
    public static List<Header> getDefaultHeadersList(){
        List<Header> result = new ArrayList<Header>();
        for (String[] header : defaultHeaders){
            result.add(new BasicHeader(header[0], header[1]));
        }
        return result;
    }
}
