package com.crm.createcontact;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateContactTest {
	public static void main(String[] args) throws InterruptedException, IOException {
		
		WebDriver driver=null;



		//Fetch the common data from property file	
		FileInputStream fileInputStream=new FileInputStream("./src/test/resources/commondata.properties.txt");
		Properties properties=new Properties();
		properties.load(fileInputStream);
		//get the property from the property file by using getProperty
		String URL=properties.getProperty("url");
		String USERNAME=properties.getProperty("un");
		String PASSWORD=properties.getProperty("pass");
		String BROWSER=properties.getProperty("browser");

		//this is an example for polymorphism
		if(BROWSER.equalsIgnoreCase("chrome"))
		{
			driver=WebDriverManager.chromedriver().create();
			
			
		}else if(BROWSER.equalsIgnoreCase("firefox"))
			{
				driver=WebDriverManager.firefoxdriver().create();
					
			}else if(BROWSER.equalsIgnoreCase("microsoftEdge"))
			{
				driver =WebDriverManager.edgedriver().create();
				}else {
					driver=WebDriverManager.firefoxdriver().create();
				}


		///////for genertaing random number
		Random random=new Random();
		int randomnum=random.nextInt(100);

		//fetch the data from Excel sheet
		Workbook workbook=WorkbookFactory.create(new FileInputStream("./src/test/resources/Data.xlsx"));
		String value=workbook.getSheet("contact").getRow(2).getCell(2).toString();
		String FirstName=value+randomnum;
		//fetch the last name
		String value1=workbook.getSheet("contact").getRow(2).getCell(3).toString();
		String LastName=value1+randomnum;
		//fetch organization name
		String value2=workbook.getSheet("sheet1").getRow(1).getCell(2).toString();
		String orgname=value2+randomnum;

		//maximize 
		driver.manage().window().maximize();
		//enter rmgyantra url
		driver.get(URL);


		//implicitlywait for 10 seconds//

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.name("user_name")).sendKeys(USERNAME);
		driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
		driver.findElement(By.id("submitButton")).click();
		driver.findElement(By.xpath("//a[text()='Contacts']")).click();
		//click on contacts module
		driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();
		//type first name in textfield

		driver.findElement(By.name("firstname")).sendKeys(FirstName);
		//lastname
		driver.findElement(By.name("lastname")).sendKeys(LastName);

		//click on create contact button
		driver.findElement(By.xpath("//tbody/tr[5]/td[2]/img[1]")).click();
		//select the organisation name
		String parentId = driver.getWindowHandle();
		Set<String> allwh=driver.getWindowHandles();

		for(String check:allwh)
		{
			driver.switchTo().window(check);
			String title=driver.getTitle();
			if(title.contains("Accounts&action"))
			{
				break;
			}
		}

		Thread.sleep(3000);
		//enter the organisation name
		driver.findElement(By.id("search_txt")).sendKeys(orgname);
		//click on search button
		driver.findElement(By.name("search")).click();
		//select organisation name
		//driver.findElement(By.id("1")).click();
		driver.findElement(By.xpath("//a[text()='TestYantra1']")).click();

		//switch to main window
		driver.switchTo().window(parentId);

		//click on save button
		driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])[1]")).click();
		
		//verify contact is created or not
		String contact=driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if(contact.contains(LastName))
		{
			System.out.println("contact is created");
		}
		else {
			System.out.println("contact is not created");
		}
		//mouseover on administrator link
		WebElement element =driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		Actions action=new Actions(driver);
		action.moveToElement(element).perform();
		//click on signout link
		driver.findElement(By.xpath("//a[.='Sign Out']")).click();

	}






}

