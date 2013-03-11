/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.taskplanner;

import esa.titan.mdb.sb.AbstractFacade;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author tiloW7-2012
 */
@Stateless
public class TaskService extends AbstractFacade<Task> {

    @PersistenceContext(unitName = "PersitenceUnitTitan")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TaskService() {
        super(Task.class);
        Logger.getLogger(TaskService.class.getName()).log(Level.INFO, "new TaskService Objekt erstellt");
    }

    public List<Task> getTasks() {
        TypedQuery<Task> query = em.createQuery("SELECT c FROM Task c", Task.class);
        return query.getResultList();
    }

    public void delete(int id) {
        em.createQuery("DELETE FROM Task c WHERE c.id=:id").setParameter("id",id).executeUpdate();
    }

    public List<Task> getPersonTasksForSelectedDate(Long personid, int year, int month, int day) {
        TypedQuery<Task> query = em.createQuery("SELECT c FROM Task c WHERE "
                + "c.personid=:id "
                + "AND c.task_year=:year AND c.task_month=:month "
                + "AND c.task_day=:day", Task.class).setParameter("id",personid)
                .setParameter("year",year)
                .setParameter("month",month)
                .setParameter("day",day);
        return query.getResultList();
    }
}
