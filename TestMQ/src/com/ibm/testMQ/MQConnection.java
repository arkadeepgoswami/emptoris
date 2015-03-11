package com.ibm.testMQ;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.NamingException;

public class MQConnection {
	
	private Session mqSession;
	private MessageProducer mqSender;
	
	public MessageProducer getMqSender() {
		return mqSender;
	}

	public void setMqSender(MessageProducer mqSender) {
		this.mqSender = mqSender;
	}

	public Session getMqSession() {
		return mqSession;
	}

	public void setMqSession(Session mqSession) {
		this.mqSession = mqSession;
	}

	public String createMQConnection(String jndiName,String queueName)
	{
QSender qs = new QSender();
		
		try {
		
			qs.setConnection("jms/"+jndiName);
			
			qs.setQueue("jms/"+queueName);
		
			this.setMqSession(qs.getSession());
			this.setMqSender(qs.getSender());
			return "success";
		}
		catch (JMSException e) {
			// TODO Auto-generated catch block
		
			e.printStackTrace();
			return "JMSException: "+e.getMessage();
		}catch (NamingException  e) {
			// TODO: handle exception
		
			e.printStackTrace();
			return "NamingException: "+e.getMessage();
		}catch (Throwable  e) {
			// TODO: handle exception
		
			e.printStackTrace();
			return "Throwable Exception: "+e.getMessage();
		}
		
		
		
		
	}
	
	public String snedMQMessage(String xmlData)
	{
		try {
			Message msg =this.getMqSession().createTextMessage(xmlData);
			this.getMqSender().send(msg);
			return "success";
		} catch (JMSException e) {
			// TODO Auto-generated catch block
		
			e.printStackTrace();
			return "JMSException: "+e.getMessage();
		}catch (Throwable  e) {
			// TODO: handle exception
		
			e.printStackTrace();
			return "Throwable Exception: "+e.getMessage();
		}
		
	}

}
