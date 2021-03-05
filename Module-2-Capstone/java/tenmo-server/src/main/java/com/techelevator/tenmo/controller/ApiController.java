package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountsDAO;
import com.techelevator.tenmo.dao.AccountsSqlDAO;
import com.techelevator.tenmo.dao.TransfersDAO;
import com.techelevator.tenmo.dao.TransfersSqlDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.dao.UserSqlDAO;
import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;

/*******************************************************************************************************
 * This is where you code any API controllers you may create
********************************************************************************************************/
@RestController
//@PreAuthorize("isAuthenticated()")
public class ApiController {
	private AccountsDAO accountsDAO;
	private TransfersDAO transfersDAO;
	private UserDAO userDAO;
	
	public ApiController(AccountsDAO accountsDAO, TransfersDAO transfersDAO, UserDAO userDAO) {
		this.accountsDAO = accountsDAO;
		this.transfersDAO = transfersDAO;
		this.userDAO = userDAO;
		
	}
//	@PreAuthorize("permitAll()")  // allow anyone regardless of their login status to access this method
    @RequestMapping(path = "/account/{userId}", method = RequestMethod.GET)
    public Accounts getAccountByUserId(@PathVariable Long userId) {
		//Long Id = Long.valueOf(userId);
    	//System.out.println("path received: /balance/" + userId);
        return accountsDAO.getAccountByUserId(userId);
	}
    
    ///// ********** VERIFY THIS LATER  *****************
    
//	@RequestMapping(path="/transfers", method= RequestMethod.GET) // The following function will handle /departments path 
//	public List<Transfers> listAllTransfers() {
//		List<Transfers> theTransfers;
//		theTransfers = transfersDAO.getAllTransfers();
//		return theTransfers;
//	}
	
//    @RequestMapping(path = "/transfers/{transferId}", method = RequestMethod.GET)
//   public Transfers getTransferById(@PathVariable Long transferId) {
//    	return transfersDAO.getTransfersByTransferId(transferId);
//	}
    
    @RequestMapping(path = "/balance/{userId}", method = RequestMethod.GET)
    public Accounts getBalanceByUserId(@PathVariable Long userId) {
		    	
        return accountsDAO.getBalanceByUserId(userId);
	}
 
    @RequestMapping(path = "/{accountId}/transfers", method = RequestMethod.GET)
    public List<Transfers> getTransfersByUserAccount(@PathVariable Long accountId) {
    	return transfersDAO.getTransfersByAccount(accountId);
    }
 
    @RequestMapping(path = "/transfers/{transferId}", method = RequestMethod.GET)
    public Transfers getTransferByTransferId(@PathVariable Long transferId) {
    	return transfersDAO.getTransfersByTransferId(transferId);
    }
    
//    @RequestMapping(path = "/transfers/{transferId}", method = RequestMethod.POST)
//    public void updateTransfers(@RequestBody Transfers transfer, @PathVariable Long transferId) {
//    	transfersDAO.updateTransfers(transfer, transferId);
//    }
    @RequestMapping(path = "/account/balance", method = RequestMethod.POST)
    public void updateBalance(@RequestBody Accounts account, double balance) {
    	accountsDAO.updateBalance(account, balance);
    }
    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public List<User> findAllUsers(){
    	List<User> findAll;
    	findAll = userDAO.findAll();
    	return findAll;
    }
    
    @RequestMapping(path = "/user/{username}/id", method = RequestMethod.GET)
    public int getIdFromUsername(@PathVariable String username) {
    	return userDAO.findIdByUsername(username);
    }
    
    @RequestMapping(path = "/user/{id}/username", method = RequestMethod.GET)
    public String getUsernameFromId(@PathVariable Long id) {
    	return userDAO.findUsernameById(id);
    }
	
    
    @RequestMapping(path = "/account/{accountNumber}/username", method = RequestMethod.GET)
    public String findUsernameByAccount(@PathVariable Long accountNumber) {
    	return userDAO.findUsernameByAccount(accountNumber);
    }
    @RequestMapping(path = "/transfers/{transferId}", method = RequestMethod.POST)
    public Transfers pushTransfer(@RequestBody Transfers transfer, @PathVariable Long transferId) {
    	return transfersDAO.pushTransfer(transfer);
    }
	
    
    
    
    
    
   
    
	
}
