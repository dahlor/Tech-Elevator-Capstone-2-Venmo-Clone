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

/*******************************************************************************************************
 * This is where you code any API controllers you may create
********************************************************************************************************/
@RestController
@PreAuthorize("isAuthenticated()")
public class ApiController {
	private AccountsDAO accountsDAO;
	private TransfersDAO transfersDAO;
	private UserDAO userDAO;
	public ApiController() {
		this.accountsDAO = new AccountsSqlDAO();
		this.transfersDAO = new TransfersSqlDAO();
		this.userDAO = new UserSqlDAO();
		
	}
	@PreAuthorize("permitAll()")  // allow anyone regardless of their login status to access this method
    @RequestMapping(path = "/balance/{userId}", method = RequestMethod.GET)
    public double getAccountByUserId(@PathVariable Long userId) {
        return accountsDAO.getBalanceByUserId(userId);
	}
}
