/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.taskplanner;

import esa.titan.mdb.sb.AbstractFacade;
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

    public List<Person> getPersons() {
        TypedQuery<Person> query = em.createQuery("select c from Task c", Person.class);
        return query.getResultList();
    }
}
