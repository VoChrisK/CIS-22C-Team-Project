/*==============================
MH.H: "MaxHeap Header + Implementation"
Max heap file for keeping track of most often searched
stock objects in an array.
==============================*/

#ifndef MH_H
#define MH_H

#include <iostream>
#include <vector>
#include "HT.h"
using namespace std;

class MH
{
private:
	vector<Stock_Data*> maxheap;
	int count;
	int numSearch;
public:
	MH();
	~MH();
	void displayTop();
	void insert(Stock_Data*);
	void deletion(string);
	void sort();
};

/*==============================
MH::MH
	Constructor.
==============================*/

MH::MH()
{
	maxheap.push_back(NULL);
	count = 0;
}

/*==============================
MH::~MH
	Destructor loop.
==============================*/

MH::~MH()
{
	for (int i = 0; i < count; i++)
        maxheap[i] = NULL;
	count = 0;
}

/*==============================
void MH::displayTop()
	Displays the most searched item. Shows all the fields of the data stock and
	the number of times it has been searched.
==============================*/
void MH::displayTop()
{
	if (count < 1)
		cout << endl << "No items have been searched." << endl;
	else
	{
		cout << endl << "MOST SEARCHED ITEM" << endl;
		cout
			<< "Ticker: " << maxheap[1]->ticker << endl
			<< "Name:   " << maxheap[1]->name << endl
			<< "Price:  " << maxheap[1]->value << endl
			<< "Volume: " << maxheap[1]->volume << endl
			<< "Searched " << maxheap[1]->searchcount << " times." << endl;
	}
}

/*==============================
void MH::insert(Stock_Data *)
	Inserts the stock data into the max heap array.
==============================*/
void MH::insert(Stock_Data *input)
{
	bool exists = false;		//checks if stock has already been searched
	bool done = false;			//secondary loop condition

	for (int i = 1; i <= count && !done; i++)
	{
		if (maxheap[i]->ticker == input->ticker)		//if the stock is already in

			exists = done = true;						//the vector, exit loop
	}

	if (exists)					//if the stock exists in the vector,
		sort();					//just re-sort and update the vector
	
	else
	{
		maxheap.push_back(input);		//if the stock is new to the vector,
		sort();						//add it to the end and re-sort
		count++;
	}
}

/*==============================
void MH::deletion(string)
	Deletes a index in the max heap array.
==============================*/
void MH::deletion(string target)
{
	//variable declarations
	bool done = false;


	for (int i = 1; i < count + 1 && !done; i++)
	{
		if (maxheap[i]->ticker == target)
		{
			delete maxheap[i];
			sort();
			maxheap.erase(maxheap.begin()+count);
			count--;
			done = true;
		}
	}
}

/*==============================
void MH::sort()
	Sorts the max heap array.
==============================*/
void MH::sort()
{
	bool done = false;
	for (int i = count / 2; i >= 1 && !done; i--)
	{
		int j = 2 * i;
		Stock_Data *temp = maxheap[i];
		while (j <= count)
		{
			if (j < count && maxheap[j + 1]->searchcount > maxheap[j]->searchcount)		//picks largest child
				j = j + 1;
			if (temp->searchcount > maxheap[j]->searchcount)							//if parent is bigger, loop is done
			{
				j = count + 1;
				done = true;
			}
			else if (temp->searchcount <= maxheap[j]->searchcount)						//if child is bigger, go further down the tree
			{																			//to check for more inconsistencies
				maxheap[j / 2] = maxheap[j];
				j = 2 * j;
			}
		}
		maxheap[j / 2] = temp;		//swap bigger child with smaller parent
	}
}

#endif