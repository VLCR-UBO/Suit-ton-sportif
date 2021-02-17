package ihm;

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
  @FXML
  private Tab tabStatistique;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    tabReponse.setOnSelectionChanged(e -> {
      if (tabReponse.isSelected()) {
        final URL fxmlUrl2 = getClass().getResource("/ihm/reponse.fxml");
        final FXMLLoader fxmlLoader2 = new FXMLLoader(fxmlUrl2);
        try {
          Pane root2 = fxmlLoader2.load();
          tabReponse.setContent(root2);
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    });
    
    tabStatistique.setOnSelectionChanged(e -> {
      if (tabStatistique.isSelected()) {
        final URL fxmlUrl4 = getClass().getResource("/ihm/statistique.fxml");
        final FXMLLoader fxmlLoader4 = new FXMLLoader(fxmlUrl4);
        try {
          Pane root4 = fxmlLoader4.load();
          tabStatistique.setContent(root4);
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    });

    final URL fxmlUrl = getClass().getResource("/ihm/sportif.fxml");
    final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);

    final URL fxmlUrl3 = getClass().getResource("/ihm/questionnaire.fxml");
    final FXMLLoader fxmlLoader3 = new FXMLLoader(fxmlUrl3);

    try {
      Pane root = fxmlLoader.load();
      tabSportif.setContent(root);

      Pane root3 = fxmlLoader3.load();
      tabQuestionnaire.setContent(root3);
    } catch (IOException e2) {
      e2.printStackTrace();
    }
  }
}
