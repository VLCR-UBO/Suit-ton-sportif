package fonctionnalite;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * La classe Sportif permet la cr�ation d'un sportif et contient toutes les donn�es de celui-ci. Des
 * Getters et des Setters permettent l'acc�s � ces informations.
 * 
 * @author ychan
 *
 */
public class Sportif {
  private String nom;
  private String prenom;
  private String pseudo;
  private String motDePasse;
  private Calendar dateDeNaissance;
  private List<ActiviteSportive> listeDesActivitesSportive;

  /**
   * Constructeur de la classe Sportif. Il cr�e un nouveau sportif avec les param�tres qui lui sont
   * fourni.
   * 
   * @param nom : Chaine de caract�res non null et non vide.
   * @param prenom : Chaine de caract�res non null et non vide.
   * @param pseudo : Chaine de caract�res non null et non vide qui permet l'identification du
   *        sportif.
   * @param motDePasse : Chaine de caract�res non null et non vide.
   * @param dateDeNaissance : une date non null.
   */
  public Sportif(String nom, String prenom, String pseudo, String motDePasse,
      Calendar dateDeNaissance) {
    this.nom = nom;
    this.prenom = prenom;
    this.pseudo = pseudo;
    this.motDePasse = motDePasse;
    this.dateDeNaissance = dateDeNaissance;
    this.listeDesActivitesSportive = new ArrayList<ActiviteSportive>();
    this.listeDesActivitesSportive.add(ActiviteSportive.BADMINTON);
    this.listeDesActivitesSportive.add(ActiviteSportive.TENNIS);
  }

  public List<ActiviteSportive> getListeDesActivitesSportive() {
    return listeDesActivitesSportive;
  }

  public void setListeDesActivitesSportive(List<ActiviteSportive> listeDesActivitesSportive) {
    this.listeDesActivitesSportive = listeDesActivitesSportive;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public String getPseudo() {
    return pseudo;
  }

  public void setPseudo(String pseudo) {
    this.pseudo = pseudo;
  }

  public String getMotDePasse() {
    return motDePasse;
  }

  public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
  }

  public Calendar getDateDeNaissance() {
    return dateDeNaissance;
  }

  public void setDateDeNaissance(Calendar dateDeNaissance) {
    this.dateDeNaissance = dateDeNaissance;
  }
}

// la liste de tout les sportif
// A partir du pseudo recup un sportif
// supprimer un spotif a partir de son pseudo
// modifi� les infos a partir du pseudo
