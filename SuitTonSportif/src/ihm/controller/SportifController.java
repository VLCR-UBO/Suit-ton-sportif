package ihm.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ihm.PopUp;
import javafx.stage.Stage;

public class SportifController implements Initializable {
  @FXML
  private ListView<HBox> list;
  @FXML
  private Label nom;
  @FXML
  private Label prenom;
  @FXML
  private Label pseudo;
  @FXML
  private Label dateDeNaissance;
  @FXML
  private Label sports;
  @FXML
  private VBox information;

  private List<String> sportif = new ArrayList<String>();// TO DO : remplacer par les sportif qd ce
                                                         // sera créer
  private List<HBox> lignes;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // récuperer la liste des sportif
    sportif.add("patrick");
    sportif.add("denis");
    sportif.add("jp");

    // remplir les hbox avec le nom et les bouton modifier et supprimer
    lignes = new ArrayList<HBox>();
    for (String nom : sportif) {
      HBox ligne = new HBox();
      ligne.setSpacing(10);

      Button modifier = new Button();
      Image crayon = new Image(getClass().getResourceAsStream("../icon/crayon.png"));
      ImageView image = new ImageView(crayon);
      image.setFitWidth(20);
      image.setPreserveRatio(true);
      modifier.setGraphic(image);
      modifier.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          modifier(nom);
        }
      });

      Button supprimer = new Button();
      Image pbl = new Image(getClass().getResourceAsStream("../icon/poubelle.png"));
      ImageView image2 = new ImageView(pbl);
      image2.setFitWidth(20);
      image2.setPreserveRatio(true);
      supprimer.setGraphic(image2);
      supprimer.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          supprimer(nom, ligne);
        }
      });

      ligne.getChildren().addAll(new Label(nom), modifier, supprimer);
      lignes.add(ligne);
    }

    // créer la liste view avec les hbox
    ObservableList<HBox> items = FXCollections.observableArrayList();
    items.addAll(lignes);
    list.setItems(items);
  }

  @FXML
  public void ajoutSportif(MouseEvent mouseEvent) throws IOException {
    final URL fxmlUrl = getClass().getResource("../view/ajoutSportif.fxml");
    final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
    Pane root = fxmlLoader.load();

    PopUp popup = new PopUp(root, "Ajout d'un sportif");
    popup.display();
  }
  
  @FXML
  public void afficherInformationSportif(MouseEvent mouseEvent)  {
    information.setVisible(true);
    HBox selected = list.getSelectionModel().getSelectedItem();
    Label sportif = (Label) selected.getChildren().get(0);
    pseudo.setText(sportif.getText());
  }

  public void modifier(String nom) {

  }

  public void supprimer(String nom, HBox hb) {
    sportif.remove(nom);
    list.getItems().remove(hb);
  }
}
