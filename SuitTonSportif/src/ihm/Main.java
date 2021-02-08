package ihm;

import java.io.IOException;
import java.net.URL;
import fonctionnalite.Facade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
  public static Facade facade = new Facade();

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    final URL fxmlUrl = getClass().getResource("onglet.fxml");
    final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
    Pane root = fxmlLoader.load();

    primaryStage.setTitle("Suit ton sportif !!");
    primaryStage.setScene(new Scene(root));
    primaryStage.centerOnScreen();
    primaryStage.show();
  }
}
