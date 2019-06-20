package com.fsoft.libms.model;


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity()
@Table(name = "loan_book")
public class LoanBook implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "loan_id")
	private long id;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true )
	@JoinColumn(name = "code_id")
	private CodeId code;
	@OneToOne()
	@JoinColumn(name = "book_id")
	private Book book;
	@OneToOne()
	@JoinColumn(name = "user_id")
	private User user;
	@Column(name = "reserve_date")
	private long reserveDate;
	@Column(name = "request_date")
	private long requestDate;
	@Column(name = "loan_date")
	private long loanDate;
	@Column(name = "return_date")
	private long returnDate;
	@Column(name = "reason_reject")
	private String reason;
	@Column(name = "disable_")
	private boolean disable;
	
	
	public LoanBook() {
	}

	public boolean isDisable() {
		return disable;
	}



	public void setDisable(boolean disable) {
		this.disable = disable;
	}



	public CodeId getCode() {
		return code;
	}



	public void setCode(CodeId code) {
		this.code = code;
	}



	public String getReason() {
		return reason;
	}



	public void setReason(String reason) {
		this.reason = reason;
	}



	



	







	@Enumerated(EnumType.STRING)
	private LoanStatus status;

	

	

	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public void setId(int id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getReserveDate() {
		return reserveDate;
	}

	public void setReserveDate(long reserveDate) {
		this.reserveDate = reserveDate;
	}

	public long getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(long requestDate) {
		this.requestDate = requestDate;
	}

	public long getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(long loanDate) {
		this.loanDate = loanDate;
	}

	public long getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(long returnDate) {
		this.returnDate = returnDate;
	}

	public LoanStatus getStatus() {
		return status;
	}

	public void setStatus(LoanStatus status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (loanDate ^ (loanDate >>> 32));
		result = prime * result + (int) (requestDate ^ (requestDate >>> 32));
		result = prime * result + (int) (reserveDate ^ (reserveDate >>> 32));
		result = prime * result + (int) (returnDate ^ (returnDate >>> 32));
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoanBook other = (LoanBook) obj;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		if (id != other.id)
			return false;
		if (loanDate != other.loanDate)
			return false;
		if (requestDate != other.requestDate)
			return false;
		if (reserveDate != other.reserveDate)
			return false;
		if (returnDate != other.returnDate)
			return false;
		if (status != other.status)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	
	



}
