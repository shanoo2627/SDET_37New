package org.com.project;

import org.testng.annotations.Test;

public class PracticeTest {
 @Test
	 public void demo()
	 {
	 String url=System.getProperty("URL");
	 String browser=System.getProperty("BROWSER");
	 String username=System.getProperty("USERNAME");
	 String password=System.getProperty("PASSWORD");
		System.out.println("Test1-->Test3"); 
		System.out.println("url====>"+url);
		System.out.println("browser====>"+browser);
		System.out.println("username====>"+username);
		System.out.println("password====>"+password);
		
	 }

	@Test		public void demo1() {
			System.out.println("Test2-->Test3");
			System.out.println("hi all");
		}
}

