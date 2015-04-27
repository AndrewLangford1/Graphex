package graphex;

import datastructs.AbstractTree;
import datastructs.FiniteAutomaton;
import datastructs.State;
import datastructs.Token;
import datastructs.TreeNode;

public class NFAGen {
	
	
	private AbstractTree parseTree;
	private FiniteAutomaton nfa;
	
	public NFAGen(AbstractTree parseTree){
		this.parseTree = parseTree;
		this.nfa = new FiniteAutomaton();
	}
	
	
	/**
	 * Simply calls build sub automaton for the root.
	 */
	public FiniteAutomaton generateNFA(){
		System.out.println("Generating nfa... ");
		nfa = buildSubAutomaton(parseTree.getRoot());
		return nfa;
	}
	
	
	/**
	 * performs in order traversal over the parseTree, and generates sub automatons for each operation in the regex.
	 * 
	 * @param currentNode the current node we are visiting.
	 */
	private FiniteAutomaton buildSubAutomaton(TreeNode currentNode){
		System.out.println("Generating subautomaton for " + currentNode.getValue() + " node.");
		FiniteAutomaton subAutomaton = new FiniteAutomaton();
		
		switch(currentNode.getValue()){
		
			case("<REGEX>"):{
				subAutomaton = buildRegex(currentNode);
			}
			
			break;
			
			case("<UNION>"):{
				subAutomaton = buildUnion(currentNode);
			}
			
			break;
			
			case("<STAR>"):{
				subAutomaton = buildStar(currentNode);
			}
			
			break;
			
			case("<CONCATENTATION>"):{
				subAutomaton = buildConcatenation(currentNode);
			}
			
			break;
			
			case("<GROUPING>"):{
				subAutomaton = buildGrouping(currentNode);
			}
			
			break;
			
			//its (hopefully a char)
			default:{
				if(!(currentNode.getToken() == null)){
					if(currentNode.getToken().isOfType(Token.TokenType.CHAR))
						subAutomaton = buildCharacter(currentNode);
				}
			}
		}
		
		return subAutomaton;
	}
	
	
	/**
	 * constructs the nfa for the regex. 
	 * 
	 * @param currentNode the current node of the tree we are on.
	 * 
	 * @return the NFA for the regex
	 */
	private FiniteAutomaton buildRegex(TreeNode currentNode){
		System.out.println("Building a regex automaton");
		if(currentNode.hasChildren()){
			
			//create a new automaton for the entire regex
			FiniteAutomaton regex = blankAutomaton();
			
			//build a subautomaton for the rest of the regex.
			FiniteAutomaton subAutomaton = buildSubAutomaton(currentNode.getChildren().get(0));
			
			//add epsilon transition from the regex start state to the sub's start state
			patchStartState(regex, subAutomaton);
			
			
			//add epsilon transitions from the subs accepting states to the new accepting state
			patchAcceptingStates(regex, subAutomaton);
			
			//add subautomatons states into the new automaton.
			regex.addStates(subAutomaton.getStates());
			
			return regex;
		}
		else{
			//if there was no children, then no regex was added as an argument.
			return null;
		}
	}
	
	/**
	 * Builds a union automaton
	 * @param currentNode
	 * @return
	 */
	private FiniteAutomaton buildUnion(TreeNode currentNode){
		System.out.println("Generating union sub");
		FiniteAutomaton union = blankAutomaton();
		
		//build a sub automaton for the left operand of union operator
		FiniteAutomaton leftOperand = buildSubAutomaton(currentNode.getChildren().get(0));
		
		//build a sub automaton for the right operand of union operator
		FiniteAutomaton rightOperand = buildSubAutomaton(currentNode.getChildren().get(1));
		
		//patch start states for left and right operands
		patchStartState(union, leftOperand);
		patchStartState(union, rightOperand);
		
		
		//patch accepting states for left and right operands
		patchAcceptingStates(union, leftOperand);
		patchAcceptingStates(union, rightOperand);
		
		//add the left and right operands states to the new union finite automaton.
		union.addStates(leftOperand.getStates());
		union.addStates(rightOperand.getStates());
		
		return union;	
	}
	
