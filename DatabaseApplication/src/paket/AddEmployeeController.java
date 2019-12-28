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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddEmployeeController implements Initializable {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSurname;

    @FXML
    private TextField txtEmail;

    private Connection connection;
    private PreparedStatement query;
    private ResultSet solutionCluster;
    private AnchorPane root;
    private Stage stage;
    private Scene scene;
    
    
    @FXML
    void add(ActionEvent event) {
    	String name = txtName.getText();
    	String surname = txtSurname.getText();
    	String email = txtEmail.getText();
    	try {
			query = connection.prepareStatement("INSERT INTO Employees (Name,Surname,Email) values(?,?,?)");
			query.setString(1, name);
			query.setString(2, surname);
			query.setString(3, email);
			if (query.executeUpdate() == 1) {
				Alert informationMessage = new Alert(AlertType.INFORMATION);
				informationMessage.setTitle("Information");
				informationMessage.setHeaderText("Succesfully added.");
				informationMessage.show();
			} else {
				Alert errorMessage = new Alert(AlertType.ERROR);
				errorMessage.setTitle("Error");
				errorMessage.setHeaderText("Somethings is gone wrong...");
				errorMessage.show();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    }

    @FXML
    void turnBackToTableScreen(ActionEvent event) {
    	Button pressedButton = (Button) event.getSource();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("TableScreen.fxml"));
    	try {
			root = loader.load();
			scene = new Scene(root);
			stage = (Stage) pressedButton.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			// TODO: handle exception
		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection("jdbc:sqlserver://YASIN;database=Employee;integratedSecurity=true");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}