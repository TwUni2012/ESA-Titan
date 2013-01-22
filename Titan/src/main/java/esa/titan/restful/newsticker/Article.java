/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.restful.newsticker;

/**
 *
 * @author tiloW7-2012
 */
public class Article {

    String title;
    String publicationDate;
    String url;
    String imageUrl;
    String content;

    public Article(String title, String publicationDate, String url, String imageUrl, String content) {
        this.title = title;
        this.publicationDate = publicationDate;
        this.url = url;
        this.imageUrl = imageUrl;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
