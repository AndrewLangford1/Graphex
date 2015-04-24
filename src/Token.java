


/**
 * Class to hold and define types of tokens we find in the regexp
 * @author Andrew
 *
 */
public class Token {
	
	
	/**
	 * enum class to define types of tokens allowed.
	 * Note: concat operator is uneeded, as it is defined by the lack of symbol between symbols. 
	 * Will catch concat operators in parsing phase.
	 * @author Andrew
	 *
	 */
	public enum TokenType {
	    
		OPENGROUP('(', "OPENGROUP"), CLOSEGROUP(')', "CLOSEGROUP"), STAROPERATOR('*', "STAROPERATOR"), UNIONOPERATOR('|', "UNIONOPERATOR"), CHAR("CHAR");
		
	   private Character type;
	   private String name;
	   
	   
	   TokenType(Character type) {
		   this.type = type;

	    }
	    TokenType(String name) {
	    	this.name = name;

	    }
	    
	    TokenType(Character type, String name){
	    	this.type = type;
	    	this.name = name;
	    	
	    }
	    
	    public Character getChar(){
	    	return this.type;
	    }
	    
	    public String getName(){
	    	return this.name;
	    }
	}
	
//--Fields--//
	private TokenType tokenType;
	private Character value;
	
//--METHODS--//
	
	public Token(){
		this.tokenType = null;
		this.value = null;
	}
	
	public Token(TokenType type, Character value){
		this.tokenType = type;	
		this.value = value;
	}
	
	/**
	 * @return the value
	 */
	public Character getValue() {
		return value;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(Character value) {
		this.value = value;
	}
	
	/**
	 * 
	 * @return the token type
	 */
	public TokenType getTokenType(){
		return this.tokenType;
	}
	
	/**
	 * 
	 * @param type the token type to set
	 */
	public void setToken(TokenType type){
		this.tokenType = type;
		
	}
	
	
	/**
	 * 
	 * @param tokenType
	 * @return
	 */
	public boolean isOfType(Token.TokenType tokenType){
		if(getTokenType() == tokenType){
			return true;
		}
		else{
			return false;
			
		}
	}
	
	
	/**
	 * Returns this object as a string
	 */
	public String toString(){
		String asString = "<" + this.getValue() + " , " + this.getTokenType().getName() +">";
		return asString;	
	}

}
