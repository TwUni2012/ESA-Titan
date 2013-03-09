/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.taskplanner;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author tiloW7-2012
 */
@ManagedBean
//@RequestScoped
public class User {
    private String name = "";
    private String password = "";
    
    public User() {
        Logger.getLogger(User.class.getName()).log(Level.INFO, "new User");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String login() {
        Logger.getLogger(User.class.getName()).log(Level.INFO, "Name: " + this.name);
        Logger.getLogger(User.class.getName()).log(Level.INFO, "Password: " + this.password);
        // database access to validate the users
        if(name.equalsIgnoreCase("tester") && password.equalsIgnoreCase("tester")) {
            return "taskplannerLogin";
        } else {
            return "";
        }
    }
}
