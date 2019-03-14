
package pl.edu.pwr.pp;

import java.util.Arrays;
import java.util.Collection;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ImageConverterParametrizedTestHighQuality {

	@Parameters
	public static Collection<Object[]> data() {
		// @formatter:off
		return Arrays.asList(new Object[][] {
			{0, '$'}, {3, '$'}, 
			{4, '@'}, {7, '@'}, 
			{8, 'B'}, {10, 'B'}, 
			{11, '%'}, {14, '%'}, 
			{15, '8'}, {18, '8'}, 
			{19, '&'}, {21, '&'}, 
			{22, 'W'}, {25, 'W'}, 
			{26, 'M'}, {29, 'M'}, 
			{30, '#'}, {32, '#'}, 
			{33, '*'}, {36, '*'}, 
			{37, 'o'}, {40, 'o'}, 
			{41, 'a'}, {43, 'a'}, 
			{44, 'h'}, {47, 'h'}, 
			{48, 'k'}, {51, 'k'}, 
			{52, 'b'}, {54, 'b'}, 
			{55, 'd'}, {58, 'd'}, 
			{59, 'p'}, {62, 'p'}, 
			{63, 'q'}, {65, 'q'}, 
			{66, 'w'}, {69, 'w'}, 
			{70, 'm'}, {73, 'm'}, 
			{74, 'Z'}, {76, 'Z'}, 
			{77, 'O'}, {80, 'O'}, 
			{81, '0'}, {84, '0'}, 
			{85, 'Q'}, {87, 'Q'}, 
			{88, 'L'}, {91, 'L'}, 
			{92, 'C'}, {95, 'C'}, 
			{96, 'J'}, {98, 'J'}, 
			{99, 'U'}, {102, 'U'}, 
			{103, 'Y'}, {106, 'Y'}, 
			{107, 'X'}, {109, 'X'}, 
			{110, 'z'}, {113, 'z'}, 
			{114, 'c'}, {117, 'c'}, 
			{118, 'v'}, {120, 'v'}, 
			{121, 'u'}, {124, 'u'}, 
			{125, 'n'}, {127, 'n'}, 
			{128, 'x'}, {131, 'x'}, 
			{132, 'r'}, {135, 'r'}, 
			{136, 'j'}, {138, 'j'}, 
			{139, 'f'}, {142, 'f'}, 
			{143, 't'}, {146, 't'}, 
			{147, '/'}, {149, '/'}, 
			{150, '\\'}, {153, '\\'}, 
			{154, '|'}, {157, '|'}, 
			{158, '('}, {160, '('}, 
			{161, ')'}, {164, ')'}, 
			{165, '1'}, {168, '1'}, 
			{169, '{'}, {171, '{'}, 
			{172, '}'}, {175, '}'}, 
			{176, '['}, {179, '['}, 
			{180, ']'}, {182, ']'}, 
			{183, '?'}, {186, '?'}, 
			{187, '-'}, {190, '-'}, 
			{191, '_'}, {193, '_'}, 
			{194, '+'}, {197, '+'}, 
			{198, '~'}, {201, '~'}, 
			{202, '<'}, {204, '<'}, 
			{205, '>'}, {208, '>'}, 
			{209, 'i'}, {212, 'i'}, 
			{213, '!'}, {215, '!'}, 
			{216, 'l'}, {219, 'l'}, 
			{220, 'I'}, {223, 'I'}, 
			{224, ';'}, {226, ';'}, 
			{227, ':'}, {230, ':'}, 
			{231, ','}, {234, ','}, 
			{235, '"'}, {237, '"'}, 
			{238, '^'}, {241, '^'}, 
			{242, '`'}, {245, '`'}, 
			{246, '\''}, {248, '\''}, 
			{249, '.'}, {252, '.'}, 
			{253, ' '}, {255, ' '}
		});
		// @formatter:on
	}

	private int input;
	private char expected;

	public ImageConverterParametrizedTestHighQuality(int input, char expected) {

		this.input = input;
		this.expected = expected;
	}

	@Test
	public void shouldReturnRightCharacterForIntensity() {
		MatcherAssert.assertThat(ImageConverter.intensityToAsciiHigh(this.input),
				Matchers.is(Matchers.equalTo(this.expected)));

	}
	
	/*
	@Test
	public void shouldReturnRightCharacterForImageOfGivenIntensity() {
		//Given
		BufferedImage image = new BufferedImage(1, 1,BufferedImage.TYPE_BYTE_GRAY);	
		image.setRGB(0, 0, new Color(this.input, this.input, this.input,255).getRGB());
		System.out.println(new Color(this.input, this.input, this.input,255).getRGB() & 0xff);
		System.out.println(image.getRGB(0,0) & 0xff);
		
		//BufferedImage image = new BufferedImage(1, 1,BufferedImage.TYPE_BYTE_GRAY);	
		//WritableRaster raster = image.getRaster();
		//int[] color = new int[]{this.input, this.input, this.input};
		//raster.setPixel(0, 0, color);
		//System.out.println(raster.getSample(0,0,0));
		//System.out.println(new Color(image.getRGB(0,0)).getRed());
		
		char asciiFinal[][] = new char[][]{{this.expected}};
		
		//When
		char[][] ascii =ImageConverter.imageToAsciiHigh(image);
		
		//Then
		MatcherAssert.assertThat(Arrays.deepEquals(ascii, asciiFinal),is(equalTo(true)));
	}*/
}