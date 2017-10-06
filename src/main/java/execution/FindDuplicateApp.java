package execution;

import entity.Paper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlbeans.impl.common.Levenshtein;

public class FindDuplicateApp {

	public static void main(String[] args) throws IOException {

		Paper paper = new Paper();

		String excelFilePath = "/home/marcela/Documents/papers-2018.xlsx";
		List<Paper> papersList = paper.readPapersFromExcelFile(excelFilePath);
		List<Paper> duplicatedList = new ArrayList<>();

		for (int i = 0; i < papersList.size() - 1; i++) {
			for (int k = i + 1; k < papersList.size(); k++) {

				if (!papersList.get(i).isDuplicated()) {
					String actualTitle = papersList.get(i).getTitle();
					String nextTitle = papersList.get(k).getTitle();

					actualTitle = cleanData(actualTitle);
					nextTitle = cleanData(nextTitle);

					int levenshteinDistance = Levenshtein.distance(actualTitle, nextTitle);

					if (levenshteinDistance <= 1) {
						duplicatedList.add(papersList.get(k));
						papersList.get(k).setDuplicated(true);
					}
				}

			}

		}

		int count = 0;
		for (int i = 0; i < duplicatedList.size(); i++) {
			System.out.println(duplicatedList.get(i).getId());
			count++;
		}

		System.out.println(count);

	}

	private static String cleanData(String title) {
		return title.toLowerCase().replaceAll("[^a-zA-Z0-9]", "").replace("[CITA__O]", "");
	}
}
