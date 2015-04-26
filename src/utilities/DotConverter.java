package utilities;

import java.util.ArrayList;
import java.util.HashMap;

import datastructs.FiniteAutomaton;
import datastructs.State;

public class DotConverter {
		
	public static ArrayList<String> convertToDotFile(FiniteAutomaton fa){
		ArrayList<String> lines = new ArrayList<String>();
		//add the header
		lines.add("digraph FiniteAutomaton {");
		
		//add graph orientation direction
		lines.add("rankdir=LR;");
		
		//add an arrow from nowehere indicating the start point
		lines.add("node [shape = point ]; qi;");
		
		//TODO make this a single pass over the states. 
		
		//add all the nodes to the diagram.
		for(State state : fa.getStates()){
			if(state.isAcceptingState()){
				lines.add("node [shape = doublecircle]; " + state.getName() + ";");
			}
			else{
				lines.add("node [shape = circle]; " + state.getName() + ";");
			}
		}
		
		//iterate over all the states and add transitions
		for(State state: fa.getStates()){
			
			//if its the start state, draw a transition from nowehere to it.
			if(state == fa.getStartState()){
				lines.add("qi-> " + state.getName() + ";");
			}
			
			HashMap<String, ArrayList<State>> transitions = state.getTransitions();
			
			//add all of the transitions with appropriate labels to the graph
			for(String key : transitions.keySet()){
				for(State transitionState : transitions.get(key)){
					lines.add(  state.getName() + " -> " + transitionState.getName() + "[ label = \" " + key + "\" ]; " );
				}
			}
		}
	
		
		lines.add("}");
		
		return lines;
		
	}
	
	
}
