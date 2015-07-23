package pdfwriter;

import java.io.File;
import java.io.IOException;

public class NewPDF extends File {

	/*
	 * @author Brian McMahon Class to create a new pdf file.
	 */
	private static final long serialVersionUID = 1L;

	public NewPDF(String fileName) {
		super(fileName + ".pdf");
	}

	@Override
	public boolean createNewFile() {
		try {
			super.createNewFile();
			// return true as new files is created
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			// return false as new file was not created
			return false;
		}
	}

}
