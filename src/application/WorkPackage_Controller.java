package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WorkPackage_Controller implements Initializable, Refreshable {

	@FXML
	private Button closeButton;
	@FXML
	private Button addTaskButton;
	@FXML
	private Button editTaskButton;
	@FXML
	private Button removeTaskButton;

	@FXML
	private TextField name;
	@FXML
	private TextField author;
	@FXML
	private TextField scope;
	@FXML
	private TextField type;
	@FXML
	private TextField version;

	@FXML
	DatePicker startDate;
	@FXML
	DatePicker endDate;

	@FXML
	private ListView<Task> taskListView;
	private ObservableList<Task> taskObservableList;

	private ObservableList<WorkPackage> workPackageObservableList;
	private Refreshable prevController;

	private WorkPackage workPackage;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		taskObservableList = FXCollections.observableArrayList();
		taskListView.setItems(taskObservableList);
		workPackage = null;
	}

	public void refresh() {

	}

	public void addTask(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Task.fxml"));
			Parent root = fxmlLoader.load();

			Task_Controller controller = fxmlLoader.getController();
			controller.setPreviousController(this);
			controller.setTaskList(taskObservableList);
			
			
			Stage addTaskStage = new Stage();
			addTaskStage.setTitle("Estimation Suite - Estimator - Estimate Project");
			addTaskStage.setScene(new Scene(root));

			addTaskStage.show();
			addTaskStage.setResizable(true);
			addTaskStage.sizeToScene();

			StageHandler.addStage(addTaskStage);
			StageHandler.hidePreviousStage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void editTask(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Task.fxml"));
			Parent root = fxmlLoader.load();

			Task_Controller controller = fxmlLoader.getController();
			controller.setPreviousController(this);
			controller.setTaskList(taskObservableList);
			controller.setTask(taskListView.getSelectionModel().getSelectedItem());
			
			Stage addTaskStage = new Stage();
			addTaskStage.setTitle("Estimation Suite - Estimator - Estimate Project");
			addTaskStage.setScene(new Scene(root));

			addTaskStage.show();
			addTaskStage.setResizable(true);
			addTaskStage.sizeToScene();

			StageHandler.addStage(addTaskStage);
			StageHandler.hidePreviousStage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeTask(ActionEvent event) {

	}

	public void close() {
		closeCurrent();
	}

	public void save() {
		boolean flag = workPackage == null;
		if (flag) {
			workPackage = new WorkPackage();
		}

		workPackage.setName(name.getText());
		workPackage.setWptype(type.getText());
		workPackage.setAuthor(author.getText());
		workPackage.setScope(scope.getText());
		workPackage.setVersion(version.getText());
		workPackage.setPopEnd(endDate.getValue().toString());
		workPackage.setPopStart(startDate.getValue().toString());
		workPackage.setTasks(new ArrayList<Task>(taskObservableList));

		if (flag) {
			workPackageObservableList.add(workPackage);
		}
	}

	public void saveAndClose() {
		save();
		close();
	}

	public void setPreviousController(Refreshable controller) {
		this.prevController = controller;
	}

	private void closeCurrent() {
		prevController.refresh();
		StageHandler.showPreviousStage();
		StageHandler.closeCurrentStage();
	}

	public void setWorkPackageList(ObservableList<WorkPackage> list) {
		this.workPackageObservableList = list;
	}

	public void setWorkPackage(WorkPackage wp) {
		this.workPackage = wp;
		setAllFields();
	}

	private void setAllFields() {

		name.setText(workPackage.getName());
		author.setText(workPackage.getAuthor());
		scope.setText(workPackage.getScope());
		type.setText(workPackage.getWptype());
		version.setText(workPackage.getVersion());

		startDate.setValue(LocalDate.parse(workPackage.getPopStart()));
		endDate.setValue(LocalDate.parse(workPackage.getPopEnd()));
		taskObservableList.addAll(workPackage.getTasks());
	}
}
