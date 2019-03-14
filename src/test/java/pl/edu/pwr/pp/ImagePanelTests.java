package pl.edu.pwr.pp;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import org.junit.Assert;
import org.junit.Test;

public class ImagePanelTests {
	
	@Test
	public void ShouldWriteAsciiArtToTxtFile() {
		//given 
		String fileName = "textOutput2.txt";
		ImagePanel panel = new ImagePanel();
		BufferedImage img = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
		img.setRGB(0, 0, new Color(53,53,53).getRGB());
		
		//when
		panel.setOrginalImage(img);
		panel.setWidth(panel.getOrginalImage().getWidth());
		panel.setQuality(false);
		panel.saveAsciiartTxt(fileName);
		
		//then
		try(BufferedReader reader =new BufferedReader(new FileReader(fileName)))
		{
			assertEquals("#",reader.readLine());
			assertEquals(null, reader.readLine());
		}
		catch (Exception e)
		{
			Assert.fail("Should read the file");
		}
	}

}
