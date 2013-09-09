package ua.kharkiv.epam.dereza.chain;

import java.io.File;

public class FileFilterMinSize extends FileFilter {

	private Long minSize;
	
	public FileFilterMinSize(Long minSize) {
		this(null, minSize);
	}
	
	public FileFilterMinSize(FileFilter next, Long minSize) {
		super(next);
		this.minSize = minSize;
	}

	@Override
	public boolean internalCheck(File file) {
		if (file.length() < minSize)
			return false;

		return true;
	}

}
