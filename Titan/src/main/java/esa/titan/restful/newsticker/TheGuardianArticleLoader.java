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

    private String url;

    public TheGuardianArticleLoader(String url) {
        this.url = url;
    }

    public Article loadFirstArticle() {
        Logger.getLogger(TheGuardianArticleLoader.class.getName()).log(Level.INFO, "LoadArticles: 'loadFirstArticle()'");
        Article article = null;

        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            int i = 0;
            Elements contentElements = doc.getElementsByTag("content");
            Element e = contentElements.get(i);
            article = getArticle(e, i);      
        } catch (IOException ex) {
            Logger.getLogger(TheGuardianArticleLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return article;
    }

    public ArrayList<Article> nextArticles() {
        Logger.getLogger(TheGuardianArticleLoader.class.getName()).log(Level.INFO, "LoadArticles: 'nextArticles()'");
        ArrayList<Article> articles = new ArrayList<Article>();

        Document doc;
        try {
            doc = Jsoup.connect(url).get();

            int i = 0;
            Elements contentElements = doc.getElementsByTag("content");
            for (Element e : contentElements) {
                i++;
                Article article = getArticle(e, i);
                if (i != 1) {
                    articles.add(article);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(TheGuardianArticleLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return articles;
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
        Logger.getLogger(TheGuardianArticleLoader.class.getName()).log(Level.INFO, "#######");
        Logger.getLogger(TheGuardianArticleLoader.class.getName()).log(Level.INFO, "{0}# webTitle: {1}", new Object[]{i, webTitle});
        Logger.getLogger(TheGuardianArticleLoader.class.getName()).log(Level.INFO, "{0}### webPublicationDate: {1}", new Object[]{i, webPublicationDate});
        Logger.getLogger(TheGuardianArticleLoader.class.getName()).log(Level.INFO, "{0}###### webUrl: {1}", new Object[]{i, webUrl});
        Logger.getLogger(TheGuardianArticleLoader.class.getName()).log(Level.INFO, "{0}######## imageUrl: {1}", new Object[]{i, imageUrl});
        Logger.getLogger(TheGuardianArticleLoader.class.getName()).log(Level.INFO, "{0}######## webUrlContent: {1}", new Object[]{i, webUrlContent});

        return new Article(webTitle, webPublicationDate, webUrl, imageUrl, webUrlContent);
    }
}
