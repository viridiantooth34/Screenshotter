package utilities;

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

	public void copyingImagesFromScreenshotstoWordDocument() {
		{
			try {
				// Folder path containing PNG files
				File folder = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\Screenshots\\");

				// Create a new document
				XWPFDocument document = new XWPFDocument();

				// Define maximum width and height for images
				int maxWidth = Units.toEMU(500); // Adjust as needed
				int maxHeight = Units.toEMU(700); // Adjust as needed

				// Process each PNG file in the folder
				for (File file : folder.listFiles()) {
					if (file.isFile() && file.getName().endsWith(".png")) {
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

						// Create a new paragraph
						XWPFParagraph paragraph = document.createParagraph();
						XWPFRun run = paragraph.createRun();

						// Add the image to the document with adjusted dimensions
						run.addPicture(inputStream, XWPFDocument.PICTURE_TYPE_PNG, file.getName(), width, height);

						// Add a new line after each image
						run.addBreak();
					}
				}

				// Write the document to a file
				try (FileOutputStream out = new FileOutputStream("output.docx")) {
					document.write(out);
				}

				System.out.println("PNG images inserted into Word document successfully.");
			} catch (Exception e) {
				e.printStackTrace();
			}
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
//        // Path to the specific folder
//        String folderPath = System.getProperty("user.dir") + "\\output.docx";
//
//        // Open the folder in the file explorer
//        File folder = new File(folderPath);
//
//        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
//            try {
//                Desktop.getDesktop().open(folder);
//                System.out.println("Folder opened successfully.");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.out.println("Desktop is not supported or the OPEN action is not supported.");
//        }
		// Path to the specific file
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
