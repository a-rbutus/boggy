/**
 * 
 *  @author Natalia Cornwall
 * 
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Random;

public class Boggle extends Graph{

	public int minWordLength;
	public int boggleSize;
	public Trie dictionary;
				
	public Boggle(Trie dictionary) {
		super();
		this.minWordLength = 0;
		this.boggleSize = 0;
		this.dictionary = dictionary;
	}
	
	public void shake(){
		String [][] randomTiles = makeRandomTiles(boggleSize);
		createOrUpdateGraph(minWordLength, randomTiles);
	}
	
	public void shake(int minWordLength, int size){
		String [][] randomTiles = makeRandomTiles(size);
		createOrUpdateGraph(minWordLength, randomTiles);
	}
	
	public String[][] makeRandomTiles(int size){
		String [][] randomTiles = new String[size][size];
		for (int i = 0; i < size; i ++){
			for (int k = 0; k < size; k ++){
				Random r = new Random();
				randomTiles[i][k] = Character.toString((char)(r.nextInt(26) + 'a'));
			}				
		}
		return randomTiles;
	}
	
	public void parseUserInputBoggle(int minWordLength, String board) throws InputMismatchException{
		
		Double squareRoot = Math.sqrt(board.length());
		if (squareRoot % 1 != 0 || board.isEmpty() || board == null){
			throw new InputMismatchException();
		}
		int boggleSize = squareRoot.intValue();
		String [][] tiles = new String[boggleSize][boggleSize];
		for (int i = 0; i < boggleSize; i ++){
			for (int k = 0; k < boggleSize; k ++){
				tiles[i][k] = board.substring(k*boggleSize+i, k*boggleSize+i+1);
			}				
		}
		createOrUpdateGraph(minWordLength, tiles);
	}
	
	private void createOrUpdateGraph(int minWordLength, String [][] tiles){
					
		//Same sized board. We only need to update the node values.
		if (this.boggleSize == tiles.length){
			for (Node n: nodes){
				n.value = tiles[n.getX()][n.getY()];
			}
		}
		//We need to make a new graph
		else {
			nodes.removeAll(nodes);
		
			for (int i = 0; i < tiles.length; i ++){
				for (int k = 0; k < tiles[i].length; k ++){
					addNode(new Node(i, k, tiles[i][k]));
				}
			}
			generateAdjacentNodes_GridPattern();
		}
		
		this.minWordLength = minWordLength;
		this.boggleSize = tiles.length;
	}
	
	/*
	 * Start DFS search, using each node as a DFS start node
	 */
	public HashSet<String> solveBoggy(){	
		
		HashSet<String> solutions = new HashSet<String>();
		
		for (Node n : nodes){
			ArrayList<Node> currentPath = new ArrayList<Node>();
			currentPath.add(n);
			search(solutions, currentPath);			
		}
		return solutions;
	}

	public void search(HashSet<String> solutions, ArrayList<Node> currentPath){
		
		String word = wordFromSearchPath(currentPath);
		if (dictionary.containsWord(word) && word.length() >= minWordLength){
			solutions.add(word);
		}
		
		Node lastNode = currentPath.get(currentPath.size()-1);
		for (Node n : lastNode.getAdjacentNodes()){
			if (!currentPath.contains(n)){
				
				//if the dictionary does not contain this prefix we can backtrack
				if (dictionary.containsPrefix(word)){
					currentPath.add(n);
					search(solutions, currentPath);
					currentPath.remove(n);
				}
			}
		}
	}
	
	public String wordFromSearchPath(ArrayList<Node> path){
		StringBuilder builder = new StringBuilder();
		for (Node n : path ){
			builder.append(n.value);
		}
		return builder.toString();
	}
	
	public String toString(){
		
		// load the adjacency list into a matrix
		Node[][] matrix = new Node[boggleSize][boggleSize];
		for (Node n : nodes){
			matrix[n.getX()][n.getY()] = n;
		}
				
		//print the matrix in a grid
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i<boggleSize; i++){
		    for (int k = 0; k<boggleSize; k++){
		    	builder.append(matrix[k][i]);
		    }
		    builder.append("\n");
		}
		return builder.toString();
	}

}