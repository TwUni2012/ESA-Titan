/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.restful.newsticker;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author tiloW7-2012
 */
@ManagedBean
@SessionScoped
public class RestfulLink implements Serializable{

    private String domain = "http://content.guardianapis.com/";
    private String category = "world";
    private String format = "?format=xml";
    private String parameter = "&order-by=newest&date-id=date%2Flast24hours";
    private static int counter = 0;
    private String time = "";
    private String[] countries = {"Hallo", "Welt", "Ich", "Hammer", "Blub"};
    private List<SelectItem> categories = new LinkedList<SelectItem>();
    private String selected = "world";
    private LoadArticles loadArticles;
    private Loader l;
    private boolean loaded = false;
    
    public RestfulLink() {
        Logger.getLogger(RestfulLink.class.getName()).log(Level.INFO, "### RestfulLink Objekt wurde erstellt");     
                    
        categories.add((new SelectItem("world", "world")));
        categories.add(new SelectItem("sport", "sport"));
        categories.add(new SelectItem("football", "football"));
        categories.add(new SelectItem("stage", "stage"));
        categories.add(new SelectItem("music", "music"));
        categories.add(new SelectItem("travel", "travel"));
        categories.add(new SelectItem("film", "film"));
        categories.add(new SelectItem("politics", "politics"));
        
        //loadFirstElement
        loadURL();
    }

    public String getSelected() {
        return selected;
    }
    
    public void setSelected(String selected) {
        this.selected = selected;
    }
    
    public List<SelectItem> getCategories() {
        return categories;
    }
    
    public void setCategories(List<SelectItem> categories) {
        this.categories = categories;
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
        return domain + getSelected() + format + parameter;
    }

    public String getTime() {
        if (countries.length == counter) {
            counter = 0;
        }
        String result = countries[counter];
        counter++;
        return result;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void loadURL() {
        String url = getUrl();
        Logger.getLogger(RestfulLink.class.getName()).log(Level.INFO, "#### loadURK: " + url);
//        loadArticles = new LoadArticles(url);
//        ArrayList<Article> articles = loadArticles.parseHTML(url);
//        ArticleManager.setArticles(articles);
        l = new Loader(url);
        l.loadFirstElement();
        loaded = true;
        
//        return "Load";
    }

    public void loadAllOtherArticleElements() {
        if((l != null) && (loaded == true)) {
            l.loadAllOtherArticleElements();
            loaded = false;
        }
    }
    
    /*
    private void loadFirstElement() {

//        loadArticles = new LoadArticles();
        Article firstArticle = loadArticles.loadFirstArticle();
        ArticleManager.clear();
        ArticleManager.addArticle(firstArticle);
    }

    @Asynchronous
    private void loadAllOtherArticleElements() {

        Logger.getLogger(RestfulLink.class.getName()).log(Level.INFO, "### loadAllOtherArticleElements: ");
                try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(RestfulLink.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(RestfulLink.class.getName()).log(Level.INFO, "### loadAllOtherArticleElements after 10 sec sleep");
        ArrayList<Article> nextArticles = loadArticles.nextArticles();
        Logger.getLogger(RestfulLink.class.getName()).log(Level.INFO, "### nextArticles-size: " + nextArticles.size());
        ArticleManager.addArticle(nextArticles);
    }
 */   
    public void parse(ActionEvent e) {
        Logger.getLogger(RestfulLink.class.getName()).log(Level.INFO, "### Link soll geparst werden");
    }
}
