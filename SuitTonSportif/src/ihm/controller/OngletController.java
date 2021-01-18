package ihm.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

public class OngletController implements Initializable {
  @FXML
  private Tab tabSportif;
  @FXML
  private Tab tabQuestionnaire;
  @FXML
  private Tab tabReponse;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // Onglet sportif
    final URL fxmlUrl = getClass().getResource("../view/sportif.fxml");
    final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);

    final URL fxmlUrl2 = getClass().getResource("../view/reponse.fxml");
    final FXMLLoader fxmlLoader2 = new FXMLLoader(fxmlUrl2);

    final URL fxmlUrl3 = getClass().getResource("../view/questionnaire.fxml");
    final FXMLLoader fxmlLoader3 = new FXMLLoader(fxmlUrl3);

    try {
      Pane root = fxmlLoader.load();
      tabSportif.setContent(root);

      Pane root2 = fxmlLoader2.load();
      tabReponse.setContent(root2);

      Pane root3 = fxmlLoader3.load();
      tabQuestionnaire.setContent(root3);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
