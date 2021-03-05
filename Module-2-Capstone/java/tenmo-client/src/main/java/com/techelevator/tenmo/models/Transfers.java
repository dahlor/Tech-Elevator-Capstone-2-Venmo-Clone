package com.techelevator.tenmo.models;

public class Transfers {
	
	private Long transferId;
	private Long transferTypeId;
	private Long transferStatus;
	private Long accountFrom;
	private Long accountTo;
	private double amount;
	
	public Transfers() {
	}
	
	public Transfers(Long transferId, Long transferTypeId, 
					 Long transferStatus, Long accountFrom, 
					 Long accountTo, double amount) {
		
		this.transferId = transferId;
		this.transferTypeId = transferTypeId;
		this.transferStatus = transferStatus;
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.amount = amount;
	}

	public Long getTransferId() {
		return transferId;
	}

	public void setTransferId(Long transferId) {
		this.transferId = transferId;
	}

	public Long getTransferTypeId() {
		return transferTypeId;
	}

	public void setTransferTypeId(Long transferTypeId) {
		this.transferTypeId = transferTypeId;
	}

	public Long getTransferStatus() {
		return transferStatus;
	}

	public void setTransferStatus(Long transferStatus) {
		this.transferStatus = transferStatus;
	}

	public Long getAccountFrom() {
		return accountFrom;
	}

	public void setAccountFrom(Long accountFrom) {
		this.accountFrom = accountFrom;
	}

	public Long getAccountTo() {
		return accountTo;
	}

	public void setAccountTo(Long accountTo) {
		this.accountTo = accountTo;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
