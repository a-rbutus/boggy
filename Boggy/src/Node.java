/**
 * 
 *  @author Natalia Cornwall
 * 
 */
import java.util.ArrayList;

public class Node{
	
	private ArrayList<Node> adjacentNodes;
	Boolean visited = false;
	int x;
	int y;
	String value;

	public Node(int x, int y, String value){
		adjacentNodes = new ArrayList<Node>();
		this.x = x;
		this.y = y;
		this.value = value;
	}

	public void addAdjacentNode(Node n){
		adjacentNodes.add(n);
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}
	
	public ArrayList<Node> getAdjacentNodes(){
		return adjacentNodes;
	}
	public void setAdjacentNodes(ArrayList<Node> nodes){
		adjacentNodes = nodes;
	}

	public String toString(){
		//return "(" + this.x + "," + this.y + "):" + this.value;
		return this.value;
	}
	
	public Boolean equals(Node other){
		return this.x == other.x && this.y == other.y && this.value == other.value;
	}
}