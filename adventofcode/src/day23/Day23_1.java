package day23;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Day23_1 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        Day23_1 day23 = new Day23_1();
        System.out.println("Part 1: " + day23.part1(list));
    }

    int[][] dirs = new int[][] {{1,0}, {0,1}, {-1,0}, {0,-1}};
    int[] phase1Pos = new int[] {14,15,17,19,21,23,24};
    //int[] phase1Pos = new int[] {14,15,16,17,18,19,20,21,22,23,24};
    int[][] phase2Pos = new int[][] {{42,29},{44,31},{46,33},{48,35}};
    int[] playerCosts = new int[] {1,1,10,10,100,100,1000,1000};
    //int[] allowedToMove = new int[] {0,1,1,1,1,0,1,1};
    int[] allowedToMove = new int[] {1,1,1,1,1,1,1,1};
    char[] playerNames = new char[] {'A','A','B','B','C','C','D','D'};
    int width = 13;
    int height = 5;
    
    long part1(ArrayList<String> list) {
    	
    	String mapStr = "#############"
    			      + "#...........#"
    			      + "###B#C#B#D###"
    	              + "###A#D#C#A###"
    	              + "#############";
    
    	mapStr = "#############"
			   + "#...........#"
			   + "###A#D#C#A###"
	           + "###C#D#B#B###"
	           + "#############";
    	
    	String targetMapStr = "#############"
 			   			    + "#...........#"
 			                + "###A#B#C#D###"
 	                        + "###A#B#C#D###"
 	                        + "#############";
    	
    	
    	char[] map = mapStr.toCharArray();
    	//int[] playerPhases = new int[] {2,0,0,0,0,2,0,0};
    	int[] playerPhases = new int[] {0,0,0,0,0,0,0,0};
    	int[] playerPoints = new int[8];
    	//int[] playerPos = new int[] {42,48,29,33,31,46,35,44};
    	int[] playerPos = new int[] {29,35,46,48,33,42,31,44};
    	
    	return generateStates(playerPos, playerPhases, playerPoints, map, targetMapStr);
    }
    int min = Integer.MAX_VALUE;
    HashMap<String, Integer> memo = new HashMap<>();
    		
    int generateStates(int[] playerPos, 
    		int[] playerPhases, 
    		int[] playerPoints, 
    		char[] map, String targetMap) {

    	int sumPhases = 0;
    	for (int phase : playerPhases) {
    		sumPhases += phase;
    	}
    	if (sumPhases == 16) {
    		String mapStr = new String(map);
    		boolean isEqual = mapStr.equals(targetMap);
    		int total = 0;
    		for (int points : playerPoints) {
    			total += points; 
    		}
    		if (isEqual) {
    			min = Math.min(min, total);
    		}
    		return isEqual ? total : Integer.MAX_VALUE;
    	}
    	
    	int player = 0;
    	int myMin = Integer.MAX_VALUE;
    	String key = "";
    	for (int pos : playerPos) key += " " + pos;
    	for (int ph : playerPhases) key += " " + ph;
    	for (int poi : playerPoints) key += " " + poi;
    	
    	if (memo.containsKey(key)) {
    		return memo.get(key);
    	}
    	for (int from : playerPos) {
    		
    		if (playerPhases[player] == 0 && allowedToMove[player] == 1) {
    			for (int to : phase1Pos) {
    				
    				int[] res = bfs(from, to, map, player);
    				if (res[0] == 1) {
    					
    					int tmpPos = playerPos[player];
    					int tmpPhase = playerPhases[player];
    					int tmpPoint = playerPoints[player];
    					char tmpMap = map[from];
    					
    					playerPos[player] = to;
    					playerPhases[player]++;
    					playerPoints[player] += res[1];
    					map[from] = '.';
    					map[to] = tmpMap;
    					myMin = Math.min(myMin, generateStates(playerPos, playerPhases, playerPoints, map, targetMap));
    					playerPos[player] = tmpPos;
    					playerPhases[player] = tmpPhase;
    					playerPoints[player] = tmpPoint;
    					map[from] = tmpMap;
    					map[to] = '.';
    				}
    				
    			}
    		}
    		
    		if (playerPhases[player] == 1 && allowedToMove[player] == 1) {
    			int[] tos = phase2Pos[player / 2];
    			int to = -1;
    			if (map[tos[0]] == '.' && map[tos[1]] == '.') {
    				to = tos[0];
    			} else if (map[tos[0]] == playerNames[player] && map[tos[1]] == '.') {
    				to = tos[1];
    			}
    			if (to >= 0) {
					int[] res = bfs(from, to, map, player);
					if (res[0] == 1) {
						
						int tmpPos = playerPos[player];
						int tmpPhase = playerPhases[player];
						int tmpPoint = playerPoints[player];
						char tmpMap = map[from];
						
						playerPos[player] = to;
						playerPhases[player]++;
						playerPoints[player] += res[1];
						map[from] = '.';
						map[to] = tmpMap;
						myMin = Math.min(myMin, generateStates(playerPos, playerPhases, playerPoints, map, targetMap));
						playerPos[player] = tmpPos;
						playerPhases[player] = tmpPhase;
						playerPoints[player] = tmpPoint;
						map[from] = tmpMap;
						map[to] = '.';
					}
    			}
    		}
    		
    		player++;
    	}
    	memo.put(key, myMin);
    	return myMin;
    }
    
    int[] bfs(int from, int to, char[] map, int player) {
    	int success = 0;
    	int points = 0;
    	if (from == to || map[to] != '.') {
    		return new int[] {success, points};
    	}
    	Queue<Integer> queue = new LinkedList<>();
    	HashSet<Integer> visited = new HashSet<>();
    	queue.add(from);
    	visited.add(from);
    	int[] pointSums = new int[width * height];
    	while (!queue.isEmpty()) {
    		int node = queue.remove();
    		int x = node % width;
			int y = node / width;
    		for (int[] dir : dirs) {
    			int newX = x + dir[0];
    			int newY = y + dir[1];
    			if (newX >= 0 && newX < width && newY >= 0 && newY < height
    					&& !visited.contains(newY * width + newX) 
    					&& map[newY * width + newX] == '.') {
    				visited.add(newY * width + newX);
    				queue.add(newY * width + newX);
    				pointSums[newY * width + newX] = pointSums[node] + playerCosts[player];
    				if (to == newY * width + newX) {
    					success = 1;
    					points = pointSums[newY * width + newX]; 
    					break;
    				}
    			}
    		}
    	}
    	return new int[] {success, points};
    }
}