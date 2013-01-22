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
public class LoadArticles {

    public void load(String url) {
        //load the content of the article to extract the preview image url

            /*
             HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection();
             connection.setRequestMethod("GET");
             connection.connect();

             // read the response from the restful service
             BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             String line;
             StringBuffer result = new StringBuffer();
             while ((line = reader.readLine()) != null) {
             //look for <meta property="og:image" content="..."/>
             Logger.getLogger(LoadArticles.class.getName()).log(Level.INFO, line);
             result.append(line);
                
             }
             reader.close();
             parseHTML(result.toString());
             * */
            parseHTML(url);
    }

    public ArrayList<Article> parseHTML(String url) {
        ArrayList<Article> articles = new ArrayList<Article>();

        Document doc;
        try {
            doc = Jsoup.connect(url).get();

            int i = 0;
            Elements contentElements = doc.getElementsByTag("content");
            for (Element e : contentElements) {

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
                        webUrlContent = webUrlContentElements.text();
                    } catch (Exception eeee) {
                    }
                } catch (Exception ee) {
                }
                Logger.getLogger(LoadArticles.class.getName()).log(Level.INFO, i + "# webTitle: " + webTitle);
                Logger.getLogger(LoadArticles.class.getName()).log(Level.INFO, i + "### webPublicationDate: " + webPublicationDate);
                Logger.getLogger(LoadArticles.class.getName()).log(Level.INFO, i + "###### webUrl: " + webUrl);
                Logger.getLogger(LoadArticles.class.getName()).log(Level.INFO, i + "######## imageUrl: " + imageUrl);
                Logger.getLogger(LoadArticles.class.getName()).log(Level.INFO, i + "######## webUrlContent: " + webUrlContent);
                i++;
                Article article = new Article(webTitle, webPublicationDate, webUrl, imageUrl, webUrlContent);
                articles.add(article);
            }
        } catch (IOException ex) {
            Logger.getLogger(LoadArticles.class.getName()).log(Level.SEVERE, null, ex);
        }
        return articles;
    }
}
