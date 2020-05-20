package csv;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;

public class CSVReader {

	String filePath1;
	String filePath2;
	
	public CSVReader(){
		
	}
	
	public CSVReader(String filePath1){
	this.filePath1 = filePath1;	
	}
	
	public CSVReader(String filePath1, String filePath2){
		this.filePath1 = filePath1;	
		this.filePath2 = filePath2;
		}
	
	public void readCSV() throws IOException{
		File f = new File(this.filePath1);
		FileInputStream fis = new FileInputStream(f);
		BufferedInputStream bis = new BufferedInputStream(fis);
		int c;
		while( (c = bis.read()) != -1){
			System.out.println((char)c);
		}
		
		bis.close();
		fis.close();
	
		
	}
	
	public ArrayList<String[]> readCSVLine() throws IOException{
		File f = new File(this.filePath1);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<String[]> list1 = new ArrayList<String[]>();
		String sline;
		
		while(((sline = br.readLine()) != null)){
		
		System.out.println(sline);
		String[] arr = sline.split(",");
		list1.add(arr);
		}
		
		System.out.println(StringUtils.repeat("*", 45));
		
		for(String[] str: list1){
			for(String s: str){
				System.out.println(s);
			}
		}
		
		return list1;
	}
	
	
	public ArrayList<String[]> readTwoCSVs() throws IOException{
		File f1 = new File(this.filePath1);
		FileReader fr1 = new FileReader(f1);
		BufferedReader br1 = new BufferedReader(fr1);
		
		File f2 = new File(this.filePath2);
		FileReader fr2 = new FileReader(f2);
		BufferedReader br2 = new BufferedReader(fr2);
		
		ArrayList<String[]> list1 = new ArrayList<String[]>();
		ArrayList<String[]> list2 = new ArrayList<String[]>();
		String sline;
		String tline;
		int rowNum = 0;
		int colNum;
		int lineNum;
		String[] sheaders = null;
		String[] theaders = null;
		String[] snonHeaders;
		String[] tnonHeaders;
		while(((sline = br1.readLine()) != null) && ((tline = br2.readLine()) != null)){
		
		//System.out.println(sline + "\t" + tline);
		if(rowNum == 0){
		sheaders = sline.split(",");
		theaders = tline.split(",");
		list1.add(sheaders);
		list2.add(theaders);
		} else{
			snonHeaders = sline.split(",");
			tnonHeaders = tline.split(",");
			list1.add(snonHeaders);
			list2.add(tnonHeaders);
		}
		rowNum++;
		
		}
		System.out.println(StringUtils.repeat("*", 100));
		
		System.out.println(StringUtils.repeat("=", 30)+"HEADER INFORMATION"+StringUtils.repeat("=", 36));
		System.out.println("Number of Source File Headers: "+sheaders.length);
		System.out.println("Number of Target File Headers: "+theaders.length);
		System.out.println(StringUtils.repeat("-", 85));
		System.out.printf("%25s %15s %20s" ,"Source File Headers","|","Target File Headers");
		
		System.out.println();
		System.out.println(StringUtils.repeat("-", 85));
		
		for(int i=0; i<sheaders.length; i++){
			System.out.format("%25s %15s %20s\n", sheaders[i], "|", theaders[i]);
		}
		
		
		System.out.println(StringUtils.repeat("=", 30)+"END OF HEADER INFORMATION"+StringUtils.repeat("=", 30));
		
		br1.close();
		br2.close();
		
		return list1;
	}
	
	 	
	
	public static void main(String[] args) throws IOException{
		
		//"C:\\Source\\bookmonth.csv"
		//C:\\Users\\sivaa\\Downloads\\1000000 Sales Records\\1000000 Sales Records.csv
		
		CSVReader read = new CSVReader("C:\\Users\\sivaa\\Downloads\\1000000 Sales Records\\1000000 Sales Records.csv", "C:\\Users\\sivaa\\Downloads\\1000000 Sales Records\\1000000 Sales Records.csv");
		read.readTwoCSVs();
		
	}
	
	
}
