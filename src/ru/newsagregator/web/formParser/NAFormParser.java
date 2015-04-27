/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.newsagregator.web.formParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;




/**
 *
 * @author Пётр
 */
public class NAFormParser {
    private Document parsingPage;
    private Elements inputParams;
    private List<NameValuePair> paramList;
    
    public NAFormParser(String page) throws IOException{
        parsingPage = Jsoup.parse(page);
        paramList = new ArrayList<>();
    }
    
    public List<NameValuePair> getInputFormParams(String email, String password) {
        setInputParams(getParsingPage().select("input"));
        for (int i=0; i<getInputParams().size(); i++) {       
            if (getInputParams().eq(i).attr("type").equals("hidden")) {
                getParamList().add(new BasicNameValuePair(getInputParams().eq(i).attr("name"), getInputParams().eq(i).attr("value")));
            }
        }
        getParamList().add(new BasicNameValuePair("email",email));
        getParamList().add(new BasicNameValuePair("pass",password));
        return getParamList();
        
    }


    /**
     * @return the parsingPage
     */
    public Document getParsingPage() {
        return parsingPage;
    }

    /**
     * @param parsingPage the parsingPage to set
     */
    public void setParsingPage(Document parsingPage) {
        this.parsingPage = parsingPage;
    }

    /**
     * @return the inputParams
     */
    public Elements getInputParams() {
        return inputParams;
    }

    /**
     * @param inputParams the inputParams to set
     */
    public void setInputParams(Elements inputParams) {
        this.inputParams = inputParams;
    }

    /**
     * @return the paramList
     */
    public List<NameValuePair> getParamList() {
        return paramList;
    }

    /**
     * @param paramList the paramList to set
     */
    public void setParamList(List<NameValuePair> paramList) {
        this.paramList = paramList;
    }
}
