package day25;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Day25 {
	public static void main(String[] args) throws IOException {
		ArrayList<String> list = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader("adventofcode/input/day25/input.txt"));
		String line = "";
		while ((line = reader.readLine()) != null) {
			list.add(line);
		}
		reader.close();
		Day25 day25 = new Day25();
		System.out.println("Part 1: " + day25.part1(list));
	}

	long part1(ArrayList<String> list) {
		int width = list.get(0).length();
		int height = list.size();
		int[] matrix = new int[width * height];
		for (int y = 0; y < list.size(); y++) {
			for (int x = 0; x < list.get(y).length(); x++) {
				matrix[y * width + x] = list.get(y).charAt(x) == '>' ? 1 : (list.get(y).charAt(x) == 'v' ? 2 : 0);
			}
		}
		boolean active = true;
		int steps = 0;
		HashSet<Integer> visited = new HashSet<>();
		while (active) {
			active = false;
			visited.clear();
			for (int a = 0; a < width * height; a++) {
				int x = a % width;
				int y = a / width;
				int value = matrix[a];
				int nextX = (x + 1) % width;
				int nextY = y;
				int nextValue = matrix[nextY * width + nextX];
				if (!visited.contains(nextY * width + nextX) && !visited.contains(y * width + x) && value == 1
						&& nextValue == 0) {
					matrix[nextY * width + nextX] = value;
					matrix[a] = 0;
					visited.add(nextY * width + nextX);
					visited.add(y * width + x);
					active = true;
				}
			}
			visited.clear();
			for (int a = 0; a < width * height; a++) {
				int x = a % width;
				int y = a / width;
				int value = matrix[a];
				int nextX = x;
				int nextY = (y + 1) % height;
				int nextValue = matrix[nextY * width + nextX];
				if (!visited.contains(nextY * width + nextX) && !visited.contains(y * width + x) && value == 2
						&& nextValue == 0) {
					matrix[nextY * width + nextX] = value;
					matrix[a] = 0;
					visited.add(nextY * width + nextX);
					visited.add(y * width + x);
					active = true;
				}
			}
			steps++;
		}
		return steps;
	}
}