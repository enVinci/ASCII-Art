package pl.edu.pwr.pp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class ImageFileReader {

	/**
	 * Metoda czyta plik pgm i zwraca tablicę odcieni szarości.
	 * 
	 * @param fileName
	 *            nazwa pliku pgm
	 * @return tablica odcieni szarości odczytanych z pliku
	 * @throws URISyntaxException
	 *             jeżeli plik nie istnieje
	 */
	public static int[][] readPgmUrl(URL url) throws IOException {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
			return readPgm(in);
		}
	}

	public static int[][] readPgmFile(String fileName) throws IOException {

		try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
			return readPgm(in);
		}
	}

	public static int[][] readPgm(BufferedReader in) throws IOException {
		int columns = 0;
		int rows = 0;
		int maxIntensity = 0;
		int[][] intensities = null;

		try (Scanner scanner = new Scanner(in)) {
			// mega super regex
			scanner.useDelimiter("((#.*)?\\s+)+");

			// najpierw powinno być P2
			if (!scanner.hasNext("P2"))
				throw new IOException("Bad magic number");
			else
				scanner.next();

			// kolejno liczby columns i rows
			if (scanner.hasNextInt())
				columns = scanner.nextInt();
			else
				throw new IOException("No colums count");
			if (scanner.hasNextInt())
				rows = scanner.nextInt();
			else
				throw new IOException("No rows count");

			// maksymalny odcień szarości, na razie tylko 255 (brak
			// normalizacji)
			if (scanner.hasNextInt())
				maxIntensity = scanner.nextInt();
			else
				throw new IOException("No max intensity");
			if (maxIntensity != 255)
				throw new IOException("Unsupported max intensity");

			// inicjalizacja tablicy na odcienie szarości
			intensities = new int[rows][];

			for (int i = 0; i < rows; i++) {
				intensities[i] = new int[columns];
			}

			// wczytujemy kolejno wartości pikseli
			for (int currentRow = 0; currentRow < rows; ++currentRow) {
				for (int currentColumn = 0; currentColumn < columns; ++currentColumn) {
					if (scanner.hasNextInt())
						intensities[currentRow][currentColumn] = scanner.nextInt();
					else
						throw new IOException("No pixel value");

					if (intensities[currentRow][currentColumn] < 0
							|| intensities[currentRow][currentColumn] > maxIntensity) {
						throw new IOException("Illegal pixel value");
					}
				}
			}
		} catch (IOException e) {
			throw (e);
		}
		return intensities;
	}
}
