/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.restful.newsticker;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author tiloW7-2012
 */
@ManagedBean
//@Named
//@SessionScoped
public class RestfulLink {
    
    private String domain = "http://content.guardianapis.com/";
    private String category = "world";
    private String format = "?format=xml";
    private String parameter = "&order-by=newest&date-id=date%2Flast24hours";

    public RestfulLink() {
        Logger.getLogger(RestfulLink.class.getName()).log(Level.INFO, "### RestfulLink Objekt wurde erstellt");
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getDomain() {
        return domain;
    }

    public String getFormat() {
        return format;
    }
    
    public String getUrl() {
        return domain + category + format + parameter;
    }
    
    public void parse(ActionEvent e) {
        Logger.getLogger(RestfulLink.class.getName()).log(Level.INFO, "### Link soll geparst werden");
//        XMLParser xmlP = new XMLParser(this.getUrl());
//        xmlP.parse();
    }
}
