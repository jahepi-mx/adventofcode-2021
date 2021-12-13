package day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day4 {
	
    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day4/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day4 day4 = new Day4();
        System.out.println(day4.part1(list));
        System.out.println(day4.part2(list));
    }
    
    int max;
    int[][] boards = new int[150][5 * 5];
    int[] nums;
    private void load(ArrayList<String> list) {
        max = 0;
        String[] numsStr = list.get(0).split(",");
        nums = new int[numsStr.length];
        for (int a = 0; a < numsStr.length; a++) {
            nums[a] = Integer.valueOf(numsStr[a]);
        }
        int[] board = new int[5 * 5];
        for (int a = 2, i = 0; a < list.size(); a++) {
            for (String n : list.get(a).trim().split("\\s+")) {
                if (n.length() > 0) {
                    board[i++] = Integer.valueOf(n);
                }
            }
            if (list.get(a).length() == 0) {
                boards[max++] = board.clone();
                i = 0;
            }
        }
        boards[max++] = board.clone();
    }
    
    private int part1(ArrayList<String> list) {
        load(list);
        for (int num : nums) {
            for (int a  = 0; a < max; a++) {
                setValue(boards[a], num);
            }
            for (int a  = 0; a < max; a++) {
                if (hasRowCol(boards[a])) {
                    int sum = 0;
                    for (int n : boards[a]) {
                        sum += n > 0 ? n : 0;
                    }
                    return sum * num;
                }
            }
        }
        return 0;
    }
    
    public void setValue(int[] board, int val) {
        for (int a = 0; a < board.length; a++) {
            board[a] = board[a] == val ? -1 : board[a];
        }
    }
    
    public boolean hasRowCol(int[] board) {
        for (int y = 0; y < 5; y++) {
            int sumRow = 0, sumCol = 0;
            for (int x = 0; x < 5; x++) {
                sumRow += board[y * 5 + x];
                sumCol += board[x * 5 + y];
            }
            if (sumCol == -5 || sumRow == -5) {
                return true;
            }
        }
        return false;
    }
    
    private int part2(ArrayList<String> list) {
        load(list);
        int wins = max;
        int[] boardWinners = new int[max];
        for (int num : nums) {
            for (int a  = 0; a < max; a++) {
                setValue(boards[a], num);
            }
            for (int a = 0; a < max; a++) {
                if (boardWinners[a] == 0 && hasRowCol(boards[a])) {
                    boardWinners[a] = 1;
                    if (--wins == 0) {
                        int sum = 0;
                        for (int n : boards[a]) {
                            sum += n > 0 ? n : 0;
                        }
                        return sum * num;
                    }
                }
            }
        }
        return 0;
    }
}