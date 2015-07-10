package dataaccesslayer;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

import datatransferobjects.Recipe;
public interface RecipeDataAccessObject {
	DefaultTableModel getAllRecipes();
	
	DefaultTableModel getRecipeByKeyWord(String keyword);
	int addRecipe(Recipe recipe);
	int updateRecipe(Recipe recipe);
	int deleteRecipe(Recipe recipe);
}
