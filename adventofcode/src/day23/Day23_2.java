package day23;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Day23_2 {

    public static void main(String[] args) throws IOException {
        Day23_2 day23 = new Day23_2();
        System.out.println("Part 2: " + day23.process());
    }

    int[][] dirs = new int[][] {{1,0}, {0,1}, {-1,0}, {0,-1}};
    int[] phase1Pos = new int[] {14,15,17,19,21,23,24};
    int[][] phase2Pos = new int[][] {{68,55,42,29},{70,57,44,31},{72,59,46,33},{74,61,48,35}};
    int[] playerCosts = new int[] {1,1,1,1,10,10,10,10,100,100,100,100,1000,1000,1000,1000};
    char[] playerNames = new char[] {'A','A','A','A','B','B','B','B','C','C','C','C','D','D','D','D'};
    int width = 13;
    int height = 7;
    
    int process() {
    
    	String mapStr = "#############"
    				  + "#...........#"
    				  + "###A#D#C#A###"
    				  + "###D#C#B#A###"
    				  + "###D#B#A#C###"
    				  + "###C#D#B#B###"
    				  + "#############";
    	
    	String targetMapStr = "#############"
 			   			    + "#...........#"
 			                + "###A#B#C#D###"
 			                + "###A#B#C#D###"
 			                + "###A#B#C#D###"
 	                        + "###A#B#C#D###"
 	                        + "#############";
    	
    	
    	char[] map = mapStr.toCharArray();
    	int[] playerPhases = new int[16];
    	int[] playerPoints = new int[16];
    	int[] playerPos = new int[] {29,35,48,59, 46,57,72,74, 33,44,61,68, 31,42,55,70};
    	
    	return generateStates(playerPos, playerPhases, playerPoints, map, targetMapStr);
    }
    
    
    HashMap<String, Integer> memo = new HashMap<>();		
    int generateStates(int[] playerPos, 
    		int[] playerPhases, 
    		int[] playerPoints, 
    		char[] map, String targetMap) {

    	int sumPhases = 0;
    	for (int phase : playerPhases) {
    		sumPhases += phase;
    	}
    	if (sumPhases == 32) {
    		String mapStr = new String(map);
    		boolean isEqual = mapStr.equals(targetMap);
    		int total = 0;
    		for (int points : playerPoints) {
    			total += points; 
    		}
    		return isEqual ? total : Integer.MAX_VALUE;
    	}
    	
    	int player = 0;
    	int myMin = Integer.MAX_VALUE;
    	String key = "";
    	for (int tmpPos : playerPos) {
    		key += " " + tmpPos;
    	}
    	for (int tmpPhase : playerPhases) {
    		key += " " + tmpPhase;
    	}
    	for (int tmpPoint : playerPoints) {
    		key += " " + tmpPoint;
    	}
    	
    	if (memo.containsKey(key)) {
    		return memo.get(key);
    	}
    	for (int from : playerPos) {
    		
    		if (playerPhases[player] == 0) {
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
    		
    		if (playerPhases[player] == 1) {
    			int[] tos = phase2Pos[player / 4];
    			int to = -1;
    			if (map[tos[0]] == '.' && map[tos[1]] == '.' 
    					&& map[tos[2]] == '.' && map[tos[3]] == '.') {
    				to = tos[0];
    			} else if (map[tos[0]] == playerNames[player] 
    					&& map[tos[1]] == '.' && map[tos[2]] == '.' && map[tos[3]] == '.') {
    				to = tos[1];
    			} else if (map[tos[0]] == playerNames[player] 
    					&& map[tos[1]] == playerNames[player]  && map[tos[2]] == '.' && map[tos[3]] == '.') {
    				to = tos[2];
    			} else if (map[tos[0]] == playerNames[player] 
    					&& map[tos[1]] == playerNames[player]  && map[tos[2]] == playerNames[player] && map[tos[3]] == '.') {
    				to = tos[3];
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