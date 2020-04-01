## Stocks and Stuff

A four persons team project `CIS 22C - Intro to Data Structures` at `De Anza College`. This project stores and manages 
user-created stock data in various data structures: **Binary Search Tree**, **Hash Table**, and **Max Heap**. **Note**: 
This is one of the first projects I've done in college. This project was done in Spring 2014.

![intro](https://github.com/VoChrisK/Stocks-and-Stuff/blob/master/assets/stocks-and-stuff-intro.png)

## Background and Overview

Each data structure is written from scratch using C++. We implemented each ADT and respective functions in separate classes, and any additional functions that fit our project's theme. As with all ADT implmentations in C++, we need to have destructor functions to deallocate memory when we no longer use the data structures.

### Binary Search Tree

In our Binary Search Tree implementation, the nodes are lexicographically ordered based on their stocks' ticker values. We do not need to worry about choosing the optimal root at each insertion/deletion because since nodes are in lexicographical order and we have prepopulated data, we can choose any stock ticker that starts with either 'M' or 'N' as the root. In our remove function, we have three cases for removing a node:

1) The node is a leaf
2) The node has a single child
3) The node has two children

If the node is a leaf, we can simply remove it. But in other cases, we have to perform some extra measures. In case #2, if the node has
one child, then we can simply assign its _parent's_ child pointer to its child. In case #3, if the node has two children, then we either
replace the node with the smallest child in the right subtree or we would traverse the tree again to find suitable candidates to append
the node's children to.

![tree](https://github.com/VoChrisK/Stocks-and-Stuff/blob/master/assets/stocks-and-stuff-4.png)

### Hash Table

In our Hash Table implementation, we define a fixed amount of buckets (2D array of stock data structure pointers) and our own method to hash each data. To generate the hash key,
our hash function takes in the sum of each character's ASCII value in the stock ticker and mod it by 31. 

```c++
int HT::gethash(string key)
{
	int premod = 0;
	int length = key.length();

	for (int i = 0; i < length; i++)
		premod += key[i];

	int hash = premod % 31;
	return hash;
}
```

![bucket](https://github.com/VoChrisK/Stocks-and-Stuff/blob/master/assets/stocks-and-stuff-1.png)

We also have a stats function
where we keep track of and display the load factor, number of collisions, and number of full buckets. Whenever we delete an entry in a bucket,
we would move each other entries up by one.

![stats](https://github.com/VoChrisK/Stocks-and-Stuff/blob/master/assets/stocks-and-stuff-2.png)

### Max Heap

We used the Max Heap data structure to keep track of the most often searched stock item from our other data structures. 

![max heap](https://github.com/VoChrisK/Stocks-and-Stuff/blob/master/assets/stocks-and-stuff-3.png)

We implemented the
Max Heap using a vector of stock data pointers. Each stock data has an associated search counter and every time a stock is searched, we
would increment it by one. Thus, every time we search for a stock, we also insert it in the max heap and sort, if it exists, and resort
it at the same time. Here is a code snippet of the sorting function for our Max Heap implementation:

```c++
void MH::sort()
{
	bool done = false;
	for (int i = count / 2; i >= 1 && !done; i--)
	{
		int j = 2 * i;
		Stock_Data *temp = maxheap[i];
		while (j <= count)
		{
			if (j < count && maxheap[j + 1]->searchcount > maxheap[j]->searchcount)
				j = j + 1;
			if (temp->searchcount > maxheap[j]->searchcount)
			{
				j = count + 1;
				done = true;
			}
			else if (temp->searchcount <= maxheap[j]->searchcount)
			{																			//to check for more inconsistencies
				maxheap[j / 2] = maxheap[j];
				j = 2 * j;
			}
		}
		maxheap[j / 2] = temp;		//swap bigger child with smaller parent
	}
}
```

## Team Members
* [Chris Vo](https://www.linkedin.com/in/chris-vo-/)
* [David Babanezhad](https://www.linkedin.com/in/david-babanezhad-20a95a138/)
* [Michael Seo](https://www.linkedin.com/in/michaelseo/)
* Edward Medios Luna  
