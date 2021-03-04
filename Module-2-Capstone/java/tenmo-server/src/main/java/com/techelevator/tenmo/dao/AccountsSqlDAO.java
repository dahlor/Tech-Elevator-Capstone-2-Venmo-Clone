package com.techelevator.tenmo.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.User;
@Component
public class AccountsSqlDAO implements AccountsDAO {

    private JdbcTemplate jdbcTemplate;

    public AccountsSqlDAO(DataSource aDataSource) {
        this.jdbcTemplate = new JdbcTemplate(aDataSource);
    }
	
	@Override
	public Accounts getAccountByUserId(Long userId) {
		String sqlQuery = "select account_id from accounts where user_id = ?";
		SqlRowSet theRowSet = jdbcTemplate.queryForRowSet(sqlQuery, userId);
		Accounts returnedAccount = new Accounts();
		returnedAccount = mapRowToAccounts(theRowSet);
		return returnedAccount;
	}

	@Override
	public void updateBalance(Accounts account, double balance) {
		String updateBalanceQuery = "insert into (account_id, user_id, balance) values (?, ?, ?)";
		jdbcTemplate.queryForRowSet(updateBalanceQuery, account.getAccountId(), account.getUserId(), balance);
	}

	@Override
	public Accounts getBalanceByUserId(Long userId) {
		String sqlQuery = "select * from accounts where user_id = ?";
		SqlRowSet theRowSet = jdbcTemplate.queryForRowSet(sqlQuery, userId);
		Accounts returnedAccount = new Accounts();
		while(theRowSet.next()) {
			returnedAccount = mapRowToAccounts(theRowSet);
			
		}
		
		return returnedAccount;
		
		//double returnedBalance = theRowSet.getDouble("balance");
		//return returnedBalance;
	}
	
    private Accounts mapRowToAccounts(SqlRowSet rs) {
        Accounts account = new Accounts();
        account.setAccountId(rs.getLong("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(rs.getDouble("balance"));
        return account;
    }

}
