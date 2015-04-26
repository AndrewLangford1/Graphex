package datastructs;

import java.util.ArrayList;

public class FiniteAutomaton {
	
	private ArrayList<State> states;
	
	private State startState;	
	
	public FiniteAutomaton(){
		this.states = new ArrayList<State>();
		this.startState = null;
		
	}
	
	
	/**
	 * returns all of the states contained in this finite automaton
	 * 	
	 * @return the states in the FA
	 */
	public ArrayList<State> getStates(){
		return this.states;
	}
	
	public void addStates(ArrayList<State> statesToAdd){
		for(State state : statesToAdd){
			states.add(state);
		}
	}
	
	
	/**
	 * adds a state to the set of states.
	 * 
	 * @param state, the state to add to the set of states
	 */
	public void addState(State state){
		states.add(state);
	}
	
	/**
	 * Creates a start state and adds it to the set of states.
	 */
	public void createStartState(){
		State startState = new State();
		addState(startState);
		this.startState = startState;
	}
	
	public void createAcceptingState(){
		State acceptingState = new State();
		acceptingState.setAcceptingState(true);
		addState(acceptingState);
	}
	

	public State getStartState(){
		return this.startState;
	}
	
	
	/**
	 * 
	 * @return the array of accepting states.
	 */
	public ArrayList<State> getAcceptingStates(){
		ArrayList<State> accepting = new ArrayList<State>();
		for(State state : this.states){
			if(state.isAcceptingState()){
				accepting.add(state);
			}
		}
		
		return accepting;
	}
		
	public void printFiniteAutomaton(){
		System.out.println("Number of states => " + states.size());
		System.out.println("START STATE => ");
		this.startState.print();
		for(State state : states){
			System.out.println();
			System.out.println();
			if(state != startState){
				state.print();
			}
		}
	}
}
