package com.ibm.test;
import com.dicarta.webservices.faults.AuthenticationFault;
import com.dicarta.webservices.faults.AuthorizationFault;
import com.dicarta.webservices.faults.ObjectNotFoundFault;
import com.dicarta.webservices.faults.SystemFault;

import com.dicarta.webservices.services.contract.ContractServices_BindingStub;
import com.dicarta.webservices.services.contract.ContractServices_ServiceLocator;

import com.dicarta.webservices.services.contract.UpdateTermRequest;
import com.dicarta.webservices.services.contract.GetTermResponse;
import com.dicarta.webservices.services.contract.SetExternalPartyData;
import com.dicarta.webservices.types.domain.ExternalParties;
import com.dicarta.webservices.types.domain.PartyType;


import com.dicarta.webservices.types.common.Authentication;
import com.dicarta.webservices.types.common.Credential;
import com.dicarta.webservices.types.domain.Contract;
import com.dicarta.webservices.types.domain.ContractReference;
import com.dicarta.webservices.types.domain.ExternalParty;
import com.dicarta.webservices.types.domain.Term;
import javax.activation.DataHandler;

/**
 *  The following code illustrates how to call a webservice using a 
 *  java client stub.   All classes required to make this call are available
 *  in the jar file webservices-client.jar, available in the jars directory
 *  of the isdk
 */

public class TestExternalPartySet {

    public static void main(String[] args) throws Exception {
//        if (args.length != 4) {
//           usage();
//        }
//
//        String hostUrl = args[0];
//        String userId = args[1];
//        String password = args[2];
//        String contractUuid = args[3];
        String hostUrl = "https://ibmblueharmonysbssm.emptoris.com";
        String userId = "arkgoswa";
        String password = "Viperstrikes@060";
        String contractUuid = "231614dd9ebf4bc48755c27d3ca402e7";
        
        String externalParty_Type="Organization";
        String externalParty_ID="ec3d9ee966da4692a76da2d39f3792c9";
        externalPartySetTest(hostUrl, userId, password, contractUuid,externalParty_Type,externalParty_ID);

    }

    public static void externalPartySetTest(String hostUrl, String userId, String password, String contractUuid,String externalParty_Type,String externalParty_ID) {

        ContractServices_BindingStub binding = null;

        // The following code obtains the binding stub for a given
        // webservice (Contract, Organization, etc).
        try {
            java.net.URL endpoint;
            // This address needs to contain whatever host:port on which
            // the diCarta Contracts service is deployed
            String serviceAddress = hostUrl + 
                "/webservices/services/ContractServices";

            endpoint = new java.net.URL(serviceAddress);
            binding = (ContractServices_BindingStub)
               new ContractServices_ServiceLocator().getContractServices(
               endpoint);
        } catch (Exception e) {
        	
         //   e.printStackTrace();
            System.exit(1);
        }

        // Time out after a minute
        binding.setTimeout(60000);

        // If the client program is going to be making multiple webservice 
        // calls in close sucesssion we can ask the server to maintain a
        // session for this client, for this example we wont.
        binding.setMaintainSession(false);

        // Make the webservice call

        try {

             // Build the Authentication piece
             Credential credential = new Credential();
             credential.setPassword(password);
             Authentication auth = new Authentication(userId, credential);

            
             
             
             
             ExternalParties extParties=new ExternalParties();
             System.out.println(extParties.getTypeDesc());
        com.dicarta.webservices.types.domain.ExternalParty[] newExtparty = new com.dicarta.webservices.types.domain.ExternalParty[1];
             
             com.dicarta.webservices.types.domain.ExternalParty extpartynew= new com.dicarta.webservices.types.domain.ExternalParty();
             
             PartyType pType=PartyType.fromString(externalParty_Type);
     
             extpartynew.setPartyType(pType);
             extpartynew.setPartyId(externalParty_ID);
             
             
             newExtparty[0]=extpartynew;   
             
            extParties.setExternalParty(newExtparty);
        
            //     System.out.println(extpartynew.toString());
         
             
             SetExternalPartyData termTest= new SetExternalPartyData();
             termTest.setContractId(contractUuid);
             termTest.setExternalParties(extParties);
            
              
             
             com.dicarta.webservices.services.contract.SetExternalPartyRequest request=new com.dicarta.webservices.services.contract.SetExternalPartyRequest();
             request.setAuthentication(auth); 
             
             
             request.setRequestData(termTest);
             
          
            
      //       System.out.println("request:"+request.getRequestData().toString());
             binding.setExternalParty(request);
        
             
             System.out.println("External Party Successfully added.");

        } 
        
    
    catch (AuthenticationFault e) {
             System.out.println ("Unable to authentication");
        } catch (AuthorizationFault e) {
             System.out.println ("Invalid security permission for operation");
        } catch (ObjectNotFoundFault e) {
             System.out.println ("Invalid contract specified");
        } catch (SystemFault e) {
             System.out.println ("Unknown fault occured");
        }
      
        catch (Exception e) {
       	     e.printStackTrace();
             System.out.println ("Unknown error occured");
        }

    }

    private static void usage() {
        System.out.println ("Usage:  java GetContract <hostUrl> <userId> <password> <contractUuid>");
        System.exit(1);
    }
}
