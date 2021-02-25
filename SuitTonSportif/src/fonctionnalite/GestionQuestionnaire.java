package fonctionnalite;

import bdd.GestionQuestionnaireBdd;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

  /**
   * Permet de charger les données depuis la base de données, dans l'application.
   * 
   * @return true si la demande c'est bien passé, false sinon.
   */
  public boolean load() {
    return this.gestionQuestionnaireBdd.load(this);
  }


  /**
   * Une méthode pour ajouter un questionnaire depuis une liste de string utilisée pour créée les
   * questions.
   * 
   * @param nomQuestionnaire : L'identifiant unique du nouveau questionnaire.
   * @param questions : la liste de string utilisée pour créée les questions.
   * @return Retourne 1 si la demande c'est bien passé, ou un nombre inférieur en cas d'erreur.
   */
  public int ajouterQuestionnaire(String nomQuestionnaire, List<String> questions) {
    if (nomQuestionnaire == null || nomQuestionnaire.length() < 1 || questions == null) {
      return -2;
    }
    List<Question> listeQuestions = new ArrayList<Question>();
    Iterator<Questionnaire> questionnaireIterator = this.listeQuestionnaire.iterator();
    Questionnaire questionnaireVerif;
    while (questionnaireIterator.hasNext()) {
      questionnaireVerif = questionnaireIterator.next();
      if (questionnaireVerif.getNomQuestionnaire() == nomQuestionnaire) {
        return -1;
      }
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
   * @return Retourne 1 si la demande c'est bien passé, ou un nombre inférieur en cas d'erreur.
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
   * @return Retourne 1 si la demande c'est bien passé, ou un nombre inférieur en cas d'erreur.
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

  /**
   * Cette méthode permet l'ajout d'une question, avec l'intitulé fourni pour identifiant, au
   * questionnaire cible également fourni en paramètre.
   * 
   * @param nomQuestionnaire : Chaine non null et non vide qui représente l'identifiant unique du
   *        questionnaire.
   * @param intitule : Chaine non null et non vide qui représente l'identifiant unique de la
   *        question.
   * @return Retourne 1 si la demande c'est bien passé, ou un nombre inférieur en cas d'erreur.
   */
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

  /**
   * Cette méthode permet la modification d'une question présent de le questionnaire cible, la
   * question est identifié avec l'ancienIntitule, et celle-ci va être modifié avec les autres
   * paramètres fourni.
   * 
   * @param nomQuestionnaire : Chaine non null et non vide qui représente l'identifiant unique du
   *        questionnaire.
   * @param ancienIntitule : Chaine non null et non vide qui représente l'identifiant unique de la
   *        question.
   * @param nouveauIntitule : Chaine non null et non vide qui représente le nouveau identifiant
   *        unique de la question.
   * @return Retourne 1 si la demande c'est bien passé, ou un nombre inférieur en cas d'erreur.
   */
  public int modifierQuestion(String nomQuestionnaire, String ancienIntitule,
      String nouveauIntitule, boolean defaut) {
    if (nomQuestionnaire == null || nomQuestionnaire.length() < 1 || ancienIntitule == null
        || ancienIntitule.length() < 1 || nouveauIntitule == null || nouveauIntitule.length() < 1) {
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

  /**
   * Cette méthode permet la suppression d'une question présent dans le questionnaire cible. La
   * question est identifié avec l'intitulé de la question fourni en paramètre.
   * 
   * @param nomQuestionnaire : Chaine non null et non vide qui représente l'identifiant unique du
   *        questionnaire.
   * @param intitule : Chaine non null et non vide qui représente l'identifiant unique de la
   *        question.
   * @return Retourne 1 si la demande c'est bien passé, ou un nombre inférieur en cas d'erreur.
   */
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
