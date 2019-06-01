// Binary Search Tree Implementation File

#include <iostream>
#include <iomanip>
#include "BST.h"
#include "BST_Queue.h"


/**~*~*
Constructor
This function sets the root of the tree to 0 and the count to 0
 *~**/
BinarySearchTree::BinarySearchTree()
{
    // Set the root equal to NULL
    root = NULL;
}

 
 /**~*~*
 Destructor
 This function calls a recursive function to delete all nodes in the binary tree
 *~**/
BinarySearchTree::~BinarySearchTree()
{
    // If the root is not NULL, call the destroy function to delete the tree
    if (root)
        BST_Destroy(root);
}

/**~*~*
BST_Destroy
This function traverses the binary tree in postorder and deletes every node
 *~**/
void BinarySearchTree::BST_Destroy(tree_node *root)
{
    // If the root is not NULL, delete the data to its left and right
    if (root)
    {
        BST_Destroy(root->left);
        BST_Destroy(root->right);
        delete root;
    }
    return;
}

/**~*~*
 insert
 This function inserts data from the file into the BST
 *~**/
void BinarySearchTree::insert(Stock_Data inputData)
{
    // Declare pointers for traversing
    tree_node* newNode;
    tree_node* pWalk;
    tree_node* parent;
    
    // Create a new node
    newNode = new tree_node;
    
    // Set the data of the new node equal to the input data
    newNode->data = inputData;

    // Initialize the left and right values to NULL
    newNode->left = NULL;
    newNode->right = NULL;
    
    // If this is a new tree, set the node to the first piece of data
    if(!root)
        root = newNode;
    
    // Insert to the left or right
    else
    {
        // ALL insertions are as leaf nodes
        pWalk = root;
        //tree_node* curr;
        //curr = root;
        
        // Find the Node's parent
        while(pWalk)
        {
            parent = pWalk;
            
            if(inputData.ticker > pWalk->data.ticker)
                pWalk = pWalk->right;
            
            else
                pWalk = pWalk->left;
        }
        
        // If the new data is less than the parent, insert to the left
        if(inputData.ticker < parent->data.ticker) // Change back to ticker
            parent->left = newNode;
        
        // Else insert to the right
        else
            parent->right = newNode;
    }
}

/**~*~*
 print_inorder
 This function calls the inorder the function and is used in main to print
 *~**/
void BinarySearchTree::print_inorder()
{
    inorder(root);
}

/**~*~*
 inorder
 This function shows the inorder traversal of the tree using recursion
 *~**/
void BinarySearchTree::inorder(tree_node* p)
{
    if(p == NULL)
        p = root;
    
    // Print the data inorder by going from the left node to the root node to the right node
    if(p != NULL)
    {
        if(p->left) inorder(p->left);
        cout << "\n" << p->data.ticker;
        cout << endl << p->data.name;
        cout << endl << p->data.value;
        cout << endl << p->data.volume;
        cout << endl;
        if(p->right) inorder(p->right);
    }
    else return;
}

/**~*~*
 print_print_tree
 This calls the print_tree function and is used in main to print
 *~**/
void BinarySearchTree::print_print_tree()
{
    // Calls the print function
    print_tree(root);
}

/**~*~*
 print_tree
 This function prints out the contents of the tree with indents
 *~**/
void BinarySearchTree::print_tree(tree_node* node)
{
    static int indent = 0; 

    // If node is not NULL
    if(node)
    {
        // Print the contents of the right node using recursion
        if(node->right)
        {
            indent++;
            print_tree(node->right);
            indent--;
        }
        
        // Add indents to print the tree
        for(int i = 0; i < indent; i++)
        {
            cout << "        ";
        }
        
        cout << (indent+1) << ". " << node->data.ticker << endl;
        
        // Print the contents of the left node using recursion
        if(node->left)
        {
            indent++;
            print_tree(node->left);
            indent--;
        }
    }
}

/**~*~*
 Search a BST for a given target: if found, returns true and passes back
 data, otherwise returns false. It calls the private _search to locate the node.
 *~**/
bool BinarySearchTree::Search_Tree(string searchTick, Stock_Data &tickerSearch)
{
    // Declare a pointer and set it equal to the value from the _search function
    tree_node* found = _search(searchTick);
    
    // If found, set the tickerSearch equal to the data of the object
    if(found)
    {
        tickerSearch = found->data;
        return true;
    }
    
    // If not found return false
    return false;
}

