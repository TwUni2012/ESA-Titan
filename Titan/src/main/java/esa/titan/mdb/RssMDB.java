/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.mdb;

import esa.titan.mdb.entity.RSSFeed;
import esa.titan.mdb.sb.RSSFeedFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

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
    
    RSSFeed rf = new RSSFeed();
    rf.setFeed(msg);
    RSSFeedFacade facade = new RSSFeedFacade();
    facade.create(rf);


    //RSSFeed rf = new RSSFeed();
    //RSSFeedFacade facade = new RSSFeedFacade();
    //facade.create(rf);
    System.out.println(message.toString());
  }
}
