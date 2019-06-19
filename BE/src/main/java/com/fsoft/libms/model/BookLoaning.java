package com.fsoft.libms.model;
import java.io.Serializable;
public interface  BookLoaning  extends Serializable {

	public Book getBook();
	public int getNumberOfUser();
	
}
