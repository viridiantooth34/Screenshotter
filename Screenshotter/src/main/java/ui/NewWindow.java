
package ui;

import javax.swing.JFrame;

import javax.swing.JTextField;

import javax.swing.JLabel;

import javax.swing.JPanel;

import javax.swing.JButton;

import javax.swing.JDialog;

import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;

import javax.swing.UIManager;

import java.awt.Font;

import javax.swing.BoxLayout;

import net.miginfocom.swing.MigLayout;

import java.awt.FlowLayout;

import java.awt.GridLayout;

import javax.swing.GroupLayout;

import javax.swing.GroupLayout.Alignment;

import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Color;

import java.awt.Toolkit;

public class NewWindow extends JFrame {

	// private JTextField textDocFileName;

	// public static String docFilename;

	public static JButton btnOk; // Declare btnOk as an instance variable

	public NewWindow(String message) {

		setResizable(false);

		setAlwaysOnTop(true);

		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(System.getProperty("user.dir") + "\\src\\main\\resources\\icon.png"));

		setBackground(new Color(102, 153, 0));

		getContentPane().setBackground(Color.BLACK);

		// Set up the new window

		setTitle("Jira Upload Status");

		setSize(521, 223);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLocationRelativeTo(null);

		getContentPane().setLayout(null);

		// Create a panel and a label

		JPanel panel = new JPanel();

		panel.setBackground(Color.BLACK);

		panel.setBounds(0, 0, 499, 167);

		JLabel label = new JLabel(message);

		label.setToolTipText("Status");

		label.setForeground(Color.WHITE);

		label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));

		// label.setFont(new Font("Perpetua", Font.PLAIN, 33));

		getContentPane().add(panel);

		JButton btnClose = new JButton("Close");

		btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				dispose();

			}

		});

		GroupLayout gl_panel = new GroupLayout(panel);

		gl_panel.setHorizontalGroup(

				gl_panel.createParallelGroup(Alignment.TRAILING)

						.addGroup(gl_panel.createSequentialGroup()

								.addGap(81)

								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)

										.addComponent(label, GroupLayout.PREFERRED_SIZE, 388,
												GroupLayout.PREFERRED_SIZE)

										.addComponent(btnClose))

								.addContainerGap(30, Short.MAX_VALUE))

		);

		gl_panel.setVerticalGroup(

				gl_panel.createParallelGroup(Alignment.TRAILING)

						.addGroup(gl_panel.createSequentialGroup()

								.addContainerGap()

								.addComponent(label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)

								.addGap(81)

								.addComponent(btnClose)

								.addContainerGap())

		);

		panel.setLayout(gl_panel);

	}

}