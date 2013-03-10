/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.taskplanner;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
/**
 *
 * @author tiloW7-2012
 */
@Entity
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
//    @Temporal(javax.persistence.TemporalType.DATE)
//    @Column(name="taskdate")
//    private Date date;

    private String text;
    private Long personid;
    private String task_time;
    private int task_day;
    private int task_month;
    private int task_year;
//    @ManyToOne
//    private Person person;
    
    public Task() {
    }

    public Task(String text, Long personid, String time, int day, int month, int year) {
        this.text = text;
        this.personid = personid;
        this.task_time = time;
        this.task_day = day;
        this.task_month = month;
        this.task_year = year;
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
//
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }

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
        return "esa.titan.taskplanner.Task[ id=" + id + " ]";
    }
    
}
