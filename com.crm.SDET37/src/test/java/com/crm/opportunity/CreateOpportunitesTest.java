package com.crm.opportunity;

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
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateOpportunitesTest {
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
		String value=workbook.getSheet("opportunity").getRow(1).getCell(2).toString();
		String opportunityname=value+randomnum;
		
		//fetch the last name
		String value2=workbook.getSheet("contact").getRow(2).getCell(3).toString();
		String LastName=value2+randomnum;
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
		//click on opportunities
		driver.findElement(By.xpath("(//a[text()='Opportunities'])[1]")).click();
		//click on lookup icon of create opportunities
		driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
		//enter opportunity in textfield
		driver.findElement(By.xpath("(//input[@class='detailedViewTextBox'])[1]")).sendKeys(opportunityname);
		//
		WebElement drop=driver.findElement(By.id("related_to_type"));
		Select s=new Select(drop);
		s.selectByIndex(1);
		driver.findElement(By.xpath("(//img[@src='themes/softed/images/select.gif'])[1]")).click();
		//switching control to window
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
		//serach
		driver.findElement(By.xpath("//input[@class='txtBox']")).sendKeys(LastName);
		//search now
		driver.findElement(By.xpath("//input[@class='crmbutton small create']")).click();
		driver.findElement(By.xpath("//a[text()='Pavitra Joshi']")).click();
		driver.switchTo().window(parentId);
		//lookupon campaign source
		driver.findElement(By.xpath("(//img[@src='themes/softed/images/select.gif'])[2]")).click();

		Set<String> allwh1=driver.getWindowHandles();

		for(String check1:allwh1)
		{
			driver.switchTo().window(check1);
			String title=driver.getTitle();
			if(title.contains("Campaigns&action"))

			{
				break;
			}

		}
		driver.findElement(By.xpath("(//a[text()='Hplaptop'])[1]")).click();
		driver.switchTo().window(parentId);
		//calender popup lookup
		driver.findElement(By.xpath("(//img[@src='themes/softed/images/btnL3Calendar.gif'])[2]")).click();
		/*WebElement today=driver.findElement(By.xpath(""));
	Select s1=new Select(today);
	s1.deselectByValue("15");*/
		driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])[2]")).click();
		//verify whether opportunities created or not 
		String oppo=driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if(oppo.contains(opportunityname)) {
			System.out.println("opportunity is created");
		}
		else
		{
			System.out.println("opportunity is not created");
		}


	}
}
