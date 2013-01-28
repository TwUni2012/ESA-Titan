/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.restful.newsticker;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author tiloW7-2012
 */
@ManagedBean
@SessionScoped
public class RestfulLink implements Serializable{

    // TODO Refactoring, Fehlerbehandlung, wenn Article nicht verfuegbar, bessere Objektverwaltung, Laden der Artikel in Asynchrone Methode auslagern
    
//    @EJB
//    private ArticleManager articleManager;
    private String domain = "http://content.guardianapis.com/";
    private String category = "world";
    private String format = "?format=xml";
    private String parameter = "&order-by=newest&date-id=date%2Flast24hours";
    private static int counter = 0;
    private String time = "";
    private String[] countries = {"Hallo", "Welt", "Ich", "Hammer", "Blub"};

    public RestfulLink() {
        Logger.getLogger(RestfulLink.class.getName()).log(Level.INFO, "### RestfulLink Objekt wurde erstellt");
//        IncrementCounterThread i = new IncrementCounterThread(this);
//        i.run();
        loadURK();
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

    public String getTime() {
        if (countries.length == counter) {
            counter = 0;
        }
        String result = countries[counter];
        counter++;
//        return Long.toString(System.currentTimeMillis());
        return result;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String loadURK() {
        String url = getUrl();
        Logger.getLogger(RestfulLink.class.getName()).log(Level.INFO, "#### loadURK: " + url);
        LoadArticles la = new LoadArticles();
        ArrayList<Article> articles = la.parseHTML(url);
        ArticleManager.setArticles(articles);

        return "Load";
    }

//    public int getCounter() {
//        return counter;
//    }
//
//    public void setCounter(int counter) {
//        this.counter = counter;
//    }
    public void parse(ActionEvent e) {
        Logger.getLogger(RestfulLink.class.getName()).log(Level.INFO, "### Link soll geparst werden");
//        XMLParser xmlP = new XMLParser(this.getUrl());
//        xmlP.parse();
//         xMLParser.parse('http://content.guardianapis.com')
    }
}
