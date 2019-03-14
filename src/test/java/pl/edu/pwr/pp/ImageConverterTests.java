package pl.edu.pwr.pp;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.junit.Test;

public class ImageConverterTests {

	public static float R_SCALE = 0.2989f;
	public static float G_SCALE = 0.5870f;
	public static float B_SCALE = 0.1140f;
	//private ImageConverter imageConverter = new ImageConverter();
	@Test
	public void convertToGrayScaleTest() {
		
		
		BufferedImage image = new BufferedImage(1, 1,
				BufferedImage.TYPE_BYTE_GRAY);
		
		image.setRGB(0,0,0);
		Color color = new Color(1, 0, 0);	
		
		assertEquals(image.getRGB(0, 0),  ImageConverter.convertToGrayscale(image, color).getRGB(0, 0));
		
	}
	@Test
	public void convertToGrayScaleTestColor() {
		
		
		BufferedImage image = new BufferedImage(1, 1,
				BufferedImage.TYPE_INT_BGR);
		
		float red =  27;
		float green = 84;
		float blue = 34;
		
		int mean = Math.round(red*R_SCALE+green*G_SCALE+blue*B_SCALE);	
		
		Color color2 = new Color((int)red,(int)green,(int)blue);		
		image.setRGB(0,0,color2.getRGB());			
		BufferedImage result = ImageConverter.convertToGrayscale(image, new Color(0,0,0));		
		Color color = new Color(result.getRGB(0, 0));		
		assertEquals(mean, color.getRed());
		
	}
	
	@Test
	public void resizeImageTest() {
		BufferedImage image = new BufferedImage(4, 4,
				BufferedImage.TYPE_INT_BGR);
		
		
		BufferedImage result = ImageConverter.resizeImage(image, 1);				
		assertEquals(1, result.getWidth());
		
	}

	@Test
	public void AsciiToPngTest() {
		//given
		char[][] asciiMap = new char[][]{{'$'}};
		int mean=0;
		//when
		BufferedImage img = ImageConverter.asciiToImage(8,asciiMap);
		//then
		assertEquals(img.getHeight(),8);
		assertEquals(img.getWidth(),5);
		for(int x = 0; x<img.getWidth(); ++x){
			for(int y = 0; y<img.getHeight(); ++y){
				mean+=new Color(img.getRGB(x, y)).getRed();
			}
		}
		mean/=img.getWidth()*img.getHeight();
		assertThat(mean>0, is(equalTo(true)));
		assertThat(mean<255, is(equalTo(true)));
	}
}
