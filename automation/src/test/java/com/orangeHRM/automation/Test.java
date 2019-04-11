package com.orangeHRM.automation;

public class Test extends Thread{
	
	public void run()
	{
		Thread t = Thread.currentThread();
		System.out.println(" thread: "+t);
	}
	

public static class MyThread {
	
	public static void main(String[] args)
	{
		Test t1 = new Test();
		t1.run();
		System.out.println("hello world!!");
		Thread t = Thread.currentThread();
		System.out.println("current thread information is: "+t);
		System.out.println("current thread priority is: "+t.getPriority());
		System.out.println("current thread name is: "+t.getName());
		System.out.println("hi siva");
		System.out.println("hello");
	}

}
	
}


