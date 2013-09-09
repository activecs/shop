package ua.kharkiv.epam.dereza.chain;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileFilterName extends FileFilter {

	private String filterFilename;
	
	public FileFilterName(String filterFilename) {
		this(null, filterFilename);
	}
	
	public FileFilterName(FileFilter next, String filterFilename) {
		super(next);
		this.filterFilename = filterFilename;
	}

	@Override
	public boolean internalCheck(File file) {
		Pattern pattern = Pattern.compile("\\.\\w{1,}\\z");
		Matcher matcher = pattern.matcher(file.getName());
		String realFilename = matcher.replaceAll("");

		if (!realFilename.equals(filterFilename))
			return false;

		return true;
	}

}
