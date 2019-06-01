
/*

CIS 22C Spring 2014
Team Project

$tocks and $tuff

Programers
Chris Vo: Team Leader
Edward Medios Luna: Hash Algorithm
Michael Seo: Max Heap
David Babanezhad: Binary Search Tree Algorithm

*/


#include "BST.h"
#include "HT.h"
#include "MH.h"
#include <cctype>

//function prototypes
bool GetFile(ifstream &);
void ReadData(ifstream &, BinarySearchTree &, HT &);
void MenuInterface(BinarySearchTree &, HT &, MH &, ofstream &);

//subfunction prototypes
void MenuOptions();
void AddData(BinarySearchTree &, HT &);
void DeleteData(BinarySearchTree &, HT &, MH &);
void SearchWithKey(BinarySearchTree &, HT &, MH &);
void DisplayDataAsHashTableSequence(HT &);
void DisplayDataAsKeySequence(BinarySearchTree &);
void PrintIndentedTree(BinarySearchTree &);
void OutData(BinarySearchTree &, ofstream &);
void HashStatistics(HT &);
void DisplayMostFrequentItem(MH &);

int main()
{
	//variable declarations
	ifstream inFile;
	ofstream outFile;
	BinarySearchTree StockBST;
	HT StockHT;
	MH StockMH;

	//function to check if the Team Project file is found or not
	if(!(GetFile(inFile))) //if file is not found, end the program by returning 1
		return 1;

	//function to read the data from the file and insert the data into the BST and Hash Table
	ReadData(inFile, StockBST, StockHT);

	//function to display the menu interface along with the entirety of its options. Subfunctions are handled in this function
	MenuInterface(StockBST, StockHT, StockMH, outFile);

	system("pause");

	return 0;
}

/*
Purpose: To check if the Team Project file is found (returns true) or not (returns false)
Input Parameters: ifstream inFile
Return Value: boolean value (either true or false)
*/
bool GetFile(ifstream &inFile)
{
	inFile.open("StockInput.txt");

	if(inFile.fail())
	{
		cout << "File not found!" << endl;
		system("pause");
		return false;
	}

	cout << "File has been found!" << endl;

	return true;
}

/*
Purpose: Read the data from the file, store each data into a struct, and insert the struct into the BST and Hash Table before the menu interface is called from main
Input Parameters: ifstream inFile, Stock of type pass-by-referenced BinarySearchTree (class)
Return Value: none
*/
void ReadData(ifstream &inFile, BinarySearchTree &StockBST, HT & StockHT)
{
	cout << "\nNow processing data from the file into the BST and Hash Table.\n" << endl;

	//variable declarations
	Stock_Data *Stocks;

	//while loop to read data from file into the BST and Hash Table
	while(!inFile.eof()) //while inFile does not reach to the end of the file
	{
		Stocks = new Stock_Data;
		getline(inFile, Stocks->ticker);
		getline(inFile, Stocks->name);
		inFile >> Stocks->value;
		inFile.ignore();
		inFile >> Stocks->volume;
		inFile.ignore();
		StockHT.insert(Stocks);
		StockBST.insert(*Stocks); //insert the Stock_Data structure into the BST while the loop persists
    }

	cout << "Data has been successfully implemented into the BST and Hash Table.\n" << endl;

	return;
}

