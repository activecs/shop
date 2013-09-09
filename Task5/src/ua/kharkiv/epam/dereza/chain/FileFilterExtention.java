package ua.kharkiv.epam.dereza.chain;

import java.io.File;

public class FileFilterExtention extends FileFilter {

	private String fileExtention;
	
	public FileFilterExtention(String fileExtention) {
		this(null, fileExtention);
	}
	
	public FileFilterExtention(FileFilter next, String fileExtention) {
		super(next);
		this.fileExtention = fileExtention;
	}

	@Override
	public boolean internalCheck(File file) {
		if(!file.getName().endsWith((fileExtention)))
			return false;
		
		return true;
	}

}
