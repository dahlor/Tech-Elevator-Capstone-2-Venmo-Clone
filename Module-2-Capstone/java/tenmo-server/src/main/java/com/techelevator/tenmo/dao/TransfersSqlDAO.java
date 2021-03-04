package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

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
	public Transfers create(Transfers transfers) {
		Transfers theTransfer = new Transfers();
		String sqlQuery = "insert into (transfer_id,transfer_type_id,transfer_status_id,account_from,account_to,amount) " +
		                   " values (?,?,?,?,?,?) ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery,transfers);
		while(results.next()) {
			theTransfer = mapRowToTransfer(results);
			
		}
		
		return theTransfer;
		
		
		
	}

	@Override
	public Transfers getTransfersByTransferId(Long transferId) {
		Transfers theTransfer = new Transfers();
		String sqlQuery = "select transfer_id,transfer_type_id,transfer_status_id,account_from,account_to,amount from transfers " + " where transfer_id = ? ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery,transferId);
		while(results.next()) {
			theTransfer = mapRowToTransfer(results);
			
		}
		return theTransfer;
		
		
		
		
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

}
