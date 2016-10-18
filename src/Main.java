/**
 * Do proper commenting in your source files from now on. It doesn't matter
 * if you decide to use javadoc or comments. 
 * 
 * The purpose of this assignment is to see how polymorphism works
 * and why do we need to provide such interface to users of our class
 * to illustrate abstraction layers, encapsulation, inheritance and polymorphism.
 * 
 * 
 **/

import java.io.*;
import java.util.Scanner;

public class Main 
{
	static String inputFileName = "input.txt";
	static String outputFileName = "output.txt";
	
	//Naive way of writing to a file
	public static void GenerateRandomOutput(int numOfElements)
	{
		FileWriter fw;
		try
		{
			fw = new FileWriter(new File(outputFileName));
			for(int i = 0; i < numOfElements; i++)
			{
				fw.write(((int)(Math.random()*numOfElements) ) + "\r\n");
			}
			fw.close();
		}
		catch(IOException ex)
		{
			System.out.println("IOException error message: " + ex.getMessage());
		}
	}
	
	//Read from input file and display it
	public static void DisplayInputFile()
	{
		try
		{
			Scanner sc = new Scanner(new File(inputFileName));
			while(sc.hasNextInt())
			{
				System.out.println(sc.nextInt());
			}
		}
		catch(IOException ex)
		{
			System.out.println("IOException error message: " + ex.getMessage());
		}
	}
	
	public static void main(String[] args)
	{
		//DisplayInputFile();
		MyContainer a = new MyArrayContainer();
		MyContainer b = new MyLinkedListContainer();
		
		//You need to insert the same data into both containers
		a.Add(1);
		b.Add(1);
		
		//They should both display the same result, use this to check if your implementation works. 
		a.DisplayData();
		b.DisplayData();
		
		//Again, they should both produce the same text file as output. 
		for(int i = 0; i < a.length();i++)
		{
			System.out.println(a.GetDataAt(i));//Modify this code to write result to a text file instead
			System.out.println(b.GetDataAt(i));//Modify this code to write result to a text file instead
		}
		
	}
}
