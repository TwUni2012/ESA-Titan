/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author tiloW7-2012
 */
@ManagedBean
@RequestScoped
public class IndexBean {
    
    private String message = "Hello World";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
