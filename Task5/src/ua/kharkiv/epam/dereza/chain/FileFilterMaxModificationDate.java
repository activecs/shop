package ua.kharkiv.epam.dereza.chain;

import java.io.File;
import java.util.Date;

public class FileFilterMaxModificationDate extends FileFilter {

	private Date maxDate;

	public FileFilterMaxModificationDate(Date maxDate) {
		this(null, maxDate);
	}

	public FileFilterMaxModificationDate(FileFilter next, Date maxDate) {
		super(next);
		this.maxDate = maxDate;
	}

	@Override
	public boolean internalCheck(File file) {
		Date lastModifyDate = new Date(file.lastModified());
		if (maxDate.before(lastModifyDate))
			return false;

		return true;
	}

}
