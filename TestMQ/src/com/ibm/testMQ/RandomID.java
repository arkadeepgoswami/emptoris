package com.ibm.testMQ;

import java.math.BigInteger;
import java.security.SecureRandom;
public class RandomID {

	
	private SecureRandom random = new SecureRandom();

	  public String nextRandomId() {
	    return new BigInteger(130, random).toString(32).substring(0, 16);
	  }
	  
	  
	  public static void main(String args[])
	  {
		  RandomID rndid=new RandomID();
		  System.out.println(rndid.nextRandomId().toString().substring(0, 16));
		  
	  }
	  
}
