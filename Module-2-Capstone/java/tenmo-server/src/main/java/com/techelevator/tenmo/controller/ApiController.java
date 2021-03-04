package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
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
    @RequestMapping(path = "/balance/{userId}", method = RequestMethod.GET)
    public Accounts getAccountByUserId(@PathVariable Long userId) {
		//Long Id = Long.valueOf(userId);
    	System.out.println("path received: /balance/" + userId);
        return accountsDAO.getBalanceByUserId(userId);
	}
    
    ///// ********** VERIFY THIS LATER  *****************
    
	@RequestMapping(path="/transfers", method= RequestMethod.GET) // The following function will handle /departments path 
	public List<Transfers> listAllTransfers() {
		List<Transfers> theTransfers;
		theTransfers = transfersDAO.getAllTransfers();
		return theTransfers;
	}
	
    @RequestMapping(path = "/transfers/{transferId}", method = RequestMethod.GET)
    public Transfers getTransferById(@PathVariable Long transferId) {
    	return transfersDAO.getTransfersByTransferId(transferId);
	}
    
	
}
