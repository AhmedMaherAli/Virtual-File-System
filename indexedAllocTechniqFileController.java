import java.io.Serializable;

public class indexedAllocTechniqFileController implements FileController,Serializable{
	private DiskStructure diskStructure;
	static class indexblock{
		public static int [] indexes;
		public static int indexBlockindex;
	}
	
	
	indexedAllocTechniqFileController( DiskStructure ds){
		diskStructure=ds;
	}
	@Override
	public boolean allocate(int Size) {
		indexblock.indexBlockindex=diskStructure.allocate();
		indexblock.indexes=new int[Size];
		int i=0;
		while(Size>0)
		{
			int ind=diskStructure.allocate();
			if(ind==-1)
			{
				deallocate();return false;
			}
			indexblock.indexes[i++]=ind;
			Size--;
		}
		return true;
		
	}

	@Override
	public void deallocate() {
		for(int i=0;i<indexblock.indexes.length;i++)
		{
			diskStructure.deallocate(indexblock.indexes[i]);
		}
		if(indexblock.indexBlockindex!=-1)
		diskStructure.deallocate(indexblock.indexBlockindex);
	}

	@Override
	public int[] getBlocks() {
		return indexblock.indexes;
	}
	public int getIndexBlock()
	{
		return indexblock.indexBlockindex;
	}

}
