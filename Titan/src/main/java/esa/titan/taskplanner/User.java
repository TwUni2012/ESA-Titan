/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.taskplanner;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
    private static final Logger LOGGER =  Logger.getLogger(User.class.getName());
    private Person currentPerson;
    
    @EJB
    private PersonService personService;
    
    @EJB
    private CalendarBean calendarBean;
    
    
    public CalendarBean getCalendar() {
        return calendarBean;
    }
    
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
        Person person = new Person(name, password);
        boolean login = false;
        List<Person> personlist = personService.getPersons();
        for(Person p : personlist) {
            if((person.getName().equals(p.getName())) && (person.getPassword().equals(p.getPassword()))) {
                login = true;
                currentPerson = p;
            }
        }
        
        LOGGER.log(Level.INFO, "Login: " + login);
        
        LOGGER.log(Level.INFO, "Name: " + this.name);
        LOGGER.log(Level.INFO, "Password: " + this.password);
        // database access to validate the users
        if(login) {
            return "taskplannerLogin";
        } else {
            personService.create(person);
            return "";
        }
    }
    
    public Person getCurrentPerson() {
        if(currentPerson != null) {
            return currentPerson;
        }
        return new Person("", "");
    }
}
