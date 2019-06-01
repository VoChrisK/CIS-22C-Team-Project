//Binary Search Tree Source File

#ifndef __practice_output__BST__
#define __practice_output__BST__

#include <iostream>
#include <string>
#include <cstdlib>
#include <iomanip>
#include <fstream>
#include "BST_Queue.h"
using namespace std;

struct Stock_Data
{
    string ticker;
    string name;
    double value;
    int volume;
	int searchcount;

	Stock_Data()
	{ 
		searchcount = 0; 
	}
};

class BinarySearchTree
{
private:
    struct tree_node
    {
        Stock_Data data;
        tree_node* left;
        tree_node* right;
    };
    tree_node* root;
 
    tree_node*_search(string value);
        
public:
    BinarySearchTree();
    
    ~BinarySearchTree();
    
    bool isEmpty() const {return root == NULL;}
    void print_inorder();
    void inorder(tree_node* p);
    //void print_preorder(); not used
    //void preorder(tree_node* p); not used
    //void print_postorder(); not used
    //void postorder(tree_node* p); not used
    //void print_BreadthFirst(); not used
    //void breadthFirst(tree_node* root); not used
    void insert(Stock_Data inputData);
    void BST_Destroy(tree_node* p);
    void print_tree(tree_node* node);
    void print_print_tree();
    void inorder_iterative(tree_node* root);
    bool Search_Tree(string searchTick, Stock_Data &tickerSearch);
    //void Range_Search(string smallString, string largeStriing, tree_node* root); not used
    //void print_Range_Search(string smallString, string largeString); not used
    bool remove(string targetValue);
	void _WriteFileData(string &);
	void WriteFileData(tree_node*, ofstream &);
    bool search(string);
};


#endif
