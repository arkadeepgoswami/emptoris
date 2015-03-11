<%@page import="javax.naming.NamingException"%>
<%@page import="javax.jms.JMSException"%>
<%@page import="javax.jms.Message"%>
<%@page import="com.ibm.testMQ.QSender,com.ibm.testMQ.*"%>
<%@page import="com.ibm.esh.armsoh.unittest.*,com.ibm.esh.armsoh.common.*,com.ibm.esh.armsoh.util.*,org.apache.xerces.impl.dv.util.Base64"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%
		//QSender qs = new QSender();
		MQConnection mqCon= new MQConnection();
		
		try {
		String jndiName=request.getParameter("qcf");
		String queueName=request.getParameter("q");
		
		String refNum=request.getParameter("refnum");
		String txtMsg=request.getParameter("txtmsg");
		
		
		//	qs.setConnection("jms/"+jndiName);
		//	qs.setConnection("jms/BHQCF");
		//	qs.setQueue("jms/"+queueName);
		//	qs.setQueue("jms/BHQ");
		//	out.println("Queue Set Successfull");
		
		
		String conn=mqCon.createMQConnection(jndiName, queueName);
		if(conn.equals("success"))
		{
		out.println("Connection Set Successfull");
		out.println();
		
		
		}
		else
		{
		out.println("Connection Set Failure: "+conn);
		out.println();
		}
			
			
					
		CreateXML xmlmsg= new CreateXML();
		xmlmsg.getXMLmessage(refNum,txtMsg);
		String xmldata= xmlmsg.getXmldata();
		
			
		//	Message msg =  qs.getSession().createTextMessage("Testing putting message in MQ..");
		//	Message msg =  qs.getSession().createTextMessage(xmldata);
		//		Message msg = mqCon.getMqSession().createTextMessage(xmldata);
		//out.println("Message creation Successfull");
		//qs.getSender().send(msg);
		//out.println("Message sending Successfull");
		
		
		
		String sendStatus=mqCon.snedMQMessage(xmldata);
	
			
			if(sendStatus.equals("success"))
			
			{
			out.println("Message creation and sending Successfull");
			out.println();
			}
			else
			{
			out.println("Message creation and sending Failure: "+sendStatus);
			out.println();
			}
		} 
	/*	catch (JMSException e) {
			// TODO Auto-generated catch block
			out.println("JMSException: "+e.getMessage());
			e.printStackTrace();
		}
		catch (NamingException  e) {
			// TODO: handle exception
			out.println("NamingException: "+e.getMessage());
			e.printStackTrace();
		}
		*/
		catch (Throwable  e) {
			// TODO: handle exception
			out.println("Throwable Exception: "+e.getMessage());
			e.printStackTrace();
		}



 %>

</body>
</html>