package com.techelevator.tenmo.services;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;


import com.techelevator.tenmo.models.Accounts;
import com.techelevator.tenmo.models.Transfers;
import com.techelevator.tenmo.models.User;
import com.techelevator.view.ConsoleService;

/*******************************************************************************************************
 * This is where you code Application Services required by your solution
 * 
 * Remember:  theApp ==> ApplicationServices  ==>  Controller  ==>  DAO
********************************************************************************************************/

public class TenmoApplicationServices {
	
	public static String AUTH_TOKEN = "";   // Hold the JWT from the login process
      // It's public so it can accessed outside this class
      //      which OK since it's used outside this
// private final String INVALID_RE_MSG = "Invalid Reservation. Please enter the Hotel Id, Full Name, Checkin Date, Checkout Date and Guests";
	private final String BASE_URL;
	private final RestTemplate restTemplate = new RestTemplate();
	
	public TenmoApplicationServices(String url) {
		  BASE_URL = url;
	}
	
	public Accounts getBalanceByUserId(Long userId) throws AuthenticationServiceException{
	    Accounts myAccount = null;
	    try {
	      myAccount = restTemplate.exchange(BASE_URL + "balance/" + userId, HttpMethod.GET, makeAuthEntity(), Accounts.class).getBody();
	    } catch (RestClientResponseException ex) {
	      throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
	    }
	    return myAccount;
	  }
	
	public int getIdByUsername(String username) throws AuthenticationServiceException{
	    int myUserId;
	    try {
	      myUserId = restTemplate.exchange(BASE_URL + "user/" + username + "/id", HttpMethod.GET, makeAuthEntity(), int.class).getBody();
	    } catch (RestClientResponseException ex) {
	      throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
	    }
	    return myUserId;
	  }
	
	public String getUsernameById(Long userId) throws AuthenticationServiceException{
	    String myUsername;
	    try {
	      myUsername = restTemplate.exchange(BASE_URL + "user/" + userId + "/username", HttpMethod.GET, makeAuthEntity(), String.class).getBody();
	    } catch (RestClientResponseException ex) {
	      throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
	    }
	    return myUsername;
	  }
	
    public Transfers[] getTransfersByAccount(Long accountId) throws AuthenticationServiceException {
    	Transfers[] listOfTransfers = null;
	    try {
	      listOfTransfers = restTemplate.exchange(BASE_URL + "/" + accountId + "/transfers", HttpMethod.GET, makeAuthEntity(), Transfers[].class).getBody();
	    } catch (RestClientResponseException ex) {
	      throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
	    }
	    return listOfTransfers;
	  }
    
    public Transfers getTransfersByTransferId(Long transferId) throws AuthenticationServiceException {
    	Transfers myTransfers = null;
	    try {
	      myTransfers = restTemplate.exchange(BASE_URL + "/transfers/" + transferId, HttpMethod.GET, makeAuthEntity(), Transfers.class).getBody();
	    } catch (RestClientResponseException ex) {
	      throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
	    }
	    return myTransfers;
	  }
	
	
	  public User[] listUsers() throws AuthenticationServiceException {
		    User[] listOfUsers = null;
		    try {
		      listOfUsers = restTemplate.exchange(BASE_URL + "user", HttpMethod.GET, makeAuthEntity(), User[].class).getBody();
		    } catch (RestClientResponseException ex) {
		      throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		    }
		    return listOfUsers;
		  }
	  
	  public String findUsernameByAccount(Long accountNumber) throws AuthenticationServiceException {
		  String myUsername;
		    try {
		      myUsername = restTemplate.exchange(BASE_URL + "account/" + accountNumber + "/username", HttpMethod.GET, makeAuthEntity(), String.class).getBody();
		    } catch (RestClientResponseException ex) {
		      throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		    }
		    return myUsername;  
	  }
	  public Transfers createTransfersByUserId(Long transferId) throws AuthenticationServiceException {
	    	Transfers myTransfers = null;
		    try {
		      myTransfers = restTemplate.exchange(BASE_URL + "/transfers/" + transferId, HttpMethod.GET, makeAuthEntity(), Transfers.class).getBody();
		    } catch (RestClientResponseException ex) {
		      throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		    }
		    return myTransfers;
		  }
	  
	  public Transfers pushTransfer(Long transferId, Transfers transfer) throws AuthenticationServiceException {
		  Transfers myTransfers = null;
		    try {
		      myTransfers = restTemplate.exchange(BASE_URL + "/transfers/"+ transferId , HttpMethod.POST, makeAuthEntity(), Transfers.class).getBody();
		    } catch (RestClientResponseException ex) {
		      throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		    }
		    return myTransfers;
		  
	  }
	  
	/*
	 * public Transfers updateEverybodysBalances(Long accountFrom, Long accountTo,
	 * double amount) { Accounts accounts =
	 * sendTransferAccounts(accountFrom,accountTo,amount); try {
	 * restTemplate.put(BASE_URL + "account/" + accountFrom.,HttpMethod.POST,
	 * makeAuthEntity()); } catch (RestClientResponseException ex) { throw new
	 * AuthenticationServiceException(ex.getRawStatusCode() + " : " +
	 * ex.getResponseBodyAsString()); } }
	 */
	
	  
//	  public Transfers[] listTransfers(Long userId) throws AuthenticationServiceException {
//		    Transfers[] listOfTransfers = null;
//		    try {
//		      listOfTransfers = restTemplate.exchange(BASE_URL + "transfers/" + userId, HttpMethod.GET, makeAuthEntity(), Transfers[].class).getBody();
//		    } catch (RestClientResponseException ex) {
//		      throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
//		    }
//		    return listOfTransfers;
//		  }
	  private Transfers sendTransferAccounts(Long accountFrom,Long accountTo,double amount) {
		  Transfers accountsnumber = null ;
		 
			  accountsnumber.setAccountFrom(accountFrom);
			  accountsnumber.setAccountTo(accountTo);
			  accountsnumber.setAmount(amount);
			  accountsnumber.setTransferStatusId(Long.parseLong("2"));
			  accountsnumber.setTransferTypeId(Long.parseLong("2"));
			  return accountsnumber;
			 
			 
		  }
		
		  
	  
	
	private HttpEntity makeAuthEntity() {
		   HttpHeaders headers = new HttpHeaders();        // instantiate a header object for request
		    headers.setBearerAuth(AUTH_TOKEN);              // Set the "Bearer" attribute in the head for header to JWT
		                                                    // The "Bearer" attribute in a request header hold JWT
		     HttpEntity entity = new HttpEntity<>(headers);  // Create a properly formatted request by instantiating an entity
		    return entity;
		  }
	}