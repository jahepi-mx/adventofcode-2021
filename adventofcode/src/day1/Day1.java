package day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day1 {

    public static void main(String[] args) throws IOException {
        ArrayList<Integer> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day1/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(Integer.valueOf(line));
        }
        reader.close();
        Day1 day1 = new Day1();
        System.out.println(day1.part1(list));
        System.out.println(day1.part2(list));
    }

    private int part1(ArrayList<Integer> list) {
        int prev = Integer.MAX_VALUE, count = 0;
        for (int n : list) {
            count += n > prev ? 1 : 0;
            prev = n;
        }
        return count;
    }

    private int part2(ArrayList<Integer> list) {
        int a = 0, b = 0, c = 0, d = 0, count = 0, i = 0;
        for (int n : list) {
            d = c;
            c = b;
            b = a;
            a = n;
            count += i++ >= 3 && b + c + d < a + b + c ? 1 : 0;
        }
        return count;
    }
}