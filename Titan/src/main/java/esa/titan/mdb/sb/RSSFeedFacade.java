/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.mdb.sb;

import esa.titan.mdb.entity.RSSFeed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author strongerthanbefore
 */
@Stateless
public class RSSFeedFacade extends AbstractFacade<RSSFeed> {
//  @PersistenceContext(unitName = "esa_Titan_war_1.0-SNAPSHOTPU")

    @PersistenceContext(unitName = "PersitenceUnitTitan")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RSSFeedFacade() {
        super(RSSFeed.class);
    }
}
