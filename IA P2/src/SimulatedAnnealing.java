
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimulatedAnnealing {
	static String fileDir;
	static File file;
	static Scanner scanner;
	static List<String> cities = new ArrayList<String>();
	static int[][] matrix;

	public static void main(String[] args) throws IOException {
		switch (args.length) {
		case 2:
			fileDir = "/home/alvaro/eclipse-workspace/IA P2/" + args[0];
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
			// Lê o percurso
			fileDir = "/home/alvaro/eclipse-workspace/IA P2/" + args[1];
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

			// Calcular custo

			System.out.println(distance(path));
			break;
		default:
			System.out.printf("filename");
			break;
		}
	}

	public static int distance(int[] path) {
		int cost = 0;
		for (int i = 0; i < path.length - 1; i++) {
			cost += matrix[path[i]][path[i + 1]];
		}
		return cost;
	}

}
