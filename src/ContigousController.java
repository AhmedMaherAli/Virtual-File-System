import java.io.Serializable;

public class ContigousController implements FileController,Serializable{
	private int size;
	private int start;
	private DiskStructure disk;
	
	public ContigousController(DiskStructure disk) {
		this.disk = disk;
		start=-1;
		size=0;
	}
	//utilities
	private boolean checkTheBlocks()
	{
		if(size>disk.getDiskSize())
			return false;
		int s=0;
		for(int i=0;i<disk.getDiskSize();i++)
		{
			s=i;
			int j;
			for(j=i;j<i+size;j++)
			{
				if(!disk.getStatusBlock(j))
					break;
			}
			if(j==i+size)
			{
				start=s;
				return true;
			}
		}
		return false;
	}
	public boolean allocate(int Size) {
		this.size=Size;
		boolean checkAllocation=this.checkTheBlocks();
		if(!checkAllocation)
		{
			System.out.println("there is not contious blocks with size "+size);
			start=-1;
			size=0;
			return false;
		}
		for(int i=start;i<start+size;i++)
		{
			disk.allocate(i);
		}
		return true;
		
	}
	public void deallocate() {
		for(int i=start;i<start+size;i++)
		{
			disk.deallocate(i);
		}
		size=0;
		start=-1;
	}
	public int[] getBlocks() {
		if(size==-1)
			return null;
		int []blocks=new int[size];
		int j=0;
		for(int i=start;i<start+size&&j<size;i++,j++)
		{
			blocks[j]=i;
		}
		return blocks;
	}

}
