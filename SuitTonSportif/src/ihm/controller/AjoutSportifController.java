package ihm.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AjoutSportifController implements Initializable{
	@FXML private TextField nom;
	@FXML private TextField prenom;
	@FXML private TextField pseudo;
	@FXML private DatePicker dateNaissance;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	public void fermerPopUp(MouseEvent mouseEvent) throws IOException {
		
	}
	
	@FXML
	public void creationSportif(MouseEvent mouseEvent) throws IOException {
		
	}
}
