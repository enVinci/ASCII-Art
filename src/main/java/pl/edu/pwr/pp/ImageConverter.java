package pl.edu.pwr.pp;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.stream.IntStream;

import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class ImageConverter {

	/**
	 * Znaki odpowiadające kolejnym poziomom odcieni szarości - od czarnego (0)
	 * do białego (255).
	 */
	public static String INTENSITY_2_ASCII = "@%#*+=-:. ";
	public static String INTENSITY_2_ASCII_HIGH = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ";
	public static int MAX_INTENSITY = 255;
	public static float R_SCALE = 0.2989f;
	public static float G_SCALE = 0.5870f;
	public static float B_SCALE = 0.1140f;

	/**
	 * Metoda zwraca znak odpowiadający danemu odcieniowi szarości. Odcienie
	 * szarości mogą przyjmować wartości z zakresu [0,255]. Zakres jest dzielony
	 * na równe przedziały, liczba przedziałów jest równa ilości znaków w
	 * {@value #INTENSITY_2_ASCII}. Zwracany znak jest znakiem dla przedziału,
	 * do którego należy zadany odcień szarości.
	 * 
	 * 
	 * @param intensity
	 *            odcień szarości w zakresie od 0 do 255
	 * @return znak odpowiadający zadanemu odcieniowi szarości
	 */

	public static char intensityToAscii(int intensity) {
		return INTENSITY_2_ASCII.charAt(intensity * INTENSITY_2_ASCII.length() / (MAX_INTENSITY + 1));
	}

	public static char intensityToAsciiHigh(int intensity) {
		return INTENSITY_2_ASCII_HIGH.charAt(intensity * INTENSITY_2_ASCII_HIGH.length() / (MAX_INTENSITY + 1));
	}

	/**
	 * Metoda zwraca dwuwymiarową tablicę znaków ASCII mając dwuwymiarową
	 * tablicę odcieni szarości. Metoda iteruje po elementach tablicy odcieni
	 * szarości i dla każdego elementu wywołuje {@ref #intensityToAscii(int)}.
	 * 
	 * @param intensities
	 *            tablica odcieni szarości obrazu
	 * @return tablica znaków ASCII
	 */
	public static char[][] intensitiesToAscii(int[][] intensities) {
		char[][] ascii = new char[intensities.length][];

		for (int i = 0; i < intensities.length; ++i) {
			ascii[i] = new char[intensities[i].length];
			for (int j = 0; j < intensities[i].length; ++j) {
				ascii[i][j] = intensityToAscii(intensities[i][j]);
			}
		}
		return ascii;
	}

	public static char[][] imageToAsciiLow(BufferedImage grayImage) {
		char[][] ascii = new char[grayImage.getHeight()][grayImage.getWidth()];

		IntStream.range(0, grayImage.getWidth() * grayImage.getHeight())
				.forEach(i -> ascii[i / grayImage.getWidth()][i % grayImage.getWidth()] = intensityToAscii(
						new Color(grayImage.getRGB(i % grayImage.getWidth(), i / grayImage.getWidth())).getRed()));
		return ascii;
	}

	public static char[][] imageToAsciiHigh(BufferedImage grayImage) {
		char[][] ascii = new char[grayImage.getHeight()][grayImage.getWidth()];

		IntStream.range(0, grayImage.getWidth() * grayImage.getHeight())
				.forEach(i -> ascii[i / grayImage.getWidth()][i % grayImage.getWidth()] = intensityToAsciiHigh(
						new Color(grayImage.getRGB(i % grayImage.getWidth(), i / grayImage.getWidth())).getRed()));
		return ascii;
	}

	public static BufferedImage intensitiesToImage(int[][] intensities) {
		BufferedImage image = null;
		if (intensities.length > 0 && intensities[0].length > 0) {
			image = new BufferedImage(intensities[0].length, intensities.length, BufferedImage.TYPE_BYTE_GRAY);
			WritableRaster raster = image.getRaster();
			for (int y = 0; y < intensities.length; y++) {
				for (int x = 0; (x < intensities[y].length); x++) {
					raster.setSample(x, y, 0, intensities[y][x]);
				}
			}
		}
		return image;
	}

	public static BufferedImage convertToGrayscale(BufferedImage colorImage, Color bgColor) {
		if (colorImage.getType() != BufferedImage.TYPE_BYTE_GRAY) {
			BufferedImage gray = new BufferedImage(colorImage.getWidth(), colorImage.getHeight(),
					BufferedImage.TYPE_BYTE_GRAY);
			for (int y = 0; y < colorImage.getHeight(); y++) {
				for (int x = 0; x < colorImage.getWidth(); x++) {
					Color pixelColor = new Color(colorImage.getRGB(x, y), true);
					int r = (int) ((pixelColor.getRed() * pixelColor.getAlpha()
							+ bgColor.getRed() * (255 - pixelColor.getAlpha())) * R_SCALE / 255);
					int g = (int) ((pixelColor.getGreen() * pixelColor.getAlpha()
							+ bgColor.getGreen() * (255 - pixelColor.getAlpha())) * G_SCALE / 255);
					int b = (int) ((pixelColor.getBlue() * pixelColor.getAlpha()
							+ bgColor.getBlue() * (255 - pixelColor.getAlpha())) * B_SCALE / 255);
					int w = r + g + b;
					gray.setRGB(x, y, new Color(w, w, w).getRGB());
				}
			}
			return gray;
		}
		return colorImage;
	}

	public static BufferedImage resizeImage(BufferedImage orginalImage, int w) {
		int h = orginalImage.getHeight() * w / orginalImage.getWidth();
		BufferedImage resizedImage = new BufferedImage(w, h, orginalImage.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(orginalImage, 0, 0, w, h, null);
		g.dispose();
		return resizedImage;
	}
	
	public static BufferedImage asciiToImage(int fontSize, char[][]asciiMap) throws IndexOutOfBoundsException {
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, true, false);
		Font font = new Font("Monospaced", Font.PLAIN, fontSize);
		int w = (int) (font.getStringBounds(new String(asciiMap[0]), frc).getWidth());
		int h = fontSize * asciiMap.length;
		if(w>5000 || h>5000) throw new IndexOutOfBoundsException();
		JOptionPane.showMessageDialog(null, "Rozmiar stworzonego obrazka to " +w + " " + h + " pikseli");
		try {
			BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = img.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setPaint(Color.WHITE);
			g.fillRect(0, 0, w, h);
			g.setPaint(Color.BLACK);
			g.setFont(font);
			for (int i = 0; i < asciiMap.length; i++) {
				g.drawChars(asciiMap[i], 0, asciiMap[i].length, 0, i * fontSize);
			}
			g.dispose();
			return img;
		} catch (Exception e) {
			throw new IndexOutOfBoundsException();
		}
	}
}
