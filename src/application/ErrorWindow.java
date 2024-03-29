package application;
	
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class ErrorWindow extends Application implements Initializable{
	
	@FXML
	private Button exitBtn;

	@FXML
	private Text errorTxt;
	

	
	public void start(Stage primaryStage) {
		try {
				Parent root = FXMLLoader.load(getClass()
						.getResource("Error_Window.fxml"));
	            primaryStage.setTitle("ERROR");
				primaryStage.setScene(new Scene(root));
				primaryStage.show();
			
			}
		catch(Exception e) {
			e.printStackTrace();
			}
	}

   
    public void errorMessage(String words){
        errorTxt.setText(words);
      //Should be able to use this to append multiple errors to the same window, when implemented
      //errorTxt.setText(errorTxt + words);
    }
	
	public void Exit(ActionEvent event) {
		Stage stage = (Stage) exitBtn.getScene().getWindow();
		stage.close();

	}
	
	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}




}