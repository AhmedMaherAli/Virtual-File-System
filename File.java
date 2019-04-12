import java.io.Serializable;

public class File implements Serializable{
	private String fileName;
	private FileController fileController;
	private int Size;
	private DiskStructure Disk ;
	public File(String fileName,int Size,DiskStructure dk)
	{
		this.fileName=fileName;
		this.Size=Size;
		Disk=dk;
	}
	public String getfileName() {
		return fileName;
	}
	public boolean allocate()
	{
		boolean allocated=fileController.allocate(Size);
		if(allocated)return true;
		else return false;
	}
	public void deAllocate()
	{
		fileController.deallocate();
	}
	public FileController createController(String allocationStategy)
	{
		if(allocationStategy=="contiguous")
		{

			fileController=new ContigousController(Disk);
		}
		else if(allocationStategy=="linked")
		{
			//create linked controller like return new LinkedController()
		}
		else
		{
			fileController=new indexedAllocTechniqFileController(Disk);
		}
		return null; //note remove this return (dummy return);
	}
	public FileController getFileController() {
		return fileController;
	}
	public int getSize()
	{
		return Size;
	}
	
}
