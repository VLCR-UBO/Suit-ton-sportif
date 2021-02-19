package ihm;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopUp {
  private Scene scene;
  private Stage popup;

  /**
   *création d'une popup afin d'ajouter, modifier une question, un questionnaire ou une réponse.
   *
   * @param p : emplacement du popup
   * @param titre : titre du popup
   */
  public PopUp(Parent p, String titre) {
    popup = new Stage();
    popup.setTitle(titre);
    popup.initModality(Modality.NONE);
    popup.initStyle(StageStyle.UNDECORATED);

    scene = new Scene(p);

    /*popup.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
      if (!isNowFocused) {
        popup.hide();
      }
    });*/
  }

  public Stage getStage() {
    return popup;
  }

  public Scene getScene() {
    return scene;
  }


  public void display() {
    popup.setScene(scene);
    popup.showAndWait();
  }
}
