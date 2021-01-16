package ihm;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ListViewModif {
  private List<HBox> lignes;
  private Button modifier;
  private Button supprimer;

  /**
   * Créer un liste view de hbox contenant un nom un 
   * bouton de suppression et un bouton de modification.
   * @param noms : Liste de noms à mettre dans la liste view
   */
  public ListViewModif(List<String> noms, ListView<HBox> list) {
    lignes = new ArrayList<HBox>();

    for (String nom : noms) {
      HBox ligne = new HBox();
      ligne.setSpacing(10);

      modifier = new Button();
      Image crayon = new Image(getClass().getResourceAsStream("icon/crayon.png"));
      ImageView image = new ImageView(crayon);
      image.setFitWidth(20);
      image.setPreserveRatio(true);
      modifier.setGraphic(image);

      supprimer = new Button();
      Image pbl = new Image(getClass().getResourceAsStream("icon/poubelle.png"));
      ImageView image2 = new ImageView(pbl);
      image2.setFitWidth(20);
      image2.setPreserveRatio(true);
      supprimer.setGraphic(image2);

      ligne.getChildren().addAll(new Label(nom), modifier, supprimer);
      lignes.add(ligne);
    }

    // créer la liste view avec les hbox
    ObservableList<HBox> items = FXCollections.observableArrayList();
    items.addAll(lignes);
    list.setItems(items);
  }

  public Button getModifier() {
    return modifier;
  }

  public void setModifier(Button modifier) {
    this.modifier = modifier;
  }

  public Button getSupprimer() {
    return supprimer;
  }

  public void setSupprimer(Button supprimer) {
    this.supprimer = supprimer;
  }
}
