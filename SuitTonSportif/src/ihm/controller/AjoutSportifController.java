package ihm.controller;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AjoutSportifController implements Initializable {
  @FXML
  private Label titre;
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
    if (SportifController.nomSelectionner != null) {
      titre.setText("Modifier un sportif :");
      nom.setText("nom");
      prenom.setText("prenom");
      pseudo.setText("pseudo");
      Date date = new Date(System.currentTimeMillis());
      dateNaissance.setValue(
          Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
    } else {
      titre.setText("Ajouter un sportif :");
    }
  }

  /**
   * Ferme la PopUp sans éffectuer d'action.
   *
   * @param mouseEvent : clique de l'utilisateur
   * @throws IOException : en cas d'échec de l'ecture du fxml
   */
  @FXML
  public void fermerPopUp(MouseEvent mouseEvent) throws IOException {
    SportifController.nomSelectionner = null;
    Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
    stage.close();
  }

  /**
   * Creer un sportif grâce aux informations saisie par l'utilisateur, puis ferme la PopUp.
   *
   * @param mouseEvent : clique de l'utilisateur
   * @throws IOException : en cas d'échec de l'ecture du fxml
   */
  @FXML
  public void creationSportif(MouseEvent mouseEvent) throws IOException {
    LocalDate naissance = dateNaissance.getValue();
    Date date = Date.from(naissance.atStartOfDay(ZoneId.systemDefault()).toInstant());
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    this.fermerPopUp(mouseEvent);
  }
}
