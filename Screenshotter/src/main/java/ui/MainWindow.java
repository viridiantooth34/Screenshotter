package ui;

/*************************************************************************
 *                                                                       *
 *                            Author: Subhayan Bakshi                    *
 *                                                                       *
 *************************************************************************/

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import utilities.Utilities;
import java.awt.Color;
import javax.swing.JTextField;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JButton btnStopButton; // Declare stop button as an instance variable
	private JTextField txtEnteryourTextHere;
	private ArrayList<String> textList; // Declare the ArrayList as a class-level variable

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainWindow() {
		// Initialize the ArrayList
		textList = new ArrayList<>();

		// Frame properties
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(System.getProperty("user.dir") + "\\src\\main\\resources\\icon.png"));
		setBackground(Color.ORANGE);
		setTitle("ScreenShotter");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Define the frame size and position
		int frameWidth = 450;
		int frameHeight = 200;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width - frameWidth;
		int y = screenSize.height - frameHeight;
		setBounds(x, y, frameWidth, frameHeight);
		setResizable(false);

		// Initialize content pane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(0, 0, 434, 161);
		contentPane.add(panel);
		panel.setLayout(null);

		// Close button
		JButton btnCloseButton = new JButton("Close");
		btnCloseButton.setBounds(305, 93, 109, 23);
		panel.add(btnCloseButton);
		btnCloseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		// Take Screenshot button
		JButton btnTakeScreenShotButton = new JButton("Take ScreenShot");
		btnTakeScreenShotButton.setBounds(272, 35, 142, 23);
		panel.add(btnTakeScreenShotButton);
		btnTakeScreenShotButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				Utilities utilities = new Utilities();
				utilities.takeWindowScreenShot();
				addTextToList(); // Ensure text is added to the list
				setVisible(true);
				btnStopButton.setEnabled(true);
			}
		});

		// Stop button
		btnStopButton = new JButton("Stop!");
		btnStopButton.setBounds(25, 93, 89, 23);
		btnStopButton.setEnabled(false);
		panel.add(btnStopButton);
		btnStopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.println("ArrayList = " + textList);
				Utilities utilities = new Utilities();
				utilities.copyingImagesFromScreenshotstoWordDocument(textList);
				utilities.deleteContentsofScreenshotsFolder();
				btnStopButton.setEnabled(false); // disable the Stop button
				textList.clear();
				
			}
		});

		// Open the Screenshots button
		JButton btnOpenTheScreenshotsButton = new JButton("Open the Screenshots");
		btnOpenTheScreenshotsButton.setBounds(130, 93, 163, 23);
		panel.add(btnOpenTheScreenshotsButton);
		btnOpenTheScreenshotsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Utilities utilities = new Utilities();
				utilities.openWordDocument();
			}
		});

		// Text field
		txtEnteryourTextHere = new JTextField();
		txtEnteryourTextHere.setToolTipText("Enter your text here");
		txtEnteryourTextHere.setBounds(25, 36, 227, 20);
		panel.add(txtEnteryourTextHere);
		txtEnteryourTextHere.setColumns(10);
	}

	public void addTextToList() {
		String text = txtEnteryourTextHere.getText();
		if (text.isEmpty()) {
			text = ""; // Add an empty string if no text is entered
		}
		textList.add(text);
		txtEnteryourTextHere.setText(""); // Clear the text field
		System.out.println("Text added: " + text);
	}
}
