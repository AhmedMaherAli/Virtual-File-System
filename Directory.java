import java.awt.TextArea;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Directory implements Serializable{
	private String name;
	private HashMap<String, Directory> subDirectories;
	private HashMap<String, File> files;
	private String allocationStategy;
	TextArea textarea;
	DiskStructure Disk;
	public Directory(){
		subDirectories=new HashMap<>();
		files=new HashMap<>();
	}
	public Directory(String Name)
	{
		name=Name;
	}
	public Directory(String allocationStategy,String name,DiskStructure ds,TextArea ta)
	{
		subDirectories=new HashMap<>();
		files=new HashMap<>();
		this.allocationStategy=allocationStategy;
		this.name=name;
		Disk=ds;
		textarea=ta;
	}
	
	//file operations
	public void addFile(String filePath,int Size)
	{
		Directory parent=giveMeParent(filePath);
		String fileName="";
		for(int i=0;i<filePath.length();i++)
		{
			fileName+=filePath.charAt(i);
			if(filePath.charAt(i)=='/')fileName="";
		}
		if(!canBeAfile(fileName))
		{
			textarea.append("Error: The file should have an extension\n");
			System.out.println("Error: The file should have an extension");
			return;
		}
		if(parent.containsFile(fileName))
		{
			textarea.append("Error: There is a file that has this name\n");
			System.out.println("Error: There is a file that has this name");
			return;
		}
		File f=new File(fileName,Size,Disk);
		f.createController(allocationStategy);
		FileController fileController=f.getFileController();
		boolean allocated=fileController.allocate(Size);
		if(allocated)
		{parent.files.put(fileName, f);textarea.append("File Is Added Successfully\n");System.out.println("File Is Added Successfully ");}
		
	}
	public boolean canBeAfile(String s)
	{
		for(int i=0;i<s.length();i++)
		{
			if(s.charAt(i)=='.')return true;
		}
		return false;
	}
	public Directory giveMeParent(String path)
	{
		String dir="";
		Directory temp=this;
		int i=0,n=path.length();
		while(i<n)
		{
			if(path.charAt(i)!='/')
			{
				dir+=path.charAt(i);
				if(i==n-1)
				{
						return temp;		
				}
			}
			else
			{
				if(i!=n-1)
				{
					if(dir.equals("root")) {i++;dir="";continue;}
					if(temp.containsSubDirectory(dir))
					temp=temp.subDirectories.get(dir);
					else
					{
						textarea.append("InvalidPath" + dir);textarea.append("\n");
						System.out.println("InvalidPath" + dir);
						return null;
					}
				}
				dir="";
			}
			i++;
		}
		return null;
	}

	public void addFolder(String path)
	{
		Directory parent=getFolderParent(path);
		String folderName="";
		for(int i=0;i<path.length();i++)
		{
			folderName+=path.charAt(i);
			if(path.charAt(i)=='/')folderName="";
		}
		Directory dir=new Directory(allocationStategy,folderName,Disk,textarea);
		if(parent.containsSubDirectory(folderName))
		{
			textarea.append("There is a folder that has this name\n");
			System.out.println("There is a folder that has this name");
			return;
		}
		else
		{
			parent.subDirectories.put(folderName,dir);
			textarea.append("Folder Is added Successfully\n");
			System.out.println("Folder Is added Successfully;");
		}
		
	}
	public Directory getFolderParent(String path)
	{
		String dir="";
		Directory temp=this;
		int i=0,n=path.length();
		while(i<n)
		{
			if(path.charAt(i)!='/')
			{
				dir+=path.charAt(i);
				
				if(i==n-1)
				{
						return temp;
				}
			}
			else
			{
				if(i!=n-1)
				{
					if(dir.equals("root")) {i++;dir="";continue;}
					if(temp.containsSubDirectory(dir))
					{temp=temp.subDirectories.get(dir);}
					else
					{
						textarea.append("InvalidPath\n");
						System.out.println("InvalidPath");
						return null;
					}
				}
				dir="";
			}
			i++;
		}
		return null;
	}

	public void PrintStructure(Directory root)
	{
		System.out.print("The Structure now looks like:\n");
		textarea.append("The Structure now looks like:\n");
		for(int i=0;i<40;i++) {System.out.print("*");textarea.append("*");}System.out.println();textarea.append("\n");
		textarea.append("root\n");
		System.out.print("root\n");
		recurseOnStructure(root,1);
		for(Map.Entry<String, File> m:root.files.entrySet())
		{
			for(int i=0;i<6;i++) {System.out.print(" ");textarea.append(" ");}
			textarea.append(m.getKey());
			textarea.append("\n");
			System.out.println(m.getKey());
		}
		System.out.println();
		textarea.append("\n");for(int i=0;i<40;i++) {System.out.print("*");textarea.append("*");}System.out.println();textarea.append("\n");
	}
	public void recurseOnStructure(Directory dir,int level)		// here we implemented Dipth First Traverse(dfs) to print the childs of the childs before parents 
	{
		for(Entry<String, Directory> m:dir.subDirectories.entrySet())
		{

			for(int i=0;i<6*level;i++) {System.out.print(" ");textarea.append(" ");}
			System.out.println(m.getKey());
			textarea.append(m.getKey());
			textarea.append("\n");
			recurseOnStructure(m.getValue(),level+1);
			
		}
		if(dir.name=="root")return;
		for(Map.Entry<String, File> m:dir.files.entrySet())
		{
			for(int i=0;i<(6*level);i++) {System.out.print(" ");textarea.append(" ");}
			System.out.println(m.getKey());
			textarea.append(m.getKey());
			textarea.append("\n");
		}
	}
	public void deleteFile(String path)
	{
		Directory parent=giveMeParent(path);
		String fileName="";
		for(int i=0;i<path.length();i++)
		{
			fileName+=path.charAt(i);
			if(path.charAt(i)=='/')fileName="";
		}
		if(parent.containsFile(fileName))
		{
			File f=parent.files.get(fileName);
			f.deAllocate();
			parent.files.remove(fileName);
			textarea.append("File Is deleted succefully\n");
			System.out.println("File Is deleted succefully");
		}
		else
		{
			textarea.append("Error: There is no file with that name\n");
			System.out.println("Error: There is no file with that name");
		}
	}
	public void deleteFolder(String path)
	{
		Directory parent=getFolderParent(path);
		String folderName="";
		for(int i=0;i<path.length();i++)
		{
			folderName+=path.charAt(i);
			if(path.charAt(i)=='/')folderName="";
		}
		if(parent.containsSubDirectory(folderName))
		{
			parent.subDirectories.remove(folderName);
			textarea.append("Folder is deleted successfully\n");
			System.out.println("Folder is deleted successfully");
			
		}
		else
		{
			textarea.append("Error: There is no folder with this name\n");
			System.out.println("Error: There is no folder with this name");
		}
	}

	public File getFile(String fileName)
	{
		if(!files.containsKey(fileName))
			return null;
		return files.get(fileName);
	}
	public boolean containsFile(String fileName)
	{
		return files.containsKey(fileName);
	}
	
	public boolean containsSubDirectory(String folder)
	{
		return subDirectories.containsKey(folder);
	}
	public void dfsOnDirectories(Directory cur,String path,PrintWriter DirectoryWriter,PrintWriter FilesWriter) throws IOException
	{
		for(Entry<String, Directory> m:cur.subDirectories.entrySet())
		{
			path+=("/"+m.getKey());
			DirectoryWriter.println(path);
			dfsOnDirectories(m.getValue(),path,DirectoryWriter,FilesWriter);
		}
		if(cur.name=="root")return;
		for(Map.Entry<String, File> m:cur.files.entrySet())
		{
			path+=(m.getKey()+" ");
			FilesWriter.print(path);
			FilesWriter.print(m.getValue().getSize());
		}
	}
	public void saveOnFile(Directory root) throws IOException
	{
		FileWriter fileWriter = new FileWriter("Disk.txt");
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    FileWriter DirectoryWriter = new FileWriter("Directories.txt");
	    PrintWriter directoryPrinter = new PrintWriter(DirectoryWriter);
	    FileWriter filesWriter = new FileWriter("Files.txt");
	    PrintWriter filePrinter = new PrintWriter(filesWriter );
	    int dsksz=Disk.getDiskSize();		//SaveDiskStatus 
	    printWriter.println(dsksz);
	    for(int i=0;i<dsksz;i++) //write on disk
	    	{if(Disk.getStatusBlock(i)==true)printWriter.print(1);else printWriter.print(0);}printWriter.println();
	    
	    directoryPrinter.println("root");
	    dfsOnDirectories(root,"root",directoryPrinter,filePrinter );
		for(Map.Entry<String, File> m:root.files.entrySet())
		{
			String path="root/"+m.getKey();
			filePrinter.print(path);
			filePrinter.print(" ");
			filePrinter.println(m.getValue().getSize());
			filePrinter.println();
		}
		printWriter.close();
		filePrinter.close();
		directoryPrinter.close();
		
	}
}
//public void dfsOnFile(Directory cur,String path,int level,PrintWriter printWriter) throws IOException
//{
//	
//	for(Entry<String, Directory> m:cur.subDirectories.entrySet())
//	{
//
//		for(int i=0;i<4*level;i++) {printWriter.print(" ");}
//		printWriter.println(m.getKey());
//		dfsOnFile(m.getValue(),level+1,printWriter);
//	}
//	if(cur.name=="root")return;
//	for(Map.Entry<String, File> m:cur.files.entrySet())
//	{
//		for(int i=0;i<(4*level);i++) {printWriter.print(" ");}
//		printWriter.print(m.getKey());
//		printWriter.print(" ");
//		printWriter.println(m.getValue().getSize());
//		printWriter.println();
//	}
//}
//public void saveOnFile(Directory root) throws IOException
//{
//	FileWriter fileWriter = new FileWriter("Disk.txt");
//    PrintWriter printWriter = new PrintWriter(fileWriter);
//    int dsksz=Disk.getDiskSize();		//SaveDiskStatus 
//    printWriter.println(dsksz);
//    for(int i=0;i<dsksz;i++)
//    	{if(Disk.getStatusBlock(i)==true)printWriter.print(1);else printWriter.print(0);}
//    printWriter.println();
//    printWriter.println("root");
//	dfsOnFile(root,1,printWriter);
//	for(Map.Entry<String, File> m:root.files.entrySet())
//	{
//		for(int i=0;i<4;i++) {printWriter.print(" ");}
//		printWriter.print(m.getKey());
//		printWriter.print(" ");
//		printWriter.println(m.getValue().getSize());
//		printWriter.println();
//	}
//	printWriter.close();
//}
//public void folderCreationCommand(String path)
//{
//	String dir="";
//	Directory temp=this;
//	int i=0,n=path.length();
//	while(i<n)
//	{
//		if(path.charAt(i)!='/')
//		{
//			dir+=path.charAt(i);
//			
//			if(i==n-1)
//			{	
//				if(temp.containsSubDirectory(dir))
//				{
//					System.out.println("There is a folder that has this name");
//					return;
//				}
//				else {
//					temp.addFolder(dir,temp);
//				}
//			}
//		}
//		else
//		{
//			if(i!=n-1)
//			{
//				if(dir.equals("root")) {i++;dir="";continue;}
//				if(temp.containsSubDirectory(dir))
//				{temp=temp.subDirectories.get(dir);}
//				else
//				{
//					System.out.println("InvalidPath");
//					return;
//				}
//			}
//			dir="";
//		}
//		i++;
//	}
//}

//public void fileCreationCommand(String filePath,int Size)
//{
//	String dir="";
//	Directory temp=this;
//	int i=0,n=filePath.length();
//	while(i<n)
//	{
//
//		if(filePath.charAt(i)!='/')
//		{
//			dir+=filePath.charAt(i);
//			if(i==n-1)
//			{
//				if(temp.containsFile(dir))
//				{
//					System.out.println("Error: There is a file that has this name");
//					return;
//				}
//				else if(!canBeAfile(dir))
//				{
//					System.out.println("Error: The file should have an extension");
//				}
//				else 
//				{
//					temp.addFile(dir, Size,temp);
//					
//				}
//			}
//		}
//		else
//		{
//			if(i!=n-1)
//			{
//				if(dir.equals("root")) {i++;dir="";continue;}
//				if(temp.containsSubDirectory(dir))
//				temp=temp.subDirectories.get(dir);
//				else
//				{
//					System.out.println("InvalidPath" + dir);
//					return;
//				}
//			}
//			dir="";
//		}
//		i++;
//	}
//	
//}
