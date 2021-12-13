package day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day2 {

	public static void main(String[] args) throws IOException {
		ArrayList<String> list = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader("input/day2/input.txt"));
		String line = "";
		while ((line = reader.readLine()) != null) {
			list.add(line);
		}
		reader.close();
		Day2 day2 = new Day2();
		System.out.println(day2.part1(list));
		System.out.println(day2.part2(list));
	}
	
	
	private int part1(ArrayList<String> list) {
		int x = 0, y = 0;
		for (String line : list) {
			String[] parts = line.split(" ");
			String dir = parts[0];
			int scalar = Integer.valueOf(parts[1]);
			x += dir.equals("forward") ? scalar : 0;
			y += dir.equals("down") ? scalar : 0;
			y += dir.equals("up") ? -scalar : 0;
		}
		return x * y;
	}
	
	private int part2(ArrayList<String> list) {
		int x = 0, y = 0, aim = 0;
		for (String line : list) {
			String[] parts = line.split(" ");
			String dir = parts[0];
			int scalar = Integer.valueOf(parts[1]);
			x += dir.equals("forward") ? scalar : 0;
			y += dir.equals("forward") ? aim * scalar : 0;
			aim += dir.equals("down") ? scalar : 0;
			aim += dir.equals("up") ? -scalar : 0;
		}
		return x * y;
	}
}
