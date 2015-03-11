package com.ibm.test;
import com.dicarta.webservices.faults.AuthenticationFault;
import com.dicarta.webservices.faults.AuthorizationFault;
import com.dicarta.webservices.faults.ObjectNotFoundFault;
import com.dicarta.webservices.faults.SystemFault;

import com.dicarta.webservices.services.contract.ContractServices_BindingStub;
import com.dicarta.webservices.services.contract.ContractServices_ServiceLocator;

import com.dicarta.webservices.services.contract.UpdateTermRequest;
import com.dicarta.webservices.services.contract.GetTermResponse;
import com.dicarta.webservices.services.contract.AddTermData;




import com.dicarta.webservices.types.common.Authentication;
import com.dicarta.webservices.types.common.Credential;
import com.dicarta.webservices.types.domain.Contract;
import com.dicarta.webservices.types.domain.ContractReference;
import com.dicarta.webservices.types.domain.Term;
import javax.activation.DataHandler;

/**
 *  The following code illustrates how to call a webservice using a 
 *  java client stub.   All classes required to make this call are available
 *  in the jar file webservices-client.jar, available in the jars directory
 *  of the isdk
 */

public class TestAddTerms {

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
        
        String termName="contract_hdr_udf_net_value";
        String termValue="Add term Test Value";
        getTermsTest(hostUrl, userId, password, contractUuid,termName,termValue);

    }

    public static void getTermsTest(String hostUrl, String userId, String password, String contractUuid,String termName,String termValue) {

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

             // Build the request data piece

            // ContractReference reference = new ContractReference();
            
           //  reference.setId(contractUuid);
             
             
           //  Term termTest=new Term();
            // termTest.setLabel(internalPartyName);
             
             AddTermData termTest= new AddTermData();
             termTest.setContractId(contractUuid);
             termTest.setTermName(termName);
      
            
         
             
             com.dicarta.webservices.services.contract.AddTermRequest request=new com.dicarta.webservices.services.contract.AddTermRequest();
             request.setAuthentication(auth); 
             
             
             request.setRequestData(termTest);
             
          
            
             System.out.println("request:"+request.getRequestData().toString());
             binding.addTerm(request);
             
             
             System.out.println("Term Successfully added.");

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
