package com.crm.salesorder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateSalesOrderWithOpportunitiesTest {
	public static void main(String[] args) throws IOException {

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
		String value=workbook.getSheet("salesorder").getRow(1).getCell(2).toString();
		String subject=value+randomnum;


		//maximize 
		driver.manage().window().maximize();
		//enter rmgyantra url
		driver.get(URL);

		//	
		//implicitlywait for 10 seconds//

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		//login to vtiger
		driver.findElement(By.name("user_name")).sendKeys( USERNAME);
		driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
		driver.findElement(By.id("submitButton")).click();

		//mousehover to more
		WebElement element =driver.findElement(By.xpath("//img[@src='themes/softed/images/menuDnArrow.gif']"));
		Actions action=new Actions(driver);
		action.moveToElement(element).perform();
		//click on sales order
		driver.findElement(By.xpath("//a[text()='Sales Order']")).click();
		driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
		//subject name
		driver.findElement(By.xpath("(//input[@class='detailedViewTextBox'])[1]")).sendKeys(subject);
		//contactname
		driver.findElement(By.xpath("(//img[@src='themes/softed/images/select.gif'])[3]")).click();


		String parentId = driver.getWindowHandle();
		Set<String> allwh=driver.getWindowHandles();

		for(String check:allwh)
		{
			driver.switchTo().window(check);
			String title=driver.getTitle();
			if(title.contains("Contacts&action"))
			{
				break;
			}
		}

		//driver.findElement(By.xpath(""))

		driver.findElement(By.xpath("//a[text()='Kristal-87453076 Day']")).click();
		driver.switchTo().alert().accept();
		driver.switchTo().window(parentId);
		//select status to approved
		WebElement wb=driver.findElement(By.xpath("(//select[@class='small'])[3]"));
		Select s=new Select(wb);
		s.selectByIndex(2);
		//Calender Date select 15
		driver.findElement(By.xpath("//img[@id='jscal_trigger_duedate']")).click();
		driver.findElement(By.xpath("//td[text()='15']")).click();
		//click on lookup icon of opportunity
		driver.findElement(By.xpath("(//img[@src='themes/softed/images/select.gif'])[1]")).click();
		Set<String> allwh1=driver.getWindowHandles();
		for(String check1:allwh1)
		{
			driver.switchTo().window(check1);
			String title=driver.getTitle();
			if(title.contains("Potentials&action"))
			{
				break;
			}
		}

		driver.findElement(By.xpath("(//a[text()='opportunity1'])[1]")).click();
		driver.switchTo().window(parentId);
		//click on organization lookup icon
		driver.findElement(By.xpath("(//img[@src='themes/softed/images/select.gif'])[4]")).click();



		Set<String> allwh2=driver.getWindowHandles();
		for(String check2:allwh2)
		{
			driver.switchTo().window(check2);
			String title=driver.getTitle();
			if(title.contains("Accounts&action"))
			{
				break;
			}
		}

		driver.findElement(By.xpath("//a[text()='Test']")).click();
		driver.switchTo().alert().accept();

		driver.switchTo().window(parentId);
		//billing address
		driver.findElement(By.xpath("(//textarea[@class='detailedViewTextBox'])[1]")).sendKeys("btm layout banglore");
		//driver.switchTo().alert().accept();

		//shiping address
		driver.findElement(By.xpath("//textarea[@name='ship_street']")).sendKeys("whitefield banglore no902");
		/////add item or product
		driver.findElement(By.id("searchIcon1")).click();

		Set<String> allwh3=driver.getWindowHandles();
		for(String check3:allwh3)
		{
			driver.switchTo().window(check3);
			String title=driver.getTitle();
			if(title.contains("Products&action"))
			{
				break;
			}
		}
		//add product from lookup icon
		driver.findElement(By.id("popup_product_45")).click();
		driver.switchTo().window(parentId);
		//product quantity
		driver.findElement(By.id("qty1")).sendKeys("5");
		//click on save button
		driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])[2]")).click();
		//Verify sales order created or not
		String	Salesorder=driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if(Salesorder.contains(subject)) {
			System.out.println("sales order is created");
		}
		else {
			System.out.println("sales order is not created");
		}



	}
}
