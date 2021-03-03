package com.techelevator.tenmo.dao;
import java.util.List;

import com.techelevator.tenmo.model.Accounts;

public interface AccountsDAO {
	

	
	Accounts getAccountsByUserId(Long userId);
	
	double updateBalance(Long accountId,double balance);
	
	double getBalanceByUserId(Long userId);

}
