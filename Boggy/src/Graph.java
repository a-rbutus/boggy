/**
 * 
 *  @author Natalia Cornwall
 * 
 */
import java.util.ArrayList;


/*
 * Implementation of a graph using an adjacency list
 * Nodes can have a (x,y) position and a String value
 */
public class Graph {
	
	public ArrayList<Node> nodes;
	
	public Graph() {
		super();
		nodes = new ArrayList<Node>();
	}
	
	public void addNode(Node node){
		nodes.add(node);
	}

	/* 
	 * Given a list of nodes with (x,y) positions, generates adjacent nodes for each node
	 * A node is adjacent if it is one away on the grid
	 * (i.e. 3 elements immediately above, left and right, and 3 elements immediately below)
	 */
	public void generateAdjacentNodes_GridPattern(){

		for (int i = 0; i < nodes.size(); i ++){
			Node u = nodes.get(i);
			for (int k = 0; k < nodes.size(); k ++){					
				Node v = nodes.get(k);	
				
				// it doesn't need an edge to itself
				if (i != k){
					int y_diff = Math.abs(u.getY()-v.getY());
					//we're in the row immediately above u, in u's row, or in the row immediately below u		
					if (y_diff == 1 || y_diff == 0){ 
						
						int x_diff = Math.abs(u.getX()-v.getX());
						//we're in the column immediately to the left or right of u	
						if (x_diff == 1 || x_diff == 0){ 
							u.addAdjacentNode(v);
						}	
					}
				}
			}
		}
	}
	
	/* 
	 * Given a list of nodes with (x,y) positions, generates adjacent nodes for each node
	 * A node is adjacent if it would be in the same row, column
	 * or box of 9 nodes
	 */
	public void generateAdjacentNodes_SudokuPattern(){
		
		// Create the edges
		for (int i = 0; i < nodes.size(); i ++){
			Node u = nodes.get(i);
			for (int k = 0; k < nodes.size(); k ++){					
				Node v = nodes.get(k);								

				// it doesn't need an edge to itself
				if (i != k){

					// Add edges that are within the same row
					if (u.getX() == v.getX()){
						u.addAdjacentNode(v);
					}
					// Add edges that are within the same column
					else if (u.getY() == v.getY()){
						u.addAdjacentNode(v);
					}
					// Add edges that are within the same box
					else if (u.getX() / 3 == v.getX() / 3 && u.getY() / 3 == v.getY() / 3){
						u.addAdjacentNode(v);
					}
				}
			}
		}
	}
	
	/*
	 * Resets the "visited" flag on all the nodes in the graph.
	 * Useful if you're starting a new search.
	 */
	public void resetVisitedStatusForAllNodes(){
		for (Node n : nodes){
			n.visited = false;
		}
	}
	
	
}
