package com.techelevator.tenmo.dao;
import java.util.List;

import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.Transfers;

public interface TransfersDAO {
	
	
	
	List<Transfers> getAllTransfers() ;
	
	Transfers pushTransfer(Transfers transfers);
	
    Transfers getTransfersByTransferId(Long transferId);
    
    Transfers getTransfersByUserId(Long userId);
    
    List<Transfers> getTransfersByAccount(Long accountId);
    
    void updateTransfers(Transfers transfer, Long transferId);
    
   // Transfers createNewTransfer(Long transferId);

}
