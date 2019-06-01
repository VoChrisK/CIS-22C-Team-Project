package sjsu.vo.cs146.project3;

/**
 * This interface creates an object to keep track of the nodes' keys. Mainly used for testing.
 * @author Chris Vo
 * @Version 1.0
 */
public interface Visitor<Key extends Comparable<Key>> {
		/**
		This method is called at each node.
		@param n the visited node
			*/
		void visit(RedBlackTree.Node<Key> n);  
	}