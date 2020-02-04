package test;

import static org.junit.Assert.*;

import application_logic_layer.gestione_corsi_insegnamento.CorsoInsegnamento;
import application_logic_layer.gestione_lezioni.Lezione;
import application_logic_layer.gestione_utente.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import storage_layer.CorsoInsegnamentoDao;
import storage_layer.DriverManagerConnectionPool;
import storage_layer.LezioneDao;
import storage_layer.UtenteDao;

public class LezioneDaoTest {

  Connection connection = null;

  /**
   * setUp.
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    PreparedStatement preparedStatement2 = null;
    connection = DriverManagerConnectionPool.getConnection();
    System.out.println("**********setUp***********");
    preparedStatement = connection.prepareStatement("ALTER TABLE utente AUTO_INCREMENT = 0;");
    preparedStatement1 = connection.prepareStatement("ALTER TABLE corso AUTO_INCREMENT = 0;");
    preparedStatement2 = connection.prepareStatement("ALTER TABLE lezione AUTO_INCREMENT = 0;");
    preparedStatement.executeUpdate();
    preparedStatement1.executeUpdate();
    preparedStatement2.executeUpdate();
    connection.commit();
  }

  /**
   * tearDown.
   */
  @After
  public void tearDown() throws Exception {
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    connection = DriverManagerConnectionPool.getConnection();
    preparedStatement = connection.prepareStatement("DELETE FROM utente;");
    preparedStatement = connection.prepareStatement("DELETE FROM corso;");
    preparedStatement1 = connection.prepareStatement("DELETE FROM lezione;");

    preparedStatement.executeUpdate();
    preparedStatement1.executeUpdate();
    connection.commit();
  }

  @Test
  public final void testAddLezione() throws Exception {

    System.out.println("TestAddLezione");
    setUp();

    Utente utente = new Utente();

    utente.setNome("Andrea");
    utente.setCognome("DeLucia");
    utente.setTipo("docente");
    utente.setUsername("adelucia");
    utente.setMatricola("0512104731");
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
        "Scenario visionary, as is, training, casi d’uso, diagrammi dei casi d’uso");

    LezioneDao.addLezione(lezione, 1);

    ArrayList<Lezione> lezioni = LezioneDao.getListaLezioni(1);

    Lezione lezione1 = null;
    Iterator<Lezione> i = lezioni.iterator();

    while (i.hasNext()) {
      lezione1 = (Lezione) i.next();
    }

