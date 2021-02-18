package fonctionnalite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import bdd.GestionQuestionnaireBdd;
import bdd.GestionSportifBdd;

public class GestionQuestionnaire {
  private GestionQuestionnaireBdd gestionQuestionnaireBdd;
  private List<Questionnaire> listeQuestionnaire;

  /**
   * Un constructeur qui genère une classe qui va gérer les questionnaires.
   * 
   */
  public GestionQuestionnaire() {
    this.setListeQuestionnaire(new ArrayList<Questionnaire>());
    gestionQuestionnaireBdd = new GestionQuestionnaireBdd();
  }

  public boolean load() {
    return this.gestionQuestionnaireBdd.load(this);
  }


  /**
   * Une méthode pour ajouter un questionnaire depuis une liste de string utilisée pour créée les
   * questions.
   * 
   * @param nomQuestionnaire : L'identifiant unique du nouveau questionnaire.
   * @param questions : la liste de string utilisée pour créée les questions.
   */
  public int ajouterQuestionnaire(String nomQuestionnaire, List<String> questions) {
    if (nomQuestionnaire == null || nomQuestionnaire.length() < 1 || questions == null) {
      return -2;
    }
    List<Question> listeQuestions = new ArrayList<Question>();
    Iterator<Questionnaire> questionnaireIterator = this.listeQuestionnaire.iterator();
    Questionnaire questionnaireVerif;
    boolean verif = false;
    while (questionnaireIterator.hasNext() && (!verif)) {
      questionnaireVerif = questionnaireIterator.next();
      if (questionnaireVerif.getNomQuestionnaire() == nomQuestionnaire) {
        verif = true;
      }
    }
    if (verif) {
      return -1;
    }
    boolean retBdd = gestionQuestionnaireBdd.ajouterQuestionnaire(nomQuestionnaire, questions);
    if (!retBdd) {
      return 0;
    }
    for (String intitule : questions) {
      Question question = new QuestionBoolenne(intitule, true);
      listeQuestions.add(question);
    }
    Questionnaire questionnaire = new Questionnaire(nomQuestionnaire, listeQuestions);
    if (!this.listeQuestionnaire.contains(questionnaire)) {
      this.listeQuestionnaire.add(questionnaire);
      return 1;
    }
    return -1;
  }

  /**
   * Une méthode pour modifier un questionnaire depuis une liste de string utilisée pour créée le
   * nouveau questionnaire.
   * 
   * @param ancienNomQuestionnaire : L'identifiant unique pour retrouver notre questionnaire
   * @param nouveauNomQuestionnaire : Le nouvelle identifiant unique pour notre questionnaire
   * @param questions : la liste de string utilisée pour le nouveau questionnaire
   * @return Retourne vrai si le questionnaire à été modifié, faux sinon
   */
  public int modifierQuestionnaire(String ancienNomQuestionnaire, String nouveauNomQuestionnaire,
      List<String> questions) {
    if (ancienNomQuestionnaire == null || ancienNomQuestionnaire.length() < 1
        || nouveauNomQuestionnaire == null || nouveauNomQuestionnaire.length() < 1
        || questions == null) {
      return -2;
    }
    List<Question> listeQuestions = new ArrayList<Question>();
    Iterator<Questionnaire> questionnaireIterator = this.listeQuestionnaire.iterator();
    Questionnaire questionnaireVerif;
    boolean verif = false;
    while (questionnaireIterator.hasNext() && (!verif)) {
      questionnaireVerif = questionnaireIterator.next();
      if (questionnaireVerif.getNomQuestionnaire() == ancienNomQuestionnaire) {
        verif = true;
      }
    }
    if (!verif) {
      return -1;
    }
    boolean retBdd = gestionQuestionnaireBdd.modifierQuestionnaire(ancienNomQuestionnaire,
        nouveauNomQuestionnaire);
    if (!retBdd) {
      return 0;
    }
    for (String intitule : questions) {
      Question question = new QuestionBoolenne(intitule, true);
      listeQuestions.add(question);
    }
    verif = false;
    questionnaireIterator = this.listeQuestionnaire.iterator();
    while (questionnaireIterator.hasNext() && (!verif)) {
      questionnaireVerif = questionnaireIterator.next();
      if (questionnaireVerif.getNomQuestionnaire() == ancienNomQuestionnaire) {
        this.listeQuestionnaire.remove(questionnaireVerif);
        questionnaireVerif.setListeDeQuestions(listeQuestions);
        questionnaireVerif.setNomQuestionnaire(nouveauNomQuestionnaire);
        this.listeQuestionnaire.add(questionnaireVerif);
        verif = true;
      }
    }
    return 1;
  }