	/**
	 * builds a grouping automaton
	 * @param currentNode
	 * @return
	 */
	private FiniteAutomaton buildGrouping(TreeNode currentNode){
		System.out.println("Generating grouping automaton");
		//create a new automaton for the entire grouping
		FiniteAutomaton grouping = blankAutomaton();
		
		//build a subautomaton for the rest of the regex.
		FiniteAutomaton subAutomaton = buildSubAutomaton(currentNode.getChildren().get(0));
		
		//add epsilon transition from the regex start state to the sub's start state
		patchStartState(grouping, subAutomaton);
		
		//add epsilon transitions from the subs accepting states to the new accepting state
		patchAcceptingStates(grouping, subAutomaton);
		
		//add subautomatons states into the new automaton.
		grouping.addStates(subAutomaton.getStates());
		
		return grouping;
	}
	
	/**
	 * Constructs a star automaton
	 * @param currentNode
	 * @return a star automaton
	 */
	private FiniteAutomaton buildStar(TreeNode currentNode){
		System.out.println("Generating star automaton");
		//create a new automaton for the entire regex
		FiniteAutomaton star = blankAutomaton();
				
		//build a subautomaton for the rest of the regex.
		FiniteAutomaton subAutomaton = buildSubAutomaton(currentNode.getChildren().get(0));
				
		//add epsilon transition from the regex start state to the sub's start state
		patchStartState(star, subAutomaton);
				
		//add epsilon transitions from the subs accepting states to the new accepting state
		patchAcceptingStates(star, subAutomaton);
		
		//STAR
		//add epsilon transition from new accepting state to new start state
		star.getAcceptingStates().get(0).addTransition("epsilon", star.getStartState());
				
		//add subautomatons states into the new automaton.
		star.addStates(subAutomaton.getStates());
				
		return star;
		
	}
	
	
	/**
	 * constructs a concatenation automaton.
	 * @param currentNode
	 * @return a concatenation sub automaton
	 */
	private FiniteAutomaton buildConcatenation(TreeNode currentNode){
		System.out.println("building concatentation sub");
		//build a new blank automaton.
		FiniteAutomaton concatenation = blankAutomaton();
		
		//grab left and right concatenation operands.
		FiniteAutomaton leftConcat = buildSubAutomaton(currentNode.getChildren().get(0));
		
		FiniteAutomaton rightConcat = buildSubAutomaton(currentNode.getChildren().get(1));
		
		//add epsilon transitions from left operands accepting states to right operands start state
		for(State accepting: leftConcat.getAcceptingStates()){
			accepting.addTransition("epsilon", rightConcat.getStartState());
			//no longer accepting states
			accepting.setAcceptingState(false);
		}
		
		//add an epsilon transition from new start state to left operands start state.
		patchStartState(concatenation, leftConcat);
		
		//add epsilon transition from right operands accepting state to new accepting state.
		patchAcceptingStates(concatenation, rightConcat);
		
		
		//add left and right operands states to the new automaton.
		concatenation.addStates(leftConcat.getStates());
		concatenation.addStates(rightConcat.getStates());
		
		
		return concatenation;
	}
	
	private FiniteAutomaton buildCharacter(TreeNode currentNode){
		System.out.println("Generating character sub");
		FiniteAutomaton character = blankAutomaton();
		
		State startState = character.getStartState();
		State acceptingState = character.getAcceptingStates().get(0);
		
		//add the transition for this character
		startState.addTransition(currentNode.getValue(), acceptingState);
		
		
		return character;
	}
	
	
	/**
	 * Creates a blank automaton, with a start state, accepting state, but no transitions.
	 * @return the blank automaton.
	 */
	private FiniteAutomaton blankAutomaton(){
		System.out.println("creating a new blank shell automaton");
		FiniteAutomaton blankAutomaton = new FiniteAutomaton();
		blankAutomaton.createStartState();
		blankAutomaton.createAcceptingState();
		return blankAutomaton;
	}
	
	private void patchStartState(FiniteAutomaton supra, FiniteAutomaton sub){
		System.out.println("patching start states");
		//add an epsilon transition from the new start state to the old start state.
		supra.getStartState().addTransition("epsilon", sub.getStartState());
	}
	
	private void patchAcceptingStates(FiniteAutomaton supra, FiniteAutomaton sub){
		System.out.println("patching accepting states");
		//add an epsilon transition from the old accepting states to the new accepting state.
		for(State state: sub.getAcceptingStates()){
			//note, we know that the new regex only has a single accepting state, because we just built it.
			state.addTransition("epsilon", supra.getAcceptingStates().get(0));

			//make them no longer accepting states.
			state.setAcceptingState(false);

		}
	}
}
