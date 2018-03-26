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
        ArrayList<ArrayList<Integer>> capacities = new ArrayList<>();
        ArrayList<Integer> Q = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            ArrayList<Integer> capacity = new ArrayList<>();
            capacity.add(0);
            capacity.add(Integer.MAX_VALUE);
            capacities.add(capacity);
            Q.add(i);
        }
        capacities.get(sourcePort).set(0, Integer.MAX_VALUE);
        while(!Q.isEmpty()) {
            int bestPort = findMaxCapacity(Q, capacities);
            if(capacities.get(bestPort).get(0) == 0) {
                break;
            }
            Q.remove(Q.indexOf(bestPort));
            if(bestPort == destPort) {
                break;
            }
            ArrayList<Edge> adjacencyList = myGraph.get(bestPort);
            for(Edge e : adjacencyList) {
                if(Q.contains(e.node)) {
                    int minCapacity = capacities.get(bestPort).get(0);
                    if (e.capacity <= minCapacity) {
                        minCapacity = e.capacity;
                    }
                    if(minCapacity > capacities.get(e.node).get(0)) {
                        capacities.get(e.node).set(0, minCapacity);
                    }
                }
            }
        }
        return capacities.get(destPort).get(0);
    }

    private int findMaxCapacity(ArrayList<Integer> Q, ArrayList<ArrayList<Integer>> capacities) {
        int bestPort = Integer.MAX_VALUE;
        int maxCapacity = 0;
        for(int i = 0; i < Q.size(); i++) {
            int currentPort = Q.get(i);
            if(capacities.get(currentPort).get(0) > maxCapacity) {
                bestPort = currentPort;
                maxCapacity = capacities.get(currentPort).get(0);
            }
        }
        return bestPort;
    }
	/*// This function is the solution for the Widest Path problem.
	// The output of this function is an int which is the maximum capacity from source port to destination port 
	// Do not change its parameters or return type.
	public int findCapOptimalPath(int sourcePort, int destPort) {
        //System.out.println(System.currentTimeMillis());
        ArrayList<ArrayList<Integer>> capacities = new ArrayList<>();
	    for(int i = 0; i < n; i++) {
	        ArrayList<Integer> capacity = new ArrayList<>();
	        capacity.add(0);
	        capacity.add(Integer.MAX_VALUE);
	        capacities.add(capacity);
        }
        capacities.get(sourcePort).set(0, Integer.MAX_VALUE);
        ArrayList<Integer> portsKnown = new ArrayList<>();
        portsKnown.add(sourcePort);
        ArrayList<Edge> possibleNextEdge = new ArrayList<>();
        while(!portsKnown.contains(destPort)) {
	        /*ArrayList<Integer> updateInfo = findBestPath(portsKnown, capacities, possibleNextEdge);
	        capacities.get(updateInfo.get(0)).set(0, updateInfo.get(1));
	        capacities.get(updateInfo.get(0)).set(1, updateInfo.get(2));
	        int insertionIndex = 0;
	        while(updateInfo.get(1) < capacities.get(insertionIndex).get(0)) {
	            insertionIndex++;
            }
            portsKnown.add(insertionIndex, updateInfo.get(0));//
	        if(possibleNextEdge.size() == 0 || !portsKnown.contains(possibleNextEdge.get(0).node)) {
                possibleNextEdge = updatePossibilities(portsKnown, capacities, possibleNextEdge);
                Edge currentEdge = possibleNextEdge.remove(0);
                ArrayList<Integer> portsInfo = capacities.get(currentEdge.node);
                portsInfo.set(0, currentEdge.capacity);
                // This is not necessarily true
                portsInfo.set(1, portsKnown.get(portsKnown.size() - 1));
                capacities.set(currentEdge.node, portsInfo);
                portsKnown.add(currentEdge.node);
            }
            else {
	            possibleNextEdge.remove(0);
            }
        }
        int currentPort = destPort;
	    int minCapacityOfPath = Integer.MAX_VALUE;
	    while(currentPort != sourcePort) {
	        int currentCapacity = capacities.get(currentPort).get(0);
	        if(currentCapacity < minCapacityOfPath) {
	            minCapacityOfPath = currentCapacity;
            }
            currentPort = capacities.get(currentPort).get(1);
        }
        //System.out.println(System.currentTimeMillis());
        return minCapacityOfPath;
	}

    private ArrayList<Edge> updatePossibilities(ArrayList<Integer> portsKnown, ArrayList<ArrayList<Integer>> capacities, ArrayList<Edge> possibleNextEdge) {
        int currentPort = portsKnown.get(portsKnown.size() - 1);
        for(Edge e : myGraph.get(currentPort)) {
            if(!portsKnown.contains(e.node)) {
                int insertionIndex;
                if(possibleNextEdge.size() > 0) {
                     insertionIndex = add(e, possibleNextEdge);
                     possibleNextEdge.add(insertionIndex, e);
                }
                else {
                    possibleNextEdge.add(e);
                }
            }
        }
        return possibleNextEdge;
    }

    private int add(Edge e, ArrayList<Edge> possibleNextEdge) {
	    int max = possibleNextEdge.size() - 1;
	    int min = 0;
	    while(min < max && min != max) {
	        int mid = (min + max) / 2;
	        if(possibleNextEdge.get(mid).capacity < e.capacity) {
	            if(mid == 0 || possibleNextEdge.get(mid - 1).capacity > e.capacity) {
	                return mid;
                }
            }
            if(possibleNextEdge.get(mid).capacity > e.capacity) {
	            min = mid + 1;
            }
            else {
	            max = mid - 1;
            }
        }
        if(possibleNextEdge.get(min).capacity < e.capacity) {
            return min;
        }
        else {
	        return min + 1;
        }
	}

    private ArrayList<Integer> findBestPath(ArrayList<Integer> portsKnown, ArrayList<ArrayList<Integer>> capacities, ArrayList<Edge> possibleNextEdge) {
        // Return bestPort, bestCapacity, and parent of bestPort
	    ArrayList<Integer> returnInfo = new ArrayList<>();
	    int bestPort = Integer.MAX_VALUE;
	    int bestCapacity = 0;
	    returnInfo.add(bestPort);
	    returnInfo.add(bestCapacity);
	    returnInfo.add(bestPort);
	    for(Integer port : portsKnown) {
	        ArrayList<Edge> adjacencyList = myGraph.get(port);
	        for(Edge e : adjacencyList) {
	            if(!portsKnown.contains(e.node)) {
                    if (e.capacity > bestCapacity) {
                        bestCapacity = e.capacity;
                        bestPort = e.node;
                        returnInfo.set(0, bestPort);
                        returnInfo.set(1, bestCapacity);
                        returnInfo.set(2, port);
                    }
                }
            }
        }
        return returnInfo;
    }*/

	// This function returns the neighboring ports of node.
	// This function is used to test if you have constructed the graph correct.
	public ArrayList<Integer> getNeighbors(int node) {
		ArrayList<Integer> edges = new ArrayList<Integer>();
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