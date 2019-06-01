package sjsu.vo.cs146.project3;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This class tests the functionalities of the Red Black Tree by reading in two input files:
 * a dictionary containing several hundred thousand words and a poem.
 * @author Chris Vo
 * @Version 1.0
 */
public class RBTTester {
	RedBlackTree<String> rbt;
	BufferedReader in;
	BufferedReader in2;
	
	@Test
    //Test the Red Black Tree
	public void test() {
		rbt = new RedBlackTree<String>();
        rbt.insert("D");
        rbt.insert("B");
        rbt.insert("A");
        rbt.insert("C");
        rbt.insert("F");
        rbt.insert("E");
        rbt.insert("H");
        rbt.insert("G");
        rbt.insert("I");
        rbt.insert("J");
        assertEquals("DBACFEHGIJ", makeString(rbt));
        System.out.println("\n");
        String str=     "Color: 1, Key:D Parent: \n"+
                        "Color: 1, Key:B Parent: D\n"+
                        "Color: 1, Key:A Parent: B\n"+
                        "Color: 1, Key:C Parent: B\n"+
                        "Color: 1, Key:F Parent: D\n"+
                        "Color: 1, Key:E Parent: F\n"+
                        "Color: 0, Key:H Parent: F\n"+
                        "Color: 1, Key:G Parent: H\n"+
                        "Color: 1, Key:I Parent: H\n"+
                        "Color: 0, Key:J Parent: I\n";
		assertEquals(str, makeStringDetails(rbt));
		System.out.println("\n");
    }
    
    //add tester for spell checker
	@Test
	public void test2() {
		try {
			rbt = new RedBlackTree<String>();
			String input;
			String[] inputs;
			long oldtime = System.currentTimeMillis();
			in = new BufferedReader(new FileReader("dictionary.txt"));
			in2 = new BufferedReader(new FileReader("poem.txt"));
			
			while((input = in.readLine()) != null)
				rbt.insert(input);
			
			long newtime = System.currentTimeMillis() - oldtime;
			System.out.println("The time it took to read in the entire dictionary is: " + newtime + " milliseconds");
			oldtime = System.currentTimeMillis();
			int found = 0;
			int notfound = 0;
			
			while((input = in2.readLine()) != null) {
				inputs = input.split(" ");
				for(int i = 0; i < inputs.length; i++) {
					if(rbt.lookup(inputs[i]))
						found++;
					else
						notfound++;
				}
			}
			
			System.out.println("Words found in dictionary: " + found);
			System.out.println("Words not found in dictionary: " + notfound);
			
			newtime = System.currentTimeMillis() - oldtime;
			System.out.println("The time it took to read in the entire dictionary is: " + newtime + " milliseconds");
			
		} catch (IOException e) {
			System.out.println("Error reading input file!");
		}
	}
    
    public static String makeString(RedBlackTree<String> t) {
       class MyVisitor implements Visitor {
          String result = "";
          public void visit(RedBlackTree.Node n)
          {
             result = result + n.key;
          }
       };
       MyVisitor v = new MyVisitor();
       t.preOrderVisit(v);
       return v.result;
    }

    public static String makeStringDetails(RedBlackTree<String> t) {
    	{
    	       class MyVisitor implements Visitor {
    	          String result = "";
    	          public void visit(RedBlackTree.Node n)
    	          {
    	        	  if(!(n.key).equals("")) {
    	        		  if(n.isRed)
    	        			  n.color = 0;
    	        		  else
    	        			  n.color = 1;
    	        		  
    	        		  result = result +"Color: "+n.color+", Key:"+n.key+" Parent: ";
    	        		  if(n.parent == null)
    	        			  result = result + "" + "\n";
    	        		  else
    	        			  result = result + n.parent.key+"\n";
    	        	  }
    	             
    	          }
    	       };
    	       MyVisitor v = new MyVisitor();
    	       t.preOrderVisit(v);
    	       return v.result;
    	 }
    }
}
