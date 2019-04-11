package com.automation.utilities;

import java.util.HashMap;
import java.util.Map;

public class Credentials {

	public static void main(String[] args)
	{
		try{
			Map<String,String> registered = User.getUser("ssdf");
			System.out.println("username is: "+registered.get("username")+" and password is: "
					+registered.get("password"));
			
		}
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		/*Map<String,String> cred = new HashMap<String,String>();
		cred.put("username", "abhilash");
		cred.put("password", "abhi123");
		System.out.println("username: "+cred.get("username")+ " and password is: "+cred.get("password"));
	*/
		
		
		
	}
	
}
