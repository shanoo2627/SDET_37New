package com.crm.vendorproduct;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateProductWithVendorsVtigerTest {
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
		String value=workbook.getSheet("vendor").getRow(1).getCell(2).toString();
		String vendorname=value+randomnum;
		//productname from excel
		String value1=workbook.getSheet("product").getRow(1).getCell(2).toString();
		String productname=value1+randomnum;


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
		//click on vendors
		driver.findElement(By.name("Vendors")).click();
		//driver.findElement(By.xpath("//body[@class='small']")).click();
		//click on create vendors lookup
		driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
		//enter vendors name
		driver.findElement(By.name("vendorname")).sendKeys(vendorname);
		//click on save button
		//driver.findElement(By.name("button")).click();
		driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])[2]")).click();
		Thread.sleep(5000);


		///////Verify whether vendor created or not
		String vendor= driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();

		if(vendor.contains(vendorname))
		{
			System.out.println("vendor is created");
		}
		else {
			System.out.println("vendor is not created");
		}
		///create the product
		driver.findElement(By.xpath("(//a[text()='Products'])[1]")).click();
		driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
		//add the product name
		driver.findElement(By.xpath("(//input[@class='detailedViewTextBox'])[1]")).sendKeys(productname);
		//save the created product
		driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])[1]")).click();

	}
}
