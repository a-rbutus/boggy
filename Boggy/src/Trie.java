/**
 * 
 *  @author Natalia Cornwall
 * 
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class Trie {
	
	TrieNode root;
	
	public Trie(){
		root = new TrieNode();
	}

	public void loadDictionary(String filename){	
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String line = in.readLine();
			while (line != null) {		
				addWord(line);
				line = in.readLine();
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean addWord(String word){
		if (!(word.isEmpty() && word == null)){
			TrieNode node = root;
			for (char c : word.toCharArray()){
				node = node.add(c);
			}
			node.isLeaf = true;
		}
		return false;
	}
	
	public boolean containsWord(String word){
		TrieNode node = search(word);
		return node != null && node.isLeaf;
	}
	
	public boolean containsPrefix(String word){
		TrieNode node = search(word);
		return node != null;
	}
	
	private TrieNode search(String word){
		TrieNode node = root;
		for (char c : word.toCharArray()){
			node = node.get(c);
			if (node == null){
				return null;
			}
		}
		return node;
	}
	
	private class TrieNode {
	    char c;
	    HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
	    boolean isLeaf;
	 
	    public TrieNode() {}
	 
	    public TrieNode(char c){
	        this.c = c;
	    }
		
	    public TrieNode add(char c){
	    	if (children == null) {
	    		children = new HashMap<Character, TrieNode>();
	    	}

	    	if (children.get(c) == null) {
	    		TrieNode child = new TrieNode(c);
	    		children.put(c, child);
	    		return child;
	    	}
	    	return children.get(c);
	    }
	    
	    public TrieNode get(char c){
	    	return children.get(c);
	    }
	    
	}
	
}
