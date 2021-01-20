package fonctionnalite;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Facade {
  private GestionSportif gestionSportif;
  private GestionReponses gestionReponses;
  private GestionQuestionnaire gestionQuestionnaire;

  public Facade() {
    gestionSportif = new GestionSportif();
    gestionReponses = new GestionReponses();
    gestionQuestionnaire = new GestionQuestionnaire();
  }

  /////////////////////////// SPORTIF ///////////////////////////

  public List<Sportif> obtenirListeSportif() {
    if (gestionSportif == null || gestionSportif.getListeDesSportifs().size() == 0) {
      return null;
    }
    return gestionSportif.getListeDesSportifs();
  }

  public Sportif obtenirUnSportif(String pseudo) {
    if (gestionSportif == null) {
      return null;
    }
    return gestionSportif.consulterSportif(pseudo);
  }

  public boolean supprimerUnSportif(String pseudo) {
    if (gestionSportif == null) {
      return false;
    }
    return gestionSportif.supprimerSportif(pseudo);
  }

  public boolean modifierUnSportif(String ancienPseudo, String nom, String prenom, String pseudo,
      String motDePasse, Calendar dateDeNaissance) {
    if (gestionSportif == null) {
      return false;
    }
    return gestionSportif.modifierSportif(ancienPseudo, nom, prenom, pseudo, motDePasse,
        dateDeNaissance);
  }

  public boolean ajouterUnSportif(String nom, String prenom, String pseudo, String motDePasse,
      Calendar dateDeNaissance) {
    if (gestionSportif == null) {
      return false;
    }
    return gestionSportif.ajouterSportif(nom, prenom, pseudo, motDePasse, dateDeNaissance);
  }

  public List<ActiviteSportive> consulterLesActivitesDuSportif(String pseudo) {
    if (gestionSportif == null) {
      return null;
    }
    return gestionSportif.consulterSportif(pseudo).getListeDesActivitesSportive();
  }

  /////////////////////////// REPONSES ///////////////////////////


  public HashMap<String, Integer> obtenirQuestionnaireEtReponses(Integer numeroSemaine,
      String pseudo, String nomQuestionnaire) {
    if (gestionReponses == null || gestionQuestionnaire == null || gestionSportif == null) {
      return null;
    }
    Sportif unSportif = gestionSportif.consulterSportif(pseudo);
    Questionnaire unQuestionnaire = gestionQuestionnaire.consulterListeQuestion(nomQuestionnaire);
    List<Integer> listeReponses =
        gestionReponses.consulterReponses(numeroSemaine, unSportif, unQuestionnaire);
    List<Question> listeQuestions = unQuestionnaire.getListeDeQuestions();
    return null;
  }

}
