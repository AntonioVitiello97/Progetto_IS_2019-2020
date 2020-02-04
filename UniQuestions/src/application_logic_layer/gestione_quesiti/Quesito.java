package application_logic_layer.gestione_quesiti;

import java.util.ArrayList;

import application_logic_layer.gestione_utente.Utente;

/**
 * Oggetto Quesito che identifica un quesito.
 *
 * @author AntonioVitiello
 */
public class Quesito {
  private int id;
  private String risposta;
  private String data;
  private String domanda;
  private ArrayList<Utente> docenti;

  /** Costruttore vuoto. */
  public Quesito() {
    id = -1;
    risposta = null;
    data = null;
    domanda = null;
    docenti = new ArrayList<Utente>();
  }

  /**
   * Costruttore con parametri.
   *
   * @param id indica il codice univoco
   * @param risposta indica la risposta data al quesito
   * @param data indica la data in cui è stato posto il quesito
   * @param domanda indica la domanda data al quesito
   */
  public Quesito(int id, String risposta, String data, String domanda, ArrayList<Utente> docenti) {
    this.id = id;
    this.risposta = risposta;
    this.data = data;
    this.domanda = domanda;
    this.docenti = docenti;
  }

  /**
   * Restituisce il codice univoco del quesito.
   *
   * @return id
   */
  public int getId() {
    return id;
  }

  /**
   * Setta il codice univoco al quesito.
   *
   * @param id indica il nuovo ID
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Restituisce la risposta data al quesito.
   *
   * @return risposta
   */
  public String getRisposta() {
    return risposta;
  }

  /**
   * Setta la risposta al quesito.
   * @param risposta indica la nuova risposta
   */
  public void setRisposta(String risposta) {
    this.risposta = risposta;
  }

  /**
   * Restituisce la data del quesito.
   *
   * @return data
   */
  public String getData() {
    return data;
  }

  /**
   * Setta la data al quesito.
   *
   * @param data indica la nuova data
   */
  public void setData(String data) {
    this.data = data;
  }

  /**
   * Restituisce la domanda del quesito.
   *
   * @return domanda
   */
  public String getDomanda() {
    return domanda;
  }

  /**
   * Setta la domanda al quesito.
   *
   * @param domanda indica la nuova domanda
   */
  public void setDomanda(String domanda) {
    this.domanda = domanda;
  }

  /**
   * Restituisce la lista dei docenti che ricevono i quesiti.
   *
   * @return docenti
   */
  public ArrayList<Utente> getDocenti() {
    return docenti;
  }

  /**
   * Setta la lista di docenti che devono ricevere i quesiti.
   *
   * @param docenti indica la nuova lista di docenti
   */
  public void setDocenti(ArrayList<Utente> docenti) {
    this.docenti = docenti;
  }

  @Override
  public String toString() {
    return "Quesito [id="
        + id
        + ", risposta="
        + risposta
        + ", data="
        + data
        + ", domanda="
        + domanda
        + "]";
  }
}
