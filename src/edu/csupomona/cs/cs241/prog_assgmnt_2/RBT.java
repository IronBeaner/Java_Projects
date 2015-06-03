package edu.csupomona.cs.cs241.prog_assgmnt_2;

import java.util.ArrayList;
enum Color{RED,BLACK,DUMMY};

public class RBT<K extends Comparable<K>, V> implements Tree<K,V> {
	protected int numberOfNodes=0;
	protected Node root=null;
	
	@Override
	public void add(K key, V value) {
		//case 0
		if(root==null){
			root=new Node(key,value,Color.BLACK);
			root.leftChild=new Node(Color.BLACK);
			root.leftChild.parent=root;
			root.rightChild=new Node(Color.BLACK);
			root.rightChild.parent=root;
			numberOfNodes+=3;
		}
		else{
			Node operatingNode=traverseTreeInsert(key);
			if(!operatingNode.isLeaf()&&operatingNode.key.equals(key)){
				System.out.println("A node with key: "+key+" is already in the tree! Its value has been updated!");
				operatingNode.value=value;
				return;
			}
			else{
				operatingNode.setInsertedNode(new Node(key,value,Color.RED));
				operatingNode.leftChild.parent=operatingNode;
				operatingNode.rightChild.parent=operatingNode;
				System.out.println("Adding node: "+operatingNode.key+ " its color is "+operatingNode.color);
				System.out.println("Its parent is "+operatingNode.parent);
				System.out.println("its left Child is "+ operatingNode.leftChild);
				System.out.println("its right Child is "+operatingNode.rightChild);
		//case 1
				insertCase0(operatingNode);
				root.color=Color.BLACK;//put here for extra safety measures!
				numberOfNodes+=2;
			}
		}
		System.out.println(numberOfNodes+ " Nodes In The Tree"+"\n");   
	}
	 
	
	
	
	/** behavior
	*   post : The number of Nodes in the tree is returned.
	*         
	*
	*
	*This method returns the number of nodes in the Red Black Tree.
	@return returns the number of nodes in the tree.
	**/
	public int numOfNodes(){
		return numberOfNodes;
	}
	private void insertCase0(Node operatingNode){
		 if (operatingNode.parent == null)
		        operatingNode.color = Color.BLACK;
		 else
			 insertCase1(operatingNode);
	}
	private void insertCase1(Node operatingNode){
		if(operatingNode.parent().color==Color.BLACK){
			System.out.println("No violation");
			return;
		}
		else
			insertCase2(operatingNode);
	}
	private void insertCase2(Node operatingNode){
		if(operatingNode.uncle().color==Color.RED){
			System.out.println("Case 2 triggered");
			operatingNode.parent().color=Color.BLACK;
			operatingNode.uncle().color=Color.BLACK;
			operatingNode.grandParent().color=Color.RED;
			insertCase0(operatingNode.grandParent());
		}
		else
		insertCase3(operatingNode);
	}
	private void insertCase3(Node operatingNode){
		if(operatingNode.parent().color ==Color.RED&&operatingNode.uncle().color==Color.BLACK&&internalRightNode(operatingNode)){
			System.out.println("Case 3 triggered");
			System.out.println("Trying to do a right rotate");
			rightRotate(operatingNode.parent());
			insertCase4(operatingNode.rightChild);
		}
		else if(operatingNode.parent().color ==Color.RED&&operatingNode.uncle().color==Color.BLACK&&internalLeftNode(operatingNode)){
			System.out.println("Case 3 triggered");
			System.out.println("Trying to do a left rotate");
			leftRotate(operatingNode.parent());
			insertCase4(operatingNode.leftChild);
		}
		else
		insertCase4(operatingNode);
	}
	private void insertCase4(Node operatingNode){
		System.out.println("Case 4 triggered");
		operatingNode.parent().color=Color.BLACK;
		operatingNode.grandParent().color=Color.RED;
		if(externalRightNode(operatingNode)&&operatingNode.uncle().color==Color.BLACK){
				System.out.println("Trying to do a left rotate");
				leftRotate(operatingNode.grandParent());
		}
		if(externalLeftNode(operatingNode)==true&&operatingNode.uncle().color==Color.BLACK){
			System.out.println("Trying to do a right rotate");
			rightRotate(operatingNode.grandParent());
		}
	}
	@Override
	public V remove(K key) {
		Node operatingNode=root;
		boolean keyHasNotBeenFound=true;
		V temp=null;
		System.out.println("Trying to Find a node with key "+key);
		while(!operatingNode.isLeaf()&&keyHasNotBeenFound){
			if(key.compareTo(operatingNode.key)==-1){
				if(operatingNode.leftChild!=null){
					operatingNode=operatingNode.leftChild;
					System.out.println("moved left");
				}
			}
			else if(key.compareTo(operatingNode.key)==1){
				if(operatingNode.rightChild!=null){
					operatingNode=operatingNode.rightChild;
					System.out.println("moved right");
				}
			}
			if(operatingNode.key==null){
				System.out.println("Key was not found!");
				break;
			}
			else if(operatingNode.key.equals(key)){
				keyHasNotBeenFound=false;
				temp=operatingNode.value;
				removeCase0(operatingNode);
			}	
		
		}
		return temp;
	}
	private void removeCase0(Node operatingNode){
		System.out.println("Found "+operatingNode);
		if (!operatingNode.leftChild.isLeaf()&&!operatingNode.rightChild.isLeaf()){
		        // Copy key/value from predecessor and then delete it instead
		        Node successor = inOrderSuccessor(operatingNode);
		        operatingNode.key=successor.key;
		        operatingNode.value=successor.value;
		        operatingNode=successor;
		        System.out.println("found its inorder Successor,"+" it is "
		        +successor+" will continue to check on this node and its child.");
		    }
		Node child=null;
		if(operatingNode.leftChild.isLeaf()||operatingNode.rightChild.isLeaf()){
			System.out.println("trying to get "+operatingNode+" non leaf Child");
		  		if(operatingNode.rightChild.isLeaf())
		  			child=operatingNode.leftChild;
		  		else
		  			child=operatingNode.rightChild;
		  		
		  		System.out.println("its non leaf child is: "+child);
		  		if(operatingNode.color==Color.BLACK){
		  			operatingNode.color=child.color;
		  			removeCase1(operatingNode);
		  		}
		}
		replaceNode(operatingNode, child);
	}
	private void removeCase1(Node operatingNode){
		if (operatingNode.parent == null){
			System.out.println("remove case 1 triggered");
	        return;
		}
	    else
	        removeCase2(operatingNode);
	}
	private void removeCase2(Node operatingNode){
		if(operatingNode.sibling().color==Color.RED){
			 operatingNode.parent.color=Color.RED;
			 operatingNode.sibling().color=Color.BLACK;
			if (operatingNode == operatingNode.parent.leftChild){
				System.out.println("remove case 2 triggered");
				System.out.println("trying to rotate left on "+operatingNode.parent());
				leftRotate(operatingNode.parent());
			}
			else{
				System.out.println("remove case 2 triggered");
				System.out.println("trying to rotate left on "+operatingNode.parent());
				rightRotate(operatingNode.parent());
			}
		}
       
		removeCase3(operatingNode);
	}
	private void removeCase3(Node operatingNode){
		if( operatingNode.parent().color==Color.BLACK&&operatingNode.sibling().color==Color.BLACK&&(operatingNode.sibling().leftChild.color==Color.BLACK
				&&operatingNode.sibling().rightChild.color==Color.BLACK)){
			System.out.println("remove case 3 triggered, will recurse on parent.");
			operatingNode.sibling().color=Color.RED;
			removeCase1(operatingNode.parent);
		}
		else if(operatingNode.parent().color==Color.RED&&operatingNode.sibling().color==Color.BLACK
				&&operatingNode.sibling().leftChild.color==Color.BLACK&&
				operatingNode.sibling().rightChild.color==Color.BLACK){
			System.out.println("remove case 3 triggered");
			operatingNode.sibling().color=Color.RED;
			operatingNode.parent().color=Color.BLACK;
		}
		else 
			removeCase4(operatingNode);
	}
	private void removeCase4(Node operatingNode){
		if(operatingNode.parent.leftChild==operatingNode&&operatingNode.sibling().color==Color.BLACK
				&&operatingNode.sibling().leftChild.color==Color.RED&&operatingNode.sibling().rightChild.color==Color.BLACK){
			System.out.println("remove case 4 triggered");
			System.out.println("trying to rotate left on "+ operatingNode.sibling());
			operatingNode.sibling().color=Color.RED;
			operatingNode.leftChild.color=Color.BLACK;
			leftRotate(operatingNode.sibling());
			removeCase5(operatingNode);
		}			
		if(operatingNode.parent.rightChild==operatingNode
				&&operatingNode.sibling().color==Color.BLACK
				&&operatingNode.sibling().rightChild.color==Color.RED
				&&operatingNode.sibling().leftChild.color==Color.BLACK){
			System.out.println("remove case 4 triggered");
			System.out.println("trying to rotate right on "+ operatingNode.sibling());
			operatingNode.sibling().color=Color.RED;
			operatingNode.rightChild.color=Color.BLACK;
			rightRotate(operatingNode.sibling());
			removeCase5(operatingNode);
		}
		else
			removeCase5(operatingNode);
	}
	private void removeCase5(Node operatingNode){
		if(operatingNode.parent.leftChild==operatingNode&&
				operatingNode.sibling().color==Color.BLACK&&externalRightNode(operatingNode.sibling().rightChild)){
			if(operatingNode.sibling().rightChild.color==Color.RED){
				System.out.println("remove case 5 triggered");
				System.out.println("trying to rotate left on "+operatingNode.parent());
			operatingNode.sibling().color=operatingNode.parent().color;
			operatingNode.parent().color=Color.BLACK;
			operatingNode.sibling().rightChild.color=Color.BLACK;
			leftRotate(operatingNode.parent());
			}
			
		}
		else if(operatingNode.parent.rightChild==operatingNode&&
				operatingNode.sibling().color==Color.BLACK&&externalLeftNode(operatingNode.sibling().leftChild)){
			if(operatingNode.leftChild.color==Color.RED){
				operatingNode.sibling().color=operatingNode.parent().color;
				operatingNode.parent().color=Color.BLACK;
				operatingNode.sibling().rightChild.color=Color.BLACK;
				System.out.println("remove case 5 triggered");
				System.out.println("trying to rotate right on "+operatingNode.parent());
				operatingNode.sibling().color=Color.BLACK;
				rightRotate(operatingNode.parent);
			}
		}
	}
	  
	
	
