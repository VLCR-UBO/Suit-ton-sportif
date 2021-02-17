package ihm;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AjoutReponseController implements Initializable {
  @FXML
  private Label question;
  @FXML
  private ToggleGroup reponse;
  @FXML
  private RadioButton radioOui;
  @FXML
  private RadioButton radioNon;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    question.setText(ReponseController.questionnaireSelectionner);
  }

  /**
   * Supprime un sportif de la liste.
   *
   * @param mouseEvent : clique de l'utilisateur
   * @throws IOException : en cas d'échec de l'ecture du fxml
   */
  @FXML
  public void fermerPopUp(MouseEvent mouseEvent) {
    ReponseController.questionnaireSelectionner = null;
    Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
    stage.close();
  }

  @FXML
  public void envoyerReponse(MouseEvent mouseEvent) {
  }
}
