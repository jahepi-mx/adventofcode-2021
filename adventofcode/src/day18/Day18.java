package day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Day18 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day18/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day18 day18 = new Day18();
        System.out.println("Part 1: " + day18.part1(list));
        System.out.println("Part 2: " + day18.part2(list));
    }

    int part1(ArrayList<String> list) {
        String str = list.get(0);
        for (int a = 1; a < list.size(); a++) {
            str = "[" + str + "," + list.get(a) + "]";
            boolean flag = true;
            while (flag) {
                int count = 0;
                boolean explode = true;
                while (explode) {
                    String tmp = str;
                    str = explode(str);
                    explode = !str.equals(tmp);
                    count += explode ? 1 : 0;
                }
                boolean split = true;
                String tmp = str;
                str = split(str);
                split = !str.equals(tmp);
                count += split ? 1 : 0;
                flag = count == 0 ? false : flag;
            }
        }
        return magnitude(str);
    }
    
    int part2(ArrayList<String> list) {
        int max = Integer.MIN_VALUE;
        for (int a = 0; a < list.size(); a++) {
            for (int b = 0; b < list.size(); b++) {
                String str = "[" + list.get(a) + "," + list.get(b) + "]";
                boolean flag = true;
                while (flag) {
                    int count = 0;
                    boolean explode = true;
                    while (explode) {
                        String tmp = str;
                        str = explode(str);
                        explode = !str.equals(tmp);
                        count += explode ? 1 : 0;
                    }
                    boolean split = true;
                    String tmp = str;
                    str = split(str);
                    split = !str.equals(tmp);
                    count += split ? 1 : 0;
                    flag = count == 0 ? false : flag;
                }
                max = Math.max(max, magnitude(str));
            }
        }
        return max;
    }

    int magnitude(String s) {
        String prev = "";
        Stack<String> stack = new Stack<>();
        for (char ch : s.toCharArray()) {
            if ("[,]".indexOf(ch) == -1) {
                prev += ch;
            }
            if (ch == '[') {
                stack.push(ch + "");
            }
            if ("[,]".indexOf(ch) >= 0 && prev.length() > 0) {
                stack.push(prev);
            }
            if (ch == ']') {
                int a = Integer.valueOf(stack.pop());
                int b = Integer.valueOf(stack.pop());
                stack.pop();
                stack.push((b * 3 + a * 2) + "");
            }
            if ("[,]".indexOf(ch) >= 0) {
                prev = "";
            }
        }
        return Integer.valueOf(stack.pop());
    }

    String split(String s) {
        String prev = "";
        int index = 0, start = 0;
        for (char ch : s.toCharArray()) {
            if ("[,]".indexOf(ch) == -1) {
                prev += ch;
            }
            if ("[,]".indexOf(ch) >= 0 && prev.length() > 0) {
                int num = Integer.valueOf(prev);
                if (num >= 10) {
                    int left = num / 2;
                    int right = num / 2 + (num % 2 > 0 ? 1 : 0);
                    return s.substring(0, start + 1) + "[" + left + "," + right + "]" + s.substring(index, s.length());
                }
            }
            if ("[,]".indexOf(ch) >= 0) {
                start = index;
                prev = "";
            }
            index++;
        }
        return s;
    }

    String explode(String s) {
        String prev = "";
        int index = 0, start = 0, level = 0;
        for (char ch : s.toCharArray()) {
            level += ch == '[' ? 1 : (ch == ']' ? -1 : 0);
            if ("[]".indexOf(ch) == -1) {
                prev += ch;
            }
            if ("]".indexOf(ch) >= 0 && prev.length() > 0 && level >= 4) {
                if (prev.indexOf(",") >= 0 && prev.length() > 1) {
                    int left = Integer.valueOf(prev.split(",")[0]);
                    int right = Integer.valueOf(prev.split(",")[1]);
                    String leftStr = s.substring(0, start);
                    String rightStr = s.substring(index + 1, s.length());
                    String str = replaceLeft(leftStr, left) + "0";
                    str += replaceRight(rightStr, right);
                    return str;
                }
            }
            if ("[".indexOf(ch) >= 0) {
                start = index;
            }
            if ("[]".indexOf(ch) >= 0) {
                prev = "";
            }
            index++;
        }
        return s;
    }

    String replaceRight(String s, int n) {
        String prev = "";
        int index = 0, start = 0;
        for (char ch : s.toCharArray()) {
            if ("[],".indexOf(ch) == -1) {
                prev += ch;
            }
            if ("[],".indexOf(ch) >= 0) {
                if (prev.length() > 0) {
                    String tmp = s.substring(0, start + 1);
                    tmp += (Integer.valueOf(prev) + n);
                    tmp += s.substring(index, s.length());
                    return tmp;
                }
                start = index;
                prev = "";
            }
            index++;
        }
        return s;
    }

    String replaceLeft(String s, int n) {
        String prev = "";
        int start = s.length() - 1;
        for (int a = s.length() - 1; a >= 0; a--) {
            char ch = s.charAt(a);
            if ("[],".indexOf(ch) == -1) {
                prev = ch + prev;
            }
            if ("[],".indexOf(ch) >= 0) {
                if (prev.length() > 0) {
                    String tmp = s.substring(0, a + 1);
                    tmp += (Integer.valueOf(prev) + n);
                    tmp += s.substring(start, s.length());
                    return tmp;
                }
                start = a;
                prev = "";
            }
        }
        return s;
    }
}