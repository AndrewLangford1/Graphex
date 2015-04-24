package datastructs;

import java.util.ArrayList;

public class AbstractTree extends Tree{

//--Fields--//
	
	//the concrete regex parse tree we want to convert to a more abstract version.
	Tree concreteTree;

//--Constructors--//
	
	
	/**
	 * Main constructor
	 * 
	 * @param concreteTree the concrete regex parse tree we want to convert
	 */
	public AbstractTree(Tree concreteTree){
		super();
		this.concreteTree = concreteTree;
	}
	
	
	/**
	 * Converts the concrete regex tree into a simpler tree suitible for NFA construction.
	 */
	public void convertTree(){
		abstractSubtree(concreteTree.getRoot());
		
	}
	
	
	/**
	 * Abstracts the subrees of the concrete tree.
	 * 
	 * @param branchNode the current node of the concrete tree
	 */
	private void abstractSubtree(TreeNode currentNode){
		switch(currentNode.getValue()){
		
			case("<REGEX>"):{
				abstractRegularExpression(currentNode);
			}
			
			break;
			
			case("<regex>"):{
				abstractRegex(currentNode);
				
				
			}
			
			break;
			
			case("<term>"):{
				abstractTerm(currentNode);
				
				
			}
			
			break;
			
			case("<factor>"):{
				abstractFactor(currentNode);
				
				
			}
			
			break;
			
			case("<base>"):{
				
				abstractBase(currentNode);
				
				
			}
			
			break;
			
			case("<char>"):{
				abstractChar(currentNode);
			
			}
			
			break;
			
			default:{
				
				
			}
		}
	}
	
	
	/**
	 * abstracts the entire regex
	 * @param currentNode
	 */
	private void abstractRegularExpression(TreeNode currentNode){
		if(currentNode.hasChildren()){
			abstractSubtree(currentNode.getChildren().get(0));
		}
	}
	
	
	/**
	 * abstracts a sub regex of the overall regex.
	 * @param currentNode
	 */
	private void abstractRegex(TreeNode currentNode){
		addBranchTreeNode("<Regex>");
		if(currentNode.hasChildren()){
			ArrayList<TreeNode> children = currentNode.getChildren();
			
			//if the number of children in this regex is greater than 1, we know that is is a union of a term
			//and another regex.
			if(children.size() > 1){
				
				//add a UNION branch node
				addBranchTreeNode("<UNION>");
				
				//head towards the left term.
				abstractSubtree(children.get(0));
				
				//head towards the right regex
				abstractSubtree(children.get(2));
				
				returnToParent();
			}
			
			//there is just one term.
			else{
				abstractSubtree(children.get(0));
			}
		}
		
		returnToParent();
	}
	
	
	/**
	 * Abstracts a term.
	 * @param currentNode
	 */
	private void abstractTerm(TreeNode currentNode){
		
		if(currentNode.hasChildren()){
			ArrayList<TreeNode> children = currentNode.getChildren();
			
			//if the number of children in this term is greater than one, we know that there is a concatenation between a factor and another term.
			if(children.size() > 1){
				
				//add a CONCAT branch node. since there is no nonterminal or terminal that denotes CONCAT, just make a placeholder.
				addBranchTreeNode("<CONCATENTATION>");
				
				//head towards the left factor
				abstractSubtree(children.get(0));
				
				//head towards the right term.
				abstractSubtree(children.get(1));
				
				returnToParent();
			}
			
			//there is just one factor
			else{
				abstractSubtree(children.get(0));
			}
		}
	}
	
	/**
	 * Abstracts a factor
	 * @param currentNode
	 */
	private void abstractFactor(TreeNode currentNode){
		if(currentNode.hasChildren()){
			ArrayList<TreeNode> children = currentNode.getChildren();
			
			//if the number of children in this term is greater than one, we know that a base is being starred
			if(children.size() > 1){
				
				//head towards the STAR operator to make it a branch node.
				this.addBranchTreeNode("<STAR>");
				
				
				//head towards the base that is being STARRED
				abstractSubtree(children.get(0));
				
				returnToParent();
			}
			
			//there is just a base.
			else{
				abstractSubtree(children.get(0));
			}
		}	
	}
	
	
	/**
	 * Abstracts a base
	 * @param currentNode
	 */
	private void abstractBase(TreeNode currentNode){
		if(currentNode.hasChildren()){
			ArrayList<TreeNode> children = currentNode.getChildren();
			
			//if the number of children in this term is greater than one, we know there is a grouping.
			if(children.size() > 1){
				
				//going to throw out the () characters because they aren't needed in nfa construction.
				this.addBranchTreeNode("<GROUPING>");
				
				
				//head towards the regex that is being STARRED
				abstractSubtree(children.get(1));
				
				returnToParent();
			}
			
			//there is just a char
			else{
				abstractSubtree(children.get(0));
			}
		}
	}
	
	
	/**
	 * Abstracts a char
	 * @param currentNode
	 */
	private void abstractChar(TreeNode currentNode){
		if(currentNode.hasChildren()){
			TreeNode node = currentNode.getChildren().get(0);
			this.addLeafTreeNode(node.getValue(), node.getToken());
		}
	}

}
