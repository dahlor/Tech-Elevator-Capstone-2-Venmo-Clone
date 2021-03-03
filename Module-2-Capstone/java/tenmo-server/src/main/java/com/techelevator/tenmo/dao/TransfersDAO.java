package com.techelevator.tenmo.dao;
import java.util.List;

import com.techelevator.tenmo.model.Transfers;

public interface TransfersDAO {
	
	
	
	List<Transfers> getAllTransfers() ;
	
	Transfers create(Transfers transfers);
	
    Transfers getTransfersByTransferId();

}
