package test;

import static org.junit.Assert.*;

import application_logic_layer.gestione_corsi_insegnamento.CorsoInsegnamento;
import application_logic_layer.gestione_lezioni.Lezione;
import application_logic_layer.gestione_quesiti.Quesito;
import application_logic_layer.gestione_utente.Utente;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import storage_layer.CorsoInsegnamentoDao;
import storage_layer.DriverManagerConnectionPool;
import storage_layer.LezioneDao;
import storage_layer.QuesitoDao;
import storage_layer.UtenteDao;

public class QuesitoDaoTest {

  Connection connection = null;

  /**
   * setUp.
   */
  @Before
  public void setUp() throws Exception {
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
  public final void testAddDomanda() throws Exception {
    System.out.println("TestAddDomanda");
    setUp();

    Utente docente = new Utente();

    docente.setNome("Andrea");
    docente.setCognome("DeLucia");
    docente.setTipo("docente");
    docente.setUsername("adelucia");
    docente.setMatricola("0512104731");
    docente.setEmail("a.vitiello59@studenti.unisa.it");
    docente.setPassword("0123456789");
    docente.setNazionalita("Italiana");
    int codice1 = 8912;

    UtenteDao.registraUtente(docente, codice1);
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
    int codice2 = 8913;

    UtenteDao.registraUtente(studente, codice2);

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

    Quesito quesito = new Quesito();

    quesito.setDomanda("Qual è la differenza tra scenario visionary e training?");
    quesito.setData("21/12/2019");
    quesito.setRisposta("vuoto");
    quesito.setDocenti(docenti);

    QuesitoDao.addDomanda(quesito, 1, 2);

    ArrayList<Quesito> domande = QuesitoDao.getDomandeByIdUtente(1);

    Quesito domanda = null;

    Iterator<Quesito> i = domande.iterator();

    while (i.hasNext()) {

      domanda = (Quesito) i.next();
    }
    assertEquals(domanda.getDomanda(), quesito.getDomanda());
    CorsoInsegnamentoDao.removeCorso(1);
    LezioneDao.removeLezione(1);
    QuesitoDao.removeQuesito(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testAddRisposta() throws Exception {
    System.out.println("TestAddRisposta");
    setUp();

    Utente docente = new Utente();

    docente.setNome("Andrea");
    docente.setCognome("DeLucia");
    docente.setTipo("docente");
    docente.setUsername("adelucia");
    docente.setMatricola("0512104731");
    docente.setEmail("a.vitiello59@studenti.unisa.it");
    docente.setPassword("0123456789");
    docente.setNazionalita("Italiana");
    int codice1 = 8912;

    UtenteDao.registraUtente(docente, codice1);
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
    int codice2 = 8913;

    UtenteDao.registraUtente(studente, codice2);

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

    Quesito quesito = new Quesito();

    quesito.setDomanda(
        "Qual’è la differenza tra prodotto software generico e prodotto software specifico?");
    quesito.setData("21/12/2019");
    quesito.setRisposta("vuoto");
    quesito.setDocenti(docenti);

    QuesitoDao.addDomanda(quesito, 1, 2);
    ArrayList<Quesito> domande = QuesitoDao.getDomandeByIdUtente(2);

    quesito.setRisposta(
        "SW generici sono sistemi prodotti da una organizzazione e venduti a un mercato di massa, SW specifici sono sistemi commissionati da uno specifico utente e sviluppati specificatamente");

    quesito.setId(1);
    QuesitoDao.addRisposta(quesito);

    ArrayList<Quesito> risposte = QuesitoDao.getRisposteByIdUtente(2);

    Quesito risposta = null;
    Iterator<Quesito> iterator = risposte.iterator();

    while (iterator.hasNext()) {
      risposta = (Quesito) iterator.next();
    }

    assertEquals(risposta.getRisposta(), quesito.getRisposta());
    CorsoInsegnamentoDao.removeCorso(1);
    LezioneDao.removeLezione(1);
    QuesitoDao.removeQuesito(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testRemoveQuesito() throws Exception {
    System.out.println("TestRemoveQuesito");
    setUp();

    Utente docente = new Utente();

    docente.setNome("Andrea");
    docente.setCognome("DeLucia");
    docente.setTipo("docente");
    docente.setUsername("adelucia");
    docente.setMatricola("0512104731");
    docente.setEmail("a.vitiello59@studenti.unisa.it");
    docente.setPassword("0123456789");
    docente.setNazionalita("Italiana");
    int codice1 = 8912;

    UtenteDao.registraUtente(docente, codice1);
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
    int codice2 = 8913;

    UtenteDao.registraUtente(studente, codice2);

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

    Quesito quesito = new Quesito();

    quesito.setDomanda("Qual è la differenza tra scenario visionary e training?");
    quesito.setData("21/12/2019");
    quesito.setRisposta("vuoto");
    quesito.setDocenti(docenti);

    QuesitoDao.addDomanda(quesito, 1, 2);

    QuesitoDao.removeQuesito(1);

    ArrayList<Quesito> domande = QuesitoDao.getDomandeByIdUtente(1);
    Quesito domanda = null;
    Iterator<Quesito> i = domande.iterator();

    while (i.hasNext()) {
      domanda = (Quesito) i.next();
      assertNull(domanda.getDomanda());
    }

    CorsoInsegnamentoDao.removeCorso(1);
    LezioneDao.removeLezione(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testGetAllRisposte() throws Exception {
    System.out.println("TestGetAllRisposte");
    setUp();

    Utente docente = new Utente();

    docente.setNome("Andrea");
    docente.setCognome("DeLucia");
    docente.setTipo("docente");
    docente.setUsername("adelucia");
    docente.setMatricola("0512104731");
    docente.setEmail("a.vitiello59@studenti.unisa.it");
    docente.setPassword("0123456789");
    docente.setNazionalita("Italiana");
    int codice1 = 8912;

    UtenteDao.registraUtente(docente, codice1);
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
    int codice2 = 8913;

    UtenteDao.registraUtente(studente, codice2);

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

    Quesito quesito = new Quesito();

    quesito.setDomanda(
        "Qual’è la differenza tra prodotto software generico e prodotto software specifico?");
    quesito.setData("21/12/2019");
    quesito.setRisposta("vuoto");
    quesito.setDocenti(docenti);

    QuesitoDao.addDomanda(quesito, 1, 2);
    ArrayList<Quesito> domande = QuesitoDao.getDomandeByIdUtente(2);

    quesito.setRisposta(
        "SW generici sono sistemi prodotti da una organizzazione e venduti a un mercato di massa, SW specifici sono sistemi commissionati da uno specifico utente e sviluppati specificatamente");

    quesito.setId(1);
    QuesitoDao.addRisposta(quesito);

    ArrayList<Quesito> risposte = QuesitoDao.getAllRisposte();

    Quesito risposta = null;
    Iterator<Quesito> iterator = risposte.iterator();

    while (iterator.hasNext()) {
      risposta = (Quesito) iterator.next();
    }

    assertEquals(risposta.getRisposta(), quesito.getRisposta());
    CorsoInsegnamentoDao.removeCorso(1);
    LezioneDao.removeLezione(1);
    QuesitoDao.removeQuesito(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testGetCountDomandeByIdLezione() throws Exception {
    System.out.println("testGetCountDomandeByIdLezione");
    setUp();

    Utente docente = new Utente();

    docente.setNome("Andrea");
    docente.setCognome("DeLucia");
    docente.setTipo("docente");
    docente.setUsername("adelucia");
    docente.setMatricola("0512104731");
    docente.setEmail("a.vitiello59@studenti.unisa.it");
    docente.setPassword("0123456789");
    docente.setNazionalita("Italiana");
    int codice1 = 8912;

    UtenteDao.registraUtente(docente, codice1);
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
    int codice2 = 8913;

    UtenteDao.registraUtente(studente, codice2);

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

    Quesito quesito = new Quesito();

    quesito.setDomanda(
        "Qual’è la differenza tra prodotto software generico e prodotto software specifico?");
    quesito.setData("21/12/2019");
    quesito.setRisposta("vuoto");
    quesito.setDocenti(docenti);

    QuesitoDao.addDomanda(quesito, 1, 2);

    int count = QuesitoDao.getCountDomandeByIdLezione(1);

    assertEquals(count, 1);
    CorsoInsegnamentoDao.removeCorso(1);
    LezioneDao.removeLezione(1);
    QuesitoDao.removeQuesito(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testGetCountDomandeByIdCorso() throws Exception {
    System.out.println("testGetCountDomandeByIdCorso");
    setUp();

    Utente docente = new Utente();

    docente.setNome("Andrea");
    docente.setCognome("DeLucia");
    docente.setTipo("docente");
    docente.setUsername("adelucia");
    docente.setMatricola("0512104731");
    docente.setEmail("a.vitiello59@studenti.unisa.it");
    docente.setPassword("0123456789");
    docente.setNazionalita("Italiana");
    int codice1 = 8912;

    UtenteDao.registraUtente(docente, codice1);
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
    int codice2 = 8913;

    UtenteDao.registraUtente(studente, codice2);

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

    Quesito quesito = new Quesito();

    quesito.setDomanda(
        "Qual’è la differenza tra prodotto software generico e prodotto software specifico?");
    quesito.setData("21/12/2019");
    quesito.setRisposta("vuoto");
    quesito.setDocenti(docenti);

    QuesitoDao.addDomanda(quesito, 1, 2);

    int count = QuesitoDao.getCountDomandeByIdCorso(1);
    assertEquals(count, 1);
    CorsoInsegnamentoDao.removeCorso(1);
    LezioneDao.removeLezione(1);
    QuesitoDao.removeQuesito(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testGetRisposteByLezione() throws Exception {
    System.out.println("TestGetRisposteByLezione");
    setUp();

    Utente docente = new Utente();

    docente.setNome("Andrea");
    docente.setCognome("DeLucia");
    docente.setTipo("docente");
    docente.setUsername("adelucia");
    docente.setMatricola("0512104731");
    docente.setEmail("a.vitiello59@studenti.unisa.it");
    docente.setPassword("0123456789");
    docente.setNazionalita("Italiana");
    int codice1 = 8912;

    UtenteDao.registraUtente(docente, codice1);
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
    int codice2 = 8913;

    UtenteDao.registraUtente(studente, codice2);

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

    Quesito quesito = new Quesito();

    quesito.setDomanda(
        "Qual’è la differenza tra prodotto software generico e prodotto software specifico?");
    quesito.setData("21/12/2019");
    quesito.setRisposta("vuoto");
    quesito.setDocenti(docenti);

    QuesitoDao.addDomanda(quesito, 1, 2);
    ArrayList<Quesito> domande = QuesitoDao.getDomandeByIdUtente(2);

    quesito.setRisposta(
        "SW generici sono sistemi prodotti da una organizzazione e venduti a un mercato di massa, SW specifici sono sistemi commissionati da uno specifico utente e sviluppati specificatamente");

    quesito.setId(1);
    QuesitoDao.addRisposta(quesito);

    ArrayList<Quesito> risposte = QuesitoDao.getRisposteByLezione(1);

    Quesito risposta = null;
    Iterator<Quesito> iterator = risposte.iterator();

    while (iterator.hasNext()) {
      risposta = (Quesito) iterator.next();
    }

    assertEquals(risposta.getRisposta(), quesito.getRisposta());
    CorsoInsegnamentoDao.removeCorso(1);
    LezioneDao.removeLezione(1);
    QuesitoDao.removeQuesito(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testGetRisposteByIdUtente() throws Exception {
    System.out.println("TestGetRisposteByIdUtente");
    setUp();

    Utente docente = new Utente();

    docente.setNome("Andrea");
    docente.setCognome("DeLucia");
    docente.setTipo("docente");
    docente.setUsername("adelucia");
    docente.setMatricola("0512104731");
    docente.setEmail("a.vitiello59@studenti.unisa.it");
    docente.setPassword("0123456789");
    docente.setNazionalita("Italiana");
    int codice1 = 8912;

    UtenteDao.registraUtente(docente, codice1);
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
    int codice2 = 8913;

    UtenteDao.registraUtente(studente, codice2);

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

    Quesito quesito = new Quesito();

    quesito.setDomanda(
        "Qual’è la differenza tra prodotto software generico e prodotto software specifico?");
    quesito.setData("21/12/2019");
    quesito.setRisposta("vuoto");
    quesito.setDocenti(docenti);

    QuesitoDao.addDomanda(quesito, 1, 2);
    ArrayList<Quesito> domande = QuesitoDao.getDomandeByIdUtente(2);

    quesito.setRisposta(
        "SW generici sono sistemi prodotti da una organizzazione e venduti a un mercato di massa, SW specifici sono sistemi commissionati da uno specifico utente e sviluppati specificatamente");

    quesito.setId(1);
    QuesitoDao.addRisposta(quesito);

    ArrayList<Quesito> risposte = QuesitoDao.getRisposteByIdUtente(2);

    Quesito risposta = null;
    Iterator<Quesito> iterator = risposte.iterator();

    while (iterator.hasNext()) {
      risposta = (Quesito) iterator.next();
    }

    assertEquals(risposta.getRisposta(), quesito.getRisposta());
    CorsoInsegnamentoDao.removeCorso(1);
    LezioneDao.removeLezione(1);
    QuesitoDao.removeQuesito(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testGetDomandeByIdUtente() throws Exception {
    System.out.println("TestGetDomandeByIdUtente");
    setUp();

    Utente docente = new Utente();

    docente.setNome("Andrea");
    docente.setCognome("DeLucia");
    docente.setTipo("docente");
    docente.setUsername("adelucia");
    docente.setMatricola("0512104731");
    docente.setEmail("a.vitiello59@studenti.unisa.it");
    docente.setPassword("0123456789");
    docente.setNazionalita("Italiana");
    int codice1 = 8912;

    UtenteDao.registraUtente(docente, codice1);
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
    int codice2 = 8913;

    UtenteDao.registraUtente(studente, codice2);

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

    Quesito quesito = new Quesito();

    quesito.setDomanda(
        "Qual’è la differenza tra prodotto software generico e prodotto software specifico?");
    quesito.setData("21/12/2019");
    quesito.setRisposta("vuoto");
    quesito.setDocenti(docenti);

    QuesitoDao.addDomanda(quesito, 1, 2);

    ArrayList<Quesito> domande = QuesitoDao.getDomandeByIdUtente(1);

    Quesito domanda = null;
    Iterator<Quesito> iterator = domande.iterator();

    while (iterator.hasNext()) {
      domanda = (Quesito) iterator.next();
    }

    assertEquals(domanda.getRisposta(), quesito.getRisposta());
    CorsoInsegnamentoDao.removeCorso(1);
    LezioneDao.removeLezione(1);
    QuesitoDao.removeQuesito(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testGetDomandeByLezioneIdUtente() throws Exception {
    System.out.println("testGetDomandeByLezioneIdUtente");
    setUp();

    Utente docente = new Utente();

    docente.setNome("Andrea");
    docente.setCognome("DeLucia");
    docente.setTipo("docente");
    docente.setUsername("adelucia");
    docente.setMatricola("0512104731");
    docente.setEmail("a.vitiello59@studenti.unisa.it");
    docente.setPassword("0123456789");
    docente.setNazionalita("Italiana");
    int codice1 = 8912;

    UtenteDao.registraUtente(docente, codice1);
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
    int codice2 = 8913;

    UtenteDao.registraUtente(studente, codice2);

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

    Quesito quesito = new Quesito();

    quesito.setDomanda(
        "Qual’è la differenza tra prodotto software generico e prodotto software specifico?");
    quesito.setData("21/12/2019");
    quesito.setRisposta("vuoto");
    quesito.setDocenti(docenti);

    QuesitoDao.addDomanda(quesito, 1, 2);

    ArrayList<Quesito> domande = QuesitoDao.getDomandeByLezioneIdUtente(1, 1);

    Quesito domanda = null;
    Iterator<Quesito> iterator = domande.iterator();

    while (iterator.hasNext()) {
      domanda = (Quesito) iterator.next();
      assertEquals(domanda.getRisposta(), quesito.getRisposta());
    }

    CorsoInsegnamentoDao.removeCorso(1);
    LezioneDao.removeLezione(1);
    QuesitoDao.removeQuesito(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testGetCorsoByIdQuesito() throws Exception {
    System.out.println("testGetCorsoByIdQuesito");
    setUp();

    Utente docente = new Utente();

    docente.setNome("Andrea");
    docente.setCognome("DeLucia");
    docente.setTipo("docente");
    docente.setUsername("adelucia");
    docente.setMatricola("0512104731");
    docente.setEmail("a.vitiello59@studenti.unisa.it");
    docente.setPassword("0123456789");
    docente.setNazionalita("Italiana");
    int codice1 = 8912;

    UtenteDao.registraUtente(docente, codice1);
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
    int codice2 = 8913;

    UtenteDao.registraUtente(studente, codice2);

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

    Quesito quesito = new Quesito();

    quesito.setDomanda(
        "Qual’è la differenza tra prodotto software generico e prodotto software specifico?");
    quesito.setData("21/12/2019");
    quesito.setRisposta("vuoto");
    quesito.setDocenti(docenti);

    QuesitoDao.addDomanda(quesito, 1, 2);

    CorsoInsegnamento result = QuesitoDao.getCorsoByIdQuesito(1);

    assertEquals(result.getNome(), corso.getNome());

    CorsoInsegnamentoDao.removeCorso(1);
    LezioneDao.removeLezione(1);
    QuesitoDao.removeQuesito(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testGetQuesitoById() throws Exception {
    System.out.println("testGetQuesitoById");
    setUp();

    Utente docente = new Utente();

    docente.setNome("Andrea");
    docente.setCognome("DeLucia");
    docente.setTipo("docente");
    docente.setUsername("adelucia");
    docente.setMatricola("0512104731");
    docente.setEmail("a.vitiello59@studenti.unisa.it");
    docente.setPassword("0123456789");
    docente.setNazionalita("Italiana");
    int codice1 = 8912;

    UtenteDao.registraUtente(docente, codice1);
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
    int codice2 = 8913;

    UtenteDao.registraUtente(studente, codice2);

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

    Quesito quesito = new Quesito();

    quesito.setDomanda(
        "Qual’è la differenza tra prodotto software generico e prodotto software specifico?");
    quesito.setData("21/12/2019");
    quesito.setRisposta("vuoto");
    quesito.setDocenti(docenti);

    QuesitoDao.addDomanda(quesito, 1, 2);

    Quesito result = QuesitoDao.getQuesitoById(1);

    assertEquals(result.getDomanda(), quesito.getDomanda());

    CorsoInsegnamentoDao.removeCorso(1);
    LezioneDao.removeLezione(1);
    QuesitoDao.removeQuesito(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testGetLezioneByIdQuesito() throws Exception {
    System.out.println("testGetLezioneByIdQuesito");
    setUp();

    Utente docente = new Utente();

    docente.setNome("Andrea");
    docente.setCognome("DeLucia");
    docente.setTipo("docente");
    docente.setUsername("adelucia");
    docente.setMatricola("0512104731");
    docente.setEmail("a.vitiello59@studenti.unisa.it");
    docente.setPassword("0123456789");
    docente.setNazionalita("Italiana");
    int codice1 = 8912;

    UtenteDao.registraUtente(docente, codice1);
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
    int codice2 = 8913;

    UtenteDao.registraUtente(studente, codice2);

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

    Quesito quesito = new Quesito();

    quesito.setDomanda(
        "Qual’è la differenza tra prodotto software generico e prodotto software specifico?");
    quesito.setData("21/12/2019");
    quesito.setRisposta("vuoto");
    quesito.setDocenti(docenti);

    QuesitoDao.addDomanda(quesito, 1, 2);

    Lezione result = QuesitoDao.getLezioneByIdQuesito(1);

    assertEquals(result.getNome(), lezione.getNome());

    CorsoInsegnamentoDao.removeCorso(1);
    LezioneDao.removeLezione(1);
    QuesitoDao.removeQuesito(1);
    tearDown();
    System.out.println("\n");
  }

  @Test
  public final void testGetQuesitiByricerca() throws Exception {
    System.out.println("testGetQuesitiByricerca");
    setUp();

    Utente docente = new Utente();

    docente.setNome("Andrea");
    docente.setCognome("DeLucia");
    docente.setTipo("docente");
    docente.setUsername("adelucia");
    docente.setMatricola("0512104731");
    docente.setEmail("a.vitiello59@studenti.unisa.it");
    docente.setPassword("0123456789");
    docente.setNazionalita("Italiana");
    int codice1 = 8912;

    UtenteDao.registraUtente(docente, codice1);
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
    int codice2 = 8913;

    UtenteDao.registraUtente(studente, codice2);

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

    Quesito quesito = new Quesito();

    quesito.setDomanda(
        "Qual’è la differenza tra prodotto software generico e prodotto software specifico?");
    quesito.setData("21/12/2019");
    quesito.setRisposta("vuoto");
    quesito.setDocenti(docenti);

    QuesitoDao.addDomanda(quesito, 1, 2);
    ArrayList<Quesito> domande = QuesitoDao.getDomandeByIdUtente(2);

    quesito.setRisposta(
        "SW generici sono sistemi prodotti da una organizzazione e venduti a un mercato di massa, SW specifici sono sistemi commissionati da uno specifico utente e sviluppati specificatamente");

    quesito.setId(1);
    QuesitoDao.addRisposta(quesito);

    ArrayList<Quesito> results = QuesitoDao.getQuesitiByricerca("prodotto");

    Quesito risposta = null;
    Iterator<Quesito> iterator = results.iterator();

    while (iterator.hasNext()) {
      risposta = (Quesito) iterator.next();
      assertEquals(risposta.getRisposta(), quesito.getRisposta());
    }

    CorsoInsegnamentoDao.removeCorso(1);
    LezioneDao.removeLezione(1);
    QuesitoDao.removeQuesito(1);
    tearDown();
    System.out.println("\n");
  }
}
