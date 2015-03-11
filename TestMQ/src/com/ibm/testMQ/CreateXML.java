package com.ibm.testMQ;

import org.apache.xerces.impl.dv.util.Base64;

import com.ibm.esh.armsoh.common.ArmsBean;
import com.ibm.esh.armsoh.common.ArmsLogXMLHandler;
import com.ibm.esh.armsoh.unittest.TestDriver;
import com.ibm.esh.armsoh.util.ArmsDataUtil;
import com.ibm.esh.armsoh.util.ArmsIDGen;
import com.ibm.esh.armsoh.util.IDGenerator;
import com.ibm.esh.armsoh.util.TimeAndDates;
import com.ibm.esh.armsoh.util.MD5Generator;


public class CreateXML {

	private String xmldata="";
	
	public String getXmldata() {
		return xmldata;
	}

	public void setXmldata(String xmldata) {
		this.xmldata = xmldata;
	}

	public ArmsBean createArmsBean(String refNum,String txtMsg) {
		ArmsBean ab = new ArmsBean();
		
		ab.setLogtype("BH");
		ab.setTimestamp("Place holder"); //XSD timestamp format required
		ab.setDomain("MISC");
		ab.setMsgformat("XML");
		ab.setDoctype("Document type");
		ab.setSender("BH_EMPTORIS");
		ab.setReceiver("Receiver System");
		//ab.setRefnum("EMPTORIS"+new RandomID().nextRandomId().toString());
		ab.setRefnum(refNum);
		ab.setStatus("Success");
		ab.setMd5hash("askjfkljdsfkjsdjfkd");
		
		ab.getArmsBizBean().setBiz_att1("biz1");
		ab.getArmsBizBean().setBiz_att2("biz2");
		ab.getArmsBizBean().setBiz_att3("biz3");
		ab.getArmsBizBean().setBiz_att4("biz4");
		ab.getArmsBizBean().setBiz_att5("biz5");
		ab.getArmsBizBean().setBiz_att6("biz6");
		ab.getArmsBizBean().setBiz_att7("biz7");
		ab.getArmsBizBean().setBiz_info_xml("<xxx>xxx</xxx><yyy>yyy</yyy><zzz>zzz</zzz>");
		
		ab.getArmsTechBean().setTech_att1("tech1");
		ab.getArmsTechBean().setTech_att2("tech2");
		ab.getArmsTechBean().setTech_att3("tech3");
		ab.getArmsTechBean().setTech_att4("tech4");
		ab.getArmsTechBean().setTech_att5("tech5");
		ab.getArmsTechBean().setTech_att6("tech6");
		ab.getArmsTechBean().setTech_att7("tech7");
		ab.getArmsTechBean().setTech_att7("tech8");
		ab.getArmsTechBean().setTech_info_xml("");

		ab.setAppmsg_code("Appmsg code");
		ab.setAppmsg_reason("Appmsg reason");
	//	ab.setAppmsg_text("EMPTORIS MQ Test");
		ab.setAppmsg_text(txtMsg);
		
		ab.setEmail_flag("email flag");
		ab.setEmail_addresses("email_addresses");
		ab.setEmail_body("email_body\nMy body \"\"&&\"\"<><><><");
		
		ab.setPayload_comp_b64("the big log message");
		
		return ab;
	}
	
	public byte [] createArmsLogXML(ArmsBean ab) throws Exception {
		ArmsLogXMLHandler alxh = new ArmsLogXMLHandler();
		
		byte [] arms_ba_xml = alxh.createARMSLogXML(ab);
		
		return arms_ba_xml;
	}
	
	
	/**
	 * @param args
	 */
	public void getXMLmessage(String refNum,String txtMsg) {
		// TODO Auto-generated method stub

		CreateXML td = new CreateXML();
		
		ArmsBean ab = td.createArmsBean(refNum,txtMsg);

		try {

			//Set unique id
			ab.setUniqueid (ab.getLogtype() + IDGenerator.getUniqueId(8) + ArmsIDGen.getNextID());
			
			//Set time
			ab.setTimestamp(TimeAndDates.getCurrentXSDdateTime());
			
			//Set payload & md5hash
			String payload_str = "This is a test payload";
			byte[] payload = payload_str.getBytes("UTF-8");
			
			ab.setMd5hash(MD5Generator.generateMD5Hash(payload)); //MD5 Hash
			
			payload = ArmsDataUtil.getCompressedTrans(payload);
			
			//Base64 encode the data so that it can be added to the ARMS Log XML.
			ab.setPayload_comp_b64(Base64.encode(payload));
			
			//Create the ARMS Log XML
			byte [] data = td.createArmsLogXML(ab);
			this.xmldata = new String(data, "UTF-8");
						
			System.out.println(xmldata);
			//return xmldata;
		} catch (Exception e) {
			this.xmldata = "XML Creation unsuccesful";
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}

	
}
