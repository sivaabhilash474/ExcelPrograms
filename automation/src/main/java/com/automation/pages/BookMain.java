package com.automation.pages;

import java.util.ArrayList;
import java.util.List;

public class BookMain {
	//Book b = new Book();
	
	public static void main(String[] args)
	{
		List<Book> list = new ArrayList<Book>();
		Book book = new Book();
		book.setBookName("Social Studies");
		book.setISBN(123456789);
		Book book1 = new Book();
		book1.setBookName("Science");
		book1.setISBN(1233344455);
		
		list.add(book);
		list.add(book1);
		System.out.println(book.getBookName());
		System.out.println(book.getISBN());
		
		System.out.println(list);
		
	}

}
