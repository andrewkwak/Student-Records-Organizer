class Node 
{
	//private data members or attributes
	private int data;
	private Node next;
	
	//Get and Set
	public int GetData(){ return data;}
	public Node GetNext(){ return next; }
	public void SetData(int newData){ data = newData; }
	public void SetNext(Node newNode){ next = newNode; }
	
	//Constructors
	public Node()
	{
		data = 0;
		next = null;
	}
	public Node(int newData)
	{
		data = newData;
		next = null;
	}
	public Node(int newData, Node nextNode)
	{
		data = newData;
		next = nextNode;
	}
}
