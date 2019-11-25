package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import DB.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class PM_ProjectsController implements Initializable {

	@FXML
	private Button newProjectBtn;
	@FXML
	private Button logoutButton;
	@FXML
	private Button discardButton;
	@FXML
	private TabPane projectTabPane;
	@FXML
	private Tab submittedTab;
	@FXML
	private Tab unsubmittedTab;
	@FXML
	private Text editBtn;
	@FXML
	private Text discardBtn;
	@FXML
	private Text editBtn2;
	@FXML
	private Text discardBtn2;

	// Project list fields
	Project proj;
	@FXML
	private ListView<Project> unsubmittedListView;
	private ObservableList<Project> unsubmittedObservableList;

	@FXML
	private ListView<Project> estimatedListView;
	private ObservableList<Project> estimatedObservableList;

	@FXML
	private ListView<Project> unestimatedListView;
	private ObservableList<Project> unestimatedObservableList;
	

	private ArrayList<CLIN> clinDelete = new ArrayList<CLIN>();
	private ArrayList<SOW> sowDelete = new ArrayList<SOW>();
	private ArrayList<SDRL> sdrlDelete = new ArrayList<SDRL>();
	

	/**
	 * Initialize the page, create the lists, fill all the lists with data from the
	 * database
	 */
	public void initialize(URL location, ResourceBundle resources) {
		// Initialize the observable list, give it to the list view
		unsubmittedObservableList = FXCollections.observableArrayList();
		unsubmittedListView.setItems(unsubmittedObservableList);

		// Give the list view the custom HBox so that it has per-element buttons
		unsubmittedListView.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>() {
			@Override
			public ListCell<Project> call(ListView<Project> param) {
				return new UnsubmittedCell();
			}
		}); // https://stackoverflow.com/questions/15661500/javafx-listview-item-with-an-image-button

		estimatedObservableList = FXCollections.observableArrayList();
		estimatedListView.setItems(estimatedObservableList);

		estimatedListView.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>() {
			@Override
			public ListCell<Project> call(ListView<Project> param) {
				return new EstimatedCell();
			}
		});

		unestimatedObservableList = FXCollections.observableArrayList();
		unestimatedListView.setItems(unestimatedObservableList);

		unestimatedListView.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>() {
			@Override
			public ListCell<Project> call(ListView<Project> param) {
				return new UnestimatedCell();
			}
		});

		// Fill each list with relevant projects from database
		System.out.println("\nUnsubmitted Project Names");
		fillProjectList("SELECT * FROM Project WHERE Submit_Date IS NULL", unsubmittedObservableList);

		System.out.println("\nUnestimated Project Names");
		fillProjectList("SELECT * FROM Project WHERE Submit_Date IS NOT NULL AND Estimated_Date IS NULL",
				unestimatedObservableList);

		System.out.println("\nEstimated Project Names");
		fillProjectList("SELECT * FROM Project WHERE Submit_Date IS NOT NULL AND Estimated_Date IS NOT NULL",
				estimatedObservableList);
	}

	/**
	 * Fills the given list with projects retrieved with the given query
	 *
	 * @param query The query to retrieve projects from the database
	 * @param list  The list to fill
	 */
	private void fillProjectList(String query, ObservableList<Project> list) {
		try {

			ResultSet rs = DBUtil.dbExecuteQuery(query);

			// Go through each project in the result set
			while (rs.next()) {
				String projName = rs.getString("Project_Name");
				String projID = rs.getString("idProject"); // ID is stored for later use to get project versions
				Project proj = new Project(projName, projID);
				// TODO: Get the versions from the database, put them in project

				ResultSet rs2 = DBUtil.dbExecuteQuery("SELECT * FROM ProjectVersion WHERE idProject=" + proj.getID());
				while (rs2.next()) {
					proj.addVersion(rs2.getString("Version_Number"));
				}
				rs2.close();

				list.add(proj); // Add the project to the list
				System.out.println("\t" + projName);
			}

			rs.close();
		} catch (SQLException e) {
			System.out.println("SQL Exception putting projects in list");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException putting projects into list");
			e.printStackTrace();
		}
	}

	/**
	 * Sets the Project observable list to allow the editor to add to the list view
	 *
	 */
	/*
	 * public void setList(ObservableList<Project> projObservableList) {
	 * this.projObservableList = projObservableList; }
	 */

	/**
	 * Switches back to the login page
	 * 
	 * @param event
	 */
	public void logout(ActionEvent event) {
		try {
			// Opens Login page
			Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

			Stage loginStage = new Stage();
			loginStage.setTitle("Estimation Suite - Login Page");
			loginStage.setScene(new Scene(root));
			loginStage.show();

			// Closes PM Page
			Stage stage = (Stage) logoutButton.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Switches to the project creation page
	 * 
	 * @param event
	 */
	public void addNewProject(ActionEvent event) {
		try {
			// Opens New Project page
			Parent root = FXMLLoader.load(getClass().getResource("PM_NewProject.fxml"));

			Stage pmNewProjectStage = new Stage();
			pmNewProjectStage.setTitle("Estimation Suite - Product Manager - New Project");
			pmNewProjectStage.setScene(new Scene(root));
			pmNewProjectStage.show();
			pmNewProjectStage.setResizable(true);
			pmNewProjectStage.sizeToScene();

			// Closes PM Page
			Stage stage = (Stage) newProjectBtn.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Switches to the project editor page, loads the projects information
	 * 
	 * @param project The project to edit
	 */
	public void editProject(Project project, String versionNumber) {
		try {

			// System.out.println("You are now editing project version id: " +
			// projectVersionID);

			// Create a new version to hold all the data to be edited

			// Opens Edit Project page
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PM_EditProject.fxml"));
			Parent root = fxmlLoader.load();

			Stage pmNewProjectStage = new Stage();
			pmNewProjectStage.setTitle("Estimation Suite - Product Manager - Edit Project");
			pmNewProjectStage.setScene(new Scene(root));

			PM_EditProjectController controller = fxmlLoader.getController();

			ProjectVersion version = loadProjectVersion(project, versionNumber);

			controller.setProject(version);

			pmNewProjectStage.show();
			pmNewProjectStage.setResizable(true);
			pmNewProjectStage.sizeToScene();

			// Closes PM Page
			Stage stage = (Stage) newProjectBtn.getScene().getWindow();
			stage.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Remove the given project from the database and the list
	 * 
	 * @param project The project to remove
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void discardProject(Project project) throws SQLException, ClassNotFoundException {
		DBUtil.dbExecuteUpdate("CALL delete_project(" + project.getID() + ")");
		unsubmittedObservableList.remove(project);
	}

	public void estimateProject(Project project, String versionNumber) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EstimateProject.fxml"));
			Parent root = fxmlLoader.load();

			EstimateProjectController controller = fxmlLoader.getController();

			controller.setCameFromEstimator(false);

			ProjectVersion version = loadProjectVersion(project, versionNumber);

			if (version == null) {
				System.out.println("ERROR ERROR NULL ERROR ERROR");
			}

			controller.setProjectVersion(version);

			Stage eEstimateProjectStage = new Stage();
			eEstimateProjectStage.setTitle("Estimation Suite - Estimator - Estimate Project");
			eEstimateProjectStage.setScene(new Scene(root));

			// EstimateProject_Controller controller = fxmlLoader.getController();

			eEstimateProjectStage.show();
			eEstimateProjectStage.setResizable(true);
			eEstimateProjectStage.sizeToScene();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void viewProjectEstimate(Project proj, String versionNumber) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PM_ViewProjectEstimate.fxml"));
			Parent root = fxmlLoader.load();

			
			//EstimateProjectController controller = fxmlLoader.getController();

			//controller.setCameFromEstimator(false);

			ProjectVersion version = loadProjectVersion(proj, versionNumber);

			/*if (version == null) {
				System.out.println("ERROR ERROR NULL ERROR ERROR");
			}*/

			PM_VPE_Controller controller = fxmlLoader.getController();

			
			controller.setProject(version);

			Stage eEstimateProjectStage = new Stage();
			eEstimateProjectStage.setTitle("Estimation Suite - Project Manager - Estimate Project");
			eEstimateProjectStage.setScene(new Scene(root));

			// EstimateProject_Controller controller = fxmlLoader.getController();

			eEstimateProjectStage.show();
			eEstimateProjectStage.setResizable(true);
			eEstimateProjectStage.sizeToScene();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ProjectVersion loadProjectVersion(Project project, String versionNumber) {
		ProjectVersion version = new ProjectVersion();

		try {

			// Get all versions of the given project
			ResultSet rs = DBUtil.dbExecuteQuery("SELECT * FROM ProjectVersion WHERE idProject=" + project.getID()
					+ " AND Version_Number=" + versionNumber);

			// Should only have one item, but go to latest just in case (maybe throw error?)
			rs.last();

			String versionID = rs.getString("idProjectVersion");

			// Set all of the project information
			version.setName(project.getName());
			version.setProjectManager(rs.getString("Project_Manager"));
			version.setVersionNumber(rs.getString("Version_Number"));
			version.setProposalNumber(rs.getString("Proposal_Number"));
			version.setProjectID(project.getID());
			version.setProjectVersionID(rs.getString("idProjectVersion"));
			// Save dates since some are null, causes errors parsing
			// TODO: should have null checks for all fields maybe
			String start = rs.getString("PoP_Start");
			String end = rs.getString("PoP_End");
			// Null check the date strings to prevent errors
			version.setPopStart(start == null ? null : LocalDate.parse(start));
			version.setPopEnd(end == null ? null : LocalDate.parse(end));

			// Get all the clins, add them to the project
			rs = DBUtil.dbExecuteQuery("CALL select_clins(" + versionID + ")");
			while (rs.next()) {
				// System.out.println(rs.getString("CLIN_Index"));

				version.addCLIN(new CLIN(rs.getString("idCLIN"), rs.getString("CLIN_Index"),
						rs.getString("Version_Number"),rs.getString("Project_Type"), rs.getString("CLIN_Description")
						,rs.getString("PoP_Start"), rs.getString("PoP_End")));
			}

			// Get all the SDRLs, add them to the project
			rs = DBUtil.dbExecuteQuery("CALL select_sdrls(" + versionID + ")");
			while (rs.next()) {
				// System.out.println(rs.getString("CLIN_Index"));
				version.addSDRL(new SDRL(rs.getString("idSDRL"), rs.getString("SDRL_Title"), rs.getString("Version_Number"),
						rs.getString("SDRL_Description")));
			}

			// Get all the SOWs, add them to the project
			rs = DBUtil.dbExecuteQuery("CALL select_sows(" + versionID + ")");
			while (rs.next()) {
				// System.out.println(rs.getString("CLIN_Index"));
				version.addSOW(new SOW(rs.getString("idSoW"), rs.getString("Reference_Number"), rs.getString("Version_Number"),
						rs.getString("SoW_Description")));
			}

			rs.close();
		} catch (SQLException e) {

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}

	/**
	 * Generic List cell to hold all of the things common to the other list cells
	 */
	class ProjectListCell extends ListCell<Project> {
		// https://stackoverflow.com/questions/15661500/javafx-listview-item-with-an-image-button
		HBox hbox = new HBox();
		Label label = new Label("(empty)");
		Pane pane = new Pane();
		ComboBox<String> versionList = new ComboBox<String>();

		public ProjectListCell() {
			super();
			hbox.getChildren().addAll(label, pane, versionList);
			HBox.setHgrow(pane, Priority.ALWAYS); // pushes buttons to right side
			hbox.setSpacing(10); // keeps buttons from touching
		}

		/**
		 * Add a button to the HBox
		 * 
		 * @param b
		 */
		protected void addButton(Button b) {
			hbox.getChildren().add(b);
		}

		@Override
		/**
		 * Updates the list when something is added?
		 */
		protected void updateItem(Project item, boolean empty) {
			super.updateItem(item, empty);
			setText(null); // No text in label of super class
			if (empty) {
				setGraphic(null);
			} else {
				label.setText(item != null ? item.toString() : "<null>");
				setGraphic(hbox);

				versionList.getItems().addAll(this.getItem().getVersionStrings());

			}

			versionList.getSelectionModel().select(versionList.getItems().size() - 1);

		}
	}

	/**
	 * List Cell to hold buttons for unsubmitted list
	 */
	class UnsubmittedCell extends ProjectListCell {
		Button edit = new Button("Edit");
		Button remove = new Button("Remove");

		public UnsubmittedCell() {
			super();

			hbox.getChildren().addAll(edit, remove);

			// Edits selected project
			edit.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("EDIT ITEM: " + getItem() + "  VERSION: "
							+ versionList.getSelectionModel().getSelectedItem());

					// Get item and Version Number from combo box
					editProject(getItem(), versionList.getSelectionModel().getSelectedItem());
				}
			});

			// Removes selected project
			remove.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("REMOVE ITEM" + getItem());

					try {
						discardProject(getItem());
					} catch (SQLException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			});
		}

	}

	/**
	 * List Cell to hold the buttons for an unestimated project
	 */
	class UnestimatedCell extends ProjectListCell {
		Button estimateButton = new Button("Estimate");
		Button returnButton = new Button("Return");

		public UnestimatedCell() {
			super();

			hbox.getChildren().addAll(estimateButton, returnButton);

			estimateButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("Estimate ITEM: " + getItem());

					/**
					 * TODO As of right now, estimating a project as a PM, when you click discard,
					 * it brings user to Estimator page, need to fix.
					 */

					estimateProject(getItem(), versionList.getSelectionModel().getSelectedItem());

					Stage stage = (Stage) estimateButton.getScene().getWindow();
					stage.close();

				}
			});

			returnButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("Return ITEM" + getItem());

					try {
						DBUtil.dbExecuteUpdate("CALL return_project('" + getItem().getID() + "')");

						unsubmittedObservableList.add(getItem());
						unestimatedObservableList.remove(getItem());


					} catch (SQLException | ClassNotFoundException e ) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	/**
	 * List cell to hold buttons for the Estimated Project List
	 */
	class EstimatedCell extends ProjectListCell {
		Button viewButton = new Button("View Project Estimate");

		public EstimatedCell() {
			super();

			hbox.getChildren().addAll(viewButton);

			viewButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("VIEW ITEM: " + getItem());

					viewProjectEstimate(getItem(), versionList.getSelectionModel().getSelectedItem());

					Stage stage = (Stage) viewButton.getScene().getWindow();
					stage.close();
				}
			});
		}
	}

}
