package datastructs;



public class Tree {
		
	//--Fields--//	
		
		//root node of the tree
		private TreeNode root;
		
		//current node
		private TreeNode currentTreeNode;

		
		
	//--Constructors--//`
		
		/**
		 * default constructor
		 */
		public Tree(){
			this.root = null;
			this.currentTreeNode = null;
		}
		
		
		/**
		 * Constructor that takes a node
		 * @param root  the node that will be the root of the tree
		 */
		public Tree(TreeNode root){
			this.root = root;
			this.currentTreeNode = root;
			
		}
		
		
	//--Methods--//
		
		
		/**
		 * Set the root node of the tree
		 * @param node the node to be the root of the tree
		 * 
		 */
		public void setRoot(TreeNode node){
			this.root = node;
		}
		
		
		/**
		 * @return the root node of the tree
		 */
		public TreeNode getRoot(){
			return this.root;
		}
		
		
		/**
		 *@return the current node we are looking at
		 */
		public TreeNode getCurrent(){
			return this.currentTreeNode;
		}
		
		
		/**
		 * set the current node we want to look at 
		 *
		 */
		public void setCurrent(TreeNode node){
			this.currentTreeNode = node;	
		}
		
		
		/**
		 * set the current node to the current node's parent
		 */
		public void returnToParent(){
			if(!(currentTreeNode.getParent() == null)){
				this.currentTreeNode = currentTreeNode.getParent();
			}
		}
		
		
		/**
		 * Add a branch node to this tree as a child of the current node.  
		 * note: all branch nodes are non terminals in this grammar for the CST
		 *boolop and intop are branch nodes in the AST
		 * @param0 value the value of this node 
		 */
		public void addBranchTreeNode(String value){
					
			//create a new node
			TreeNode branchTreeNode = new TreeNode();
			branchTreeNode.setValue(value);
			branchTreeNode.setLeafTreeNode(false);
			
			//if there's no root node, make this node the root
			if(this.root == null){
				this.root = branchTreeNode;
			}
			
			//if we aren't root, add ourselves to our parent's children and update current to us.
			else{
				//set this new nodes parent
				branchTreeNode.setParent(this.currentTreeNode);
				
				//add this node to parent's children
				this.currentTreeNode.addChild(branchTreeNode);
				
			}
		
			//current node is now new branchnode
			this.currentTreeNode = branchTreeNode;
		}
			
		
		/**
		 * Add a branch node to this tree as a child of the current node.  
		 * note: all branch nodes are non terminals in this grammar.
		 * @param value the value of this node , token
		 */
		public void addBranchTreeNode(String value, Token token){
					
			//create a new node
			TreeNode branchTreeNode = new TreeNode();
			branchTreeNode.setValue(value);
			branchTreeNode.setLeafTreeNode(false);
			branchTreeNode.setToken(token);
			
			//if there's no root node, make this node the root
			if(this.root == null){
				this.root = branchTreeNode;
			}
			
			//if we aren't root, add ourselves to our parent's children and update current to us.
			else{
				//set this new nodes parent
				branchTreeNode.setParent(this.currentTreeNode);
				
				//add this node to parent's children
				this.currentTreeNode.addChild(branchTreeNode);
				
			}
		
			//current node is now new branchnode
			this.currentTreeNode = branchTreeNode;
		}
		

		/**
		 * @
		 * Add a leaf node to the tree as a child of the current node.
		 * @param value the value of this node
		 * @param token the token
		 * note all leaf nodes are terminals in the grammar.
		 */
		public void addLeafTreeNode(String value, Token token){
			//create a new node
			TreeNode leafTreeNode = new TreeNode();
			
			//set the title of this node for printing purposes
			leafTreeNode.setValue(value);
			leafTreeNode.setToken(token);
			leafTreeNode.setLeafTreeNode(true);
			
			if(this.root == null){
				//TODO should raise an error
			}
			
			//if we aren't root, add ourselves to our parent's children
			else{
				//set this new nodes parent
				leafTreeNode.setParent(this.currentTreeNode);
				
				//add this node to parent's children
				this.currentTreeNode.addChild(leafTreeNode);
		
			}
		}
		
		
		
		
		/**
		 * @
		 * Add a leaf node to the tree as a child of the current node.
		 * @param value the value of this node
		 * @param token the token
		 * note all leaf nodes are terminals in the grammar.
		 */
		public void addLeafTreeNode(String value){
			//create a new node
			TreeNode leafTreeNode = new TreeNode();
			
			//set the title of this node for printing purposes
			leafTreeNode.setValue(value);
			leafTreeNode.setLeafTreeNode(true);
			
			if(this.root == null){
				//TODO should raise an error
			}
			
			//if we aren't root, add ourselves to our parent's children
			else{
				//set this new nodes parent
				leafTreeNode.setParent(this.currentTreeNode);
				
				//add this node to parent's children
				this.currentTreeNode.addChild(leafTreeNode);
		
			}
		}
			
		
		/**
		 * Simply calls the printLevel method
		 * 
		 */
		public void print(){
			try {
				printLevel(this.getRoot(), 0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error printing");
			}
		}
			
		/**
		 * prints the tree out in a nested tree fashion.
		 * @param node the node to start at
		 * @param level the level of the tree
		 */
		private void printLevel(TreeNode node, int level){
			try {
				String toPrint = "";
				for(int i =0; i<level; i++){
					toPrint += "  ";
				}
				System.out.println(toPrint+ node.getValue());
				if(node.getChildren().isEmpty()){
					return;
				}
				else{
					level += 1;
					for(TreeNode x : node.getChildren()){
						this.printLevel(x, level);
					}
				}
			} catch (Exception e) {
				System.out.println("Error printing tree at level " + level + " at node " + node.getValue());
				e.printStackTrace();
			}
		}	
}
