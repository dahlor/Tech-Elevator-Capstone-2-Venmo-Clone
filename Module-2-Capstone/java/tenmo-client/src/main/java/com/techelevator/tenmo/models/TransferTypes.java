package com.techelevator.tenmo.models;

public class TransferTypes {
	
	private Long transferTypeId;
	private String transferTypeDescription;
	
	public TransferTypes() {
	}

	public TransferTypes(Long transferTypeId, String transferTypeDescription) {
		this.transferTypeId = transferTypeId;
		this.transferTypeDescription = transferTypeDescription;
	}

	public Long getTransferTypeId() {
		return transferTypeId;
	}

	public void setTransferTypeId(Long transferTypeId) {
		this.transferTypeId = transferTypeId;
	}

	public String getTransferTypeDescription() {
		return transferTypeDescription;
	}

	public void setTransferTypeDescription(String transferTypeDescription) {
		this.transferTypeDescription = transferTypeDescription;
	}

	@Override
	public String toString() {
		return "TransferTypes [transferTypeId=" + transferTypeId + ", transferTypeDescription="
				+ transferTypeDescription + "]";
	}
}
