package ihm;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class AjoutQuestionsController implements Initializable {
  @FXML
  private TextField intitule;
  @FXML
  private Label titre;
  @FXML
  private Label erreure;


  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    if (QuestionnaireController.questionSelectionner != null) {
      titre.setText("Modifier une question");
      intitule.setText(QuestionnaireController.questionSelectionner);
    } else {
      titre.setText("Ajouter une question");
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
    Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
    stage.close();
  }

  /**
   * Creer une question grâce aux informations saisie par l'utilisateur, puis ferme la PopUp.
   *
   * @param mouseEvent : clique de l'utilisateur
   * @throws IOException : en cas d'échec de l'ecture du fxml
   */

  @FXML
  public void creationQuestions(MouseEvent mouseEvent) throws IOException {
    int err;

    if (!intitule.getText().trim().isEmpty()) {
      if (QuestionnaireController.questionSelectionner != null) {
        err = Main.facade.modifierUneQuestion(QuestionnaireController.nomSelectionner,
            QuestionnaireController.questionSelectionner, intitule.getText(), false);
      } else {
        err = Main.facade.ajouterUneQuestion(QuestionnaireController.nomSelectionner,
            intitule.getText());
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
