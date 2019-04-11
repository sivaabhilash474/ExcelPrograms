package com.automation.excel.utilities;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.automation.implementations.ExcelHeaderImplementation;

public class ExcelFormat {
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;
	public ExcelFormat(XSSFWorkbook workbook, XSSFSheet sheet){
		ExcelFormat.workbook = workbook;
		ExcelFormat.sheet = sheet;
	}
	

	
	
	public static XSSFCellStyle setThickBorder(XSSFWorkbook workbook){
		XSSFCellStyle style = workbook.createCellStyle();
		style.setBorderTop(BorderStyle.DOUBLE);
		style.setBorderBottom(BorderStyle.DOUBLE);
		style.setBorderLeft(BorderStyle.DOUBLE);
		style.setBorderRight(BorderStyle.DOUBLE);
		return style;
		
	}
	
	
	public XSSFFont setBoldFont(XSSFWorkbook workbook){
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		return font;
		
	}

}
