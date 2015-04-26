package datastructs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


/**
 * Class to represent states in a finite automaton.
 * 
 * @author Andrew
 *
 */
public class State {
	
	
//--Fields--//
	
	
	//whether or not this state is an accepting state
	private boolean isAcceptingState;
	private String name;
	
	
	//transition function for this state
	private HashMap<String, ArrayList<State>> transitions;

//--Constructors--//
	public State(){
		this.isAcceptingState = false;
		this.transitions = new HashMap<String, ArrayList<State>>();
		this.name = "q" + (int)(Math.random() * 10000);
	}
	
	public State(boolean isAcceptingState, boolean isStartState, HashMap<String, ArrayList<State>> transitions){
		this.isAcceptingState = isAcceptingState;
		this.transitions = transitions;
	}

	/**
	 * @return true if the state is accepting, false otherwise.
	 */
	public boolean isAcceptingState() {
		return isAcceptingState;
	}

	/**
	 * method to set whether or not this state is accepting
	 */
	public void setAcceptingState(boolean isAcceptingState) {
		this.isAcceptingState = isAcceptingState;
	}


	/**
	 * @return the transitions, the mappings of input characters to other states.
	 */
	public HashMap<String, ArrayList<State>> getTransitions() {
		return transitions;
	}

	/**
	 * @param transitions the transitions to set
	 */
	public void setTransitions(HashMap<String, ArrayList<State>> transitions) {
		this.transitions = transitions;
	}
	
	
	/**
	 * Adds the given state to its map key input character. if there is no transition for the input character, we make a new entry 
	 * in the transitions table.
	 * 
	 * @param inputString the key 
	 * @param state the state to add to the key
	 */
	public void addTransition(String inputString, State state){
		if(transitions.containsKey(inputString)){
			transitions.get(inputString).add(state);
		}
		else{
			ArrayList<State> states = new ArrayList<State>();
			states.add(state);
			transitions.put(inputString, states);
		}
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	
	public void print(){
		String toPrint = " /--STATE--/ \n";
		toPrint += "ID => " + this.getName() +  "\n";
		toPrint  += "Accepting State => " + this.isAcceptingState + "\n";
		toPrint += "/--Transitions--/ \n";
		for(String key : transitions.keySet()){
			for(State state : transitions.get(key)){
				toPrint += key + " => " + state.getName() + "\n";
			}
		}
		
		System.out.println(toPrint);
	}
	
}
