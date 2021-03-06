package graphex;
import java.util.ArrayList;
import java.util.HashSet;

import datastructs.Token;



/**
 * Class to tokenize and check validity of the regular expression
 * @author Andrew
 *
 */
public class Lexer {
	
//--Fields--//
	private String regex;
	private HashSet<Character> alphabet;
	private ArrayList<Token> tokenList;
	 
//--Constructors--//
	/**
	 * 
	 * @param regexToLex the file containing the regex we read in from the command line, and turned into a string.
	 * @param alphabet, the alphabet we learned from the file
	 */
	public Lexer(String regexToLex, HashSet<Character> alphabet){
		this.regex = regexToLex;
		this.alphabet = alphabet;
		this.tokenList = new ArrayList<Token>();
	}
	
	
	/**
	 * Matches tokens and builds a token list to be sent to a regular expression parser.
	 */
	public ArrayList<Token> lex(){
		for(int i =0; i< regex.length(); i++){
			tokenMatch(regex.charAt(i));
		}
		
		return this.tokenList;
	}
	
	
	/**
	 * Matches tokens for star, union, and grouping operators, as well as vaid symbols in the language provided by the input file. anything else raises an error.
	 * @param currentChar
	 */
	private void tokenMatch(Character currentChar){
		switch(currentChar){
		
		//Match operator symbols
			case('*'):{
				buildToken(Token.TokenType.STAROPERATOR, Token.TokenType.STAROPERATOR.getChar());
			}
			
			break;
			
			case('|'):{
				buildToken(Token.TokenType.UNIONOPERATOR, Token.TokenType.UNIONOPERATOR.getChar());	
			}
			
			break;
			
			case('('):{
				buildToken(Token.TokenType.OPENGROUP, Token.TokenType.OPENGROUP.getChar());	
			}
				
			break;
			
			case(')'):{
				buildToken(Token.TokenType.CLOSEGROUP, Token.TokenType.CLOSEGROUP.getChar());
			}
			
			break;
			
			
			//If we get here, these are symbols in the alphabet
			default:{
				if(alphabet.contains(currentChar)){
					buildToken(Token.TokenType.CHAR, currentChar);
				}
				
				//if the current char isn't found in the alphabet, theres an error and we kill the function
				else{
					invalidSymbolError(currentChar);
				}
			}
		}
	}
	
	/**
	 * builds a token and adds it to the token list to be parsed
	 * @param type the type of token this is
	 * @param value the value of the token
	 */
	private void buildToken(Token.TokenType type, Character value){
		Token token = new Token(type, value);
		addToken(token);
	}
	
	
	/**
	 * SImply add a token to the token list
	 * @param token the token to add to the list
	 */
	private void addToken(Token token){
		tokenList.add(token);
	}
	
	
	/**
	 * Prints an invalid token error and exits the program
	 * @param errorChar the character that was invalid
	 */
	private void invalidSymbolError(Character errorChar){
		String error = "Invalid symbol" + "'" + errorChar + "'" +   "found in regular expression. GREP Exiting.....";
		System.out.println(error);
		System.exit(0);
	}
}
