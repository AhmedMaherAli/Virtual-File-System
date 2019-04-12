import java.awt.TextArea;
import java.io.Serializable;

public class DiskStructure implements Serializable{
	private boolean []blocks;
	private int numEmptyBlocks;
	private int numAllocatedBlocks;
	TextArea textarea;
	public DiskStructure(int diskSize,TextArea ta)
	{
		blocks=new boolean[diskSize];
		for(int i=0;i<diskSize;i++)
		{
			// i is a block number
			blocks[i]=true; //true means free space
		}
		textarea=ta;
		numEmptyBlocks=diskSize;
		numAllocatedBlocks=0;
	}
	public int allocate()
	{
		//find free block to be allocated and return it
		if(numEmptyBlocks<=0)
		{
			textarea.append("Sorry, thre are not any enough space\n");
			System.out.println("Sorry, thre are not any enough space");
			return -1;
		}
		for(int i=0;i<blocks.length;i++)
		{
			if(blocks[i])
			{
				blocks[i]=false; //means allocated
				numEmptyBlocks--;
				numAllocatedBlocks++;
				return i;
			}
		}
		//end for
		return -1;
	}
	public int allocate(int i)
	{
		if(blocks[i])
		{
			blocks[i]=false; //means allocated
			numEmptyBlocks--;
			numAllocatedBlocks++;
			return i;
		}
		return -1;
	}
	public boolean deallocate(int i)
	{
		if(blocks[i])
			return false; //means this block already free
		//otherwise
		blocks[i]=true; //make it free block
		numAllocatedBlocks--;
		numEmptyBlocks++;
		return true;//means successful operation
	}
	
	public void displayDiskStatus()
	{
		String bitMap="";
		textarea.append("Empty Space: "+numEmptyBlocks+"KB\n");
		System.out.println("Empty Space: "+numEmptyBlocks+"KB");
		textarea.append("Allocated Space: "+numAllocatedBlocks+"KB\n");
		System.out.println("Allocated Space: "+numAllocatedBlocks+"KB");
		//print all bitmap or blocks
		for(int i=0;i<blocks.length;i++)
		{
			if(blocks[i])
				bitMap+="1";
			else
				bitMap+="0";
		}
		textarea.append("bit map: "+bitMap);
		System.out.println("bit map: "+bitMap);
	}
	public boolean getStatusBlock(int i)
	{
		return blocks[i];
	}
	public int getDiskSize()
	{
		return blocks.length;
	}public boolean isallocated(int i) {
		return blocks[i];
	}
	
}
