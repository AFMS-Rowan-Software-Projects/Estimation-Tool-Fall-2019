package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class LoginController implements Initializable, Refreshable{

	@FXML
	private Button loginBtn;

	@FXML
	private Button exitBtn;
	
	@FXML
	private ComboBox<String> loginBox = new ComboBox<String>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginBox.getItems().addAll("Product Manager", "Estimator");
		
	}
	
	public void refresh() {
		loginBox.getSelectionModel().clearSelection();
	}
	
	public void Login(ActionEvent event) {
		//changed to check for the null event first
		if(loginBox.getValue() == null) 
			{
					try {
					
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Error_Window.fxml"));
						Parent root = fxmlLoader.load();

						ErrorWindow controller = fxmlLoader.getController();
						
						//Loads the Code to change the error text for this type of error
						controller.errorMessage("Please select a role");
						
						Stage errorStage = new Stage();
						errorStage.setTitle("ERROR");
						errorStage.setScene(new Scene(root));

						errorStage.show();
				

				}
				catch(Exception e) {
					e.printStackTrace();
					}
				}
			
		
	else if (loginBox.getValue().equals("Product Manager")) {
			try {
	            // Opens Product Manager page
	            
	            
	            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PM_Projects.fxml"));
				Parent root = fxmlLoader.load();

				PM_ProjectsController controller = fxmlLoader.getController();
				controller.setPreviousController(this);
	            
	            
	            Stage pmProjectsStage = new Stage();
	            pmProjectsStage.setTitle("Estimation Suite - Product Manager - Projects");
	            pmProjectsStage.setScene(new Scene(root));
	            pmProjectsStage.show();
	            pmProjectsStage.centerOnScreen();
	            //pmProjectsStage.setMaximized(true);
	            pmProjectsStage.setResizable(true);
	            pmProjectsStage.sizeToScene();
	            
	            StageHandler.addStage(pmProjectsStage);
	            StageHandler.hidePreviousStage();
	            //Closes Login Page
	           // Stage stage = (Stage) loginBtn.getScene().getWindow();
	            //stage.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if (loginBox.getValue().equals("Estimator")) {
			//Open Estimator page here
			try {         
	            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Estimator_Projects.fxml"));
				Parent root = fxmlLoader.load();

				EstimatorProjectsController controller = fxmlLoader.getController();
				controller.setPreviousController(this);
	            
	            
	            Stage estimatorProjectsStage = new Stage();
	            estimatorProjectsStage.setTitle("Estimation Suite - Estimator - Projects");
	            estimatorProjectsStage.setScene(new Scene(root));
	            estimatorProjectsStage.show();
	            estimatorProjectsStage.centerOnScreen();
	            estimatorProjectsStage.setResizable(true);
	            estimatorProjectsStage.sizeToScene();
	            
	            StageHandler.addStage(estimatorProjectsStage);
	            
	            //Closes Login Page
	            //Stage stage = (Stage) loginBtn.getScene().getWindow();
	           // stage.close();
	            StageHandler.hidePreviousStage();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	
	}
	public void Exit(ActionEvent event) throws SQLException {
		Stage stage = (Stage) exitBtn.getScene().getWindow();
		stage.close();
	}
}
