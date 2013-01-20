/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.restful.newsticker;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author tiloW7-2012
 */
@ManagedBean
public class XMLParser {

//    private String url;

    public XMLParser() {
//        this.url = url;
    }

    public void parse(String url) {
        Logger.getLogger(XMLParser.class.getName()).log(Level.INFO, url + " wird geparst");
        URL u;
        InputStream is = null;
        DataInputStream dis;
        String s;

        try {
            u = new URL(url);

            is = u.openStream();
            dis = new DataInputStream(new BufferedInputStream(is));
            while ((s = dis.readLine()) != null) {
//                Logger.getLogger(XMLParser.class.getName()).log(Level.INFO, s);
//                System.out.println(s);
            }
        } catch (MalformedURLException mue) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.WARNING, "### MalformedURLException");
        } catch (IOException ioe) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.WARNING, "### IOException");
        } finally {
            try {
                is.close();
            } catch (IOException ioe) {
                Logger.getLogger(XMLParser.class.getName()).log(Level.WARNING, "### IOException");
            }
        }
    }
}
