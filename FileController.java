
public interface FileController {
	public boolean allocate(int Size);
	public void deallocate();
	public int [] getBlocks();
}
