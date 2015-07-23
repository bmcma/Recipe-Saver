package viewlayer;

import java.io.FileNotFoundException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import pdfwriter.Writer;
import com.itextpdf.text.DocumentException;
import businesslayer.ValidationException;
import datatransferobjects.Recipe;

/*
 * Controller in the MVC pattern - interacts with the model and view
 * @author Brian McMahon
 */
public class RecipeController {

	// instantiate model object
	private final RecipeModel rModel;

	// initialize model object in constructor
	public RecipeController() {
		rModel = new RecipeModel();
	}

	// gets an instance of the recipe
	public Recipe getRecipe() {
		return rModel.getRecipe();
	}

	// gets the title of a recipe
	public String getTitle() {
		return rModel.getRecipe().getTitle();
	}

	// gets the ingredients of a recipe
	public String getIngredients() {
		return rModel.getRecipe().getIngredients();
	}

	// gets the instructions for a recipe
	public String getInstructions() {
		return rModel.getRecipe().getInstructions();
	}

	// sets the title for a recipe
	public void setTitle(String title) {
		rModel.getRecipe().setTitle(title);
	}

	// sets the ingredients for a recipe
	public void setIngredients(String ingredients) {
		rModel.getRecipe().setIngredients(ingredients);
	}

	// sets the instructions for a recipe
	public void setInstructions(String instructions) {
		rModel.getRecipe().setInstructions(instructions);
	}

	// gets all of the recipes from the database
	public DefaultTableModel getAllRecipes() {
		return rModel.getRbl().getAllRecipes();
	}

	// adds a new recipe to the database
	public int addRecipe(Recipe recipe) throws ValidationException {
		return rModel.getRbl().addRecipe(recipe);
	}

	// updates a recipe in the database
	public int updateRecipe(Recipe recipe) throws ValidationException {
		return rModel.getRbl().updateRecipe(recipe);
	}

	// searches for a recipe based on a keyword
	public DefaultTableModel getRecipeByKeyWord(String keyword) {
		return rModel.getRbl().getRecipeByKeyWord(keyword);
	}

	// deletes recipe from database
	public int deleteRecipe(Recipe recipe) {
		return rModel.getRbl().deleteRecipe(recipe);
	}

	// method to update the JTable and Database based on user entries
	public void update(DefaultTableModel model, JTable table) {
		// loop through the tables rows and update the database with all changes
		int lastRow = model.getRowCount();
		for (int i = 0; i < lastRow; i++) {
			// get the recipeId
			int recipeId = (int) model.getValueAt(i, 0);
			// get the title
			String title = (String) model.getValueAt(i, 1);
			// get the ingredients
			String ingredients = (String) model.getValueAt(i, 2);
			// get the instructions
			String instructions = (String) model.getValueAt(i, 3);
			// create new instance of a recipe and add values
			Recipe recipe = getRecipe();
			recipe.setRecipeId(recipeId);
			recipe.setTitle(title);
			recipe.setIngredients(ingredients);
			recipe.setInstructions(instructions);
			try {
				updateRecipe(recipe);
			} catch (ValidationException e1) {
				e1.printStackTrace();
			}
		}
	}

	// method to delete the recipe from the table and the database
	public void delete(DefaultTableModel model, JTable table) {
		int recipeId = (int) model.getValueAt(table.getSelectedRow(), 0);
		// get new instance of a recipe
		Recipe recipe = getRecipe();
		recipe.setRecipeId(recipeId);
		deleteRecipe(recipe);
		model.removeRow(table.getSelectedRow());
	}

	// output table or individual recipe to pdf format
	public void saveToPdf(DefaultTableModel model, JTable table) {

		String fileName = JOptionPane.showInputDialog("Enter the file name");
		// check to see if recipe was selected, if not print full table
		try {
			String title = (String) model.getValueAt(table.getSelectedRow(), 1);
			String ingredients = (String) model.getValueAt(
					table.getSelectedRow(), 2);
			String instructions = (String) model.getValueAt(
					table.getSelectedRow(), 3);
			try {
				// create instance of pdf Writer object
				Writer writer = rModel.getWriter(fileName);

				// add recipe title leftAligned
				writer.addToPage(writer.newParagraph(title, true, false, false));
				// newline
				writer.addToPage(writer.newParagraph("", false, false, false));
				// add recipe ingredients leftAligned
				writer.addToPage(writer.newParagraph(ingredients, true, false,
						false));
				// newline
				writer.addToPage(writer.newParagraph("", false, false, false));
				// add recipe instructions leftAligned
				writer.addToPage(writer.newParagraph(instructions, true, false,
						false));
				// close and save document
				writer.closeDoc();

				JOptionPane.showMessageDialog(null,
						"Recipe successfully saved to PDF", "RecipeSaved", 2);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}// end inner try/catch

		} catch (ArrayIndexOutOfBoundsException e1) {
			try {
				// create instance on pdf Writer object
				Writer writer = rModel.getWriter(fileName);
				// adds table to page
				writer.addToPage(writer.addTable(getNColumns(model),
						getTableData(model), table));
				// close and save document
				writer.closeDoc();

				JOptionPane.showMessageDialog(null,
						"Recipe successfully saved to PDF", "RecipeSaved",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (FileNotFoundException e11) {
				e11.printStackTrace();
			} catch (DocumentException e11) {
				e11.printStackTrace();
			}// end inner try/catch

		}// end try/catch
	}

	// method to get model information for pdf
	public static String getTableColNames(JTable table, int i) {
		return table.getModel().getColumnName(i).toString();
	}

	// get the number of columns for pdfTable
	private int getNColumns(DefaultTableModel model) {
		return model.getColumnCount();
	}

	// get the row data from the table to be used in pdf output
	private String[][] getTableData(DefaultTableModel model) {
		// int for row and col counts
		int rowCount = model.getRowCount(), colCount = model.getColumnCount();
		// 2d array to hold tableData
		String[][] tableData = new String[rowCount][colCount];
		// loop through data to populate array
		for (int i = 0; i < rowCount; i++)
			for (int j = 1; j < colCount; j++)
				tableData[i][j] = model.getValueAt(i, j).toString();

		// return the table data in a 2d array
		return tableData;

	}

}