    assertEquals(lezione1.getNome(), lezione.getNome());
    CorsoInsegnamentoDao.removeCorso(1);
    LezioneDao.removeLezione(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void addValutazioneLezione() throws Exception {
    System.out.println("addValutazioneLezione");
    setUp();

    Utente utente = new Utente();

    utente.setNome("Andrea");
    utente.setCognome("DeLucia");
    utente.setTipo("docente");
    utente.setUsername("adelucia");
    utente.setMatricola("0512104731");
    utente.setEmail("a.vitiello59@studenti.unisa.it");
    utente.setPassword("0123456789");
    utente.setNazionalita("Italiana");
    int codice = 8912;

    UtenteDao.registraUtente(utente, codice);
    Utente utente1 = UtenteDao.getUtenteByUsername("adelucia");
    ArrayList<Utente> docenti = new ArrayList<Utente>();
    docenti.add(utente1);

    Utente studente = new Utente();

    studente.setNome("Antonio");
    studente.setCognome("Vitiello");
    studente.setTipo("studente");
    studente.setUsername("a.vitiello59");
    studente.setMatricola("0512104732");
    studente.setEmail("a.vitiello59@studenti.unisa.it");
    studente.setPassword("0123456789");
    studente.setNazionalita("Italiana");
    int codice1 = 8913;

    UtenteDao.registraUtente(studente, codice1);

    CorsoInsegnamento corso = new CorsoInsegnamento();

    corso.setNome("Ingegneria del Software");
    corso.setCorsoDiLaurea("Informatica - Triennale");
    corso.setAnnoAccademico("2019/2020");
    corso.setSemestre("Primo");
    corso.setAnnoDiStudio("Primo");
    corso.setDocenti(docenti);

    CorsoInsegnamentoDao.addCorso(corso);

    CorsoInsegnamentoDao.iscrizioneCorso(1, 2);

    Lezione lezione = new Lezione();

    lezione.setNome("Scenari e casi d'uso");
    lezione.setData("21/09/2019");
    lezione.setDescrizione(
        "Scenario visionary, as is, training, casi d'uso, diagrammi dei casi d'uso");

    LezioneDao.addLezione(lezione, 1);

    LezioneDao.addValutazioneLezione(2, 1, 3);

    int result = (int) LezioneDao.getMediaValutazioniById(1);

    assertEquals(result, 3);
    CorsoInsegnamentoDao.removeCorso(1);
    LezioneDao.removeLezione(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testRemoveLezione() throws Exception {

    System.out.println("TestRemoveLezione");
    setUp();

    Utente utente = new Utente();

    utente.setNome("Andrea");
    utente.setCognome("DeLucia");
    utente.setTipo("docente");
    utente.setUsername("adelucia");
    utente.setMatricola("0512104731");
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
        "Scenario visionary, as is, training, casi d’uso, diagrammi dei casi d’uso");

    LezioneDao.addLezione(lezione, 1);
    LezioneDao.removeLezione(1);

    ArrayList<Lezione> lezioni = LezioneDao.getListaLezioni(1);

    Lezione lezione1 = null;
    Iterator<Lezione> i = lezioni.iterator();

    while (i.hasNext()) {
      lezione1 = (Lezione) i.next();
    }

    assertNull(lezione1);
    CorsoInsegnamentoDao.removeCorso(1);
    LezioneDao.removeLezione(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testGetListaLezioni() throws Exception {

    System.out.println("TestGetListaLezioni");
    setUp();

    CorsoInsegnamento corso = new CorsoInsegnamento();

    corso.setNome("Ingegneria del Software");
    corso.setCorsoDiLaurea("Informatica - Triennale");
    corso.setAnnoAccademico("2019/2020");
    corso.setSemestre("Primo");
    corso.setAnnoDiStudio("Primo");

    CorsoInsegnamentoDao.addCorso(corso);

    Lezione lezione = new Lezione();
    Lezione lezione1 = new Lezione();

    lezione.setNome("Scenari e casi d'uso");
    lezione.setData("21/09/2019");
    lezione.setDescrizione(
        "Scenario visionary, as is, training, casi d’uso, diagrammi dei casi d’uso");

    lezione1.setNome("Requisiti");
    lezione1.setData("21/09/2019");
    lezione1.setDescrizione("Requisiti funzionali e non funzionali");

    LezioneDao.addLezione(lezione, 1);
    LezioneDao.addLezione(lezione1, 1);

    ArrayList<Lezione> lezioni = new ArrayList<Lezione>();
    lezioni = LezioneDao.getListaLezioni(1);

    Iterator<Lezione> it = lezioni.iterator();

    int flag = 0;
    while (it.hasNext()) {
      Lezione lezione_db = (Lezione) it.next();
      if (flag == 0) {
        assertEquals(lezione_db.getNome(), lezione.getNome());
      } else {
        assertEquals(lezione_db.getNome(), lezione1.getNome());
      }
      flag = 1;
    }

    CorsoInsegnamentoDao.removeCorso(1);

    LezioneDao.removeLezione(1);
    LezioneDao.removeLezione(2);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testGetLezioneById() throws Exception {
    System.out.println("TestGetListaLezioni");
    setUp();
    
    Utente utente = new Utente();

    utente.setNome("Andrea");
    utente.setCognome("DeLucia");
    utente.setTipo("docente");
    utente.setUsername("adelucia");
    utente.setMatricola("0512104731");
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
    Lezione lezione1 = new Lezione();

    lezione.setNome("Scenari e casi d'uso");
    lezione.setData("21/09/2019");
    lezione.setDescrizione(
        "Scenario visionary, as is, training, casi d’uso, diagrammi dei casi d’uso");

    lezione1.setNome("Requisiti");
    lezione1.setData("21/09/2019");
    lezione1.setDescrizione("Requisiti funzionali e non funzionali");

    LezioneDao.addLezione(lezione, 1);
    LezioneDao.addLezione(lezione1, 1);

    Lezione result = LezioneDao.getLezioneById(1);

    assertEquals(lezione.getNome(), result.getNome());

    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testUpdateValutazioneLezione() throws Exception {
    System.out.println("testUpdateValutazioneLezione");
    setUp();

    Utente utente = new Utente();

    utente.setNome("Andrea");
    utente.setCognome("DeLucia");
    utente.setTipo("docente");
    utente.setUsername("adelucia");
    utente.setMatricola("0512104731");
    utente.setEmail("a.vitiello59@studenti.unisa.it");
    utente.setPassword("0123456789");
    utente.setNazionalita("Italiana");
    int codice = 8912;

    UtenteDao.registraUtente(utente, codice);
    Utente utente1 = UtenteDao.getUtenteByUsername("adelucia");
    ArrayList<Utente> docenti = new ArrayList<Utente>();
    docenti.add(utente1);

    Utente studente = new Utente();

    studente.setNome("Antonio");
    studente.setCognome("Vitiello");
    studente.setTipo("studente");
    studente.setUsername("a.vitiello59");
    studente.setMatricola("0512104732");
    studente.setEmail("a.vitiello59@studenti.unisa.it");
    studente.setPassword("0123456789");
    studente.setNazionalita("Italiana");
    int codice1 = 8913;

    UtenteDao.registraUtente(studente, codice1);

    CorsoInsegnamento corso = new CorsoInsegnamento();

    corso.setNome("Ingegneria del Software");
    corso.setCorsoDiLaurea("Informatica - Triennale");
    corso.setAnnoAccademico("2019/2020");
    corso.setSemestre("Primo");
    corso.setAnnoDiStudio("Primo");
    corso.setDocenti(docenti);

    CorsoInsegnamentoDao.addCorso(corso);

    CorsoInsegnamentoDao.iscrizioneCorso(1, 2);

    Lezione lezione = new Lezione();

    lezione.setNome("Scenari e casi d'uso");
    lezione.setData("21/09/2019");
    lezione.setDescrizione(
        "Scenario visionary, as is, training, casi d’uso, diagrammi dei casi d’uso");

    LezioneDao.addLezione(lezione, 1);
    LezioneDao.addValutazioneLezione(2, 1, 2);
    LezioneDao.updateValutazioneLezione(1, 2);

    int result = (int) LezioneDao.getMediaValutazioniById(1);
    assertEquals(result, 2);
    CorsoInsegnamentoDao.removeCorso(1);
    LezioneDao.removeLezione(1);
    tearDown();
    System.out.println("\n");
  }
}
