package ihm.controller;

import ihm.Main;
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
  public static List<String> questions = new ArrayList<String>();

  private List<HBox> lignes;
  private List<HBox> lignes2;

  public static String nomSelectionner = null;
  public static String questionSelectionner = null;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // récuperer la liste des sportif
    questionnaire = Main.facade.consulterLesQuestionnaire();

    if (questionnaire == null) {
      questionnaire = new ArrayList<String>();
    }

    if (questions == null) {
      questions = new ArrayList<String>();
    }

    this.remplirListeQuestionnaire();

  }

  /**
   * remplie la liste 
   */
  public void remplirListeQuestionnaire() {
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
            modifierQuestionnaire(nom);
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
          supprimerQuestionnaire(nom);
        }
      });

      ligne.getChildren().addAll(new Label(nom), modifier, supprimer);
      lignes.add(ligne);
    }

    ObservableList<HBox> items = FXCollections.observableArrayList();
    items.addAll(lignes);
    list.setItems(items);

  }

  public void remplirListQuestions() {
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
            modifierQuestions(intitule);
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
          supprimerQuestions(intitule);
        }
      });

      ligne.getChildren().addAll(new Label(intitule), modifier, supprimer);
      lignes2.add(ligne);
    }

    // créer la liste view avec les hbox


    ObservableList<HBox> items2 = FXCollections.observableArrayList();
    items2.addAll(lignes2);
    list2.setItems(items2);
  }

  @FXML
  public void selectionnerQuestion(MouseEvent mouseEvent) throws IOException {
    HBox selected2 = list2.getSelectionModel().getSelectedItem();
    Label question = (Label) selected2.getChildren().get(0);
    questionSelectionner = question.getText();

    this.modifierQuestions(question.getText());
  }


  /**
   * Lance la PopUp pour ajouter un questionnaire à la liste.
   *
   * @param mouseEvent : clique de l'utilisateur
   * @throws IOException : en cas d'échec de l'ecture du fxml
   */
  @FXML
  public void ajoutQuestionnaire(MouseEvent mouseEvent) throws IOException {
    nomSelectionner = null;
    final URL fxmlUrl = getClass().getResource("../view/ajoutQuestionnaire.fxml");
    final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
    Pane root = fxmlLoader.load();

    PopUp popup = new PopUp(root, "Ajout d'un questionnaire");
    popup.display();

    questionnaire = Main.facade.consulterLesQuestionnaire();
    if (questionnaire == null) {
      questionnaire = new ArrayList<String>();
    }
    this.remplirListeQuestionnaire();
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

    HBox selected = list.getSelectionModel().getSelectedItem();
    Label questionnaire = (Label) selected.getChildren().get(0);

    questions = Main.facade.consulterLesQuestionDuQuestionnaire(questionnaire.getText());
    System.out.println(questions);


    if (questions == null) {
      questions = new ArrayList<String>();
    }
    this.remplirListQuestions();
  }

  /**
   * Afficher dans la partie de droite les informations du sportif : - nom - prénom - pseudo - date
   * de naissance - sports.
   *
   * @param mouseEvent : clique de l'utilisateur
   */
  @FXML
  public void afficherQuestions(MouseEvent mouseEvent) {
    HBox selected = list.getSelectionModel().getSelectedItem();
    Label questionnaire = (Label) selected.getChildren().get(0);
    nomSelectionner = questionnaire.getText();

    this.afficherQuestions(questionnaire.getText());


  }

  /**
   * Affiche les questions correspondantes au questionnaire.
   * @param nom : nom du questionanire
   */
  public void afficherQuestions(String nom) {
    information.setVisible(true);

    questions = Main.facade.consulterLesQuestionDuQuestionnaire(nom);

    this.remplirListQuestions();
  }

  /**
   * Ouvre la PopUp pour modifier les infos du sportif.
   *
   * @param nom : nom du sportif à modifier
   * @throws IOException : en cas d'échec de l'ecture du fxml
   */
  public void modifierQuestionnaire(String nom) throws IOException {
    nomSelectionner = nom;
    final URL fxmlUrl = getClass().getResource("../view/ajoutQuestionnaire.fxml");
    final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
    Pane root = fxmlLoader.load();

    PopUp popup = new PopUp(root, "Modification du questionnaire");
    popup.display();

    questionnaire = Main.facade.consulterLesQuestionnaire();
    if (questionnaire == null) {
      questionnaire = new ArrayList<String>();
    }
    questions = Main.facade.consulterLesQuestionDuQuestionnaire(questionSelectionner);
    if (questions == null) {
      questions = new ArrayList<String>();
    }
    this.remplirListQuestions();
    this.remplirListeQuestionnaire();
  }

  /**
   * Mofdifie le nom d'une question.
   * @param intitule : nom de la question à modfier
   * @throws IOException : si la popup s'ouvre pas
   */
  public void modifierQuestions(String intitule) throws IOException {
    questionSelectionner = intitule;
    final URL fxmlUrl = getClass().getResource("../view/ajoutQuestions.fxml");
    final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
    Pane root = fxmlLoader.load();

    PopUp popup = new PopUp(root, "Modification d'une question");
    popup.display();

    questionSelectionner = null;

    questionnaire = Main.facade.consulterLesQuestionDuQuestionnaire(nomSelectionner);
    if (questionnaire == null) {
      questionnaire = new ArrayList<String>();
    }
    questions = Main.facade.consulterLesQuestionDuQuestionnaire(questionSelectionner);
    if (questions == null) {
      questions = new ArrayList<String>();
    }
    this.remplirListQuestions();
    this.remplirListQuestions();
  }

  /**
   * Supprime un sportif de la liste.
   *
   * @param nom : nom du sportif à supprimer
   */
  public void supprimerQuestionnaire(String nom) {
    Main.facade.supprimerUnQuestionnaire(nom);
    questionnaire = Main.facade.consulterLesQuestionnaire();
    if (questionnaire == null) {
      questionnaire = new ArrayList<String>();
    }
    this.remplirListeQuestionnaire();
    information.setVisible(false);
  }

  /**
   * Supprime une questiond de la liste.
   * @param nom : intitule de la question
   */
  public void supprimerQuestions(String nom) {
    Main.facade.supprimerUneQuestion(nomSelectionner, nom);
    questions = Main.facade.consulterLesQuestionDuQuestionnaire(nomSelectionner);
    if (questions == null) {
      questions = new ArrayList<String>();
      information.setVisible(false);
    }
    this.remplirListQuestions();

  }
}
