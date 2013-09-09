package ua.kharkiv.epam.dereza.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class FileWrapper implements Iterable<String>{
	
	private File sourceFile;
		
	public FileWrapper() {
		super();
	}
	
	public FileWrapper(File sourceFile) throws FileNotFoundException{
		this.sourceFile = sourceFile;
		initStream();
	}
	
	public File getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(File sourceFile) throws FileNotFoundException{
		this.sourceFile = sourceFile;
		initStream();
	}

	@Override
	public Iterator<String> iterator() {
		return new IteratorImpl();
	}
	
	private void initStream() throws FileNotFoundException{
		String message = null;
		if (sourceFile.isDirectory())
			message = "sourceFile.isDirectory()";
		if (!sourceFile.exists())
			message = "!sourceFile.exists()";
		if (message != null)
			throw new IllegalArgumentException(new IOException(message));
	}
	
	private class IteratorImpl implements Iterator<String>{
		
		private BufferedReader bfReadr;
		
		public IteratorImpl() {
			try{
				bfReadr = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile)));
			}catch(FileNotFoundException ex){
				throw new RuntimeException(ex);
			}
		}
		
		@Override
		public boolean hasNext() {
			try {
				return bfReadr.ready();
			} catch (IOException e) {
				throw new RuntimeException("promblems with stream", e);
			}
		}

		@Override
		public String next() {
			try {
				return bfReadr.readLine();
			} catch (IOException e) {
				throw new RuntimeException("promblems with stream", e);
			}	 
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
}
