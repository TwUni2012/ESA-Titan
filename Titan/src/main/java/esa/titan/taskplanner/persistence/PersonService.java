/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.taskplanner.persistence;

import esa.titan.mdb.sb.AbstractFacade;
import esa.titan.taskplanner.entity.Person;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


@Stateless
public class PersonService extends AbstractFacade<Person> {

    @PersistenceContext(unitName = "esa_Titan_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonService() {
        super(Person.class);
        Logger.getLogger(PersonService.class.getName()).log(Level.INFO, "new PersonService Objekt erstellt");
    }

    public List<Person> getPersons() {
        TypedQuery<Person> query = em.createQuery("select c from Person c", Person.class);
        return query.getResultList();
    }  
}
