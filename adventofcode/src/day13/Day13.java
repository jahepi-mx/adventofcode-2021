package day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Day13 {

	public static void main(String[] args) throws IOException {
		ArrayList<String> list = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader("input/day13/input.txt"));
		String line = "";
		while ((line = reader.readLine()) != null) {
			list.add(line);
		}
		reader.close();
		Day13 day13 = new Day13();
		day13.calculate(list);
	}

	private void calculate(ArrayList<String> list) {
		HashSet<Integer> positions = new HashSet<>();
		List<String> folds = new ArrayList<>();
		int rowLen = 10000;
    	for (String line : list) {
    		if (line.indexOf("fold") >= 0) {
    			folds.add(line);
    		} else if (line.length() > 0) {
    			positions.add(Integer.valueOf(line.split(",")[1]) * rowLen + Integer.valueOf(line.split(",")[0]));
    		}
    	}
    	for (int a = 0; a < folds.size(); a++) {
    		int n = Integer.valueOf(folds.get(a).split("=")[1]);
    		Boolean yAxis = folds.get(a).indexOf('y') >= 0;
    		HashSet<Integer> tmp = new HashSet<>();
    		for (int position : positions) {
    			int x = position % rowLen, y = position / rowLen;
    			if (yAxis && y < n) {
    				tmp.add(y * rowLen + x);
    			} else if(yAxis && (y = 2 * n - y) < n) {
	        		tmp.add(y * rowLen + x);
        		} else if (!yAxis && x < n) {
    				tmp.add(y * rowLen + x);
    			} else if (!yAxis && (x = 2 * n - x) < n) {
	        		tmp.add(y * rowLen + x);
        		}
    		}
    		positions = tmp;
    		System.out.println("Number of dots on fold " + (a + 1) + ": " + positions.size());
    	}
		for (int y = 0; y <= 10; y++) { // Prints hidden message for part 2
			for (int x = 0; x <= 40; x++) {
				System.out.print(positions.contains(y * rowLen + x) ? "*" : " ");
			}
			System.out.println("");
		}
	}
}
