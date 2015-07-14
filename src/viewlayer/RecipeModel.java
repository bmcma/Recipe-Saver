package viewlayer;
import java.util.Observable;
import javax.swing.table.DefaultTableModel;
import datatransferobjects.Recipe;
import businesslayer.RecipeBusinessLogic;
import businesslayer.ValidationException;

/*
 * Model for the MVC pattern - interacts with the data
 */

public class RecipeModel extends Observable{
	
	private RecipeBusinessLogic rpl;
	private Recipe recipe;
	
	public RecipeModel(){
		rpl = new RecipeBusinessLogic();
		recipe = new Recipe();
	}
	
	public String getTitle(){
		return recipe.getTitle();
	}
	
	public String getIngredients(){
		return recipe.getIngredients();
	}
	
	public String getInstructions(){
		return recipe.getInstructions();
	}
	
	public DefaultTableModel getAllRecipes(){
		return rpl.getAllRecipes();
	}
	
	public int addRecipe(Recipe recipe) throws ValidationException{
		rpl.validateRecipe(recipe);
		return rpl.addRecipe(recipe);
	}
	
	public int updateRecipe(Recipe recipe) throws ValidationException{
		rpl.validateRecipe(recipe);
		return rpl.updateRecipe(recipe);
	}
	
	public DefaultTableModel getRecipeByKeyWord(String keyword){
		return rpl.getRecipeByKeyWord(keyword);
	}
	
	public int deleteRecipe(Recipe recipe) {
		return rpl.deleteRecipe(recipe);
	}

}
