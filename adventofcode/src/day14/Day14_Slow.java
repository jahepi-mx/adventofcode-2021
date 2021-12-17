package day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Day14_Slow {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day14/test.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day14_Slow day14 = new Day14_Slow();
        System.out.println("Part 1: " + day14.calculate(list));
    }
    
    HashMap<Integer, Integer> map = new HashMap<>();
    
    private int calculate(ArrayList<String> list) {
        char[] chars = list.get(0).toCharArray();
        for (int a = 2; a < list.size(); a++) {
            String[] parts = list.get(a).split(" -> ");
            map.put(parts[0].charAt(0) * 200 + parts[0].charAt(1), parts[1].charAt(0) * 1);
        }
        int a = 0, steps = 10;
        int[] sums = new int[255];
        for (a = 0; a < chars.length - 1; a++) {
            Arrays.fill(count, 0);
            sums[chars[a]]++;
            count(chars[a], chars[a + 1], steps);
            for (int i = 0; i < count.length; i++) {
                sums[i] += count[i];
            }
        }
        sums[chars[a]]++;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int n : sums) {
            if (n != 0) {
                min = Math.min(min, n);
                max = Math.max(max, n);
            }
        }
        return max - min;
    }
    
    long[] count = new long[200];
    public void count(int prev, int next, int level) {
        if (level == 0) {
            return;
        }
        int value = map.get(prev * 200 + next);
        count[value]++;
        count(prev, value, level - 1);
        count(value, next, level - 1);
    }
    
    public String print(int prev, int next, int level) {
        if (level == 0) {
            return "";
        }
        int value = map.get(prev * 200 + next);
        System.out.println((char) prev + "" + (char) next + "->" + (char)value + "    L" + level);
        return print(prev, value, level - 1) + "" + ((char)value) + "" + print(value, next, level - 1);
    }
}