package datastructs;

import java.util.ArrayList;

/**
 * Class to represent nodes of the regex parse tree
 * @author Andrew
 *
 */


public class TreeNode {
	
	//--Fields--//
	
		//this nodes children
		private ArrayList<TreeNode> children;
		
		//this nodes parent node
		private TreeNode parent;
		
		//
		private boolean isLeafTreeNode;
		
		//face value of the node
		private String value;
		
		private Token token;
		
		
	
		
		
			
	//--Constructors--//
		
		/**
		 * Default Constructor
		 */
		public TreeNode(){
			this.children = new ArrayList<TreeNode>();
			this.parent = null;
		}
		
		/**
		 * @param parent this node's parent node
		 */
		public TreeNode(TreeNode parent){
			this.children = new ArrayList<TreeNode>();
			this.parent = parent;
		}
		
		
	//--Methods--//
		


		/**
		 * returns the parent node of this node
		 * @return TreeNode, the parent node of this node
		 * 
		 */
		public TreeNode getParent(){
			return this.parent;
		}
		
		/**
		 * sets the parent node of this node
		 * @param parent the parent node
		 */
		public void setParent(TreeNode parent){
			this.parent = parent;
		}
		
		
		/**
		 * 
		 * 
		 * @return true if the node has a parent, false otherwise
		 */
		public boolean hasParent(){
			if(this.parent == null){
				return false;
			}
			else{
				return true;
			}
			
		}
		
		/**
		 * 
		 * @return true if the node is a leaf node, false if otherwise
		 */
		public boolean isLeafTreeNode() {
			return isLeafTreeNode;
		}

		
		/**
		 * @param isLeafTreeNode set true if this node is a leaf node, false if otherwise
		 */
		public void setLeafTreeNode(boolean isLeafTreeNode) {
			this.isLeafTreeNode = isLeafTreeNode;
		}
		
		
		/**
		 * 
		 * @return the value of this node.
		 */
		public String getValue() {
			return this.value;
		}

		
		/**
		 * 
		 * @param value the node value to set
		 */
		public void setValue(String value) {
			this.value = value;
		}
		

		
		/**
		 * add a child node to this node.
		 * @param  node to add to this nodes children
		 * 
		 */
		public void addChild(TreeNode node){
			this.children.add(node);
		}
		
		/**
		 * 
		 * @return the child nodes of this node
		 */
		public ArrayList<TreeNode> getChildren() {
			return children;
		}
				
		/**
		 * 
		 * @return true if this node contains child nodes
		 */
		public boolean hasChildren(){
			return !(this.children.isEmpty());
		}
		
		
		/**
		 * @return the token
		 */
		public Token getToken() {
			return token;
		}

		/**
		 * @param token the token to set
		 */
		public void setToken(Token token) {
			this.token = token;
		}



}
