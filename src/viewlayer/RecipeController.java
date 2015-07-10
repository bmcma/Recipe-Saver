package viewlayer;
import java.util.Observer;
import javax.swing.table.DefaultTableModel;

import businesslayer.ValidationException;
import datatransferobjects.Recipe;

public class RecipeController {
	
	private final RecipeModel model;
	
	public RecipeController(){
		model = new RecipeModel();
	}
	
	public void addObserver(Observer o){
		model.addObserver(o);
	}
	
	public String getTitle(){
		return model.getTitle();
	}
	
	public String getIngredients(){
		return model.getIngredients();
	}
	
	public String getInstructions(){
		return model.getInstructions();
	}
	
	public DefaultTableModel getAllRecipes(){
		return model.getAllRecipes();
	}
	
	public int addRecipe(Recipe recipe) throws ValidationException{
		return model.addRecipe(recipe);
	}
	
	public int updateRecipe(Recipe recipe) throws ValidationException{
		return model.updateRecipe(recipe);
	}
	
	public DefaultTableModel getRecipeByKeyWord(String keyword){
		return model.getRecipeByKeyWord(keyword);
	}
	
	public int deleteRecipe(Recipe recipe) {
		return model.deleteRecipe(recipe);
	}
}
