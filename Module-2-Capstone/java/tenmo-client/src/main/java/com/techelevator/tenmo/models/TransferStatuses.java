package com.techelevator.tenmo.models;

public class TransferStatuses {
	
	private Long transferStatusId;
	private String transferStatusDescription;
	
	public TransferStatuses() {
	}
	
	public TransferStatuses(Long transferStatusId, String transferStatusDescription) {
		this.transferStatusId = transferStatusId;
		this.transferStatusDescription = transferStatusDescription;
	}

	public Long getTransferStatusId() {
		return transferStatusId;
	}

	public void setTransferStatusId(Long transferStatusId) {
		this.transferStatusId = transferStatusId;
	}

	public String getTransferStatusDescription() {
		return transferStatusDescription;
	}

	public void setTransferStatusDescription(String transferStatusDescription) {
		this.transferStatusDescription = transferStatusDescription;
	}

	@Override
	public String toString() {
		return "TransferStatuses [transferStatusId=" + transferStatusId + ", transferStatusDescription="
				+ transferStatusDescription + "]";
	}
}
