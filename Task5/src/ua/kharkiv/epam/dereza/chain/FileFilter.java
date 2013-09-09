package ua.kharkiv.epam.dereza.chain;

import java.io.File;

public abstract class FileFilter {
	
	private FileFilter next;
	
	public FileFilter(FileFilter next) {
		this.next = next;
	}
	
	public boolean check(File file) {
		if (!internalCheck(file))
			return false;
		
		// if we reach end of chain
		if (next == null)
			return true;
		return next.check(file);
	};
	
	protected abstract boolean internalCheck(File file);
	
}
