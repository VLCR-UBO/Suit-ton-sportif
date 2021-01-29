package fonctionnalite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GestionSportifBDD {
  private Connection connection;
  private Statement sqlStatement;

  public GestionSportifBDD() {
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
    return false;
  }
}
