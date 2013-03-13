/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.mdb;

import esa.titan.mdb.entity.RSSFeed;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author strongerthanbefore
 */
@MessageDriven(mappedName = "RSSQueue", activationConfig = {
  @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
  @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class RssMDB implements MessageListener {

  public RssMDB() {
  }

  @Override
  public void onMessage(Message message) {
    String msg = new String();
    TextMessage tmsg = null;
    tmsg = (TextMessage) message;
    try {
      msg = tmsg.getText();
    } catch (JMSException ex) {
      Logger.getLogger(RssMDB.class.getName()).log(Level.SEVERE, null, ex);
    }
    System.out.println(msg.toString());

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("esa_Titan_war_1.0-SNAPSHOTPU");
    EntityManager em = emf.createEntityManager();
//    
//    // ohne JTA
//    //    em.getTransaction().begin();
//    //    Fehler: Use UserTransaction
    RSSFeed rf = new RSSFeed();
    rf.setFeed(msg);
    System.out.println(rf);
    em.persist(rf);
    em.flush();
    RSSFeed rf2 = em.find(RSSFeed.class, rf.getId());
    System.out.println("RSSFeed-ID: " + rf2.getClass());
    //    em.getTransaction().commit();
    em.close();
    emf.close();



  }
}
