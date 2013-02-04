/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.restful.newsticker;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author tiloW7-2012
 */
@ManagedBean
@SessionScoped
public class Newsticker implements Serializable {


    private List<SelectItem> categories = new LinkedList<SelectItem>();
    private String selected = "world";

    private TheGuardianLink theGuardianLink = new TheGuardianLink();
    private ArticleManager articleManager = new ArticleManager();
    

    public Newsticker() {
        Logger.getLogger(Newsticker.class.getName()).log(Level.INFO, "### ArticleManager Objekt wurde erstellt");
        initCategories();
        loadURL();
    }

    public void loadURL() {
        String url = getUrl();
        Logger.getLogger(Newsticker.class.getName()).log(Level.INFO, "RestfulLink: loadURL(): {0}", url);
        articleManager.loadFirstElement(url);
    }

    private void initCategories() {
        categories.add((new SelectItem("world", "world")));
        categories.add(new SelectItem("sport", "sport"));
        categories.add(new SelectItem("football", "football"));
        categories.add(new SelectItem("stage", "stage"));
        categories.add(new SelectItem("music", "music"));
        categories.add(new SelectItem("travel", "travel"));
        categories.add(new SelectItem("film", "film"));
        categories.add(new SelectItem("politics", "politics"));
    }

    public void openExternURL() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(articleManager.getCurrentArticle().url);
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

    public TheGuardianLink getTheGuardianLink() {
        return theGuardianLink;
    }
    
    public ArticleManager getArticleManager() {
        return articleManager;
    }

    public String getUrl() {
        return theGuardianLink.getDomain() + getSelected() + theGuardianLink.getFormat() + theGuardianLink.getParameter();
    }






}
