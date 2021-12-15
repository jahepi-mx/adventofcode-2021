package day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Day15 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day15/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day15 day15 = new Day15();
        System.out.println(day15.part1(list));
        System.out.println(day15.part2(list));
    }
    
    private int width, height;
    int[] map;
    
    private int part1(ArrayList<String> list) {
        width = list.get(0).length();
        height = list.size();
        map = new int[width * height];
        for (int a = 0, c = 0; a < list.size(); a++) {
            for (int b = 0; b < list.get(a).length(); b++) {
                map[c++] = list.get(a).charAt(b) - 48;
            }
        }
        return minCost();
    }
    
    private int part2(ArrayList<String> list) {
        width = list.get(0).length() * 5;
        height = list.size() * 5;
        map = new int[width * height];
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                for (int oy = 0; oy < height / 5; oy++) {
                    for (int ox = 0; ox < width / 5; ox++) {
                        int value = list.get(oy).charAt(ox) - 49;
                        map[(y * height / 5 + oy) * width + (x * width / 5 + ox)] = (value + y + x) % 9 + 1;
                    }
                }
            }
        }
        return minCost();
    }
    
    private int minCost() {
        int currNode = 0;
        int[] visited = new int[width * height];
        int[] distances = new int[width * height];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[0] = 0;
        visited[0] = 1;
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> (distances[a] - distances[b]));
        queue.add(currNode);
        while (!queue.isEmpty()) {
            currNode = queue.remove();
            for (int[] dirs : new int[][] {{1,0},{0,1},{-1,0},{0,-1}}) {
                int x = currNode % width + dirs[0], y = currNode / height + dirs[1];
                if (x >= 0 && y >= 0 && x < width && y < height && visited[y * width + x] == 0) {
                    if (distances[currNode] + map[y * width + x] < distances[y * width + x]) {
                        distances[y * width + x] = distances[currNode] + map[y * width + x];
                    }
                    queue.add(y * width + x);
                    visited[y * width + x] = 1;
                }
            }
        }
        return distances[width * height - 1];
    }
}