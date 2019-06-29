package com.automation.excel.utilities;

import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	
	public static void main(String[] args) throws IOException{
		
		XSSFWorkbook workbook = new XSSFWorkbook("G:\\credentilas.xlsx");
		XSSFSheet sheet = workbook.getSheetAt(0);

		Iterator<Row> rowIterator = sheet.iterator();
		while(rowIterator.hasNext()){
			Row row = rowIterator.next();
			
			Iterator<Cell> cellIterator = row.iterator();
			while(cellIterator.hasNext()){
				Cell cell = cellIterator.next();
				
				
			}
		}
		
	}

}
