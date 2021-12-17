package day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Day14 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day14/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day14 day14 = new Day14();
        System.out.println("Part 1: " + day14.calculate(list, 10));
        System.out.println("Part 2: " + day14.calculate(list, 40));
    }
    
    private long calculate(ArrayList<String> list, int steps) {
        HashMap<String, String> pairs = new HashMap<>();
        HashMap<String, Long> map = new HashMap<>();
        char[] chars = list.get(0).toCharArray();
        for (int a = 2; a < list.size(); a++) {
            String[] parts = list.get(a).split(" -> ");
            pairs.put(parts[0], parts[1]);
        }
        for (int a = 0; a < chars.length - 1; a++) {
            String key = chars[a] + "" + chars[a + 1];
            map.put(chars[a] + "" + chars[a + 1], map.containsKey(key) ? map.get(key) + 1 : 1);
        }
        for (int a = 0; a < steps; a++) {
            HashMap<String, Long> tmp = new HashMap<>();
            for (String key : map.keySet()) {
                long count = map.get(key);
                String prev = key.charAt(0) + pairs.get(key);
                String next =  pairs.get(key) + key.charAt(1);
                tmp.put(prev, (tmp.containsKey(prev) ? tmp.get(prev) : 0) + count);
                tmp.put(next, (tmp.containsKey(next) ? tmp.get(next) : 0) + count);
            }
            map = tmp;
        }
        
        Map<Character, Long> results = new HashMap<>();
        results.put(chars[0], 1l);
        results.put(chars[chars.length - 1], 1l);
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        for (String key : map.keySet()) {
            results.put(key.charAt(0), results.containsKey(key.charAt(0)) ? results.get(key.charAt(0)) + map.get(key) : map.get(key));
            results.put(key.charAt(1), results.containsKey(key.charAt(1)) ? results.get(key.charAt(1)) + map.get(key) : map.get(key));
        }
        for (char key : results.keySet()) {
            min = Math.min(min, results.get(key));
            max = Math.max(max, results.get(key));
        }
        return max / 2 - min / 2;
    }
}