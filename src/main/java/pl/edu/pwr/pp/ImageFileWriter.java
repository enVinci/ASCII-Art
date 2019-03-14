package pl.edu.pwr.pp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

public class ImageFileWriter {

	public static void saveToTxtFile(char[][] ascii, String fileName) throws IOException {
		// np. korzystając z java.io.PrintWriter

		try (PrintWriter writer = new PrintWriter(fileName)) {
			for (int h = 0; h < ascii.length; ++h) {
				for (int w = 0; w < ascii[h].length; ++w) {
					writer.print(ascii[h][w]);
				}
				writer.println();
			}
		} catch (IOException e) {
			throw e;
		}
	}

	public static void saveToPngFile(BufferedImage img, String fileName) throws IOException {

		try {
			File file = new File(fileName);
			ImageIO.write(img, "PNG", file);
		} catch (

		Exception e) {
			throw new IOException();
		}
	}
}
