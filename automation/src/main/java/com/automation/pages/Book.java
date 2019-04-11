package com.automation.pages;

public class Book {
	private String bookName;
	private long ISBN;

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public long getISBN() {
		return ISBN;
	}

	public void setISBN(long iSBN) {
		ISBN = iSBN;
	}

	@Override
	public String toString() {
		return "[bookName=" + bookName + ", ISBN=" + ISBN + "]";
	}

}
