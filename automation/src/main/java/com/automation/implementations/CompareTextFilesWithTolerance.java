package com.automation.implementations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class CompareTextFilesWithTolerance {

	
	public static String[] getFileListFromDir(String srcDir, final String fileExt) {
		String[] srcFileList = null;
		new ResponseObject();
		int cntr = 0;
		File sDir = new File(srcDir);
		File[] srcFiles = sDir.listFiles(new FilenameFilter() {
			public boolean accept(File sDir, String name) {
				return name.endsWith(fileExt);
			}
		});
		srcFileList = new String[srcFiles.length];
		File[] var7 = srcFiles;
		int var8 = srcFiles.length;
		
		for(int var9 =0; var9<var8; ++var9) {
			File mySrcFile = var7[var9];
			srcFileList[cntr] = mySrcFile.getAbsolutePath();
			++cntr;
		}
		
		return srcFileList;
	}
	public static void main(String[] args) throws IOException {
		
		String message = "";
		String srcDir ="C:\\source";  //just give the source directory name it will collectes all the CSV files in this directory
		String tgtDir ="C:\\target";
		String reportDir = "C:\\results\\";
		
		int rounding = 8;
		double tolerance = 1.0E-5D;
		String delim = "space";
		String fileExt = "csv";
		if(fileExt.equals("txt")) {
			delim = " ";
		} else if(fileExt.equals("csv")){
			delim = ",";
		}
		
		String[] srcFileList = getFileListFromDir(srcDir, fileExt);
		String[] tgtFileList = getFileListFromDir(tgtDir, fileExt);
		
		for(int i=0; i<srcFileList.length; ++i) {
			for(int j=0; j<tgtFileList.length; ++j) {
				File mySrcFile = new File(srcFileList[i]);
				File myTgtFile = new File(tgtFileList[j]);
				if(mySrcFile.getName().equals(myTgtFile.getName())) {
					compareFileContentsWrapper(srcFileList[i], tgtFileList[j], reportDir, delim, Integer.valueOf(rounding), tolerance);
					
				}
			}
		}
		
	}
	
	
	public static String compareFileContentsWrapper(String srcFile, String tgtFile, String reportDir, String delim, Object... objOptParms) throws IOException {
		String message = "";
		String delimiter = "";
		File mySrcFile = new File(srcFile);
		File myTgtFile = new File(tgtFile);
		if(delim.equals("space")) {
			delimiter = " ";
		} else if(delim.equals("comma") || delim.equals(",")) {
			delimiter = ",";
		}
		
		int rounding = objOptParms.length > 0 ? (Integer)objOptParms[0] : 14;
		double tolerance = objOptParms.length > 1 ? (Double)objOptParms[1] : 0.0D;
		long maxPrint = objOptParms.length > 2 ? (Long)objOptParms[2] : 100000L;
		
		String[] fileParts = splitPath("Compare Report_", tgtFile);
		String outFile = reportDir + fileParts[1].split("\\.")[0] +".txt";
		File myOutFileName = new File(outFile);
		message = compareTextFileContents(mySrcFile, myTgtFile, myOutFileName, delim, rounding, tolerance, maxPrint);
		return message;
	}
	
	
	private static String compareTextFileContents(File srcFile, File tgtFile, File outFile, String delimeter, int rounding, double tolerance, long maxPrint) throws IOException {
		String lineSrc = "";
		String lineTgt = "";
		String delim = "";
		String message = "";
		int cntr = 0;
		long numDiff = 0L;
		if(delimeter.equals("space")) {
			delim = " ";
		} else if(delimeter.equals("comma")) {
			delim = ",";
		}
		
		DecimalFormat df = new DecimalFormat("#");
		df.setMaximumFractionDigits(rounding);
		DecimalFormat dft = new DecimalFormat("#");
		dft.setMaximumFractionDigits(8);
		FileWriter fw = new FileWriter(outFile);
		BufferedWriter bw = new BufferedWriter(fw);
		LineIterator itSrc = FileUtils.lineIterator(srcFile);
		LineIterator itTgt = FileUtils.lineIterator(tgtFile);
		String srcPath = srcFile.getPath();
		String tgtPath = tgtFile.getPath();
		long srcFileLineCount = Files.lines(Paths.get(srcPath)).count();
		long tgtFileLineCount = Files.lines(Paths.get(tgtPath)).count();
		int numSrcCols = ((String)Files.lines(Paths.get(srcPath)).findFirst().get()).split(delim).length;
		int numTgtCols = ((String)Files.lines(Paths.get(tgtPath)).findFirst().get()).split(delim).length;
		System.out.println(StringUtils.repeat("#", 100));
		System.out.println("Source File		=> " +srcFile);
		System.out.println("Target File     => " +tgtFile);
		System.out.println("Report File     => " +outFile);
		System.out.println("Rounding        => " +rounding);
		System.out.println("Tolerance       => " +dft.format(tolerance));
		System.out.println(StringUtils.repeat("#", 100));
		bw.write(StringUtils.repeat("#", 100));
		bw.newLine();
		bw.write("Source File      => " + srcFile);
		bw.newLine();
		bw.write("Target File      => " + tgtFile);
		bw.newLine();
		bw.write("Report File      => " + outFile);
		bw.newLine();
		bw.write("Rounding         => " + rounding);
		bw.newLine();
		bw.write("Tolerance        => " + dft.format(tolerance));
		bw.newLine();
		bw.write(StringUtils.repeat("#", 100));
		bw.newLine();
		if(srcFileLineCount != tgtFileLineCount) {
			System.out.println(StringUtils.repeat("#", 100));
			System.out.println("WARNING :: Number of Rows do not Match, # Rows in Source: " + srcFileLineCount + " # Rows in Target: " +tgtFileLineCount);
			System.out.println(StringUtils.repeat("#", 100));
			bw.write(StringUtils.repeat("#", 100));
			bw.newLine();
			bw.write("WARNING :: Number of Rows do not Match, # Rows in Source: "+ srcFileLineCount + " # Rows in Target: "+tgtFileLineCount);
			bw.newLine();
			bw.write(StringUtils.repeat("#", 100));
			bw.newLine();
		}
		
		if(numSrcCols != numTgtCols) {
			System.out.println(StringUtils.repeat("#", 100));
			System.out.println("WARNING :: Number of Columns do not Match, # Cols in Source: " + numSrcCols + " # Cols in Target: "+numTgtCols);
			System.out.println(StringUtils.repeat("#", 100));
			bw.write(StringUtils.repeat("#", 100));
			bw.newLine();
			bw.write("WARNING :: Number of Column do not Match, # Cols in Source: "+numSrcCols +" #Cols in Target: "+numTgtCols +"\n");
			bw.write(StringUtils.repeat("#", 100));
			bw.newLine();
			
			
		}
		
		long commonLineCount = srcFileLineCount > tgtFileLineCount ? tgtFileLineCount : srcFileLineCount;
		long commonColCount = numSrcCols > numTgtCols ? (long)numTgtCols : (long)numSrcCols;
		ResponseObject obj = new ResponseObject();
		
		try {
			
			while(itSrc.hasNext() && (long)cntr != commonLineCount -1L) {
				lineSrc = itSrc.nextLine();
				lineTgt = itTgt.nextLine();
				if(!lineSrc.equals(lineTgt)) {
					String[] lineSrcSplit = lineSrc.split(delim, -1);
					String[] lineTgtSplit = lineTgt.split(delim, -1);
					
					for(int i=0; (long)i < commonColCount; ++i) {
						String splitSrc = lineSrcSplit[i];
						String splitTgt = lineTgtSplit[i];
						if(isNumeric(splitSrc) && isNumeric(splitTgt)) {
							double valSrc = RoundDouble(Double.parseDouble(splitSrc), rounding);
							double valTgt = RoundDouble(Double.parseDouble(splitTgt),rounding);
							if(!String.valueOf(valSrc).equals(String.valueOf(valTgt)) && Math.abs(valSrc - valTgt) >= tolerance) {
								++numDiff;
								if(numDiff <= maxPrint) {
									bw.write("Row "+(cntr +1) + " - Column " +(i+1) + ") :: Difference above tolerance " + dft.format(tolerance) + " |>>>| Source Value: " +df.format(valSrc) +" Target Value: " +df.format(valTgt) + " <==> Difference: " +df.format(Math.abs(valSrc-valTgt)));
									bw.newLine();
								}
							}
						} else if(!splitSrc.equals(splitTgt)) {
							++numDiff;
							if(numDiff <= maxPrint) {
								bw.write("(Row " +(cntr + 1) + " - Column " +(i+1) +") :: Souce Value: " + splitSrc + "Target Value: " +splitTgt);
								bw.newLine();
							}
						}
					}
				}
				
				++cntr;
				if(cntr % 1000000 == 0) {
					;
				}
			}
			
			if(numDiff >= maxPrint) {
				bw.write(StringUtils.repeat("@", 100));
				bw.newLine();
				bw.write("# of Differences Exceeded Limit of " + maxPrint + ", not printing any further differences");
				bw.newLine();
				bw.write(StringUtils.repeat("@", 100));
				bw.newLine();
			}
			
			obj.setMessage("Success");
			message = "Success";
			
		} catch(Exception e) {
			obj.setMessage(ExceptionUtils.getStackTrace(e));
			message = ExceptionUtils.getStackTrace(e);
			System.out.println(ExceptionUtils.getStackTrace(e));
		} finally {
			LineIterator.closeQuietly(itSrc);
			LineIterator.closeQuietly(itTgt);
			System.out.println(StringUtils.repeat("#", 100));
			System.out.println("# of Rows Compared  :  " +srcFileLineCount);
			System.out.println("# of Different Rows :  " +numDiff);
			System.out.println("# of Same Rows  :  " +(srcFileLineCount - numDiff));
			System.out.println(StringUtils.repeat("#", 100));
			bw.write(StringUtils.repeat("#", 100));
			bw.newLine();
			bw.write("# of Rows Compared  : "+srcFileLineCount);
			bw.newLine();
			bw.write("# of Rows Differnt Rows: " + numDiff);
			bw.newLine();
			bw.write("# of Same Rows  : " + (srcFileLineCount - numDiff));
			bw.newLine();
			bw.write(StringUtils.repeat("#", 100));
			
		}
		
		bw.flush();
		fw.close();
		bw.close();
		return message;
	}
	
	public static String CompareFiles(String inFile1, String inFile2, String reportDir, int rounding, String delim, double tolerance) {
		String message = "";
		String delimiter = "";
		if(delim.equals("space")) {
			delimiter = " ";
		} else if(delim.equals("comma")) {
			delimiter = ",";
		}
		
		String[] fileParts = splitPath("Compare Report_", inFile2);
		String outFileName = reportDir + fileParts[1].split("\\.")[0] + ".txt";
		ResponseObject obj = new ResponseObject();
		
		try {
			
			File outFile = new File(outFileName);
			BufferedWriter bw = CreateFile(outFile, 0);
			String[] fileContent1 = readFileToArray(inFile1);
			int numRows1 = fileContent1.length;
			String lineData1 = fileContent1[0];
			String[] numCells = lineData1.split(delimiter);
			int numCols1 = numCells.length;
			String[] fileContent2 = readFileToArray(inFile2);
			int numRows2 = fileContent2.length;
			String lineData2 = fileContent2[0];
			String[] numCells2 = lineData2.split(delimiter);
			int numCols2 = numCells2.length;
			DecimalFormat dft = new DecimalFormat("#");
			dft.setMaximumFractionDigits(8);
			System.out.println(StringUtils.repeat("*", 100));
			System.out.println("Source    =>  "+inFile1);
			System.out.println("Target    =>  "+inFile2);
			System.out.println("Rounding Value Applied: " + rounding + " Decimals");
			System.out.println("Tolerance Value Applied: "+ dft.format(tolerance));
			System.out.println(StringUtils.repeat("*", 100));
			bw.write(StringUtils.repeat("*", 100));
			bw.newLine();
			bw.write("Source => " +inFile1);
			bw.newLine();
			bw.write("Target => " +inFile2);
			bw.newLine();
			bw.write("Rounding Value Applied: " + rounding +" Decimals");
			bw.newLine();
			bw.write(StringUtils.repeat("*", 100));
			bw.newLine();
			if(numRows1 != numRows2) {
				System.out.println(StringUtils.repeat("*", 100));
				System.out.println("WARNING :: Number of Rows do not Match, # Rows in Source: "+ numRows1 + " # Rows in Target: "+numRows2);
				System.out.println(StringUtils.repeat("*", 100));
				bw.write(StringUtils.repeat("*", 100));
				bw.write("WARNING :: Number of Rows do not Match, # Rows in Source: " + numRows1 + " # Rows in Target: "+ numRows2 + "\n");
				bw.write(StringUtils.repeat("*", 100));
				
			}
			
			if(numCols1 != numCols2) {
				System.out.println(StringUtils.repeat("*", 100));
				System.out.println("WARNING :: Number of Columns do not Match, # Cols in Source: "+numCols1 + " # Cols in Target: "+numCols2);
				System.out.println(StringUtils.repeat("*", 100));
				bw.write(StringUtils.repeat("*", 100));
				bw.write("WARNING :: Number of Columns do not Match, #Cols in Source: "+numCols1 + " #Cols in Target: " +numCols2 +"\n");
				bw.write(StringUtils.repeat("*", 100));
				
			}
			
			int comRows;
			if(numRows1 > numRows2) {
				comRows = numRows2;
				
			} else {
				comRows = numRows1;
			}
			
			int comCols;
			if(numCols1 > numCols2) {
				comCols = numCols2;
			} else {
				comCols = numCols1;
			}
			
			String[][] cellData1 = new String[comRows][comCols + 1];
			String[][] cellData2 = new String[comRows][comCols + 1];
			String rowDiff = "N";
			int numDiff = 0;
			
			for(int i=0; i<comRows; ++i) {
				String tempData1 = fileContent1[i];
				String[] splitData1 = tempData1.split(delimiter,-1);
				String tempData2 = fileContent2[i];
				String[] splitData2 = tempData2.split(delimiter,-1);
				
				for(int j=0; j<comCols; ++j) {
					if(!splitData1[j].equals("") && !splitData2[j].equals("")) {
						cellData1[i][j] = splitData1[j];
						cellData2[i][j] = splitData2[j];
						if(i==0) {
							if(!cellData1[i][j].equals(cellData2[i][j])){
								System.out.println("DIFFERENCE FOUND IN HEADER ROW (ROW " +i + " - Column " +j + ") :: Source Value: " +cellData1[i][j] +" Target Value: "+cellData2[i][j]);
								bw.write("DIFFERENCE FOUND INHEADER ROW (ROW " + i + " - Column "+ j +") :: Source Value: " +cellData1[i][j] + " Target Value: "+ cellData2[i][j] + "\n");
								rowDiff = "Y";
							}
													
						} else if(isNumeric(cellData1[i][j]) && isNumeric(cellData2[i][j])) {
							DecimalFormat df = new DecimalFormat("#");
							df.setMaximumFractionDigits(rounding);
							Double val1 = RoundDouble(Double.parseDouble(cellData1[i][j]), rounding);
							Double val2 = RoundDouble(Double.parseDouble(cellData2[i][j]), rounding);
							if(!String.valueOf(val1).equals(String.valueOf(val2)) && Math.abs(val1-val2) >= tolerance) {
								System.out.println("Row " +(i+1) + " - Column " + (j+1) + ") :: Difference above tolerance " +df.format(tolerance) + " |>>>|  Source Value: " + df.format(val1) + " Target Value: " +df.format(val2) + " <==> "+df.format(Math.abs(val1 - val2)));
								
								bw.write("Row " +(i+1) + " - Column " + (j+1) + ") :: Difference above tolerance " +df.format(tolerance) + " |>>>|  Source Value: " + df.format(val1) + " Target Value: " +df.format(val2) + " <==> "+df.format(Math.abs(val1 - val2)));
								bw.newLine();
								rowDiff = "Y";
							}
						} else if(String.valueOf(cellData1[i][j]).equals(String.valueOf(cellData2[i][j]))){
							System.out.println("(Row " +(i+1) +" - Column " + (j+1) +") :: Difference above tolerance " +dft.format(tolerance) +" |>>>| Source Value: " +cellData1[i][j] + " Target Value: " +cellData2[i][j]);
							bw.write("(Row " +(i+1) +" - Column " + (j+1) +") :: Difference above tolerance " +dft.format(tolerance) +" |>>>| Source Value: " +cellData1[i][j] + " Target Value: " +cellData2[i][j]);
							bw.newLine();
							rowDiff = "Y";
						}
					} else {
						--comCols;
					}
				}
				
				if(rowDiff.equals("Y")) {
					++numDiff;
					rowDiff = "N";
				}
			}
					
			System.out.println(StringUtils.repeat("*", 100));
			System.out.println("# of Rows Compared : " +comRows);
			System.out.println("# of Different Rows: " + numDiff);
			System.out.println("# of Same Rows     : " +(comRows - numDiff));
			System.out.println(StringUtils.repeat("*", 100));
			bw.write(StringUtils.repeat("*", 100));
			bw.write("# of Rows Compared : " + comRows + "\n");
			bw.write("# of Different Rows :" + numDiff + "\n");
			bw.write("# of Same Rows      :" + (comRows - numDiff) + "\n");
			bw.write(StringUtils.repeat("*", 100));
			bw.close();
			obj.setMessage("Success");
			
		} catch(Exception e) {
			obj.setMessage(ExceptionUtils.getStackTrace(e));
			message = ExceptionUtils.getStackTrace(e);
			System.out.println(ExceptionUtils.getStackTrace(e));
		}
		
		return message;
	}
	
	
	private static String[] readFileToArray(String fileName) {
		List<String> fileArray = new ArrayList<>();
		String line = null;
		
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((line = bufferedReader.readLine()) != null) {
				fileArray.add(line);
			}
			
			bufferedReader.close();
			
		} catch(FileNotFoundException e) {
			System.out.println("Unable to open file '" + fileName + "'");
			
		} catch(IOException e) {
			System.out.println("Error reading file '" + fileName + "'");
		}
		
		return (String[])fileArray.toArray(new String[0]);
	}
	
	
	public static BufferedWriter CreateFile(File myFile, int mode) throws IOException {
	
		if(!myFile.exists()) {
			myFile.createNewFile();
		}
		
		FileWriter fw;
		BufferedWriter bw;
		if(mode ==1) {
			fw = new FileWriter(myFile.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			return bw;
		} else {
			fw = new FileWriter(myFile.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			return bw;
		}
		
	}
	
	
	
	public static boolean isNumeric(String str) {
		try {
			double var1 = Double.parseDouble(str);
			return true;			
		}catch(NumberFormatException e) {
			return false;
		}
	}
	
	
	public static double RoundDouble(double value, int places) {
		if(places < 0) {
			throw new IllegalArgumentException();
		} else {
			BigDecimal bd = new BigDecimal(value);
			bd = bd.setScale(places, RoundingMode.HALF_UP);
			return bd.doubleValue();
		}
	}
	
	public static String[] splitPath(String prefix, String filePath) {
		String[] pathArr = new String[2];
		String pattern = Pattern.quote(System.getProperty("file.separator"));
		String[] fileParts = filePath.split(pattern);
		int numParts = fileParts.length;
		String outPath = "";
		String fileName = "";
		
		int i;
		for(i =0; i<numParts; ++i) {
			if(i<numParts -1) {
				outPath = outPath + System.getProperty("file.separator") + fileParts[i];
			} else {
				fileName = fileParts[i];
			}
		}
		
		i = outPath.length();
		outPath = outPath.substring(i -(i - 2)) + System.getProperty("file.seperator");
		pathArr[0] = outPath + System.getProperty("file.separator");
		pathArr[1] = prefix + fileName;
		return pathArr;
	}
}
