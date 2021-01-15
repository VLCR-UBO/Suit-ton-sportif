package ihm.controller;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class AjoutSportifController implements Initializable {
  @FXML
  private TextField nom;
  @FXML
  private TextField prenom;
  @FXML
  private TextField pseudo;
  @FXML
  private DatePicker dateNaissance;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    nom.setText("nom");
    prenom.setText("prenom");
    pseudo.setText("pseudo");
    Date date = new Date(System.currentTimeMillis());
    dateNaissance.setValue(
        Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
  }

  /**
   * Ferme la PopUp sans éffectuer d'action.
   * @param mouseEvent : clique de l'utilisateur
   * @throws IOException : en cas d'échec de l'ecture du fxml
   */
  @FXML
  public void fermerPopUp(MouseEvent mouseEvent) throws IOException {
    Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
    stage.close();
  }

  /**
   * Creer un sportif grâce aux informations saisie par l'utilisateur, puis ferme la PopUp.
   * @param mouseEvent : clique de l'utilisateur
   * @throws IOException : en cas d'échec de l'ecture du fxml
   */
  @FXML
  public void creationSportif(MouseEvent mouseEvent) throws IOException {
    LocalDate naissance = dateNaissance.getValue();
    Date date = Date.from(naissance.atStartOfDay(ZoneId.systemDefault()).toInstant());
    this.fermerPopUp(mouseEvent);
  }
}
