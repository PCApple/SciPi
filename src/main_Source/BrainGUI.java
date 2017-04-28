package main_Source;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class BrainGUI extends JFrame {

	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	static Object result;
	public JPanel contentPane;
	protected File defaultSaveFile = new File((System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop"));
	ArrayList<String> output = new ArrayList<String>();
	public static String name = "";
	public JTextField Iterations_Field;
	public int matchTotal;
	private JTextField Radius_Field;
	public PrintStream out;
	public PrintStream err;
	public JTextArea Console_Area;
	public JScrollPane Console_Scroll;
//	public JScrollPane scrollPane;
	
	
	

	/**
	 * Launch the application.
	 */
	public final File getEvent() {
//		area = new JTextArea();
//		area.setEditable(false);
//		
//		
//		scroll = new JScrollPane();
//		scroll.setViewportView(area);
		
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setCurrentDirectory(defaultSaveFile);

		int result = chooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			 return chooser.getSelectedFile();
		} else {
			return null;
		}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BrainGUI frame = new BrainGUI();
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
	public BrainGUI() {
		Console_Area = new JTextArea();
		Console_Area.setEditable(false);
		setForeground(Color.WHITE);
		setTitle("BrainRead");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 608, 612);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel Iterations_Label = new JLabel("Iterations");
		Iterations_Label.setBounds(10, 45, 69, 14);
		contentPane.add(Iterations_Label);
		
		Iterations_Field = new JTextField();
		Iterations_Field.setBounds(89, 42, 120, 20);
		contentPane.add(Iterations_Field);
		Iterations_Field.setColumns(10);
		
		JLabel Radius_Label = new JLabel("Radius");
		Radius_Label.setBounds(10, 70, 58, 14);
		contentPane.add(Radius_Label);
		
		Radius_Field = new JTextField();
		Radius_Field.setBounds(89, 67, 120, 20);
		contentPane.add(Radius_Field);
		Radius_Field.setColumns(10);
		
		JButton Iterations_Enter_Button = new JButton("ENTER");
		Iterations_Enter_Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				Methods.max_its = Integer.parseInt(Iterations_Field.getText());
				System.out.println("New Max Iterations: " + Methods.max_its);
			}
		});
		Iterations_Enter_Button.setBounds(219, 41, 89, 23);
		contentPane.add(Iterations_Enter_Button);
		
		JButton Radius_Enter_Button = new JButton("ENTER");
		Radius_Enter_Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
				Methods.radius = Integer.parseInt(Radius_Field.getText());
				System.out.println("New Radius: " + Methods.radius);
				
			}
		});
		Radius_Enter_Button.setBounds(219, 66, 89, 23);
		contentPane.add(Radius_Enter_Button);
		
		Console_Scroll = new JScrollPane();
		Console_Scroll.setBounds(10, 95, 572, 468);
		Console_Scroll.setViewportView(Console_Area);
		contentPane.add(Console_Scroll);
		
		JButton Run_Button = new JButton("RUN");
		Run_Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
					System.out.println("Running...");
					Methods.main();
				
			}
		});
		Run_Button.setBounds(412, 41, 89, 23);
		contentPane.add(Run_Button);
		
		JButton Input_Path_Button = new JButton("Set In File");
		Input_Path_Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				Methods.IMPORT_FILE = getInFilePath();
			}
		});
		Input_Path_Button.setBounds(10, 11, 89, 23);
		contentPane.add(Input_Path_Button);
		out = new PrintStream(new OutputStream() {
			
			@Override
			public void write(int b) throws IOException {
				char c = (char) b;
				BrainGUI.this.actionPerformed(new ActionEvent(c, ActionEvent.ACTION_PERFORMED, "write"));
			}
			
		});
		err = new PrintStream(new OutputStream() {
			
						@Override
						public void write(int b) throws IOException {
							char c = (char) b;
							BrainGUI.this.actionPerformed(new ActionEvent(c, ActionEvent.ACTION_PERFORMED, "write"));
						}
						
					});
		
		System.setOut(out);
		System.setErr(err);

	}
	
	public String getInFilePath(){
		File file= null;
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.setFileFilter(new RawFilter());
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setCurrentDirectory(defaultSaveFile);
		fc.setDialogTitle("Set Save Location");
		int result = fc.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			
		file = fc.getSelectedFile();
		}
		return file.getAbsolutePath();
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("write")) {
			char c = (char) e.getSource();
			Console_Area.setText(Console_Area.getText() + c);
		}
		else if (e.getActionCommand().equals("clear")) {
			Console_Area.setText("");
		}
	}
}