	/** behavior
	*   pre  : The right Child of the node who's inorder successor you want to find is passed in.
	*   post : the in order successor of the given nodes parent is returned.
	*          
	*
	*
	*This method rotates nodes right in respect to the supplied pivot.
	@param operatingNode  The right Child of the node who's in order successor you want to find 
	@return returns the in order successor of the given node parent.
	**/
	private Node inOrderSuccessor(Node operatingNode){
		operatingNode=operatingNode.rightChild;
		 if(operatingNode!= null);
		    while (!operatingNode.leftChild.isLeaf()) {
		        operatingNode=operatingNode.leftChild;
		    }
		    return operatingNode;
	}
	@Override
	public V lookup(K key) {
		Node operatingNode=root;
		while(!operatingNode.isLeaf()){
			if(key.compareTo(operatingNode.key)==-1){
				if(operatingNode.leftChild!=null){
					operatingNode=operatingNode.leftChild;
					System.out.println("moved left");
				}
			}
			else if(key.compareTo(operatingNode.key)>0){
				if(operatingNode.rightChild!=null){
					operatingNode=operatingNode.rightChild;
					System.out.println("moved right");
				}
			}
			if(operatingNode.isLeaf())
				break;
			else if (key.equals(operatingNode.key))
				return operatingNode.value;
		}
		return null;
	}
	  
	 
	