/*
Purpose: To display the menu-driven interface along with its options in order to interact with the data structures (BST and Hash Table)
Input Parameters: Stock of type pass-by-referenced BinarySearchTree (class)
Return Value: none
*/
void MenuInterface(BinarySearchTree &StockBST, HT &StockHT, MH &StockMH, ofstream &outFile)
{
	//variable declaration
	string choice;

	//header
	cout << "\t\t************************************************" << endl;
	cout << "\t\t*   Welcome to Stocks & Stuff - Team Project   *" << endl;
	cout << "\t\t************************************************" << endl;

	//shows the options for the menu at least once
	MenuOptions();

	//do-while loop to continuously run the menu until the user inputs 'Q'
	do
	{

	cout << "\nEnter a choice (0, 1, 2, 3, 4, 5, 6, 7, 8, 9): ";
	cin >> choice;
	
		while(choice.length() > 1)// input validation
		{
		cout << "Enter a valid choice! (0, 1, 2, 3, 4, 5, 6, 7, 8, 9): ";
		cin >> choice;
		}

	//switch function along with multiple case statements to organize the menu interface; the user's input will execute one of these options
	switch(toupper(choice[0]))
	{
	case '1':
		AddData(StockBST, StockHT); //subfunction to insert new data into the BST and Hash Table
		break;
	case '2':
		DeleteData(StockBST, StockHT, StockMH); //subfunction to delete existing data from the BST and Hash Table
		break;
	case '3':
		SearchWithKey(StockBST, StockHT, StockMH); //subfunction to search for a specified key from the BST and Hash Table
		break;
	case '4':
		DisplayDataAsHashTableSequence(StockHT); //subfunction to display data in hash table
		break;
	case '5':
        DisplayDataAsKeySequence(StockBST); //subfunction to display data in key sequence
		break;
	case '6':
		PrintIndentedTree(StockBST); //subfunction to print the BST as an indented list/tree
		break;
	case '7':
		OutData(StockBST, outFile); //subfunction to output the entire data/displayed output into a file
		break;
	case '8':
		HashStatistics(StockHT); //subfunction to print out hash statistics
		break;
	case '9':
		DisplayMostFrequentItem(StockMH); //subfunction to print out most searched item
		break;
	case 'M':
		cout << endl;
		MenuOptions(); //subfunction to display the menu again in accordance to the user's input
		break;
	case '0':
		cout << "Quitting the menu interface." << endl;
		OutData(StockBST, outFile);
		break;
	default: //default to indicate if the user did not input the correct choices
		cout << "You did not input the right choices for this menu! Please try again." << endl;
	}

	//if loop to repeat the indicated message until the user inputs '0'
	if(toupper(choice[0]) != '0')
	{
        cout << endl;
		cout << "____________________________________" << endl;
		cout << "\nEnter M to open up the menu options" << endl;
	}

	} while(toupper(choice[0]) != '0'); //while the user does not input '0'

	return;
}

/*
Purpose: To display the list of menu options for the user to input
Input Parameters: none
Return Value: none
*/
void MenuOptions()
{
	cout << "\tEnter a choice:" << endl;
	cout << "\t1 - Add new data" << endl;
	cout << "\t2 - Delete data" << endl;
	cout << "\t3 - Search with primary key" << endl;
	cout << "\t4 - List data in hash table sequence" << endl;
	cout << "\t5 - List data in key sequence (sorted)" << endl;
	cout << "\t6 - Print indented tree" << endl;
	cout << "\t7 - Write data to file" << endl;
	cout << "\t8 - Hash statistics" << endl;
	cout << "\t9 - Display the most frequently searched item" << endl;
	cout << "\t0 - Quit menu and program" << endl;

	return;
}

