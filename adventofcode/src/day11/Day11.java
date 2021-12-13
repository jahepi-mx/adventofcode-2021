package day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day11 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day11/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day11 day11 = new Day11();
        day11.calculate(list);
    }
    
    int width = 10, height = 10;
    int[][] dirs = new int[][] {{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}};
    private void calculate(ArrayList<String> list) {
        int a = 0;
        int[] matrix = new int[width * height];
        for (String line : list) {
            for (String n : line.split("")) {
                matrix[a++] = Integer.valueOf(n);
            }
        }
        int days = 0, count = 0;
        while (true) {
            days++;
            int[] visited = new int[width * height];
            int total = 0;
            for (a = 0; a < width * height; a++) {
                if (visited[a] == 0) {
                    if (matrix[a] == 9) {
                        total += flash(a, matrix, visited) + 1;
                    } else {
                        matrix[a]++;
                    }
                }
            }
            count += total;
            if (days == 100) {
                System.out.println("Part 1: " + count);
            }
            if (total == width * height) {
                System.out.println("Part 2: " + days);
                return;
            }
        }
    }
    
    private int flash(int a, int[] matrix, int[] visited) {
        matrix[a] = 0;
        visited[a] = 1;
        int x = a % width, y = a / width, count = 0;
        for (int[] dir : dirs) {
            int newX = x + dir[0], newY = y + dir[1], v = newY * width + newX;
            if (newX >= 0 && newX < width && newY >= 0 && newY < height && visited[v] == 0) {
                if (matrix[v] == 9) {
                    count += flash(v, matrix, visited) + 1;
                } else {
                    matrix[v]++;
                }
            }
        }
        return count;
    }
}