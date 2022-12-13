/**
 * file: PA11Main.java
 * class: CSC210
 * instructor: David Claveau
 * author: Aiden Foster
 * purpose: The purpose of this file is to handle the reading of a file and the execution of commands in DGraph
 */
import java.io.*;
import java.util.*;

public class PA11Main {

	/**
	 * This is the main method that initiates everything from the command line
	 * @param args is the file name and commands
	 * @throws FileNotFoundException is the exception we throw when the file is not there or found
	 */
	public static void main(String[] args) throws FileNotFoundException {
		File fileName =  new File(args[0]);
		Scanner file = new Scanner(fileName);
		readFile(file, args);
	}

	/**
	 * This method reads the files and creates the directed graph
	 * @param file is the file we have opened to read
	 * @param commands is a list of the commands we need to execute
	 */
	public static void readFile(Scanner file, String[] commands) {
		String currentLine = file.nextLine();
		// goes through the beginning of the file and gets past all of the useless stuff
		while(file.hasNextLine() && currentLine.charAt(0) == '%') {
			currentLine = file.nextLine();
		}
		//
		String[] parameters = currentLine.split(" +");
		DGraph dg = new DGraph(Integer.parseInt(parameters[0]));
		addEdges(file, dg);
		runCommands(dg, commands);
	}
	
	/**
	 * 
	 * @param file is the file we have opened to read
	 * @param dg is the directed graph we have initiated
	 * @return we will return the directed graph we have made
	 */
	public static DGraph addEdges(Scanner file, DGraph dg) {
		while(file.hasNextLine()) {
			String currentLine = file.nextLine();
			String[] lineSplit = currentLine.split(" +");
			dg.addEdge(Integer.parseInt(lineSplit[0]), Integer.parseInt(lineSplit[1]), Double.parseDouble(lineSplit[2]));
		}
		return dg;
	}
	
	/**
	 * This is the method that goes through the commands and executes them all
	 * @param dg is the directed graph we have created
	 * @param commands is the list of commands we must complete
	 */
	public static void runCommands(DGraph dg, String[] commands) {
		List<Integer> path = new ArrayList<>();
		for(int i=1; i <= commands.length-1; i++) {
			if(commands[i].equals("HEURISTIC")) {
				dg.tspHeuristic(1, path);
			}
			else if(commands[i].equals("BACKTRACK")) {
				dg.tspBacktracking(i, path);
			}
			else if(commands[i].equals("TIME")){		// own method for going through
				timing(dg,commands);
			}
			else {
				dg.mine(1, path);
			}
		}
	}
	 /**
	  * This is the method that was used to collect the times
	  * @param dg is the directed graph we have created
	  * @param commands is the list of commands we must complete
	  */
	public static void timing(DGraph dg, String[] commands) {
		List<Integer> path = new ArrayList<>();
		long startTime = System.nanoTime();
		double tspCost = dg.tspHeuristic(1, path);
		long endTime = System.nanoTime();
		float duration = (endTime - startTime)/1000000.0f;
		System.out.println("heuristic: cost = " + tspCost + ", " + duration);
		
		startTime = System.nanoTime();
		tspCost = dg.tspBacktracking(1, path);
		endTime = System.nanoTime();
		duration = (endTime - startTime)/1000000.0f;
		System.out.println("backtrack: cost = " + tspCost + ", " + duration);
		
		startTime = System.nanoTime();
		tspCost = dg.mine(1, path);
		endTime = System.nanoTime();
		duration = (endTime - startTime)/1000000.0f;
		System.out.println("mine: cost = " + tspCost + ", " + duration);
	}

}
