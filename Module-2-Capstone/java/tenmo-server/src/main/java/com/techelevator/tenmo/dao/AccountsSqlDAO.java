package com.techelevator.tenmo.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.Transfers;
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
	public void updateBalances(Transfers transfer) {
		String updateMyBalance = "update accounts set balance = (balance - ?) where account_id = ?";
		jdbcTemplate.update(updateMyBalance, transfer.getAmount(), transfer.getAccountFrom());
		String updateYourBalance = "update accounts set balance = (balance + ?) where account_id = ?";
		jdbcTemplate.update(updateYourBalance, transfer.getAmount(), transfer.getAccountTo());
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
	}
	
	@Override
	public Accounts getBalanceByAccountId(Long accountId) {
		String sqlQuery = "select * from accounts where account_id = ?";
		SqlRowSet theRowSet = jdbcTemplate.queryForRowSet(sqlQuery, accountId);
		Accounts returnedAccount = new Accounts();
		while(theRowSet.next()) {
			returnedAccount = mapRowToAccounts(theRowSet);
		}
		return returnedAccount;
	}
	
    private Accounts mapRowToAccounts(SqlRowSet rs) {
        Accounts account = new Accounts();
        account.setAccountId(rs.getLong("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(rs.getDouble("balance"));
        return account;
    }
}
