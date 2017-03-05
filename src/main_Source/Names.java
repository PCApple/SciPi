package main_Source;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Window.Type;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Names extends JFrame {

	private JPanel contentPane;
	private JTextField txtNewEvent;

	/**
	 * Create the frame.
	 */
	public Names() {
		setTitle("Name?");
		setType(Type.POPUP);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 366, 129);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtNewEvent = new JTextField();
		txtNewEvent.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
					BrainGUI.name = txtNewEvent.getText();
					System.out.println("New Name is: " + BrainGUI.name);
				}
			}
		});
		txtNewEvent.setText("New Event");
		txtNewEvent.setToolTipText("New Event");
		txtNewEvent.setBounds(10, 36, 330, 20);
		contentPane.add(txtNewEvent);
		txtNewEvent.setColumns(10);
	}
}
