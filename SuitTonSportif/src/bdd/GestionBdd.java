package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * La classe GestionBDD permet la connexion à la base de données, et l'exécution des requêtes. Elle
 * est utilisé par nos trois gestionnaire du coté de la base de données, pour leurs requêtes.
 * 
 * @author ychan
 *
 */
public class GestionBdd {
  private Connection connection;
  private Statement sqlStatement;

  /**
   * Ce constructeur initialise la connexion avec notre base de données.
   */
  public GestionBdd() {
    try {
      String url = "jdbc:mysql://localhost/enregistretonsportif";
      String user = "root";
      String passwd = "motdepasse";

      connection = DriverManager.getConnection(url, user, passwd);
      sqlStatement =
          connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Cette méthode permet l'exécution de la requête passez en paramètre.
   * 
   * @param requete : Chaine de caractères contenant notre requete à réalisé.
   * @throws SQLException : Renvoie à la fonction appelante les erreurs qui pourrait ce produire.
   */
  public void executerRequete(String requete) throws SQLException {
    sqlStatement.executeUpdate(requete);
  }

  /**
   * Cette méthode permet l'exécution de la requête passez en paramètre, et renvoie le résultat de
   * celle-ci à la fonction appelante.
   * 
   * @param requete : Chaine de caractères contenant notre requete à réalisé.
   * @return Retourne le resultat de la requête.
   * @throws SQLException : Renvoie à la fonction appelante les erreurs qui pourrait ce produire.
   */
  public ResultSet executerRequeteAvecReponse(String requete) throws SQLException {
    return sqlStatement.executeQuery(requete);
  }
}
