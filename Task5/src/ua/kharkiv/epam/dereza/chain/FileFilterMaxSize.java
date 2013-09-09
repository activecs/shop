package ua.kharkiv.epam.dereza.chain;

import java.io.File;

public class FileFilterMaxSize extends FileFilter {

	private Long maxSize;
	
	public FileFilterMaxSize(Long maxSize) {
		this(null, maxSize);
	}
	
	public FileFilterMaxSize(FileFilter next, Long maxSize) {
		super(next);
		this.maxSize = maxSize;
	}

	@Override
	public boolean internalCheck(File file) {
		if (file.length() > maxSize)
			return false;

		return true;
	}

}
