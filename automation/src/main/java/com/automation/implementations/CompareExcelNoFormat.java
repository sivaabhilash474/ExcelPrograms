package com.automation.implementations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CompareExcelNoFormat {
 static FileWriter fw;
 static BufferedWriter bw;
 static String xlsExclusionFilePath;

 public CompareExcelNoFormat() {
	 
	
 }

 public static void main(String[] args) throws IOException {
     String filePath1 = "C:\\excelfiles\\sample1.xlsx";
     String filePath2 = "C:\\excelfiles2\\sample1.xlsx";
     String reportDir = "C:\\results";
     String exclusionCellListPath = "C:\\exclusion\\exclusionCellValues.txt";
     compareWorkbooks(filePath1, filePath2, reportDir, 0, exclusionCellListPath);

     /*try {
         compareWorkbooks(filePath1, filePath2, reportDir, 0);
     } catch (IOException var5) {
         var5.printStackTrace();
     }*/

 }

 public static String compareWorkbooks(String filePath1, String filePath2, String reportDir, int startTab, String exclusionCellListPath) throws IOException {
     try {
         File file1 = new File(filePath1);
         File file2 = new File(filePath2);
         xlsExclusionFilePath = exclusionCellListPath;
         FileInputStream excelFile1 = new FileInputStream(file1);
         FileInputStream excelFile2 = new FileInputStream(file2);
         String outFile = "";
         String outFileTxt = "";
         String prefix = "Compare_Report_";
         String[] splitArr = filePath2.split(Pattern.quote(File.separator));
         String splitFileNameTmp = splitArr[splitArr.length - 1];
         String splitFileName = splitFileNameTmp.split("\\.")[0];
         outFile = prefix + splitFileName + ".xlsx";
         FileOutputStream outXLS = new FileOutputStream(new File(reportDir + File.separatorChar + outFile));
         String[] fileParts = outFile.split("\\.");
         outFileTxt = fileParts[0];
         File file = new File(reportDir + File.separatorChar + outFileTxt + " .txt");
         if (!file.exists()) {
             file.createNewFile();
         }

         fw = new FileWriter(file.getAbsolutePath());
         bw = new BufferedWriter(fw);
         XSSFWorkbook workbook1 = new XSSFWorkbook(excelFile1);
         int numSheet1 = workbook1.getNumberOfSheets();
         XSSFWorkbook workbook2 = new XSSFWorkbook(excelFile2);
         int numSheet2 = workbook2.getNumberOfSheets();
         System.out.println("#################################Excel Data Compare Report##################");
         System.out.println("============================================================================");
         System.out.println("Source File: " + filePath1 + "\r\n");
         System.out.println("Target File: " + filePath2 + "\r\n");
         bw.write("###############################Excel Data Compare Report#########################");
         bw.write("==================================================================================");
         int comSheets;
         if (numSheet1 != numSheet2) {
             System.err.println("The number of sheets do not match(#Source sheets: " + numSheet1 + "#Target Sheets: " + numSheet2 + ")\r\n");
             bw.write("The number of sheets do not match(#Source sheets: " + numSheet1 + "#Target sheets: " + numSheet2 + ")\r\n");
             if (numSheet1 < numSheet2) {
                 comSheets = numSheet1;
             } else {
                 comSheets = numSheet2;
             }
         } else {
             comSheets = numSheet1;
         }

         System.out.println("=====================================================");
         bw.write("=============================================================");

         for(int cntr = startTab; cntr < comSheets; ++cntr) {
             XSSFSheet sheet1 = workbook1.getSheetAt(cntr);
             XSSFSheet sheet2 = workbook2.getSheetAt(cntr);
             String sheetName1 = workbook1.getSheetName(cntr);
             String sheetName2 = workbook2.getSheetName(cntr);
             Font my_font = workbook2.createFont();
             short color = 10;
             short bold = 700;
             my_font.setBoldweight(bold);
             my_font.setColor(color);
             CellStyle style = workbook2.createCellStyle();
             style.setFont(my_font);
             if (sheetName1.equals(sheetName2)) {
                 System.out.println("*********************************************************");
                 System.out.println("Comparing WorkSheet :" + sheetName1);
                 bw.write("************************************************************");
                 bw.write("comparing worksheet: " + sheetName1 + "\r\n");
                 if (compareTwoSheets(style, sheet1, sheet2)) {
                     System.out.println("The contents of the worksheet MATCHES");
                     System.out.println("***********************************************");
                     bw.write("The contents of the worksheet MATCHES\r\n");
                     bw.write("*******************************************************\r\n");
                 } else {
                     System.err.println("The contents of the worksheets DOES NOT MATCH \r\n");
                     System.out.println("**************************************************");
                     bw.write("The contents of the worksheet DOES NOT MATCH\r\n");
                     bw.write("************************************************************");
                 }
             } else {
                 System.err.println("Sheet Names don't match");
                 System.out.println("SheetName in Source Workbook (" + filePath1 + ") : " + sheetName1);
                 System.out.println("SheetName in Target Workbook (" + filePath2 + ") : " + sheetName2 + "\r\n");
                 bw.write("\nSheet Names don't match\r\n");
                 bw.write("SheetName in Source Workbook (" + filePath1 + ") : " + sheetName1 + "\r\n");
                 bw.write("SheetName in Target Workbook (" + filePath2 + ") : " + sheetName2 + "\r\n");
             }
         }

         workbook2.write(outXLS);
         outXLS.close();
         System.out.println("##################################################### End of Report ####################################");
         bw.write("########################################################## End of Report ###################################");
         excelFile1.close();
         excelFile2.close();
         bw.close();
         return "Successfully Done";
     } catch (Exception var31) {
         var31.printStackTrace();
         return "Process Failed !!!!";
     }
 }

 public static List<String> readFileToList(File file) throws FileNotFoundException, IOException{
	 List<String> strLine = new ArrayList<String>();
	 try (BufferedReader br = new BufferedReader (new FileReader(file))){
		 for(String line; (line = br.readLine()) != null;){
			 strLine.add(line);
		 }
	 }
	 return strLine;
 }
 
 public static ArrayList<Integer> getListNonMergedRowsInExcelSheet(Sheet sheet){
	 ArrayList<Integer> nonMergedList = new ArrayList<>();
	 
	 for(int row =0; row < sheet.getLastRowNum(); ++row){
		 int n = getNumberOfMergedRegionsInExcelSheet(sheet, row);
		 if(n ==0){
			 nonMergedList.add(Integer.valueOf(row));
		 }
	 }
	 
	 return nonMergedList;
 }
 
 public static int getNumberOfMergedRegionsInExcelSheet(Sheet sheet, int row){
	 int count = 0;
	 
	 for(int i=0; i<sheet.getNumMergedRegions(); ++i){
		 org.apache.poi.ss.util.CellRangeAddress range = sheet.getMergedRegion(i);
		 if(range.getFirstRow() <= row && range.getLastRow() >= row){
			 ++count;
		 }
	 }
	 
	 return count;
 }
 
 public static boolean compareTwoSheets(CellStyle style, Sheet sheet1, Sheet sheet2) throws IOException {
     int firstRow1 = 0;
     int firstRow2 = 0;
     int compFirstRow = 0;
     List<String> excludeCellValuesList = null;
     if(StringUtils.isNotBlank(xlsExclusionFilePath)){
    	 try{
    		 excludeCellValuesList = readFileToList(new File(xlsExclusionFilePath));
    	 } catch (FileNotFoundException var21){
    		 System.out.println("!!!! WARNING ==== XLS exclusion List not provided. Exception - "+var21.getMessage());
    	 }
     }
     
     for(int r =0; r<100; ++r){
    	 Row firstRow = sheet1.getRow(r);
    	 if(firstRow != null){
    		 boolean firstrowtest = isRowEmpty(firstRow);
    		 if(!firstrowtest){
    			 compFirstRow = r;
    			 break;
    		 }
    	 }
     }
     
     new ArrayList();
     new ArrayList();
     
     ArrayList<Integer> myNmList1 = getListNonMergedRowsInExcelSheet(sheet1);
     ArrayList<Integer> myNmList2 = getListNonMergedRowsInExcelSheet(sheet2);
     int lastRow1;
     Row firstR2;
     for(lastRow1 =0; lastRow1 <= myNmList1.size(); ++lastRow1){
    	 firstR2 = sheet1.getRow(((Integer)myNmList1.get(lastRow1)).intValue());
    	 if(firstR2 != null){
    		 boolean rowtest1 = isRowEmpty(sheet1.getRow(((Integer)myNmList1.get(lastRow1)).intValue()));
    		 if(!rowtest1){
    			 firstRow1 = ((Integer)myNmList1.get(lastRow1)).intValue();
    			 break;
    		 }
    	 }
     }
     
     for(lastRow1 =0 ; lastRow1 <= myNmList2.size(); ++lastRow1){
    	 firstR2 = sheet2.getRow(((Integer)myNmList2.get(lastRow1)).intValue());
    	 if(firstR2 != null){
    		 boolean rowtest2 = isRowEmpty(sheet2.getRow(((Integer)myNmList2.get(lastRow1)).intValue()));
    		 if(!rowtest2){
    			 firstRow2 = ((Integer)myNmList2.get(lastRow1)).intValue();
    			 break;
    		 }
    	 }
     }
     
     
     
     
     
  /*   boolean rowtest2;
     for(lastRow1 = 0; lastRow1 < 100; ++lastRow1) {
         firstR2 = sheet1.getRow(lastRow1);
         if (firstR2 != null) {
             rowtest2 = isRowEmpty(firstR2);
             if (!rowtest2) {
                 firstRow1 = lastRow1;
                 break;
             }
         }
     }

     for(lastRow1 = 0; lastRow1 < 100; ++lastRow1) {
         firstR2 = sheet2.getRow(lastRow1);
         if (firstR2 != null) {
             rowtest2 = isRowEmpty(firstR2);
             if (!rowtest2) {
                 firstRow2 = lastRow1;
                 break;
             }
         }
     }
     */
     

     lastRow1 = sheet1.getLastRowNum();
     int lastRow2 = sheet2.getLastRowNum();
     System.out.println("Last Row for Sheet1 - " +lastRow1);
     System.out.println("Last Row for Sheet2 - " +lastRow2);
     lastRow1 = getLastNonEmptyRowNum(sheet1, lastRow1);
     lastRow2 = getLastNonEmptyRowNum(sheet2, lastRow2);
     System.out.println("Last NonEmpty row for sheet1 - " +lastRow1);
     System.out.println("Last NonEmpty row for sheet2 - " +lastRow2);
     
     int numCol1 = sheet1.getRow(firstRow1).getPhysicalNumberOfCells();
     int numCol2 = sheet2.getRow(firstRow2).getPhysicalNumberOfCells();
//     int comRows = false;
     int comRows;
     if (lastRow1 > lastRow2) {
         comRows = lastRow2;
     } else {
         comRows = lastRow1;
     }

     if (lastRow1 != lastRow2) {
         System.err.println("The number of rows dont match (Source sheet: " + lastRow1 + "Target sheet: " + lastRow2 + ")");
         bw.write("The number of columns dont match (Source sheet: " + numCol1 + "Target sheet: " + numCol2 + ")\r\n");
     }

     if(numCol1 != numCol2){
    	 System.err.println("The number of columns DONT MATCH (Source Sheet: " +numCol1 + " Target Sheet: "+numCol2 +")");
    	 bw.write("The number of columns DONT MATCH (Source Sheet: "+ numCol1 + " Target Sheet: " +numCol2 +")\r\n");
     }
     
     boolean equalSheets = true;

     for(int i = compFirstRow; i <= comRows; ++i) {
         XSSFRow row1 = (XSSFRow)sheet1.getRow(i);
         XSSFRow row2 = (XSSFRow)sheet2.getRow(i);
         if (!compareTwoRows(style, row1, row2, excludeCellValuesList)) {
             equalSheets = false;
             System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
             System.err.println("==> Row" + (i + 1) + " <== Not Equal");
             System.out.println("\r\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\r\n");
             bw.write(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\r\n");
             bw.write("==> Row " + (i + 1) + " <== Not Equal\r\n");
             bw.write(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\r\n");
         }
     }

     return equalSheets;
 }

 public static boolean compareTwoRows(CellStyle style, XSSFRow row1, XSSFRow row2) throws IOException {
     if (row1 == null && row2 == null) {
         return true;
     } else if (row1 != null && row2 != null) {
         int firstCell1 = row1.getFirstCellNum();
         int lastCell1 = row1.getLastCellNum();
         int lastCell2 = row2.getLastCellNum();
         boolean equalRows = true;
         short comCols;
         if (lastCell1 > lastCell2) {
             comCols = (short) lastCell2;
         } else {
             comCols = (short) lastCell1;
         }

         short pattern = 4;
         style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
         style.setFillPattern(pattern);

         for(int i = firstCell1; i <= comCols - 1; ++i) {
             XSSFCell cell1 = row1.getCell(i);
             XSSFCell cell2 = row2.getCell(i);
             if (!compareTwoCells(cell1, cell2)) {
                 equalRows = false;
                 if (cell1 != null && cell1.getCellType() != 3) {
                     if (cell2 != null && cell2.getCellType() != 3) {
                         cell2.setCellStyle(style);
                         bw.write("Target worksheet cell " + (i + 1) + "is Blank or null \r\n");
                     } else {
                         bw.write("Target Worksheet cell " + (i + 1) + " is Blank or null\r\n");
                     }
                 } else {
                     bw.write("Source worksheet cell " + (i + 1) + " is Blank or null\r\n");
                 }
             }
         }

         return equalRows;
     } else {
         return false;
     }
 }

 
 public static boolean compareTwoRows(CellStyle style, XSSFRow row1, XSSFRow row2, List<String> excludeCellValuesList) throws IOException {
	 if(row1 == null && row2 == null) {
		 return true;
	 } else if(row1 != null && row2 != null) {
		 int firstCell1 = row1.getFirstCellNum();
		 int lastCell1 = row1.getLastCellNum();
		 int lastCell2 = row2.getLastCellNum();
		 int comCols;
		 boolean equalRows = true;
		 
		 if(lastCell1 > lastCell2) {
			 comCols = lastCell2;
		 } else {
			 comCols = lastCell1;
		 }
		 style.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
		 style.setFillPattern((short)4);
		 
		 for(int i = firstCell1; i <= comCols - 1; ++i) {
             XSSFCell cell1 = row1.getCell(i);
             XSSFCell cell2 = row2.getCell(i);
             if (cell1 != null && cell2 != null && !compareTwoCells(cell1, cell2, excludeCellValuesList)) {
                 equalRows = false;
                 if ((cell1.getCellType() != 1 || cell1.getStringCellValue() != null) && cell1.getCellType() != 3) {
                    	 if((cell2.getCellType() != 1 || cell2.getStringCellValue() != null) && cell2.getCellType() != 3) {
                         cell2.setCellStyle(style);
                         bw.write("Cell  " + (i + 1) + " ==> Not Equal\r\n");
                     } else {
                         bw.write("Target Worksheet cell " + (i + 1) + " is Blank or NULL\r\n");
                     }
                 } else {
                     bw.write("Source worksheet cell " + (i + 1) + " is Blank or NULL\r\n");
                 }
             }
         }

         return equalRows;
     } else {
         return false;
		 
	 }
 }
 
	private static boolean compareTwoCells(XSSFCell cell1, XSSFCell cell2, List<String> excludeCellValuesList) throws IOException {
	boolean equalCells = false;
	int type1 = cell1.getCellType();
	int type2 = cell1.getCellType();
	if(type1 != type2) {
		return false;
	} else {
		switch(cell1.getCellType()) {
		case 0:
			double diffVal = 0.0D;
			double cellVal1 = cell1.getNumericCellValue();
			double cellVal2 = cell2.getNumericCellValue();
			diffVal = cellVal1 - cellVal2;
			if(Math.abs(diffVal) < 1.0E-10D) {
				equalCells = true;
			} else {
				bw.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\r\n");
				bw.write("value in source: " + cellVal1 + "\r\n");
                bw.write("value in target: " + cellVal2 + "\r\n");
                bw.write("Difference in values: " + Math.abs(diffVal) + "\r\n");
                bw.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\r\n");
            }
            break;
        case 1:
            if (cell1.getStringCellValue() != null & cell1.getStringCellValue() != null) {
                if (excludeCellValuesList != null && excludeCellValuesList.size() > 0) {
                	Iterator var12 = excludeCellValuesList.iterator();
                	while(var12.hasNext()) {
                		String exclVal = (String)var12.next();
                		if(cell1.getStringCellValue().contains(exclVal) && cell2.getStringCellValue().contains(exclVal)) {
                			bw.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\r\n");
                			bw.write("Excluding cell comparision for excludeCell List provided in Source - " + cell1.getStringCellValue());
                			bw.write("Excluding cell comparision for excludeCell List provided in Target - " + cell2.getStringCellValue());
                			bw.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\r\n");
                			equalCells = true;
                			break;
            
             }
          }
      }
                
          if (cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
                equalCells = true;
            } else {
            	bw.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\r\n");
                bw.write("Value in source: " + cell1.getStringCellValue() + "\r\n");
                bw.write("Value in target: " + cell2.getStringCellValue() + "\r\n");
                bw.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\r\n");
            	
            }
            } else if(cell1.getStringCellValue() == null && cell1.getStringCellValue() == null) {
            	equalCells = true;
            }
            	else {
            		equalCells = false;
            	}
            	break;
        case 2:
            if (cell1.getCellFormula().equals(cell2.getCellFormula())) {
                equalCells = true;
            }
            break;
        case 3:
            if (cell2.getCellType() == 3) {
                equalCells = true;
            }
            break;
        case 4:
            if (cell1.getBooleanCellValue() == cell2.getBooleanCellValue()) {
                equalCells = true;
            }
            break;
        case 5:
            if (cell1.getErrorCellValue() == cell2.getErrorCellValue()) {
                equalCells = true;
            }
            break;
        default:
            if (cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
                equalCells = true;
            }
        }

        return equalCells;
    } 
   }


public static boolean compareTwoCells(XSSFCell cell1, XSSFCell cell2) throws IOException {
     if (cell1 == null && cell2 == null) {
         return true;
     } else if (cell1 != null & cell2 != null) {
         boolean equalCells = false;
         int type1 = cell1.getCellType();
         int type2 = cell1.getCellType();
         if (type1 == type2) {
             switch(cell1.getCellType()) {
             case 0:
                 double diffVal = 0.0D;
                 double cellVal1 = cell1.getNumericCellValue();
                 double cellVal2 = cell2.getNumericCellValue();
                 diffVal = cellVal1 - cellVal2;
                 if (Math.abs(diffVal) < 1.0E-10D) {
                     equalCells = true;
                 } else {
                     System.out.println("Numeric value not matching");
                     System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                     System.out.println("Value in source: " + cellVal1);
                     System.out.println("Value in target: " + cellVal2);
                     System.out.println("Difference is: " + Math.abs(diffVal) + "\r\n");
                     System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                     bw.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                     bw.write("value in source: " + cellVal1 + "\r\n");
                     bw.write("value in target: " + cellVal2 + "\r\n");
                     bw.write("Difference in values: " + Math.abs(diffVal) + "\r\n");
                     bw.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\r\n");
                 }
                 break;
             case 1:
                 if (cell1.getStringCellValue() != null & cell1.getStringCellValue() != null) {
                     if (cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
                         equalCells = true;
                     } else {
                         System.err.println("\r\nString Value not matching\r\n");
                         System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                         System.out.println("Value in source: " + cell1.getStringCellValue());
                         System.out.println("Value in target: " + cell2.getStringCellValue());
                         System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                         bw.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\r\n");
                         bw.write("Value in source: " + cell1.getStringCellValue() + "\r\n");
                         bw.write("Value in target: " + cell2.getStringCellValue() + "\r\n");
                         bw.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\r\n");
                     }
                 }
                 break;
             case 2:
                 if (cell1.getCellFormula().equals(cell2.getCellFormula())) {
                     equalCells = true;
                 }
                 break;
             case 3:
                 if (cell2.getCellType() == 3) {
                     equalCells = true;
                 }
                 break;
             case 4:
                 if (cell1.getBooleanCellValue() == cell2.getBooleanCellValue()) {
                     equalCells = true;
                 }
                 break;
             case 5:
                 if (cell1.getErrorCellValue() == cell2.getErrorCellValue()) {
                     equalCells = true;
                 }
                 break;
             default:
                 if (cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
                     equalCells = true;
                 }
             }

             return equalCells;
         } else {
             return false;
         }
     } else {
         return false;
     }
 }

 public static boolean isRowEmpty(Row row) {
     for(int c = row.getFirstCellNum(); c < row.getLastCellNum(); ++c) {
         Cell cell = row.getCell(c);
         if (cell != null && cell.getCellType() != 3) {
             return false;
         }
     }

     return true;
 }
 
 public static int getLastNonEmptyRowNum(Sheet sheet, int lastRowNum){
	 int rowNum = lastRowNum;
	 
	 for(Row lastRow = sheet.getRow(lastRowNum); isRowEmpty(lastRow); lastRow = sheet.getRow(rowNum)){
		 --rowNum;
	 }
	 
	 return rowNum;
 }
}
