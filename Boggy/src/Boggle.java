import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class Boggle extends Graph{

	public int minWordLength= 0;
	public int boggleSize = 0;
	
	private Hashtable<String, ArrayList<String>> boggleDictionary = new Hashtable<String, ArrayList<String>>();
		
	public Boggle(int minWordLength, String [][] tiles) {
		super();
		this.minWordLength = minWordLength;
		this.boggleSize = tiles.length;
		
		for (int i = 0; i < tiles.length; i ++){
			for (int k = 0; k < tiles[i].length; k ++){
				addNode(new Node(i, k, tiles[i][k]));
			}
		}
		generateAdjacentNodes_GridPattern();
	}
	
	public void loadDictionary(String filename){	
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String line = in.readLine();
			while (line != null) {		
				addWordToDictionary(line);
				line = in.readLine();
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Start DFS search, using each node as a start node
	 */
	public ArrayList<String> solveBoggy(){	
		
		ArrayList<String> solutions = new ArrayList<String>();
		
		for (int i = 0; i < nodes.size(); i++){
			ArrayList<Node> currentPath = new ArrayList<Node>();
			Node root = nodes.get(i);
			currentPath.add(root);
			performDFS(solutions, currentPath);			
		}
		return solutions;
	}

	public void performDFS(ArrayList<String> solutions, ArrayList<Node> currentPath){
		
		String word = wordFromPath(currentPath);
		if (dictionaryContainsWord(word)){
			solutions.add(word);
		}
		
		Node lastNode = currentPath.get(currentPath.size()-1);
		for (Node n : lastNode.getAdjacentNodes()){
			if (!currentPath.contains(n)){
				
				//if the dictionary does not contain this prefix we can backtrack
				if (dictionaryContainsPrefix(word)){
					currentPath.add(n);
					performDFS(solutions, currentPath);
					currentPath.remove(n);
				}
			}
		}
	}
	
	public String wordFromPath(ArrayList<Node> path){
		String word = "";
		for (Node n :path ){
			word = word + n.value;
		}
		return word;
	}
	
	public String toString(){
		
		// load the adjacency list into a matrix
		Node[][] matrix = new Node[boggleSize][boggleSize];
		for (Node n : nodes){
			matrix[n.getX()][n.getY()] = n;
		}
		
		String result = "";
		
		//print the matrix in a grid
		for (int i = 0; i<boggleSize; i++){
		    for (int k = 0; k<boggleSize; k++){
		        result +=matrix[k][i];
		    }
		    result += "\n";
		}
		return result;
	}

	
	public void addWordToDictionary(String word){	
		if (word.length() < nodes.size() && word.length() >= minWordLength){
			String key = word.substring(0, minWordLength);
			if (boggleDictionary.containsKey(key)){				
				boggleDictionary.get(key).add(word);
			}
			else {
				ArrayList<String> words = new ArrayList<String>();
				words.add(word);
				boggleDictionary.put(key, words);
			}
		}
	}
	
	public boolean dictionaryContainsPrefix(String prefix){
		if (prefix.length() < minWordLength) return true;
		String key = prefix.substring(0, minWordLength);
		return boggleDictionary.containsKey(key);
	}
	
	public boolean dictionaryContainsWord(String word){
		if (word.length() < minWordLength) return false;
		String key = word.substring(0, minWordLength);
		return (boggleDictionary.containsKey(key) &&
				boggleDictionary.get(key).contains(word));
	}	
	
	public void printDictionary(){
		 for (String key : boggleDictionary.keySet()) {
			 System.out.print("\n" + key + ": ");
			 ArrayList<String> words = boggleDictionary.get(key);
			 for (String word : words){
				 System.out.print(word + " ");
			 }
		 }
	}
	
	public Hashtable<String, ArrayList<String>> getBoggleDictionary() {
		return boggleDictionary;
	}

	public void setBoggleDictionary(
			Hashtable<String, ArrayList<String>> boggleDictionary) {
		this.boggleDictionary = boggleDictionary;
	}

}