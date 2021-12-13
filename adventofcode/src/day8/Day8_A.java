package day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day8_A {

    public static void main(String[] args) throws IOException {
		ArrayList<String> list = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader("input/day8/input2.txt"));
		String line = "";
		while ((line = reader.readLine()) != null) {
			list.add(line);
		}
		reader.close();
		run(list);
    }
    
    public static void run(ArrayList<String> list) {
        int result = 0;
        int[] allowedLengths = new int[] {0, 0, 1, 1, 1, 0, 0, 1};
        for (String str : list) {
            var numbers = str.split("\\|")[1].trim().split(" "); 
            for (String number : numbers) {
                result += allowedLengths[number.length()];
            }
        }
        System.out.println(result);
    }
}