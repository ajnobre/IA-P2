
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SimulatedAnnealing {
	static String fileDir;
	static File file;
	static Scanner scanner;
	static List<String> cities = new ArrayList<String>();
//	static List<Integer> path = new ArrayList<Integer>();
	static int[][] matrix;

	public static void main(String[] args) throws IOException {
		switch (args.length) {
		case 2:
			fileDir = "/home/alvaro/git/repository/IA P2/" + args[0];
			file = new File(fileDir);
			scanner = new Scanner(file);
			String line = "";

			int lines = 0;
			while (scanner.hasNextLine()) {
				lines++;
				scanner.nextLine();
			}
			matrix = new int[lines][lines];

			scanner = new Scanner(file);
//			String[] cities = new String[lines];

			String[] tokens = null;

			makeMatrix();
			// Lê o percurso
			fileDir = "/home/alvaro/git/repository/IA P2/" + args[1];
			file = new File(fileDir);
			scanner = new Scanner(file);
			tokens = scanner.nextLine().split("\\s+");
			int path[] = new int[tokens.length];

			// Coloca indices das cidades num array correspondente ao percurso
			for (int i = 0; i < tokens.length; i++) {
				path[i] = cities.indexOf(tokens[i]);
				// System.out.println(path[i]);
			}
			int cost = 0;
			int lastCost = 0;

			for (int i = 0; i < 10; i++) {
				List<Integer> newpath = getNewPath(path, getIJ(path));
				System.out.println(distance2(newpath));

			}
			break;
		default:
			System.out.printf("filename");
			break;
		}
	}

	private static void makeMatrix() {
		String line;
		String[] tokens;
		for (int i = 0; scanner.hasNextLine(); i++) {
			line = scanner.nextLine();
			tokens = line.split("\\s+");

			if (i == 0) {
				cities.add(tokens[1]);
			} else {
				cities.add(tokens[0]);
				for (int j = 0; j + 1 < tokens.length - 1; j++) {
					matrix[i][j] = Integer.parseInt(tokens[j + 1]);
					matrix[j][i] = Integer.parseInt(tokens[j + 1]);
				}
			}
			matrix[i][i] = 0;
		}
	}

	public static int[] getIJ(int[] path) {
		Random rand = new Random();
		int[] ij = new int[2];

		int i = rand.nextInt(path.length);

		int[] neighboors = getNeighboors(path.length - 1, i);
		int j = rand.nextInt(path.length);

		while (j == i || j == (neighboors[0]) || j == (neighboors[1])) {
			j = rand.nextInt(path.length);
		}
		ij[0] = i;
		ij[1] = j;

		return ij;
	}

	private static int[] getNeighboors(int length, int i) {
		// neighboors[0] corresponde ao indice -1 e neighboors[1] corresponde a indice
		// +1. Indicies podem tomam os valores de i ou j.
		int[] neighboors = new int[2];
		if (i == length) {
			neighboors[0] = i - 1;
			neighboors[1] = 0;
		} else if (i == 0) {
			neighboors[0] = length;
			neighboors[1] = i + 1;
		} else {
			neighboors[0] = i + -1;
			neighboors[1] = i + 1;
		}
		return neighboors;
	}

//	public static int distance(int[] path) {
//		int cost = 0;
//		for (int i = 0; i < path.length - 1; i++) {
//			cost += matrix[path[i]][path[i + 1]];
//		}
//		// distancia do fim ao inicio
//		cost += matrix[path[0]][path[path.length - 1]];
//		return cost;
//	}

	public static int distance2(List<Integer> path) {
		int cost = 0;
		for (int i = 0; i < path.size() - 1; i++) {
			cost += matrix[path.get(i)][path.get(i + 1)];
		}
		// distancia do fim ao inicio
		cost += matrix[path.get(0)][path.get(path.size() - 1)];
		return cost;
	}

	public static List<Integer> getNewPath(int[] path, int[] ij) {
		int[] iNbrs = getNeighboors(path.length - 1, ij[0]);
		// int[] jNbrs = getNeighboors(path.length - 1, ij[1]);
		List<Integer> arrayA = new ArrayList<Integer>();
		List<Integer> arrayB = new ArrayList<Integer>();

		for (int j = ij[1]; j != iNbrs[1]; j = ((j + 1) % path.length)) {
			arrayA.add(path[j]);
		}
		for (int k = path.length - 1; k >= 0; k--) {
			if (!arrayA.contains(path[k])) {
				arrayB.add(path[k]);
			}
		}
		int initialA = arrayA.get(0);
		arrayA.remove(arrayA.get(0));
		arrayA.add(initialA);
		// System.out.println("i " + ij[0] + " j " + ij[1]);
		arrayA.addAll(arrayB);

//		return matrix[path[ij[0]]][path[ij[1]]] + matrix[path[iNbrs[1]]][path[jNbrs[1]]] - matrix[path[ij[0]]][iNbrs[1]]
//				- matrix[ij[1]][jNbrs[1]];
		return arrayA;
	}

}
