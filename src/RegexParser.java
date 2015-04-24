import java.util.ArrayList;



/**
 * Class to parse regular expressions. 
 * @author Andrew
 *
 */
public class RegexParser {
	
	private ArrayList<Token> tokenList;
	
	
	public RegexParser(ArrayList<Token> tokenList){
		this.tokenList = tokenList;	
	}
	
	
	/**
	 * method to initialize recursive descent parser
	 */
	public void parseRegularExpression(){
		
		parseRegex();
		
		//if we have more tokens, then there are trailing invalid tokens, and we should raise an error
		if(hasNextToken()){
			switch(nextToken().getTokenType().getName()){
				case("OPENGROUP"):{
					String error = "Found dangling opengroup character";
					raiseErrorParsing(error);
					
				}
				
				break;
				
				case("CLOSEGROUP"):{
					String error = "Found dangling closegroup character";
					raiseErrorParsing(error);
					
					
				}
				
				break;
				
				default:{
					//not sure what would come up here
					
				}
				
			}
		}
	}

	
	/**
	 * Parses a regular expression
	 */
	private void parseRegex() {
		System.out.println("Parsing Regular Expression");
		
		//simple term case
		parseTerm();
		
		
		//Union operation
		if(hasNextToken() && nextToken().isOfType(Token.TokenType.UNIONOPERATOR)){
			match(Token.TokenType.UNIONOPERATOR);
			parseRegex();
		}
			
	}
	
	/**
	 * Parses terms
	 */
	private void parseTerm(){
		System.out.println("Parsing Term");
		
		//parse the factor
		parseFactor();
		
		
		//if we have another char or regex, recurse back to parse term.
		if(hasNextToken()){
			switch(nextToken().getTokenType().getName()){
				case("CHAR"):{
					parseTerm();
				}
				
				break;
				
				case("OPENGROUP"):{
					parseTerm();
				
				}
				
				break;
				
				default :{
					//otherwise end <factor> <term> recursion.
					return;
					
				}
			}			
		}
	}
	
	
	/**
	 * Parses factors
	 */
	private void parseFactor(){
		System.out.println("Parsing Factor");
		
		//parse the base
		parseBase();
		
		//parse 1 or many star operators, will delete erroneous star operators later on 
		while(hasNextToken() && nextToken().isOfType(Token.TokenType.STAROPERATOR)){
			match(Token.TokenType.STAROPERATOR);
		}
	
	}
	
	
	/**
	 * Parses a base
	 */
	private void parseBase(){
		System.out.println("Parsing Base");
		
		//get the next token
		Token nextToken = nextToken();
		
		if(nextToken != null){
			
			switch(nextToken.getTokenType().getName()){
			
			//--chars and opengroups are valid first sets of bases.
				case("CHAR"):{
					parseChar();
				} 
				
				break;
				
				
				//starts a new regex
				case("OPENGROUP"):{
					match(Token.TokenType.OPENGROUP);
					
					parseRegex();
					
					match(Token.TokenType.CLOSEGROUP);
					
				}
				
				
				break;
			
				
				//If we get anything else, we know its an error
				default: {
					String error = "Expected '( <regex> )' or <char>, received " + nextToken.getTokenType().getName();
					raiseErrorParsing(error);
				}	
			}
		}
		
		//we were expecting either a char or a new group, but received nothing.
		else{
			String error = "Expected '( <regex> )' or <char>, received NULL";
			raiseErrorParsing(error);
		}
	}
	
	
	/**
	 * parses and matches a valid character
	 */
	private void parseChar(){
		System.out.println("Parsing Char");
		if(hasNextToken() && nextToken().isOfType(Token.TokenType.CHAR)){
			match(Token.TokenType.CHAR);
		}
		else{
			System.out.println("wasn't a char");
			
		}	
	}
	
	
	/**
	 * Matches the current token with the expected token, 
	 * @param toMatch the token type we are attempting to match.
	 */
	private void match(Token.TokenType toMatch){
		Token nextToken = nextToken();
		if(nextToken != null){
			Token.TokenType nextTokenType = nextToken.getTokenType();
			if(nextTokenType == toMatch){
				System.out.println("Matched a " + nextTokenType.getName() + ",  '" + nextToken.getValue() + "'");
				consumeToken();
				//if we get here, we are successful matching and return
				return;
			}
			raiseErrorParsing("Failed to match " + toMatch.getName() + " with " +  nextToken.getTokenType());
		}
		
		raiseErrorParsing("Expecting to match a token, received NULL");	
	}
	
	
	/**
	 * 
	 * @return true if there is another token in the list, false otherwise
	 */
	private boolean hasNextToken(){
		return (!tokenList.isEmpty());
	}
	
	
	/**
	 * 
	 * @return the next token in the list or null if there are no more tokens.
	 */
	private Token nextToken(){
		if(hasNextToken())
			return tokenList.get(0);
		else
			return null;
	}
	
	
	/**
	 * Simply removes the first token from the list.
	 */
	private void consumeToken(){
		if(hasNextToken()){
			tokenList.remove(0);
		}	
	}
	
	
	/**
	 * 
	 * Raises an error and exits the program gracefully.
	 * 
	 * @param errorMessage the error message we want to print
	 */
	private void raiseErrorParsing(String errorMessage){
		System.out.println(errorMessage);
		System.out.println("Regex parse failed. Grep exiting.........");
		System.exit(0);	
	}
}
