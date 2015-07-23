package pdfwriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.swing.JTable;
import viewlayer.RecipeController;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/*
 * Class to implement the iText API to allow saving to pdf formats
 */
public class Writer {
	// initialize a new document object
	private Document document = new Document();

	public Writer(String filename) throws FileNotFoundException,
			DocumentException {
		// get a new instance of PdfWriter
		PdfWriter.getInstance(this.getDocument(), new FileOutputStream(
				new NewPDF(filename)));
		// open the document for writing
		getDocument().open();
	}

	// add metadata
	public void addMetaData(String title, String subject, String author) {
		getDocument().addTitle(title);
		getDocument().addSubject(subject);
		getDocument().addAuthor(author);
	}

	// add element to the page
	public void addToPage(Element add) throws DocumentException {
		getDocument().add(add);
	}

	// new page
	public void newPage() {
		getDocument().newPage();
	}

	// new paragraph for page
	public Paragraph newParagraph(String text, boolean alignLeft,
			boolean alignCentre, boolean alignRight) {

		// instantiate new paragraph
		Paragraph para = new Paragraph();

		// set alignment
		if ((alignCentre) && (!alignLeft) && (!alignRight)) {
			para.setAlignment(Element.ALIGN_CENTER);
		} else if ((alignRight) && (!alignLeft) && (!alignCentre)) {
			para.setAlignment(Element.ALIGN_RIGHT);
		} else
			para.setAlignment(Element.ALIGN_LEFT);// if alignLeft is selected or
													// more than one alignment
													// is selected, then align
													// to the default left
													// position

		// adds text to the paragraph
		para.add(text);

		return para;
	}

	// add full table to pdf
	public Section addTable(int nColumns, String[][] rowData, JTable table) {
		// create new section
		Section tableSection = new Chapter(0).addSection(newParagraph("", true,
				false, false));

		PdfPTable newTable = new PdfPTable(nColumns);

		// loop through table to add columns
		for (int i = 1; i < nColumns; i++) {
			newTable.addCell(new PdfPCell(new Phrase(RecipeController
					.getTableColNames(table, i))));
		}
		// add data from table
		for (String[] data : rowData)
			for (String element : data)
				newTable.addCell(element);

		tableSection.add(newTable);
		return tableSection;
	}

	// close and save the document
	public void closeDoc() {
		document.close();
	}

	// getter to access document
	public Document getDocument() {
		return document;
	}

	// setter for document
	public void setDocument(Document document) {
		this.document = document;
	}

}
