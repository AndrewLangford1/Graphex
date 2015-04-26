package graphex;


import java.util.ArrayList;

import datastructs.AbstractTree;
import datastructs.Alphabet;
import datastructs.FiniteAutomaton;
import datastructs.Token;
import datastructs.Tree;
import utilities.DotConverter;
import utilities.InputFileReader;
import utilities.OutputFileWriter;


 /**
  * Main Class
  * @author Andrew
  *
  */

public class Grep {
	
	/**
	 * Main function
	 * 
	 * @param args command line args
	 */
	public static void main(String[] args){
		
		try{
		
			
			mainControl(args);
				
		}
		
		catch(Exception e){
			System.out.println("Error in main grep function");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Manages whether or not user wants DOT output files.
	 * @param commandLineArgs
	 */
	private static void mainControl(String[] args){
		int argumentLength = args.length;
		ArrayList<FiniteAutomaton> machines = null;
		
		try{
			
			switch(argumentLength){
			
			//no Dot files specified
				case(2):{
					machines = grep(args[1], args[0]);	
				}
				
				break;
				
				
				//case one dot file specified
				case(4):{
					System.out.println(args[1]);
					
					//TODO recognize what file is wanted in output
					 machines = grep(args[3], args[2]);
					 writeFAtoFile(machines.get(0), args[1]);
				}
				
				break;
				
				
				
				case(6):{
					machines = grep(args[5], args[4]);	
				}
				
				break;
				
				default:{
					
			
					
				}
			}
			
		} catch(Exception e){
			
			
			
		}
	}
	
	
	/**
	 * Learns the alphabet from the given file, and  parses the regex.
	 * 
	 * @param file the file to run GREP against
	 * @param regexToParse the regex we want to parse
	 */
	public static ArrayList<FiniteAutomaton> grep(String file, String regexToParse){
		
		ArrayList<FiniteAutomaton> machines = new ArrayList<FiniteAutomaton>();
		
		//learn the alphabet
		Alphabet alphabet = learnAlphabetFromFile(file);
		
		//grab the regex from the command line
		String regex = regexToParse;
		
		//lex the regex into tokens so we know valid characters and symbols are used.
		ArrayList<Token> tokenList = lexRegex(regex, alphabet);
		
		if(!tokenList.isEmpty()){
			AbstractTree abstractTree = parseRegex(tokenList);	
			
			FiniteAutomaton nfa = buildNFA(abstractTree);
			
			machines.add(nfa);
			
			//convert nfa to dfa
			
			
			//perform pattern matching
			
		}	
		
		return machines;
		
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
	private static AbstractTree parseRegex(ArrayList<Token> tokens){
		RegexParser parser = new RegexParser(tokens);
		
		
		//parse the regex
		Tree parseTree = parser.parseRegularExpression();
		
		//abstract the tree
		AbstractTree abstractTree = new AbstractTree(parseTree);
		
		abstractTree.convertTree();
		
		abstractTree.print();
		
		return abstractTree;
		
	}
	
	/**
	 * 
	 * @param fileName the file we are testing the regex on
	 */
	private static Alphabet learnAlphabetFromFile(String file){
		String fileAsString = InputFileReader.readFile(file);
		
		Alphabet alphabet = new Alphabet();
		
		alphabet.learnAlphabet(fileAsString);	
		
		
		return alphabet;
	}
	
	
	/**
	 * Builds an NFA from the abstract parse tree.
	 * @param parseTree the parse tree generated during the parsing phase
	 * @return the nfa
	 */
	private static FiniteAutomaton buildNFA(AbstractTree parseTree){
		NFAGen nfaGen = new NFAGen(parseTree);
		FiniteAutomaton nfa =  nfaGen.generateNFA();
		return nfa;
	}
	
	/**
	 * 
	 * @param automaton the automaton we wish to generate a DOT file for
	 * @param fileName the fileName we wish to write to.
	 */
	private static void writeFAtoFile(FiniteAutomaton automaton, String fileName){
		ArrayList<String> fileLines = DotConverter.convertToDotFile(automaton);
		OutputFileWriter.writeToFile(fileName, fileLines);
	}


}
