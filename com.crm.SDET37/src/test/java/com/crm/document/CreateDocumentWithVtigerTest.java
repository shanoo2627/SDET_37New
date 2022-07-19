package com.crm.document;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateDocumentWithVtigerTest {
public static void main(String[] args) throws EncryptedDocumentException, IOException {
	
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
	String value=workbook.getSheet("document").getRow(1).getCell(2).toString();
	String documentname=value+randomnum;
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
//click on doceuments
driver.findElement(By.xpath("//a[text()='Documents']")).click();

//click on create document lookup
driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
//write title
driver.findElement(By.name("notes_title")).sendKeys(documentname);


////////////how to write in description box
driver.findElement(By.xpath("//iframe[@title='Rich text editor, notecontent, press ALT 0 for help.']")).sendKeys("created");
driver.findElement(By.id("filename_I__")).sendKeys("C:\\Users\\Admin\\Downloads\\SDresume.pdf");
driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])[2]")).click();
//verify whether documnet created or not
String document=driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
if(document.contains(documentname)) {
	System.out.println("Resume is created");
}
else {
	System.out.println("Resume is not created");
}


}
}
