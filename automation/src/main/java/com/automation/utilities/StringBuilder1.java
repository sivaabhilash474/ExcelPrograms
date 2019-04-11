package com.automation.utilities;

public class StringBuilder1 {

	public static void main(String[] args){
		
		
		StringBuilder sb1 = new StringBuilder();
		
		String address = "1234 W Brook Street, ashford road, ST4 2EX";
		
		for(int i = 0; i< address.length() -1 ; i++){
			
			if(!Character.isDigit(address.charAt(i))){
				sb1.append(address.charAt(i));
			}
			
		}
		
		System.out.println("String builder: "+sb1);
		
		
	}
	
}
