 package pl.edu.pwr.pp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class ImageFileWriterTest {

	@Test
	public void shouldReadWrittenCharacters() {
		// given
		String fileName = "testOutput.txt";
		Random rand = new Random();
		char[][] ascii = new char[10][20];
		for (int h = 0; h < ascii.length; ++h) {
			for (int w = 0; w < ascii[h].length; ++w) {
				ascii[h][w] = (char)(32 + rand.nextInt(95));
			}
		}
		// when
		try {
			ImageFileWriter.saveToTxtFile(ascii, fileName);
		} catch (IOException e) {
			Assert.fail("File should be written");
		}
		// then
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			for (int h = 0; h < ascii.length; ++h) {
				String lineBuffer = reader.readLine();
				for (int w = 0; w < ascii[h].length; ++w) {
					assertThat(lineBuffer.charAt(w), is(equalTo(ascii[h][w])));
				}
			}
			assertThat(reader.readLine(), is(equalTo(null))); // end of file
		} catch (IOException e) {
			Assert.fail("File should be read");
		}
	}
}
