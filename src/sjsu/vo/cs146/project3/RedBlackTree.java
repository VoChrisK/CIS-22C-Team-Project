package sjsu.vo.cs146.project3;

/**
 * This class constructs a Red Black Tree data structure. This class also maintains the tree's
 * properties by setting appropriate colors of the nodes when adding a new node to the tree.
 * Lastly, it contains other methods for printing the tree and search for a specific node.
 * @author Chris Vo
 * @Version 1.0
 */
public class RedBlackTree<Key extends Comparable<Key>> {	
	//class variable declarations
	private static RedBlackTree.Node<String> root; //the root of the tree, initially null
	
	/**
	* This method prints the contents of the current node
	* @param n  the current node in the tree
	* @return none
	*/
	public void visit(Node<Key> n) {
		System.out.println(n.key);
	}
		
	/**
	* This method sets the current node to root and call the method to print each node in preorder
	* tree walk
	* @param none
	* @return none
	*/
	public void printTree() {  //preorder: visit, go left, go right
		RedBlackTree.Node<String> currentNode = root;	
		printTree(currentNode);
	}
	
	/**
	* This method prints the nodes recursively in pre-order tree walk
	* @param node  the current node in the tree to be printed
	* @return none
	*/
	public void printTree(RedBlackTree.Node<String> node) {
		if(node == null)
			return;
		
		System.out.print(node.key);
	
		printTree(node.leftChild);
		printTree(node.rightChild);
	}
	
	/**
	* This method properly inserts a new node into the tree. The method follows the
	* binary search tree concept and place the new node based on the value. Afterwards,
	* it calls fixNode method to maintain the red black tree's properties 
	* @param data  the data to be added in the new node
	* @return none
	*/
	public void addNode(String data) {  	//this < that  <0.  this > that  >0
		//method variable declarations
		RedBlackTree.Node<String> pointer1 = null; //to point the parent of the new node
		RedBlackTree.Node<String> pointer2 = root; //to point where the new node be placed at
		RedBlackTree.Node<String> newnode = new RedBlackTree.Node<String>(data); //a new node to be added
		
		while(pointer2 != null) {
			pointer1 = pointer2; 
			if(newnode.key.compareTo(pointer2.key) < 0)
				pointer2 = pointer2.leftChild;
			else
				pointer2 = pointer2.rightChild;
			newnode.parent = pointer1;
		}
		
		if(pointer1 == null)
			root = newnode;
		else if(newnode.key.compareTo(pointer1.key) < 0)
			pointer1.leftChild = newnode;
		else
			pointer1.rightChild = newnode;
		newnode.leftChild = null;
		newnode.rightChild = null;
		newnode.isRed = true;
		newnode.color = 1;
		fixTree(newnode);
	}

	/**
	* This method calls the insertion method of the red black tree
	* @param data  the data to be added in the new node
	* @return none
	*/
	public void insert(String data) {
		addNode(data);	
	}
	
	/**
	* This method searches for a specific value in the tree
	* @param data 			 the data to be added in the new node
	* @return boolean value  true if the key is found in the tree, false otherwise
	*/
	public boolean lookup(String k) { 
		RedBlackTree.Node<String> search = root;
		
		while(search != null) {
			if(k.equals(search.key))
				return true;
			if(k.compareTo(search.key) < 0)
				search = search.leftChild;
			else
				search = search.rightChild;
		}
		
		return false;
	}
		
	/**
	* This method returns the sibling (the other node of the parent) of the node
	* @param n  the node to return its sibling
	* @return Node value  returns the sibling, null otherwise
	*/
	public RedBlackTree.Node<String> getSibling(RedBlackTree.Node<String> n) {  
		if(n.equals(n.parent.leftChild))
			return n.parent.rightChild;
		else
			return n.parent.leftChild;
	}
		
	/**
	* This method returns the aunt (the parent's sibling) of the node
	* @param n  the node to return its aunt
	* @return Node value  returns the aunt, null otherwise
	*/
	public RedBlackTree.Node<String> getAunt(RedBlackTree.Node<String> n) {
		if(n.parent.equals(n.parent.parent.leftChild))
			return n.parent.parent.rightChild;
		else
			return n.parent.parent.leftChild;
	}
		
	/**
	* This method returns the grandparent (the parent's parent) of the node
	* @param n  the node to return its grandparent
	* @return Node value  returns the grandparent, null otherwise
	*/
	public RedBlackTree.Node<String> getGrandparent(RedBlackTree.Node<String> n) {
		return n.parent.parent;
	}
		
