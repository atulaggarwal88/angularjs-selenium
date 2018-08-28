package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileReader {
	private Workbook workbook;
	private Sheet sheet;
	private InputStream fis;
	private String excelPath;

	private static ExcelFileReader excelFileReader = null;

	private ExcelFileReader(String ePath) {
		this.excelPath = ePath;
		File file = new File(excelPath);
		try{
			fis = new FileInputStream(file);
			//			workbook = new XSSFWorkbook(fis);
			workbook = WorkbookFactory.create(fis);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static ExcelFileReader getInstance(String excelPath) {
		synchronized(ExcelFileReader.class) {
			if(excelFileReader == null) {
				excelFileReader = new ExcelFileReader(excelPath);
			}
		}
		return excelFileReader;		
	}
	public String readSheet_IndxRowCol(int sheetIndex, int rowId, int colId) {
		sheet = workbook.getSheetAt(sheetIndex);
		Cell cell = sheet.getRow(rowId).getCell(colId);		
		return cell.toString();
	}
	public void writeSheet_IndxRowCol(int sheetIndex, int rowId, int colId, String cellVal) {
		sheet = workbook.getSheetAt(sheetIndex);
		System.out.println(sheet.getSheetName());
		Row row = sheet.getRow(rowId);
		if(row == null) row = sheet.createRow(rowId);
		Cell cell = row.getCell(colId);
		if(cell == null) cell = row.createCell(colId);
		cell.setCellValue(cellVal);		
		System.out.println(cell);
		writeWorkbook();
	}
	private void writeWorkbook() {
		try (FileOutputStream fos = new FileOutputStream(excelPath)){		
			workbook.write(fos);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {			
		}		
	}

	public void writeSheet_IndxMapOfList(HashMap<String, ArrayList<String>> map, int sheetIndex) {
		AtomicInteger rowId = new AtomicInteger(0);
		AtomicInteger colId = new AtomicInteger(-1);

		//		map.keySet().stream()
		//		//		.map(k -> k)
		//		.forEach(k -> writeSheet_IndxRowCol(sheetIndex, rowId.get(), colId.incrementAndGet(), k));	
		map.entrySet().stream()
		.forEach(e -> {
			writeSheet_IndxRowCol(sheetIndex, 0, colId.incrementAndGet(), e.getKey());
			rowId.set(0);
			e.getValue().stream()
			.forEach(v -> {				
				writeSheet_IndxRowCol(sheetIndex, rowId.incrementAndGet(), colId.get(), v);
			});
		});
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> l1 = new ArrayList<>();
		l1.add("l1_1");l1.add("l1_2");		
		ArrayList<String> l2 = new ArrayList<>(l1);
		ArrayList<String> l3 = new ArrayList<>(l1);
		HashMap<String, ArrayList<String>> map = new HashMap<>();
		map.put("team1", l1);map.put("team2", l2);map.put("team3", l3);				

		String fPath = "E:\\atul\\personal\\SOAProjects\\TestData\\XMLSoccerTest_TestData.xlsx";
		ExcelFileReader excelFileReader = ExcelFileReader.getInstance(fPath);
		excelFileReader.writeSheet_IndxMapOfList(map, 0);
	}


}
