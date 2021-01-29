package bdd;

import fonctionnalite.GestionSportif;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

public class GestionSportifBdd {
  private Connection connection;
  private Statement sqlStatement;

  public GestionSportifBdd() {
    try {
      String url = "jdbc:mysql://localhost/enregistretonsportif";
      String user = "root";
      String passwd = "motdepasse";

      connection = DriverManager.getConnection(url, user, passwd);
      sqlStatement =
          connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

      System.out.println("Connexion OK");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public boolean supprimerSportif(String pseudo) {
    if (pseudo == null || pseudo.length() < 1) {
      return false; // parametres incorrects
    }
    String query = "DELETE FROM sportif WHERE pseudo =" + pseudo.toString();
    try {
      sqlStatement.executeUpdate(query);
    } catch (SQLException e) {
      return false; // problème sql
    }
    return true;
  }

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
    Date date = dateDeNaissance.getTime();
    String query =
        "INSERT INTO sportif (pseudo, nomSportif, prenomSportif, dateDeNaissanceSportif, "
            + "motDePasseSportif) VALUES (" + pseudo.toString() + "," + nom.toString() + ","
            + prenom.toString() + "," + date + "," + motDePasse.toString() + ")";
    try {
      sqlStatement.executeUpdate(query);
    } catch (SQLException e) {
      return false;
    }
    return true;
  }

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
    Date date = dateDeNaissance.getTime();
    String query = "UPDATE sportif SET pseudo = " + pseudo.toString() + ", nomSportif = "
        + nom.toString() + ", prenomSportif = " + prenom.toString() + ", dateDeNaissanceSportif = "
        + date + ", motDePasseSportif = " + motDePasse.toString() + " WHERE pseudo = "
        + ancienPseudo.toString();
    try {
      sqlStatement.executeUpdate(query);
    } catch (SQLException e) {
      return false;
    }
    return true;
  }

}
