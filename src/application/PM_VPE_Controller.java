package application;

import DB.DBUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PM_VPE_Controller implements Initializable {

	@FXML
	private Button returnEstimationButton;
	@FXML
	private Button approveButton;
	@FXML
	private Button denyButton;
	@FXML
	private Button closeEstimationButton;
	@FXML
	private Button viewCLINestimateButton;
	@FXML
	private Label projectManager;
	@FXML
	private Label propNum;
	@FXML
	private Label versionNum;
	@FXML
	private DatePicker startDate;
	@FXML
	private DatePicker endDate;

	@FXML
	private ListView<CLIN> clinListView;
	private ObservableList<CLIN> clinObservableList;

	@FXML
	private ListView<SDRL> sdrlListView;
	private ObservableList<SDRL> sdrlObservableList;

	@FXML
	private ListView<SOW> sowListView;
	private ObservableList<SOW> sowObservableList;

	
	private ProjectVersion project;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		clinObservableList = FXCollections.observableArrayList();
		clinListView.setItems(clinObservableList);

		sdrlObservableList = FXCollections.observableArrayList();
		sdrlListView.setItems(sdrlObservableList);

		sowObservableList = FXCollections.observableArrayList();
		sowListView.setItems(sowObservableList);
	}

	public void closeEstimation(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PM_Projects.fxml"));
			Parent root = fxmlLoader.load();

			Stage closeEst = new Stage();
			closeEst.setTitle("Estimation Suite - Project Manager - Estimate Project");
			closeEst.setScene(new Scene(root));

			closeEst.show();
			closeEst.setResizable(true);
			closeEst.sizeToScene();

			Stage stage = (Stage) closeEstimationButton.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void returnEstimation(ActionEvent event) {

	}

	public void approveProject(ActionEvent event) throws SQLException, ClassNotFoundException {
		DBUtil.dbExecuteUpdate("CALL approve_project(" + project.getProjectID() + ")");
		try {
			Parent root = FXMLLoader.load(getClass().getResource("PM_Projects.fxml"));

			Stage pmProjectsStage = new Stage();
			pmProjectsStage.setTitle("Estimation Suite - Product Manager - Projects");
			pmProjectsStage.setScene(new Scene(root));
			pmProjectsStage.show();

			Stage stage = (Stage) approveButton.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void denyProject(ActionEvent event) throws SQLException, ClassNotFoundException {
		DBUtil.dbExecuteUpdate("CALL deny_project(" + project.getProjectID() + ")");
		try {
			Parent root = FXMLLoader.load(getClass().getResource("PM_Projects.fxml"));

			Stage pmProjectsStage = new Stage();
			pmProjectsStage.setTitle("Estimation Suite - Product Manager - Projects");
			pmProjectsStage.setScene(new Scene(root));
			pmProjectsStage.show();

			Stage stage = (Stage) denyButton.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void viewCLINestimation(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewCLINEstimate.fxml"));
			Parent root = fxmlLoader.load();

			Stage viewCLIN = new Stage();
			viewCLIN.setTitle("Estimation Suite - Project Manager - Estimate Project");
			viewCLIN.setScene(new Scene(root));

			viewCLIN.show();
			viewCLIN.setResizable(true);
			viewCLIN.sizeToScene();

			Stage stage = (Stage) viewCLINestimateButton.getScene().getWindow();
			stage.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setProject(ProjectVersion project) {
		this.project = project;
		setAllFields();
	}

	private void setAllFields() {
		if (project != null) {
			projectManager.setText(project.getProjectManager());
			propNum.setText(project.getProposalNumber());
			versionNum.setText(project.getVersionNumber());

			startDate.setValue(project.getPopStart());
			endDate.setValue(project.getPopEnd());
			
			clinObservableList.setAll(project.getCLINList());
			sowObservableList.setAll(project.getSOWList());
			sdrlObservableList.setAll(project.getSDRLList());
			
		} else {
			System.out.println("ERROR: NULL PROJECT");
		}
	}
}