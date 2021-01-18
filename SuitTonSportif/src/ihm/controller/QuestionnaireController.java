package ihm.controller;

import ihm.PopUp;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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


public class QuestionnaireController implements Initializable {
  @FXML
  private ListView<HBox> list;
  @FXML
  private ListView<HBox> list2;
  @FXML
  private Label nom;
  @FXML
  private Label intitule;
  @FXML
  private VBox information;
  @FXML
  private ListView<String> listQuestions;

  private List<String> questionnaire = new ArrayList<String>();
  private List<String> questions = new ArrayList<String>();

  private List<HBox> lignes;
  private List<HBox> lignes2;

  public static String nomSelectionner = null;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // récuperer la liste des sportif
    questionnaire.add("1");
    questionnaire.add("2");
    questionnaire.add("3");

    questions.add("pouet");
    questions.add("prout");
    questions.add("boop");

    // remplir les hbox avec le nom et les bouton modifier et supprimer
    lignes = new ArrayList<HBox>();
    for (String nom : questionnaire) {
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
          try {
            modifier(nom);
          } catch (IOException e1) {
            // TODO Auto-generated catch block
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
      supprimer.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          supprimer(nom, ligne);
        }
      });

      ligne.getChildren().addAll(new Label(nom), modifier, supprimer);
      lignes.add(ligne);
    }


    lignes2 = new ArrayList<HBox>();
    for (String intitule : questions) {
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
          try {
            modifier(intitule);
          } catch (IOException e1) {
            // TODO Auto-generated catch block
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
      supprimer.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          supprimer(intitule, ligne);
        }
      });

      ligne.getChildren().addAll(new Label(intitule), modifier, supprimer);
      lignes2.add(ligne);
    }

    // créer la liste view avec les hbox
    ObservableList<HBox> items = FXCollections.observableArrayList();
    items.addAll(lignes);
    list.setItems(items);

    ObservableList<HBox> items2 = FXCollections.observableArrayList();
    System.out.println(items2);
    System.out.println(list2);
    items2.addAll(lignes2);
    list2.setItems(items2);
  }

  /**
   * Lance la PopUp pour ajouter un questionnaire à la liste.
   *
   * @param mouseEvent : clique de l'utilisateur
   * @throws IOException : en cas d'échec de l'ecture du fxml
   */
  @FXML
  public void ajoutQuestionnaire(MouseEvent mouseEvent) throws IOException {
    final URL fxmlUrl = getClass().getResource("../view/ajoutQuestionnaire.fxml");
    final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
    Pane root = fxmlLoader.load();

    PopUp popup = new PopUp(root, "Ajout d'un questionnaire");
    popup.display();
  }

  /**
   * Lance la PopUp pour ajouter une question à la liste.
   *
   * @param mouseEvent : clique de l'utilisateur
   * @throws IOException : en cas d'échec de l'ecture du fxml
   */
  @FXML
  public void ajoutQuestions(MouseEvent mouseEvent) throws IOException {
    final URL fxmlUrl = getClass().getResource("../view/ajoutQuestions.fxml");
    final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
    Pane root = fxmlLoader.load();

    PopUp popup = new PopUp(root, "Ajout d'une question");
    popup.display();
  }

  /**
   * Afficher dans la partie de droite les informations du sportif : - nom - prénom - pseudo - date
   * de naissance - sports.
   *
   * @param mouseEvent : clique de l'utilisateur
   */
  @FXML
  public void afficherQuestions(MouseEvent mouseEvent) {
    information.setVisible(true);
    HBox selected = list.getSelectionModel().getSelectedItem();
    Label questionnaire = (Label) selected.getChildren().get(0);
    nom.setText(questionnaire.getText());
  }

  /**
   * Ouvre la PopUp pour modifier les infos du sportif.
   *
   * @param nom : nom du sportif à modifier
   * @throws IOException : en cas d'échec de l'ecture du fxml
   */
  public void modifier(String nom) throws IOException {
    nomSelectionner = nom;
    final URL fxmlUrl = getClass().getResource("../view/ajoutQuestionnaire.fxml");
    final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
    Pane root = fxmlLoader.load();

    PopUp popup = new PopUp(root, "Modification du questionnaire");
    popup.display();
  }

  /**
   * Supprime un sportif de la liste.
   *
   * @param nom : nom du sportif à supprimer
   * @param hb : HBox à supprimer de la liste view
   */
  public void supprimer(String nom, HBox hb) {
    questionnaire.remove(nom);
    list.getItems().remove(hb);
  }
}
