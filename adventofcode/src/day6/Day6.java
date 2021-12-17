package day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day6 {
    
    public static void main(String[] args) throws IOException {
        int[] nums = new int[] {};
        BufferedReader reader = new BufferedReader(new FileReader("input/day6/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            nums = new int[parts.length];
            for (int a = 0; a < parts.length; a++) {
                nums[a] = Integer.valueOf(parts[a]);
            }
        }
        reader.close();
        Day6 day6 = new Day6();
        System.out.println("Part 1: " + day6.calculate(80, nums));
        System.out.println("Part 2: " + day6.calculate(256, nums));
    }
    
    private long calculate(int days, int[] nums) {
        Arrays.fill(mem, -1);
        long total = 0;
        for (int n : nums) {
            total += countFish(n, days);
        }
        return total;
    }
    
    long[] mem = new long[256 * 7];
    private long countFish(int start, int daysLeft) {
        long count = 0, flag = 0;
        while (daysLeft > 0) {
            start--;
            if (start == 0) {
                start = 7;
                flag = 1;
            }
            if (flag > 0 && start == 6) {
                if (mem[start * 256 + daysLeft] < 0) {
                    mem[start * 256 + daysLeft] = countFish(8, daysLeft - 1);
                }
                count += mem[start * 256 + daysLeft];
                flag = 0;
            }
            daysLeft--;
        }
        return count + 1;
    }
}