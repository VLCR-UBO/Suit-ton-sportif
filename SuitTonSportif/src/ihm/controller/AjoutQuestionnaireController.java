package ihm.controller;

import ihm.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AjoutQuestionnaireController implements Initializable {
  @FXML
  private TextField nom;
  @FXML
  private Label titre;
  @FXML
  private ListView<HBox> list;


  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    if (QuestionnaireController.nomSelectionner != null) {
      titre.setText("Modifier un questionnaire");
      nom.setText(QuestionnaireController.nomSelectionner);
    } else {
      titre.setText("Ajouter un questionnaire");
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
    QuestionnaireController.nomSelectionner = null;
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
  public void creationQuestionnaire(MouseEvent mouseEvent) throws IOException {

    ArrayList<String> questionnaire = new ArrayList<String>();
    if (nom.getText() != null) {
      if (QuestionnaireController.nomSelectionner != null) {
        Main.facade.modifierUnQuestionnaire(QuestionnaireController.nomSelectionner, nom.getText(),
            QuestionnaireController.questions);
      } else {
        Main.facade.ajouterUnQuestionnaire(nom.getText(), questionnaire);
      }
    }

    this.fermerPopUp(mouseEvent);
  }
}