/*
Purpose: To prompt the user to add new data into the BST and Hash Table
Input Parameters: Stock of type pass-by-referenced BinarySearchTree (class)
Return Value: none
*/
void AddData(BinarySearchTree &StockBST, HT &StockHT)
{
	//variable declarations
	Stock_Data *Stocks;
	Stocks = new Stock_Data;
	string inValue = " ";
	bool validNum = true;

	//header
	cout << "\n-------------------------------------------------------------------" << endl;
	cout << "This option prompts the user to add new data to the BST and the Hash Table." << endl;
	cout << "Data includes: ticker, company name, price, volume" << endl << endl;

	cout << "Enter new stock information:" << endl;
	cout << "Type 'QUIT' to return to choices." << endl << endl; 
	cout << "Enter the stock ticker: ";
	
	cin.ignore();
	getline(cin, Stocks->ticker);
	
	// Sets ticker to uppercase
	for (int i = 0; i < (int)Stocks->ticker.length(); i++)
		Stocks->ticker[i] = toupper(Stocks->ticker[i]); 

	// User does not want to add new data.
    if (Stocks->ticker == "QUIT")
		return;

	// Checks if already in structures
    if(StockBST.search(Stocks->ticker))
    {
        cout << "Already in BST and HASH!!!" << endl;
        return;
    }
	
	cout << "Enter the stock company: ";
	getline(cin, Stocks->name);
	
	// User does not want to add new data.
	if (Stocks->name == "QUIT")
		return;

	cout << "Enter the stock price: ";
	//cin >> Stocks->value;
	cin >> inValue;
	
	// User chooses wrong choice and wants to quit.
    if (inValue == "QUIT")
		return;

	// Validates input value. (Must be a number)
	do
	{
		validNum = true;
		for (int i = 0; i < (int)inValue.length(); i++)
		if (isalpha(inValue[i]))
			validNum = false;
		if (!validNum)
		{
			cout << "Value must be a number. Enter the stock price: ";
			cin >> inValue;
		}
	}while (!validNum);

	// Sets value in stocks struct
	Stocks->value = stof(inValue);

	cout << "Enter the stock volume: ";
	//cin >> Stocks->volume;
	cin >> inValue;
	
	// User chooses wrong choice and wants to quit.
    if (inValue == "QUIT")
		return;
	// Validates input value (Must be a number)
	do
	{
		validNum = true;
		for (int i = 0; i < (int)inValue.length(); i++)
		if (isalpha(inValue[i]))
			validNum = false;
		if (!validNum)
		{
			cout << "Value must be a number. Enter the stock volume: ";
			cin >> inValue;
		}
	}while (!validNum);

	Stocks->volume = stoi(inValue);

	StockBST.insert(*Stocks); //calls the insertion member function of the BST
	StockHT.insert(Stocks); //calls the insertion member function of the Hash Table

	cout << "Insertion of the new stock has been completed!" << endl;

	return;
}

/*
Purpose: To prompt the user to delete data from the BST and Hash Table
Input Parameters: Stock of type pass-by-referenced BinarySearchTree (class)
Return Value: none
*/
void DeleteData(BinarySearchTree &StockBST, HT &StockHT, MH &StockMH)
{
	//variable declarations
	string Key;

	//header
	cout << "\n-------------------------------------------------------------------" << endl;
	cout << "This option prompts the user to delete a stock data from the BST and the" << endl;
	cout << "Hash Table. This option only prompts the user to input the ticker to be" << endl;
	cout << "deleted. The rest of the data regarding to the deleted stock will be deleted" << endl;
	cout << "alongside with the ticker when the member function for removal is called.\n" << endl;

	cout << "Enter the stock ticker: ";
	cin.ignore();
	getline(cin, Key);

	//if/else statement to 
	if(StockBST.remove(Key) && StockHT.deletion(Key)) //if the member function returns true
	{
		cout << Key << " has been successfully deleted in the BST and Hash Table!" << endl;
		StockMH.deletion(Key);
	}
	else //else if the member function returns false
		cout << Key << " is not found or the BST and Hash Table are empty!" << endl;

	return;
}

/*
Purpose: To prompt the user to input data to be searched from the BST and Hash Table
Input Parameters: Stock of type pass-by-referenced BinarySearchTree (class)
Return Value: none
*/
void SearchWithKey(BinarySearchTree &StockBST, HT &StockHT, MH &StockMH)
{
	//variable declarations
	Stock_Data *Stocks;
	Stock_Data *Temp_Stocks;
	string Key;

	Stocks = new Stock_Data;
	Temp_Stocks = new Stock_Data;

	//header
	cout << "\n-------------------------------------------------------------------" << endl;
	cout << "This option searches for a user-specified key in the BST and Hash Table." << endl;
	cout << "This only prompts the user to input the ticker to be searched for." << endl;
	cout << "The rest of the data, if found, will be saved to the same structure variable" << endl;
	cout << "as a pass-by-referenced variable along with the stock ticker.\n" << endl;

	cout << "Enter the stock ticker to be searched: ";
	cin.ignore();
	getline(cin, Key);

	cout << "Now searching the stock ticker in the BST." << endl;

	Temp_Stocks = StockHT.search(Key);

	//if/else statement to
	if(StockBST.Search_Tree(Key, *Stocks) && Temp_Stocks) //if the member function returns true
			StockMH.insert(Temp_Stocks);
	else //else if the member function returns false
		cout << Key << " cannot be found in the BST nor Hash Table!" << endl;

	return;
}

