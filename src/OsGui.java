import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Button;
import java.awt.SystemColor;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.*;
import java.io.*;

public class OsGui extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JTextField Command;
	private static JTextField DiskSize;
	TextArea textArea = new TextArea();
	private JTextField allocationMethod;
	static String alloMethod;
	int diskSize;
	DiskStructure disk;
	Directory root;
	private JTextField FileSize;
	private JTextField Path;
	public void Write_root_to_file(String filename) throws FileNotFoundException, IOException {
		ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(filename));
		writer.writeObject(root);
		writer.close();
	}
	
	public void Write_disk_to_file(String filename) throws FileNotFoundException, IOException {
		ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(filename));
		writer.writeObject(disk);
		writer.close();
	}
	
	public void Read_root_from_file(String filename) throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream reader=new ObjectInputStream(new FileInputStream(filename));
		root=(Directory) reader.readObject();
		reader.close();
	}
	
	public void Read_disk_from_file(String filename) throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream reader=new ObjectInputStream(new FileInputStream(filename));
		disk=(DiskStructure) reader.readObject();
		reader.close();
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OsGui frame = new OsGui();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 */
	public OsGui() {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 794, 417);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(10, 0, 193, 390);
		contentPane.add(panel);
		panel.setLayout(null);
		JLabel lblTask= new JLabel("Virtual File");
		lblTask .setForeground(Color.WHITE);
		lblTask .setFont(new Font("Wide Latin", Font.BOLD, 14));
		lblTask .setBounds(10, 52, 180, 51);
		panel.add(lblTask );
		
		JLabel lblSystem = new JLabel("System");
		lblSystem.setForeground(Color.WHITE);
		lblSystem.setFont(new Font("Wide Latin", Font.BOLD, 14));
		lblSystem.setBounds(39, 83, 131, 51);
		panel.add(lblSystem);
		
		Button button = new Button("SaveStructureOnfile");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					root.saveOnFile(root);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});Button button_1 = new Button("LoadStructureFromfile");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Read_root_from_file("Directories.txt");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					Read_disk_from_file("Disk.txt");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_1.setForeground(Color.WHITE);
		button_1.setBackground(Color.BLACK);
		button_1.setBounds(23, 212, 143, 35);
		panel.add(button_1);
		button.setBounds(23, 271, 143, 35);
		panel.add(button);
		button.setForeground(Color.WHITE);
		button.setBackground(SystemColor.desktop);
		Button Excecute = new Button("Execute");

		Excecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String command=Command.getText();
				String path=Path.getText();
				if(command.equals("CreateFile"))
				{
					int filesize=Integer.parseInt(FileSize.getText());
					root.addFile(path,filesize);
				}
				else if(command.equals("CreateFolder"))
				{
					root.addFolder(path);
				}
				else if(command.equals("DeleteFile"))
				{
					root.deleteFile(path);
				}
				else if(command.equals("DeleteFolder"))
				{
					root.deleteFolder(path);
				}
				else if(command.equals("DisplayDiskStatus"))
				{
					disk.displayDiskStatus();
				}
				else if(command.equals("DisplayDiskStructure"))
				{
					root.PrintStructure(root);
				}

			}
		});
		

		Excecute.setForeground(Color.WHITE);
		Excecute.setBackground(new Color(241, 57,83));
		Excecute.setBounds(242, 319, 230, 35);
		contentPane.add(Excecute);
		
		Command = new JTextField();
		Command.setBounds(213, 271, 86, 35);
		contentPane.add(Command);
		Command.setColumns(10);
		JSeparator separator = new JSeparator();
		separator.setBounds(404, 98, 262, -7);
		contentPane.add(separator);
		
		JLabel lblUsername = new JLabel("Command");
		lblUsername.setBounds(213, 247, 86, 24);
		contentPane.add(lblUsername);
		
		DiskSize = new JTextField();
		DiskSize.setColumns(10);
		DiskSize.setBounds(213, 67, 113, 41);
		contentPane.add(DiskSize);
		
		JLabel lblEnterDiskSize = new JLabel("Disk Size");
		lblEnterDiskSize.setBounds(213, 39, 94, 29);
		contentPane.add(lblEnterDiskSize);
		
		allocationMethod = new JTextField();
		allocationMethod.setColumns(10);
		allocationMethod.setBounds(394, 67, 113, 41);
		contentPane.add(allocationMethod);
		
		JLabel lblEnterAllocationMethod = new JLabel("Allocation Method");
		lblEnterAllocationMethod.setBounds(400, 41, 117, 24);
		contentPane.add(lblEnterAllocationMethod);
		
		textArea.setBounds(523, 50, 255, 319);
		contentPane.add(textArea);
		
		JLabel lblOutputArea = new JLabel("Output Area");
		lblOutputArea.setBounds(597, 21, 128, 24);
		contentPane.add(lblOutputArea);
		
		Button DiskInfo = new Button("Submit Disk Info");
		DiskInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					diskSize=Integer.parseInt(DiskSize.getText());
					alloMethod=allocationMethod.getText();
					disk=new DiskStructure(diskSize,textArea);
					root=new Directory(alloMethod,"root",disk,textArea);
			}
		});
		DiskInfo.setForeground(Color.WHITE);
		DiskInfo.setBackground(new Color(241, 57, 83));
		DiskInfo.setBounds(256, 180, 200, 35);
		contentPane.add(DiskInfo);
		
		FileSize = new JTextField();
		FileSize.setColumns(10);
		FileSize.setBounds(473, 271, 44, 35);
		contentPane.add(FileSize);
		
		JLabel lblFilesize = new JLabel("FileSize");
		lblFilesize.setBounds(473, 247, 44, 24);
		contentPane.add(lblFilesize);
		
		Path = new JTextField();
		Path.setColumns(10);
		Path.setBounds(301, 271, 171, 35);
		contentPane.add(Path);
		
		JLabel lblPath = new JLabel("CommandPath");
		lblPath.setBounds(308, 247, 94, 24);
		contentPane.add(lblPath);
		
		
	}
}
