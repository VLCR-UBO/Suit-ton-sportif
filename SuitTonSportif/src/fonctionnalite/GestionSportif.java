package fonctionnalite;

import bdd.GestionSportifBdd;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * La classe GestionSportif contient la liste des sportifs. Elle permet l'ajout, la modification, la
 * suppression et la consultation des sportifs.
 * 
 * @author ychan
 *
 */
public class GestionSportif {
  private GestionSportifBdd gestionSportifBdd;
  private List<Sportif> listeDesSportifs;

  /**
   * Constructeur de la classe GestionSportif. Il initialise une liste vide qui contiendra nos
   * sportifs.
   */
  public GestionSportif() {
    this.listeDesSportifs = new ArrayList<Sportif>();
    gestionSportifBdd = new GestionSportifBdd();
  }
  
  /**
   * Permet de charger les données depuis la base de données, dans l'application.
   * 
   * @return true si la demande c'est bien passé, false sinon.
   */
  public boolean load() {
    return this.gestionSportifBdd.load(this);
  }

  /**
   * Cette méthode permet d'ajouter un sportif avec les paramètres qui lui sont fourni. L'ajout aura
   * lieu si et seulement si les paramètres sont correct et que le pseudo n'existe pas déjà. Une
   * valeur boolean sera retournée : true si le sportif est ajouté, false sinon.
   * 
   * @param nom : Chaine de caractères non null et non vide.
   * @param prenom : Chaine de caractères non null et non vide.
   * @param pseudo : Chaine de caractères non null et non vide qui permet l'identification du
   *        sportif.
   * @param motDePasse : Chaine de caractères non null et non vide.
   * @param dateDeNaissance : Une date non null.
   * @return Retourne 1 si la demande c'est bien passé, ou un nombre inférieur en cas d'erreur.
   */
  public int ajouterSportif(String nom, String prenom, String pseudo, String motDePasse,
      Calendar dateDeNaissance) {
    if (nom == null || nom.length() < 1 || prenom == null || prenom.length() < 1 || pseudo == null
        || pseudo.length() < 1 || motDePasse == null || motDePasse.length() < 1
        || dateDeNaissance == null) {
      return -2; // parametres incorrects
    }
    int taille = this.listeDesSportifs.size();
    for (int i = 0; i < taille; i++) {
      if (this.listeDesSportifs.get(i).getPseudo().equals(pseudo)) {
        return -1; // deja present
      }
    }
    boolean retBdd = gestionSportifBdd.ajouterSportif(this, nom, prenom, pseudo,
        motDePasse, dateDeNaissance);
    if (!retBdd) {
      return 0;
    }
    this.listeDesSportifs.add(new Sportif(nom, prenom, pseudo, motDePasse, dateDeNaissance));
    return 1;
  }

  /**
   * Cette méthode permet de supprimer le sportif qui lui est passer en paramètre. Une valeur
   * boolean sera retournée : true si le sportif est supprimé, false sinon.
   * 
   * @param pseudo : Chaine de caractères non null et non vide qui permet l'identification du
   *        sportif.
   * @return Retourne 1 si la demande c'est bien passé, ou un nombre inférieur en cas d'erreur.
   */
  public int supprimerSportif(String pseudo) {
    if (pseudo == null || pseudo.length() < 1) {
      return -2; // parametres incorrects
    }
    boolean retBdd = gestionSportifBdd.supprimerSportif(pseudo);
    if (!retBdd) {
      return -1;
    }
    int taille = this.listeDesSportifs.size();
    for (int i = 0; i < taille; i++) {
      if (this.listeDesSportifs.get(i).getPseudo().equals(pseudo)) {
        this.listeDesSportifs.remove(i);
        return 1;
      }
    }
    return 0; // le sportif n'a pas �t� trouv�
  }

  /**
   * Cette méthode permet de modifier les informations d'un sportif, celui-ci est identifié avec le
   * paramètre ancienPseudo. Les informations de ce sportif sont modifié avec les autres paramètres
   * fourni. Si le nouveau pseudo existe déjà, la modification sera annulé. Une valeur boolean sera
   * retournée : true si le sportif est modifié, false sinon.
   * 
   * @param ancienPseudo : Chaine de caractères non null et non vide qui permet l'identification du
   *        sportif.
   * @param nom : Chaine de caractères non null et non vide. Il s'agit du nouveau nom du sportif.
   * @param prenom : Chaine de caractères non null et non vide. Il s'agit du nouveau prenom du
   *        sportif.
   * @param pseudo : Chaine de caractères non null et non vide. Il s'agit du nouveau pseudo du
   *        sportif.
   * @param motDePasse : Chaine de caractères non null et non vide. Il s'agit du nouveau motDePasse
   *        du sportif.
   * @param dateDeNaissance : une date non null. Il s'agit de la nouvelle dateDeNaissance du
   *        sportif.
   * @return Retourne 1 si la demande c'est bien passé, ou un nombre inférieur en cas d'erreur.
   */
  public int modifierSportif(String ancienPseudo, String nom, String prenom, String pseudo,
      String motDePasse, Calendar dateDeNaissance) {
    if (ancienPseudo == null || ancienPseudo.length() < 1 || nom == null || nom.length() < 1
        || prenom == null || prenom.length() < 1 || pseudo == null || pseudo.length() < 1
        || motDePasse == null || motDePasse.length() < 1 || dateDeNaissance == null) {
      return -3; // parametres incorrects
    }
    int taille = this.listeDesSportifs.size();
    Sportif leSportif = null;
    for (int i = 0; i < taille; i++) {
      if (this.listeDesSportifs.get(i).getPseudo().equals(pseudo)
          && !this.listeDesSportifs.get(i).getPseudo().equals(ancienPseudo)) {
        return -2; // deja present
      }
      if (this.listeDesSportifs.get(i).getPseudo().equals(ancienPseudo)) {
        leSportif = this.listeDesSportifs.get(i);
      }
    }
    if (leSportif == null) {
      return -1; // Sportif non trouv�
    }
    boolean retBdd = gestionSportifBdd.modifierSportif(this, ancienPseudo, nom, prenom,
        pseudo, motDePasse, dateDeNaissance);
    if (!retBdd) {
      return 0;
    }
    leSportif.setNom(nom);
    leSportif.setPrenom(prenom);
    leSportif.setPseudo(pseudo);
    leSportif.setMotDePasse(motDePasse);
    leSportif.setDateDeNaissance(dateDeNaissance);
    return 1;
  }

  /**
   * Cette méthode permet de recupérer un sportif parmi la liste. Celui-ci est identifié avec son
   * pseudo.
   * 
   * @param pseudo : Chaine de caractères non null et non vide. Il s'agit du nouveau pseudo du
   *        sportif.
   * @return Retourne le sportif correspondant au pseudo, ou null s'il n'est pas trouvé.
   */
  public Sportif consulterSportif(String pseudo) {
    if (pseudo == null || pseudo.length() < 1) {
      return null; // parametres incorrects
    }
    int taille = this.listeDesSportifs.size();
    for (int i = 0; i < taille; i++) {
      if (this.listeDesSportifs.get(i).getPseudo().equals(pseudo)) {
        return this.listeDesSportifs.get(i);
      }
    }
    return null; // sportif absent
  }

  public List<Sportif> getListeDesSportifs() {
    return listeDesSportifs;
  }
  
}
