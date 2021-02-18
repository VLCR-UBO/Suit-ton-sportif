package ihm;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
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
  @FXML
  private Label erreure;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    if (SportifController.nomSelectionner != null) {
      titre.setText("Modifier un sportif");
      nom.setText(Main.facade.obtenirNomSportif(SportifController.nomSelectionner));
      prenom.setText(Main.facade.obtenirPrenomSportif(SportifController.nomSelectionner));
      pseudo.setText(SportifController.nomSelectionner);
      Calendar date = Main.facade.obtenirDateDeNaissanceSportif(SportifController.nomSelectionner);
      Date input = date.getTime();
      dateNaissance.setValue(input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
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

    if (!nom.getText().trim().isEmpty() && !prenom.getText().trim().isEmpty()
        && !pseudo.getText().trim().isEmpty() && naissance != null) {
      Calendar calendar = new GregorianCalendar.Builder()
          .setDate(naissance.getYear(), naissance.getMonthValue(), naissance.getDayOfMonth())
          .build();

      int err;

      if (SportifController.nomSelectionner != null) {
        err = Main.facade.modifierUnSportif(SportifController.nomSelectionner, nom.getText(),
            prenom.getText(), pseudo.getText(), "pouet", calendar);
      } else {
        err = Main.facade.ajouterUnSportif(nom.getText(), prenom.getText(), pseudo.getText(),
            "pouet", calendar);
      }

      if (err != 1) {
        erreure.setVisible(true);
        erreure.setText("Ce pseudo est déjà utilisé !");
        erreure.setTextFill(Paint.valueOf("#e00404"));
      } else {
        this.fermerPopUp(mouseEvent);
      }
    } else {
      erreure.setVisible(true);
      erreure.setText("Tout les champs doivent etre remplies");
      erreure.setTextFill(Paint.valueOf("#e1a903"));
    }
  }
}
