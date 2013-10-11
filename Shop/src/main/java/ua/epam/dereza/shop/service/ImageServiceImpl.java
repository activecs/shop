package ua.epam.dereza.shop.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.fileupload.FileItem;

import ua.epam.dereza.shop.db.dto.UserDTO;
import ua.epam.dereza.shop.util.ImageUtil;

/**
 * Service for uploading and downloading images
 * 
 * @author Eduard_Dereza
 *
 */
public class ImageServiceImpl implements ImageService {

	private String baseDir;
	private String avatarDir;
	private static final String AVATAR_EXTENTION = "png";
	private static final String AVATAR_DEFAULT = "default.png";
	private static final int AVATAR_MAX_WIDTH = 400;

	public ImageServiceImpl(String baseDir) {
		this.baseDir = baseDir;
		avatarDir = this.baseDir + "avatar\\";
	}

	@Override
	public FileInputStream getAvatar(UserDTO user) throws FileNotFoundException {
		String avatarName = user.getAvatar();
		File avatar = new File(avatarDir + avatarName);
		avatar.mkdirs();
		FileInputStream stream;

		if (avatar.isDirectory() || !avatar.exists()) {
			avatar = new File(avatarDir + AVATAR_DEFAULT);
		}
		stream = new FileInputStream(avatar);

		return stream;
	}

	@Override
	public void saveAvatar(FileItem image, UserDTO user) throws IOException {
		if(image.getSize() == 0){
			user.setAvatar(AVATAR_DEFAULT);
			return;
		}
		user.setAvatar(user.getEmail() + AVATAR_EXTENTION);
		File avatarFile = new File(avatarDir + user.getAvatar());
		FileOutputStream avatarOut = new FileOutputStream(avatarFile);

		BufferedImage bufImage = ImageUtil.convertAndScale(image.getInputStream(), AVATAR_EXTENTION, AVATAR_MAX_WIDTH);
		ImageUtil.writeImageTo(avatarOut, bufImage, AVATAR_EXTENTION);
	}



}
