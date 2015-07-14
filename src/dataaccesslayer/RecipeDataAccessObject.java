package dataaccesslayer;
import javax.swing.table.DefaultTableModel;
import datatransferobjects.Recipe;

/*
 * DataAccessObject interface
 */
public interface RecipeDataAccessObject {
	DefaultTableModel getAllRecipes();
	DefaultTableModel getRecipeByKeyWord(String keyword);
	int addRecipe(Recipe recipe);
	int updateRecipe(Recipe recipe);
	int deleteRecipe(Recipe recipe);
}
