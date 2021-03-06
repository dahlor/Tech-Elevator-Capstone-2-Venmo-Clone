package com.techelevator.tenmo.services;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;


import com.techelevator.tenmo.models.Accounts;
import com.techelevator.tenmo.models.Transfers;
import com.techelevator.tenmo.models.User;

/*******************************************************************************************************
 * This is where you code Application Services required by your solution
 * 
 * Remember:  theApp ==> ApplicationServices  ==>  Controller  ==>  DAO
********************************************************************************************************/

public class TenmoApplicationServices {
	
	public static String AUTH_TOKEN = "";
	private final String BASE_URL;
	private final RestTemplate restTemplate = new RestTemplate();
	
	public TenmoApplicationServices(String url) {
		  BASE_URL = url;
	}
	
	public Accounts getBalanceByUserId(Long userId) throws AuthenticationServiceException{
	    Accounts myAccount = null;
	    try {
	      myAccount = restTemplate.exchange(BASE_URL + "balance/" + userId, HttpMethod.GET, makeAuthEntity(AUTH_TOKEN), Accounts.class).getBody();
	    } catch (RestClientResponseException ex) {
	      throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
	    }
	    return myAccount;
	}
	
	public Accounts getBalanceByAccountId(Long accountId) throws AuthenticationServiceException{
	    Accounts myAccount = null;
	    try {
	      myAccount = restTemplate.exchange(BASE_URL + "account/"+accountId+"/balance", HttpMethod.GET, makeAuthEntity(AUTH_TOKEN), Accounts.class).getBody();
	    } catch (RestClientResponseException ex) {
	      throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
	    }
	    return myAccount;
	}
	
	public int getIdByUsername(String username) throws AuthenticationServiceException{
	    int myUserId;
	    try {
	      myUserId = restTemplate.exchange(BASE_URL + "user/" + username + "/id", HttpMethod.GET, makeAuthEntity(AUTH_TOKEN), int.class).getBody();
	    } catch (RestClientResponseException ex) {
	      throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
	    }
	    return myUserId;
	}
	
	public String getUsernameById(Long userId) throws AuthenticationServiceException{
	    String myUsername;
	    try {
	      myUsername = restTemplate.exchange(BASE_URL + "user/" + userId + "/username", HttpMethod.GET, makeAuthEntity(AUTH_TOKEN), String.class).getBody();
	    } catch (RestClientResponseException ex) {
	      throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
	    }
	    return myUsername;
	}
	
    public Transfers[] getTransfersByAccount(Long accountId) throws AuthenticationServiceException {
    	Transfers[] listOfTransfers = null;
	    try {
	      listOfTransfers = restTemplate.exchange(BASE_URL + "/" + accountId + "/transfers", HttpMethod.GET, makeAuthEntity(AUTH_TOKEN), Transfers[].class).getBody();
	    } catch (RestClientResponseException ex) {
	      throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
	    }
	    return listOfTransfers;
    }
    
    public Transfers getTransfersByTransferId(Long transferId) throws AuthenticationServiceException {
    	Transfers myTransfers = null;
	    try {
	      myTransfers = restTemplate.exchange(BASE_URL + "/transfers/" + transferId, HttpMethod.GET, makeAuthEntity(AUTH_TOKEN), Transfers.class).getBody();
	    } catch (RestClientResponseException ex) {
	      throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
	    }
	    return myTransfers;
	  }
	
	
	  public User[] listUsers() throws AuthenticationServiceException {
		    User[] listOfUsers = null;
		    try {
		      listOfUsers = restTemplate.exchange(BASE_URL + "user", HttpMethod.GET, makeAuthEntity(AUTH_TOKEN), User[].class).getBody();
		    } catch (RestClientResponseException ex) {
		      throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		    }
		    return listOfUsers;
	  }
	  
	  public String findUsernameByAccount(Long accountNumber) throws AuthenticationServiceException {
		  String myUsername;
		    try {
		      myUsername = restTemplate.exchange(BASE_URL + "account/" + accountNumber + "/username", HttpMethod.GET, makeAuthEntity(AUTH_TOKEN), String.class).getBody();
		    } catch (RestClientResponseException ex) {
		      throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		    }
		    return myUsername;  
	  }
	  
	  public Transfers createTransfersByUserId(Long transferId) throws AuthenticationServiceException {
	    	Transfers myTransfers = null;
		    try {
		      myTransfers = restTemplate.exchange(BASE_URL + "/transfers/" + transferId, HttpMethod.GET, makeAuthEntity(AUTH_TOKEN), Transfers.class).getBody();
		    } catch (RestClientResponseException ex) {
		      throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		    }
		    return myTransfers;
	  }
	  
	  public Transfers pushTransfer(Long transferId, Transfers transfer, String AUTH_TOKEN) throws AuthenticationServiceException {
		  Transfers myTransfers = null;
		    try {
		      myTransfers = restTemplate.postForObject(BASE_URL + "/transfers/"+transferId, makeTransferEntity(transfer, AUTH_TOKEN), Transfers.class);
		    } catch (RestClientResponseException ex) {
		      throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		    }
		    return myTransfers;
	  }
	  
	  public Transfers updateBalances(Long transferId, Transfers transfer) throws AuthenticationServiceException {
		  Transfers myTransfers = null;
		    try {
		      myTransfers = restTemplate.postForObject(BASE_URL + "/account/balances/update/"+transferId, makeTransferEntity(transfer, AUTH_TOKEN), Transfers.class);
		    } catch (RestClientResponseException ex) {
		      throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		    }
		    return myTransfers;
	  }
	  
	  public Long getNextTransferId() throws AuthenticationServiceException {
	    Long myNextTransferId = null;
			try {
		      myNextTransferId = restTemplate.exchange(BASE_URL + "/transfers/nextval", HttpMethod.GET, makeAuthEntity(AUTH_TOKEN), Long.class).getBody();
		    } catch (RestClientResponseException ex) {
		      throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		    }
		return myNextTransferId;
	}
	
	private HttpEntity makeAuthEntity(String AUTH_TOKEN) {
		HttpHeaders headers = new HttpHeaders();       
		headers.setBearerAuth(AUTH_TOKEN);                                                           
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}
	
	private HttpEntity<Transfers> makeTransferEntity(Transfers newTransfer, String AUTH_TOKEN) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity<Transfers> entity = new HttpEntity<>(newTransfer, headers);
		return entity;
	}
}