	/** behavior
		*   pre  : A key must be supplied
		*   post : A suitable location where the new entry should be stored is returned
		*          
		*
		*
		*This method searches for the right place to store and entry and it returns it.
		@param key The key that will be used to determine where the new entry should go.
		@return returns the leaf where the new entry should be added.
		**/
	private Node traverseTreeInsert(K key){
		Node operatingNode=root;
		while(!operatingNode.isLeaf()){
			if(!operatingNode.isLeaf()&&operatingNode.key.equals(key))
				return operatingNode;
			else if(key.compareTo(operatingNode.key)==-1){
				if(operatingNode.leftChild!=null)
				operatingNode=operatingNode.leftChild;
			}
			else if(key.compareTo(operatingNode.key)>=0){
				if(operatingNode.rightChild!=null)
				operatingNode=operatingNode.rightChild;
			}
		}
		return operatingNode;
	}
	  
	
	
	/** behavior
			*   pre  : A key must be supplied
			*   post : If a node is found with the same specified key it is returned else null is returned
			*          
			*
			*
			*This method searches for an entry with the given key, if found it returns it else it returns null.
			@param key The key that will determine what entry to look for.
			@return returns the entry that matches the given key if its in the tree, else null is returned.
			**/
	private Node traverseTreeRemove(K key){
		Node operatingNode=root;
		while(!operatingNode.isLeaf()){
			if(key.compareTo(operatingNode.key)==-1){
				if(operatingNode.leftChild!=null)
				operatingNode=operatingNode.leftChild;
			}
			if(key.compareTo(operatingNode.key)>=0){
				if(operatingNode.rightChild!=null)
				operatingNode=operatingNode.rightChild;
			}
			if(key.equals(operatingNode.key))
				return operatingNode;
		}
		return null;
	}
	
