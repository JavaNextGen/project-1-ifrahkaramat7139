package com.revature.models;

import java.util.Date;

/**
 * This concrete Reimbursement class can include additional fields that can be used for
 * extended functionality of the ERS application.
 *
 * Example fields:
 * <ul>
 *     <li>Description</li>
 *     <li>Creation Date</li>
 *     <li>Resolution Date</li>
 *     <li>Receipt Image</li>
 * </ul>
 *
 */
public class Reimbursement extends AbstractReimbursement {

	private Date submitDate;
	private Date resolvedDate;
	private byte[] receipt;
	private String description;
	private Type type;
	
    public Reimbursement() {
        super();
    }

    /**
     * This includes the minimum parameters needed for the {@link com.revature.models.AbstractReimbursement} class.
     * If other fields are needed, please create additional constructors.
     */
    public Reimbursement(int id, Status status, Integer author, Integer resolver, double amount) {
        super(id, status, author, resolver, amount);
    }
    
    

	public Reimbursement(int id, Status status, Integer author, Integer resolver, double amount, Date submitDate, Date resolvedDate, byte[] receipt, String description, Type type) {
		super(id, status, author, resolver, amount);
		this.submitDate = submitDate;
		this.resolvedDate = resolvedDate;
		this.receipt = receipt;
		this.description = description;
		this.type = type;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public Date getResolvedDate() {
		return resolvedDate;
	}

	public void setResolvedDate(Date resolvedDate) {
		this.resolvedDate = resolvedDate;
	}

	public byte[] getReceipt() {
		return receipt;
	}

	public void setReceipt(byte[] receipt) {
		this.receipt = receipt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
    
    
}
