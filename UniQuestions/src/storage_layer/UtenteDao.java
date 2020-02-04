package storage_layer;

import application_logic_layer.gestione_utente.Utente;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class UtenteDao {

  /**
   * registraUtente.
   * @author UmbertoLibrera Permette la registrazione di un utente nel database
   * @param utente oggetto utente da registrare
   * @param codice codice di verfica registrazione
   * @return true se l'utente è stato registrato con successo false altrimenti
   * @throws SQLException Eccezione
   */
  public static boolean registraUtente(Utente utente, int codice) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    PreparedStatement preparedStatement2 = null;
    PreparedStatement preparedStatement3 = null;

    int id_utente = 0;

    final String insert_sql =
        "INSERT INTO utente (username, password, tipo, nome, cognome, email, nazionalita,"
            + "matricola) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    final String controllo_username = "SELECT * FROM utente WHERE username = ?";

    final String controllo_matricola = "SELECT * FROM utente WHERE matricola = ?";

    final String insert_sql1 = "INSERT INTO codici_conferma (codice, id_utente) VALUES (?, ?)";

    try {
      connection = DriverManagerConnectionPool.getConnection();
      preparedStatement2 = connection.prepareStatement(controllo_username);
      preparedStatement3 = connection.prepareStatement(controllo_matricola);

      preparedStatement2.setString(1, utente.getUsername());
      preparedStatement3.setString(1, utente.getMatricola());

      System.out.println("controllo_username: " + preparedStatement2.toString());
      System.out.println("controllo_matricola: " + preparedStatement3.toString());
      ResultSet result_set1 = preparedStatement2.executeQuery();
      ResultSet result_set2 = preparedStatement3.executeQuery();

      if ((result_set1.next() == false) && (result_set2.next() == false)) {
        preparedStatement =
            connection.prepareStatement(insert_sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, utente.getUsername());
        preparedStatement.setString(2, utente.getPassword());
        preparedStatement.setString(3, utente.getTipo());
        preparedStatement.setString(4, utente.getNome());
        preparedStatement.setString(5, utente.getCognome());
        preparedStatement.setString(6, utente.getEmail());
        preparedStatement.setString(7, utente.getNazionalita());
        preparedStatement.setString(8, utente.getMatricola());

        System.out.println("registraUtente: " + preparedStatement.toString());

        preparedStatement.executeUpdate();

        ResultSet rs = preparedStatement.getGeneratedKeys();

        if (rs.next()) {
          id_utente = rs.getInt(1);
        }
        rs.close();

        preparedStatement1 = connection.prepareStatement(insert_sql1);
        preparedStatement1.setInt(1, codice);
        preparedStatement1.setInt(2, id_utente);

        System.out.println("confermaCodiceUtente: " + preparedStatement1.toString());

        preparedStatement1.executeUpdate();

        connection.commit();
        return true;
      } else {
        return false;
      }
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
   * aggiornaUtente.
   * @author UmbertoLibrera Permette di aggiornare la tipologia di un utente a secondo del dominio
   *     passato
   * @param id_utente id dell'utente che si vuole aggiornare
   * @param dominio stringa di dominio appartenente all'emai dell'utente
   * @return true se l'utente è stato aggiornato con successo false altrimenti
   */
  public static boolean aggiornaUtente(int id_utente, String dominio) throws SQLException {
    final String dominio_docenti = "unisa.it";
    final String dominio_studenti = "studenti.unisa.it";
    String tipo;

    if (dominio.equals(dominio_docenti)) {
      tipo = "docente";
    } else if (dominio.equals(dominio_studenti)) {
      tipo = "studente";
    } else {
      tipo = "email_non_istituzionale";
    }

    Connection connection = null;
    PreparedStatement preparedStatement = null;

    int result = 0;

    final String update_sql = "UPDATE utente SET tipo= ? WHERE id = ?";

    try {
      connection = DriverManagerConnectionPool.getConnection();
      preparedStatement = connection.prepareStatement(update_sql);
      preparedStatement.setString(1, tipo);
      preparedStatement.setInt(2, id_utente);

      System.out.println("aggiornaUtente: " + preparedStatement.toString());
      result = preparedStatement.executeUpdate();

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
    return (result != 0);
  }

  /**
   * verificaCodice.
   * @author UmbertoLibrera Permette di verificare il codice di conferma di un determinato utente
   * @param id_utente id dell'utente che si vuole aggiornare
   * @return codice di conferma
   * @throws SQLException Eccezione
   */
  public static int verificaCodice(int id_utente) throws SQLException {
    Connection connection = null;
    Utente utente = new Utente();
    PreparedStatement preparedStatement = null;

    final String select_sql = "SELECT codice FROM codici_conferma WHERE id_utente= ?";
    int codice_conferma = 0;

    try {
      connection = DriverManagerConnectionPool.getConnection();
      preparedStatement = connection.prepareStatement(select_sql);

      preparedStatement.setInt(1, id_utente);

      System.out.println("verificaCodice:" + preparedStatement.toString());

      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        codice_conferma = rs.getInt(1);
      }
    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } finally {
        DriverManagerConnectionPool.releaseConnection(connection);
      }
    }

    return codice_conferma;
  }

  /**
   * deleteCodiceUtente.
   * @author AntonioVitiello Permette di eliminare il codice di conferma dal database
   * @param codice codice che si vuole eliminare
   * @return true se il codice è stato eliminato con successo false se il codice non è stato
   *     eliminato con successo
   * @throws SQLException
   */
  public static boolean deleteCodiceUtente(int codice) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    int result = 0;

    final String delete_sql = "DELETE FROM codici_conferma WHERE codice = ?";

    try {
      connection = DriverManagerConnectionPool.getConnection();
      preparedStatement = connection.prepareStatement(delete_sql);
      preparedStatement.setInt(1, codice);

      System.out.println("deleteCodiceUtente: " + preparedStatement.toString());
      result = preparedStatement.executeUpdate();

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
    return (result != 0);
  }

  /**
   * login.
   * @author UmbertoLibrera Permette di controllare le credenziali di accesso di un utente
   * @param utente oggetto utente che vuole accedere al sistema
   * @return restituisce l'utente
   */
  public static Utente login(Utente utente) throws SQLException {

    Connection connection = null;
    PreparedStatement preparedStatement = null;

    Utente user = new Utente();

    final String select_sql = "SELECT * FROM utente WHERE username = ? AND password = ?";

    try {
      connection = DriverManagerConnectionPool.getConnection();
      preparedStatement = connection.prepareStatement(select_sql);

      preparedStatement.setString(1, utente.getUsername());
      preparedStatement.setString(2, utente.getPassword());

      System.out.println("doLogin: " + preparedStatement.toString());

      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setNome(rs.getString("nome"));
        user.setCognome(rs.getString("cognome"));
        user.setEmail(rs.getString("email"));
        user.setMatricola(rs.getString("matricola"));
        user.setNazionalita(rs.getString("nazionalita"));
        user.setPassword(rs.getString("password"));
        user.setTipo(rs.getString("tipo"));
        connection.commit();
      }

    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } finally {
        DriverManagerConnectionPool.releaseConnection(connection);
      }
    }

    return user;
  }

  /**
   * getDocentiByLezioneId.
   * @author UmbertoLibrera Permette di ottenere una lista dei docenti che insegnano in una lezione
   * @param id_lezione id della lezione d'interesse
   * @return restituisce una lista di utente
   */
  public static ArrayList<Utente> getDocentiByLezioneId(int id_lezione) throws SQLException {

    ArrayList<Utente> docenti = new ArrayList<Utente>();
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    final String select_sql =
        "SELECT *  FROM utente WHERE id IN( SELECT id_utente FROM insegna WHERE id_corso"
        + " IN (SELECT id FROM corso WHERE id IN (SELECT id_corso FROM lezione WHERE id = ?)));";

    try {
      connection = DriverManagerConnectionPool.getConnection();
      preparedStatement = connection.prepareStatement(select_sql);

      preparedStatement.setInt(1, id_lezione);

      System.out.println("getDocentiByLezioneId: " + preparedStatement.toString());

      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        Utente user = new Utente();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setNome(rs.getString("nome"));
        user.setCognome(rs.getString("cognome"));
        user.setEmail(rs.getString("email"));
        user.setMatricola(rs.getString("matricola"));
        user.setNazionalita(rs.getString("nazionalita"));
        user.setPassword(rs.getString("password"));
        user.setTipo(rs.getString("tipo"));
        docenti.add(user);
        connection.commit();
      }

    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } finally {
        DriverManagerConnectionPool.releaseConnection(connection);
      }
    }

    return docenti;
  }

  /**
   * controlloResetPassword.
   * @author UmbertoLibrera Permette di controllare se l'username e l'email inserite per il reset hanno
   *     una corrispondenza nel database
   * @param utente oggetto utente che vuole resettare la password
   * @return true se è stata trovata una corrispondenza nel database false se non è stata trovata
   *     una corrispondenza nel database
   * @throws SQLException Eccezione
   */
  public static boolean controlloResetPassword(Utente utente) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    final String select_sql = "SELECT * FROM utente WHERE username = ? AND email = ?";

    try {
      connection = DriverManagerConnectionPool.getConnection();
      preparedStatement = connection.prepareStatement(select_sql);

      preparedStatement.setString(1, utente.getUsername());
      preparedStatement.setString(2, utente.getEmail());

      System.out.println("controlloResetPassword:" + preparedStatement.toString());

      ResultSet rs = preparedStatement.executeQuery();

      if (rs.next() == false) {
        return false;
      } else {
        return true;
      }
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
   * resetPassword.
   * @author AntonioVitiello Permette di resettare una password di un utente
   * @param user oggetto utente che vuole resettare la password
   * @throws SQLException Eccezione
   */
  public static void resetPassword(Utente user) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    final String update_sql = "UPDATE utente SET password= ? WHERE username = ?";

    try {
      connection = DriverManagerConnectionPool.getConnection();
      preparedStatement = connection.prepareStatement(update_sql);
      preparedStatement.setString(1, user.getPassword());
      preparedStatement.setString(2, user.getUsername());

      System.out.println("aggiornaUtente: " + preparedStatement.toString());
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
   * getUtenteByUsername.
   * @author AntonioVitiello Permette di ottenere un utente mediante il suo username
   * @param username username dell'utente che si vuole ottenere
   * @return restituisce un utente
   * @throws SQLException Eccezione
   */
  public static Utente getUtenteByUsername(String username) throws SQLException {
    Connection connection = null;
    Utente utente = new Utente();
    PreparedStatement preparedStatement = null;

    final String select_sql = "SELECT * FROM utente WHERE username= ?";

    try {
      connection = DriverManagerConnectionPool.getConnection();
      preparedStatement = connection.prepareStatement(select_sql);

      preparedStatement.setString(1, username);

      System.out.println("getUtenteByUsername:" + preparedStatement.toString());

      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {

        utente.setId(rs.getInt(1));
        utente.setUsername(rs.getString(2));
        utente.setPassword(rs.getString(3));
        utente.setTipo(rs.getString(4));
        utente.setNome(rs.getString(5));
        utente.setCognome(rs.getString(6));
        utente.setEmail(rs.getString(7));
        utente.setNazionalita(rs.getString(8));
        utente.setMatricola(rs.getString(9));
      }
    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } finally {
        DriverManagerConnectionPool.releaseConnection(connection);
      }
    }

    return utente;
  }

  /**
   * getUtenteById.
   * @author UmbertoLibrera Permette di ottenere un utente mediante il suo id
   * @param id username dell'utente che si vuole ottenere
   * @return restituisce un utente
   * @throws SQLException Eccezione
   */
  public static Utente getUtenteById(int id) throws SQLException {
    Connection connection = null;
    Utente utente = new Utente();
    PreparedStatement preparedStatement = null;

    final String select_sql = "SELECT * FROM utente WHERE id= ?";

    try {
      connection = DriverManagerConnectionPool.getConnection();
      preparedStatement = connection.prepareStatement(select_sql);

      preparedStatement.setInt(1, id);

      System.out.println("getUtenteById:" + preparedStatement.toString());

      ResultSet rs = preparedStatement.executeQuery();

      if (rs.next()) {
        utente.setId(rs.getInt(1));
        utente.setUsername(rs.getString(2));
        utente.setPassword(rs.getString(3));
        utente.setTipo(rs.getString(4));
        utente.setNome(rs.getString(5));
        utente.setCognome(rs.getString(6));
        utente.setEmail(rs.getString(7));
        utente.setNazionalita(rs.getString(8));
        utente.setMatricola(rs.getString(9));

      } else {
        utente = null;
      }
      return utente;

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
   * deleteUtenteById.
   * @author AntonioVitiello Permette di eliminare un utente mediante il suo id
   * @param id id dell'utente che si vuole eliminare
   * @throws SQLException Eccezione
   */
  public static void deleteUtenteById(int id) throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    final String delete_sql = "DELETE FROM utente WHERE id= ?";

    try {
      connection = DriverManagerConnectionPool.getConnection();
      preparedStatement = connection.prepareStatement(delete_sql);

      preparedStatement.setInt(1, id);

      System.out.println("deleteUtenteById:" + preparedStatement.toString());

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
   * getAllDocenti.
   * @author UmbertoLibrera Permette di ottenere una lista di tutti i docenti presenti sul database
   * @return restituisce una lista di utenti
   * @throws SQLException eccezione
   */
  public static Collection<Utente> getAllDocenti() throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatament = null;

    Collection<Utente> docenti = new LinkedList<Utente>();

    final String select_sql = "SELECT * FROM utente WHERE tipo='docente'";

    try {
      connection = DriverManagerConnectionPool.getConnection();
      preparedStatament = connection.prepareStatement(select_sql);

      System.out.println("getAllDocenti:" + preparedStatament.toString());

      ResultSet rs = preparedStatament.executeQuery();

      while (rs.next()) {
        Utente user = new Utente();

        user.setId(rs.getInt("id"));
        user.setNome(rs.getString("nome"));
        user.setCognome(rs.getString("cognome"));
        user.setEmail(rs.getString("email"));
        user.setNazionalita(rs.getString("matricola"));
        user.setUsername(rs.getString("username"));

        docenti.add(user);
      }
    } finally {
      try {
        if (preparedStatament != null) {
          preparedStatament.close();
        }
      } finally {
        DriverManagerConnectionPool.releaseConnection(connection);
      }
    }

    return docenti;
  }
}
