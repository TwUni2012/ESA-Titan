/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.restful.newsticker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tiloW7-2012
 */
public class LoadArticles {

    public void load(String url) {
        //load the content of the article to extract the preview image url
        try {
            HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // read the response from the restful service
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                //look for <meta property="og:image" content="..."/>
                Logger.getLogger(LoadArticles.class.getName()).log(Level.INFO, line);
                
            }
            reader.close();


        } catch (MalformedURLException mue) {
        } catch (IOException ioe) {
        } catch (Exception e) {
        }
    }
}
