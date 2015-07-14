package dataaccesslayer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import datatransferobjects.Recipe;

/*
 * DataAccessObject interface implementation
 */

public class RecipeDataAccessObjectImplementation implements RecipeDataAccessObject{
	
	
	//queries for all the recipes in the database
	@Override
	public DefaultTableModel getAllRecipes() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DefaultTableModel recipes = null;
		Object [] columns = {"ID", "TITLE", "INGREDIENTS", "INSTRUCTIONS"};
		try{
			DataSource ds = new DataSource();
			con = ds.createConnection();
			pstmt = con.prepareStatement(
					"SELECT recipeId, title, ingredients, instructions FROM recipes ORDER BY recipeId");
			//query database
			rs = pstmt.executeQuery();
			Object [][] rows = null;
			recipes = new DefaultTableModel(rows, columns);
			
			Object row [];
			while(rs.next()){
				//add rows to table
				row = new Object[]{
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4)
				};
				recipes.addRow(row);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			//close the connection
			try{ if(rs != null){ rs.close(); } }
			catch(SQLException ex){System.out.println(ex.getMessage());}
			try{ if(pstmt != null){ pstmt.close(); }}
			catch(SQLException ex){System.out.println(ex.getMessage());}
			try{ if(con != null){ con.close(); }}
			catch(SQLException ex){System.out.println(ex.getMessage());}
		}
		return recipes;
	}

	//add a new recipe to the database
	@Override
	public int addRecipe(Recipe recipe) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int recordsChanged = 0;
		try{
			DataSource ds = new DataSource();
			con = ds.createConnection();
			//SQL insert statement
			pstmt = con.prepareStatement(
					"INSERT INTO recipes (title, ingredients, instructions) " +
			        "VALUES(?, ?, ?)");
			pstmt.setString(1, recipe.getTitle());
			pstmt.setString(2, recipe.getIngredients());
			pstmt.setString(3, recipe.getInstructions());
			recordsChanged = pstmt.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			//close connection
			try{ if(pstmt != null){ pstmt.close(); }}
			catch(SQLException ex){System.out.println(ex.getMessage());}
			try{ if(con != null){ con.close(); }}
			catch(SQLException ex){System.out.println(ex.getMessage());}
		}
		return recordsChanged;
	}
	
	//delete recipe from database
	@Override
	public int deleteRecipe(Recipe recipe) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int recordsChanged = 0;
		try{
			DataSource ds = new DataSource();
			con = ds.createConnection();
			//delete recipe which matches the primary key recipeId
			pstmt = con.prepareStatement("DELETE FROM recipes WHERE recipeId = ?");
			pstmt.setInt(1, recipe.getRecipeId());
			recordsChanged = pstmt.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			//close the connection
			try{ if(pstmt != null){ pstmt.close(); }}
			catch(SQLException ex){System.out.println(ex.getMessage());}
			try{ if(con != null){ con.close(); }}
			catch(SQLException ex){System.out.println(ex.getMessage());}
		}
		return recordsChanged;
	}
	
	//search for recipe based on keyword
	@Override
	public DefaultTableModel getRecipeByKeyWord(String keyword) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DefaultTableModel recipes = null;
		
		try{
			DataSource ds = new DataSource();
			con = ds.createConnection();
			//select recipes where the title contains the keyword
			pstmt = con.prepareStatement(
					"SELECT recipeId, title, ingredients, instructions FROM recipes WHERE title LIKE ?");
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			Object [] colNames = {"ID", "TITLE", "INGREDIENTS", "INSTRUCTIONS"};
			Object [][] rows = null;
			recipes = new DefaultTableModel(rows, colNames);
			Object[] row;
			while(rs.next()){
				row = new Object[]{
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4)
				};
				recipes.addRow(row);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			//close the connection
			try{ if(rs != null){ rs.close(); } }
			catch(SQLException ex){System.out.println(ex.getMessage());}
			try{ if(pstmt != null){ pstmt.close(); }}
			catch(SQLException ex){System.out.println(ex.getMessage());}
			try{ if(con != null){ con.close(); }}
			catch(SQLException ex){System.out.println(ex.getMessage());}
		}
		return recipes;
	}

	//update the recipe in the database
	@Override
	public int updateRecipe(Recipe recipe) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int recordsChanged = 0;
		try{
			DataSource ds = new DataSource();
			con = ds.createConnection();
			pstmt = con.prepareStatement("UPDATE recipes SET title = ?, ingredients = ?, instructions = ? WHERE recipeId = ?");
			pstmt.setString(1, recipe.getTitle());
			pstmt.setString(2, recipe.getIngredients());
			pstmt.setString(3, recipe.getInstructions());
			pstmt.setInt(4, recipe.getRecipeId());
			recordsChanged = pstmt.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			//close the connection
			try{ if(pstmt != null){pstmt.close();}}
			catch (SQLException ex) {System.out.println(ex.getMessage());}
			try{ if(con != null){con.close();}}
			catch(SQLException ex) {System.out.println(ex.getMessage());}
			
		}
		return recordsChanged;
	}

}
