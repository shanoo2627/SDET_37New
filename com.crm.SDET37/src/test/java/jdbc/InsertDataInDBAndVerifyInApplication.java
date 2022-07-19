package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.mysql.cj.jdbc.Driver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class InsertDataInDBAndVerifyInApplication {
	public static void main(String[] args) throws SQLException, InterruptedException {
		String projectname="BlueWhell";
		Connection connection=null;
		
		
			try {
				//get registerfor mysql database//
				Driver driverRef = new Driver();
				//step2:connect to mysql//
				DriverManager.registerDriver(driverRef);
				 connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/projects","root","root");
				//create a statement
				Statement statement= connection.createStatement();
				int result=statement.executeUpdate("insert into project values('ID001','amazon','08/12/2022','BlueWhell','Created','10')");
						//verification
						if(result==1)
						{
							System.out.println("data is createsd,pass");
						}
			}
						catch(SQLException e) {
							e.printStackTrace();
						
							System.out.println("data is not created,false");
						}finally {
							connection.close();
			
						System.out.println("databse is closed");
						}
	
		
				
						
				//checking in RmgYantra
	//launching chrome browser
						WebDriverManager.chromedriver().setup();
						WebDriver driver=new ChromeDriver();
						//maximize 
						driver.manage().window().maximize();
				//enter rmgyantra url
						driver.get("http://localhost:8084/");
		
//						
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
		driver.findElement(By.xpath("//a[text()='Projects']")).click();	
		//click on  create project//
		//driver.findElement(By.xpath("//span[text()='Create Project']")).click();
		//project name//
		List<WebElement> allelement=driver.findElements(By.xpath("//td[2]"));////have to take xpath for all rpojects name
	//List<WebElement> allelement=driver.findElements(By.xpath("//table[@class='table table-striped table-hover']"));
		boolean temp=false;
		for(WebElement a:allelement) {
			if(a.getText().equals(projectname)) {
				temp=true;
				System.out.println(a.getText());
			}
			
	}
		if(temp==true) {
			System.out.println("project is created");
		}
		else {
			System.out.println("project is not created");
		}
		Thread.sleep(3000);
driver.quit();
						
}
}
	
