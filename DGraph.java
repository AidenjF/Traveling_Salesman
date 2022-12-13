/**
 * file: DGraph.java
 * class: CSC210
 * instructor: David Claveau
 * author: Aiden Foster
 * purpose: The purpose of this file is to hold the methods that allow us to traverse the graph we made
 */
import java.util.*;

public class DGraph {
	
	/**
	 * This is the constructor that allows us to create a hold vertices and access their info
	 */
	private class Edge {
		int label;
		double weight;
		Edge(int v, double w) {
			label = v;
			weight = w;
		}
	}
	
	private ArrayList<LinkedList<Edge>> adjList = new ArrayList<>();
	private int numVertices;
	
	/**
	 * This is the constructor that creates a directed graph for us to use in the traversal methods
	 * @param numVertices is the number of vertices that we will have in this graph
	 */
	DGraph(int numVertices) {
		this.numVertices = numVertices;
		adjList.add(null);
		for (int i = 1; i < numVertices+1; i++)
			adjList.add(new LinkedList<Edge>());
	}
	
	/**
	 * This method adds the edge to our graph
	 * @param u the vertex
	 * @param v the vertex it points to
	 * @param w the weight of the arc to the other vertex
	 */
	void addEdge(int u, int v, double w) {
		adjList.get(u).add(new Edge(v,w));
	}
	
	/**
	 * This is the method that finds the best route using a Heuristic approach
	 * @param start is the method we are starting on 
	 * @param path is the current path of nodes we have taken
	 * @return this returns the cost of the path we have found
	 */
	public double tspHeuristic(int start, List<Integer> path) {
		boolean[] visited = new boolean[numVertices];
		LinkedList<Integer> queue = new LinkedList<>();
		queue.add(start);
		visited[start]=true;
		double tspCost = 0.0;
		// while the queue still has elements we keep moving
		while (queue.size() != 0) {
			int u = queue.pollFirst();
			path.add(u);
			double minEdgeWeight = Double.MAX_VALUE;
			int minVertex = -1;
			for (int i = 0; i < adjList.get(u).size(); i++){
				// if this edge is less and not already visited we want this one instead
				if(adjList.get(u).get(i).weight < minEdgeWeight && !path.contains(adjList.get(u).get(i).label)) {
					minEdgeWeight = adjList.get(u).get(i).weight;
					minVertex = adjList.get(u).get(i).label;
				}
				if(path.size() == numVertices && adjList.get(u).get(i).label == 1) {
					tspCost += adjList.get(u).get(i).weight;
				}
			}
			if (minVertex != -1) {
				tspCost += minEdgeWeight;
				queue.add(minVertex);
			}
		}
		System.out.println("cost = " + String.format("%.1f",tspCost) + ", visitOrder = " + path);
	return tspCost;
	}
	
	/**
	 * This is the method that initiates the backtrack traversal to find a route
	 * @param start is the vertex we will be starting on 
	 * @param path is the current path of vertexes we have taken
	 * @return we will return the cost of the minimum path
	 */
	public double tspBacktracking(int start, List<Integer> path) { 
		path.add(start);
		boolean[] visited = new boolean[numVertices];
		List<Integer> minimumPath = new ArrayList<>();
		double minCost = tspBacktracking(start, path, visited, 0, minimumPath, Double.MAX_VALUE);
		System.out.println("cost = " + String.format("%.1f", minCost) + ", visitOrder = " + minimumPath);
		return minCost;
	}
	
	/**
	 * This is the recursive method for the backtracking traversal that allows us to find the best path
	 * @param start is the vertex we will be starting on
	 * @param path is the current path of vertexes we have taken
	 * @param visited is a list of whether or not we have visited certain vertexes
	 * @param currCost is the current cost of the path we are on
	 * @param minimumPath is the path that is the smallest so far
	 * @param minCost is the cost of the path that is the smallest so far
	 * @return we will return the cost of the minimum path
	 */
	double tspBacktracking(int start, List<Integer> path, boolean[] visited, double currCost, List<Integer> minimumPath, double minCost) {
		visited[start-1] = true;
		boolean all = true;
		for(boolean e: visited) {
			if(e == false)
				all = false;
		}
		// if all the nodes are visited then we need to see if there is a path back to the start
		if(all) {
			for(int i=0; i < adjList.get(start).size(); i++) {
				if(adjList.get(start).get(i).label == 1) {
					currCost += adjList.get(start).get(i).weight;
					break;
				}
			}			
			// if this path is less costly then change this new one to the new minimum
			if(currCost < minCost) {
				minCost = currCost;
				minimumPath.clear();
				minimumPath.addAll(path);
				return minCost;
			}
		}
		else {
			for(int i = 0; i < adjList.get(start).size(); i++) {
				int v = adjList.get(start).get(i).label;
				double w = adjList.get(start).get(i).weight;
				// as we go through the adjList check to see if we have visited that vertex yet
				if(!visited[v-1]) {
					path.add(v);
					currCost += w;
					minCost = tspBacktracking(v,path,visited,currCost,minimumPath,minCost);
					path.remove(path.size()-1);
					currCost-= w;
					visited[v-1] = false;
				}
			}
		}
		return minCost;
	}	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * This is the method that initiates the backtrack traversal to find a route
	 * @param start is the vertex we will be starting on 
	 * @param path is the current path of vertexes we have taken
	 * @return we will return the cost of the minimum path
	 */
	public double mine(int start, List<Integer> path) { 
		path.add(start);
		boolean[] visited = new boolean[numVertices];
		List<Integer> minimumPath = new ArrayList<>();
		double minCost = mine(start, path, visited, 0, minimumPath, Double.MAX_VALUE);
		//System.out.println("cost = " + String.format("%.1f", minCost) + ", visitOrder = " + minimumPath);
		return minCost;
	}
	
	/**
	 * This is the recursive method for the backtracking traversal that allows us to find the best path
	 * @param start is the vertex we will be starting on
	 * @param path is the current path of vertexes we have taken
	 * @param visited is a list of whether or not we have visited certain vertexes
	 * @param currCost is the current cost of the path we are on
	 * @param minimumPath is the path that is the smallest so far
	 * @param minCost is the cost of the path that is the smallest so far
	 * @return we will return the cost of the minimum path
	 */
	double mine(int start, List<Integer> path, boolean[] visited, double currCost, List<Integer> minimumPath, double minCost) {
		visited[start-1] = true;
		boolean all = true;
		for(boolean e: visited) {
			if(e == false)
				all = false;
		}
		// this allows us to prune all of the test cases and see if we should continue or stop early
		if(currCost > minCost) {
			return minCost;
		}
		// if all the nodes are visited then we need to see if there is a path back to the start
		if(all) {
			for(int i=0; i < adjList.get(start).size(); i++) {
				if(adjList.get(start).get(i).label == 1) {
					currCost += adjList.get(start).get(i).weight;
					break;
				}
			}
			// if this path is less costly then change this new one to the new minimum
			if(currCost < minCost) {
				minCost = currCost;
				minimumPath.clear();
				minimumPath.addAll(path);
				return minCost;
			}
		}
		else {
			for(int i = 0; i < adjList.get(start).size(); i++) {
				int v = adjList.get(start).get(i).label;
				double w = adjList.get(start).get(i).weight;
				// as we go through the adjList check to see if we have visited that vertex yet
				if(!visited[v-1]) {
					path.add(v);
					currCost += w;
					minCost = tspBacktracking(v,path,visited,currCost,minimumPath,minCost);
					path.remove(path.size()-1);
					currCost-= w;
					visited[v-1] = false;
				}
			}
		}
		return minCost;
	}
}
