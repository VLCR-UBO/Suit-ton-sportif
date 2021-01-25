package fonctionnalite;

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
  private List<Sportif> listeDesSportifs;

  /**
   * Constructeur de la classe GestionSportif. Il initialise une liste vide qui contiendra nos
   * sportifs.
   */
  public GestionSportif() {
    this.listeDesSportifs = new ArrayList<Sportif>();
  }

  /**
   * Cette m�thode permet d'ajouter un sportif avec les param�tres qui lui sont fourni. L'ajout aura
   * lieu si et seulement si les param�tres sont correct et que le pseudo n'existe pas d�j�. Une
   * valeur boolean sera retourn�e : true si le sportif � �t� ajout�, false sinon.
   * 
   * @param nom : Chaine de caract�res non null et non vide.
   * @param prenom : Chaine de caract�res non null et non vide.
   * @param pseudo : Chaine de caract�res non null et non vide qui permet l'identification du
   *        sportif.
   * @param motDePasse : Chaine de caract�res non null et non vide.
   * @param dateDeNaissance : Une date non null.
   * @return Retourne true si le sportif � �t� ajout�e, false sinon.
   */
  public boolean ajouterSportif(String nom, String prenom, String pseudo, String motDePasse,
      Calendar dateDeNaissance) {
    if (nom == null || nom.length() < 1 || prenom == null || prenom.length() < 1 || pseudo == null
        || pseudo.length() < 1 || motDePasse == null || motDePasse.length() < 1
        || dateDeNaissance == null) {
      return false; // parametres incorrects
    }
    int taille = this.listeDesSportifs.size();
    for (int i = 0; i < taille; i++) {
      if (this.listeDesSportifs.get(i).getPseudo().equals(pseudo)) {
        return false; // deja present
      }
    }
    this.listeDesSportifs.add(new Sportif(nom, prenom, pseudo, motDePasse, dateDeNaissance));
    return true;
  }

  /**
   * Cette m�thode permet de supprimer le sportif qui lui est passer en param�tre. Une valeur
   * boolean sera retourn�e : true si le sportif � �t� supprim�, false sinon.
   * 
   * @param pseudo : Chaine de caract�res non null et non vide qui permet l'identification du
   *        sportif.
   * @return Retourne true si le sportif � �t� supprim�, false sinon.
   */
  public boolean supprimerSportif(String pseudo) {
    if (pseudo == null || pseudo.length() < 1) {
      return false; // parametres incorrects
    }
    int taille = this.listeDesSportifs.size();
    for (int i = 0; i < taille; i++) {
      if (this.listeDesSportifs.get(i).getPseudo().equals(pseudo)) {
        this.listeDesSportifs.remove(i);
        return true;
      }
    }
    return false; // le sportif n'a pas �t� trouv�
  }

  /**
   * Cette m�thode permet de modifier les informations d'un sportif, celui-ci est identifi� avec le
   * param�tre ancienPseudo. Les informations de ce sportif sont modifi� avec les autres param�tres
   * fourni. Si le nouveau pseudo existe déjà, la modification sera annulé. Une valeur boolean sera
   * retourn�e : true si le sportif � �t� modifi�, false sinon.
   * 
   * @param ancienPseudo : Chaine de caract�res non null et non vide qui permet l'identification du
   *        sportif.
   * @param nom : Chaine de caract�res non null et non vide. Il s'agit du nouveau nom du sportif.
   * @param prenom : Chaine de caract�res non null et non vide. Il s'agit du nouveau prenom du
   *        sportif.
   * @param pseudo : Chaine de caract�res non null et non vide. Il s'agit du nouveau pseudo du
   *        sportif.
   * @param motDePasse : Chaine de caract�res non null et non vide. Il s'agit du nouveau motDePasse
   *        du sportif.
   * @param dateDeNaissance : une date non null. Il s'agit de la nouvelle dateDeNaissance du
   *        sportif.
   * @return Retourne true si le sportif � �t� modifi�, false sinon.
   */
  public boolean modifierSportif(String ancienPseudo, String nom, String prenom, String pseudo,
      String motDePasse, Calendar dateDeNaissance) {
    if (ancienPseudo == null || ancienPseudo.length() < 1 || nom == null || nom.length() < 1
        || prenom == null || prenom.length() < 1 || pseudo == null || pseudo.length() < 1
        || motDePasse == null || motDePasse.length() < 1 || dateDeNaissance == null) {
      return false; // parametres incorrects
    }
    int taille = this.listeDesSportifs.size();
    Sportif leSportif = null;
    for (int i = 0; i < taille; i++) {
      if (this.listeDesSportifs.get(i).getPseudo().equals(pseudo)) {
        return false; // deja present
      }
      if (this.listeDesSportifs.get(i).getPseudo().equals(ancienPseudo)) {
        leSportif = this.listeDesSportifs.get(i);
      }
    }
    if (leSportif == null) {
      return false; // Sportif non trouv�
    }
    leSportif.setNom(nom);
    leSportif.setPrenom(prenom);
    leSportif.setPseudo(pseudo);
    leSportif.setMotDePasse(motDePasse);
    leSportif.setDateDeNaissance(dateDeNaissance);
    return true;
  }

  /**
   * Cette m�thode permet de recup�rer un sportif parmi la liste. Celui-ci est identifi� avec son
   * pseudo.
   * 
   * @param pseudo : Chaine de caract�res non null et non vide. Il s'agit du nouveau pseudo du
   *        sportif.
   * @return Retourne le sportif correspondant au pseudo, ou null s'il n'est pas trouv�.
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
