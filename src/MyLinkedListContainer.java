public class MyLinkedListContainer extends MyContainer
{
	Node head;
	
	//Constructor
	public MyLinkedListContainer()
	{
		containerCount++;
	}
	
	//Required abstract methods due to inheritance
	public void Add(int newNum)
	{
		Node newNode = new Node(newNum);
		Node current = head;
		if(head.GetData() > newNum){
			current = head;	
		}
		while(current.GetNext().GetData() < newNum && current.GetNext() != null){
			current = current.GetNext();
		}
		newNode.SetNext(current.GetNext());	
		
	}
	public void Add(int[] newNums)
	{
		for(int i = 1; i < newNums.length; i++){
			
			Node newNode = new Node(newNums[i]);
			Node current = head;
			if(head.GetData() > newNums[i]){
				current = head;
			}
			while(current.GetNext().GetData() < newNums[i] && current.GetNext() != null){
				current = current.GetNext();
			}
			newNode.SetNext(current.GetNext());
		
		}
		
	}
	public void DisplayData()
	{
		System.out.println("Calling Display Data from MyLinkedListContainer Class");
	}
	public int Find(int data)
	{
		int index = -1;
		
		
		return index;
	}
	public int GetDataAt(int index)
	{
		int data = -1;
		
		
		return data;
	}
}
