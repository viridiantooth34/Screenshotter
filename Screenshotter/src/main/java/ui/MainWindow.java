package ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import utilities.Utilities;
import java.awt.Color;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JButton btnStopButton; // Declare stop button as an instance variable

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(System.getProperty("user.dir") + "\\src\\main\\resources\\icon.png"));
		setBackground(Color.ORANGE);
		setTitle("ScreenShotter");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Define the frame size
		int frameWidth = 450;
		int frameHeight = 200;

		// Get screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		// Calculate the bottom-right corner position
		int x = screenWidth - frameWidth;
		int y = screenHeight - frameHeight;

		// Set the frame's bounds
		setBounds(x, y, frameWidth, frameHeight);

		// Make the frame not resizable (thus, not maximizable)
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(0, 0, 434, 161);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton btnCloseButton = new JButton("Close");
		btnCloseButton.setBounds(305, 93, 109, 23);
		panel.add(btnCloseButton);

		JButton btnTakeScreenShotButton = new JButton("Take ScreenShot");
		btnTakeScreenShotButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Utilities utilities = new Utilities();
				utilities.takeWindowScreenShot();
				btnStopButton.setEnabled(true); // Enable the Stop button
			}
		});
		btnTakeScreenShotButton.setBounds(130, 31, 163, 23);
		panel.add(btnTakeScreenShotButton);

		btnStopButton = new JButton("Stop!");
		btnStopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Utilities utilities = new Utilities();
				utilities.copyingImagesFromScreenshotstoWordDocument();
				utilities.deleteContentsofScreenshotsFolder();
				btnStopButton.setEnabled(false); // disable the Stop button
			}
		});
		btnStopButton.setBounds(25, 93, 89, 23);
		btnStopButton.setEnabled(false); // Initially disable the Stop button
		panel.add(btnStopButton);

		JButton btnOpenTheScreenshotsButton = new JButton("Open the Screenshots");
		btnOpenTheScreenshotsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Utilities utilities = new Utilities();
				utilities.openWordDocument();
			}
		});
		btnOpenTheScreenshotsButton.setBounds(130, 93, 163, 23);
		panel.add(btnOpenTheScreenshotsButton);

		btnCloseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}
