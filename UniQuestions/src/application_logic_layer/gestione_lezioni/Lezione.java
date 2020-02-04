package application_logic_layer.gestione_lezioni;

/**
 * Oggetto Lezione che identifica una lezione.
 *
 * @author AntonioVitiello
 */
public class Lezione {
  private int id;
  private String data;
  private String descrizione;
  private String valutazione;
  private String nome;

  /** 
   * Costruttore vuoto.
   * */
  public Lezione() {
    id = -1;
    data = null;
    descrizione = null;
    valutazione = null;
    nome = null;
  }

  /**
   * Costruttore con parametri.
   *
   * @param id indica il codice univoco
   * @param data indica la data in cui � stata effettuata la lezione
   * @param descrizione indica la descrizione della lezione
   * @param valutazione indica la valutazione assegnata alla lezione
   * @param nome indica il nome della lezione
   */
  public Lezione(int id, String data, String descrizione, String valutazione, String nome) {
    this.id = id;
    this.data = data;
    this.descrizione = descrizione;
    this.valutazione = valutazione;
    this.nome = nome;
  }

  /**
   * Restituisce il codice univoco della lezione.
   *
   * @return id
   */
  public int getId() {
    return id;
  }

  /**
   * Setta il codice univoco alla lezione.
   *
   * @param id indica il nuovo ID
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Restituisce la data di inserimento della lezione.
   *
   * @return data
   */
  public String getData() {
    return data;
  }

  /**
   * Setta la data di inserimento alla lezione.
   *
   * @param data indica la nuova data
   */
  public void setData(String data) {
    this.data = data;
  }

  /**
   * Restituisce la descrizione della lezione.
   *
   * @return descrizione
   */
  public String getDescrizione() {
    return descrizione;
  }

  /**
   * Setta la descrizione alla lezione.
   *
   * @param descrizione indica la nuova descrizione
   */
  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  /**
   * Restituisce la valutazione della lezione.
   *
   * @return valutazione
   */
  public String getValutazione() {
    return valutazione;
  }

  /**
   * Setta la valutazione alla lezione.
   *
   * @param valutazione indica la nuova valutazione
   */
  public void setValutazione(String valutazione) {
    this.valutazione = valutazione;
  }

  /**
   * Restituisce il nome della lezione.
   *
   * @return nome
   */
  public String getNome() {
    return nome;
  }

  /**
   * Setta il nome alla lezione.
   *
   * @param nome indica il nuovo nome
   */
  public void setNome(String nome) {
    this.nome = nome;
  }

  @Override
  public String toString() {
    return "Lezione [id="
        + id
        + ", data="
        + data
        + ", descrizione="
        + descrizione
        + ", valutazione="
        + valutazione
        + ", nome="
        + nome
        + "]";
  }
}
