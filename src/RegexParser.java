import java.util.ArrayList;



/**
 * Class to parse regular expressions. 
 * @author Andrew
 *
 */
public class RegexParser {
	
	private ArrayList<Token> tokenList;
	private ArrayList<String> errorList;
	
	
	public RegexParser(ArrayList<Token> tokenList){
		this.tokenList = tokenList;	
		this.errorList = new ArrayList<String>();
	}
	
	
	/**
	 * method to initialize recursive descent parser
	 */
	public void parseRegularExpression(){
		
		parseRegex();
		
		//if we have more tokens, then there are trailing tokens.
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
	
	private void parseTerm(){
		System.out.println("Parsing Term");
		parseFactor();
		
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
					
					
				}
			}			
		}
	}
	
	private void parseFactor(){
		System.out.println("Parsing Factor");
		parseBase();
		
		while(hasNextToken() && nextToken().isOfType(Token.TokenType.STAROPERATOR)){
			match(Token.TokenType.STAROPERATOR);
		}
	
	}
	
	private void parseBase(){
		System.out.println("Parsin Base");
		Token nextToken = nextToken();
		if(nextToken != null){
			switch(nextToken.getTokenType().getName()){
				case("CHAR"):{
					parseChar();
				} 
				
				break;
				
				case("OPENGROUP"):{
					match(Token.TokenType.OPENGROUP);
					
					parseRegex();
					
					match(Token.TokenType.CLOSEGROUP);
					
				}
				
				
				break;
			
				default: {
					String error = "Expected '( <regex> )' or <char>, received " + nextToken.getTokenType().getName();
					raiseErrorParsing(error);
				}	
			}
		}
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
	
	private void raiseErrorParsing(String errorMessage){
		System.out.println(errorMessage);
		System.out.println("Regex parse failed. Grep exiting.........");
		System.exit(0);	
	}
}
