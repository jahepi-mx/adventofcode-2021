package day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Day10 {

	public static void main(String[] args) throws IOException {
		ArrayList<String> list = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader("input/day10/input.txt"));
		String line = "";
		while ((line = reader.readLine()) != null) {
			list.add(line);
		}
		reader.close();
		Day10 day10 = new Day10();
		day10.calculate(list);
	}

	private void calculate(ArrayList<String> list) {
		int score = 0;
		String open = "({[<", close = ")}]>";
		int[] points = { 3, 1197, 57, 25137 }, points2 = { 1, 3, 2, 4 };
		ArrayList<Long> scores = new ArrayList<>(); 
		for (String line : list) {
			Stack<Character> stack = new Stack<>();
			for (char ch : line.toCharArray()) {
				int openIndex = open.indexOf(ch), closeIndex = close.indexOf(ch);
				if (openIndex >= 0) {
					stack.push(ch);
				}
				if (closeIndex >= 0) {
					openIndex = open.indexOf(stack.pop());
					score += openIndex != closeIndex ? points[closeIndex] : 0;
					if (openIndex != closeIndex) {
						stack.empty();
					}
				}
			}
			long sum = 0;
			while (!stack.isEmpty()) {
				sum *= 5;
				sum += points2[open.indexOf(stack.pop())];
			}
			scores.add(sum);
		}
		Collections.sort(scores);
		System.out.println("Part1: " + score);
		System.out.println("Part2: " + scores.get(scores.size() / 2));
	}
}
