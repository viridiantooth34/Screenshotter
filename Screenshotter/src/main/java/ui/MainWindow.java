package ui;

/*************************************************************************

 * *

 * Author: Subhayan Bakshi *

 * *

 *************************************************************************/

import java.awt.Dimension;

import java.awt.EventQueue;

import java.awt.Toolkit;

import javax.swing.JFrame;

import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;

import javax.swing.JButton;

import javax.swing.JFileChooser;

import java.awt.event.ActionListener;

import java.io.File;

import java.util.ArrayList;

import java.awt.event.ActionEvent;

import utilities.JiraIntegration;

import utilities.Utilities;

import java.awt.Color;

import javax.swing.JTextField;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;

import javax.swing.UIManager;

import javax.swing.JToolBar;

import javax.swing.border.LineBorder;

import javax.swing.JScrollPane;

import javax.swing.border.MatteBorder;

import javax.swing.border.SoftBevelBorder;

import javax.swing.border.BevelBorder;

import javax.swing.JSeparator;

public class MainWindow extends JFrame {

	private JPanel contentPane;

	private JButton btnStopButton; // Declare stop button as an instance variable

	private JButton btnOpenTheScreenshotsButton; // Declare OpenDocument button as an instance variable

	private JButton btnUploadToJira;// Declare Upload to Jira button as an instance variable

	private JButton btnUploadToJiraCustom;

	private JTextField txtHeading1;

	private ArrayList<String> textList; // Declare the ArrayList as a class-level variable

	private ArrayList<String> textListHeading2; // Declare the ArrayList as a class-level variable

	private String textStringDocFileName;

	private String textStringJiraID;

	private String textStringJiraIDCustom;

	private JTextField txtHeading2;

	private JTextField txtDocFileName;

	private JTextField txtJiraID;

	private String textFileNamePathCustom;

	private JTextField txtJiraIDCustom;

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

		setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 17));

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

		int y = screenSize.height - frameWidth;

		//setBounds(x, y, 441, 305);
		setBounds(x, y, 450, 303);

		setResizable(false);

		// Initialize content pane

		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		contentPane.setLayout(null);

		JPanel panel = new JPanel();

		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));

		panel.setBackground(Color.BLACK);

		panel.setBounds(0, 0, 434, 220);

		contentPane.add(panel);

		panel.setLayout(null);

		// ScrollBar

		// btnStopButton.setEnabled(false);

		// Take Screenshot button

		JButton btnTakeScreenShotButton = new JButton("Take ScreenShot");

		btnTakeScreenShotButton.setBounds(272, 72, 142, 23);

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

				btnOpenTheScreenshotsButton.setEnabled(false);

				btnUploadToJira.setEnabled(false);

			}

		});

		// Stop button

		btnStopButton = new JButton("Stop!");

		btnStopButton.setBounds(325, 111, 89, 23);

		btnStopButton.setEnabled(false);

		panel.add(btnStopButton);

		btnStopButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				addTextToDocFileName();

				System.out.println("ArrayList Heading1 = " + textList);

				System.out.println("ArrayList Heading2 = " + textListHeading2);

				System.out.println("File name = " + textStringDocFileName);

				Utilities utilities = new Utilities();

				utilities.copyingImagesFromScreenshotstoWordDocument(textList, textListHeading2, textStringDocFileName);

				utilities.deleteContentsofScreenshotsFolder();

				btnStopButton.setEnabled(false); // disable the Stop button

				textList.clear();

				textListHeading2.clear();

				btnOpenTheScreenshotsButton.setEnabled(true);

				btnUploadToJira.setEnabled(true);

				txtDocFileName.setText("");
				utilities.copyGitKeepFile();

			}

		});

		// Open the Screenshots button

		btnOpenTheScreenshotsButton = new JButton("Open Document");

		btnOpenTheScreenshotsButton.setBounds(25, 182, 147, 29);

		btnOpenTheScreenshotsButton.setEnabled(false);

		panel.add(btnOpenTheScreenshotsButton);

		btnOpenTheScreenshotsButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Utilities utilities = new Utilities();

				utilities.openWordDocument(textStringDocFileName);

			}

		});

		// Text field

		txtHeading1 = new JTextField();

		txtHeading1.setToolTipText("Enter your text here");

		txtHeading1.setBounds(25, 26, 389, 20);

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

		heading2.setBounds(25, 55, 149, 14);

		panel.add(heading2);

		txtHeading2 = new JTextField();

		txtHeading2.setBounds(25, 73, 227, 20);

		panel.add(txtHeading2);

		txtHeading2.setColumns(10);

		JLabel lblEnterTheFile = new JLabel("Enter the file name");

		lblEnterTheFile.setFont(UIManager.getFont("ToolBar.font"));

		lblEnterTheFile.setForeground(Color.WHITE);

		lblEnterTheFile.setBounds(25, 112, 142, 23);

		panel.add(lblEnterTheFile);

		txtDocFileName = new JTextField();

		txtDocFileName.setBounds(164, 111, 146, 20);

		panel.add(txtDocFileName);

		txtDocFileName.setColumns(10);

		/*
		 * 
		 * JLabel lblJiraStatusTrue = new JLabel("Uploaded Successfully to JIRA!");
		 * 
		 * lblJiraStatusTrue.setFont(UIManager.getFont("Label.font"));
		 * 
		 * lblJiraStatusTrue.setForeground(Color.WHITE);
		 * 
		 * lblJiraStatusTrue.setBounds(208, 182, 218, 20);
		 * 
		 */

		// panel.add(lblJiraStatusTrue);

		// lblJiraStatusTrue.setEnabled(false);

		/*
		 * 
		 * JLabel lblJiraStatusFalse = new JLabel("Unable to upload to JIRA!");
		 * 
		 * lblJiraStatusFalse.setFont(UIManager.getFont("Label.font"));
		 * 
		 * lblJiraStatusFalse.setForeground(Color.WHITE);
		 * 
		 * lblJiraStatusFalse.setBounds(233, 182, 186, 20);
		 * 
		 * panel.add(lblJiraStatusFalse);
		 * 
		 */

		// panel.add(lblJiraStatusFalse);

		// lblJiraStatusFalse.setEnabled(false);

		btnUploadToJira = new JButton("Upload to Jira");

		btnUploadToJira.setEnabled(false);

		btnUploadToJira.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				JiraIntegration jira = new JiraIntegration();

				addTextToJiraID();

				try {

					jira.attachWordDoc2Jira(textStringDocFileName, textStringJiraID);

				} catch (Exception e1) {

					// TODO Auto-generated catch block

					e1.printStackTrace();

				}

				if (jira.status == true) {

					// Argument to pass to the new window

					String message = "Successfully uploaded to Jira! \uD83D\uDE42";

					// Create and show the new window

					NewWindow newWindow = new NewWindow(message);

					newWindow.setVisible(true);

				} else {

					// Argument to pass to the new window

					String message = "Failed to upload to Jira! \uD83D\uDE1E";

					// Create and show the new window

					NewWindow newWindow = new NewWindow(message);

					newWindow.setVisible(true);

				}

				txtJiraID.setText("");

			}

		});

		btnUploadToJira.setBounds(299, 182, 115, 29);

		panel.add(btnUploadToJira);

		JButton btnConfig = new JButton("Config");

		btnConfig.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Utilities utilities = new Utilities();

				utilities.openConfigJson();

			}

		});

		btnConfig.setBounds(189, 182, 95, 29);

		panel.add(btnConfig);

		JLabel lblJiraIssueId = new JLabel("JIRA issue ID:");

		lblJiraIssueId.setFont(UIManager.getFont("ToolBar.font"));

		lblJiraIssueId.setForeground(Color.WHITE);

		lblJiraIssueId.setBounds(25, 151, 105, 20);

		panel.add(lblJiraIssueId);

		txtJiraID = new JTextField();

		txtJiraID.setBounds(138, 150, 276, 23);

		panel.add(txtJiraID);

		txtJiraID.setColumns(10);

		JPanel panel_1 = new JPanel();

		panel_1.setForeground(Color.WHITE);

		panel_1.setBackground(Color.BLACK);

		panel_1.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));

		panel_1.setBounds(0, 219, 434, 46);

		contentPane.add(panel_1);

		panel_1.setLayout(null);

		final JButton btnUploadToJiraCustom = new JButton("Upload to Jira");

		btnUploadToJiraCustom.setEnabled(false);

		btnUploadToJiraCustom.setFont(new Font("Tahoma", Font.PLAIN, 10));

		btnUploadToJiraCustom.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// JiraUpload Custom

				JiraIntegration jiraIntergration = new JiraIntegration();

				addTextToJiraIDCustom();

				try {

					jiraIntergration.uploadToJiraCustom(textFileNamePathCustom, textStringJiraIDCustom);

				} catch (Exception e1) {

					// TODO Auto-generated catch block

					e1.printStackTrace();

				}

				if (jiraIntergration.status == true) {

					// Argument to pass to the new window

					String message = "Successfully uploaded to Jira! \uD83D\uDE42";

					// Create and show the new window

					NewWindow newWindow = new NewWindow(message);

					newWindow.setVisible(true);

				} else {

					// Argument to pass to the new window

					String message = "Failed to upload to Jira! \uD83D\uDE1E";

					// Create and show the new window

					NewWindow newWindow = new NewWindow(message);

					newWindow.setVisible(true);

				}

				btnUploadToJiraCustom.setEnabled(false);

				txtJiraIDCustom.setText("");

			}

		});

		btnUploadToJiraCustom.setBounds(312, 11, 100, 21);

		panel_1.add(btnUploadToJiraCustom);

		JButton btnUploadAFile = new JButton("Select a file");

		btnUploadAFile.setFont(new Font("Tahoma", Font.PLAIN, 10));

		btnUploadAFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				JFileChooser fileChooser = new JFileChooser();

				int result = fileChooser.showOpenDialog(null);

				if (result == JFileChooser.APPROVE_OPTION) {

					File selectedFile = fileChooser.getSelectedFile();

					System.out.println("Selected file path: " + selectedFile.getAbsolutePath());

					textFileNamePathCustom = selectedFile.getAbsolutePath();

					btnUploadToJiraCustom.setEnabled(true);

				}

			}

		});

		btnUploadAFile.setBounds(28, 11, 94, 21);

		panel_1.add(btnUploadAFile);

		txtJiraIDCustom = new JTextField();

		txtJiraIDCustom.setColumns(10);

		txtJiraIDCustom.setBounds(216, 13, 86, 20);

		panel_1.add(txtJiraIDCustom);

		JLabel label = new JLabel("JIRA issue ID:");

		label.setForeground(Color.WHITE);

		label.setFont(new Font("Segoe UI", Font.PLAIN, 12));

		label.setBounds(138, 14, 68, 16);

		panel_1.add(label);

		JSeparator separator = new JSeparator();

		separator.setBounds(0, 0, 434, 2);

		panel_1.add(separator);

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

	public void addTextToDocFileName() {

		String text = txtDocFileName.getText();

		if (text.isEmpty()) {

			text = "output";

		}

		textStringDocFileName = text;

		System.out.println("File name: " + text);

	}

	public void addTextToJiraID() {

		String text = txtJiraID.getText().trim();

		textStringJiraID = text;

		System.out.println("Jira ID: " + text);

	}

	public void addTextToJiraIDCustom() {

		String text = txtJiraIDCustom.getText().trim();

		textStringJiraIDCustom = text;

		System.out.println("Jira ID Custom: " + text);

	}

}
