package com.crm.excelData;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.github.bonigarcia.wdm.WebDriverManager;

public class InsertMultipleDataIntoExcel {
public static void main(String[] args) throws EncryptedDocumentException, FileNotFoundException, IOException {
	Workbook workbook=WorkbookFactory.create(new FileInputStream("./src/test/resources/MyExcel.xlsx")); 
	Sheet sheet=workbook.getSheet("sheet1");
	WebDriver driver=WebDriverManager.chromedriver().create();
	
	driver.get("http://localhost:8888/");

	
	//implicitlywait for 10 seconds//

	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	driver.findElement(By.name("user_name")).sendKeys("admin");
	driver.findElement(By.name("user_password")).sendKeys("admin");
	driver.findElement(By.id("submitButton")).click();
	List<WebElement>links=driver.findElements(By.xpath("//a"));
	int count=links.size();
	for(int i=0;i<count;i++) {
		Row row=sheet.createRow(i);
		Cell cell=row.createCell(0);
		//Cell cell=row.createCell(i); links will be printed in every cell
		cell.setCellValue(links.get(i).getAttribute("href"));
	}
	FileOutputStream fileOutputStream= new FileOutputStream("./src/test/resources/MyExcel.xlsx");
	workbook.write(fileOutputStream);
}
}
