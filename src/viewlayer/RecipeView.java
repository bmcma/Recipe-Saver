package viewlayer;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import businesslayer.ValidationException;
import datatransferobjects.Recipe;

//TODO Change layout to GridBagLayout
//TODO Add print functionality

public class RecipeView implements Observer {
	private JFrame frame = new JFrame("Recipe Builder");
	private DefaultTableModel model, model1;
	private JTable searchRecipeTable = new JTable();
	private JTable allRecipeTable = new JTable();
	private JTextField addTitle = new JTextField(30);
	private JTextArea addIngredients = new JTextArea(10, 30);
	private JTextArea addInstructions = new JTextArea(10, 30);
	private JLabel title, ingredients, instructions;
	private JPanel container, addPanel, homePanel, allPanel, searchPanel;
	private CardLayout cl = new CardLayout();
	private JButton add, search, cancel, cancel1, cancel2, clear,
			save, delete, delete1, update, update1, print, print1,
			viewAll;
	private JLabel welcome = new JLabel(
			"<html>Welcome to the Recipe Builder<br>To add a new recipe click on the add button below.<br>"
					+ "To search for existing recipes click on the search button below.</html>");
	private JLabel searchList = new JLabel();
	private RecipeController con;

	public RecipeView(RecipeController con) {
		this.con = con;
		// container panel for the cardlayout
		container = new JPanel();
		container.setLayout(cl);

		addJPanel();
		homeJPanel();
		searchJPanel();
		allJPanel();

		// add panels to container panel
		container.add(homePanel, "1");
		container.add(addPanel, "2");
		container.add(searchPanel, "3");
		container.add(allPanel, "4");
		cl.show(container, "1");

		registerHomePanelEvents();
		registerAddPanelEvents();
		registerSearchPanelEvents();
		registerAllPanelEvents();

		frame.setLayout(new GridLayout(1, 5));
		frame.add(container);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.pack();
		frame.setSize(400, 600);
		frame.setVisible(true);

	}
	
	// panel and features for welcome screen
	private void homeJPanel() {
		homePanel = new JPanel();
		search = new JButton("Search");
		add = new JButton("Add");
		viewAll = new JButton("All Recipes");
		homePanel.add(welcome);
		homePanel.add(add);
		homePanel.add(search);
		homePanel.add(viewAll);
	}
	
	// panel and features for add recipe screen
	private void addJPanel() {
		addPanel = new JPanel();
		save = new JButton("Save");
		clear = new JButton("Clear");
		cancel = new JButton("Cancel");
		title = new JLabel("Enter title: ");
		ingredients = new JLabel("Enter ingredients: ");
		instructions = new JLabel("Enter instructions: ");
		addPanel.add(title);
		addPanel.add(addTitle);
		addPanel.add(ingredients);
		addPanel.add(new JScrollPane(addIngredients));
		addPanel.add(instructions);
		addPanel.add(new JScrollPane(addInstructions));
		addPanel.add(save);
		addPanel.add(clear);
		addPanel.add(cancel);
	}
	
	// search results panel
	private void searchJPanel() {
		searchPanel = new JPanel();
		cancel1 = new JButton("Cancel");
		delete = new JButton("Delete");
		update = new JButton("Update");
		print = new JButton("Print");
		searchPanel.add(searchList);
		searchPanel.add(new JScrollPane(searchRecipeTable));
		searchRecipeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		searchPanel.add(cancel1);
		searchPanel.add(delete);
		searchPanel.add(update);
		searchPanel.add(print);
		
	}
	
	// all recipes panel
	private void allJPanel() {
		allPanel = new JPanel();
		cancel2 = new JButton("Cancel");
		JLabel allList = new JLabel("All saved recipes are listed below: ");
		delete1 = new JButton("Delete");
		update1 = new JButton("Update");
		print1 = new JButton("Print");
		allPanel.add(allList);
		allPanel.add(new JScrollPane(allRecipeTable));
		allRecipeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		allPanel.add(cancel2);
		allPanel.add(delete1);
		allPanel.add(update1);
		allPanel.add(print1);
	}

	public String getTitle() {
		return addTitle.getText();
	}

	public String getIngredients() {
		return addIngredients.getText();
	}

