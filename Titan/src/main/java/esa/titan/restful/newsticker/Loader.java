/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.restful.newsticker;

import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

/**
 *
 * @author tilo
 */
public class Loader {

    private LoadArticles loadArticles;

    public Loader() {
    }

    public Loader(String url) {
        loadArticles = new LoadArticles(url);
    }

    public void loadFirstElement() {
        Article firstArticle = loadArticles.loadFirstArticle();
        ArticleManager.clear();
        ArticleManager.addArticle(firstArticle);
    }

    @Asynchronous
    public void loadAllOtherArticleElements() {
        ArticleManager.clearWithoutFirstArticleElement();
        Logger.getLogger(RestfulLink.class.getName()).log(Level.INFO, "### loadAllOtherArticleElements: ");
        ArrayList<Article> nextArticles = loadArticles.nextArticles();
        Logger.getLogger(RestfulLink.class.getName()).log(Level.INFO, "### nextArticles-size: " + nextArticles.size());
        ArticleManager.addArticle(nextArticles);
    }
}
