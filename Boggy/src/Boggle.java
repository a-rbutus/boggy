import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

public class Boggle {

	public Boggle(Tile[] boggle, int min, int boggle_total_tiles) {
		super();
		this.tiles = boggle;
		this.min_word_length = min;
		this.boggle_total_tiles = boggle_total_tiles;
		totalPaths = 0;
	}

	private Tile[] tiles;
	private int min_word_length;
	private int boggle_total_tiles;
	private int totalPaths;
	private Hashtable<String, ArrayList<String>> boggle_dictionary = new Hashtable<String, ArrayList<String>>();
	
	private ArrayList<String> words;
	
	private Stack<ArrayList<Integer>> paths = new Stack<ArrayList<Integer>>();

	public boolean containsWord(String word){
		if (word.length() < min_word_length) return false;
		String key = word.substring(0, min_word_length);
		return (boggle_dictionary.containsKey(key) &&
				boggle_dictionary.get(key).contains(word));
	}	
	
	public ArrayList<String> search_boggy(){		
		words = new ArrayList<String>();			
		ArrayList<Integer> firstPath = new ArrayList<Integer>();
		
		//generate initial paths
		for (int i = 0; i < boggle_total_tiles ; i ++){
			add_path(firstPath, i);
		}
		
		while (!paths.empty()){
			ArrayList<Integer> testPath = paths.pop();
			String testWord = word_from_tiles(testPath);
						
			//check the current path
			if (containsWord(testWord) && testWord.length() >= min_word_length){
				words.add(testWord);
			}
			
			//check if you need to add new paths
			if (testWord.length() >= min_word_length){			
				if (boggle_dictionary.containsKey(testWord.substring(0, min_word_length))){
					make_new_paths(testPath);
				}
			}
			else make_new_paths(testPath);
			
		}	
		return words;
	}

	private void make_new_paths(ArrayList<Integer> testPath) {
		
		int row = (int) Math.sqrt(boggle_total_tiles);		
		int lastElem = testPath.get(testPath.size() - 1);
		
		ArrayList<Integer> validMoves = new ArrayList<Integer>();
		
		lastElem ++ ;
		
		//top left move
		if ((lastElem - row - 1 ) % row != 0)
			validMoves.add(lastElem - row - 2);
		
		//top middle move
		validMoves.add(lastElem - row - 1);
		
		//top right move
		if ((lastElem - row + 1) % row != 1)
			validMoves.add(lastElem - row);
		
		//left move
		if ((lastElem - 1) % row != 0)
			validMoves.add(lastElem - 2);

		//right move
		if ((lastElem + 1) % row != 1)
			validMoves.add(lastElem);
		
		//bottom left move
		if ((lastElem + row - 1) % row != 0)
			validMoves.add(lastElem + row - 2);
		
		//bottom middle move
		validMoves.add(lastElem + row - 1);

		//bottom right move
		if ((lastElem + row + 1) % row != 1)
			validMoves.add(lastElem + row);
		
		for (int i : validMoves){
			if (!testPath.contains(i) && i >= 0 && i < boggle_total_tiles){
				add_path(testPath, i);
			}
		}
	}

	public void add_path(ArrayList<Integer> src, int addToPath){	

		ArrayList<Integer> newPath = new ArrayList<Integer>();
		newPath.addAll(src);
		newPath.add(addToPath);
		//System.out.println("New path: " + newPath);
		paths.push(newPath);
		totalPaths ++;
	}

	public String word_from_tiles(ArrayList<Integer> path){
		String word = "";
		for (Integer i : path){	
			String tile =  tiles[i].get_tile_value();
			word = word + tile;
		}
		return word;
	}

	public void add_word(String word){	
		
		if (word.length() < boggle_total_tiles && word.length() >= min_word_length){
			String key = word.substring(0, min_word_length);
			if (boggle_dictionary.containsKey(key)){				
				boggle_dictionary.get(key).add(word);
			}
			else {
				ArrayList<String> newValue = new ArrayList<String>();
				newValue.add(word);
				boggle_dictionary.put(key, newValue);
			}
		}
	}

	public void printBoggy(){
		System.out.println("Your Boggle is: ");

		Tile[] tiles = this.getTiles();
		for (int i = 0 ; i < boggle_total_tiles; i ++){
			if (i % Math.sqrt(boggle_total_tiles) == 0) {
				// print new line for every row
				System.out.println();
			}
			System.out.print(tiles[i].get_tile_value() + " ");
		}
		System.out.println("\n");		
		//print_dictionary();
	}
	
	public void print_dictionary(){
		 for (String k : boggle_dictionary.keySet()) {
			 System.out.print("\n" + k + ": ");
			 ArrayList<String> listy = boggle_dictionary.get(k);
			 for (String j : listy){
				 System.out.print(j + " ");
			 }
		 }
	}

	public Tile[] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[] tiles) {
		this.tiles = tiles;
	}

	public Hashtable<String, ArrayList<String>> get_boggle_dictionary() {
		return boggle_dictionary;
	}

	public void set_boggle_dictionary(
			Hashtable<String, ArrayList<String>> dict) {
		this.boggle_dictionary = dict;
	}

	public int get_min_word_length() {
		return min_word_length;
	}

	public void set_min_word_length(int min_word_length) {
		this.min_word_length = min_word_length;
	}

	public Stack<ArrayList<Integer>> getPaths() {
		return paths;
	}

	public void setPaths(Stack<ArrayList<Integer>> paths) {
		this.paths = paths;
	}

	public int getTotalPaths() {
		return totalPaths;
	}

}