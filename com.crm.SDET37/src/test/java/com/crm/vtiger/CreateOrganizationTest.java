package com.crm.vtiger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;
/**
 * 
 * @author Admin
 *
 */
public class CreateOrganizationTest {
public static void main(String[] args) throws SQLException, IOException {
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

//get the random number
Random random=new Random();
int randomNum=random.nextInt();


//Fetch the data from Excel
Workbook workbook=WorkbookFactory.create(new FileInputStream("./src/test/resources/Data.xlsx"));
String value1=workbook.getSheet("sheet1").getRow(1).getCell(1).toString();
String orgname=value1+randomNum;

//	WebDriverManager.chromedriver().setup();
	//WebDriver driver=new ChromeDriver();
	//maximize 
	driver.manage().window().maximize();
//enter rmgyantra url
	driver.get(URL);

	
//implicitlywait for 10 seconds//

driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//Login for vtiger i.e username and password

driver.findElement(By.name("user_name")).sendKeys(USERNAME);
driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
driver.findElement(By.id("submitButton")).click();

//create organization
driver.findElement(By.xpath("//td[6]/a[text()='Organizations']")).click();
driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
driver.findElement(By.name("accountname")).sendKeys(orgname);
//indusrty
WebElement drop=driver.findElement(By.name("industry"));
 //java.util.List<WebElement> drop=driver.findElements(By.name("industry"));
Select s= new Select(drop);
s.selectByIndex(2);

//click on save button
driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])[1]")).click();
///Verify whether it is created or not
String orgname1=driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
if(orgname1.contains(orgname)) {
	System.out.println("organisation is created");
}
else {
	System.out.println("organisation is not created");
}
//mousehover on administaration
WebElement wb=driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
Actions actions=new Actions(driver);
actions.moveToElement(wb).perform();
driver.findElement(By.xpath("//a[.='Sign Out']")).click();

}
}
