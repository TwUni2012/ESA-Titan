/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.restful.newsticker;

/**
 *
 * @author tilo
 */
public class TheGuardianLink {

    private String format = "?format=xml";
    private String parameter = "&order-by=newest&date-id=date%2Flast24hours";
    private String domain = "http://content.guardianapis.com/";
    
    public TheGuardianLink() {
    }

    public String getFormat() {
        return format;
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
}
