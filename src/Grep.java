

import java.util.ArrayList;

import utilities.InputFileReader;


 	

public class Grep {
	
	public static void main(String[] args){
		
		try{
			
			
			
			grep(args[1], args[0]);
			
			
			
			
		}
		
		catch(Exception e){
			System.out.println("Error in main grep function");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Learns the alphabet from the given file, and  parses the regex.
	 * 
	 * @param file the file to run GREP against
	 * @param regexToParse the regex we want to parse
	 */
	public static void grep(String file, String regexToParse){
		
		//learn the alphabet
		Alphabet alphabet = learnAlphabetFromFile(file);
		
		
		//grab the regex from the command line
		String regex = regexToParse;
		
		//lex the regex into tokens so we know valid characters and symbols are used.
		ArrayList<Token> tokenList = lexRegex(regex, alphabet);
		
		if(!tokenList.isEmpty()){
			parseRegex(tokenList);
			
		}	
	}

	
	/**
	 * lexes the regex to make sure we are using valid symbols and characters.
	 * 
	 * @param regex the regex to lex
	 * @param alphabet the alphabet we learned from the file
	 * @return
	 */
	private static ArrayList<Token >lexRegex(String regex, Alphabet alphabet){
		Lexer lexer = new Lexer(regex, alphabet.getAlphabet());
		
		ArrayList<Token> tokenList = lexer.lex();
		
		return tokenList;
		
	}
	
	
	/**
	 * parses the regex and builds a cst
	 * 
	 * @param tokens the regex split up into lex tokens
	 */
	private static void parseRegex(ArrayList<Token> tokens){
		RegexParser parser = new RegexParser(tokens);
		
		parser.parseRegularExpression();
		
		
	}
	
	/**
	 * 
	 * @param fileName the file we are testing the regex on
	 */
	private static Alphabet learnAlphabetFromFile(String file){
		String fileAsString = InputFileReader.readFile(file);
		
		Alphabet alphabet = new Alphabet();
		
		alphabet.learnAlphabet(fileAsString);	
		
		System.out.println(alphabet.toString());
		
		return alphabet;
	}
	


}
