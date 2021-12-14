package day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Day8_B {
    
    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day8/input2.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        run(list);
    }
    
    public static void run(ArrayList<String> list) {
        int[][] nums = {
            {1,1,1,1,1,1,0},/*0*/{0,0,1,1,0,0,0},/*1*/{0,1,1,0,1,1,1},/*2*/{0,1,1,1,1,0,1},/*3*/{1,0,1,1,0,0,1},/*4*/
            {1,1,0,1,1,0,1},/*5*/{1,1,0,1,1,1,1},/*6*/{0,1,1,1,0,0,0},/*7*/{1,1,1,1,1,1,1},/*8*/{1,1,1,1,1,0,1},/*9*/
        };
        int result = 0;
        for (String str : list) {
            var numbers = str.split("\\|")[0].trim().split(" ");
            var fourNumbers = str.split("\\|")[1].trim().split(" ");
            var map = new HashMap<String, Integer>();
            for (String number : numbers) {
                char[] chars = number.toCharArray();
                Arrays.sort(chars);
                map.put(new String(chars), 0);
            }
            next: for (String perm : unlock("abcdefg", "", new ArrayList<>())) {
                for (int a = 0; a < nums.length; a++) {
                    String tmp = "";
                    for (int i = 0; i < nums[a].length; i++) {
                        if (nums[a][i] > 0) {
                            tmp += perm.charAt(i);
                        }
                    }
                    char[] chars = tmp.toCharArray();
                    Arrays.sort(chars);
                    tmp = new String(chars);
                    if (!map.containsKey(tmp)) {
                        continue next;
                    }
                    map.put(tmp, a);
                }
                break;
            }
            for (int a = 0, pow = 1000; a < fourNumbers.length; a++, pow /= 10) {
                char[] chars = fourNumbers[a].toCharArray();
                Arrays.sort(chars);
                result += map.get(new String(chars)) * pow;
            }
        }
        System.out.println(result);
    }
    
    public static ArrayList<String> unlock(String str, String out, ArrayList<String> keys) {
        if (str.length() == 0) {
            keys.add(out);
        }
        for (int a = 0; a < str.length(); a++) {
            unlock(str.substring(0, a) + str.substring(a + 1, str.length()), out + str.charAt(a), keys);
        }
        return keys;
    }
}