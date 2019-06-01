/*==============================
HashTable.H:
Header file for Hash Table class, used to sort
stocks read from an input file.
==============================*/

#ifndef HT_H
#define HT_H

#include "BST.h"

#include <iostream>
using namespace std;

class HT
{
private:
	static const int TABLE_LENGTH = 31;			//31 hash key sorting
	static const int TABLE_WIDTH = 3;			//max hash table depth of 3
	Stock_Data *bucket[TABLE_LENGTH][TABLE_WIDTH];	//2d array of struct pointers = hash table
	int fill[TABLE_LENGTH];						//number of buckets occupied for hash
	int total;									//total entries in hash table
public:
	HT();
	~HT();
	void insert(Stock_Data*);
	int gethash(string);
	Stock_Data *search(string);
	void print();
	void display();
	void stats();
	//bool isFull(int); not used
	bool deletion(string);
};

#endif