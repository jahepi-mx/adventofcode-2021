package day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Day12 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day12/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day12 day12 = new Day12();
        day12.calculate(list);
    }
    
    Map<String, Integer> nodeNames = new HashMap<>();
    Map<Integer, String> nodeIds = new HashMap<>();
    Map<Integer, Boolean> nodeTypes = new HashMap<>();
    int[][] map = new int[12][12];
    private void calculate(ArrayList<String> list) {
        int id = 0;
        for (String line : list) {
            String nodeA = line.split("-")[0], nodeB = line.split("-")[1];
            if (!nodeNames.containsKey(nodeA)) {
                nodeNames.put(nodeA, id);
                nodeIds.put(id, nodeA);
                nodeTypes.put(id++, nodeA.charAt(0) >= 'a');
            }
            if (!nodeNames.containsKey(nodeB)) {
                nodeNames.put(nodeB, id);
                nodeIds.put(id, nodeB);
                nodeTypes.put(id++, nodeB.charAt(0) >= 'a');
            }
            map[nodeNames.get(nodeA)][nodeNames.get(nodeB)] = 1;
            map[nodeNames.get(nodeB)][nodeNames.get(nodeA)] = 1;
        }
        
        int[] visits = new int[id];
        visits[nodeNames.get("start")] = 3;
        System.out.println("Part 1: " + find(nodeNames.get("start"), visits, 1, 0));
        System.out.println("Part 2: " + find(nodeNames.get("start"), visits, 2, 0));
    }

    private int find(int node, int[] visits, int threshold, int currLimit) {
        if (currLimit >= 2) {
            return 0;
        }
        if (nodeIds.get(node).equals("end")) {
            return 1;
        }
        int count = 0;
        for (int currNode = 0; currNode < map[node].length; currNode++) {
            if (map[node][currNode] == 0) {
                continue;
            }
            if (nodeTypes.get(currNode) && visits[currNode] < threshold) {
                int tmp = visits[currNode];
                visits[currNode]++;
                count += find(currNode, visits, threshold, currLimit + (visits[currNode] == 2 ? 1 : 0));
                visits[currNode] = tmp;
            }
            if (!nodeTypes.get(currNode)) {
                count += find(currNode, visits, threshold, currLimit);
            }
        }
        return count;
    }
}