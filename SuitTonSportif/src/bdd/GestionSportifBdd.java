package bdd;

import fonctionnalite.GestionQuestionnaire;
import fonctionnalite.GestionSportif;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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

  public boolean load(GestionSportif gestion) {
    try {
      List<String> lesPseudoSportifs = new ArrayList<String>();
      List<String> lesNomSportifs = new ArrayList<String>();
      List<String> lesPrenomSportifs = new ArrayList<String>();
      List<String> lesMotDePasseSportifs = new ArrayList<String>();
      List<Calendar> lesDateDeNaissanceSportifs = new ArrayList<Calendar>();

      ResultSet lesSportifs = this.sqlStatement.executeQuery(("SELECT * FROM sportif"));
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
        gestion.ajouterSportif(lesNomSportifs.get(i), lesPrenomSportifs.get(i),
            lesPseudoSportifs.get(i), lesMotDePasseSportifs.get(i),
            lesDateDeNaissanceSportifs.get(i));
      }
    } catch (Exception e) {
      return false;
    }
    return true;
  }

}
