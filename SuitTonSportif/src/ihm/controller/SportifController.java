package ihm.controller;

import ihm.Main;
import ihm.PopUp;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


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

  private List<String> sportif;

  private List<HBox> lignes;

  public static String nomSelectionner = null;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    sportif = Main.facade.obtenirListeSportif();
    if (sportif == null) {
      sportif = new ArrayList<String>();
    }

    this.remplirListe();
  }

  /**
   * remplie la liste view avec les pseudo des sportif.
   */
  public void remplirListe() {
    // remplir les hbox avec le nom et les bouton modifier et supprimer
    lignes = new ArrayList<HBox>();
    for (String nom : sportif) {
      HBox ligne = new HBox();
      ligne.setSpacing(5);

      Button modifier = new Button();
      Image crayon = new Image(getClass().getResourceAsStream("../icon/crayon.png"));
      ImageView image = new ImageView(crayon);
      image.setFitWidth(20);
      image.setPreserveRatio(true);
      modifier.setGraphic(image);
      modifier.getStylesheets()
          .add(getClass().getResource("../style/listView.css").toExternalForm());
      modifier.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          try {
            modifier(nom);
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        }
      });

      Button supprimer = new Button();
      Image pbl = new Image(getClass().getResourceAsStream("../icon/poubelle.png"));
      ImageView image2 = new ImageView(pbl);
      image2.setFitWidth(20);
      image2.setPreserveRatio(true);
      supprimer.setGraphic(image2);
      supprimer.getStylesheets()
          .add(getClass().getResource("../style/listView.css").toExternalForm());
      supprimer.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          supprimer(nom);
        }
      });

      Label label = new Label(nom);
      label.setPrefWidth(193);
      ligne.getChildren().addAll(label, modifier, supprimer);
      lignes.add(ligne);
    }

    // créer la liste view avec les hbox
    ObservableList<HBox> items = FXCollections.observableArrayList();
    items.addAll(lignes);
    list.setItems(items);
  }

  /**
   * Lance la PopUp pour ajouter un sportif à la liste.
   *
   * @param mouseEvent : clique de l'utilisateur
   * @throws IOException : en cas d'échec de l'ecture du fxml
   */
  @FXML
  public void ajoutSportif(MouseEvent mouseEvent) throws IOException {
    final URL fxmlUrl = getClass().getResource("../view/ajoutSportif.fxml");
    final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
    Pane root = fxmlLoader.load();

    PopUp popup = new PopUp(root, "Ajout d'un sportif");
    popup.display();

    sportif = Main.facade.obtenirListeSportif();
    if (sportif == null) {
      sportif = new ArrayList<String>();
    }
    this.remplirListe();
  }

  /**
   * Afficher dans la partie de droite les informations du sportif : - nom - prénom - pseudo - date
   * de naissance - sports.
   *
   * @param mouseEvent : clique de l'utilisateur
   */
  @FXML
  public void afficherInformationSportif(MouseEvent mouseEvent) {
    HBox selected = list.getSelectionModel().getSelectedItem();
    Label sportif = (Label) selected.getChildren().get(0);
    this.afficherInformationSportif(sportif.getText());
  }

  /**
   * Afficher dans la partie de droite les informations du sportif : - nom - prénom - pseudo - date
   * de naissance - sports.
   * 
   * @param sportif : pseudo du sportif à afficher
   */
  public void afficherInformationSportif(String sportif) {
    information.setVisible(true);
    pseudo.setText(sportif);
    nom.setText(Main.facade.obtenirNomSportif(sportif));
    prenom.setText(Main.facade.obtenirPrenomSportif(sportif));
    Calendar date = Main.facade.obtenirDateDeNaissanceSportif(sportif);
    dateDeNaissance.setText(date.get(Calendar.DAY_OF_MONTH) + " / " + date.get(Calendar.MONTH)
        + " / " + date.get(Calendar.YEAR));
    // sports.setText(Main.facade.consulterLesActivitesDuSportif(sportif.getText()).toString());
  }

  /**
   * Ouvre la PopUp pour modifier les infos du sportif.
   *
   * @param nom : nom du sportif à modifier
   * @throws IOException : en cas d'échec de l'ecture du fxml
   */
  public void modifier(String nom) throws IOException {
    nomSelectionner = nom;
    final URL fxmlUrl = getClass().getResource("../view/ajoutSportif.fxml");
    final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
    Pane root = fxmlLoader.load();

    PopUp popup = new PopUp(root, "Modification du sportif");
    popup.display();

    sportif = Main.facade.obtenirListeSportif();
    if (sportif == null) {
      sportif = new ArrayList<String>();
    }
    this.remplirListe();
    information.setVisible(false);
  }

  /**
   * Supprime un sportif de la liste.
   *
   * @param nom : nom du sportif à supprimer
   */
  public void supprimer(String nom) {
    Main.facade.supprimerUnSportif(nom);
    sportif = Main.facade.obtenirListeSportif();
    if (sportif == null) {
      sportif = new ArrayList<String>();
    }
    this.remplirListe();
    information.setVisible(false);
  }
}
