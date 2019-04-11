package com.automation.implementations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.automation.customclasses.Headers;
import com.automation.excel.utilities.ExcelFormat;

public class ExcelHeaderImplementation {
	//Reference : https://www.callicoder.com/java-write-excel-file-apache-poi/
	
	public static void main(String[] args){
		String userDirectory = System.getProperty("user.dir"); 
		String fileDirectory = System.getProperty("user.dir")+
				"\\resources\\excelFiles\\"+"fileName"+".xlsx";
		System.out.println("User Directory: "+ fileDirectory);
		
		
	}
	
	public void writeToExcel(String fileName, List<Headers> headers,
			String sheetName) throws IOException{
		String fileDirectory = System.getProperty("user.dir")+
				"\\resources\\excelFiles\\"+fileName+".xlsx";
		
		File file = new File(fileDirectory);
		//Create a workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		 /* CreationHelper helps us create instances of various things like DataFormat, 
        Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
		CreationHelper createHelper = workbook.getCreationHelper();
		
		//Create a sheet
		XSSFSheet sheet = workbook.createSheet(sheetName);
		
		//Create a font for styling header cells
		XSSFFont headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short)14);
		headerFont.setColor(IndexedColors.RED.getIndex());
		
		//Create a cellStyle with the font
		XSSFCellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		
		//Create a Row
		Row headerRow = sheet.createRow(0);
		
		//Create cells
		for(int i=0; i < headers.size(); i++){
			Cell cell = headerRow.createCell(i);
			String value = headers.get(i).toString();
			cell.setCellValue(value);
			cell.setCellStyle(headerCellStyle);
			
		}
		
		
		// Create Cell Style for formatting Date
        XSSFCellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		
     // Resize all columns to fit the content size
        for(int i = 0; i < headers.size(); i++) {
            sheet.autoSizeColumn(i);
        }
		
		
		
		
		
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
		
			fos.close();
		
		
	}

}
