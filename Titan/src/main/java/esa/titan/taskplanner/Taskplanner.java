/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.taskplanner;

import esa.titan.taskplanner.calendar.CalendarBean;
import esa.titan.taskplanner.entity.Person;
import esa.titan.taskplanner.entity.Task;
import esa.titan.taskplanner.persistence.PersonService;
import esa.titan.taskplanner.persistence.TaskService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


/**
 *
 * @author tiloW7-2012
 */
@ManagedBean
@SessionScoped
//@RequestScoped
public class Taskplanner {

    private static final Logger LOGGER = Logger.getLogger(Taskplanner.class.getName());
    private Person currentPerson;
    private List<Task> personTasks = new ArrayList<Task>();
    @EJB
    private PersonService personService;
    @EJB
    private CalendarBean calendarBean;
    @EJB
    private TaskService taskService;

    public CalendarBean getCalendar() {
        return calendarBean;
    }
    private String name = "a"; //rückgängig machen!
    private String password = "a"; //rückgängig machen!
    private String tasktext = "";
    private String hour,minute;

    public Taskplanner() {
        Logger.getLogger(Taskplanner.class.getName()).log(Level.INFO, "new User");
    }

    public String getTasktext() {
        return tasktext;
    }

    public void setTasktext(String tasktext) {
        this.tasktext = tasktext;
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

    public List<Task> getPersonTasks() {
        return personTasks;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public String getHour() {
        return hour;
    }
    
    public List<Task> getPersonTasksForSelectedDate() {

        try {
            LOGGER.log(Level.INFO, "getPersonTasksForSelectedDate {0}", personTasks.size());
        } catch (Exception e) {
        }

        List<Task> dateTasks = new ArrayList<Task>();

        try {
            dateTasks = taskService.getPersonTasksForSelectedDate(
                currentPerson.getId(),
                getCalendar().getSelectedDate().getYear(),
                getCalendar().getSelectedDate().getMonth(),
                getCalendar().getSelectedDate().getDate()
                );
        } catch (Exception e) {
        }

        for (Task t : personTasks) {
            try {
                LOGGER.log(Level.INFO, "\t-> {0}", t.getText());
            } catch (Exception e) {
            }
        }
        return dateTasks;
    }

    public String login() {
        Person person = new Person(name, password);
        boolean login = false;
        List<Person> personlist = personService.getPersons();
        for (Person p : personlist) {
            if ((person.getName().equals(p.getName())) && (person.getPassword().equals(p.getPassword()))) {
                login = true;
                currentPerson = p;
            }
        }

        LOGGER.log(Level.INFO, "Login: {0}", login);

        LOGGER.log(Level.INFO, "Name: {0}", this.name);
        LOGGER.log(Level.INFO, "Password: {0}", this.password);
        // database access to validate the users
        if (login) {
            return "taskplannerLogin";
        } else {
            personService.create(person);
            return "";
        }
    }

    public Person getCurrentPerson() {
        if (currentPerson != null) {
            return currentPerson;
        }
        return new Person("", "");
    }

    public void saveTask() {
        Date date = calendarBean.getSelectedDate();
        
        if (date != null) {
            int day = date.getDate();
            int month = date.getMonth();
            int year = date.getYear();
            Long personid = currentPerson.getId();
            Task task = new Task(tasktext, personid, hour, minute, day, month, year);
            LOGGER.log(Level.INFO, "New Task: {0}", task);
            taskService.create(task);
        }

        hour = "";
        minute = "";
        tasktext = "";
    }

    public void deleteTask(Integer taskId) {
        LOGGER.log(Level.INFO, "Delete Task {0}", taskId.intValue());
        taskService.delete(taskId.intValue());
    }
}
