package ihm.controller;

import ihm.Main;
import ihm.PopUp;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ReponseController implements Initializable {
  @FXML
  private ListView<String> listSportif;
  @FXML
  private ListView<String> listQuestionnaire;
  @FXML
  private ListView<HBox> listReponse;
  @FXML
  private DatePicker date;
  @FXML
  private VBox reponse;

  private List<String> sportifs;
  private List<String> reponses;
  private List<String> questions;

  private String sportifSelectioner = null;
  public static String questionnaireSelectionner = null;
  private int semaineSelectionner = 0;

  private List<HBox> lignes;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    sportifs = Main.facade.obtenirListeSportif();
    if (sportifs == null) {
      sportifs = new ArrayList<String>();
    }

    ObservableList<String> items = FXCollections.observableArrayList();
    items.addAll(sportifs);
    listSportif.setItems(items);

    questions = Main.facade.consulterLesQuestionnaire();
    if (questions == null) {
      questions = new ArrayList<String>();
    }

    ObservableList<String> items2 = FXCollections.observableArrayList();
    items2.addAll(questions);
    listQuestionnaire.setItems(items2);

    date.valueProperty().addListener((ov, oldValue, newValue) -> {
      selectionDate();
    });
  }

  @FXML
  public void selectionSportif(MouseEvent mouseEvent) {
    this.sportifSelectioner = listSportif.getSelectionModel().getSelectedItem();
    this.afficherReponses();
  }

  @FXML
  public void selectionQuestion(MouseEvent mouseEvent) {
    questionnaireSelectionner = listQuestionnaire.getSelectionModel().getSelectedItem();
    this.afficherReponses();
  }

  /**
   * Récupère le numero de la semaine grace à la date sélèctionner.
   */
  public void selectionDate() {
    LocalDate jour = date.getValue();
    Calendar calendar = new GregorianCalendar.Builder()
        .setDate(jour.getYear(), jour.getMonthValue(), jour.getDayOfMonth()).build();

    this.semaineSelectionner = calendar.get(Calendar.WEEK_OF_YEAR);
    this.afficherReponses();
  }

  /**
   * Affiche les reponses aux questions quand le sportif le questionaire et la date sont
   * sélectioner.
   */
  public void afficherReponses() {
    if (this.sportifSelectioner != null && questionnaireSelectionner != null
        && this.semaineSelectionner != 0) {
      reponse.setVisible(true);
      HashMap<String, Integer> questionsReponses = Main.facade.obtenirQuestionnaireEtReponses(
          this.semaineSelectionner, this.sportifSelectioner, questionnaireSelectionner);
      reponses = new ArrayList<String>();
      for (Map.Entry<String, Integer> mapentry : questionsReponses.entrySet()) {
        reponses.add(mapentry.getKey() + "\n" + mapentry.getValue());
      }
      this.remplirListeReponse();
    }
  }

  public void remplirListeReponse() {
    // remplir les hbox avec le nom et les bouton modifier et supprimer
    lignes = new ArrayList<HBox>();
    for (String nom : reponses) {
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

      Label label = new Label(nom);
      label.setPrefWidth(193);
      ligne.getChildren().addAll(label, modifier);
      lignes.add(ligne);
    }

    // créer la liste view avec les hbox
    ObservableList<HBox> items = FXCollections.observableArrayList();
    items.addAll(lignes);
    listReponse.setItems(items);
  }

  public void modifier(String nom) throws IOException {
    final URL fxmlUrl = getClass().getResource("../view/ajoutReponse.fxml");
    final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
    Pane root = fxmlLoader.load();

    PopUp popup = new PopUp(root, "Modification de la réponse");
    popup.display();
  }
}
