package day22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day22_2 {
	
	public static void main(String[] args) throws IOException {
		ArrayList<String> list = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader("input/day22/input2.txt"));
		String line = "";
		while ((line = reader.readLine()) != null) {
			list.add(line);
		}
		reader.close();
		Day22_2 day22 = new Day22_2();
		System.out.println("Part 2: " + day22.part2(list));
	}

	long part2(ArrayList<String> list) {
		ArrayList<Cuboid> cuboids = new ArrayList<>();
		for (String line : list) {
			boolean isOn = line.split(" ")[0].equals("on");
			String[] coords = line.split(" ")[1].split(",");
			String[] xData = coords[0].split("=")[1].split("\\.\\.");
			String[] yData = coords[1].split("=")[1].split("\\.\\.");
			String[] zData = coords[2].split("=")[1].split("\\.\\.");
			int xMin = Integer.valueOf(xData[0]);
			int xMax = Integer.valueOf(xData[1]);
			int yMin = Integer.valueOf(yData[0]);
			int yMax = Integer.valueOf(yData[1]);
			int zMin = Integer.valueOf(zData[0]);
			int zMax = Integer.valueOf(zData[1]);

			Cuboid cuboid = new Cuboid(xMin, xMax, yMin, yMax, zMin, zMax);
			if (cuboids.isEmpty()) {
				cuboids.add(cuboid);
			} else {
				
				ArrayList<Cuboid> tmpCuboids = new ArrayList<>();
				for (Cuboid tmpCuboid : cuboids) {
					if (tmpCuboid.intersect(cuboid)) {
						for (Cuboid splitted : tmpCuboid.split(cuboid)) {
							if (!splitted.intersect(cuboid)) {
								tmpCuboids.add(splitted);
							}
						}
					} else {
						tmpCuboids.add(tmpCuboid);
					}
				}
				if (isOn) {
					tmpCuboids.add(cuboid);
				}
				cuboids = tmpCuboids;
			}
		}
		long volume = 0;
		for (Cuboid cuboid : cuboids) {
			volume += cuboid.volume();
		}
		return volume;
	}

	public class Cuboid {
		
		int xMin, xMax;
		int yMin, yMax;
		int zMin, zMax;

		public Cuboid(int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {
			this.xMin = xMin;
			this.xMax = xMax;
			this.yMin = yMin;
			this.yMax = yMax;
			this.zMin = zMin;
			this.zMax = zMax;
		}

		public ArrayList<Cuboid> split(Cuboid cuboid) {
			// Intersection points
			int minX = Math.max(xMin, cuboid.xMin);
			int maxX = Math.min(xMax, cuboid.xMax);
			int minY = Math.max(yMin, cuboid.yMin);
			int maxY = Math.min(yMax, cuboid.yMax);
			int minZ = Math.max(zMin, cuboid.zMin);
			int maxZ = Math.min(zMax, cuboid.zMax);
			
			int[] xs = new int[] {this.xMin, minX - 1, minX, maxX, maxX + 1, this.xMax};
			int[] ys = new int[] {this.yMin, minY - 1, minY, maxY, maxY + 1, this.yMax};
			int[] zs = new int[] {this.zMin, minZ - 1, minZ, maxZ, maxZ + 1, this.zMax};
			
			ArrayList<Cuboid> cuboids = new ArrayList<>();
			for (int x = 0; x < 3; x++) {
				int xFrom = xs[x * 2], xTo = xs[x * 2 + 1];
				if (xTo - xFrom >= 0) {
					for (int y = 0; y < 3; y++) {
						int yFrom = ys[y * 2], yTo = ys[y * 2 + 1];
						if (yTo - yFrom >= 0) {
							for (int z = 0; z < 3; z++) {
								int zFrom = zs[z * 2], zTo = zs[z * 2 + 1];
								if (zTo - zFrom >= 0) {
									Cuboid tmp = new Cuboid(xFrom, xTo, yFrom, yTo, zFrom, zTo);
									cuboids.add(tmp);
								}
							}
						}
					}
				}
			}
			return cuboids;
		}

		public long volume() {
			long diffX = xMax - xMin + 1l;
			long diffY = yMax - yMin + 1l;
			long diffZ = zMax - zMin + 1l;
			return diffX * diffY * diffZ;
		}
		
		public boolean isInside(Cuboid cuboid) {
			return xMin >= cuboid.xMin && xMax <= cuboid.xMax &&
					yMin >= cuboid.yMin && yMax <= cuboid.yMax &&
					zMin >= cuboid.zMin && zMax <= cuboid.zMax;			
		}

		public boolean intersect(Cuboid cuboid) {
			return (xMin <= cuboid.xMax && xMax >= cuboid.xMin) && (yMin <= cuboid.yMax && yMax >= cuboid.yMin)
					&& (zMin <= cuboid.zMax && zMax >= cuboid.zMin);
		}
	}
}