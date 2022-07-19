package com.crm.invoice;

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

public class CreateInvoiceWithSalesOrderAndOrgName {
public static void main(String[] args) throws IOException {
	
	WebDriver driver=null;
	FileInputStream fileInputStream=new FileInputStream("./src/test/resources/commondata.properties.txt");
	Properties properties=new Properties();
	properties.load(fileInputStream);
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
				
				//get the random number
				Random random=new Random();
				int randomNum=random.nextInt();
			//fetch the data from excel
				Workbook workbook=WorkbookFactory.create(new FileInputStream("./src/test/resources/Data.xlsx"));
				String value=workbook.getSheet("Invoice").getRow(1).getCell(2).toString();
				String invoicesub=value+randomNum;
				
				//fetch sales order data from excel
				String value1=workbook.getSheet("salesorder").getRow(1).getCell(2).toString();
				String subject=value1+randomNum;


	//maximize 
	driver.manage().window().maximize();
//enter rmgyantra url
	driver.get(URL);

//implicitlywait for 10 seconds//

driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
///login to vtiger
//enter the username
driver.findElement(By.name("user_name")).sendKeys(USERNAME);
//enter the password
driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
//click on login
driver.findElement(By.id("submitButton")).click();
//mousehover to more
WebElement element =driver.findElement(By.xpath("//img[@src='themes/softed/images/menuDnArrow.gif']"));
Actions action=new Actions(driver);
action.moveToElement(element).perform();

//Click on invoice 
driver.findElement(By.xpath("//a[text()='Invoice']")).click();
// click on create inovice lookupicon
driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
//enter the invoice subject
driver.findElement(By.xpath("//input[@name='subject']")).sendKeys(invoicesub);
//click on sales order lookup icon
driver.findElement(By.xpath("(//img[@src='themes/softed/images/select.gif'])[1]")).click();
//go to sales order window

String parentw=driver.getWindowHandle();
Set<String> allwh=driver.getWindowHandles();
for(String check:allwh)
{
	driver.switchTo().window(check);
	String title=driver.getTitle();
	if(title.contains("SalesOrder&action"))
	{
		break;
	}
}

driver.switchTo().window(parentw);
driver.findElement(By.xpath("//input[@class='txtBox']")).sendKeys(subject);
/*
//List wb =driver.findElements(By.xpath("//select[@name='search_field']"));
Select select=new Select(wb);
select.


//click on lookup icon of organization name
driver.findElement(By.xpath("(//img[@src='themes/softed/images/select.gif'])[3]")).click();
Set<String> allwh1=driver.getWindowHandles();
for(String check1:allwh1)
{
	driver.switchTo().window(check1);
	String title=driver.getTitle();
	if(title.contains("Accounts&action"))
	{
		break;
	}
}
driver.findElement(By.xpath("//a[text()='TestYantra7889214564']")).click();
driver.switchTo().window(parentw);
driver.findElement(By.xpath("(//textarea[@class='detailedViewTextBox'])[1]")).sendKeys();

			}*/
}
}
}
