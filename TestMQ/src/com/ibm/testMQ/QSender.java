package com.ibm.testMQ;

import java.util.Hashtable;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QSender {
	
	
	private Connection connection;

	public Connection getConnection() {
		return connection;
	}

	private Session session;

	public MessageProducer getSender() {
		return sender;
	}

	public Session getSession() {
		return session;
	}

	private MessageProducer sender;

	private InitialContext initContext;

	private String exceptionMessage;
	   
	   
	   private InitialContext getInitContext() throws NamingException {

			if (initContext == null) {
				Hashtable env = new Hashtable();

			   // env.put(Context.PROVIDER_URL, "iiop://192.168.195.144:2809");
			    env.put(Context.INITIAL_CONTEXT_FACTORY,"com.ibm.websphere.naming.WsnInitialContextFactory");

				initContext = new InitialContext(env);
			}
			return initContext;
		}
	   
	   public void setConnection(String connectionName) throws JMSException,
	      NamingException, Throwable {

	   try {
	     
	      ConnectionFactory factory = (ConnectionFactory)getInitContext()
	         .lookup(connectionName);
	      connection = factory.createConnection();
	      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	   } catch (Throwable e) {
	     
	      throw e;
	   }
	}
	   
	   
	   public void setQueue(String queueName) throws JMSException,
	      NamingException, Throwable {

	   try {
//	      if (connection != null)
//	         connection.stop();
//	      if (sender != null)
//	         sender.close();
	      Destination queue = (Destination) getInitContext()
	            .lookup(queueName);
	      sender = session.createProducer(queue);
	      sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	      sender.setPriority(4);
	      sender.setTimeToLive(0);
	      connection.start();
	   } catch (Throwable e) {
	     
	      throw e;
	   }
	}

	/**
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
			
		QSender qs = new QSender();
		try {
			qs.setConnection("jms/BHQCF");
			qs.setQueue("jms/BHQ");
			Message msg = qs.session.createTextMessage("Arup test");
			qs.sender.send(msg);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NamingException  e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
