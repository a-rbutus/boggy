/**
 * 
 *  @author Natalia Cornwall
 * 
 */
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BoggySolver {	

	private static Scanner reader;

	public static void main (String args[]){
		
		Trie dictionary = new Trie();
		dictionary.loadDictionary("dictionary.txt");
		Boggle boggy = new Boggle(dictionary);
				
		reader = new Scanner(System.in);
		boolean tryAgain = true;

		while (tryAgain){

			try {
				System.out.println("Boggle will be an n x n board. Please enter an n:");
				int boggleSize = reader.nextInt();
				
				while (boggleSize < 1){ 
					System.out.println("Needs to be greater than 0. Please try again.");
					boggleSize = reader.nextInt();
				}

				System.out.println("Minimum word length?");
				int minWordLength = reader.nextInt();

				while (minWordLength < 1 || minWordLength > boggleSize*boggleSize){ 
					System.out.println("Minimum word length to be greater than 0 and less than the board size. Please try again.");
					minWordLength = reader.nextInt();
				}

				System.out.println("Enter 0 if you want to use random tiles or 1 to enter your own Boggle.");
				Boolean userInput = (reader.nextInt() != 0) ? true : false;

				if (userInput){
					System.out.println("Enter your tile values (all one word):");
					String userTileInput = reader.next();
					boggy.parseUserInputBoggle(minWordLength, userTileInput);
					System.out.println("\nHere is your Boggle:");
				}
				else {
					boggy.shake(minWordLength, boggleSize);
					System.out.println("\nHere is your random Boggle:");
				}
				
				System.out.println(boggy);

				System.out.println("Searching Boggle...");
				HashSet<String> solutions = boggy.solveBoggy();

				if (solutions.size() == 0){
					System.out.println("Sorry, I couldn't find any words.");
				}
				else {
					System.out.println("\nYour words are:\n");

					String longestWord = null;
					for (String word : solutions){
						System.out.println(word);
						if (longestWord == null || word.length() > longestWord.length()){
							longestWord = word;
						}
					}
					System.out.println("\nYour most awesome word is:");
					System.out.println(longestWord + "\n");
				}
				
				System.out.println("\nWould you like to play again? Enter 1 for yes or 0 for no.");
				if (reader.nextInt() == 0){
					tryAgain = false;
				}

			}
			catch (InputMismatchException e){
				System.out.println("Sorry, I couldn't read that. Please start over.\n");
				reader.nextLine();
			}
		}
	}

}
