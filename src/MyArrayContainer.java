//Complete this class yourself first before looking at the answer
//We have to do insertion sort in this class

public class MyArrayContainer extends MyContainer
{
	private final static int capacityIncrement = 10;
	int[] _internalContainer;
	
	public MyArrayContainer()
	{
		containerCount++;
	}
	
	//Required methods due to inheritance
	public void Add(int newNum)
	{
		
		if(dataSize == _internalContainer.length)
		{
			MakeRoom();
		}
		_internalContainer[dataSize] = newNum;
		dataSize++;		
		
	}
	public void Add(int[] newNums)
	{
		for(int i = 0; i < newNums.length; i++)
		{
			Add(newNums[i]);
		}
		
	}
	public void DisplayData()
	{
		System.out.println("Calling Display Data from MyArrayContainer Class");
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
	//Method specific to this class only
	public void MakeRoom()
	{
		int[] temp = new int[_internalContainer.length + capacityIncrement]; //you will lose points if you put in an arbitrary value , aka "Magic number"
		for(int i = 0; i < _internalContainer.length; i++)
		{
			temp[i] = _internalContainer[i];//copy everything over	
		}	
		_internalContainer = temp; //take the new box and get rid of the old box
		
	}
}
