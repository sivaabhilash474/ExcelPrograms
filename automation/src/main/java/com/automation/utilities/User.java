package com.automation.utilities;

import java.util.HashMap;
import java.util.Map;

public class User {
	
	static String user_name = "username";
	static String pass = "password";
	public static Map<String,String> getUser(String userType)
	{
		Map<String,String> cred = null;
		//should return user name and password
		if(userType.equals("Registered"))
		{
			 cred = new HashMap<String,String>();
			cred.put(user_name, "abhilash");
			cred.put(pass, "abhi");
		}
		
		else if(userType.equals("unRegistered"))
		{
			cred = new HashMap<String,String>();
			cred.put(user_name, "unregistered");
			cred.put(pass, "abhi");
		}
		
		else
		{
			System.out.println("user not found");
		}
		
		return cred;
	}

}
