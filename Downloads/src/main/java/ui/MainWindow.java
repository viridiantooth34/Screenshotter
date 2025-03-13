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
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JButton btnStopButton; // Declare stop button as an instance variable
	private JTextField txtHeading1;
	private ArrayList<String> textList; // Declare the ArrayList as a class-level variable
	private ArrayList<String> textListHeading2; // Declare the ArrayList as a class-level variable
	private JTextField txtHeading2;

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
		textListHeading2 = new ArrayList<>();
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
		btnCloseButton.setBounds(305, 111, 109, 23);
		panel.add(btnCloseButton);
		btnCloseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		// Take Screenshot button
		JButton btnTakeScreenShotButton = new JButton("Take ScreenShot");
		btnTakeScreenShotButton.setBounds(272, 23, 142, 23);
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
				addTextToListHeading2();
				setVisible(true);
				btnStopButton.setEnabled(true);
			}
		});

		// Stop button
		btnStopButton = new JButton("Stop!");
		btnStopButton.setBounds(25, 111, 89, 23);
		btnStopButton.setEnabled(false);
		panel.add(btnStopButton);
		btnStopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.println("ArrayList Heading1 = " + textList);
				System.out.println("ArrayList Heading2 = " + textListHeading2);
				Utilities utilities = new Utilities();
				utilities.copyingImagesFromScreenshotstoWordDocument(textList,textListHeading2);
				utilities.deleteContentsofScreenshotsFolder();
				btnStopButton.setEnabled(false); // disable the Stop button
				textList.clear();
				textListHeading2.clear();

			}
		});

		// Open the Screenshots button
		JButton btnOpenTheScreenshotsButton = new JButton("Open the Screenshots");
		btnOpenTheScreenshotsButton.setBounds(132, 111, 163, 23);
		panel.add(btnOpenTheScreenshotsButton);
		btnOpenTheScreenshotsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Utilities utilities = new Utilities();
				utilities.openWordDocument();
			}
		});

		// Text field
		txtHeading1 = new JTextField();
		txtHeading1.setToolTipText("Enter your text here");
		txtHeading1.setBounds(25, 26, 227, 20);
		panel.add(txtHeading1);
		txtHeading1.setColumns(10);

		JLabel heading1 = new JLabel("Heading 1");
		heading1.setFont(UIManager.getFont("ToolBar.font"));
		heading1.setForeground(Color.WHITE);
		heading1.setBounds(25, 11, 149, 14);
		panel.add(heading1);

		JLabel heading2 = new JLabel("Heading 2");
		heading2.setForeground(Color.WHITE);
		heading2.setFont(UIManager.getFont("ToolBar.font"));
		heading2.setBounds(25, 65, 149, 14);
		panel.add(heading2);

		txtHeading2 = new JTextField();
		txtHeading2.setBounds(25, 80, 389, 20);
		panel.add(txtHeading2);
		txtHeading2.setColumns(10);
	}

	public void addTextToList() {
		String text = txtHeading1.getText();
		if (text.isEmpty()) {
			text = ""; // Add an empty string if no text is entered
		}
		textList.add(text);
		txtHeading1.setText(""); // Clear the text field
		System.out.println("Text added: " + text);
	}

	public void addTextToListHeading2() {
		String text = txtHeading2.getText();
		if (text.isEmpty()) {
			text = ""; // Add an empty string if no text is entered
		}
		textListHeading2.add(text);
		txtHeading2.setText(""); // Clear the text field
		System.out.println("Text added: " + text);
	}
}
