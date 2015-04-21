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
	
	public void parseRegularExpression(){
		parseRegex();
	}

	private void parseRegex() {
		//simple term case
		parseTerm();
		
		//case where we have a union operator followed by one or multiple more regexes.
		if(hasNextToken()){
			match(Token.TokenType.UNIONOPERATOR);
			parseRegex();
		}
	}
	
	private void parseTerm(){
		parseFactor();
		parseTerm();
	}
	
	private void parseFactor(){
		parseBase();
		if(hasNextToken()){
			match(Token.TokenType.STAROPERATOR);
		}
	}
	
	private void parseBase(){
		Token nextToken = getNextToken();
		if(nextToken != null){
			switch(nextToken.getTokenType().getName()){
				case("CHAR"):{
					
				} 
				
				break;
				
				case("OPENGROUP"):{
					
					
				}
				
				break;
			
				default: {
					//raise error
					
				}	
			}
		}
	}
	
	
	/**
	 * Matches the current token with the expected token.
	 * @param toMatch the token type we are attempting to match.
	 */
	private void match(Token.TokenType toMatch){
		Token nextToken = getNextToken();
		if(nextToken != null){
			Token.TokenType nextTokenType = nextToken.getTokenType();
			if(nextTokenType == toMatch){
				consumeToken();
				//if we get here, we are successful matching and return
				return;
			}
		}
		
		//if we get here, there was some sort of error
		//TODO make this better at erroring.
		System.out.println("Error matching token.")
		
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
	private Token getNextToken(){
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
}
