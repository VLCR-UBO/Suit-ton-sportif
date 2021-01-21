package ihm.controller;

import ihm.Main;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ReponseController implements Initializable {
  @FXML
  private ListView<String> listSportif;
  @FXML
  private ListView<String> listQuestionnaire;
  @FXML
  private ListView<String> listReponse;
  @FXML
  private DatePicker date;
  @FXML
  private VBox reponse;

  private List<String> sportifs;
  private List<String> reponses;
  private List<String> questions;

  private String sportifSelectioner = null;
  private String questionSelectionner = null;
  private int semaineSelectionner = 0;

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
    this.questionSelectionner = listQuestionnaire.getSelectionModel().getSelectedItem();
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
    if (this.sportifSelectioner != null && this.questionSelectionner != null
        && this.semaineSelectionner != 0) {
      reponse.setVisible(true);
      HashMap<String, Integer> questionsReponses = Main.facade.obtenirQuestionnaireEtReponses(
          this.semaineSelectionner, this.sportifSelectioner, this.questionSelectionner);
      reponses = new ArrayList<String>();
      for (Map.Entry<String, Integer> mapentry : questionsReponses.entrySet()) {
        reponses.add(mapentry.getKey() + "\n" + mapentry.getValue());
      }
      ObservableList<String> items = FXCollections.observableArrayList();
      items.addAll(reponses);
      listReponse.setItems(items);
    }
  }
}
