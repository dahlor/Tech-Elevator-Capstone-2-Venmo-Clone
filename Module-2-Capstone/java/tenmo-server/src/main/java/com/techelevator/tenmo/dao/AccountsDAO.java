package com.techelevator.tenmo.dao;
import java.util.List;

import com.techelevator.tenmo.model.Accounts;

public interface AccountsDAO {
	
	Accounts getAccountByUserId(Long userId);
	
    void updateBalance(Accounts account, double balance);
	
	Accounts getBalanceByUserId(Long userId);
}
