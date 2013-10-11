package ua.epam.dereza.shop.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.apache.commons.fileupload.FileItem;

/**
 * Image utility class
 * 
 * @author Eduard_Dereza
 *
 */
public class ImageUtil {

	private static final String[] IMAGE_EXTENTIONS = {"png", "gif", "jpg"};

	/**
	 * Scales image
	 * 
	 * @param sbi image to scale
	 * @param scale factor for transformation / scaling
	 * @return scaled image
	 */
	public static BufferedImage scale(BufferedImage sbi, double scale) {
		BufferedImage dbi = null;
		if(sbi != null) {
			dbi = new BufferedImage((int)(sbi.getWidth()*scale), (int)(sbi.getHeight()*scale), BufferedImage.TYPE_INT_RGB);
			Graphics2D g = dbi.createGraphics();
			AffineTransform at = AffineTransform.getScaleInstance(scale, scale);
			g.drawRenderedImage(sbi, at);
		}
		return dbi;
	}

	/**
	 * Converts image and writes it with given extension
	 * 
	 * @param inputImage
	 * @param outputImage
	 * @param desExt
	 * @throws IOException
	 */
	public static void convert(InputStream inputImage, OutputStream outputImage, String desExt) throws IOException{
		//read image file
		BufferedImage originalImage = ImageIO.read(inputImage);

		// create a blank, RGB, same width and height, and a white background
		BufferedImage newBufferedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		newBufferedImage.createGraphics().drawImage(originalImage, 0, 0, Color.WHITE, null);

		// write image to outputStream
		ImageIO.write(newBufferedImage, desExt, outputImage);
		outputImage.flush();
		inputImage.close();
		outputImage.close();
	}

	/**
	 * Converts, scales image
	 * 
	 * @param inputImage
	 * @param desExt - desired image extension
	 * @param desWidth - desired image width
	 * @throws IOException
	 */
	public static BufferedImage convertAndScale(InputStream inputImage, String desExt, int desWidth) throws IOException{
		//read image file
		BufferedImage originalImage = ImageIO.read(inputImage);

		// create a blank, RGB, same width and height, and a white background
		BufferedImage newBufferedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		newBufferedImage.createGraphics().drawImage(originalImage, 0, 0, Color.WHITE, null);

		// calculate scale
		double ratio = (double)desWidth/newBufferedImage.getWidth();
		double scale = ratio > 0 ? ratio : 1;
		newBufferedImage = scale(newBufferedImage, scale);

		return newBufferedImage;
	}

	/**
	 *  write image to outputStream with given extension
	 * 
	 * @param out
	 * @param image
	 * @param extension
	 * @throws IOException
	 */
	public static void writeImageTo(OutputStream out, BufferedImage image, String extension) throws IOException{
		ImageIO.write(image, extension, out);
		out.flush();
		out.close();
	}

	/**
	 * Validates avatar's extension(we allow only jpeg, gif, png)
	 * 
	 * @param extension
	 * @return true if imame's extension is valid
	 */
	public static boolean validateImageExtension(String ext){
		if(!Arrays.asList(IMAGE_EXTENTIONS).contains(ext))
			return false;

		return true;
	}

	/**
	 * Parses file's extension
	 * 
	 * @param item
	 * @return file extension
	 */
	public static String getFileExtension(FileItem item){
		if(item.isFormField())
			throw new IllegalArgumentException("Wrong item type");
		String ext = item.getName().replaceAll("[\\w|\\d|\\.]{1,}\\.", "");

		return ext;
	}
}
