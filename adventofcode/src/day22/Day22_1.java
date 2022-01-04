package day22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Day22_1 {
    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day22/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day22_1 day22 = new Day22_1();
        System.out.println("Part 1: " + day22.part1(list));
    }

    int part1(ArrayList<String> list) {
        Vector bounds = new Vector(-50, 50, 0);
        Vector offset = new Vector(100, 100, 100);
        HashSet<Integer> set = new HashSet<>();
        for (String line : list) {
            Boolean isOn = line.split(" ")[0].equals("on");
            String xs = line.split(" ")[1].split(",")[0];
            String ys = line.split(" ")[1].split(",")[1];
            String zs = line.split(" ")[1].split(",")[2];
            String[]  xPoints = xs.split("=")[1].split("\\.\\.");
            String[]  yPoints = ys.split("=")[1].split("\\.\\.");
            String[]  zPoints = zs.split("=")[1].split("\\.\\.");
            int xFrom = Math.max(Integer.valueOf(xPoints[0]), bounds.x);
            int xTo = Math.min(Integer.valueOf(xPoints[1]), bounds.y);
            int yFrom = Math.max(Integer.valueOf(yPoints[0]), bounds.x);
            int yTo = Math.min(Integer.valueOf(yPoints[1]), bounds.y);
            int zFrom = Math.max(Integer.valueOf(zPoints[0]), bounds.x);
            int zTo = Math.min(Integer.valueOf(zPoints[1]), bounds.y);
            for (int x = xFrom; x <= xTo; x++) {
                for (int y = yFrom; y <= yTo; y++) {
                    for (int z = zFrom; z <= zTo; z++) {
                        int tx = x + offset.x;
                        int ty = y + offset.y;
                        int tz = z + offset.z;
                        if (isOn) {
                            set.add(tx << 16 | ty << 8 | tz);
                        } else {
                            set.remove(tx << 16 | ty << 8 | tz);
                        }
                    }
                }
            }
        }
        return set.size();
    }

    int part2(ArrayList<String> list) {
        return 0;
    }

    class Vector {
        int x, y, z;
        Vector(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
