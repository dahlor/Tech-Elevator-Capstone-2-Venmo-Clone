package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.Transfers;
@Component
public class TransfersSqlDAO implements TransfersDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public TransfersSqlDAO(JdbcTemplate jdbcTemplate) {
		
		 this.jdbcTemplate = jdbcTemplate;
	}
	

	@Override
	public List<Transfers> getAllTransfers() {
		ArrayList<Transfers> getListOfTransfers = new ArrayList<Transfers>();
		String sqlQuery = "Select * from transfers";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery);
		if(results.next()) {
			Transfers theTransfers = mapRowToTransfer(results);
			getListOfTransfers.add(theTransfers);
		}				          			   			
		return getListOfTransfers;
	}
	@Override
	public Transfers pushTransfer(Transfers transfers) {
		Transfers theTransfer = new Transfers();
		String sqlQuery = "insert into transfers (transfer_type_id,transfer_status_id,account_from,account_to,amount) " +
		                   " values (?,?,?,?,?) ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery,transfers);
		while(results.next()) {
			theTransfer = mapRowToTransfer(results);
			
		}
		
		return theTransfer;
		
		
		
	}

	// MAKE THIS A LIST ALSO!
	
	@Override
	public Transfers getTransfersByTransferId(Long transferId) {
		Transfers theTransfer = null;
		String sqlQuery = "select * from transfers where transfer_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery,transferId);
		while(results.next()) {
			theTransfer = mapRowToTransfer(results);
		}
		return theTransfer;
		
	}
	public Transfers getTransfersByUserId(Long userId) {
		Transfers theTransferById = new Transfers();
		String sqlQuery = "select transfer_id,transfer_type_id,transfer_status_id,account_from,account_to,amount,users.user_id,username " + 
				"from transfers" + 
				"join accounts" + 
				"on transfers.transfer_id = accounts.account_id" + 
				"join users" + 
				"on accounts.user_id = users.user_id ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery,userId);
		while(results.next()) {
			theTransferById = mapRowToTransfer(results);
		}
		return theTransferById;
	}
	
	public List<Transfers> getTransfersByAccount(Long accountId) {
		List<Transfers> theTransferByAccount = new ArrayList<>();
		String sqlQuery = "select * from transfers inner join transfer_statuses on transfer_statuses.transfer_status_id = transfers.transfer_status_id inner join transfer_types on transfer_types.transfer_type_id = transfers.transfer_type_id where account_from = ? or account_to = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, accountId, accountId);
		while(results.next()) {
			Transfers theOtherTransferByAccount = mapRowToTransfer(results);
			theTransferByAccount.add(theOtherTransferByAccount);
		}
		return theTransferByAccount;
	}
		
	public void updateTransfers( Transfers transfer, Long transferId) {
		Transfers theUpdateTransfer = new Transfers();
		String sqlQuery = "insert into( transfer_id,transfer_type_id,transfer_status_id,account_from,account_to,amount) values(?,?,?,?,?,? )";
		theUpdateTransfer.setTransferId(getNextTransferId());
		jdbcTemplate.update(sqlQuery,theUpdateTransfer.getTransferId(),theUpdateTransfer.getTransferTypeId(),
				            theUpdateTransfer.getTransferStatusId(),
				            theUpdateTransfer.getAccountFrom(),
				            theUpdateTransfer.getAccountTo(),
				            theUpdateTransfer.getAmount());
		//return newUpdatedTransfer;
		
	}
	
		
		
		
	
	private Transfers mapRowToTransfer(SqlRowSet results) {
		Transfers theTransfer;
		theTransfer = new Transfers();
		theTransfer.setTransferId(results.getLong("transfer_id"));
		theTransfer.setTransferTypeId(results.getLong("transfer_type_id"));
		theTransfer.setTransferStatusId(results.getLong("transfer_status_id"));
		theTransfer.setAccountFrom(results.getLong("account_from"));
		theTransfer.setAccountTo(results.getLong("account_to"));
		theTransfer.setAmount(results.getDouble("amount"));
		return theTransfer;
	}
	private long getNextTransferId() {
		SqlRowSet nextTransferIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('seq_transfer_id')");
		if(nextTransferIdResult.next()) {
			return nextTransferIdResult.getLong(1);
		
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new department");
		}
	}

}
