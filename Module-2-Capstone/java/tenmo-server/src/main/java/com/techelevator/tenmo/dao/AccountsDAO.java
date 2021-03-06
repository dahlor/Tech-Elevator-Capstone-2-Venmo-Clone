package com.techelevator.tenmo.dao;
import java.util.List;

import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.Transfers;

public interface AccountsDAO {
	
	Long getAccountByUsername(String username);
		
	Accounts getAccountByUserId(Long userId);
	
    void updateBalances(Transfers transfer);
	
    Accounts getBalanceByAccountId(Long accountId);
    
	Accounts getBalanceByUserId(Long userId);
}
