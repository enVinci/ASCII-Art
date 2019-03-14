package pl.edu.pwr.pp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

public class ImageFileReaderTest {

	ImageFileReader imageReader;

	@Test
	public void shouldReadSequenceFrom0To255GivenTestImage() {
		// given
		String fileName="";
		URI uri;
		try {
			uri = ClassLoader.getSystemResource("testImage.pgm").toURI();
			fileName=Paths.get(uri).toString();	
		} catch (URISyntaxException e) {
			Assert.fail("Should find the file");
		}
		// when
		int[][] intensities = null;
		try {
			intensities = ImageFileReader.readPgmFile(fileName);
		} catch (IOException e) {
			Assert.fail("Should read the file");
		}
		// then
		int counter = 0;
		for (int[] row : intensities) {
			for (int intensity : row) {
				assertThat(intensity, is(equalTo(counter++)));
			}
		}
	}

	@Test
	public void shouldReadSequenceFrom0To255GivenScrambledTestImageWithMultipleComments() {
		// given
		String fileName="";
		URI uri;
		try {
			uri = ClassLoader.getSystemResource("scrambledTestImage.pgm").toURI();
			fileName=Paths.get(uri).toString();	
		} catch (URISyntaxException e) {
			Assert.fail("Should find the file");
		}
		
		// when
		int[][] intensities = null;
		try {
			intensities = ImageFileReader.readPgmFile(fileName);
		} catch (IOException e) {
			Assert.fail("Should read the file");
		}
		// then
		int counter = 0;
		for (int[] row : intensities) {
			for (int intensity : row) {
				assertThat(intensity, is(equalTo(counter++)));
			}
		}
	}

	@Test
	public void shouldThrowExceptionWhenFileDontExist() {
		// given
		String fileName = "nonexistent.pgm";
		try {
			// when
			ImageFileReader.readPgmFile(fileName);
			// then
			Assert.fail("Should throw exception");
		} catch (Exception e) {
			assertThat(e, is(instanceOf(IOException.class)));
		}

	}
}
