package storage_layer;

import application_logic_layer.gestione_corsi_insegnamento.CorsoInsegnamento;
import application_logic_layer.gestione_lezioni.Lezione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LezioneDao {

  /**
   * getMediaValutazioniById.
   * @author AntonioVitiello Permette l'inserimento di una lezione nel database
   * @param lezione oggetto lezione da inserire
   * @param id_corso id del corso in cui inserire la lezione
   * @throws SQLException Eccezione
   */
  public static void addLezione(Lezione lezione, int id_corso) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    final String insert_sql =
        "INSERT INTO lezione (nome, data_lezione, descrizione, valutazione_media,"
        + " id_corso) VALUES (?, ?, ?, ?, ?)";
    try {
      connection = DriverManagerConnectionPool.getConnection();
      preparedStatement = connection.prepareStatement(insert_sql);
      preparedStatement.setString(1, lezione.getNome());
      preparedStatement.setString(2, lezione.getData());
      preparedStatement.setString(3, lezione.getDescrizione());
      preparedStatement.setInt(4, 0);
      preparedStatement.setInt(5, id_corso);

      System.out.println("addLezione: " + preparedStatement.toString());
      preparedStatement.executeUpdate();
      connection.commit();
    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } finally {
        DriverManagerConnectionPool.releaseConnection(connection);
      }
    }
  }

  /**
   * getMediaValutazioniById.
   * @author UmbertoLibrera Permette l'inserimento della valutazione ad una lezione
   * @param id_utente id del'utente che inserisce la valutazione
   * @param id_lezione id della lezione in cui inserire la valutazione
   * @param valutazione valutazione data alla lezione
   * @throws SQLException Eccezione
   */
  public static void addValutazioneLezione(int id_utente, int id_lezione, int valutazione)
      throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    final String insert_sql = "INSERT INTO valuta (id_utente,"
        + " id_lezione, valutazione) VALUES (?, ?, ?)";
    try {
      connection = DriverManagerConnectionPool.getConnection();
      preparedStatement = connection.prepareStatement(insert_sql);
      preparedStatement.setInt(1, id_utente);
      preparedStatement.setInt(2, id_lezione);
      preparedStatement.setInt(3, valutazione);

      System.out.println("addValutazioneLezione: " + preparedStatement.toString());
      preparedStatement.executeUpdate();
      connection.commit();
    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } finally {
        DriverManagerConnectionPool.releaseConnection(connection);
      }
    }
  }

  /**
   * getMediaValutazioniById.
   * @author UmbertoLibrera Permette di restituire la media delle valutazioni di una lezione
   * @param id_lezione id della lezione d'interesse
   * @return media delle valutazioni di una lezione
   * @throws SQLException Eccezione
   */
  public static double getMediaValutazioniById(int id_lezione) throws SQLException {
    double media = 0;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    final String select_sql =
        "SELECT AVG(valutazione) AS MediaValutazione FROM valuta WHERE id_lezione = ?";
    try {
      connection = DriverManagerConnectionPool.getConnection();
      preparedStatement = connection.prepareStatement(select_sql);
      preparedStatement.setInt(1, id_lezione);

      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        media = rs.getDouble("MediaValutazione");
        connection.commit();
      }

      System.out.println("getMediaValutazioniById: " + preparedStatement.toString());

      connection.commit();
    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } finally {
        DriverManagerConnectionPool.releaseConnection(connection);
      }
    }
    return media;
  }

  /**
   * getLezioneById.
   * @author AntonioVitiello Permette di ottenere una lezione desiderata dal database
   * @param id_lezione id della lezione che si vuole cercare
   * @return restituisce una lezione
   * @throws SQLException Eccezione
   */
  public static Lezione getLezioneById(int id_lezione) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Lezione lezione = new Lezione();

    final String select_sql = "SELECT * FROM lezione WHERE id = ?";
    try {

      connection = DriverManagerConnectionPool.getConnection();

      preparedStatement = connection.prepareStatement(select_sql);

      preparedStatement.setInt(1, id_lezione);

      ResultSet rs = preparedStatement.executeQuery();
      System.out.println("RS DB" + rs);
      while (rs.next()) {
        lezione.setId(rs.getInt("id"));
        lezione.setNome(rs.getString("nome"));
        lezione.setData(rs.getString("data_lezione"));
        lezione.setDescrizione(rs.getString("descrizione"));
        lezione.setValutazione(rs.getString("valutazione_media"));
        connection.commit();
      }
      System.out.println("getLezioneById:" + preparedStatement.toString());
      connection.commit();
    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } finally {
        DriverManagerConnectionPool.releaseConnection(connection);
      }
    }
    return lezione;
  }

  /**
   * removeLezione.
   * @author AntonioVitiello Permette la rimozione della lezione dal database
   * @param id_lezione id della lezione da rimuovere
   * @throws SQLException eccezione
   */
  public static void removeLezione(int id_lezione) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    final String delete_sql = "DELETE FROM lezione WHERE id = ?";
    try {
      connection = DriverManagerConnectionPool.getConnection();
      preparedStatement = connection.prepareStatement(delete_sql);

      preparedStatement.setInt(1, id_lezione);

      preparedStatement.executeUpdate();

      System.out.println("removeLezione: " + preparedStatement.toString());
      connection.commit();
    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } finally {
        DriverManagerConnectionPool.releaseConnection(connection);
      }
    }
  }

  /**
   * updateValutazioneLezione.
   * @author UmbertoLibrera Permette l'aggiornamento della valutazione di una lezione nel database
   * @param id_lezione id della lezione da aggiornare
   * @param valutazione nuova valutazione assegnata alla lezione
   * @throws SQLException Eccezione
   */
  public static void updateValutazioneLezione(int id_lezione, double valutazione)
      throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    final String update_sql = "UPDATE lezione SET valutazione_media = ?  WHERE id = ?";
    try {
      connection = DriverManagerConnectionPool.getConnection();
      preparedStatement = connection.prepareStatement(update_sql);

      preparedStatement.setDouble(1, valutazione);
      preparedStatement.setInt(2, id_lezione);

      preparedStatement.executeUpdate();

      System.out.println("updateValutazioneLezione: " + preparedStatement.toString());
      connection.commit();
    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } finally {
        DriverManagerConnectionPool.releaseConnection(connection);
      }
    }
  }

  /**
   * getListaLezioni.
   * @author AntonioVitiello Permette di ottenere una lista intera delle lezioni che appartengono ad
   *     un corso
   * @param id_corso id del corso in cui si vogliono cercare le lezioni
   * @return restituisce una lista di lezioni
   * @throws SQLException Eccezione
   */
  public static ArrayList<Lezione> getListaLezioni(int id_corso) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    ArrayList<Lezione> lezioni = new ArrayList<Lezione>();
    final String select_sql = "SELECT * FROM lezione WHERE id_corso = ?";
    try {
      connection = DriverManagerConnectionPool.getConnection();
      preparedStatement = connection.prepareStatement(select_sql);

      preparedStatement.setInt(1, id_corso);

      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        Lezione lezione = new Lezione();
        lezione.setId(rs.getInt("id"));
        lezione.setNome(rs.getString("nome"));
        lezione.setData(rs.getString("data_lezione"));
        lezione.setDescrizione(rs.getString("descrizione"));
        lezione.setValutazione(rs.getString("valutazione_media"));
        lezioni.add(lezione);
      }
      System.out.println("getListaLezioni:" + preparedStatement.toString());
      connection.commit();
    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } finally {
        DriverManagerConnectionPool.releaseConnection(connection);
      }
    }
    return lezioni;
  }
}
