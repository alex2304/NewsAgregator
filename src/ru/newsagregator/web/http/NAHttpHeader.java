/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.newsagregator.web.http;

import java.io.Serializable;
import java.util.List;
import org.apache.http.NameValuePair;

/**
 *
 * @author Leha
 */
public class NAHttpHeader implements Serializable{
    private String name;
    private List<NameValuePair> parameters;
    
    public NAHttpHeader(String name, List<NameValuePair> parameters){
        this.name = name;
        this.parameters = parameters;
    }
    
    /**
     * Метод toString() возвращает строковое представление данного объекта. 
     * 
     * @return 
     */
    @Override
    public String toString(){
        String result = "";
        result += (name == null) ? "null: ": name + ": ";
        if (parameters == null){ result += "null"; return result; }
        int i = 0;
        for (NameValuePair curr: parameters){
            if (curr != null){
                result += (curr.getValue() == null) ? curr.getName() : curr.getName() + "=" + curr.getValue();
                result += (i == parameters.size() - 1) ? "": ";";
            }
            i++;
        }
        return result;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the parameters
     */
    public List<NameValuePair> getParameters() {
        return parameters;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(List<NameValuePair> parameters) {
        this.parameters = parameters;
    }
    
    
    
}
