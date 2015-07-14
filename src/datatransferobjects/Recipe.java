package datatransferobjects;
/*
 * Class to create Recipes
 */
public class Recipe {
	
	//private variables for Recipe 
	private int recipeId;
	private String title;
	private String ingredients;
	private String instructions;
	
	//getters and setters for Recipe
	public String getTitle() {
		return title;
	}

	public String getIngredients() {
		return ingredients;
	}

	public String getInstructions() {
		return instructions;
	}

	public int getRecipeId() {
		return recipeId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	/*@Override
	public String toString() {
		String output = String.format("ID: %s \nTITLE: %s \nINGREDIENTS: %s \nINSTRUCTIONS: %s \n", String.valueOf(this.getRecipeId()), this.getTitle().toUpperCase(), 
				this.getIngredients(), this.getInstructions());
		return output;
	}*/

}
