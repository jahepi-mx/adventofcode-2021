package day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day16 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day16/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day16 day16 = new Day16();
        Packet packet = day16.calculate(list);
        System.out.println("Part1 " + packet.version);
        System.out.println("Part2 " + packet.num);
    }
    
    private Packet calculate(ArrayList<String> list) {
        int a = 0;
        String[] bits = "0000 0001 0010 0011 0100 0101 0110 0111 1000 1001 1010 1011 1100 1101 1110 1111".split(" ");
        String[] hash = new String[255];
        for (char ch : "0123456789ABCDEF".toCharArray()) {
            hash[ch] = bits[a++];
        }
        StringBuilder sb = new StringBuilder();
        for (char ch : list.get(0).toCharArray()) {
            sb.append(hash[ch]);
        }
        return choose(sb.toString(), Integer.parseInt(sb.substring(3, 6), 2), Integer.parseInt(sb.substring(0, 3), 2), 6);
    }
    
    private Packet parseOperator(String sb, int left, int type, int version) {
        ArrayList<Long> nums = new ArrayList<>();
        int orig = left - 6;
        int lengthType = sb.charAt(left++), versionSum = version;
        int offset = lengthType == '0' ? 15 : 11;
        int to = Integer.parseInt(sb.substring(left, left + offset), 2), from = 0;
        left += offset;
        while (from < to) {
            Packet packet = choose(sb.toString(), Integer.parseInt(sb.substring(left + 3, left + 6), 2), Integer.parseInt(sb.substring(left, left + 3), 2), left + 6);
            from += lengthType == '0' ? packet.len : 1;
            left += packet.len;
            versionSum += packet.version;
            nums.add(packet.num);
        }
        return new Packet(left, left - orig, versionSum, type, evaluate(type, nums));
    }
    
    private Packet parseLiteral(String sb, int left, int type, int version) {
        int orig = left - 6;
        String num = "";
        boolean active = true;
        while (active) {
            num += sb.substring(left + 1, left + 5);
            active = sb.charAt(left) == '0' ? false : active;
            left += 5;
        }
        return new Packet(left, left - orig, version, type, Long.parseLong(num, 2));
    }
    
    private long evaluate(int type, ArrayList<Long> nums) {
        long value = 0;
        switch (type) {
            case 0: for (long n : nums) {
                value += n;
            }; return value;
            case 1: value = 1;
            for (long n : nums) {
                value *= n;
            }; return value;
            case 2: value = Integer.MAX_VALUE;
            for (long n : nums) {
                value = Math.min(n, value);
            }; return value;
            case 3: value = Integer.MIN_VALUE;
            for (long n : nums) {
                value = Math.max(n, value);
            }; return value;
            case 5: return nums.get(0) > nums.get(1) ? 1 : 0;
            case 6: return nums.get(0) < nums.get(1) ? 1 : 0;
            default: return nums.get(0) == nums.get(1) ? 1 : 0;   
        }
    }
    
    private Packet choose(String sb, int type, int version, int pos) {
        return type == 4 ? parseLiteral(sb, pos, type, version) : parseOperator(sb, pos, type, version);
    }
    
    class Packet {
        int left, len, version, type;
        long num;
        Packet(int left, int len, int version, int type, long num) {
            this.left = left;
            this.len = len;
            this.version = version;
            this.type = type;
            this.num = num;
        }
    }   
}