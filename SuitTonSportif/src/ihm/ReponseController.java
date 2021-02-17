package ihm;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ReponseController implements Initializable {
  @FXML
  private ListView<String> listSportif;
  @FXML
  private ListView<String> listQuestionnaire;
  @FXML
  private ListView<VBox> listReponse;
  @FXML
  private DatePicker date;
  @FXML
  private VBox reponse;

  // Liste d'infos a afficher dans les listes view
  private List<String> sportifs;
  private Map<String, Integer> questionReponse = new HashMap<String, Integer>();
  private List<String> questionnaire;

  // Informations selectionner
  private String sportifSelectioner = null;
  public static String questionnaireSelectionner = null;
  private int semaineSelectionner = 0;

  private List<VBox> lignes;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // remplir la liste des sportifs
    sportifs = Main.facade.obtenirListeSportif();
    if (sportifs == null) {
      sportifs = new ArrayList<String>();
    }

    ObservableList<String> items = FXCollections.observableArrayList();
    items.addAll(sportifs);
    listSportif.setItems(items);

    // remplir la liste des questionnaires
    questionnaire = Main.facade.consulterLesQuestionnaire();
    if (questionnaire == null) {
      questionnaire = new ArrayList<String>();
    }

    ObservableList<String> items2 = FXCollections.observableArrayList();
    items2.addAll(questionnaire);
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
    Date date = Date.from(jour.atStartOfDay(ZoneId.systemDefault()).toInstant());
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);

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
      questionReponse = Main.facade.obtenirQuestionnaireEtReponses(this.semaineSelectionner,
          this.sportifSelectioner, questionnaireSelectionner);
      this.remplirListeReponse();
    }
  }

  /**
   * remplie la liste view avec la liste des réponses.
   */
  public void remplirListeReponse() {
    // creation des lignes contenant l question et les radioButton de reponse
    lignes = new ArrayList<VBox>();
    for (Map.Entry<String, Integer> mapentry : questionReponse.entrySet()) {
      VBox ligne = new VBox();
      ligne.setSpacing(5);

      // creation des bouton de reponses
      ToggleGroup rep = new ToggleGroup();
      RadioButton oui = new RadioButton("Oui");
      RadioButton non = new RadioButton("Non");
      oui.setToggleGroup(rep);
      non.setToggleGroup(rep);
      if (mapentry.getValue() == 0) {
        non.setSelected(true);
      } else {
        oui.setSelected(true);
      }

      // placer les boutons dans une hbox
      HBox hb = new HBox();
      hb.setSpacing(10);
      hb.getChildren().addAll(oui, non);
      hb.setAlignment(Pos.CENTER);

      Label label = new Label(mapentry.getKey());

      // tout mettre dans la ligne
      ligne.getChildren().addAll(label, hb);
      lignes.add(ligne);
    }

    // créer la liste view avec les hbox
    ObservableList<VBox> items = FXCollections.observableArrayList();
    items.addAll(lignes);
    listReponse.setItems(items);
  }

  /**
   * Enregistre les réponses aux questions.
   * 
   * @param mouseEvent : clique sur le bouton
   */
  @FXML
  public void enregistrer(MouseEvent mouseEvent) {
    Map<String, Integer> map = new HashMap<String, Integer>();
    for (VBox vb : lignes) {
      HBox hb = (HBox) vb.getChildren().get(1);
      Label l = (Label) vb.getChildren().get(0);
      RadioButton rb = (RadioButton) hb.getChildren().get(0);
      map.put(l.getText(), rb.isSelected() ? 1 : 0);
    }
    Main.facade.modifierReponses(new Date(), this.sportifSelectioner, questionnaireSelectionner,
        map, this.semaineSelectionner);
    questionReponse = Main.facade.obtenirQuestionnaireEtReponses(this.semaineSelectionner,
        this.sportifSelectioner, questionnaireSelectionner);
    this.remplirListeReponse();
  }

  /**
   * Reinitialise les réponses à leur dernier état enregistrer.
   * 
   * @param mouseEvent : clique sur le bouton
   */
  @FXML
  public void reinitialiser(MouseEvent mouseEvent) {
    this.remplirListeReponse();
  }
}
