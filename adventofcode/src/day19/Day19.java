package day19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Day19 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day19/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day19 day19 = new Day19();
        day19.calculate(list);
    }

    String orientatios = "0 0 1 0 -1 0 1 0 0\n" + "0 -1 0 0 0 -1 1 0 0\n" + "0 1 0 0 0 -1 -1 0 0\n"
            + "0 -1 0 1 0 0 0 0 1\n" + "0 1 0 -1 0 0 0 0 1\n" + "-1 0 0 0 -1 0 0 0 1\n" + "0 -1 0 0 0 1 -1 0 0\n"
            + "1 0 0 0 -1 0 0 0 -1\n" + "0 1 0 1 0 0 0 0 -1\n" + "-1 0 0 0 1 0 0 0 -1\n" + "1 0 0 0 0 -1 0 1 0\n"
            + "0 0 1 1 0 0 0 1 0\n" + "-1 0 0 0 0 1 0 1 0\n" + "-1 0 0 0 0 -1 0 -1 0\n" + "1 0 0 0 1 0 0 0 1\n"
            + "0 0 -1 1 0 0 0 -1 0\n" + "0 0 1 0 1 0 -1 0 0\n" + "0 0 -1 0 -1 0 -1 0 0\n" + "0 -1 0 -1 0 0 0 0 -1\n"
            + "0 0 -1 -1 0 0 0 1 0\n" + "1 0 0 0 0 1 0 -1 0\n" + "0 0 -1 0 1 0 1 0 0\n" + "0 0 1 -1 0 0 0 -1 0\n"
            + "0 1 0 0 0 1 1 0 0";

    int[][] matrices = new int[24][9];
    HashSet<String> spacePointHashes = new HashSet<>();
    HashSet<Vector> spacePoints = new HashSet<>();
    HashMap<Integer, List<Vector>> scannerDataMap = new HashMap<>();
    ArrayList<Vector> distances = new ArrayList<>();
    boolean[] flags;

    private void calculate(ArrayList<String> list) {
        int index = -1;
        for (String line : list) {
            if (line.indexOf("scanner") >= 0) {
                index++;
            } else if (line.length() > 0) {
                String[] parts = line.split(",");
                scannerDataMap.putIfAbsent(index, new ArrayList<>());
                scannerDataMap.get(index).add(new Vector(Integer.valueOf(parts[0]),
                        Integer.valueOf(parts[1]), Integer.valueOf(parts[2])));
            }
        }
        for (int a = 0; a < matrices.length; a++) {
            String[] nums = orientatios.split("\\n")[a].split(" ");
            for (int b = 0; b < matrices[a].length; b++) {
                matrices[a][b] = Integer.valueOf(nums[b]);
            }
        }
        for (Vector vector : scannerDataMap.get(0)) {
            spacePointHashes.add(vector.toString());
            spacePoints.add(vector);
        }
        flags = new boolean[scannerDataMap.size()];
        boolean active = true;
        while (active) {
            active = false;
            for (int a = 1; a < scannerDataMap.size(); a++) {
                flags[a] = !flags[a] ? search(a) : flags[a];
                active = !flags[a] ? true : active;
            }
        }
        int maxDistance = Integer.MIN_VALUE;
        for (Vector v1 : distances) {
            for (Vector v2 : distances) {
                maxDistance = Math.max(v1.distance(v2), maxDistance);
            }
        }
        System.out.println("Part 1: " + spacePointHashes.size());
        System.out.println("Part 2: " + maxDistance);
    }

    boolean search(int currScanner) {
        for (Vector pos : spacePoints) {
            for (Vector currPos : scannerDataMap.get(currScanner)) {
                for (int[] matrix : matrices) {
                    int count = 0;
                    Vector transVect = transform(matrix, currPos);
                    Vector scannerPos = new Vector(pos.x - transVect.x, pos.y - transVect.y, pos.z - transVect.z);
                    for (Vector nextPos : scannerDataMap.get(currScanner)) {
                        transVect = transform(matrix, nextPos);
                        transVect = new Vector(scannerPos.x + transVect.x, scannerPos.y + transVect.y,
                                scannerPos.z + transVect.z);
                        count += spacePointHashes.contains(transVect.toString()) ? 1 : 0;
                    }
                    if (count >= 12) {
                        distances.add(scannerPos);
                        for (Vector nextPos : scannerDataMap.get(currScanner)) {
                            transVect = transform(matrix, nextPos);
                            transVect = new Vector(scannerPos.x + transVect.x, scannerPos.y + transVect.y,
                                    scannerPos.z + transVect.z);
                            if (!spacePointHashes.contains(transVect.toString())) {
                                spacePoints.add(transVect);
                            }
                            spacePointHashes.add(transVect.toString());
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    Vector transform(int[] matrix, Vector vector) {
        return new Vector(vector.x * matrix[0] + vector.y * matrix[1] + vector.z * matrix[2],
                vector.x * matrix[3] + vector.y * matrix[4] + vector.z * matrix[5],
                vector.x * matrix[6] + vector.y * matrix[7] + vector.z * matrix[8]);
    }

    class Vector {
        int x, y, z;

        Vector(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public String toString() {
            return x + " " + y + " " + z;
        }

        public int distance(Vector vector) {
            return Math.abs(vector.x - x) + Math.abs(vector.y - y) + Math.abs(vector.z - z);
        }
    }
}