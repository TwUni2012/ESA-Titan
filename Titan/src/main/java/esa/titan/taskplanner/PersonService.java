/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.taskplanner;

import esa.titan.mdb.entity.RSSFeed;
import esa.titan.mdb.sb.AbstractFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author tiloW7-2012
 */
//@ManagedBean
@Stateless
public class PersonService extends AbstractFacade<Person> {

//    @PersistenceContext(unitName = "PersitenceUnitTitan")
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
    
//    public boolean login(Person person) {
//        TypedQuery<Person> query = em.createNamedQuery("select name, password from Person where name = '" + 
//                person.getName()+ "' and password = '" + person.getPassword() + "'" , Person.class);
//        if(query.getResultList().size() > 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }
    
}
