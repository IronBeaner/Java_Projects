//test
package edu.csupomona.cs.cs241.prog_assgmnt_2;
import java.util.Random;
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

public class Driver {
	
	public static void main(String[] args){
	RBT redBlack=new RBT<Integer, Integer>();
	Random g=new Random();
	//for(int x=0;x<15;x++)
	//redBlack.add(g.nextInt(10000),g.nextInt(100));
	redBlack.add(10, 15);
	System.out.println(redBlack.toPrettyString());
	redBlack.add(15, 11);
	System.out.println(redBlack.toPrettyString());
	redBlack.add(7,1);
	System.out.println(redBlack.toPrettyString());
	redBlack.add(5,1);
	System.out.println(redBlack.toPrettyString());
	redBlack.add(6,1);
	System.out.println(redBlack.toPrettyString());
	//System.out.println(redBlack.toPrettyString());
	redBlack.add(17, 17);
	//System.out.println(redBlack.toPrettyString());
	redBlack.add(2, 14);
	//System.out.println(redBlack.toPrettyString());
	redBlack.add(1, 111);
	//System.out.println(redBlack.toPrettyString());
	redBlack.add(0, 1);
	System.out.println(redBlack.toPrettyString());
	//if(redBlack.remove(10)==null)
			//System.out.println("Key was not Found");
	redBlack.add(55, 113);
	System.out.println(redBlack.toPrettyString());
	redBlack.add(33, 18);
	System.out.println(redBlack.toPrettyString());
	redBlack.add(8, 19);
	System.out.println(redBlack.toPrettyString());
	redBlack.add(7, 10);
	System.out.println(redBlack.toPrettyString());
	redBlack.add(4, 1);
	System.out.println(redBlack.toPrettyString());
	redBlack.add(3, 1);
	System.out.println(redBlack.toPrettyString());
	redBlack.add(5, 20);
	System.out.println(redBlack.toPrettyString());
	redBlack.add(6, 0);
	System.out.println(redBlack.toPrettyString());
	redBlack.add(700, 1);
	System.out.println(redBlack.toPrettyString());
	//System.out.println(redBlack.toPrettyString());
	System.out.println(redBlack.remove(15));
	//System.out.println(redBlack.toPrettyString());
	//System.out.println(redBlack.lookup(4));
	//System.out.println(lookup(12,redBlack));
	//redBlack.numOfNodes();*/
	System.out.println(redBlack.toPrettyString());
	}
	public static String lookup(int key,RBT redBlack){
		String value=redBlack.lookup(key)+"";
		if(value==null)
			return "Not Found";
		else 
			return value;
	}
}
