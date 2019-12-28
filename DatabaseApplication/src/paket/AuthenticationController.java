package paket;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AuthenticationController implements Initializable {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField pswPassword;

    private Connection connection;
    private PreparedStatement query; //For the SQL Querys
    private ResultSet solutionCluster;
    private AnchorPane root;
    private Stage stage;
    private Scene scene;

    @FXML
    void login(ActionEvent event) {
    	String username = txtUsername.getText();
    	String password = pswPassword.getText();
    	boolean isValid =false;	
    	try {
			while (solutionCluster.next()) {
				if (solutionCluster.getString(1).equals(username)&&solutionCluster.getString(2).equals(password)) {
					isValid=true;
					//Redirect to table screen
					Button pressedButton =(Button) event.getSource();
					FXMLLoader loader=new FXMLLoader(getClass().getResource("TableScreen.fxml"));
					root = loader.load();
					scene = new Scene(root);
					stage = (Stage) pressedButton.getScene().getWindow();
					stage.setScene(scene);
					stage.show();
					break;
				}
			}
			if (isValid == false) {
				Alert errorMessage = new Alert(AlertType.ERROR);
				errorMessage.setTitle("Error");
				errorMessage.setHeaderText("Username or password is wrong..");
				errorMessage.show();
			}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Database Connection
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//YASIN is my pc name.
			connection = DriverManager.getConnection("jdbc:sqlserver://YASIN;database=Employee;integratedSecurity=true");
			query = connection.prepareStatement("SELECT * FROM Users");
			solutionCluster = query.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
