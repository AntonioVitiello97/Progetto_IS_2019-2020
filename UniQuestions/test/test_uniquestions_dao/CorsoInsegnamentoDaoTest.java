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
import application_logic_layer.gestione_utente.Utente;
import storage_layer.CorsoInsegnamentoDao;
import storage_layer.DriverManagerConnectionPool;
import storage_layer.UtenteDao;

public class CorsoInsegnamentoDaoTest {

  Connection connection = null;

  /**
   * setUp.
   */
  @Before
  public void setUp() throws Exception {
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    connection = DriverManagerConnectionPool.getConnection();
    preparedStatement = connection.prepareStatement("ALTER TABLE corso AUTO_INCREMENT = 0;");
    preparedStatement1 = connection.prepareStatement("ALTER TABLE utente AUTO_INCREMENT = 0;");
    preparedStatement.executeUpdate();
    preparedStatement1.executeUpdate();
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
    preparedStatement = connection.prepareStatement("DELETE FROM corso;");
    preparedStatement1 = connection.prepareStatement("DELETE FROM utente;");
    preparedStatement2 = connection.prepareStatement("ALTER TABLE corso AUTO_INCREMENT = 0;");
    preparedStatement3 = connection.prepareStatement("ALTER TABLE utente AUTO_INCREMENT = 0;");
    preparedStatement.executeUpdate();
    preparedStatement1.executeUpdate();
    preparedStatement2.executeUpdate();
    preparedStatement3.executeUpdate();
    connection.commit();
  }

