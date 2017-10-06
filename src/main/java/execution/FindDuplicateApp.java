package execution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlbeans.impl.common.Levenshtein;

import entity.Paper;

public class FindDuplicateApp {

    public static void main(String[] args) throws IOException {
    	
    	Paper paper = new Paper();
    	
    	String excelFilePath = "/home/marcela/Documents/papers-2018.xlsx";
    	List<Paper> papersList = paper.readPapersFromExcelFile(excelFilePath);
    	List<Paper> duplicatedList = new ArrayList<>();
    	
    	for (int i = 0; i < papersList.size() - 1; i++) {
    		for (int k = i + 1; k < papersList.size(); k++) {
    			String actualTitle = papersList.get(i).getTitle().toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
    			String nextTitle = papersList.get(k).getTitle().toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
    			
    			int levenshteinDistance = Levenshtein.distance(actualTitle, nextTitle);
    			
    			if (levenshteinDistance <= 1) {
    				duplicatedList.add(papersList.get(k));
    			}
			}
			
		}
    	
    	for (int i = 0; i < duplicatedList.size(); i++) {
			System.out.println(duplicatedList.get(i).getId());
		}

    }
}
