package ihm;

import ihm.Main;
import ihm.PopUp;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
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
import javafx.stage.Stage;


public class StatistiqueController implements Initializable {
  @FXML
  private ListView<String> listQuestionnaire;
  @FXML
  private ListView<String> listQuestion;

  private List<String> questionnaire;
  private List<String> question;

  public String questionnaireSelectionner = null;
  public String questionSelectionner = null;
  private int semaineSelectionner = 0;

  @FXML
  private VBox vbQuestion;
  @FXML
  private PieChart statistique;
  @FXML
  private DatePicker date;

  private List<Integer> reponses;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    statistique.setVisible(false);
    vbQuestion.setVisible(false);

    questionnaire = Main.facade.consulterLesQuestionnaire();
    if (questionnaire == null) {
      questionnaire = new ArrayList<String>();
    }

    ObservableList<String> items = FXCollections.observableArrayList();
    items.addAll(questionnaire);
    listQuestionnaire.setItems(items);

    date.valueProperty().addListener((ov, oldValue, newValue) -> {
      selectionDate();
    });
  }

  @FXML
  public void selectionQuestionnaire(MouseEvent mouseEvent) {
    questionnaireSelectionner = listQuestionnaire.getSelectionModel().getSelectedItem();
    this.afficherQuestions();
  }

  @FXML
  public void selectionQuestion(MouseEvent mouseEvent) {
    questionSelectionner = listQuestion.getSelectionModel().getSelectedItem();
    this.afficherReponses();
  }

  public void selectionDate() {
    LocalDate jour = date.getValue();
    Date date = Date.from(jour.atStartOfDay(ZoneId.systemDefault()).toInstant());
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);

    this.semaineSelectionner = calendar.get(Calendar.WEEK_OF_YEAR);
    this.afficherReponses();
  }

  public void afficherQuestions() {
    if (this.questionnaireSelectionner != null) {
      vbQuestion.setVisible(true);

      question = Main.facade.consulterLesQuestionDuQuestionnaire(this.questionnaireSelectionner);
      if (question == null) {
        question = new ArrayList<String>();
      }

      ObservableList<String> items = FXCollections.observableArrayList();
      items.addAll(question);
      listQuestion.setItems(items);
    }
  }

  public void afficherReponses() {
    if (this.questionnaireSelectionner != null && this.questionSelectionner != null
        && this.semaineSelectionner != 0) {
      statistique.setVisible(true);
      reponses = Main.facade.obtenirReponses(this.questionSelectionner, this.semaineSelectionner);
      this.remplirListeReponse(reponses);
    }
  }

  public void remplirListeReponse(List<Integer> reponses) {
    int nbReponsesPositives = reponses.get(0);
    int nbReponsesNegatives = reponses.get(1);
    int nbReponsesTotal = nbReponsesPositives + nbReponsesNegatives;
    if (nbReponsesTotal != 0) {
      double pourcentagePositif = (double) nbReponsesPositives /  (double) nbReponsesTotal;
      double pourcentageNegatif = (double) nbReponsesNegatives / (double) nbReponsesTotal;
      int pourcentagePositifInt = (int) (pourcentagePositif * 100);
      int pourcentageNegatifInt = (int) (pourcentageNegatif * 100);
      ObservableList<PieChart.Data> pieChartData =
          FXCollections.observableArrayList(new PieChart.Data("Oui", pourcentagePositifInt),
              new PieChart.Data("Non", pourcentageNegatifInt));
      statistique.setData(pieChartData);
    } else {
      statistique.setVisible(false);
    }
  }

}
