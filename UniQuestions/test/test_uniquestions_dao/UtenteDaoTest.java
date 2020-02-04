package test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application_logic_layer.gestione_corsi_insegnamento.CorsoInsegnamento;
import application_logic_layer.gestione_lezioni.Lezione;
import application_logic_layer.gestione_utente.Utente;
import storage_layer.CorsoInsegnamentoDao;
import storage_layer.DriverManagerConnectionPool;
import storage_layer.LezioneDao;
import storage_layer.UtenteDao;

public class UtenteDaoTest {

  Connection connection = null;

  /**
   * setUp.
   */
  @Before
  public void setUp() throws SQLException {
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    PreparedStatement preparedStatement2 = null;
    PreparedStatement preparedStatement3 = null;

    connection = DriverManagerConnectionPool.getConnection();
    preparedStatement = connection.prepareStatement("ALTER TABLE utente AUTO_INCREMENT = 0;");
    preparedStatement1 = connection.prepareStatement("ALTER TABLE corso AUTO_INCREMENT = 0;");
    preparedStatement2 = connection.prepareStatement("ALTER TABLE lezione AUTO_INCREMENT = 0;");
    preparedStatement3 = connection.prepareStatement("ALTER TABLE quesito AUTO_INCREMENT = 0;");

    preparedStatement.executeUpdate();
    preparedStatement1.executeUpdate();
    preparedStatement2.executeUpdate();
    preparedStatement3.executeUpdate();

    connection.commit();
  }

  /**
   * tearDown.
   */
  @After
  public void tearDown() throws Exception {
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    PreparedStatement preparedStatement2 = null;
    PreparedStatement preparedStatement3 = null;
    connection = DriverManagerConnectionPool.getConnection();
    preparedStatement = connection.prepareStatement("DELETE FROM utente;");
    preparedStatement1 = connection.prepareStatement("DELETE FROM corso;");
    preparedStatement2 = connection.prepareStatement("DELETE FROM lezione;");
    preparedStatement3 = connection.prepareStatement("DELETE FROM quesito;");
    preparedStatement.executeUpdate();
    preparedStatement1.executeUpdate();
    preparedStatement2.executeUpdate();
    preparedStatement3.executeUpdate();
    connection.commit();
  }
  
