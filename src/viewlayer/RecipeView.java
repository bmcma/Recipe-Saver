package viewlayer;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import businesslayer.ValidationException;
import datatransferobjects.Recipe;

/*
 * View for the MVC - interacts with the user and the controller
 */

//TODO Add print functionality
//TODO Add key listener for tabbing between selections

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
	private JButton add, search, cancel, cancel1, cancel2, clear, save, delete,
			delete1, update, update1, print, print1, viewAll;
	private JLabel searchList;
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
		frame.setSize(500, 550);
		frame.setVisible(true);
		frame.setResizable(false);

	}

	// panel and features for welcome screen
	private void homeJPanel() {
		homePanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 4;
		c.insets = new Insets(0, 10, 10, 10);

		// set the text as a string and then center it in the JLabel using html
		String instructText = "<html>To add a new recipe click on the add Recipes button below<br>"
				+ "To search for existing recipes click on the search Recipes button below<br>"
				+ "To view all recipes click on the All Recipes button below</html>";
		JLabel instruct = new JLabel(
				"<html><div style =\"text-align: center;\">" + instructText
						+ "</html>");
		String welcomeText = "<html>Welcome to the Recipe Builder<html>";
		JLabel welcome = new JLabel("<html><h3>" + welcomeText + "</h3></html>");
		welcome.setHorizontalAlignment(SwingConstants.CENTER);// set welcome
																// message text
																// as centered

		// instantiate buttons
		search = new JButton("Search Recipes");
		add = new JButton("Add Recipe");
		viewAll = new JButton("All Recipes");

		c.gridx = 0;
		c.gridy = 0;
		homePanel.add(welcome, c);
		c.gridy++; // increment gridy to place each component below the previous
					// one
		homePanel.add(instruct, c);
		c.insets = new Insets(10, 20, 10, 20);// space buttons with correct
												// padding
		c.gridwidth = 1;// resets gridwidth to align buttons alongside each
						// other
		c.gridy++;
		homePanel.add(add, c);
		c.gridx++; // increment the gridx to align buttons alongside each other
		homePanel.add(search, c);
		c.gridx++;
		homePanel.add(viewAll, c);
	}

	// panel and features for add recipe screen
	private void addJPanel() {
		GridBagConstraints c = new GridBagConstraints();
		addPanel = new JPanel(new GridBagLayout());
		// instantiate buttons
		save = new JButton("Save");
		clear = new JButton("Clear");
		cancel = new JButton("Cancel");
		title = new JLabel("Enter title: ");
		ingredients = new JLabel("Enter ingredients: ");
		addIngredients.setLineWrap(true);// wraps text to new line when limit is
											// reached
		instructions = new JLabel("Enter instructions: ");
		addInstructions.setLineWrap(true);// wraps text to new line when limit
											// is reached
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10, 0, 0, 0);// set the top padding
		c.gridwidth = 4; // set the width to 4 columns
		c.weighty = 1; // begin at top of container
		c.anchor = GridBagConstraints.PAGE_START; // begin at top of container
		addPanel.add(title, c);
		c.insets = new Insets(0, 0, 0, 0);// reset the top padding
		c.gridx = 0;
		c.gridy++; // increment gridy to move to next line
		addPanel.add(addTitle, c);
		c.gridy++; // increment gridy to move to next line
		addPanel.add(ingredients, c);
		c.gridy++; // increment gridy to move to next line
		addPanel.add(new JScrollPane(addIngredients), c);
		c.gridy++; // increment gridy to move to next line
		addPanel.add(instructions, c);
		c.gridy++; // increment gridy to move to next line
		addPanel.add(new JScrollPane(addInstructions), c);
		c.insets = new Insets(0, 20, 10, 20);
		c.gridy++; // increment gridy to move to next line
		c.gridwidth = 1; // reset the gridwidth
		addPanel.add(save, c);
		c.gridx++; // increment gridx to move to next column
		addPanel.add(clear, c);
		c.gridx++; // increment gridx to move to next column
		addPanel.add(cancel, c);
	}

	// search results panel
	private void searchJPanel() {
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 0, 0, 0);
		c.anchor = GridBagConstraints.PAGE_START;
		c.weighty = 1;
		c.gridwidth = 5;
		searchPanel = new JPanel(new GridBagLayout());

		// instantiate buttons
		cancel1 = new JButton("Cancel");
		delete = new JButton("Delete");
		update = new JButton("Update");
		print = new JButton("Print");

		searchList = new JLabel();
		c.gridx = 0;
		c.gridy = 0;
		searchPanel.add(searchList, c);
		c.gridy++;
		c.fill = GridBagConstraints.HORIZONTAL;
		searchPanel.add(new JScrollPane(searchRecipeTable), c);
		searchRecipeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridwidth = 1;// reset the width to put all buttons in one row
		c.gridx++;
		c.gridy++; // new row
		searchPanel.add(cancel1, c);
		c.gridx++;
		searchPanel.add(delete, c);
		c.gridx++;
		searchPanel.add(update, c);
		c.gridx++;
		searchPanel.add(print, c);

	}

	// all recipes panel
	private void allJPanel() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridwidth = 5;
		c.weighty = 1;
		c.insets = new Insets(10, 0, 0, 0);
		allPanel = new JPanel(new GridBagLayout());
		// instantiate components
		JLabel allList = new JLabel("All saved recipes are listed below: ");
		cancel2 = new JButton("Cancel");
		delete1 = new JButton("Delete");
		update1 = new JButton("Update");
		print1 = new JButton("Print");

		c.gridx = 0;
		c.gridy = 0;
		allPanel.add(allList, c);
		c.gridy++;// new row
		c.fill = GridBagConstraints.HORIZONTAL;
		allPanel.add(new JScrollPane(allRecipeTable), c);
		allRecipeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		c.insets = new Insets(5, 5, 5, 5);
		c.gridwidth = 1;
		c.gridy++;
		c.gridx++;
		allPanel.add(cancel2, c);
		c.gridx++;
		allPanel.add(delete1, c);
		c.gridx++;
		allPanel.add(update1, c);
		c.gridx++;
		allPanel.add(print1, c);
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

	// method to delete the recipe from the table and the database
	public void delete(DefaultTableModel model, JTable table) {
		int recipeId = (int) model.getValueAt(table.getSelectedRow(), 0);
		Recipe recipe = new Recipe();
		recipe.setRecipeId(recipeId);
		con.deleteRecipe(recipe);
		model.removeRow(table.getSelectedRow());
	}

	// event listeners for the home/welcome screen
	private void registerHomePanelEvents() {
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
						// adds content to JLabel including keyword string value
						searchList.setText("Recipes containing \"" + keyword
								+ "\" are listed below:");
						// contain data in model from database
						model = (DefaultTableModel) con
								.getRecipeByKeyWord(keyword);
						// set the table to the model from database
						searchRecipeTable.setModel(model);

						// remove recipeId column from view
						searchRecipeTable.removeColumn(searchRecipeTable
								.getColumnModel().getColumn(0));

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
				// set the model for the table
				allRecipeTable.setModel(model1);
				// remove recipeId column from view
				allRecipeTable.removeColumn(allRecipeTable.getColumnModel()
						.getColumn(0));
				// show allRecipePanel
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

		// clears the screen to allow the user to start over
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
				// TODO Print output to PDF, either selected or if not then all
				// recipes!
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
				// call the update method to update the database
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
