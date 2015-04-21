

import utilities.InputFileReader;


 	

public class Grep {
	
	public static void main(String[] args){
		
		try{
			Alphabet alphabet = readFile(args[0]);
			String regex = readRegex(args[1]);
			
			System.out.println(alphabet.toString());	
			
			Lexer lexer = new Lexer(regex, alphabet.getAlphabet());
			lexer.lex();
		}
		
		catch(Exception e){
			System.out.println("Error in main grep function");
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 
	 * @param regexFileName the file we are reading the regexfrom
	 */
	private static String readRegex(String regexFileName){
		String regexFileAsString = InputFileReader.readFile(regexFileName);
		return regexFileAsString;
		
		//pass this on to NFA construction
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
