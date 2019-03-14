package pl.edu.pwr.pp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class AsciiArtApplication {

	public static void main(String[] args) {

		String[] images = new String[] { "Marilyn_Monroe", "Mona_Lisa", "Sierpinski_Triangle" };
		String pgmExtension = ".pgm";
		String txtExtension = ".txt";

		for (String imageName : images) {
			try {
				String fileName="";
				URI uri;
				try {
					uri = ClassLoader.getSystemResource(imageName + pgmExtension).toURI();
					fileName=Paths.get(uri).toString();	
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				// przeczytaj plik pgm
				int[][] intensities = ImageFileReader.readPgmFile(fileName);
				// przekształć odcienie szarości do znaków ASCII
				char[][] ascii = ImageConverter.intensitiesToAscii(intensities);
				// zapisz ASCII art do pliku tekstowego
				ImageFileWriter.saveToTxtFile(ascii, imageName + txtExtension);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
