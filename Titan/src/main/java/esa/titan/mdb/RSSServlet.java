/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.mdb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author strongerthanbefore
 */
@WebServlet(name = "RSSServlet", urlPatterns = {"/RSSServlet"})
public class RSSServlet extends HttpServlet {
  @Resource(mappedName = "RSSQueue")
  private Queue rSSQueue;
  @Resource(mappedName = "QueueFactory")
  private ConnectionFactory jSMRss;

  /**
   * Processes requests for both HTTP
   * <code>GET</code> and
   * <code>POST</code> methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    String str = request.getParameter("rssurl");
    try {
      this.sendJMSMessageToRSSQueue(str);
      
    } catch (JMSException ex) {
      Logger.getLogger(RSSServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
    try {
      /* TODO output your page here. You may use following sample code. */
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet RSSServlet</title>");      
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>Servlet RSSServlet at " + request.getContextPath() + "</h1>");
      out.println("</body>");
      out.println("</html>");
    } finally {      
      out.close();
    }
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP
   * <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP
   * <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>

  private Message createJMSMessageForrSSQueue(Session session, Object messageData) throws JMSException {
    // TODO create and populate message to send
    TextMessage tm = session.createTextMessage();
    tm.setText(messageData.toString());
    return tm;
  }

  private void sendJMSMessageToRSSQueue(Object messageData) throws JMSException {
    Connection connection = null;
    Session session = null;
    try {
      connection = jSMRss.createConnection();
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      MessageProducer messageProducer = session.createProducer(rSSQueue);
      messageProducer.send(createJMSMessageForrSSQueue(session, messageData));
    } finally {
      if (session != null) {
        try {
          session.close();
        } catch (JMSException e) {
          Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot close session", e);
        }
      }
      if (connection != null) {
        connection.close();
      }
    }
  }
}
