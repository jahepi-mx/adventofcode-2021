package day17;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day17 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day17/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day17 day17 = new Day17();
        int[] res = day17.calculate(list);
        System.out.println("Part 1: " + res[0]);
        System.out.println("Part 2: " + res[1]);
    }

    private int[] calculate(ArrayList<String> list) {
        String str = list.get(0).replaceAll("target area: x=", "").replaceAll(" y=", "");
        String[] parts = str.split(",");
        int xRectFrom = Integer.valueOf(parts[0].split("\\.\\.")[0]);
        int xRectTo = Integer.valueOf(parts[0].split("\\.\\.")[1]);
        int yRectFrom = Integer.valueOf(parts[1].split("\\.\\.")[0]);
        int yRectTo = Integer.valueOf(parts[1].split("\\.\\.")[1]);
        
        int max = Integer.MIN_VALUE;
        int nVelocities = 0;
        Vector position = new Vector();
        Vector velocity = new Vector();
        Vector acceleration = new Vector();
        for (int vx = 1; vx <= xRectTo; vx++) {
            for (int vy = -xRectTo / 2; vy <= xRectTo / 2; vy++) {
                position.x = 0;
                position.y = 0;
                velocity.x = vx;
                velocity.y = vy;
                acceleration.x = -1;
                acceleration.y = -1;
                int maxY = Integer.MIN_VALUE;
                boolean active = true;
                while (active) {
                    position.x += velocity.x;
                    position.y += velocity.y;
                    velocity.x += acceleration.x;
                    velocity.y += acceleration.y;
                    acceleration.x = velocity.x == 0 ? 0 : acceleration.x;
                    maxY = Math.max(maxY, position.y);
                    if (position.x >= xRectFrom && position.x <= xRectTo
                            && position.y >= yRectFrom && position.y <= yRectTo) {
                        nVelocities++;
                        active = false;
                    } else if (position.y < yRectFrom) {
                        maxY = Integer.MIN_VALUE;
                        active = false;
                    }
                }
                max = Math.max(max, maxY);
            }
        }
        return new int[] {max, nVelocities};
    }
    
    class Vector {
        int x, y;
    }
}