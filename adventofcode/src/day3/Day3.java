package day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day3 {

	public static void main(String[] args) throws IOException {
		ArrayList<String> list = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader("input/day3/input.txt"));
		String line = "";
		while ((line = reader.readLine()) != null) {
			list.add(line);
		}
		reader.close();
		Day3 day3 = new Day3();
		System.out.println(day3.part1(list));
		System.out.println(day3.part2(list));
	}
	
	private int part1(ArrayList<String> list) {
		int[] sums = new int[list.get(0).length()];
		for (String line : list) {
			int pos = line.length() - 1;
			for (byte bit : line.getBytes()) {
				bit -= 48;
				sums[pos--] += bit > 0 ? 1 : -1;
			}
		}
		int pow = 1, a = 0, b = 0;
		for (int n : sums) {
			a += n <= 0 ? 0 : pow;
			b += n <= 0 ? pow : 0;
			pow *= 2;
		}
		return a * b;
	}
	
	private int part2(ArrayList<String> list) {
		return Integer.valueOf(getRating(list, 1), 2) * Integer.valueOf(getRating(list, 2), 2);
	}
	
	private String getRating(ArrayList<String> list, int type) {
		int pos = 0, size = list.get(0).length();
		while (list.size() > 1) {
			int total = 0;
			for (String line : list) {
				int bit = line.getBytes()[pos] - 48;
				total += bit > 0 ? 1 : -1;
			}
			ArrayList<String> tmp = new ArrayList<>();
			for (String line : list) {
				int bit = line.getBytes()[pos] - 48;
				if (type == 1 && (total > 0 || total == 0) && bit > 0) {
					tmp.add(line);
				} else if (type == 1 && total < 0 && bit < 1) {
					tmp.add(line);
				} else if (type == 2 && total < 0 && bit > 0) {
					tmp.add(line);
				} else if (type == 2 && (total > 0 || total == 0) && bit < 1) {
					tmp.add(line);
				}
			}
			list = tmp;
			pos++;
			pos %= size;
		}
		return list.get(0);
	}
}
