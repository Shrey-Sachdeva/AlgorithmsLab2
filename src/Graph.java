import java.util.ArrayList;
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

	// Here you have to define your own data structure that you want to use
	// to represent the graph
	// Hint: This include an ArrayList or many ArrayLists?
	// ....
	//ArrayList<ArrayList<Edge>> graph = new ArrayList<>();
    private Map<Integer, ArrayList<Edge>> myGraph = new HashMap<>();


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
		// Index into overall ArrayList is the port number, Second ArrayList contains distance at index 0 and previous port at index 1
	    ArrayList<ArrayList<Integer>> distances = new ArrayList<>();
        ArrayList<Integer> Q = new ArrayList<>();
        for(int i = 0; i < n; i++) {
	        ArrayList<Integer> distance = new ArrayList<>();
	        distance.add(Integer.MAX_VALUE);
            distance.add(Integer.MAX_VALUE);
            distances.add(distance);
            /*distances.get(i).add(Integer.MAX_VALUE);
	        distances.get(i).add(Integer.MAX_VALUE);*/
	        Q.add(i);
        }
        distances.get(sourcePort).set(0, 0);
        while(!Q.isEmpty()) {
            int nearestPort = findMinDistance(Q, distances);
            if(distances.get(nearestPort).get(0) == Integer.MAX_VALUE) {
                break;
            }
            Q.remove(Q.indexOf(nearestPort));
            if(nearestPort == destPort) {
                break;
            }
            //ArrayList<Edge> adjacencyList = graph.get(nearestPort);
            ArrayList<Edge> adjacencyList = myGraph.get(nearestPort);
            for(Edge e : adjacencyList) {
                if(Q.contains(e.node)) {
                    int alt = distances.get(nearestPort).get(0) + e.time;
                    if(alt < distances.get(e.node).get(0)) {
                        distances.get(e.node).set(0, alt);
                        distances.get(e.node).set(1, nearestPort);
                    }
                }
            }
        }
	    return distances.get(destPort).get(0);
	}

	private int findMinDistance(ArrayList<Integer> Q, ArrayList<ArrayList<Integer>> distances) {
	    int nearestPort = Integer.MAX_VALUE;
	    int minDistance = Integer.MAX_VALUE;
	    for(int i = 0; i < Q.size(); i++) {
	        int currentPort = Q.get(i);
	        if(distances.get(currentPort).get(0) < minDistance) {
	            nearestPort = currentPort;
	            minDistance = distances.get(currentPort).get(0);
            }
        }
        return nearestPort;
    }

	// This function is the solution for the Widest Path problem.
	// The output of this function is an int which is the maximum capacity from source port to destination port 
	// Do not change its parameters or return type.
	public int findCapOptimalPath(int sourcePort, int destPort) {
		// Index into overall ArrayList is the port number, Second ArrayList contains capacity at index 0 and previous port at index 1
		int minCapacity = Integer.MAX_VALUE;
		ArrayList<ArrayList<Integer>> capacities = new ArrayList<>();
		ArrayList<Integer> Q = new ArrayList<>();
		for(int i = 0; i < n; i++) {
			ArrayList<Integer> distance = new ArrayList<>();
			distance.add(Integer.MAX_VALUE);
			distance.add(Integer.MAX_VALUE);
			capacities.add(distance);
            /*distances.get(i).add(Integer.MAX_VALUE);
	        distances.get(i).add(Integer.MAX_VALUE);*/
			Q.add(i);
		}
		capacities.get(sourcePort).set(0, 0);
		while(!Q.isEmpty()) {
			int widestPort = findMaxCapacity(Q, capacities);
			if(capacities.get(widestPort).get(0) == Integer.MAX_VALUE) {
				break;
			}
			Q.remove(Q.indexOf(widestPort));
			if(widestPort == destPort) {
				break;
			}
			//ArrayList<Edge> adjacencyList = graph.get(nearestPort);
			ArrayList<Edge> adjacencyList = myGraph.get(widestPort);
			for(Edge e : adjacencyList) {
				if(Q.contains(e.node)) {
				    double alt = 0;
					if(capacities.get(widestPort).get(0) != 0) {
                        alt = (1 / capacities.get(widestPort).get(0)) + (1 / e.capacity);
                    }
                    else {
					    alt = (double) 1 / e.capacity;
                    }
					if((capacities.get(e.node).get(0) == Integer.MAX_VALUE) || (alt < (1 / capacities.get(e.node).get(0)))) {
					    //capacity += e.capacity;
                        //int capacity = capacities.get(widestPort).get(0) + e.capacity;
                        int capacity = e.capacity;
                        if((capacities.get(widestPort).get(0) != 0) && (capacity > capacities.get(widestPort).get(0))) {
                            capacity = capacities.get(widestPort).get(0);
                        }
						capacities.get(e.node).set(0, capacity);
						capacities.get(e.node).set(1, widestPort);
						if(capacities.get(e.node).get(0) < minCapacity) {
						    minCapacity = capacities.get(e.node).get(0);
                        }
					}
				}
			}
		}
		//return capacities.get(destPort).get(0);
        return minCapacity;
	}

    private int findMaxCapacity(ArrayList<Integer> Q, ArrayList<ArrayList<Integer>> capacities) {
        int widestPort = Q.get(0);
        int maxCapacity = capacities.get(widestPort).get(0);
        for(int i = 0; i < Q.size(); i++) {
            int currentPort = Q.get(i);
            int currentCapacity = capacities.get(currentPort).get(0);
            if((currentCapacity != Integer.MAX_VALUE) && (currentCapacity > maxCapacity)) {
                widestPort = currentPort;
                maxCapacity = capacities.get(currentPort).get(0);
            }
        }
        return widestPort;
    }

	// This function returns the neighboring ports of node.
	// This function is used to test if you have constructed the graph correct.
	public ArrayList<Integer> getNeighbors(int node) {
		ArrayList<Integer> edges = new ArrayList<Integer>();
		//ArrayList<Edge> adjacencyList = graph.get(node);
        ArrayList<Edge> adjacencyList = myGraph.get(node);
        for(Edge e : adjacencyList) {
		    edges.add(e.node);
        }
		return edges;
	}

	public int getNumPorts() {
		return n;
	}
}
