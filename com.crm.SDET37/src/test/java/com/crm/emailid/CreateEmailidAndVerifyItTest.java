package com.crm.emailid;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateEmailidAndVerifyItTest {
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


	///////for genertaing random number
	Random random=new Random();
	int randomnum=random.nextInt(100);

	//fetch the data from Excel sheet
	Workbook workbook=WorkbookFactory.create(new FileInputStream("./src/test/resources/Data.xlsx"));
	String value=workbook.getSheet("opportunity").getRow(1).getCell(2).toString();
	String opportunityname=value+randomnum;
//maximize 
driver.manage().window().maximize();
//enter rmgyantra url
driver.get(URL);

//
//implicitlywait for 10 seconds//

driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
driver.findElement(By.name("user_name")).sendKeys(USERNAME);
driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
driver.findElement(By.id("submitButton")).click();
driver.findElement(By.xpath("//a[text()='Email']")).click();
driver.findElement(By.xpath("//a[text()='Compose']")).click();
//Switching control to windows
String parentId = driver.getWindowHandle();
Set<String> allwh=driver.getWindowHandles();

for(String check:allwh)
{
	driver.switchTo().window(check);
	String title=driver.getTitle();
	if(title.contains("Emails&action"))
	
	{
		break;
	}
	
}
driver.findElement(By.xpath("//img[@src='themes/softed/images/select.gif']")).click();

Set<String> allwh1=driver.getWindowHandles();
for(String check1:allwh1)
{
	driver.switchTo().window(check1);
	String title=driver.getTitle();
	if(title.contains("Contacts&action"))
	{
		break;
	}
	
}


driver.findElement(By.id("search_txt")).sendKeys("demo@gmail.com");

///////////mailid is not taking

//driver.findElement(By.xpath("//a[text()='Pavitra Joshi']")).click();

Set<String> allwh2=driver.getWindowHandles();

for(String check3:allwh2)
{
	driver.switchTo().window(check3);
	String title=driver.getTitle();
	if(title.contains("Emails&action"))
	
	{
		break;
	}
	
}



//driver.findElement(By.id("parent_name")).sendKeys("")
driver.findElement(By.id("cc_name")).sendKeys("admin@gmail.com");
driver.findElement(By.id("bcc_name")).sendKeys("testlead@gmail.com");
driver.findElement(By.id("subject")).sendKeys("sending testreport");
driver.findElement(By.id("subject")).sendKeys("okay");
//send the mail
driver.findElement(By.xpath("(//input[@class='crmbutton small save'])[2]")).click();

}
}
