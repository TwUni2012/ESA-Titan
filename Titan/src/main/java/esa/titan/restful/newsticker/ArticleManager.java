/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.restful.newsticker;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tilo
 */
public class ArticleManager {

    private ArrayList<Article> articles = new ArrayList<Article>();
    private int currentArticleIndex = 0;
    private TheGuardianArticleLoader theGuardianArticleLoader;
    private boolean loaded = false;

    public ArticleManager() {
    }

    public void nextArticle() {
        currentArticleIndex++;

        if (articles.size() <= currentArticleIndex) {
            currentArticleIndex = 0;
        }
        Logger.getLogger(Newsticker.class.getName()).log(Level.INFO, "### nextArticle wurde aufgerufen");
        Logger.getLogger(Newsticker.class.getName()).log(Level.INFO, "currentArticleIndex{0}", currentArticleIndex);
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public void addArticle(Article article) {
        articles.add(article);
    }

    public void addArticle(ArrayList<Article> articles) {
        articles.addAll(articles);
    }

    public void loadFirstElement(String url) {
        theGuardianArticleLoader = new TheGuardianArticleLoader(url);
        Article firstArticle = theGuardianArticleLoader.loadFirstArticle();
        clear();
        addArticle(firstArticle);
        loaded = true;
    }

    public void loadAllOtherArticleElements() {
        if (loaded == true) {
            loaded = false;
            loadAllOtherArticles();
        }
    }

    private void loadAllOtherArticles() {
        clearWithoutFirstArticleElement();
        Logger.getLogger(Newsticker.class.getName()).log(Level.INFO, "### loadAllOtherArticleElements: ");
        ArrayList<Article> nextArticles = theGuardianArticleLoader.nextArticles();
        Logger.getLogger(Newsticker.class.getName()).log(Level.INFO, "### nextArticles-size: {0}", nextArticles.size());
        addArticle(nextArticles);
    }

    public void clear() {
        articles.clear();
        currentArticleIndex = 0;
    }

    public void clearWithoutFirstArticleElement() {
        for (int i = 1; i < articles.size(); i++) {
            articles.remove(i);
        }
    }

    public Article getCurrentArticle() {
        try {
            return articles.get(currentArticleIndex);
        } catch (NullPointerException npe) {
            Logger.getLogger(Newsticker.class.getName()).log(Level.WARNING, "NullPointerException: something went wrong in 'getCurrentArticle'");
        } catch (Exception e) {
            Logger.getLogger(Newsticker.class.getName()).log(Level.WARNING, "something went wrong in 'getCurrentArticle'");
        }

        Article dummy = new Article("article not found",
                "article not found",
                "article not found",
                "article not found",
                "article not found");
        return dummy;
    }
}
