package com.automation.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CommonUtilities {
	
	
	public static void compareExcelFiles(String sourceFile, String targetFile) throws IOException
	{
		FileInputStream excelFile1 = new FileInputStream(new File(sourceFile));
		FileInputStream excelFile2 = new FileInputStream(new File(targetFile));
		
		XSSFWorkbook workbook1 = new XSSFWorkbook(excelFile1);
		XSSFWorkbook workbook2 = new XSSFWorkbook(excelFile2);		
		
		XSSFSheet sheet1 = workbook1.getSheetAt(0);
		XSSFSheet sheet2 = workbook2.getSheetAt(0);
		
		if(compareTwoSheets(sheet1, sheet2))
		{
			System.out.println("\n\n The two excel files are equal");
		}
		
		//1.Workbook---------XSSFWorkbook
		//2.Sheet------------XSSFSheet
		//3.Rows-------------XSSFRow
		//4.Cells------------XSSFCell
		
				
	}

	
	public static boolean compareTwoSheets(XSSFSheet sheet1, XSSFSheet sheet2)
	{
		boolean equalSheets = true;
		int firstRow = sheet1.getFirstRowNum();
		int lastRow = sheet1.getLastRowNum();
		
		for(int i=firstRow; i<=lastRow; i++)
		{
			System.out.println("\n\nComparing row "+i);
			XSSFRow row1= sheet1.getRow(i);
			XSSFRow row2 = sheet2.getRow(i);
			 if(!compareTwoRows(row1, row2)) {
	                equalSheets = false;
	                System.out.println("Row "+i+" - Not Equal");
	                break;
	            } else {
	               // System.out.println("Row "+i+" - Equal");
	            }
			
			
		}
				
		return equalSheets;
	}
	
	public static boolean compareTwoRows(XSSFRow row1, XSSFRow row2)
	{
		if((row1==null)&&(row2==null)){
			return true;
		}else if((row1 == null) || (row2 == null)) {
            return false;
        }
		 int firstCell1 = row1.getFirstCellNum();
	        int lastCell1 = row1.getLastCellNum();
		
		boolean equalRows = true;
		 for(int i=firstCell1; i <= lastCell1; i++) {
			 XSSFCell cell1 = row1.getCell(i);
	            XSSFCell cell2 = row2.getCell(i);
		if(!compareTwoCells(cell1, cell2)) {
            equalRows = false;
            System.err.println("       Cell "+i+" - NOt Equal");
            break;
        } else {
            System.out.println("       Cell "+i+" - Equal");
        }
	}
		
		return equalRows;
		
		
		}
	
	
	public static boolean compareTwoCells(XSSFCell cell1, XSSFCell cell2) {
        if((cell1 == null) && (cell2 == null)) {
            return true;
        } else if((cell1 == null) || (cell2 == null)) {
            return false;
        }
        
        boolean equalCells = false;
        int type1 = cell1.getCellType();
        int type2 = cell2.getCellType();
        if (type1 == type2) {
            if (cell1.getCellStyle().equals(cell2.getCellStyle())) {
                // Compare cells based on its type
                switch (cell1.getCellType()) {
                case HSSFCell.CELL_TYPE_FORMULA:
                    if (cell1.getCellFormula().equals(cell2.getCellFormula())) {
                        equalCells = true;
                    }
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    if (cell1.getNumericCellValue() == cell2
                            .getNumericCellValue()) {
                        equalCells = true;
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    if (cell1.getStringCellValue().equals(cell2
                            .getStringCellValue())) {
                        equalCells = true;
                    }
                    break;
                case HSSFCell.CELL_TYPE_BLANK:
                    if (cell2.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                        equalCells = true;
                    }
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    if (cell1.getBooleanCellValue() == cell2
                            .getBooleanCellValue()) {
                        equalCells = true;
                    }
                    break;
                case HSSFCell.CELL_TYPE_ERROR:
                    if (cell1.getErrorCellValue() == cell2.getErrorCellValue()) {
                        equalCells = true;
                    }
                    break;
                default:
                    if (cell1.getStringCellValue().equals(
                            cell2.getStringCellValue())) {
                        equalCells = true;
                    }
                    break;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return equalCells;
    }
}
