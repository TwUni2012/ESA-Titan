/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.taskplanner.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;
    private Long personid;
    private String task_time;
    private int task_day;
    private int task_month;
    private int task_year;

    public Task() {
    }

    public Task(String text, Long personid, String hour, String minute, int day, int month, int year) {
        this.text = text.replaceAll("\\r\\n", "<br/>");
        this.personid = personid;
        this.task_day = day;
        this.task_month = month;
        this.task_year = year;

        try {
            int h = Integer.parseInt(hour);
            int m = Integer.parseInt(minute);
            if (h >= 0 && h <= 23 && m >= 0 && m <= 59) {
                System.out.println("Range Check passed");
                this.task_time = (h < 10 ? "0" : "") + hour + ":" + (m < 10 ? "0" : "") + minute;
            } else {
                this.task_time = "";
            }
        } catch (Exception e) {
            this.task_time = "";
        }
    }

    public Long getPersonid() {
        return personid;
    }

    public void setPersonid(Long personid) {
        this.personid = personid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return task_time;
    }

    public void setTime(String time) {
        this.task_time = time;
    }

    public int getDay() {
        return task_day;
    }

    public void setDay(int day) {
        this.task_day = day;
    }

    public int getMonth() {
        return task_month;
    }

    public void setMonth(int month) {
        this.task_month = month;
    }

    public int getYear() {
        return task_year;
    }

    public void setYear(int year) {
        this.task_year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Task [" + hashCode() + "] " + getText();
    }
}
