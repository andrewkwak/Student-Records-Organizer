abstract class MyContainer 
{
	//Static variables and Methods
	protected static int containerCount = 0;
	public static int GetAllContainerCount(){ return containerCount; }
	
	//Non-static variables
	protected int dataSize;
	
	/* There are four levels : Public, undefined, protected, private
	 * public - means that anyone can see it
	 * undefined - it's when you don't have anything next to the variable: ex: int containerCount = 0;  
	 * protected - means that the package, as well as those that are inherited can access it
	 * private - difference with protected and private is that the package or those inherited cannot access private variables
	 * Why protected?  Because since this class is being inherited, it should be protected so the inherited class can use it
	 */
	
	//Non-abstract methods
	public int length() {return dataSize;} //returns the length of your current
	
	//Abstract Methods 
	public abstract void Add(int newNum); //This allows insertion of a single data into your container
	public abstract void Add(int[] newNums); //This allows insertion of an array of data into your container
	public abstract void DisplayData(); //This method will display all data inserted into your container
	public abstract int Find(int data); //This method will attempt to find the first occurrence of your data and returns the index
	//find(int data) is similar to indexOf
	public abstract int GetDataAt(int index); //This method will allow user to return the data at the specified location
	//This is very similar to find, but instead, it will return the location
	
	//As you can see, all these abstract methods are given to you.  Your task is to fill them all out
	//within the subclasses inside MyArrayContainer and MyLinkedListContainer due to inheritance.
}
