import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class LLAlloctionTechController implements FileController,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List <Integer> LLOfBlocks;
	DiskStructure disk;

	public LLAlloctionTechController(DiskStructure disk) {
		this.disk = disk;
		LLOfBlocks = new LinkedList<Integer>();
	}

	public boolean allocate(int Size) {
		int current_pointer_in_disk = 0;
		while (true) {
			if (disk.isallocated(current_pointer_in_disk) == false) {
				disk.allocate(current_pointer_in_disk);
				LLOfBlocks.add(current_pointer_in_disk);
			}
			if (LLOfBlocks.size() == Size) {
				return true;
			}
		}
	}

	public void deallocate() {
		for (Integer i : LLOfBlocks) {
			disk.deallocate(i);
		}
	}
	
	public int [] getBlocks() {
		int [] blocks_in_file=new int[LLOfBlocks.size()];
		for(int i=0;i<LLOfBlocks.size();i++) {
			blocks_in_file[i]=LLOfBlocks.get(i);
		}
		return blocks_in_file;
	}

}
