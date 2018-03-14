import java.util.ArrayList;
/*
 * Name: Shrey Sachdeva
 * EID: ss77335
 */

public class Graph implements Program2{
	// n is the number of ports
	private int n;
    
	// Edge is the class to represent an edge between two nodes
	// node is the destination node this edge connected to
	// time is the travel time of this edge
	// capacity is the capacity of this edge
	// Use of this class is optional. You may make your own, and comment
	// this one out.
	private class Edge{
		public int node;
		public int time;
		public int capacity;
		public Edge(int n, int t, int c){
			node = n;
			time = t;
			capacity = c;
		}

		// prints out an Edge.
		public String toString() {
			return "" + node;
		}
	}

	// Here you have to define your own data structure that you want to use
	// to represent the graph
	// Hint: This include an ArrayList or many ArrayLists?
	// ....
	ArrayList<ArrayList<Edge>> graph = new ArrayList<>();


	// This function is the constructor of the Graph. Do not change the parameters
	// of this function.
	//Hint: Do you need other functions here?
	public Graph(int x) {
		n = x;
	}
    
	// This function is called by Driver. The input is an edge in the graph.
	// Your job is to fix this function to generate your graph.
	// Do not change its parameters or return type.
	// Hint: Here is the place to build the graph with the data structure you defined.
	public void inputEdge(int port1, int port2, int time, int capacity) {
		ArrayList<Edge> adjacencyList = graph.get(port1);
		Edge newEdge = new Edge(port2, time, capacity);
		adjacencyList.add(newEdge);
		graph.set(port1, adjacencyList);
        adjacencyList = graph.get(port2);
        newEdge = new Edge(port1, time, capacity);
        adjacencyList.add(newEdge);
        graph.set(port2, adjacencyList);
	}

	// This function is the solution for the Shortest Path problem.
	// The output of this function is an int which is the shortest travel time from source port to destination port
	// Do not change its parameters or return type.
	public int findTimeOptimalPath(int sourcePort, int destPort) {
		ArrayList<Edge> adjacencyList = graph.get(sourcePort);
		A
	    return -1;
	}

	// This function is the solution for the Widest Path problem.
	// The output of this function is an int which is the maximum capacity from source port to destination port 
	// Do not change its parameters or return type.
	public int findCapOptimalPath(int sourcePort, int destPort) {
		return -1;
	}

	// This function returns the neighboring ports of node.
	// This function is used to test if you have constructed the graph correct.
	public ArrayList<Integer> getNeighbors(int node) {
		ArrayList<Integer> edges = new ArrayList<Integer>();
		ArrayList<Edge> adjacencyList = graph.get(node);
		for(Edge e : adjacencyList) {
		    edges.add(e.node);
        }
		return edges;
	}

	public int getNumPorts() {
		return n;
	}
}
