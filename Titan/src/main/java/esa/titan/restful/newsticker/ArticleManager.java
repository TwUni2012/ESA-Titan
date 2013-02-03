/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.restful.newsticker;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
public class ArticleManager implements Serializable {

    private Article dummy = new Article("Le Pong sours cross-Channel relations before Cameron EU speech",
            "2013-01-22T16:01:00Z",
            "http://www.guardian.co.uk/uk/2013/jan/22/french-gas-cloud-stink-south-east",
            "https://static-secure.guim.co.uk/sys-images/Guardian/Pix/pictures/2013/1/22/1358860652111/Lubrizol-factory-in-Rouen-004.jpg",
            "As a metaphor made literal it was almost");
    private ArrayList<Article> articles = new ArrayList<Article>();
    private int currentArticleIndex = 0;
    private String domain = "http://content.guardianapis.com/";
    private String category = "world";
    private String format = "?format=xml";
    private String parameter = "&order-by=newest&date-id=date%2Flast24hours";
    private int counter = 0;
    private String time = "";
    private String[] countries = {"Hallo", "Welt", "Ich", "Hammer", "Blub"};
    private List<SelectItem> categories = new LinkedList<SelectItem>();
    private String selected = "world";
    private LoadArticles loadArticles;
    private boolean loaded = false;

    public ArticleManager() {
        Logger.getLogger(ArticleManager.class.getName()).log(Level.INFO, "### ArticleManager Objekt wurde erstellt");
        initCategories();
        loadURL();
    }

    public String getArticleTitle() {
        return getCurrentArticle().title;
    }

    public String getArticlePublicationDate() {
        return getCurrentArticle().publicationDate;
    }

    public String getArticleUrl() {
        return getCurrentArticle().url;
    }

    public String getArticleImageUrl() {
        return getCurrentArticle().imageUrl;
    }

    public String getArticleContent() {
        String currentArticleContent = getCurrentArticle().content;

        if ("".equals(currentArticleContent)) {
            return "article not found";
        }
        return currentArticleContent;
    }

    private Article getCurrentArticle() {
        try {
            return articles.get(currentArticleIndex);
        } catch (NullPointerException npe) {
            Logger.getLogger(ArticleManager.class.getName()).log(Level.WARNING, "NullPointerException: something went wrong in 'getCurrentArticle'");
        } catch (Exception e) {
            Logger.getLogger(ArticleManager.class.getName()).log(Level.WARNING, "something went wrong in 'getCurrentArticle'");
        }
        return dummy;
    }

    public void nextArticle() {
        currentArticleIndex++;

        if (articles.size() <= currentArticleIndex) {
            currentArticleIndex = 0;
        }
        Logger.getLogger(ArticleManager.class.getName()).log(Level.INFO, "### nextArticle wurde aufgerufen");
        Logger.getLogger(ArticleManager.class.getName()).log(Level.INFO, "currentArticleIndex" + currentArticleIndex);
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public void openExternURL() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(getArticleUrl());
    }

    public void clear() {
        articles.clear();
        currentArticleIndex = 0;
    }

    public void addArticle(Article article) {
        articles.add(article);
    }

    public void addArticle(ArrayList<Article> articless) {
        articles.addAll(articless);
//        currentArticleIndex++;
    }

    public void clearWithoutFirstArticleElement() {
        for (int i = 1; i < articles.size(); i++) {
            articles.remove(i);
        }
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
        Logger.getLogger(ArticleManager.class.getName()).log(Level.INFO, "RestfulLink: loadURL(): " + url);

        loadFirstElement();
        loaded = true;
    }

    public void loadAllOtherArticleElements() {
        if (loaded == true) {
            loaded = false;
            loadAllOtherArticleElements2();
        }
    }

    private void loadFirstElement() {
        loadArticles = new LoadArticles(getUrl());
        Article firstArticle = loadArticles.loadFirstArticle();
        this.clear();
        this.addArticle(firstArticle);
    }

    private void loadAllOtherArticleElements2() {
        this.clearWithoutFirstArticleElement();
        Logger.getLogger(ArticleManager.class.getName()).log(Level.INFO, "### loadAllOtherArticleElements: ");
        ArrayList<Article> nextArticles = loadArticles.nextArticles();
        Logger.getLogger(ArticleManager.class.getName()).log(Level.INFO, "### nextArticles-size: " + nextArticles.size());
        this.addArticle(nextArticles);
    }
}
