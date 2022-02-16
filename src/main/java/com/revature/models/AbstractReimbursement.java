package com.revature.models;

import java.util.Objects;

/**
 * This AbstractReimbursement class defines a minimum functionality for
 * interacting with reimbursements in the ERS application.
 *
 * All reimbursements in this application must at least have:
 * <ul>
 *     <li>ID</li>
 *     <li>Status</li>
 *     <li>Author</li>
 *     <li>Resolver</li>
 *     <li>Amount</li>
 * </ul>
 *
 * Additional fields can be added to the concrete {@link com.revature.models.Reimbursement} class.
 *
 * @author Center of Excellence
 */
public class AbstractReimbursement {

    private int id;
    private Status status;
    private Integer authorId;
    private Integer resolverId;
    private double amount;

    public AbstractReimbursement() {
        super();
    }

    public AbstractReimbursement(int id, Status status, Integer authorId, Integer resolverId, double amount) {
        super();
        this.id = id;
        this.status = status;
        this.authorId = authorId;
        this.resolverId = resolverId;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getAuthor() {
        return authorId;
    }

    public void setAuthor(Integer author) {
        this.authorId = author;
    }

    public Integer getResolver() {
        return resolverId;
    }

    public void setResolver(Integer resolver) {
        this.resolverId = resolver;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractReimbursement that = (AbstractReimbursement) o;
        return id == that.id && Double.compare(that.amount, amount) == 0 && status == that.status && Objects.equals(authorId, that.authorId) && Objects.equals(resolverId, that.resolverId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, authorId, resolverId, amount);
    }

	@Override
	public String toString() {
		return "AbstractReimbursement [id=" + id + ", status=" + status + ", author=" + authorId + ", resolver="
				+ resolverId + ", amount=" + amount + "]";
	}

    
}

