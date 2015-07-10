package viewlayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import datatransferobjects.Recipe;
import businesslayer.RecipeBusinessLogic;
import businesslayer.ValidationException;

public class RecipeTest {

	public static void main(String[] args) {
		RecipeBusinessLogic rbl = new RecipeBusinessLogic();
		Recipe recipe = new Recipe();
		try {
			List<Recipe> recipes = new ArrayList<Recipe>();
			/*
			 * recipe.setTitle("Chicken Curry");
			 * recipe.setIngredients("Chicken, curry powder");
			 * recipe.setInstructions("Place in pan and simmer");
			 * 
			 * rbl.addRecipe(recipe);
			 * 
			 * recipe.setTitle("Beef Curry");
			 * recipe.setIngredients("Beef, curry powder");
			 * recipe.setInstructions("Place in pan and simmer");
			 * 
			 * rbl.addRecipe(recipe);
			 */


			recipe.setRecipeId(1);
			recipe.setTitle("Lamb Curry");
			recipe.setIngredients("Lamb, curry powder");
			recipe.setInstructions("Place in pan and simmer");
			rbl.updateRecipe(recipe);

			//recipes = rbl.getAllRecipes();
			for (Recipe recipeList : recipes)
				System.out.println(recipeList);
			
			recipe.setRecipeId(3);
			rbl.deleteRecipe(recipe);

			/*recipes = rbl.getRecipeByKeyWord("Chicken");
			for (Recipe recipeList : recipes)
				System.out.println(recipeList);*/

		} catch (ValidationException e) {
			System.out.print(e.getMessage());
		}
	}

}
