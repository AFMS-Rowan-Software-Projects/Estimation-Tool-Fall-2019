package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrganizationBOE_Controller implements Initializable, Refreshable {

	@FXML private Label error1;
	@FXML private Label error2;
	@FXML private Label error3;
	@FXML private Label error4;
	
	@FXML
	private Button closeButton;
	@FXML
	private Button addWorkPackButton;
	@FXML
	private Button editWorkPackButton;
	@FXML
	private Button removeWorkPackButton;
	@FXML
	private TextField orgText;
	@FXML
	private TextField productText;
	@FXML
	private TextField versionText;

	@FXML
	private ListView<WorkPackage> workPackageListView;
	private ObservableList<WorkPackage> workPackageObservableList;

	private Refreshable prevController;
	private ObservableList<OrganizationBOE> organizationBOEObservableList;

	private ArrayList<WorkPackage> wpDelete;

	private OrganizationBOE org;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		workPackageObservableList = FXCollections.observableArrayList();
		workPackageListView.setItems(workPackageObservableList);
		org = null;
		wpDelete = new ArrayList<WorkPackage>();
		versionText.setText("1");
		
		error1.setVisible(false);
		error2.setVisible(false);
		error3.setVisible(false);
		error4.setVisible(false);
	}

	public void refresh() {
		workPackageListView.refresh();
	}

	public void addWorkPack(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WorkPackage.fxml"));
			Parent root = fxmlLoader.load();

			WorkPackage_Controller controller = fxmlLoader.getController();
			controller.setPreviousController(this);
			controller.setWorkPackageList(workPackageObservableList);

			Stage addWPStage = new Stage();
			addWPStage.setTitle("Estimation Suite - Estimator - Estimate Project");
			addWPStage.setScene(new Scene(root));

			addWPStage.show();
			addWPStage.setResizable(true);
			addWPStage.sizeToScene();

			StageHandler.addStage(addWPStage);
			StageHandler.hidePreviousStage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void editWorkPackage(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WorkPackage.fxml"));
			Parent root = fxmlLoader.load();

			WorkPackage_Controller controller = fxmlLoader.getController();
			controller.setPreviousController(this);
			controller.setWorkPackageList(workPackageObservableList);
			controller.setWorkPackage(workPackageListView.getSelectionModel().getSelectedItem());
			Stage addWPStage = new Stage();
			addWPStage.setTitle("Estimation Suite - Estimator - Estimate Project");
			addWPStage.setScene(new Scene(root));

			addWPStage.show();
			addWPStage.setResizable(true);
			addWPStage.sizeToScene();

			StageHandler.addStage(addWPStage);
			StageHandler.hidePreviousStage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeWorkPackage(ActionEvent event) {
		WorkPackage wp = workPackageObservableList.remove(workPackageListView.getSelectionModel().getSelectedIndex());
		if (wp.getID() != null) {
			wpDelete.add(wp);
		}

	}

	public void close() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Remove Project");
		alert.setHeaderText("This will discard any unsaved changes.");
		alert.setContentText("Are you sure you want to exit?");

		ButtonType buttonTypeOne = new ButtonType("Discard Changes ");
		ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne) {
			closeCurrent();
		} else {
			// ... user chose CANCEL or closed the dialog
		}
	}

	public boolean save() {

		boolean passed = true;
		String errorMessage = "";

		String versionReg = "\\d+(.\\d)*";

		if (!versionText.getText().matches(versionReg)) {
			passed = false;
			errorMessage += "Error: Invalid version! Use form 1, 1.2, 1.2.3, etc.\n";
		}

		if (passed) {
			boolean flag = org == null;
			if (flag) {
				org = new OrganizationBOE();
				org.setOldVersion(versionText.getText());
			}
			org.setOrganization(orgText.getText());
			org.setProduct(productText.getText());
			org.setVersion(versionText.getText());
			org.setWorkPackages(new ArrayList<WorkPackage>(workPackageObservableList));
			
			org.setDeletedWorkPackages(wpDelete);
			
			if (flag) {
				organizationBOEObservableList.add(org);
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Saving Task");
			alert.setHeaderText("There was an error saving this task!");
			alert.setContentText(errorMessage);

			// ButtonType buttonTypeOne = new ButtonType("Discard Changes ");
			ButtonType buttonTypeCancel = new ButtonType("OK", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(buttonTypeCancel);
			alert.showAndWait();
		}
		return passed;
	}

	public void saveAndClose() {
		if (save()) {
			closeCurrent();
		}
	}

	public void setPreviousController(Refreshable controller) {
		this.prevController = controller;
	}

	public void setOrganizationList(ObservableList<OrganizationBOE> organizationBOEObservableList) {
		this.organizationBOEObservableList = organizationBOEObservableList;
	}

	public void setOrganiztion(OrganizationBOE org) {
		this.org = org;
		setAllFields();
	}

	private void setAllFields() {
		orgText.setText(org.getOrganization());
		productText.setText(org.getProduct());
		versionText.setText(org.getVersion());
		workPackageObservableList.addAll(org.getWorkPackages());
	}

	private void closeCurrent() {
		prevController.refresh();
		StageHandler.showPreviousStage();
		StageHandler.closeCurrentStage();
	}

	public ArrayList<WorkPackage> getDeleteList() {
		return wpDelete;
	}
}
