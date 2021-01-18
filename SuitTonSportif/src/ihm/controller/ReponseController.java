package ihm.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import java.util.GregorianCalendar;

public class ReponseController implements Initializable {
  @FXML
  private ListView<String> listSportif;
  @FXML
  private ListView<String> listQuestionnaire;
  @FXML
  private DatePicker date;
  @FXML
  private VBox reponse;

  private List<String> reponses;
  private List<String> questions;

  private String sportifSelectioner = null;
  private String questionSelectionner = null;
  private int semaineSelectionner = 0;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    reponses = new ArrayList<String>();
    reponses.add("pouet");
    reponses.add("prout");
    reponses.add("boop");

    ObservableList<String> items = FXCollections.observableArrayList();
    items.addAll(reponses);
    listSportif.setItems(items);

    questions = new ArrayList<String>();
    questions.add("pouet");
    questions.add("prout");
    questions.add("boop");

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
    // create a GregorianCalendar with the Pacific Daylight time zone
    // and the current date and time
    Calendar calendar = new GregorianCalendar();
    LocalDate jour = date.getValue();
    Date jourSelectioner = Date.from(jour.atStartOfDay(ZoneId.systemDefault()).toInstant());
    calendar.setTime(jourSelectioner);

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
    }
  }
}
