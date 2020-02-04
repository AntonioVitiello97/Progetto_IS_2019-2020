package application_logic_layer.gestione_corsi_insegnamento;

import application_logic_layer.gestione_utente.Utente;

import java.util.ArrayList;

/**
 * Oggetto Corso che identifica un corso d'insegnamento.
 *
 * @author AntonioVitiello
 */
public class CorsoInsegnamento {
  private int id;
  private String annoAccademico;
  private String corsoDiLaurea;
  private String annoDiStudio;
  private String semestre;
  private String nome;
  private ArrayList<Utente> docenti;

  /**
   * CorsoInsegnamento.
   * 
   */
  public CorsoInsegnamento() {
    id = -1;
    annoAccademico = null;
    corsoDiLaurea = null;
    annoDiStudio = null;
    semestre = null;
    nome = null;
    docenti = new ArrayList<Utente>();
  }

  /**
   * Costruttore con parametri.
   * @param id indica il codice univoco
   * @param annoAccademico indica in quale anno accademico � situato il corso
   * @param corsoDiLaurea indica in quale corso di laurea � situato il corso
   * @param annoDiStudio indica in quale anno di studio � situato il corso
   * @param semestre indica in quale semestre � situato il corso
   * @param docente indica il nome del docente che gestisce il corso
   * @param nome indica il nome del corso
   * @param docenti indica la lista dei docenti che gestiscono il corso
   */
  public CorsoInsegnamento(
      int id,
      String annoAccademico,
      String corsoDiLaurea,
      String annoDiStudio,
      String semestre,
      Utente docente,
      String nome,
      ArrayList<Utente> docenti) {
    this.id = id;
    this.annoAccademico = annoAccademico;
    this.corsoDiLaurea = corsoDiLaurea;
    this.annoDiStudio = annoDiStudio;
    this.nome = nome;
    this.docenti = docenti;
  }

  /**
   * Restituisce il codice univoco del corso.
   *
   * @return id
   */
  public int getId() {
    return id;
  }

  /**
   * Setta il codice univoco al corso.
   *
   * @param id indica il nuovo ID
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Restituisce l'anno accademico del corso.
   *
   * @return annoAccademico
   */
  public String getAnnoAccademico() {
    return annoAccademico;
  }

  /**
   * Setta l'anno accademico al corso.
   *
   * @param annoAccademico indica il nuovo anno Accademico
   */
  public void setAnnoAccademico(String annoAccademico) {
    this.annoAccademico = annoAccademico;
  }

  /**
   * Restituisce il corso di laurea del corso.
   *
   * @return corsoDiLaurea
   */
  public String getCorsoDiLaurea() {
    return corsoDiLaurea;
  }

  /**
   * Setta il corso di laurea al corso.
   *
   * @param corsoDiLaurea indica il nuovo corso di Laurea
   */
  public void setCorsoDiLaurea(String corsoDiLaurea) {
    this.corsoDiLaurea = corsoDiLaurea;
  }

  /**
   * Restituisce l'anno di studio del corso.
   *
   * @return annoDiStudio
   */
  public String getAnnoDiStudio() {
    return annoDiStudio;
  }

  /**
   * Setta l'anno di studio al corso.
   *
   * @param annoDiStudio indica il nuovo anno di Studio
   */
  public void setAnnoDiStudio(String annoDiStudio) {
    this.annoDiStudio = annoDiStudio;
  }

  /**
   * Restituisce il semestre del corso.
   *
   * @return semestre
   */
  public String getSemestre() {
    return semestre;
  }

  /**
   * Setta il semestre al corso.
   *
   * @param semestre indica il nuovo semestre
   */
  public void setSemestre(String semestre) {
    this.semestre = semestre;
  }

  /**
   * Restituisce il nome del corso.
   *
   * @return nome
   */
  public String getNome() {
    return nome;
  }

  /**
   * Setta il nome al corso.
   *
   * @param nome indica il nuovo nome
   */
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   * Restituisce la lista dei docenti che gestiscono il corso.
   *
   * @return docenti
   */
  public ArrayList<Utente> getDocenti() {
    return docenti;
  }

  /**
   * Setta la lista di docenti al corso.
   *
   * @param docenti indica la nuova lista di docenti
   */
  public void setDocenti(ArrayList<Utente> docenti) {
    this.docenti = docenti;
  }

  @Override
  public String toString() {
    return "CorsoInsegnamento [id="
        + id
        + ", annoAccademico="
        + annoAccademico
        + ", corsoDiLaurea="
        + corsoDiLaurea
        + ", annoDiStudio="
        + annoDiStudio
        + ", semestre="
        + semestre
        + ", nome="
        + nome
        + ", docenti="
        + docenti
        + "]";
  }
}