	/**
	* This method rotates a given node's subtree to the left
	* @param n  the node to be rotated to the left
	* @return none
	*/
	public void rotateLeft(RedBlackTree.Node<String> n) {
		RedBlackTree.Node<String> pointer = n.rightChild;
		n.rightChild = pointer.leftChild;
		if(pointer.leftChild != null)
			pointer.leftChild.parent = n;
		pointer.parent = n.parent;
		if(n.parent == null)
			root = pointer;
		else if(n == n.parent.leftChild)
			n.parent.leftChild = pointer;
		else
			n.parent.rightChild = pointer;
		pointer.leftChild = n;
		n.parent = pointer;
	}
		
	/**
	* This method rotates a given node's subtree to the right
	* @param n  the node to be rotated to the right
	* @return none
	*/
	public void rotateRight(RedBlackTree.Node<String> n) {
		RedBlackTree.Node<String> pointer = n.leftChild;
		n.leftChild = pointer.rightChild;
		if(pointer.rightChild != null)
			pointer.rightChild.parent = n;
		pointer.parent = n.parent;
		if(n.parent == null)
			root = pointer;
		else if(n == n.parent.rightChild)
			n.parent.rightChild = pointer;
		else
			n.parent.leftChild = pointer;
		pointer.rightChild = n;
		n.parent = pointer;
	}
	
	/**
	* This method maintains the red black tree's properties by recursively and correctly
	* coloring the nodes in the tree, starting with the new node.
	* @param current  the node that was recently added in the tree
	* @return none
	*/
	public void fixTree(RedBlackTree.Node<String> current) {
		if(current == root) {
			current.isRed = false;
			return;
		}
		
		if(current.parent.isRed == false)
			return;
		else {
			if(current.parent.equals(current.parent.parent.leftChild)) {
				RedBlackTree.Node<String> node = current.parent.parent.rightChild;
				if(node == null)
					node = new RedBlackTree.Node<String>("0"); //create a dummy node to prevent null pointer exception errors, the color will always be black
				if(node.isRed == false) { //case A
					if(current.equals(current.parent.rightChild)) {
						rotateLeft(current.parent);
						fixTree(current.parent);
					}
					else { //case C
						current.parent.isRed = false;
						current.parent.parent.isRed = true;
						rotateRight(current.parent.parent);
						return;
					}
				}
				else if(node.isRed = true) {
					current.parent.isRed = false;
					node.isRed = false;
					current.parent.parent.isRed = true;
					fixTree(current.parent.parent);
				}
			}
			else { //if parent is the right child of grandparent
				RedBlackTree.Node<String> node = current.parent.parent.leftChild;
				if(node == null)
					node = new RedBlackTree.Node<String>("0"); //create a dummy node to prevent null pointer exception errors, the color will always be black
				if(node.isRed == false) { //case B
					if(current.equals(current.parent.leftChild)) {
						rotateRight(current.parent);
						fixTree(current.parent);
					}
					else { //case D
						current.parent.isRed = false;
						current.parent.parent.isRed = true;
						rotateLeft(current.parent.parent);
						return;
					}
				}
				else if(node.isRed = true) {
					current.parent.isRed = false;
					node.isRed = false;
					current.parent.parent.isRed = true;
					fixTree(current.parent.parent);
				}
			}
		}
		
		return;
	}
		
	public boolean isEmpty(RedBlackTree.Node<String> n){
		if (n.key == null) {
			return true;
		}
		return false;
	}
		 
	public boolean isLeftChild(RedBlackTree.Node<String> parent, RedBlackTree.Node<String> child) {
		if (child.compareTo(parent) < 0 ) {//child is less than parent
			return true;
		}
		return false;
	}

	public void preOrderVisit(Visitor<String> v) {
		preOrderVisit(root, v);
	}
		 	 
	private static void preOrderVisit(RedBlackTree.Node<String> n, Visitor<String> v) {
		if (n == null)
			return;
		
		System.out.print(n.key);
		
		v.visit(n);
		preOrderVisit(n.leftChild, v);
		preOrderVisit(n.rightChild, v);
	}	
		
	
	public static class Node<Key extends Comparable<Key>> { //changed to static 
		//class variable declarations
		Key key;  		  
		Node<String> parent;
		Node<String> leftChild;
		Node<String> rightChild;
		boolean isRed;
		int color;
				  
		public Node(Key data) {
			this.key = data;
			leftChild = null;
			rightChild = null;
			isRed = false;
		}		
				  
		public int compareTo(Node<Key> n) { 	//this < that  <0
		    return key.compareTo(n.key);  	//this > that  >0
		}
				  
		public boolean isLeaf() {
			if (this.equals(root) && this.leftChild == null && this.rightChild == null) return true;
			if (this.equals(root)) return false;
			if (this.leftChild == null && this.rightChild == null) {
				return true;
			}
			return false;
		}
	}

		public boolean isLeaf(RedBlackTree.Node<String> n) {
			if (n.equals(root) && n.leftChild == null && n.rightChild == null) return true;
			if (n.equals(root)) return false;
			if (n.leftChild == null && n.rightChild == null){
				return true;
			}
			return false;
		}
	}