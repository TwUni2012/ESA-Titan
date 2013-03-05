/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.restful.newsticker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tilo
 */
public class ArticleManager {

    private TheGuardianArticleLoader theGuardianArticleLoader;
    private Article currentArticle;
    private Article dummy = new Article("article not found",
                "article not found",
                "article not found",
                "article not found",
                "article not found");
        
    public ArticleManager() {
    }

    public void nextArticle() {
        Logger.getLogger(Newsticker.class.getName()).log(Level.INFO, "### nextArticle wurde aufgerufen");
        currentArticle = theGuardianArticleLoader.getNextArticle();
    }

    public void loadFirstElement(String url) {
        theGuardianArticleLoader = new TheGuardianArticleLoader(url);
        currentArticle = theGuardianArticleLoader.getNextArticle();
    }

    public Article getCurrentArticle() {
        try {
            return currentArticle;
//            return articles.get(currentArticleIndex);
        } catch (NullPointerException npe) {
            Logger.getLogger(Newsticker.class.getName()).log(Level.WARNING, "NullPointerException: something went wrong in 'getCurrentArticle'");
        } catch (Exception e) {
            Logger.getLogger(Newsticker.class.getName()).log(Level.WARNING, "something went wrong in 'getCurrentArticle'");
        }

        return dummy;
    }
}
