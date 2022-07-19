package com.crm.excelData;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class InsertDataIntoSingleExcelData {
public static void main(String[] args) throws EncryptedDocumentException, FileNotFoundException, IOException {
	Workbook workbook=WorkbookFactory.create(new FileInputStream("./src/test/resources/ExcelSingledata.xlsx")); 
	Sheet sheet=workbook.getSheet("sheet1");
	Row row=sheet.getRow(0);
	Cell cell=row.createCell(1);
	cell.setCellValue("Hi");
	
	FileOutputStream fileOutputStream= new FileOutputStream("./src/test/resources/ExcelSingledata.xlsx");
	workbook.write(fileOutputStream);
}
}
