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

public class FetchSingleDataFromExcel {
public static void main(String[] args) throws EncryptedDocumentException, FileNotFoundException, IOException {
	
	Workbook workbook=WorkbookFactory.create(new FileInputStream("./src/test/resources/ExcelSingledata.xlsx")); 
Sheet sheet=workbook.getSheet("sheet1");
Row row=sheet.getRow(0);
Cell cell=row.getCell(0);

DataFormatter format=new DataFormatter();
String value=format.formatCellValue(cell);
System.out.println(value);

}

}

