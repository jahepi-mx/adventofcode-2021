package day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day20 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day20/input1.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day20 day20 = new Day20();
        System.out.println("Part 1: " + day20.calculate(list, 2));
        System.out.println("Part 2: " + day20.calculate(list, 50));
    }
    
    String code = "";
    int width, offset;
    int[][] dirs = new int[][] {{-1,-1},{0,-1},{1,-1},{-1,0},{0,0},{1,0},{-1,1},{0,1},{1,1}};
    
    int calculate(ArrayList<String> list, int iterations) {
        offset = iterations * 3;
        code = list.get(0);
        width = list.get(2).length() + offset * 2;
        int[] image = new int[width * width];
        for (int y = 2; y < list.size(); y++) {
            int x = 0;
            for (char ch : list.get(y).toCharArray()) {
                image[(offset + y) * width + offset + x++] = ch == '#' ? 1 : 0;
            }
        }
        for (int a = 0; a < iterations; a++) {
            image = enhanceImage(image);
        }
        int count = 0;       
        for (int y = iterations; y < width - iterations; y++) {
            for (int x = iterations; x < width - iterations; x++) {
                count += image[y * width + x];
            }
        }
        return count;
    }
    
    private int[] enhanceImage(int[] image) {
        int[] newImage = new int[width * width];
        for (int y = 1; y < width - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int num = 0;
                for (int[] dir : dirs) {
                    int nx = x + dir[0], ny = y + dir[1];
                    num <<= 1;
                    num += image[ny * width + nx];
                }
                newImage[y * width + x] = code.charAt(num) == '#' ? 1 : 0;
            }
        }
        return newImage;
    }
}