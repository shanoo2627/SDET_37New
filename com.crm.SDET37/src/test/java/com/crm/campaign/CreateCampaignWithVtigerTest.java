package com.crm.campaign;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateCampaignWithVtigerTest {
public static void main(String[] args) throws EncryptedDocumentException, FileNotFoundException, IOException {
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


	///////for generating random number
	Random random=new Random();
	int randomnum=random.nextInt(100);

	//fetch the data from Excel sheet campaign
	Workbook workbook=WorkbookFactory.create(new FileInputStream("./src/test/resources/Data.xlsx"));
	String value=workbook.getSheet("campaign").getRow(1).getCell(2).toString();
	String campaignname=value+randomnum;
	
	//maximize 
	driver.manage().window().maximize();
//enter rmgyantra url
	driver.get(URL);

//	
//implicitlywait for 10 seconds//

driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//login to vtiger
driver.findElement(By.name("user_name")).sendKeys(USERNAME);
driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
driver.findElement(By.id("submitButton")).click();
//mousehover to more
WebElement element =driver.findElement(By.xpath("//img[@src='themes/softed/images/menuDnArrow.gif']"));
Actions action=new Actions(driver);
action.moveToElement(element).perform();

//click on campaign

driver.findElement(By.xpath("//a[text()='Campaigns']")).click();
//click on create campaign lookup
driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
//campagin name write
driver.findElement(By.name("campaignname")).sendKeys(campaignname);
//click on save
driver.findElement(By.name("button")).click();


////////////verify whether campaign is created or not
String campaign=driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
if(campaign.contains(campaignname))
{
	System.out.println("campaign  is created");
}
else {
	System.out.println("campaign is not created");
}





}
}
