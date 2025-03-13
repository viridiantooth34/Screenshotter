package utilities;

/*************************************************************************
 *                                                                       *
 *                            Author: Subhayan Bakshi                    *
 *                                                                       *
 *************************************************************************/

import org.apache.poi.util.Units;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import java.awt.Robot;

import java.awt.AWTException;

import java.awt.Desktop;

import java.awt.Rectangle;

import java.awt.Toolkit;

import java.io.*;

import java.io.File;

import java.io.IOException;

import java.util.ArrayList;

import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.util.Units;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.util.Units;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

public class Utilities {

	public void takeWindowScreenShot() {

		try {

			// Create a Robot instance

			Robot robot = new Robot();

			// Capture the screen size

			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

			// Capture the screenshot as a BufferedImage

			BufferedImage screenCapture = robot.createScreenCapture(screenRect);

			System.out.println(System.getProperty("user.dir"));

			// Specify the file path and format

			File file = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\Screenshots\\"

					+ System.nanoTime() + ".png");

			// Save the screenshot

			ImageIO.write(screenCapture, "png", file);

			System.out.println("Screenshot taken successfully!");

		} catch (AWTException | IOException e) {

			e.printStackTrace();

		}

	}

	public void copyingImagesFromScreenshotstoWordDocument(ArrayList<String> descriptions1,
			ArrayList<String> descriptions2) {

		try {

			// Folder path containing PNG files
			File folder = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\Screenshots\\");

			// Create a new document
			XWPFDocument document = new XWPFDocument();

			// Define maximum width and height for images
			int maxWidth = Units.toEMU(500); // Adjust as needed
			int maxHeight = Units.toEMU(700); // Adjust as needed

			// Create Heading 1 style
			CTStyle heading1Style = CTStyle.Factory.newInstance();
			heading1Style.setStyleId("Heading1");

			CTString heading1StyleName = CTString.Factory.newInstance();
			heading1StyleName.setVal("Heading 1");
			heading1Style.setName(heading1StyleName);

			CTPPrBase heading1PPr = heading1Style.addNewPPr();
			heading1PPr.addNewOutlineLvl().setVal(BigInteger.valueOf(0));

			CTRPr heading1RPr = heading1Style.addNewRPr();
			// heading1RPr.addNewB().setVal(STOnOff.TRUE); // Set bold
			heading1RPr.addNewSz().setVal(BigInteger.valueOf(28 * 2)); // Font size 28
			heading1RPr.addNewColor().setVal("000000");

			XWPFStyle heading1 = new XWPFStyle(heading1Style);
			XWPFStyles styles = document.createStyles();
			styles.addStyle(heading1);

			// Create Heading 2 style
			CTStyle heading2Style = CTStyle.Factory.newInstance();
			heading2Style.setStyleId("Heading2");

			CTString heading2StyleName = CTString.Factory.newInstance();
			heading2StyleName.setVal("Heading 2");
			heading2Style.setName(heading2StyleName);

			CTPPrBase heading2PPr = heading2Style.addNewPPr();
			heading2PPr.addNewOutlineLvl().setVal(BigInteger.valueOf(1));

			CTRPr heading2RPr = heading2Style.addNewRPr();
			// heading2RPr.addNewB().setVal(STOnOff.TRUE); // Set bold
			heading2RPr.addNewSz().setVal(BigInteger.valueOf(24 * 2)); // Font size 24
			heading2RPr.addNewColor().setVal("666666");

			XWPFStyle heading2 = new XWPFStyle(heading2Style);
			styles.addStyle(heading2);

			// Process each PNG file in the folder
			int index = 0;

			for (File file : folder.listFiles()) {
				if (file.isFile() && file.getName().endsWith(".png")) {

					// Create a new paragraph for Heading 1
					XWPFParagraph heading1Paragraph = document.createParagraph();
					heading1Paragraph.setStyle("Heading1"); // Apply Heading 1 style
					XWPFRun heading1Run = heading1Paragraph.createRun();

					if (index < descriptions1.size()) {
						heading1Run.setText(descriptions1.get(index)); // Add description 1 from ArrayList
					} else {
						heading1Run.setText(""); // Fallback description
					}

					heading1Run.addBreak(); // Add a break after the description

					// Create a new paragraph for Heading 2
					XWPFParagraph heading2Paragraph = document.createParagraph();
					heading2Paragraph.setStyle("Heading2"); // Apply Heading 2 style
					XWPFRun heading2Run = heading2Paragraph.createRun();

					if (index < descriptions2.size()) {
						heading2Run.setText(descriptions2.get(index)); // Add description 2 from ArrayList
					} else {
						heading2Run.setText(""); // Fallback description
					}

					heading2Run.addBreak(); // Add a break after the description

					// Read PNG file
					BufferedImage bufferedImage = ImageIO.read(file);

					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write(bufferedImage, "png", baos);
					InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());

					// Calculate new dimensions to fit within maxWidth and maxHeight while
					// maintaining aspect ratio
					int width = Units.pixelToEMU(bufferedImage.getWidth());
					int height = Units.pixelToEMU(bufferedImage.getHeight());

					if (width > maxWidth || height > maxHeight) {
						double aspectRatio = (double) bufferedImage.getWidth() / bufferedImage.getHeight();

						if (aspectRatio > 1) { // Landscape image
							width = maxWidth;
							height = (int) (maxWidth / aspectRatio);
						} else { // Portrait image
							height = maxHeight;
							width = (int) (maxHeight * aspectRatio);
						}
					}

					// Add the image to the document with adjusted dimensions
					XWPFParagraph imageParagraph = document.createParagraph();
					XWPFRun imageRun = imageParagraph.createRun();
					imageRun.addPicture(inputStream, XWPFDocument.PICTURE_TYPE_PNG, file.getName(), width, height);

					// Add a new line after each image
					imageRun.addBreak();

					index++;
				}
			}

			// Write the document to a file
			try (FileOutputStream out = new FileOutputStream("output.docx")) {
				document.write(out);
			}

			System.out.println("PNG images and descriptions inserted into Word document successfully.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteContentsofScreenshotsFolder() {

		// Folder path to be cleaned

		File folder = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\Screenshots\\");

		// Delete all contents of the folder

		if (folder.isDirectory()) {

			for (File file : folder.listFiles()) {

				if (file.isDirectory()) {

					deleteContents(file);

				}

				if (!file.delete()) {

					System.out.println("Failed to delete " + file.getName());

				} else {

					System.out.println(file.getName() + " has been deleted.");

				}

			}

		}

		System.out.println("All contents of the folder have been deleted.");

	}

	// Helper method to delete contents of a directory

	private static void deleteContents(File folder) {

		for (File file : folder.listFiles()) {

			if (file.isDirectory()) {

				deleteContents(file);

			}

			if (!file.delete()) {

				System.out.println("Failed to delete " + file.getName());

			} else {

				System.out.println(file.getName() + " has been deleted.");

			}

		}

	}

	public void openWordDocument() {

		String filePath = System.getProperty("user.dir") + "\\output.docx";

		// Open the file

		File file = new File(filePath);

		if (file.exists()) {

			System.out.println("File found: " + filePath);

			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {

				try {

					Desktop.getDesktop().open(file);

					System.out.println("File opened successfully.");

				} catch (IOException e) {

					System.out.println("Error opening the file: " + e.getMessage());

					e.printStackTrace();

				}

			} else {

				System.out.println("Desktop is not supported or the OPEN action is not supported.");

			}

		} else {

			System.out.println("File not found: " + filePath);

		}

	}

}
