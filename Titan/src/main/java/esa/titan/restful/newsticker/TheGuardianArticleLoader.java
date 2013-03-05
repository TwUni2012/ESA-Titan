/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.restful.newsticker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author tiloW7-2012
 */
public class TheGuardianArticleLoader {

    private static final Logger LOGGER = Logger.getLogger(TheGuardianArticleLoader.class.getName());
    private String url;
    private int currentArticleIndex = 0;
    private int articleCount = 0;
    private Elements contentElements;
    private ArrayList<Article> articles = new ArrayList<Article>();

    public TheGuardianArticleLoader(String url) {
        this.url = url;
        loadContentElements();
    }

    private void loadContentElements() {
        LOGGER.log(Level.INFO, "loadContentElements()");
        Document doc;
        try {
            doc = Jsoup.connect(url).get();

            contentElements = doc.getElementsByTag("content");
            articleCount = contentElements.size();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    public Article getNextArticle() {
        LOGGER.log(Level.INFO, "getNextArticle()");
        LOGGER.log(Level.INFO, "currentArticleIndex: {0}", currentArticleIndex);
        
        Article article;

        if(articles.size() < articleCount) {
            article = loadNextArticle();
            currentArticleIndex++;
        } else {
            if(currentArticleIndex >= articles.size()) {
                currentArticleIndex = 0;
            } 
            article = articles.get(currentArticleIndex);
            currentArticleIndex++;
        }
        
        return article;
    }
    
    private Article loadNextArticle() {
         LOGGER.log(Level.INFO, "loadNextArticle()");
        Article article = null;

        if(contentElements != null) {
            Element content = contentElements.get(currentArticleIndex);
            article = getArticle(content, currentArticleIndex);
            articles.add(article);
        } 
 
        return article;
    }

    private Article getArticle(Element e, int i) {
        String webTitle = e.attr("web-title");
        String webPublicationDate = e.attr("web-publication-date");
        String webUrl = e.attr("web-url");
        String imageUrl = "";
        String webUrlContent = "";

        try {
            Document imageDoc = Jsoup.connect(webUrl).get();
            Elements metaElements = imageDoc.getElementsByTag("meta");
            for (Element element : metaElements) {
                try {
                    String property = element.attr("property");
                    if ("og:image".equals(property)) {
                        imageUrl = element.attr("content");
                        break;
                    }
                } catch (Exception eee) {
                }
            }
            try {
                Element webUrlContentElements = imageDoc.getElementById("article-body-blocks");
                String content = webUrlContentElements.text().substring(0, 400);
                int lastBlanc = content.lastIndexOf(" ");
                webUrlContent = content.substring(0, lastBlanc) + " ...";
            } catch (Exception eeee) {
            }
        } catch (Exception ee) {
        }
        LOGGER.log(Level.INFO, "#######");
        LOGGER.log(Level.INFO, "{0}# webTitle: {1}", new Object[]{i, webTitle});
        LOGGER.log(Level.INFO, "{0}### webPublicationDate: {1}", new Object[]{i, webPublicationDate});
        LOGGER.log(Level.INFO, "{0}###### webUrl: {1}", new Object[]{i, webUrl});
        LOGGER.log(Level.INFO, "{0}######## imageUrl: {1}", new Object[]{i, imageUrl});
        LOGGER.log(Level.INFO, "{0}######## webUrlContent: {1}", new Object[]{i, webUrlContent});

        return new Article(webTitle, webPublicationDate, webUrl, imageUrl, webUrlContent);
    }
}
