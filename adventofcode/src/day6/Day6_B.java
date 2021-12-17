package day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day6_B {
    
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
        
        Day6_B day6 = new Day6_B();
        System.out.println("Part 1: " + day6.calculate(nums, 80));
        System.out.println("Part 2: " + day6.calculate(nums, 256));
    }
    
    private long calculate(int[] nums, int days) {
        long[] slots = new long[9];
        long sum = 0;
        for (int n : nums) {
            slots[n]++;
        }
        while (days-- > 0) {
            long tmp = slots[0];
            slots[0] = slots[1];
            slots[1] = slots[2];
            slots[2] = slots[3];
            slots[3] = slots[4];
            slots[4] = slots[5];
            slots[5] = slots[6];
            slots[6] = slots[7] + tmp;
            slots[7] = slots[8];
            slots[8] = tmp;
        }
        for (long n : slots) {
            sum += n;
        }
        return sum;
    }
}