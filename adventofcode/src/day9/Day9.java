package day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Day9 {
	
    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day9/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day9 day9 = new Day9();
        System.out.println(day9.part1(list));
        System.out.println(day9.part2());
    }
    
    int[][] dirs = new int[][] {{1,0}, {0,1}, {-1,0}, {0,-1}};
    int height, width;
    int[] matrix;
    ArrayList<Integer> basins = new ArrayList<>();
    
    private int part1(ArrayList<String> list) {
        int sum = 0;
        height = list.size();
        width = list.get(0).length();
        matrix = new int[width * height]; 
        for (int y = 0, x = 0; y < list.size(); y++, x = 0) {
            for (byte digit : list.get(y).getBytes()) {
                matrix[y * width + x++] = digit - 48;
            }
        }
        out: for (int i = 0; i < width * height; i++) {
            int x = i % width, y = i / width, val = matrix[i];
            for (int[] dir : dirs) {
                int newX = x + dir[0], newY = y + dir[1];
                if (newX >= 0 && newX < width && newY >= 0 && newY < height && val >= matrix[newY * width + newX]) {
                    continue out;
                }
            }
            basins.add(i);
            sum += val + 1;
        }
        return sum;
    }
    
    private int part2() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int basin : basins) {
            HashSet<Integer> visited = new HashSet<>();
            Queue<Integer> queue = new LinkedList<>();
            queue.add(basin);
            visited.add(basin);
            while (!queue.isEmpty()) {
                int qBasin = queue.remove();
                int x = qBasin % width, y = qBasin / width;
                for (int[] dir : dirs) {
                    int newX = x + dir[0], newY = y + dir[1];
                    if (newX >= 0 && newX < width && newY >= 0 && newY < height &&
                        !visited.contains(newY * width + newX) && matrix[newY * width + newX] < 9) {
                        queue.add(newY * width + newX);
                        visited.add(newY * width + newX);
                    }
                }
            }
            list.add(visited.size());
        }
        Collections.sort(list, Collections.reverseOrder());
        return list.get(0) * list.get(1) * list.get(2);
    }
}