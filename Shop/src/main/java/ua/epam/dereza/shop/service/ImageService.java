package ua.epam.dereza.shop.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.fileupload.FileItem;

import ua.epam.dereza.shop.bean.User;

/**
 * Service for uploading and downloading images
 * 
 * @author Eduard_Dereza
 *
 */
public interface ImageService {

	/**
	 * Return user's avatar
	 * 
	 * @param user
	 * @return stream of avatar file
	 * @throws FileNotFoundException
	 */
	public FileInputStream getAvatar(User user) throws FileNotFoundException;

	/**
	 * Saves user's avatar
	 * 
	 * @param image
	 * @param user
	 * @throws IOException
	 */
	public void saveAvatar(FileItem image, User user) throws IOException;
}
