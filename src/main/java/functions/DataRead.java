package functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import entity.Paper;

public class DataRead {
	
	public static void main(String[] args) throws IOException {
        List<Paper> papersList = new ArrayList<>();
        String excelFilePath = "/home/marcela/Documents/papers-2018.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = firstSheet.iterator();

        while (rowIterator.hasNext()) {
            Row nextRow = rowIterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            Paper newPaper = new Paper();

            while (cellIterator.hasNext()) {
                Cell nextCell = cellIterator.next();
                int columnIndex = nextCell.getColumnIndex();

                switch (columnIndex) {
                    case 1:
                        newPaper.setId((String) getCellValue(nextCell));
                        break;
                    case 2:
                        newPaper.setTitle((String) getCellValue(nextCell).toLowercase().replaceAll("[^a-zA-Z0-9]", ""));
                        break;
//                    case 3:
//                        System.out.print(cell.getNumericCellValue());
//                        break;
//                    case 4:
                    default:
                    	break;

                }
                //System.out.print(" - ");
            }
            papersList.add(newPaper);
            //System.out.println();
        }

        workbook.close();
        inputStream.close();

        List<Paper> duplicatePapers = new ArrayList<>();
        for (int i = 0; i < papersList.size(); i++) {
            if (papersList.get(i).getTitle().equals(papersList.get(i++).getTitle())) {
                duplicatePapers.add(papersList.get(i++));
            }
        }

        for (int i = 0; i < duplicatePapers.size(); i++) {
            System.out.println(duplicatePapers.get(i).getId());
        }
    }
}
