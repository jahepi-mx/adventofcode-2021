package day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day5 {
	
    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day5/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day5 day5 = new Day5();
        System.out.println(day5.part1(list));
        System.out.println(day5.part2(list));
    }
    
    
    private int part1(ArrayList<String> list) {
        int[] matrix = new int[1000 * 1000];
        for (String item : list) {
            String[] parts = item.split(" -> ");
            int[] p1 = new int[] {Integer.valueOf(parts[0].split(",")[0]), Integer.valueOf(parts[0].split(",")[1])};
            int[] p2 = new int[] {Integer.valueOf(parts[1].split(",")[0]), Integer.valueOf(parts[1].split(",")[1])};
            if (p1[0] == p2[0] || p1[1] == p2[1]) {
                matrix[p1[1] * 1000 + p1[0]]++;
                while (p2[0] - p1[0] != 0 || p2[1] - p1[1] != 0) {
                    p1[0] += p2[0] - p1[0] == 0 ? 0 : (p2[0] - p1[0])/Math.abs(p2[0] - p1[0]);
                    p1[1] += p2[1] - p1[1] == 0 ? 0 : (p2[1] - p1[1])/Math.abs(p2[1] - p1[1]);
                    matrix[p1[1] * 1000 + p1[0]]++;
                }
            }
        }
        int count = 0;
        for (int n : matrix) {
            count += n > 1 ? 1 : 0;
        }
        return count;
    }
    
    private int part2(ArrayList<String> list) {
        int[] matrix = new int[1000 * 1000];
        for (String item : list) {
            String[] parts = item.split(" -> ");
            int[] p1 = new int[] {Integer.valueOf(parts[0].split(",")[0]), Integer.valueOf(parts[0].split(",")[1])};
            int[] p2 = new int[] {Integer.valueOf(parts[1].split(",")[0]), Integer.valueOf(parts[1].split(",")[1])};
            matrix[p1[1] * 1000 + p1[0]]++;
            while (p2[0] - p1[0] != 0 || p2[1] - p1[1] != 0) {
                p1[0] += p2[0] - p1[0] == 0 ? 0 : (p2[0] - p1[0])/Math.abs(p2[0] - p1[0]);
                p1[1] += p2[1] - p1[1] == 0 ? 0 : (p2[1] - p1[1])/Math.abs(p2[1] - p1[1]);
                matrix[p1[1] * 1000 + p1[0]]++;
            }
        }
        int count = 0;
        for (int n : matrix) {
            count += n > 1 ? 1 : 0;
        }
        return count;
    }
}