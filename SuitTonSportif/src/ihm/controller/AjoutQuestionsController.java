package ihm.controller;

import ihm.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AjoutQuestionsController implements Initializable {
  @FXML
  private TextField intitule;
  @FXML
  private Label titre;


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
   * Creer un sportif grâce aux informations saisie par l'utilisateur, puis ferme la PopUp.
   *
   * @param mouseEvent : clique de l'utilisateur
   * @throws IOException : en cas d'échec de l'ecture du fxml
   */

  @FXML
  public void creationQuestions(MouseEvent mouseEvent) throws IOException {

    if (intitule.getText() != null) {
      if (QuestionnaireController.questionSelectionner != null) {
        Main.facade.modifierUneQuestion(QuestionnaireController.nomSelectionner,
            QuestionnaireController.questionSelectionner, intitule.getText());
      } else {
        Main.facade.ajouterUneQuestion(QuestionnaireController.nomSelectionner,
            intitule.getText());
      }
    }

    this.fermerPopUp(mouseEvent);
  }
}
