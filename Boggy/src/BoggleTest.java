import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;

import org.junit.Test;

public class BoggleTest {
	
	@Test
	public void test() {
		
		Tile[] tiles = {new Tile("a"), 
				new Tile("e"),
				new Tile("r"),
				new Tile("e"),
				new Tile("s"),
				new Tile("t"),
				new Tile("o"),
				new Tile("p"),
				new Tile("n"),
			};

		Boggle boggy = new Boggle(tiles, 3, 9);
		Hashtable<String, ArrayList<String>> dict = new Hashtable<String, ArrayList<String>>();	
		
		ArrayList<String> words = new ArrayList<String>();
		words.add("erea");
		words.add("ereeb");
		dict.put("ere", words);
		boggy.set_boggle_dictionary(dict);
		
		assertTrue(boggy.containsWord("erea"));
		
		ArrayList<Integer> path0 = new ArrayList<Integer>(Arrays.asList(0, 1, 2));
		assertEquals("aer", boggy.word_from_tiles(path0));
		
		ArrayList<Integer> path1 = new ArrayList<Integer>(Arrays.asList(0));		
		ArrayList<Integer> path2 = new ArrayList<Integer>();
		boggy.add_path(path2, 0);		
		assertEquals(boggy.getPaths().search(path1), 1);
		assertTrue(boggy.getPaths().search(path0) < 1);	
		
		ArrayList<Integer> path3 = new ArrayList<Integer>(Arrays.asList(0, 1));		
		boggy.add_path(path3, 2);	
		
		ArrayList<Integer> path4 = new ArrayList<Integer>(Arrays.asList(0, 1, 2));		
		assertEquals(boggy.getPaths().pop(), path4);
	
		ArrayList<Integer> path5 = new ArrayList<Integer>();
		ArrayList<Integer> path6 = new ArrayList<Integer>(Arrays.asList(0));		
		boggy.add_path(path5, 0);	
		assertEquals(boggy.getPaths().pop(), path6);	
		
		ArrayList<Integer> path7 = new ArrayList<Integer>();
		ArrayList<Integer> path8 = new ArrayList<Integer>(Arrays.asList(7));		
		boggy.add_path(path7, 7);	
		assertEquals(boggy.getPaths().pop(), path8);
		
		boggy.add_word("erec");
		assertTrue(boggy.containsWord("erec"));
		
		boggy.add_word("ab");
		assertFalse(boggy.containsWord("ab"));

		boggy.add_word("");
		assertFalse(boggy.containsWord(""));
		
		boggy.add_word("abc");
		assertTrue(boggy.containsWord("abc"));	
		
		boggy.add_word("abcd");
		assertTrue(boggy.containsWord("abcd"));		

		boggy.add_word("abce");
		assertTrue(boggy.containsWord("abce"));	
		
		boggy.add_word("abce");
		assertTrue(boggy.containsWord("abce"));	
		
		boggy.set_min_word_length(1);
		
		boggy.add_word("a");
		assertTrue(boggy.containsWord("a"));	
		
		boggy.add_word("b");
		assertTrue(boggy.containsWord("b"));
		assertTrue(boggy.containsWord("a"));
		
		boggy.printBoggy();
		
		boggy.add_word("bc");
		assertTrue(boggy.containsWord("bc"));
		assertTrue(boggy.containsWord("a"));
		assertTrue(boggy.containsWord("b"));

		boggy.add_word("bd");
		assertTrue(boggy.containsWord("bd"));
		assertTrue(boggy.containsWord("bc"));
		assertTrue(boggy.containsWord("a"));
		assertTrue(boggy.containsWord("b"));
		
		boggy.printBoggy();
		
	}

}