  /**
   * Une méthode pour supprimer un questionnaire.
   * 
   * @param nomQuestionnaire : L'identifiant unique du questionnaire à supprimer
   * @return Retourne vrai si le questionnaire à été supprimé, faux sinon
   */
  public int supprimerQuestionnaire(String nomQuestionnaire) {
    int ret = -2;
    if (nomQuestionnaire == null || nomQuestionnaire.length() < 1) {
      return ret;
    }
    Iterator<Questionnaire> questionnaireIterator = this.listeQuestionnaire.iterator();
    Questionnaire questionnaireVerif;
    while (questionnaireIterator.hasNext() && (ret == -2)) {
      questionnaireVerif = questionnaireIterator.next();
      if (questionnaireVerif.getNomQuestionnaire() == nomQuestionnaire) {
        boolean retBdd = gestionQuestionnaireBdd.supprimerQuestionnaire(nomQuestionnaire);
        if (!retBdd) {
          return 0;
        }
        this.listeQuestionnaire.remove(questionnaireVerif);
        return 1;
      }
    }
    return -1;
  }

  public int ajouterQuestion(String nomQuestionnaire, String intitule) {
    if (nomQuestionnaire == null || nomQuestionnaire.length() < 1 || intitule == null
        || intitule.length() < 1) {
      return -3;
    }
    Questionnaire unQuestionnaire = consulterListeQuestion(nomQuestionnaire);
    if (unQuestionnaire == null) {
      return -2;
    }
    boolean retBdd = gestionQuestionnaireBdd.ajouterQuestion(nomQuestionnaire, intitule, false);
    if (!retBdd) {
      return -1;
    }
    boolean ret = unQuestionnaire.ajouterQuestionBoolenne(intitule, false);
    if (!ret) {
      return 0;
    }
    return 1;
  }
  
  public int modifierQuestion(String nomQuestionnaire, String ancienIntitule,
      String nouveauIntitule, boolean defaut) {
    if (nomQuestionnaire == null || nomQuestionnaire.length() < 1 || ancienIntitule == null
        || ancienIntitule.length() < 1 || nouveauIntitule == null
        || nouveauIntitule.length() < 1) {
      return -3;
    }
    Questionnaire unQuestionnaire = consulterListeQuestion(nomQuestionnaire);
    if (unQuestionnaire == null) {
      return -2;
    }
    boolean retBdd =
        gestionQuestionnaireBdd.modifierQuestion(ancienIntitule, nouveauIntitule, defaut);
    if (!retBdd) {
      return -1;
    }
    boolean ret = unQuestionnaire.modifierQuestionBoolenne(ancienIntitule, nouveauIntitule, defaut);
    if (!ret) {
      return 0;
    }
    return 1;
  }
  
  public int supprimerQuestion(String nomQuestionnaire, String intitule) {
    if (nomQuestionnaire == null || nomQuestionnaire.length() < 1 || intitule == null
        || intitule.length() < 1) {
      return -3;
    }
    Questionnaire unQuestionnaire = consulterListeQuestion(nomQuestionnaire);
    if (unQuestionnaire == null) {
      return -2;
    }
    boolean retBdd = gestionQuestionnaireBdd.supprimerQuestion(intitule);
    if (!retBdd) {
      return -1;
    }
    boolean ret = unQuestionnaire.supprimerQuestion(intitule);
    if (!ret) {
      return 0;
    }
    return 1;

  }

  /**
   * Une méthode permettant d'obtenir un questionnaire grâce à son nom.
   * 
   * @param nomQuestionnaire : le nom du questionnaire
   * @return Retourne la liste des question retournée
   */
  public Questionnaire consulterListeQuestion(String nomQuestionnaire) {
    Questionnaire questionnaire = null;
    if (nomQuestionnaire != null && nomQuestionnaire.length() > 0) {
      Iterator<Questionnaire> questionnaireIterator = this.listeQuestionnaire.iterator();
      Questionnaire questionnaireVerif;
      boolean verif = false;
      while (questionnaireIterator.hasNext() && (!verif)) {
        questionnaireVerif = questionnaireIterator.next();
        if (questionnaireVerif.getNomQuestionnaire().equals(nomQuestionnaire)) {
          questionnaire = questionnaireVerif;
          verif = true;
        }
      }
    }
    return questionnaire;
  }

  public List<Questionnaire> getListeQuestionnaire() {
    return listeQuestionnaire;
  }

  public void setListeQuestionnaire(List<Questionnaire> listeQuestionnaire) {
    this.listeQuestionnaire = listeQuestionnaire;
  }



}
