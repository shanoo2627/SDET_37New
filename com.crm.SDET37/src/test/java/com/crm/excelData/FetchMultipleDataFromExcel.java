package com.crm.excelData;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class FetchMultipleDataFromExcel {
	public static void main(String[] args) throws EncryptedDocumentException, FileNotFoundException, IOException {
		Workbook workbook=WorkbookFactory.create(new FileInputStream("./src/test/resources/MyExcel.xlsx")); 
		Sheet sheet=workbook.getSheet("sheet1");
		for(int i=0;i<=sheet.getLastRowNum();i++) {
			Row row=sheet.getRow(i);
		
		for(int j=0;j<=row.getLastCellNum();j++) {
			Cell cell=row.getCell(j);
			DataFormatter dataFormatter=new DataFormatter();
			String data=dataFormatter.formatCellValue(cell);
			System.out.println(data);
		}
		
		
}
}
}