/*
Purpose: To print the entire Hash Table in Hash Table Sequence. All of the contents in each bucket, including the empty ones, are displayed
Input Parameters: StockHT of type pass-by-referenced HT (class)
Return Value: none
*/
void DisplayDataAsHashTableSequence(HT &StockHT)
{
	cout << "\n-------------------------------------------------------------------" << endl;
	cout << "This option prints the entire Hash Table in Hash Table Sequence." << endl;
	cout << "In other words, each content in each bucket will be displayed respectively," << endl;
	cout << "whether each content is empty or not.\n" << endl;

	StockHT.print();

	return;
}

/*
Purpose: To print the entire Hash Table in Key Sequence. The keys are only displayed in a list-like output
Input Parameters: StockHT of type pass-by-referenced HT (class)
Return Value: none
*/
void DisplayDataAsKeySequence(BinarySearchTree &StockBST)
{
	cout << "\n-------------------------------------------------------------------" << endl;
	cout << "This option prints the entire Hash Table in Key Sequence. In other words," << endl;
	cout << "only the contents in each bucket containing the keys will be printed out in" << endl;
	cout << "list order; the empty contents are excluded.\n" << endl;
	
	StockBST.print_inorder();

	return;
}

/*
Purpose: To print the entire BST as an indented list. Its nodes are displayed level by level respectively
Input Parameters: Stock of type pass-by-referenced BinarySearchTree (class)
Return Value: none
*/
void PrintIndentedTree(BinarySearchTree &StockBST)
{
	//header
	cout << "\n-------------------------------------------------------------------" << endl;
	cout << "This option prints the entire BST as an indented list." << endl;
	cout << "In other words, the BST will be displayed level by level respectively.\n" << endl;

	StockBST.print_print_tree(); //calls upon a recursive member function to print the indented tree

	return;
}

/*
Purpose: To print the all the data to a output file. Prints the data in order.
Return Value: none
*/
void OutData(BinarySearchTree &StockBST, ofstream &outFile)
{
	//variable declarations
	string file;

	//header
	cout << "\n-------------------------------------------------------------------" << endl;
	cout << "This options prompts the user to input a file name. This file will output" << endl;
	cout << "the entire data." << endl;

	cout << "Enter the desired file name (without the .txt extension): ";
	cin.ignore();
	getline(cin, file);

	StockBST._WriteFileData(file);

	cout << "\nSuccessfully migrated the data into " << file << endl;

	return;
}

/*
Purpose: Prints out the hash table statistics to the screen
Return Value: none
*/
void HashStatistics(HT &StockHT)
{
	cout << "\n-------------------------------------------------------------------" << endl;
	cout << "This option displays some statistics of the hash table. It will display" << endl;
	cout << "the number of collisions, load factor, and the number of full buckets.\n" << endl;

	StockHT.stats();

	return;
}

/*
Purpose: Prints out the most frequently searched item.
Return Value: none
*/
void DisplayMostFrequentItem(MH &StockMH)
{
	cout << "\n-------------------------------------------------------------------" << endl;
	cout << "This option displays the most frequently searched item thus far. This option" << endl;
	cout << "utilizes a max heap to keep track on the most frequently searched item.\n" << endl;

	StockMH.displayTop();

	return;
}