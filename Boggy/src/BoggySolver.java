import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class BoggySolver {	

	private static Scanner reader;

	public static void main (String args[]){
				
		reader = new Scanner(System.in);
		boolean tryAgain = true;

		while (tryAgain){

			try {
				System.out.println("Boggle will be an n x n board. Please enter an n:");
				int boggleSize = reader.nextInt();

				System.out.println("Minimum word length?");
				int minWordLength = reader.nextInt();

				while (minWordLength < 1){ 
					System.out.println("Needs to be greater than 0. Try again.");
					minWordLength = reader.nextInt();
				}

				System.out.println("Enter 0 if you want to use random tiles or 1 to enter your own Boggle.");
				Boolean randomTiles = (reader.nextInt() == 0) ? true : false;

				String userTileInput = "";
				if (!randomTiles){
					System.out.println("Enter your " +boggleSize*boggleSize +  " tile values (all one word):");
					userTileInput = reader.next();
				}

				String [][]tiles = new String[boggleSize][boggleSize];
				
				for (int i = 0; i < boggleSize; i ++){
					for (int k = 0; k < boggleSize; k ++){
						if (randomTiles) {
							Random r = new Random();
							tiles[i][k] = Character.toString((char)(r.nextInt(26) + 'a'));
						}
						else {
							tiles[i][k] = userTileInput.substring(k*boggleSize+i, k*boggleSize+i+1);
						}
					}				
				}
				
				Boggle boggy = new Boggle(minWordLength, tiles);
				boggy.loadDictionary("dictionary.txt");
				System.out.println(boggy);

				System.out.println("Searching Boggle...");
				ArrayList<String> solutions = boggy.solveBoggy();

				if (solutions.size() == 0){
					System.out.println("Sorry, didn't find any words.");
				}
				else {
					System.out.println("\nYour words are:\n");
					for (String w : solutions){
						System.out.println("word: " + w);
					}
				}

				System.out.println("\nWould you like to try again? Enter 0 for yes or 1 for no.");
				if (reader.nextInt() == 1){
					tryAgain = false;
				}

			}
			catch (InputMismatchException e){
				System.out.println("\nSorry, bad input.");
				tryAgain = false;
			}
		}
	}

}
