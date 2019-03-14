package pl.edu.pwr.pp;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collection;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ImageConverterParametrizedTest {

	@Parameters
	public static Collection<Object[]> data() {
		// @formatter:off
		return Arrays.asList(new Object[][] {
			{0, '@'}, {12, '@'}, {25, '@'}, 
			{26, '%'}, {34, '%'}, {51, '%'},
			{52, '#'}, {66, '#'}, {76, '#'},
			{77, '*'}, {89, '*'}, {102, '*'},
			{103, '+'}, {115, '+'}, {127, '+'},
			{128, '='}, {142, '='}, {153,'='},
			{154,'-'}, {162,'-'}, {179,'-'},
			{180, ':'}, {192, ':'}, {204, ':'},
			{205, '.'}, {215, '.'}, {230, '.'},
			{231, ' '}, {245, ' '}, {255, ' '}
		});
		// @formatter:on
	}

	private int input;
	private char expected;

	public ImageConverterParametrizedTest(int input, char expected) {
		this.input = input;
		this.expected = expected;
	}

	@Test
	public void shouldReturnRightCharacterForIntensity() {
		MatcherAssert.assertThat(ImageConverter.intensityToAscii(this.input),
				Matchers.is(Matchers.equalTo(this.expected)));
	}

	@Test
	public void shouldReturnRightCharacterForImageOfGivenIntensity() {
		// Given
		BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
		image.setRGB(0, 0, new Color(this.input, this.input, this.input).getRGB());
		//System.out.println(new Color(image.getRGB(0, 0)).getRed());
		char asciiFinal[][] = new char[][] { { this.expected } };

		// When
		char[][] ascii = ImageConverter.imageToAsciiLow(image);

		// Then
		MatcherAssert.assertThat(Arrays.deepEquals(ascii, asciiFinal), is(equalTo(true)));
	}
		
}

