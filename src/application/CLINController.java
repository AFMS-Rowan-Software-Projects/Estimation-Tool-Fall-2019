package application;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * Interface to create / edit CLINs.
 */

public class CLINController extends ListCell<CLIN> implements Initializable {
	// fxml elements for the editor
	@FXML
	private Button clinSaveButton;
	@FXML
	private Button clinClose;
	@FXML
	private Button clinSaveAndClose;
	@FXML
	private TextField clinIndex;
	@FXML
	private TextField clinProjectType;
	@FXML
	private TextField clinVersion;
	@FXML
	private TextArea clinTextArea;
	@FXML
	private DatePicker clinPoPStart;
	@FXML
	private DatePicker clinPoPEnd;
	@FXML
	private GridPane gridPane;
	
	@FXML private Label errorIndex;
	@FXML private Label errorType;
	@FXML private Label errorPop;
	@FXML private Label errorVersion;
	@FXML private Label errorDetails;

	CLIN clin;
	private ObservableList<CLIN> clinObservableList;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clinVersion.setText("1");
		errorIndex.setVisible(false);
		errorType.setVisible(false);
		errorPop.setVisible(false);
		errorVersion.setVisible(false);
		errorDetails.setVisible(false);
	}
	
	
	public void checkIndex() {
		/*clinIndex.getText();
		clinIndex.getText();
		clinIndex.getText();
		String test = clinIndex.getText();
		if(test.matches("\\d+")) {
			errorIndex.setVisible(false);
		} else {
			errorIndex.setVisible(true);
		}
		System.out.println(test);*/
	}
	public void checkType() {
		
	}
	public void checkVersion() {
		
	}
	public void checkPop() {
		/*System.out.println("Test");*/
	}
	public void checkDetails() {
		
	}
	
	
	
	/**
	 * Saves the CLIN being edited. Creates a new CLIN object if the CLIN has not
	 * been saved yet, otherwise updates existing CLIN.
	 *
	 */
	public void saveCLIN() {

		// Get all the data from the form
		String index = clinIndex.getText();
		String projectType = clinProjectType.getText();
		String text = clinTextArea.getText();
		String version = clinVersion.getText();
		String start = clinPoPStart.getValue() == null ? null : clinPoPStart.getValue().toString();
		String end = clinPoPEnd.getValue() == null ? null : clinPoPEnd.getValue().toString();

		if (clin == null) {
			// Create a new object if not yet saved
			clin = new CLIN(null, null, index, version, projectType, text, start, end);
			clinObservableList.add(clin);
		} else {
			// Update CLIN with new information
			clin.setIndex(index);
			clin.setVersion(version);
			clin.setProjectType(projectType);
			clin.setClinContent(text);
			clin.setPopStart(start);
			clin.setPopEnd(end);
			clinObservableList.set(clinObservableList.indexOf(clin), clin); // probably not the "right" way to update
																			// the list
			// https://coderanch.com/t/666722/java/Notify-ObservableList-Listeners-Change-Elements
		}

		// System.out.println(clin);
	}

	/**
	 * Saves the CLIN and closes the editor
	 * 
	 * @param event
	 */
	public void saveAndClose(ActionEvent event) {
		saveCLIN();
		close();
	}

	/**
	 * Closes the editor without saving any unsaved changes
	 */
	public void close() {
		// TODO: make a popup if there are unsaved changes
		// probably keep track of state with a boolean, use
		// fxml "on input method text changed" for each input field
		Stage stage = (Stage) clinClose.getScene().getWindow();
		stage.close();
	}

	/**
	 * Sets the CLIN observable list to allow the editor to add to the list view
	 * 
	 * @param clinObservableList
	 */
	public void setList(ObservableList<CLIN> clinObservableList) {
		this.clinObservableList = clinObservableList;
	}

	/**
	 * Set the CLIN to allow for editing an existing CLIN
	 * 
	 * @param clin
	 */
	public void setCLIN(CLIN clin) {
		this.clin = clin;
	}

	/**
	 * Fills in all of the input fields with the existing CLIN's data. Only runs if
	 * CLIN is not null
	 */
	public void setInputFields() {
		if (clin != null) {
			clinIndex.setText(clin.getIndex());
			clinVersion.setText(clin.getVersion());
			clinProjectType.setText(clin.getProjectType());
			clinTextArea.setText(clin.getClinContent());
			clinPoPStart.setValue(LocalDate.parse(clin.getPopStart()));
			clinPoPEnd.setValue(LocalDate.parse(clin.getPopEnd()));
		}
	}


}
