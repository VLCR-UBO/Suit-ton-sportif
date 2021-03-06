package ihm;

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
  public static String questionnaireSelectionner = null;
  public static String questionSelectionner = null;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

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
   * remplie la liste view avec les questionnaires. ainsi que les boutons modifier et supprimer pour
   * chaque questionnaire.
   */
  public void remplirListeQuestionnaire() {
    lignes = new ArrayList<HBox>();
    for (String nom : questionnaire) {
      HBox ligne = new HBox();
      ligne.setSpacing(10);

      Button modifier = new Button();
      Image crayon = new Image(getClass().getResourceAsStream("/ihm/icon/crayon.png"));
      ImageView image = new ImageView(crayon);
      image.setFitWidth(20);
      image.setPreserveRatio(true);
      modifier.setGraphic(image);
      modifier.getStylesheets()
          .add(getClass().getResource("/ihm/style/listView.css").toExternalForm());
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
      Image pbl = new Image(getClass().getResourceAsStream("/ihm/icon/poubelle.png"));
      ImageView image2 = new ImageView(pbl);
      image2.setFitWidth(20);
      image2.setPreserveRatio(true);
      supprimer.setGraphic(image2);
      supprimer.getStylesheets()
          .add(getClass().getResource("/ihm/style/listView.css").toExternalForm());
      supprimer.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          supprimerQuestionnaire(nom);
        }
      });

      Label label = new Label(nom);
      label.setPrefWidth(155);
      ligne.getChildren().addAll(label, modifier, supprimer);
      lignes.add(ligne);
    }

    ObservableList<HBox> items = FXCollections.observableArrayList();
    items.addAll(lignes);
    list.setItems(items);

  }

  /**
   * remplie la liste view avec les questions. ainsi que les boutons modifier et supprimer pour
   * chaque question.
   */
  public void remplirListQuestions() {
    lignes2 = new ArrayList<HBox>();
    for (String intitule : questions) {
      HBox ligne = new HBox();
      ligne.setSpacing(10);

      Button modifier = new Button();
      Image crayon = new Image(getClass().getResourceAsStream("/ihm/icon/crayon.png"));
      ImageView image = new ImageView(crayon);
      image.setFitWidth(20);
      image.setPreserveRatio(true);
      modifier.setGraphic(image);
      modifier.getStylesheets()
          .add(getClass().getResource("/ihm/style/listView.css").toExternalForm());
      modifier.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          try {
            modifierQuestions(intitule, ligne);
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        }
      });


      Button supprimer = new Button();
      Image pbl = new Image(getClass().getResourceAsStream("/ihm/icon/poubelle.png"));
      ImageView image2 = new ImageView(pbl);
      image2.setFitWidth(20);
      image2.setPreserveRatio(true);
      supprimer.setGraphic(image2);
      supprimer.getStylesheets()
          .add(getClass().getResource("/ihm/style/listView.css").toExternalForm());
      supprimer.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          supprimerQuestions(intitule);
        }
      });

      Label label = new Label(intitule);
      label.setPrefWidth(148);
      ligne.getChildren().addAll(label, modifier, supprimer);
      lignes2.add(ligne);
    }

    // créer la liste view avec les hbox


    ObservableList<HBox> items2 = FXCollections.observableArrayList();
    items2.addAll(lignes2);
    list2.setItems(items2);
  }

  /**
   * permet de selectionner la question sur lequel l'utilisateur clique.
   */
  @FXML
  public void selectionnerQuestion(MouseEvent mouseEvent) throws IOException {
    HBox selected = list2.getSelectionModel().getSelectedItem();
    if (selected != null) {
      Label question = (Label) selected.getChildren().get(0);
      questionSelectionner = question.getText();

      this.modifierQuestions(question.getText(), selected);
    }
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
    final URL fxmlUrl = getClass().getResource("/ihm/ajoutQuestionnaire.fxml");
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
    if (questionnaireSelectionner != null) {
      final URL fxmlUrl = getClass().getResource("/ihm/ajoutQuestions.fxml");
      final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
      Pane root = fxmlLoader.load();

      PopUp popup = new PopUp(root, "Ajout d'une question");
      popup.display();

      HBox selected = list.getSelectionModel().getSelectedItem();
      Label questionnaire = (Label) selected.getChildren().get(0);

      questions = Main.facade.consulterLesQuestionDuQuestionnaire(questionnaire.getText());

      if (questions == null) {
        questions = new ArrayList<String>();
      }
      this.remplirListQuestions();
      this.afficherQuestions(questionnaireSelectionner);
      list.getSelectionModel().select(selected);
    }
  }

  /**
   * Afficher dans la partie de droite les questions relie a un questionnaire.
   *
   * @param mouseEvent : clique de l'utilisateur
   */
  @FXML
  public void afficherQuestions(MouseEvent mouseEvent) {
    HBox selected = list.getSelectionModel().getSelectedItem();
    if (selected != null) {
      Label questionnaire = (Label) selected.getChildren().get(0);
      nomSelectionner = questionnaire.getText();

      questionnaireSelectionner = questionnaire.getText();
      this.afficherQuestions(questionnaire.getText());
    }
  }

  /**
   * Affiche les questions correspondantes au questionnaire.
   *
   * @param nom : nom du questionanire
   */
  public void afficherQuestions(String nom) {
    information.setVisible(true);

    questions = Main.facade.consulterLesQuestionDuQuestionnaire(nom);

    this.remplirListQuestions();
  }

  /**
   * Ouvre la PopUp pour modifier le nom du questionnaire.
   *
   * @param nom : nom du sportif à modifier
   * @throws IOException : en cas d'échec de l'ecture du fxml
   */
  public void modifierQuestionnaire(String nom) throws IOException {
    nomSelectionner = nom;
    final URL fxmlUrl = getClass().getResource("/ihm/ajoutQuestionnaire.fxml");
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
   * permet de modifier une question.
   *
   * @param intitule : nom de la question à modfier
   * @throws IOException : si la popup s'ouvre pas
   */
  public void modifierQuestions(String intitule, HBox selected) throws IOException {
    questionSelectionner = intitule;
    final URL fxmlUrl = getClass().getResource("/ihm/ajoutQuestions.fxml");
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
    this.afficherQuestions(questionnaireSelectionner);
    list.getSelectionModel().select(selected);
  }

  /**
   * Supprime un questionnaire de la liste.
   *
   * @param nom : nom du questionnaire à supprimer
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
   * Supprime une question de la liste.
   *
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

  /**
   * permet d'exporter les reponses à un questionnaire.
   */
  @FXML
  public void exporter(MouseEvent mouseEvent) throws IOException {
    HBox selected = list.getSelectionModel().getSelectedItem();
    if (selected != null) {
      Label questionnaire = (Label) selected.getChildren().get(0);
      questionnaireSelectionner = questionnaire.getText();
      Main.facade.exporter(questionnaireSelectionner);
    }
  }
}
