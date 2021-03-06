package bdd;

import fonctionnalite.GestionSportif;
import fonctionnalite.Sportif;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * La classe GestionSportifBdd permet l'ajout, la modification, et la suppression des sportifs dans
 * la base de données. Elle contient également une méthode load pour charger les données dans
 * l'application.
 * 
 * @author ychan
 *
 */
public class GestionSportifBdd {
  private GestionBdd gestionBdd;

  /**
   * Constructeur de la classe GestionSportifBdd. Il initialise la connexion avec notre base de
   * données.
   */
  public GestionSportifBdd() {
    gestionBdd = new GestionBdd();
  }

  /**
   * Cette méthode permet de supprimer le sportif qui lui est passer en paramètre dans la base de
   * données. Une valeur boolean sera retournée : true si le sportif est supprimé, false sinon.
   * 
   * @param pseudo : Chaine de caractères non null et non vide qui permet l'identification du
   *        sportif.
   * @return Retourne true si le sportif est supprimé, false sinon.
   */
  public boolean supprimerSportif(String pseudo) {
    String query = "DELETE FROM SPORTIF WHERE pseudo = '" + pseudo.toString() + "'";
    try {
      gestionBdd.executerRequete(query);
    } catch (SQLException e) {
      e.printStackTrace();
      return false; // problème sql
    }
    return true;
  }

  /**
   * Cette méthode permet d'ajouter un sportif en base de données, avec les paramètres qui lui sont
   * fourni. L'ajout aura lieu si et seulement si les paramètres sont correct et que le pseudo
   * n'existe pas déjà. Une valeur boolean sera retournée : true si le sportif est ajouté, false
   * sinon.
   * 
   * @param gestionSportif : Permet d'accéder aux éléments déjà présents dans l'application.
   * @param nom : Chaine de caractères non null et non vide.
   * @param prenom : Chaine de caractères non null et non vide.
   * @param pseudo : Chaine de caractères non null et non vide qui permet l'identification du
   *        sportif.
   * @param motDePasse : Chaine de caractères non null et non vide.
   * @param dateDeNaissance : Une date non null.
   * @return Retourne true si le sportif est ajoutée, false sinon.
   */
  public boolean ajouterSportif(GestionSportif gestionSportif, String nom, String prenom,
      String pseudo, String motDePasse, Calendar dateDeNaissance) {
    if (nom == null || nom.length() < 1 || prenom == null || prenom.length() < 1 || pseudo == null
        || pseudo.length() < 1 || motDePasse == null || motDePasse.length() < 1
        || dateDeNaissance == null) {
      return false; // parametres incorrects
    }
    int taille = gestionSportif.getListeDesSportifs().size();
    for (int i = 0; i < taille; i++) {
      if (gestionSportif.getListeDesSportifs().get(i).getPseudo().equals(pseudo)) {
        return false; // deja present
      }
    }
    java.util.Date utilDate = dateDeNaissance.getTime();
    java.sql.Date date = new java.sql.Date(utilDate.getTime());
    String query =
        "INSERT INTO SPORTIF (pseudo, nomSportif, prenomSportif, dateDeNaissanceSportif, "
            + "motDePasseSportif) VALUES ('" + pseudo.toString() + "','" + nom.toString() + "','"
            + prenom.toString() + "',DATE('" + date + "'),'" + motDePasse.toString() + "')";
    try {
      gestionBdd.executerRequete(query);
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Cette méthode permet de modifier les informations d'un sportif dans la base de données,
   * celui-ci est identifié avec le paramètre ancienPseudo. Les informations de ce sportif sont
   * modifié avec les autres paramètres fourni. Si le nouveau pseudo existe déjà, la modification
   * sera annulé. Une valeur boolean sera retournée : true si le sportif est modifié, false sinon.
   * 
   * @param gestionSportif : Permet d'accéder aux éléments déjà présents dans l'application.
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
   * @return Retourne true si le sportif est modifié, false sinon.
   */
  public boolean modifierSportif(GestionSportif gestionSportif, String ancienPseudo, String nom,
      String prenom, String pseudo, String motDePasse, Calendar dateDeNaissance) {
    if (ancienPseudo == null || ancienPseudo.length() < 1 || nom == null || nom.length() < 1
        || prenom == null || prenom.length() < 1 || pseudo == null || pseudo.length() < 1
        || motDePasse == null || motDePasse.length() < 1 || dateDeNaissance == null) {
      return false; // parametres incorrects
    }
    int taille = gestionSportif.getListeDesSportifs().size();
    for (int i = 0; i < taille; i++) {
      if (gestionSportif.getListeDesSportifs().get(i).getPseudo().equals(pseudo)
          && !gestionSportif.getListeDesSportifs().get(i).getPseudo().equals(ancienPseudo)) {
        return false; // deja present
      }
    }
    java.util.Date utilDate = dateDeNaissance.getTime();
    java.sql.Date date = new java.sql.Date(utilDate.getTime());
    String query = "UPDATE SPORTIF SET pseudo = '" + pseudo.toString() + "', nomSportif = '"
        + nom.toString() + "', prenomSportif = '" + prenom.toString()
        + "', dateDeNaissanceSportif = DATE('" + date + "'), motDePasseSportif = '"
        + motDePasse.toString() + "' WHERE pseudo = '" + ancienPseudo.toString() + "'";
    try {
      gestionBdd.executerRequete(query);
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Cette méthode permet de mettre à jour les données de l'application, avec celle de la base de
   * données.
   * 
   * @param gestionSportif : Permet de mettre à jour les éléments de l'application.
   * @return true si tout c'est bien passé, false sinon.
   */
  public boolean load(GestionSportif gestionSportif) {
    try {
      List<String> lesPseudoSportifs = new ArrayList<String>();
      List<String> lesNomSportifs = new ArrayList<String>();
      List<String> lesPrenomSportifs = new ArrayList<String>();
      List<String> lesMotDePasseSportifs = new ArrayList<String>();
      List<Calendar> lesDateDeNaissanceSportifs = new ArrayList<Calendar>();

      String query = "SELECT * FROM SPORTIF";
      ResultSet lesSportifs = gestionBdd.executerRequeteAvecReponse(query);
      while (lesSportifs.next()) {
        lesPseudoSportifs.add(lesSportifs.getString("pseudo"));
        lesNomSportifs.add(lesSportifs.getString("nomSportif"));
        lesPrenomSportifs.add(lesSportifs.getString("prenomSportif"));
        lesMotDePasseSportifs.add(lesSportifs.getString("motDePasseSportif"));

        Date uneDate = lesSportifs.getDate("dateDeNaissanceSportif");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(uneDate);
        lesDateDeNaissanceSportifs.add(calendar);
      }

      int taille = lesPseudoSportifs.size();
      for (int i = 0; i < taille; i++) {
        gestionSportif.getListeDesSportifs()
            .add(new Sportif(lesNomSportifs.get(i), lesPrenomSportifs.get(i),
                lesPseudoSportifs.get(i), lesMotDePasseSportifs.get(i),
                lesDateDeNaissanceSportifs.get(i)));
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

}
