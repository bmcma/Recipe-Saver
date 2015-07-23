package viewlayer;

import java.io.FileNotFoundException;
import com.itextpdf.text.DocumentException;
import pdfwriter.Writer;
import datatransferobjects.Recipe;
import businesslayer.RecipeBusinessLogic;

/*
 * Model for the MVC pattern - interacts with the data
 */

public class RecipeModel{
	// variables needed to access and manipulate data
	private RecipeBusinessLogic rbl;
	private Recipe recipe;
	private Writer writer;

	// initialize recipe and rbl variable
	public RecipeModel() {
		rbl = new RecipeBusinessLogic();
		recipe = new Recipe();
	}

	// getters for variables
	public Recipe getRecipe() {
		return recipe;
	}

	public RecipeBusinessLogic getRbl() {
		return rbl;
	}

	public Writer getWriter(String fn) throws FileNotFoundException,
			DocumentException {
		// initialize writer to allow it to accept passed in parameters
		writer = new Writer(fn);
		return writer;
	}

}
