package businesslayer;
import javax.swing.table.DefaultTableModel;
import dataaccesslayer.RecipeDataAccessObject;
import dataaccesslayer.RecipeDataAccessObjectImplementation;
import datatransferobjects.Recipe;

/*
 * Business logic for recipe builder. Ensures user inputs are tested and match 
 */

public class RecipeBusinessLogic {
	//used to test for max length values in recipe inputs
	public static final int TITLE_MAX_LENGTH = 150;
	public static final int INGREDIENTS_MAX_LENGTH = 600;
	public static final int INSTRUCTIONS_MAX_LENGTH = 1500;
	
	private RecipeDataAccessObject recipeDAO;
	
	public RecipeBusinessLogic(){
		recipeDAO = new RecipeDataAccessObjectImplementation();
	}
	
	//returns a DefaultTableModel containing all recipes
	public DefaultTableModel getAllRecipes(){
		return recipeDAO.getAllRecipes();
	}
	
	//adds a recipe to the database
	public int addRecipe(Recipe recipe) throws ValidationException{
		validateRecipe(recipe);
		return recipeDAO.addRecipe(recipe);
	}
	
	//updates recipe in the database
	public int updateRecipe(Recipe recipe) throws ValidationException{
		validateRecipe(recipe);
		return recipeDAO.updateRecipe(recipe);
	}
	
	//returns a DefaultTableModel with all recipes containing keyword
	public DefaultTableModel getRecipeByKeyWord(String keyword){
		return recipeDAO.getRecipeByKeyWord(keyword);
	}
	
	//deletes recipe from the database
	public int deleteRecipe(Recipe recipe) {
		return recipeDAO.deleteRecipe(recipe);
	}
	
	//validates inputs
	public void validateInput(String input, String column, int maxLength, boolean canBeNull) throws ValidationException{
		if(input == null && canBeNull){
			//do nothing as null is a valid input
		}
		else if(input == null && !canBeNull){
			throw new ValidationException(String.format("%s column cannot be left empty", column));
		}
		else if(input.length() == 0){
			throw new ValidationException(String.format("%s column cannot be left empty", column));
		}
		else if(input.length() > maxLength){
			throw new ValidationException(String.format("%s column can't exceed max characters of %d", column, maxLength));
		}
		
	}
	
	//validates recipe based on inputs
	public void validateRecipe(Recipe recipe) throws ValidationException{
		validateInput(recipe.getTitle(), "title", TITLE_MAX_LENGTH, true);
		validateInput(recipe.getIngredients(), "ingredients", INGREDIENTS_MAX_LENGTH, true);
		validateInput(recipe.getInstructions(), "instructions", INSTRUCTIONS_MAX_LENGTH, true);
	}
	
}
