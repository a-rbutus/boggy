import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;


public class Solver {
	
	public Solver(Boggle b) {
		super();
		boggy = b;
	}

	public Boggle boggy;
	
	public void read(int numLines){
		
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader("/Users/talia/Desktop/local_workspace/Boggyv2/src/dictionary.txt"));

			for(int i = 0; i < numLines; i ++) {				
				this.boggy.add_word(reader.readLine());
			}

		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} finally {
			try { if (reader!=null) reader.close(); } catch (Exception e) {}
		}
	}
	
	public static void main (String args[]){
		
		boolean try_again = true;
		
		while (try_again){
	
			System.out.println("How many tiles in the Boggle?");
			Scanner reader = new Scanner(System.in);
			int boggle_total_tiles = reader.nextInt();
	
			while (Math.sqrt(boggle_total_tiles) % 1 != 0){ 
				System.out.println("Your Boggle is not square. Try again.");
				boggle_total_tiles = reader.nextInt();
			}
	
			System.out.println("Minimum word length?");
			int min_word_length = reader.nextInt();
			
			while (min_word_length < 1){ 
				System.out.println("Needs to be greater than 0. Try again.");
				min_word_length = reader.nextInt();
			}
			
			System.out.println("Enter 0 if you want to use random tiles or 1 to enter new tiles.");
			int boggle_mode = reader.nextInt();
	
			Tile [] tiles = new Tile[boggle_total_tiles];
			
			if (boggle_mode == 0){
				for (int i = 0; i < boggle_total_tiles; i ++){
					Random r = new Random();
					tiles[i] = new Tile(Character.toString((char)(r.nextInt(26) + 'a')));
				}
			}
			else if (boggle_mode == 1){
				System.out.println("Enter your tiles (all one word):");
				String user_tiles = reader.next();
				
				for (int i = 0; i < boggle_total_tiles; i ++){
					tiles[i] = new Tile(user_tiles.substring(i, i+1));
				}
			}
				
	/*	
			Tile[] non_random_tiles = {new Tile("l"), 
					new Tile("s"),
					new Tile("i"),
					new Tile("h"),
					new Tile("a"),
					new Tile("e"),
					new Tile("e"),
					new Tile("d"),
					new Tile("r"),
					new Tile("h"),
					new Tile("o"),
					new Tile("j"),
					new Tile("h"),
					new Tile("t"),
					new Tile("e"),
					new Tile("e"),
			};*/
			
			Boggle boggy = new Boggle(tiles, min_word_length, boggle_total_tiles);		
			Solver solver = new Solver (boggy);
			
			solver.read(172823);		
			boggy.printBoggy();
			System.out.println("Searching Boggle...");
			ArrayList<String> result_words = boggy.search_boggy();
			
			if (result_words.size() == 0){
				System.out.println("Sorry, didn't find any words.");
			}
			else {
				System.out.println("\nYour words are:\n");
				for (String w : result_words){
					System.out.println("word: " + w);
				}
			}
			System.out.println("\nTotal paths searched: " + boggy.getTotalPaths());
			
			System.out.println("\nWould you like to try again? Enter 0 for yes or 1 for no.");
			if (reader.nextInt() == 1)
				try_again = false;
		}
	}
}
