package day21;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day21 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("adventofcode/input/day21/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day21 day21 = new Day21();
        System.out.println("Part 1: " + day21.part1(list));
        System.out.println("Part 2: " + day21.part2(list));
    }

    int part1(ArrayList<String> list) {
        int spaceA = Integer.valueOf(list.get(0).split(":")[1].trim());
        int spaceB = Integer.valueOf(list.get(1).split(":")[1].trim());
        int dice = 0, rolls = 0;
        int scoreA = 0, scoreB = 0;
        while (true) {
            int sum = 3 * dice + 6;
            dice += 3;
            dice = dice % 100 > 0 ? dice % 100 : 100;
            rolls += 3;
            int tmp = (sum + spaceA) % 10 > 0 ? (sum + spaceA) % 10 : 10;
            scoreA += tmp;
            spaceA = tmp;
            if (scoreA >= 1000) {
                return scoreB * rolls;
            }
            sum = 3 * dice + 6;
            dice += 3;
            dice = dice % 100 > 0 ? dice % 100 : 100;
            rolls += 3;
            tmp = (sum + spaceB) % 10 > 0 ? (sum + spaceB) % 10 : 10;
            scoreB += tmp;
            spaceB = tmp;
            if (scoreB >= 1000) {
                return scoreA * rolls;
            }
        }
    }

    long part2(ArrayList<String> list) {
        int spaceA = Integer.valueOf(list.get(0).split(":")[1].trim());
        int spaceB = Integer.valueOf(list.get(1).split(":")[1].trim());

        for (int a = 0; a < memo.length; a++)
            for (int b = 0; b < memo[a].length; b++)
                for (int c = 0; c < memo[a][b].length; c++)
                    for (int d = 0; d < memo[a][b][c].length; d++)
                        for (int e = 0; e < memo[a][b][c][d].length; e++)
                            memo[a][b][c][d][e] = -1;

        return simulate(1, 0, 0, spaceA, spaceB);
    }

    long[][][][][] memo = new long[2][22][22][11][11];

    long simulate(int player, int scoreA, int scoreB, int spaceA, int spaceB) {
        if (scoreA >= 21) {
            return 1;
        }
        if (scoreB >= 21) {
            return 0;
        }
        if (memo[player][scoreA][scoreB][spaceA][spaceB] == -1) {
            long n = 0;
            for (int a = 1; a <= 3; a++) {
                for (int b = 1; b <= 3; b++) {
                    for (int c = 1; c <= 3; c++) {
                        if ((player ^ 1) == 0) {
                            int tmp = (a + b + c + spaceA) % 10;
                            tmp = tmp > 0 ? tmp : 10;
                            n += simulate(player ^ 1, scoreA + tmp, scoreB, tmp, spaceB);
                        } else {
                            int tmp = (a + b + c + spaceB) % 10;
                            tmp = tmp > 0 ? tmp : 10;
                            n += simulate(player ^ 1, scoreA, scoreB + tmp, spaceA, tmp);
                        }
                    }
                }
            }
            memo[player][scoreA][scoreB][spaceA][spaceB] = n;
        }
        return memo[player][scoreA][scoreB][spaceA][spaceB];
    }
}