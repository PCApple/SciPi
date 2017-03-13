package main_Source;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
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
	public Names N = new Names();
	public JTextField textField_1;
	public int matchTotal;
	private JTextField textField;
	public PrintStream out;
	public PrintStream err;
	public JTextArea area;
	public JScrollPane scroll;
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
		
		
		Methods md = new Methods();
		area = new JTextArea();
		area.setEditable(false);
		setForeground(Color.WHITE);
		setTitle("BrainRead");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 608, 612);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Edit Name");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				N = new Names();
				N.setVisible(true);
			}
		});
		btnNewButton.setBounds(10, 8, 102, 23);
		contentPane.add(btnNewButton);
		
		JButton btnSave = new JButton("Save");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				write(btnSave);
			}
		});
		btnSave.setBounds(513, 10, 69, 23);
		contentPane.add(btnSave);
		
		JLabel lblMatchCount = new JLabel("Iterations");
		lblMatchCount.setBounds(10, 45, 69, 14);
		contentPane.add(lblMatchCount);
		
		textField_1 = new JTextField();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					matchTotal = Integer.parseInt(textField_1.getText());
					
					System.out.println("Iteration count is: " + matchTotal);
				}
			}
		});
		textField_1.setBounds(89, 42, 120, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Radius");
		lblNewLabel.setBounds(10, 70, 58, 14);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(89, 67, 120, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnEnter = new JButton("ENTER");
		btnEnter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				Methods.max_its = Integer.parseInt(textField_1.getText());
				System.out.println("New Max Iterations: " + Methods.max_its);
			}
		});
		btnEnter.setBounds(219, 41, 89, 23);
		contentPane.add(btnEnter);
		
		JButton btnEnter_1 = new JButton("ENTER");
		btnEnter_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
				Methods.res = Integer.parseInt(textField.getText());
				System.out.println("New Radius: " + Methods.res);
				
			}
		});
		btnEnter_1.setBounds(219, 66, 89, 23);
		contentPane.add(btnEnter_1);
		
		scroll = new JScrollPane();
		scroll.setBounds(10, 95, 572, 468);
		scroll.setViewportView(area);
		contentPane.add(scroll);
		
		JButton btnRun = new JButton("RUN");
		btnRun.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
					System.out.println("Running...");
					Methods.main(null);
				
			}
		});
		btnRun.setBounds(412, 41, 89, 23);
		contentPane.add(btnRun);
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
	public void addFullEvent(){
		for (int i = 1; i<= matchTotal; i++){
			String path = textField.getText() + i;
						
		}
	}
	
	
	public final void write(JButton ep){
		
		File file = new File(this.defaultSaveFile+"\\"+name + ".txt");
		
		System.out.println("File at: " + file.getAbsolutePath());
		
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			String outpp = output.toString();
			StringBuilder b = new StringBuilder();
			for (String s : output) {
				b.append(s + "\n");
			}
			outpp = b.toString();
			Files.write(file.toPath(), outpp.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("write")) {
			char c = (char) e.getSource();
			area.setText(area.getText() + c);
		}
		else if (e.getActionCommand().equals("clear")) {
			area.setText("");
		}
	}
}
