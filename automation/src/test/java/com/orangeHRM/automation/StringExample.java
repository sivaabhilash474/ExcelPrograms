package com.orangeHRM.automation;

public class StringExample {
	
	String s1 = "name";
	String name;
	String rollno;
	
	StringExample(String name, String rollno)
	{
		this.name=name;
		this.rollno=rollno;
	}
	public void m1()
	{
		String s2 = "name";
		System.out.println(s1);
	}
	
	public void m2()
	{
		String s3 = "name";
		System.out.println(s1);
	}
	
public static void main(String[] args)
{
	StringExample s = new StringExample("abhilash","123");
	System.out.println(s.s1);
	//System.out.println("student name is: "+s.name +" and his");
	
	
	
	}
	
}