  @Test
  public final void testRegistraUtente() throws Exception {
    System.out.println("TestRegistraUtente");

    setUp();

    Utente utente = new Utente();

    utente.setNome("Antonio");
    utente.setCognome("Vitiello");
    utente.setTipo("non_verificato");
    utente.setUsername("a.vitiello59");
    utente.setMatricola("0512104731");
    utente.setEmail("a.vitiello59@studenti.unisa.it");
    utente.setPassword("0123456789");
    utente.setNazionalita("Italiana");
    int codice = 8912;
    UtenteDao.registraUtente(utente, codice);

    Utente result = UtenteDao.getUtenteByUsername("a.vitiello59");
    assertEquals(utente.getUsername(), result.getUsername());

    UtenteDao.deleteUtenteById(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testEmptyEmailRegistraUtente() throws Exception {
    System.out.println("TestEmptyEmailRegistraUtente");
    setUp();
    Utente utente = new Utente();
    utente.setNome("Antonio");
    utente.setCognome("Vitiello");
    utente.setTipo("non_verificato");
    utente.setUsername("a.vitiello59");
    utente.setMatricola("0512104731");
    utente.setPassword("0123456789");
    utente.setNazionalita("Italiana");
    int codice = 8912;

    try {
      UtenteDao.registraUtente(utente, codice);
    } catch (Exception e) {
      e.printStackTrace();
    }

    Utente result = UtenteDao.getUtenteById(1);
    assertNull(result);

    UtenteDao.deleteUtenteById(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testEmptyNazionalitaRegistraUtente() throws Exception {
    System.out.println("TestEmptyNazionalitaRegistraUtente");
    setUp();
    Utente utente = new Utente();
    utente.setNome("Antonio");
    utente.setCognome("Vitiello");
    utente.setTipo("non_verificato");
    utente.setUsername("a.vitiello59");
    utente.setMatricola("0512104731");
    utente.setPassword("0123456789");
    int codice = 8912;

    try {
      UtenteDao.registraUtente(utente, codice);
    } catch (Exception e) {
      e.printStackTrace();
    }

    Utente result = UtenteDao.getUtenteById(1);
    assertNull(result);

    UtenteDao.deleteUtenteById(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testAggiornaUtente() throws Exception {
    System.out.println("TestAggiornaUtente");
    setUp();
    Utente utente = new Utente();

    Utente utentedb = new Utente();
    utentedb.setNome("Antonio");
    utentedb.setCognome("Vitiello");
    utentedb.setTipo("non_verificato");
    utentedb.setUsername("a.vitiello59");
    utentedb.setMatricola("0512104731");
    utentedb.setEmail("a.vitiello59@studenti.unisa.it");
    utentedb.setPassword("0123456789");
    utentedb.setNazionalita("Italiana");
    int codice = 8912;
    UtenteDao.registraUtente(utentedb, codice);

    utente = UtenteDao.getUtenteByUsername("a.vitiello59");
    String email = utente.getEmail();
    String dominio = email.substring(email.indexOf("@") + 1);
    boolean result = UtenteDao.aggiornaUtente(utente.getId(), dominio);

    assertTrue(result);
    UtenteDao.deleteUtenteById(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testVerificaCodice() throws Exception {
    System.out.println("TestVerificaCodice");
    setUp();
    Utente utente = new Utente();

    Utente utentedb = new Utente();
    utentedb.setNome("Antonio");
    utentedb.setCognome("Vitiello");
    utentedb.setTipo("non_verificato");
    utentedb.setUsername("a.vitiello59");
    utentedb.setMatricola("0512104731");
    utentedb.setEmail("a.vitiello59@studenti.unisa.it");
    utentedb.setPassword("0123456789");
    utentedb.setNazionalita("Italiana");
    int codice = 8912;

    UtenteDao.registraUtente(utentedb, codice);
    utente = UtenteDao.getUtenteByUsername("a.vitiello59");

    int result = UtenteDao.verificaCodice(utente.getId());
    assertEquals(codice, result);

    UtenteDao.deleteUtenteById(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testFailDeleteCodiceUtente() throws Exception {
    System.out.println("TestDeleteCodiceUtente");
    setUp();

    Utente utentedb = new Utente();
    utentedb.setNome("Antonio");
    utentedb.setCognome("Vitiello");
    utentedb.setTipo("non_verificato");
    utentedb.setUsername("a.vitiello59");
    utentedb.setMatricola("0512104731");
    utentedb.setEmail("a.vitiello59@studenti.unisa.it");
    utentedb.setPassword("0123456789");
    utentedb.setNazionalita("Italiana");
    int codice = 8912;

    UtenteDao.registraUtente(utentedb, codice);

    boolean result = UtenteDao.deleteCodiceUtente(8112);
    assertFalse(result);
    UtenteDao.deleteUtenteById(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testLogin() throws Exception {
    System.out.println("TestLogin");
    setUp();
    Utente utente = new Utente();

    Utente utentedb = new Utente();
    utentedb.setNome("Antonio");
    utentedb.setCognome("Vitiello");
    utentedb.setTipo("non_verificato");
    utentedb.setUsername("a.vitiello59");
    utentedb.setMatricola("0512104731");
    utentedb.setEmail("a.vitiello59@studenti.unisa.it");
    utentedb.setPassword("0123456789");
    utentedb.setNazionalita("Italiana");
    int codice = 8912;
    UtenteDao.registraUtente(utentedb, codice);

    utente.setUsername("a.vitiello59");
    utente.setPassword("0123456789");
    
    String exceptResultNome = "Antonio";
    Utente result = UtenteDao.login(utente);

   assertEquals(result.getNome(), exceptResultNome);

    UtenteDao.deleteUtenteById(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testInvalidPasswordLogin() throws Exception {
    System.out.println("TestInvalidPasswordLogin");
    setUp();
    Utente utente = new Utente();

    Utente utentedb = new Utente();
    utentedb.setNome("Antonio");
    utentedb.setCognome("Vitiello");
    utentedb.setTipo("non_verificato");
    utentedb.setUsername("a.vitiello59");
    utentedb.setMatricola("0512104731");
    utentedb.setEmail("a.vitiello59@studenti.unisa.it");
    utentedb.setPassword("0123456789");
    utentedb.setNazionalita("Italiana");
    int codice = 8912;
    UtenteDao.registraUtente(utentedb, codice);

    utente.setUsername("a.vitiello59");
    utente.setPassword("antonio1");
    ;

    Utente result = UtenteDao.login(utente);
    assertNull(result.getUsername());

    UtenteDao.deleteUtenteById(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testInvalidUsernameLogin() throws Exception {
    System.out.println("TestInvalidUsernameLogin");
    setUp();
    Utente utente = new Utente();

    Utente utentedb = new Utente();
    utentedb.setNome("Antonio");
    utentedb.setCognome("Vitiello");
    utentedb.setTipo("non_verificato");
    utentedb.setUsername("a.vitiello59");
    utentedb.setMatricola("0512104731");
    utentedb.setEmail("a.vitiello59@studenti.unisa.it");
    utentedb.setPassword("0123456789");
    utentedb.setNazionalita("Italiana");
    int codice = 8912;
    UtenteDao.registraUtente(utentedb, codice);

    utente.setUsername("antonio");
    utente.setPassword("0123456789");
    

    Utente result = UtenteDao.login(utente);
    assertNull(result.getUsername());

    UtenteDao.deleteUtenteById(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testgetDocentiByLezioneId() throws Exception {
    System.out.println("testgetDocentiByLezioneId");
    setUp();

    Utente utente = new Utente();
    utente.setNome("Andrea");
    utente.setCognome("DeLucia");
    utente.setTipo("docente");
    utente.setUsername("adelucia");
    utente.setMatricola("0512104491");
    utente.setEmail("a.vitiello59@studenti.unisa.it");
    utente.setPassword("0123456789");
    utente.setNazionalita("Italiana");
    int codice = 8912;
    UtenteDao.registraUtente(utente, codice);

    Utente utente1 = UtenteDao.getUtenteByUsername("adelucia");
    ArrayList<Utente> docenti = new ArrayList<Utente>();
    docenti.add(utente1);

    CorsoInsegnamento corso = new CorsoInsegnamento();

    corso.setNome("Ingegneria del Software");
    corso.setCorsoDiLaurea("Informatica - Triennale");
    corso.setAnnoAccademico("2019/2020");
    corso.setSemestre("Primo");
    corso.setAnnoDiStudio("Primo");
    corso.setDocenti(docenti);

    CorsoInsegnamentoDao.addCorso(corso);

    Lezione lezione = new Lezione();

    lezione.setNome("Scenari e casi d'uso");
    lezione.setData("21/09/2019");
    lezione.setDescrizione(
        "Scenario visionary, scenario as is, training, casi d’uso, diagrammi dei casi d’uso");

    LezioneDao.addLezione(lezione, 1);

    ArrayList<Utente> utenti = UtenteDao.getDocentiByLezioneId(1);

    Utente user = null;
    Iterator<Utente> i = utenti.iterator();

    while (i.hasNext()) {
      user = (Utente) i.next();
      assertEquals(user.getUsername(), "adelucia");
    }

    UtenteDao.deleteUtenteById(1);
    CorsoInsegnamentoDao.removeCorso(1);
    LezioneDao.removeLezione(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testControlloResetPassword() throws SQLException {}

  @Test
  public final void testFailControlloResetPassword() throws Exception {
    System.out.println("TestControlloResetPassword");
    setUp();

    Utente utentedb = new Utente();
    utentedb.setNome("Antonio");
    utentedb.setCognome("Vitiello");
    utentedb.setTipo("non_verificato");
    utentedb.setUsername("a.vitiello59");
    utentedb.setMatricola("0512104731");
    utentedb.setEmail("a.vitiello59@studenti.unisa.it");
    utentedb.setPassword("0123456789");
    utentedb.setNazionalita("Italiana");
    int codice = 8912;
    UtenteDao.registraUtente(utentedb, codice);

    utentedb.setEmail("a.vitiello@studenti.unisa.it");
    boolean result = UtenteDao.controlloResetPassword(utentedb);

    assertFalse(result);

    UtenteDao.deleteUtenteById(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testResetPassword() throws Exception {
    System.out.println("TestResetPassword");
    setUp();

    Utente utentedb = new Utente();
    utentedb.setNome("Antonio");
    utentedb.setCognome("Vitiello");
    utentedb.setTipo("non_verificato");
    utentedb.setUsername("a.vitiello59");
    utentedb.setMatricola("0512104731");
    utentedb.setEmail("a.vitiello59@studenti.unisa.it");
    utentedb.setPassword("0123456789");
    utentedb.setNazionalita("Italiana");
    int codice = 8912;
    UtenteDao.registraUtente(utentedb, codice);

    final String nuova_password = "antonioantonio";

    utentedb.setPassword(nuova_password);

    UtenteDao.resetPassword(utentedb);

    Utente result = UtenteDao.getUtenteById(1);
    
    System.out.println("Nuova password = "+nuova_password+
    		"\nPassword result "+result.getPassword() );

    assertEquals(nuova_password, result.getPassword());

    UtenteDao.deleteUtenteById(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testGetUtenteByUsername() throws Exception {
    System.out.println("TestResetPassword");
    setUp();

    Utente utentedb = new Utente();
    utentedb.setNome("Antonio");
    utentedb.setCognome("Vitiello");
    utentedb.setTipo("non_verificato");
    utentedb.setUsername("a.vitiello59");
    utentedb.setMatricola("0512104731");
    utentedb.setEmail("a.vitiello59@studenti.unisa.it");
    utentedb.setPassword("0123456789");
    utentedb.setNazionalita("Italiana");
    int codice = 8912;
    UtenteDao.registraUtente(utentedb, codice);

    Utente result = UtenteDao.getUtenteByUsername("a.vitiello59");

    assertEquals(utentedb.getUsername(), result.getUsername());

    UtenteDao.deleteUtenteById(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testFailGetUtenteByUsername() throws Exception {
    System.out.println("TestResetPassword");
    setUp();

    Utente utentedb = new Utente();
    utentedb.setNome("Antonio");
    utentedb.setCognome("Vitiello");
    utentedb.setTipo("non_verificato");
    utentedb.setUsername("a.vitiello59");
    utentedb.setMatricola("0512104491");
    utentedb.setEmail("a.vitiello59@studenti.unisa.it");
    utentedb.setPassword("0123456789");
    utentedb.setNazionalita("Italiana");
    int codice = 8912;
    UtenteDao.registraUtente(utentedb, codice);

    Utente result = UtenteDao.getUtenteByUsername("prova");

    assertNull(result.getUsername());

    UtenteDao.deleteUtenteById(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testBranchRegistraUtente() throws Exception {

    setUp();
    Utente utentedb = new Utente();
    utentedb.setNome("Antonio");
    utentedb.setCognome("Vitiello");
    utentedb.setTipo("non_verificato");
    utentedb.setUsername("a.vitiello59");
    utentedb.setMatricola("0512104491");
    utentedb.setEmail("a.vitiello59@studenti.unisa.it");
    utentedb.setPassword("0123456789");
    utentedb.setNazionalita("Italiana");
    int codice = 8912;

    assertTrue(UtenteDao.registraUtente(utentedb, codice));
    assertFalse(UtenteDao.registraUtente(utentedb, codice));
    UtenteDao.deleteUtenteById(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testBranchControlloResetPassword() throws Exception {
    setUp();
    Utente utentedb = new Utente();
    utentedb.setNome("Antonio");
    utentedb.setCognome("Vitiello");
    utentedb.setTipo("non_verificato");
    utentedb.setUsername("a.vitiello59");
    utentedb.setMatricola("0512104491");
    utentedb.setEmail("a.vitiello59@studenti.unisa.it");
    utentedb.setPassword("0123456789");
    utentedb.setNazionalita("Italiana");
    int codice = 8912;

    UtenteDao.registraUtente(utentedb, codice);

    assertTrue(UtenteDao.controlloResetPassword(utentedb));
    utentedb.setUsername("test");
    assertFalse(UtenteDao.controlloResetPassword(utentedb));
    UtenteDao.deleteUtenteById(1);
    tearDown();
    System.out.println("\n");
  }
}
