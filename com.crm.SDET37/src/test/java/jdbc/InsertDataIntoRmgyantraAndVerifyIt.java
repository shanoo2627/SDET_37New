package jdbc;
//Scenario1:inserting data in rmgyantra and verifying in mysql
//assignment scenarion :inserting data in my sql andverify in rmgyantra
//assignment2: verification of projectname
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import com.mysql.jdbc.Driver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class InsertDataIntoRmgyantraAndVerifyIt {
	


public static void main(String[] args) throws SQLException {
	String projectname="BlueWhell";
	Connection connection=null;
	
	WebDriverManager.chromedriver().setup();
	WebDriver driver=new ChromeDriver();
	//maximize the browser/
	driver.manage().window().maximize();
	//implicitlywait for 10 seconds//
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	//enter the url of testyantra//
	driver.get("http://localhost:8084/");
	// enter the username in textfield//
	driver.findElement(By.id("usernmae")).sendKeys("rmgyantra");
	//enter the password in testfield//
	driver.findElement(By.id("inputPassword")).sendKeys("rmgy@9999");
	//click on sign in button//
	driver.findElement(By.xpath("//button[text()='Sign in']")).click();
	//click on  project//
	driver.findElement(By.xpath("//a[text()='Projects']")).click();	
	//click on  create project//
	driver.findElement(By.xpath("//span[text()='Create Project']")).click();
	//project name//
	driver.findElement(By.name("projectName")).sendKeys("project_name");
	//
	driver.findElement(By.name("createdBy")).sendKeys("prakash");
	WebElement statusDropdown=driver.findElement(By.xpath("//label[.='Project Status ']/following-sibling::select"));
	//WebElement statusDropdown=driver.findElement(By.xpath("//label[.='Project Status ']"));
	Select s=new Select(statusDropdown);
	s.selectByValue("Created");
	//click on add project button
	driver.findElement(By.xpath("//input[@class='btn btn-success']")).click();
	try {
		//get registerfor mysql database//
		Driver driverRef = new Driver();
		//step2:connect to mysql//
		DriverManager.registerDriver(driverRef);
		Connection connection1=DriverManager.getConnection("jdbc:mysql://localhost:3306/projects","root","root");
		//create a statement
		Statement statement= connection1.createStatement();
		//write a query
		String query="select * from amazon";
		//fetch all the column data
		ResultSet result=statement.executeQuery(query);
		//verify the data in database
		boolean flag = false;
		while(result.next()) {
			String allprojects=result.getString(2);
			if(allprojects.contains( projectname)) {
				flag=true;
				break;
			}
		}
		
		if(flag) {
			System.out.println("project is created in database");
		}
		else {
			System.out.println("projet is not created");
		}
		
		}catch(Exception e) {
			e.printStackTrace();
			
		}finally {
			//close database connection
			connection.close();
		
		}
	//close the browser
	driver.quit();
	
}
}
