package entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Paper {
	
	private String id;
	private String title;
	private int year;
	private boolean isDuplicated;
	
	public Paper() {
		
	}
	
	public Paper(String id, String name, int year, boolean isDuplicated) {
		this.id = id;
		this.title = name;
		this.year = year;
		this.isDuplicated = isDuplicated;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public boolean isDuplicated() {
		return isDuplicated;
	}

	public void setDuplicated(Boolean isduplicated) {
		this.isDuplicated = isduplicated;
	}

//	private Object getCellValue(Cell cell) {
//		
//		switch (cell.getCellTypeEnum()) {
//		case STRING:
//			return cell.getStringCellValue();
//		case BOOLEAN:
//			return cell.getBooleanCellValue();
//		case NUMERIC:
//			return cell.getNumericCellValue();
//		default:
//			break;
//		}
//		
//		return null;
//	}
	
	public List<Paper> readPapersFromExcelFile(String excelFilePath) throws IOException {
		
		List<Paper> papersList = new ArrayList<>();
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		
		Workbook workBook = new XSSFWorkbook(inputStream);
		Sheet sheet = workBook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();

		while(rowIterator.hasNext()) {
			Row nextRow = rowIterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			Paper newPaper = new Paper();

			while(cellIterator.hasNext()) {
				Cell nextCell = cellIterator.next();
				int columnIndex = nextCell.getColumnIndex();

				switch (columnIndex) {
					case 0:
						newPaper.setId(nextCell.getStringCellValue());
						break;
					case 1:
						newPaper.setTitle(nextCell.getStringCellValue());
						break;
//					case 3:
//						newPaper.setYear((int) getCellValue(nextCell));
//						break;
//					case 4:
//						newPaper.setDuplicated((Boolean) getCellValue(nextCell));
//						break;
					default:
						break;
				}

			}

			papersList.add(newPaper);
		}

		workBook.close();
		inputStream.close();

		return papersList;
	}

	public void writeExcelFile(List<Paper> papersList, String excelPathFile) throws IOException {
		Workbook workBook = new XSSFWorkbook();
		Sheet sheet = workBook.createSheet();

		int rowCount = 0;

		for (Paper newPaper : papersList) {
			Row newRow = sheet.createRow(++rowCount);
			writePaper(newPaper, newRow);
		}

		try (FileOutputStream outputStream = new FileOutputStream(excelPathFile)) {
			workBook.write(outputStream);
		}
		
		workBook.close();
	}

	private void writePaper(Paper paper, Row row) {
		Cell cell = row.createCell(1);
		cell.setCellValue(paper.getId());

		cell = row.createCell(2);
		cell.setCellValue(paper.getTitle());

		cell = row.createCell(3);
		cell.setCellValue(paper.getYear());

		cell = row.createCell(4);
		cell.setCellValue(paper.isDuplicated());
	}

//	private List<Paper> getListPaper() {
//		Paper paper1 = new Paper("PUB_0001", "Software development risk and project performance measurement: Evidence in Korea", "Kwan-SikNa", "James T.Simpson");
//		Paper paper2 = new Paper("PUB_0002", "Software development risk and project performance measurement: Evidence in Korea", "Kwan-SikNa", "James T.Simpson");
//		Paper paper3 = new Paper("PUB_0003", "Effective Software Testing: 50 Ways to Improve Your Software Testing", "Elfriede Dustin", "");
//		Paper paper4 = new Paper("PUB_0004", "[CITA__O] Software testing techniques", "B Beizer", "");
//
//		List<Paper> paperList = Arrays.asList(paper1, paper2, paper3, paper4);
//
//		return paperList;
//	}
}