	private void rightRotate(Node operatingNode){
		Node left=operatingNode.leftChild;
		replaceNode(operatingNode,left);
		operatingNode.leftChild=left.rightChild;
		if(left.rightChild!=null){
			left.rightChild.parent=operatingNode;
		}
		left.rightChild=operatingNode;
		operatingNode.parent=left;
	}
	  
	
	
	/** behavior
	*   pre  : A pivot node must be supplied
	*   post : Nodes have rotated around the pivot point in a left direction.
	*          
	*
	*
	*This method rotates nodes left in respect to the supplied pivot.
	@param operatingNode the pivot node
	**/
	private void leftRotate(Node operatingNode){
		Node rightOfPivotNode=operatingNode.rightChild;
		replaceNode(operatingNode,rightOfPivotNode);
		operatingNode.rightChild=rightOfPivotNode.leftChild;
		if(rightOfPivotNode.leftChild!=null){
			rightOfPivotNode.leftChild.parent=operatingNode;
		}
		rightOfPivotNode.leftChild=operatingNode;
		operatingNode.parent=rightOfPivotNode;
	}
	 
	
	
	/** behavior
	*   pre  : A pivot node must be supplied
	*   post : Nodes have rotated around the pivot point in a right direction.
	*          
	*
	*
	*This method makes newNode the new parent of pointOfRotation.
	@param pointOfRotation the pivot Node.
	@param newNode the node that will be in the position of pointOfRotation
	**/
	private void replaceNode(Node pointOfRotation, Node newNode){
		if (pointOfRotation.parent == null) 
	        root = newNode;
	    else {
	        if (pointOfRotation == pointOfRotation.parent.leftChild)
	        	pointOfRotation.parent.leftChild = newNode;
	        else
	        	pointOfRotation.parent.rightChild = newNode;
	    }
	    if (newNode != null)
	        newNode.parent = pointOfRotation.parent;
	}
	 
	
	
	
	
	/** behavior
	*   pre  : A node must be supplied
	*   post : Whether the passed in node is an external right Node or not is known.
	*          
	*
	*
	*This method checks if the passed in node is an external right node.
	@param OperatingNode the node that will be checked.
		@return returns whether the given node is an external node on the right side of the tree
	**/
	private boolean externalRightNode(Node operatingNode){
		if(operatingNode.parent().rightChild==operatingNode&&operatingNode.grandParent().rightChild==operatingNode.parent())
			return true;
		else 
			return false;		
	}
	 
	
	
	
	/** behavior
		*   pre  : A node must be supplied
		*   post : Whether the passed in node is an external left Node or not is known.
		*          
		*
		*
		*This method checks if the passed in node is an external left node.
		@param OperatingNode the node that will be checked.
		@return returns whether the given node is an external node on the left side of the tree
		**/
	private boolean externalLeftNode(Node operatingNode){
		if(operatingNode.parent().leftChild==operatingNode&&operatingNode.grandParent().leftChild==
				operatingNode.parent())
			return true;
		else 
			return false;
	}
	 
	
	
	
	
	/** behavior
	*   pre  : A node must be supplied
	*   post : Whether the passed in node is an internal left Node or not is known.
	*          
	*
	*
	*This method checks if the passed in node is an internal left node.
	@param OperatingNode the node that will be checked.
	@return returns whether the given node is an internal node on the left side of the tree
	**/
	private boolean internalLeftNode(Node operatingNode) {
		if(operatingNode.parent().rightChild==operatingNode&&
				operatingNode.grandParent().leftChild==operatingNode.parent())
			return true;
		else 
			return false;
	}
	 
	
	
