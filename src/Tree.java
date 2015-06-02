package edu.csupomona.cs.cs241.prog_assgmnt_2;
/**
 * CS 241: Data Structures and Algorithms II
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #2
 *
 * <RED BLACK TREE>
 *
 * Oscar Nevarez
 */

public interface Tree<K extends Comparable<K>,V> {

		/** data  The nodes in the binary tree that contains the values 
		*
		* term count starts at 0, when an element is added it increased by one
		* when an element is removed it decreases by 1 
	    *                              
		*
		* inv 1 : A node is either red or black.
		* inv 2 : The root is black.
		* inv 3 : All leaves (NIL) are black
		* inv 4 : Every red node must have two black child nodes.
		* inv 5 : Every path from a given node to any of its descendant NIL nodes contains the same number of black nodes
		**/
			
		/** behavior
		*   pre  : A Value and Key must be supplied
		*   post : entry is added to the RBT
		*          
		*
		*
		*This method adds an entry to the RBT, a key and value are needed!
      	@param key The key for the entry that will be added.
	  	@param value The value for the entry that will be added in.
		**/
	  public void add(K key, V value);
	
	  
	  /** behavior
		*   pre  : A key must be supplied
		*   post : If an Entry with a matching key is found in the tree, it is removed and node counts decreases.
		*          
		*
		*
		*This method searches for an entry to removed based on the given key value.
    	@param key The key for the entry that will be removed.
    	@return returns the value of the node that was removed.
		**/
	  public V remove(K key);
	  
	  
	  /** behavior
		*   pre  : A key must be supplied
		*   post : If an Entry with a matching key is found in the tree, it value is returned.
		*          
		*
		*
		*This method searches for an entry and returns it value if present in the tree.
  		@param key The key for the entry that will be searched.
  		@return returns the value of the key that was passed in, if no matching key was found returns null.
		**/
	  public V lookup(K key);
	  
	  
	  
	  /** behavior
		*   post : returns a string representation of the RBT.
		*          
		*
		*
		*This method returns a string of the the RBT.
		@return Returns the RBT in string form.
		**/
	  public String toPrettyString();

}
