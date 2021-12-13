package day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day7 {

	public static void main(String[] args) throws IOException {
		ArrayList<Integer> list = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader("input/day7/input.txt"));
		String line = "";
		while ((line = reader.readLine()) != null) {
			for (String n : line.split(",")) {
				list.add(Integer.valueOf(n));
			}
		}
		reader.close();
		Day7 day7 = new Day7();
		System.out.println(day7.part1(list));
		System.out.println(day7.part2(list));
	}
	
	
	private int part1(ArrayList<Integer> list) {
		int min = Integer.MAX_VALUE, res = min;
		int max = Integer.MIN_VALUE;
		int left = 0, right = 0;
		for (int n : list) {
			min = Math.min(n, min);
			max = Math.max(n, max);
		}
		int half = (max + min) / 2;
		for (int n : list) {
			left += n <= half ? 1 : 0;
			right += n > half ? 1 : 0;
		}
		int from = left > right ? min : half;
		int to = left > right ? half : max;
		for (int a = from, dist = 0; a <= to; a++, dist = 0) {
			for (int n : list) {
				dist += Math.abs(n - a);
			}
			res = Math.min(res, dist);
		}
		return res;
	}
	
	private int part2(ArrayList<Integer> list) {
		int min = Integer.MAX_VALUE, res = min;
		int max = Integer.MIN_VALUE;
		int left = 0, right = 0;
		for (int n : list) {
			min = Math.min(n, min);
			max = Math.max(n, max);
		}
		int half = (max + min) / 2;
		for (int n : list) {
			left += n <= half ? 1 : 0;
			right += n > half ? 1 : 0;
		}
		int from = left > right ? min : half;
		int to = left > right ? half : max;
		for (int a = from, dist = 0; a <= to; a++, dist = 0) {
			for (int n : list) {
				int tmp = Math.abs(n - a);
				dist += (tmp * tmp + tmp) / 2;
			}
			res = Math.min(res, dist);
		}
		return res;
	}
}