	/** behavior
	*   pre  : A node must be supplied
	*   post : Whether the passed in node is an internal right Node or not is known.
	*          
	*
	*
	*This method checks if the passed in node is an internal right node.
	@param OperatingNode the node that will be checked.
	@return returns whether the given node is an internal node on the right side of the tree
	**/
	private boolean internalRightNode(Node operatingNode){
		if(operatingNode.parent().leftChild==operatingNode&&
				operatingNode.grandParent().rightChild==operatingNode.parent())
			return true;
		else
		return false;	
	}
	@Override
	public String toPrettyString() {
		if(numberOfNodes==0)
			return null;
		
		int levelsOfTree=(int) Math.ceil(Math.log(numberOfNodes)/Math.log(2)+1);
		int maximumNodesInTree=(int) Math.pow(2,levelsOfTree)-1;
		
		Node movingNode=root;
		ArrayList<Node> queue= new ArrayList<Node>();
		ArrayList<String> list= new ArrayList<String>();
		queue.add(movingNode);
		for(int x=0;x<maximumNodesInTree;x++){
			movingNode=queue.remove(0);
				if(movingNode.leftChild!=null)
					queue.add(movingNode.leftChild);
				if(movingNode.leftChild==null)
					queue.add(new Node(Color.DUMMY));//flag if node is missing add a dummy to queue
				if(movingNode.rightChild!=null)
					queue.add(movingNode.rightChild);
				if(movingNode.rightChild==null)
					queue.add(new Node(Color.DUMMY));//flag
				
				if(movingNode.color==Color.DUMMY)
					list.add("[XXXXXXX]"); //space
				else
					list.add(movingNode.toString());
		}
		//store the nodes in an array first level of the array will hold 1 node 
		//second level, 2 nodes etc.
		String[] tree=new String[levelsOfTree];
		for(int x=0;x<levelsOfTree;x++){
				tree[x]="";
			for(int y=(int)Math.pow(2, x);y>0;y--){
				tree[x]+=list.remove(0);
			}
		}
		int maximumRequiredSpaces=(int)Math.pow(2, levelsOfTree-1);
		for(int x=0;x<tree.length;x++){
			int spacesToConcat=maximumRequiredSpaces-(int)Math.pow(2, x);
			for(int y=0;y<spacesToConcat;y++){
				if(y==0)
					tree[x]="  "+tree[x];
				else
					tree[x]="    "+tree[x];
			}
		}
		String paddedTree="";
		for(int x=0;x<tree.length;x++)
			paddedTree+=tree[x]+"\n";
		
		return paddedTree;
	}
	

	private class Node{
		protected Node leftChild,rightChild,parent;
		protected Color color;
		protected V value;
		protected K key;
		
		public Node(K key, V value,Color nodeColor){
			this.color=nodeColor;
			this.value=value;
			this.key=key;
			this.leftChild=new Node(Color.BLACK);
			this.rightChild=new Node(color.BLACK);
		}
		public Node(Color nodeColor){
			this.color=nodeColor;
			this.value=null;
			this.key=null;
		}
		//returns the nonleaf child of the attached node, if it exists.
		public Node nonLeafChild(){
			if(this.leftChild.isLeaf())
				return this.rightChild;
			else 
				return this.leftChild;
		}
		//sets the attached nodes fields to that of the inserted Node, this is only used
		//when adding
		public void setInsertedNode(Node insertedNode){
			this.key=insertedNode.key;
			this.value=insertedNode.value;
			this.leftChild=insertedNode.leftChild;
			this.rightChild=insertedNode.rightChild;
			this.color=insertedNode.color;
			System.out.println("Setting inserted Node its parent is "+this.parent);
			System.out.println("its sibling is "+this.sibling());
			System.out.println("its sibling's parent is "+this.sibling().parent);
		}
		//returns the grand parent of the attached node.
		public Node grandParent(){
			if(this.parent!=null&&this.parent.parent!=null)
				return this.parent.parent;
			else 
				return null;
		}
		//returns the uncle of the attached node
		public Node uncle(){
			if(this.parent().sibling()!=null)
				return this.parent().sibling();
			else 
				return null;
		}
		//returns the parent of the attached node, REDUNDANT!
		public Node parent(){
			if(this.parent!=null)
				return this.parent;
			else
				return null;
		}
		//returns the sibling of the attached node
		public Node sibling(){
			  assert parent != null; // Root node has no sibling
			    if (this == parent.leftChild)
			        return parent.rightChild;
			    else
			        return parent.leftChild;
		}
		//return whether the attached node is a leaf or not
		public boolean isLeaf(){
			if(this.color==Color.BLACK&&this.key==null&&this.value==null)
				return true;
			else 
				return false;
		}
		public String toString(){
			if(this.key==null)
				return "[N N B]";
			if(this.color==Color.BLACK)
				return "["+key+" "+value+" B]";
			else 
				return "["+key+" "+value+" R]";
		}
	}
}
