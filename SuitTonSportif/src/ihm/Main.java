package ihm;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    final URL fxmlUrl = getClass().getResource("view/onglet.fxml");
    final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
    Pane root = fxmlLoader.load();

    primaryStage.setTitle("Suit ton sportif !!");
    primaryStage.setScene(new Scene(root));
    primaryStage.centerOnScreen();
    primaryStage.show();
  }
}
