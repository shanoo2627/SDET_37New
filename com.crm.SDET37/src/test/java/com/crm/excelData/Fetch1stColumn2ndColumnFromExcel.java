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

public class Fetch1stColumn2ndColumnFromExcel {
public static void main(String[] args) throws EncryptedDocumentException, FileNotFoundException, IOException {
	
	Workbook workbook=WorkbookFactory.create(new FileInputStream("./src/test/resources/1stcol2ndcol.xlsx")); 
	Sheet sheet=workbook.getSheet("sheet1");
	//int count=sheet.getLastRowNum();
	for(int i=0;i<=4;i++) 
	{
		Row row=sheet.getRow(i);
		for(int j=0;j<2;j++)
		{
			Cell cell = row.getCell(j);
			DataFormatter format = new DataFormatter();
		    String data = format.formatCellValue(cell);
		    System.out.print(data+" ");
		}
		System.out.println();
		//String firstCellData=row.getCell(0).getStringCellValue();
	    //String secondCellData=row.getCell(1).getStringCellValue();
        //System.out.println(firstCellData+"\t"+secondCellData);
	
}
}
}

