package pl.edu.pwr.pp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Color;
import java.awt.Font;

public class ImagePanel extends JPanel {
	private enum imageType {
		ORGINAL, GRAYSCALE, ASCII
	}

	private static int FONTSIZE = 8;
	private static final long serialVersionUID = 1L;
	private BufferedImage orginalImage = null;
	private BufferedImage orginalScaledImage = null;
	private BufferedImage grayscaleImage = null;
	imageType currentImage = imageType.ORGINAL;
	private char[][] asciiMap = null;
	private Color bgColor = Color.WHITE;
	private int imageWidth = 80;
	private boolean quality = false; // false-niska, true-wysoka

	private void convert() {
		if (orginalImage == null)
			return;
		orginalScaledImage = ImageConverter.resizeImage(orginalImage, imageWidth);
		grayscaleImage = ImageConverter.convertToGrayscale(orginalScaledImage, bgColor);
		if (quality) {
			asciiMap = ImageConverter.imageToAsciiHigh(grayscaleImage);
		} else {
			asciiMap = ImageConverter.imageToAsciiLow(grayscaleImage);
		}
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
		convert();
		repaint();
	}

	public void setWidth(int imageWidth) {
		if (imageWidth > 0) {
			this.imageWidth = imageWidth;
			convert();
			repaint();
		}
	}

	public void setQuality(boolean quality) {
		this.quality = quality;
		convert();
		repaint();
	}

	public void setOrginalImage(BufferedImage image) {
		clearStoredImages();
		orginalImage = image;
		convert();
		repaint();
	}

	public void clearStoredImages() {
		currentImage = imageType.ORGINAL;
		orginalImage = null;
		orginalScaledImage = null;
		grayscaleImage = null;
		asciiMap = null;
	}

	public BufferedImage getOrginalImage() {
		return orginalImage;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		switch (currentImage) {
		case ORGINAL:
			if (orginalImage != null) {
				g2d.drawImage(orginalScaledImage, 0, 0, orginalScaledImage.getWidth(null),
						orginalScaledImage.getHeight(null), null);
			}
			break;
		case GRAYSCALE:
			if (grayscaleImage != null) {
				g2d.drawImage(grayscaleImage, 0, 0, grayscaleImage.getWidth(null), grayscaleImage.getHeight(null),
						null);
			}
			break;
		case ASCII:
			if (asciiMap != null) {

				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2d.setFont(new Font("Monospaced", Font.PLAIN, FONTSIZE));
				for (int i = 0; i < asciiMap.length; i++) {
					g.drawChars(asciiMap[i], 0, asciiMap[i].length, 10, i * FONTSIZE);
				}
			}
			break;
		}
	}

	public void paintOrginalImage() {
		currentImage = imageType.ORGINAL;
		this.convert();
		this.repaint();
	}

	public void paintGrayscaleImage() {
		currentImage = imageType.GRAYSCALE;
		this.convert();
		this.repaint();
	}

	public void paintAscii() {
		currentImage = imageType.ASCII;
		this.convert();
		this.repaint();
	}


	public void saveAsciiartPng(int fontSize, String path) {
		if (asciiMap != null) {
			try {
				BufferedImage img = ImageConverter.asciiToImage(fontSize, asciiMap);
				try {
					ImageFileWriter.saveToPngFile(img, path);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Wystąpił błąd przy zapisie do pliku");
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Wystąpił błąd przy konwersji AsciiArt do pliku png, być może obrazek wynikowy jest za duży?");
			}
		} else {
			JOptionPane.showMessageDialog(null, "AsciiArt nie został jeszcze wygenerowany!");
		}
	}

	public void saveAsciiartTxt(String path) {
		try {
			ImageFileWriter.saveToTxtFile(asciiMap, path);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Wystąpił błąd przy zapisie do pliku");
		}
	}
}