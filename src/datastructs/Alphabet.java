package datastructs;
import java.util.HashSet;



/**
 * Class that simply takes in a string, and creates an alphabet for all of the characters in that string.
 * @author Andrew
 *
 */
public class Alphabet {

	
//--Fields--//

	//contains the characters in the alphabet
	private HashSet<Character> alphabet;
	
	
//--Constructors--//
	public Alphabet(){
		alphabet = new HashSet<Character>();
		
	}
	
	
	/**
	 * 
	 * @param fileToLearn the file containing the characters we want to learn.
	 */
	public void learnAlphabet(String fileToLearn){
		for(int i =0; i < fileToLearn.length(); i++)
			alphabet.add(fileToLearn.charAt(i));
	}
	
	
	/**
	 * @return a string with all the characters seperated by commas and enclosed in [ and  ]
	 */
	public String toString(){
		return alphabet.toString();
	}
	
	/**
	 * returns the set of characters learned
	 */
	public HashSet<Character> getAlphabet(){
		return this.alphabet;
	}

}
