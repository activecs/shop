package ua.kharkiv.epam.dereza.chain;

import java.io.File;
import java.util.Date;

public class FileFilterMinModificationDate extends FileFilter {

	private Date minDate;

	public FileFilterMinModificationDate(Date minDate) {
		this(null, minDate);
	}

	public FileFilterMinModificationDate(FileFilter next, Date minDate) {
		super(next);
		this.minDate = minDate;
	}

	@Override
	public boolean internalCheck(File file) {
		Date lastModifyDate = new Date(file.lastModified());
		if (minDate.after(lastModifyDate))
			return false;
		
		return true;
	}

}