bool BinarySearchTree::search(string searchTick)
{
    // Declare a pointer and set it equal to the value from the _search function
    tree_node* found = _search(searchTick);
    
    // If found, set the tickerSearch equal to the data of the object
    if(found)
    {
        
        return true;
    }
    
    // If not found return false
    return false;
}


/**~*~*
 Locates the node that contains a given target in a BST:
 - if found returns a pointer to that node
 - if not found returns NULL
 *~**/
BinarySearchTree::tree_node* BinarySearchTree::_search(string value)
{
    // If there is no root return NULL
    if(!root)
        return NULL;
    
    // Declare a new pointer and set it equal to the root
    tree_node* pWalk = root;
    
    while(pWalk)
    {
        // If the value is less that the current data, move left
        if( value < pWalk->data.ticker )
            pWalk = pWalk->left;
        
        // If the data is not less than the current data
        else
        {
            // If the data is greater than the current data, move right
            if( value > pWalk->data.ticker)
                pWalk = pWalk->right;
            
            // If the data is not greater than or less than the current, it is equal
            else
                return pWalk; // found
        }
    }
    
    return NULL;
}

/**~*~*
 This function removes a node from the tree. 
 *~**/
bool BinarySearchTree::remove(string targetValue)
{
    //Locate the element
    bool found = false;
    if(isEmpty())
    {
        cout<<"This Tree is empty! "<<endl;
        return false;
    }
    
    tree_node* curr;
    tree_node* parent;
    curr = root;
    
    while(curr != NULL)
    {
        if(curr->data.ticker == targetValue)
        {
            found = true;
            break;
        }
        else
        {
            parent = curr;
            
            if(targetValue > curr->data.ticker)
                curr = curr->right;
            
            else curr = curr->left;
        }
    }
    
    if(!found)
        return false;
 
    
    
    // 3 cases :
    // 1. We're removing a leaf node
    // 2. We're removing a node with a single child
    // 3. we're removing a node with 2 children
    
    // Node with single child
    if((curr->left == NULL && curr->right != NULL) || (curr->left != NULL && curr->right == NULL))
    {
        if(curr->left == NULL && curr->right != NULL)
        {
            if(parent->left == curr)
            {
                parent->left = curr->right;
                delete curr;
            }
            else
            {
                parent->right = curr->left;
                delete curr;
            }
        }
        return true;
    }
    
    //We're looking at a leaf node
    if( curr->left == NULL && curr->right == NULL)
    {
        if(parent->left == curr)
        {
            parent->left = NULL;
        }
        
        else
        {
            parent->right = NULL;
        }
        
        delete curr;
        return true;
    }
    
    
    //Node with 2 children
    // replace node with smallest value in right subtree
    if (curr->left != NULL && curr->right != NULL)
    {
        tree_node* chkr;
        chkr = curr->right;
        
        if((chkr->left == NULL) && (chkr->right == NULL))
        {
            curr = chkr;
            delete chkr;
            curr->right = NULL;
        }
        
        else // right child has children
        {
            //if the node's right child has a left child
            // Move all the way down left to locate smallest element
            
            if((curr->right)->left != NULL)
            {
                tree_node* lcurr;
                tree_node* lcurrp;
                lcurrp = curr->right;
                lcurr = (curr->right)->left;
                while(lcurr->left != NULL)
                {
                    lcurrp = lcurr;
                    lcurr = lcurr->left;
                }
                curr->data = lcurr->data;
                delete lcurr;
                lcurrp->left = NULL;
            }
            
            else
            {
                tree_node* tmp;
                tmp = curr->right;
                curr->data = tmp->data;
                curr->right = tmp->right;
                delete tmp;
            }
            
        }
        
    }
    return true;
}

/**~*~*
Writes data to a .txt file. User inputs name of the 
file to be outputted to.
 *~**/
void BinarySearchTree::_WriteFileData(string &file)
{
	//variable declarations
	ofstream outFile;
	string extension(".txt");

	file += extension;

	outFile.open(file.c_str());
	
	WriteFileData(root, outFile);

	return;
}

/**~*~*
Writes data to a .txt file. Data is written in inorder.
 *~**/
void BinarySearchTree::WriteFileData(tree_node* p, ofstream &outFile)
{
    if(p == NULL)
        p = root;
    
    // Print the data inorder by going from the left node to the root node to the right node
    if(p != NULL)
    {
        if(p->left) WriteFileData(p->left, outFile);
        outFile << " " << p->data.ticker << endl;
		outFile << " " << p->data.name << endl;
		outFile << " " << p->data.value << endl;
		outFile << " " << p->data.volume << endl << endl;
        if(p->right) WriteFileData(p->right, outFile);
    }
    else return;
}