	public String getInstructions() {
		return addInstructions.getText();
	}
	
	
	// method to update the JTable and Database based on user entries
	public void update(DefaultTableModel model, JTable table) {
		// loop through the tables rows and update the database with all changes
		int lastRow = model.getRowCount();
		for (int i = 0; i < lastRow; i++) {
			int recipeId = (int) model.getValueAt(i, 0);
			String title = (String) model.getValueAt(i, 1);
			String ingredients = (String) model.getValueAt(i, 2);
			String instructions = (String) model.getValueAt(i, 3);
			Recipe recipe = new Recipe();
			recipe.setRecipeId(recipeId);
			recipe.setTitle(title);
			recipe.setIngredients(ingredients);
			recipe.setInstructions(instructions);
			try {
				con.updateRecipe(recipe);
			} catch (ValidationException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//method to delete the recipe from the table and the database
	public void delete(DefaultTableModel model, JTable table) {
		int recipeId = (int) model.getValueAt(
				table.getSelectedRow(), 0);
		Recipe recipe = new Recipe();
		recipe.setRecipeId(recipeId);
		con.deleteRecipe(recipe);	
		model.removeRow(table.getSelectedRow());
	}

	// event listeners for the home/welcome screen
	private void registerHomePanelEvents() {
		// DefaultListModel<Recipe> recipes = new DefaultListModel<Recipe>();
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// switch to add Recipe Panel
				cl.show(container, "2");

			}

		});
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// prompt user for search keyword
				String keyword = JOptionPane.showInputDialog(null,
						"Enter keyword: ", "Search Recipes",
						JOptionPane.DEFAULT_OPTION);
				try {
					if (keyword.isEmpty()) {
						return;
					} else {
						//adds content to JLabel including keyword string value
						searchList.setText("Recipes containing \"" + keyword + "\" are listed below:");
						// contain data in model from database
						model = (DefaultTableModel) con
								.getRecipeByKeyWord(keyword);
						// set the table to the model from database
						searchRecipeTable.setModel(model);
						
						//remove recipeId column from view
						searchRecipeTable.removeColumn(searchRecipeTable.getColumnModel().getColumn(0));

						// switch panels
						cl.show(container, "3");
					}
				} catch (NullPointerException e1) {
					return;
				}
				
			}
		});

		viewAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// query the DB for all recipes
				model1 = (DefaultTableModel) con.getAllRecipes();
				//set the model for the table
				allRecipeTable.setModel(model1);
				//remove recipeId column from view
				allRecipeTable.removeColumn(allRecipeTable.getColumnModel().getColumn(0));
				//show allRecipePanel
				cl.show(container, "4");
			}
		});
	}

	// event listeners for the add recipe screen
	private void registerAddPanelEvents() {

		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Recipe recipe = new Recipe();
				try {
					recipe.setTitle(getTitle());
					recipe.setIngredients(getIngredients());
					recipe.setInstructions(getInstructions());
					con.addRecipe(recipe);
					cl.show(container, "1");
				} catch (ValidationException e1) {
					JOptionPane
							.showMessageDialog(addPanel,
									"Please enter a Title, Ingredients and Instructions for your recipe! ");
				}
			}
		});
		
		//clears the screen to allow the user to start over
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addTitle.setText(null);
				addIngredients.setText(null);
				addInstructions.setText(null);
			}

		});
		// used to return to the home screen menu
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				cl.show(container, "1");
			}
		});
	}

	private void registerSearchPanelEvents() {
		
		

		// button to return to the home screen panel
		cancel1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(container, "1");
			}

		});
		// delete selected recipe from table and database
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				delete(model, searchRecipeTable);
				
			}

		});

		// update selected recipe in table and database
		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				update(model, searchRecipeTable);
			}
		});

		// print selected recipe
		print.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Print output to PDF, either selected or if not then all recipes!
			}
		});
	}

	private void registerAllPanelEvents() {
		// button to return to the home screen panel
		cancel2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(container, "1");
			}

		});

		// delete selected recipe from table and database
		delete1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				delete(model1, allRecipeTable);
			}

		});

		// update selected recipe in table and database
		update1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//call the update method to update the database
				update(model1, allRecipeTable);
			}

		});

		// print recipe
		print1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
	}

	public static void main(String[] args) {
		RecipeController cont = new RecipeController();
		// RecipeView gui = new RecipeView(cont);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new RecipeView(cont);
			}
		});
		// gui.setResizable(false);
	}

	// TODO Review Observer implementation
	@Override
	public void update(Observable o, Object object) {
		if (o instanceof RecipeModel) {
			RecipeModel model = (RecipeModel) o;
			title.setText(model.getTitle() + "");
			ingredients.setText(model.getIngredients());
			instructions.setText(model.getInstructions() + "");
		}

	}

}