  @Test
  public final void testRemoveCorso() throws Exception {
    System.out.println("TestRemoveCorso");

    setUp();

    CorsoInsegnamento corso = new CorsoInsegnamento();

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

    corso.setNome("Ingegneria del Software");
    corso.setCorsoDiLaurea("Informatica - Triennale");
    corso.setAnnoAccademico("2019/2020");
    corso.setSemestre("Primo");
    corso.setAnnoDiStudio("Primo");
    corso.setDocenti(docenti);

    CorsoInsegnamentoDao.addCorso(corso);

    boolean result = CorsoInsegnamentoDao.removeCorso(1);

    assertTrue(result);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testIscrizioneCorso() throws Exception {

    System.out.println("TestIscrizioneCorso");

    setUp();

    CorsoInsegnamento corso = new CorsoInsegnamento();

    Utente docente = new Utente();
    Utente studente = new Utente();

    docente.setNome("Andrea");
    docente.setCognome("DeLucia");
    docente.setTipo("docente");
    docente.setUsername("adelucia");
    docente.setMatricola("0512104731");
    docente.setEmail("a.vitiello59@studenti.unisa.it");
    docente.setPassword("0123456789");
    docente.setNazionalita("Italiana");
    int codice1 = 8911;

    studente.setNome("Antonio");
    studente.setCognome("Vitiello");
    studente.setTipo("studente");
    studente.setUsername("a.vitiello59");
    studente.setMatricola("0512104732");
    studente.setEmail("a.vitiello59@studenti.unisa.it");
    studente.setPassword("0123456789");
    studente.setNazionalita("Italiana");
    int codice2 = 8912;

    UtenteDao.registraUtente(docente, codice1);
    Utente utente1 = UtenteDao.getUtenteByUsername("adelucia");
    ArrayList<Utente> docenti = new ArrayList<Utente>();
    docenti.add(utente1);

    UtenteDao.registraUtente(studente, codice1);
    Utente studente1 = UtenteDao.getUtenteByUsername("a.vitiello59");

    corso.setNome("Ingegneria del Software");
    corso.setCorsoDiLaurea("Informatica - Triennale");
    corso.setAnnoAccademico("2019/2020");
    corso.setSemestre("Primo");
    corso.setAnnoDiStudio("Primo");
    corso.setDocenti(docenti);

    CorsoInsegnamentoDao.addCorso(corso);

    boolean result = CorsoInsegnamentoDao.iscrizioneCorso(1, 1);

    assertTrue(result);

    CorsoInsegnamentoDao.removeCorso(1);

    tearDown();
    System.out.println("\n");
    // fail("Not yet implemented");
  }

  @Test
  public final void testDisiscrizioneCorso() throws Exception {

    System.out.println("TestDisiscrizioneCorso");

    setUp();

    CorsoInsegnamento corso = new CorsoInsegnamento();

    Utente docente = new Utente();
    Utente studente = new Utente();

    docente.setNome("Andrea");
    docente.setCognome("DeLucia");
    docente.setTipo("docente");
    docente.setUsername("adelucia");
    docente.setMatricola("0512104731");
    docente.setEmail("a.vitiello59@studenti.unisa.it");
    docente.setPassword("0123456789");
    docente.setNazionalita("Italiana");
    int codice1 = 8911;

    studente.setNome("Antonio");
    studente.setCognome("Vitiello");
    studente.setTipo("studente");
    studente.setUsername("a.vitiello59");
    studente.setMatricola("0512104732");
    studente.setEmail("a.vitiello59@studenti.unisa.it");
    studente.setPassword("0123456789");
    studente.setNazionalita("Italiana");
    int codice2 = 8912;

    UtenteDao.registraUtente(docente, codice1);
    Utente utente1 = UtenteDao.getUtenteByUsername("adelucia");
    ArrayList<Utente> docenti = new ArrayList<Utente>();
    docenti.add(utente1);

    UtenteDao.registraUtente(studente, codice2);
    Utente studente1 = UtenteDao.getUtenteByUsername("a.vitiello59");

    corso.setNome("Ingegneria del Software");
    corso.setCorsoDiLaurea("Informatica - Triennale");
    corso.setAnnoAccademico("2019/2020");
    corso.setSemestre("Primo");
    corso.setAnnoDiStudio("Primo");
    corso.setDocenti(docenti);

    CorsoInsegnamentoDao.addCorso(corso);

    CorsoInsegnamentoDao.iscrizioneCorso(1, studente1.getId());

    // boolean result = CorsoInsegnamentoDao.disiscrizioneCorso(studente.getId(),1);

    ArrayList<CorsoInsegnamento> corsi_iscritti = CorsoInsegnamentoDao.getListaCorsiIscritti(1);

    assertTrue(corsi_iscritti.isEmpty());

    CorsoInsegnamentoDao.removeCorso(1);
    tearDown();

    System.out.println("\n");
  }

  @Test
  public final void testGetListaCorsi() throws Exception {

    System.out.println("TestGetListaCorsi");
    setUp();

    CorsoInsegnamento corso = new CorsoInsegnamento();
    CorsoInsegnamento corso1 = new CorsoInsegnamento();

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

    corso.setNome("Ingegneria del Software");
    corso.setCorsoDiLaurea("Informatica - Triennale");
    corso.setAnnoAccademico("2019/2020");
    corso.setSemestre("Primo");
    corso.setAnnoDiStudio("Primo");
    corso.setDocenti(docenti);

    corso1.setNome("Gestione Progetti Software");
    corso1.setCorsoDiLaurea("Informatica - Magistrale");
    corso1.setAnnoAccademico("2019/2020");
    corso1.setSemestre("Primo");
    corso1.setAnnoDiStudio("Secondo");
    corso1.setDocenti(docenti);

    CorsoInsegnamentoDao.addCorso(corso);
    CorsoInsegnamentoDao.addCorso(corso1);

    ArrayList<CorsoInsegnamento> corsi = new ArrayList<CorsoInsegnamento>();
    corsi = CorsoInsegnamentoDao.getListaCorsi();

    Iterator<CorsoInsegnamento> it = corsi.iterator();

    int flag = 0;
    while (it.hasNext()) {
      CorsoInsegnamento corso_ins = (CorsoInsegnamento) it.next();
      if (flag == 0) {
        assertEquals(corso_ins.getNome(), corso.getNome());
      } else {
        assertEquals(corso_ins.getNome(), corso1.getNome());
      }
      flag = 1;
    }

    CorsoInsegnamentoDao.removeCorso(1);
    CorsoInsegnamentoDao.removeCorso(2);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testAddCorso() throws Exception {
    System.out.println("TestAddCorso");
    setUp();

    CorsoInsegnamento corso = new CorsoInsegnamento();

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

    corso.setNome("Ingegneria del Software");
    corso.setCorsoDiLaurea("Informatica - Triennale");
    corso.setAnnoAccademico("2019/2020");
    corso.setSemestre("Primo");
    corso.setAnnoDiStudio("Primo");
    corso.setDocenti(docenti);

    boolean result = CorsoInsegnamentoDao.addCorso(corso);
    assertTrue(result);
    CorsoInsegnamentoDao.removeCorso(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testMaxNomeAddCorso() throws Exception {
    System.out.println("TestAddCorso");

    boolean result = true;
    setUp();

    CorsoInsegnamento corso = new CorsoInsegnamento();

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

    corso.setNome(
        "Gestione e proggettazione di sistemi software interattivi per la qualità del servizio");
    corso.setCorsoDiLaurea("Informatica - Triennale");
    corso.setAnnoAccademico("2019/2020");
    corso.setSemestre("Primo");
    corso.setAnnoDiStudio("Primo");
    corso.setDocenti(docenti);

    try {
      result = CorsoInsegnamentoDao.addCorso(corso);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      assertTrue(result);
    }
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testGetListaCorsiInsegnanti() throws Exception {
    System.out.println("TestGetListaCorsiInsegnati");
    setUp();

    CorsoInsegnamento corso = new CorsoInsegnamento();
    CorsoInsegnamento corso1 = new CorsoInsegnamento();

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

    corso.setNome("Ingegneria del Software");
    corso.setCorsoDiLaurea("Informatica - Triennale");
    corso.setAnnoAccademico("2019/2020");
    corso.setSemestre("Primo");
    corso.setAnnoDiStudio("Primo");
    corso.setDocenti(docenti);

    corso1.setNome("Gestione Progetti Software");
    corso1.setCorsoDiLaurea("Informatica - Magistrale");
    corso1.setAnnoAccademico("2019/2020");
    corso1.setSemestre("Primo");
    corso1.setAnnoDiStudio("Secondo");
    corso1.setDocenti(docenti);

    CorsoInsegnamentoDao.addCorso(corso);
    CorsoInsegnamentoDao.addCorso(corso1);

    ArrayList<CorsoInsegnamento> corsi = new ArrayList<CorsoInsegnamento>();
    corsi = CorsoInsegnamentoDao.getListaCorsiInsegnanti(1);

    Iterator<CorsoInsegnamento> it = corsi.iterator();

    int flag = 0;
    while (it.hasNext()) {
      CorsoInsegnamento corso_ins = (CorsoInsegnamento) it.next();
      if (flag == 0) {
        assertEquals(corso_ins.getNome(), corso.getNome());
      } else {
        assertEquals(corso_ins.getNome(), corso1.getNome());
      }
      flag = 1;
    }

    CorsoInsegnamentoDao.removeCorso(1);
    CorsoInsegnamentoDao.removeCorso(2);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testGetListaCorsiIscritti() throws Exception {
    System.out.println("TestGetListaCorsiInsegnati");
    setUp();

    CorsoInsegnamento corso = new CorsoInsegnamento();
    CorsoInsegnamento corso1 = new CorsoInsegnamento();

    Utente docente = new Utente();
    Utente studente = new Utente();

    docente.setNome("Andrea");
    docente.setCognome("DeLucia");
    docente.setTipo("docente");
    docente.setUsername("adelucia");
    docente.setMatricola("0512104731");
    docente.setEmail("a.vitiello59@studenti.unisa.it");
    docente.setPassword("0123456789");
    docente.setNazionalita("Italiana");
    int codice1 = 8912;

    studente.setNome("Antonio");
    studente.setCognome("Vitiello");
    studente.setTipo("studente");
    studente.setUsername("a.vitiello59");
    studente.setMatricola("0512104732");
    studente.setEmail("a.vitiello59@studenti.unisa.it");
    studente.setPassword("0123456789");
    studente.setNazionalita("Italiana");
    int codice2 = 8913;

    UtenteDao.registraUtente(docente, codice1);
    UtenteDao.registraUtente(studente, codice2);

    Utente utente1 = UtenteDao.getUtenteByUsername("adelucia");
    ArrayList<Utente> docenti = new ArrayList<Utente>();
    docenti.add(utente1);

    corso.setNome("Ingegneria del Software");
    corso.setCorsoDiLaurea("Informatica - Triennale");
    corso.setAnnoAccademico("2019/2020");
    corso.setSemestre("Primo");
    corso.setAnnoDiStudio("Primo");
    corso.setDocenti(docenti);

    corso1.setNome("Gestione Progetti Software");
    corso1.setCorsoDiLaurea("Informatica - Magistrale");
    corso1.setAnnoAccademico("2019/2020");
    corso1.setSemestre("Primo");
    corso1.setAnnoDiStudio("Secondo");
    corso1.setDocenti(docenti);

    CorsoInsegnamentoDao.addCorso(corso);
    CorsoInsegnamentoDao.addCorso(corso1);

    CorsoInsegnamentoDao.iscrizioneCorso(1, 1);
    CorsoInsegnamentoDao.iscrizioneCorso(2, 1);

    ArrayList<CorsoInsegnamento> corsi = new ArrayList<CorsoInsegnamento>();
    corsi = CorsoInsegnamentoDao.getListaCorsiIscritti(1);

    Iterator<CorsoInsegnamento> it = corsi.iterator();

    int flag = 0;
    while (it.hasNext()) {
      final CorsoInsegnamento corso_ins = (CorsoInsegnamento) it.next();
      if (flag == 0) {
        assertEquals(corso_ins.getNome(), corso.getNome());
      } else {
        assertEquals(corso_ins.getNome(), corso1.getNome());
      }
      flag = 1;
    }

    CorsoInsegnamentoDao.removeCorso(1);
    CorsoInsegnamentoDao.removeCorso(2);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testGetListaDocentiByIdCorso() throws Exception {
    System.out.println("TestGetListaCorsiInsegnati");
    setUp();

    CorsoInsegnamento corso = new CorsoInsegnamento();

    Utente docente = new Utente();
    Utente studente = new Utente();

    docente.setNome("Andrea");
    docente.setCognome("DeLucia");
    docente.setTipo("docente");
    docente.setUsername("adelucia");
    docente.setMatricola("0512104731");
    docente.setEmail("a.vitiello59@studenti.unisa.it");
    docente.setPassword("0123456789");
    docente.setNazionalita("Italiana");
    int codice1 = 8912;

    studente.setNome("Antonio");
    studente.setCognome("Vitiello");
    studente.setTipo("studente");
    studente.setUsername("a.vitiello59");
    studente.setMatricola("0512104732");
    studente.setEmail("a.vitiello59@studenti.unisa.it");
    studente.setPassword("0123456789");
    studente.setNazionalita("Italiana");
    int codice2 = 8913;

    UtenteDao.registraUtente(docente, codice1);
    UtenteDao.registraUtente(studente, codice2);

    Utente utente1 = UtenteDao.getUtenteByUsername("adelucia");
    ArrayList<Utente> docenti = new ArrayList<Utente>();
    docenti.add(utente1);

    corso.setNome("Ingegneria del Software");
    corso.setCorsoDiLaurea("Informatica - Triennale");
    corso.setAnnoAccademico("2019/2020");
    corso.setSemestre("Primo");
    corso.setAnnoDiStudio("Primo");
    corso.setDocenti(docenti);

    CorsoInsegnamentoDao.addCorso(corso);

    ArrayList<Utente> results = CorsoInsegnamentoDao.getListaDocentiByIdCorso(1);

    Iterator<Utente> it = results.iterator();

    while (it.hasNext()) {
      Utente result = (Utente) it.next();
      assertEquals(result.getUsername(), docente.getUsername());
    }

    CorsoInsegnamentoDao.removeCorso(1);
    CorsoInsegnamentoDao.removeCorso(2);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testGetCorsoById() throws Exception {
    System.out.println("testGetCorsoById");
    setUp();

    CorsoInsegnamento corso = new CorsoInsegnamento();

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

    corso.setNome("Ingegneria del Software");
    corso.setCorsoDiLaurea("Informatica - Triennale");
    corso.setAnnoAccademico("2019/2020");
    corso.setSemestre("Primo");
    corso.setAnnoDiStudio("Primo");
    corso.setDocenti(docenti);

    CorsoInsegnamentoDao.addCorso(corso);
    CorsoInsegnamento result = CorsoInsegnamentoDao.getCorsoById(1);
    assertEquals(result.getNome(), corso.getNome());
    CorsoInsegnamentoDao.removeCorso(1);
    CorsoInsegnamentoDao.removeCorso(2);
    tearDown();
    System.out.println("\n");
  }
}
