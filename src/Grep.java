

import java.util.ArrayList;

import utilities.InputFileReader;


 	

public class Grep {
	
	public static void main(String[] args){
		
		try{
			Alphabet alphabet = readFile(args[1]);
			
			String regex = args[0];
			
			System.out.println(alphabet.toString());	
			
			Lexer lexer = new Lexer(regex, alphabet.getAlphabet());
			
			ArrayList<Token> tokenList = lexer.lex();
			
			RegexParser parser = new RegexParser(tokenList);
			
			parser.parseRegularExpression();
		}
		
		catch(Exception e){
			System.out.println("Error in main grep function");
			e.printStackTrace();
		}
	}	
	


	
	/**
	 * 
	 * @param fileName the file we are testing the regex on
	 */
	private static Alphabet readFile(String file){
		String fileAsString = InputFileReader.readFile(file);
		
		Alphabet alphabet = new Alphabet();
		
		alphabet.learnAlphabet(fileAsString);	
		
		return alphabet;
		// learn the alphabet
		
		//perform grep functionality
	}
	


}
