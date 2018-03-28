import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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

	// Data structure used to store the graph
    private Map<Integer, ArrayList<Edge>> myGraph = new HashMap<>();


	// This function is the constructor of the Graph
	public Graph(int x) {
		n = x;
	}
    
	// This function is called by Driver. The input is an edge in the graph.
	// Your job is to fix this function to generate your graph.
	// Do not change its parameters or return type.
	// Hint: Here is the place to build the graph with the data structure you defined.
	public void inputEdge(int port1, int port2, int time, int capacity) {
	    // Add an edge to port1's adjacency list
	    ArrayList<Edge> adjacencyList;
	    if(myGraph.containsKey(port1)) {
	        adjacencyList = myGraph.get(port1);
        }
        else {
	        adjacencyList = new ArrayList<>();
        }
	    Edge newEdge = new Edge(port2, time, capacity);
	    adjacencyList.add(newEdge);
	    myGraph.put(port1, adjacencyList);
	    // Add an edge to port2's adjacency list
	    if(myGraph.containsKey(port2)) {
	        adjacencyList = myGraph.get(port2);
        }
        else {
	        adjacencyList = new ArrayList<>();
        }
        newEdge = new Edge(port1, time, capacity);
	    adjacencyList.add(newEdge);
	    myGraph.put(port2, adjacencyList);
	}

	// This function is the solution for the Shortest Path problem.
	// The output of this function is an int which is the shortest travel time from source port to destination port
	// Do not change its parameters or return type.
	public int findTimeOptimalPath(int sourcePort, int destPort) {
        ArrayList<Integer> distances = new ArrayList<>();
        ArrayList<Integer> Q = new ArrayList<>();
        // Initialize distances for every port to infinity and add all ports to which the shortest path is not known to Q (initially all of them)
        for(int i = 0; i < n; i++) {
            distances.add(Integer.MAX_VALUE);
	        Q.add(i);
        }
        // Set the distance between the source and itself to 0
        distances.set(sourcePort, 0);
        while(!Q.isEmpty()) {
            // Find the port still in Q with the smallest distance from the source
            int nearestPort = findMinDistance(Q, distances);
            // End if the graph becomes unconnected
            if(distances.get(nearestPort) == Integer.MAX_VALUE) {
                break;
            }
            // Set the distance of the nearestPort by removing it from Q
            Q.remove(Q.indexOf(nearestPort));
            // End if set distance for destination
            if(nearestPort == destPort) {
                break;
            }
            // Update the distances for every port in Q and in the nearestPort's adjacency list, if needed
            ArrayList<Edge> adjacencyList = myGraph.get(nearestPort);
            for(Edge e : adjacencyList) {
                if(Q.contains(e.node)) {
                    // Calculate distance to the ports in nearestPort's adjacency list from the nearestPort and update the ports' distances in distances
                    int alt = distances.get(nearestPort) + e.time;
                    if(alt < distances.get(e.node)) {
                        distances.set(e.node, alt);
                    }
                }
            }
        }
        // Return the time of the shortest path from source to destination
        return distances.get(destPort);
	}

    // Find the port still in Q with the smallest distance from the source
    private int findMinDistance(ArrayList<Integer> Q, ArrayList<Integer> distances) {
	    int nearestPort = Integer.MAX_VALUE;
	    int minDistance = Integer.MAX_VALUE;
	    for(int i = 0; i < Q.size(); i++) {
	        int currentPort = Q.get(i);
	        // Update nearestPort if currentPort is close to the source
	        if(distances.get(currentPort) < minDistance) {
	            nearestPort = currentPort;
	            minDistance = distances.get(currentPort);
            }
        }
        return nearestPort;
    }

    // This function is the solution for the Widest Path problem.
    // The output of this function is an int which is the maximum capacity from source port to destination port
    // Do not change its parameters or return type.
    public int findCapOptimalPath(int sourcePort, int destPort) {
        ArrayList<Integer> capacities = new ArrayList<>();
        ArrayList<Integer> Q = new ArrayList<>();
        // Initialize capacities for every port to 0 and add all ports to which the widest path is not known to Q (initially all of them)
        for(int i = 0; i < n; i++) {
            capacities.add(0);
            Q.add(i);
        }
        // Set the capacity from the source to the source to infinity
        capacities.set(sourcePort, Integer.MAX_VALUE);
        while(!Q.isEmpty()) {
            // Find the port with the greatest bottleneck capacity on a path from the source
            int widestPort = findMaxCapacity(Q, capacities);
            // If the graph is not connected, break
            if(capacities.get(widestPort) == 0) {
                break;
            }
            // Remove the current widestPort from the list of ports for which we do not know the maximum bottleneck capacity
            Q.remove(Q.indexOf(widestPort));
            // If the widestPort is the destination, we are done
            if(widestPort == destPort) {
                break;
            }
            // Update the capacities for every port in Q and in the widestPort's adjacency list, if needed
            ArrayList<Edge> adjacencyList = myGraph.get(widestPort);
            for(Edge e : adjacencyList) {
                if(Q.contains(e.node)) {
                    // Find the bottleneck capacity of the widestPort
                    int minCapacity = capacities.get(widestPort);
                    // Choose the minimum of widestPort's capacity and the edge's capacity to be the bottleneck
                    if (e.capacity <= minCapacity) {
                        minCapacity = e.capacity;
                    }
                    // Update the capacity associated with the other end of the edge, if minCapacity is a larger bottleneck
                    if(minCapacity > capacities.get(e.node)) {
                        capacities.set(e.node, minCapacity);
                    }
                }
            }
        }
        // Return the maximum bottleneck capacity of the paths from source to destination
        return capacities.get(destPort);
    }

    // Find the port with the greatest bottleneck capacity in Q
    private int findMaxCapacity(ArrayList<Integer> Q, ArrayList<Integer> capacities) {
        int widestPort = Integer.MAX_VALUE;
        int maxCapacity = 0;
        for(int i = 0; i < Q.size(); i++) {
            int currentPort = Q.get(i);
            // Update widestPort as wider paths are found
            if(capacities.get(currentPort) > maxCapacity) {
                widestPort = currentPort;
                maxCapacity = capacities.get(currentPort);
            }
        }
        return widestPort;
    }

	// This function returns the neighboring ports of node.
	// This function is used to test if you have constructed the graph correctly.
	public ArrayList<Integer> getNeighbors(int node) {
		ArrayList<Integer> edges = new ArrayList<Integer>();
        ArrayList<Edge> adjacencyList = myGraph.get(node);
        for(Edge e : adjacencyList) {
		    edges.add(e.node);
        }
		return edges;
	}

	// Returns the number of ports in a graph
	public int getNumPorts() {
		return n;
	}
}