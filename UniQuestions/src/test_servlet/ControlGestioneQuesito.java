package test_servlet;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import application_logic_layer.gestione_corsi_insegnamento.CorsoInsegnamento;
import application_logic_layer.gestione_lezioni.Lezione;
import application_logic_layer.gestione_quesiti.InvioDomanda;
import application_logic_layer.gestione_quesiti.InvioRisposta;
import application_logic_layer.gestione_quesiti.Quesito;
import application_logic_layer.gestione_quesiti.RicercaAq;
import application_logic_layer.gestione_quesiti.VisualizzaDomande;
import application_logic_layer.gestione_quesiti.VisualizzaRisposte;
import application_logic_layer.gestione_utente.Utente;
import junit.framework.TestCase;
import storage_layer.CorsoInsegnamentoDao;
import storage_layer.DriverManagerConnectionPool;
import storage_layer.LezioneDao;
import storage_layer.QuesitoDao;
import storage_layer.UtenteDao;

import java.util.*;

public class ControlGestioneQuesito extends TestCase {

	MockHttpServletRequest request;
	MockHttpServletResponse response;
	
	Connection connection = null;
	
	
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
	  public void testInvioDomanda() throws Exception
	  {
		  setUp();
		  System.out.println("testInvioDomanda");
		  request=new MockHttpServletRequest();
		  response=new MockHttpServletResponse();
		  
		  Utente docente = new Utente();
		  Utente studente = new Utente();
		  CorsoInsegnamento corso = new CorsoInsegnamento();
			
		  studente.setNome("Antonio");
		  studente.setCognome("Vitiello");
		  studente.setTipo("studente");
		  studente.setUsername("a.vitiello59");
		  studente.setMatricola("0512104732");
		  studente.setEmail("a.vitiello59@studenti.unisa.it");
		  studente.setPassword("0123456789");
		  studente.setNazionalita("Italiana");
		  int codice2 = 8912;
		    
		  UtenteDao.registraUtente(studente, codice2);
			
		  docente.setNome("Andrea");
		  docente.setCognome("DeLucia");
	      docente.setTipo("docente");
		  docente.setUsername("adelucia");
		  docente.setMatricola("0512104731");
		  docente.setEmail("a.vitiello59@studenti.unisa.it");
			docente.setPassword("0123456789");
			docente.setNazionalita("Italiana");
			int codice = 8911;

			UtenteDao.registraUtente(docente, codice);
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

			Lezione lezione = new Lezione();

			lezione.setNome("Scenari e casi d'uso");
			lezione.setData("21/09/2019");
			lezione.setDescrizione("Scenario visionary, as is, training, casi d’uso, diagrammi dei casi d’uso");

			LezioneDao.addLezione(lezione, 1);
			
		   request.getSession().setAttribute("account", studente);
		   request.setParameter("id_lezione", "1");
		   request.setParameter("id_utente", "1");
		   request.setParameter("domanda", "Qual è la differenza tra scenario visionary e training?");
		   
		   InvioDomanda invio = new InvioDomanda();
		   invio.service(request, response);
		   
		   assertNotNull(QuesitoDao.getDomandeByIdUtente(1));
		   tearDown();
	  }
	  
	  public void testInvioRisposta() throws Exception
	  {
		  setUp();
		  System.out.println("testInvioRisposta");
		  request=new MockHttpServletRequest();
		  response=new MockHttpServletResponse();
		  
		  Utente docente = new Utente();
		  Utente studente = new Utente();
		  CorsoInsegnamento corso = new CorsoInsegnamento();
			
		  studente.setNome("Antonio");
		  studente.setCognome("Vitiello");
		  studente.setTipo("studente");
		  studente.setUsername("a.vitiello59");
		  studente.setMatricola("0512104732");
		  studente.setEmail("a.vitiello59@studenti.unisa.it");
		  studente.setPassword("0123456789");
		  studente.setNazionalita("Italiana");
		  int codice2 = 8912;
		    
		  UtenteDao.registraUtente(studente, codice2);
			
		  docente.setNome("Andrea");
		  docente.setCognome("DeLucia");
	      docente.setTipo("docente");
		  docente.setUsername("adelucia");
		  docente.setMatricola("0512104731");
		  docente.setEmail("a.vitiello59@studenti.unisa.it");
			docente.setPassword("0123456789");
			docente.setNazionalita("Italiana");
			int codice = 8911;

			UtenteDao.registraUtente(docente, codice);
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

			Lezione lezione = new Lezione();

			lezione.setNome("Scenari e casi d'uso");
			lezione.setData("21/09/2019");
			lezione.setDescrizione("Scenario visionary, as is, training, casi d’uso, diagrammi dei casi d’uso");

			LezioneDao.addLezione(lezione, 1);
			
			Quesito quesito = new Quesito();

		    quesito.setDomanda("Qual è la differenza tra scenario visionary e training?");
		    quesito.setData("21/12/2019");
		    quesito.setRisposta("vuoto");
		    quesito.setDocenti(docenti);

		    QuesitoDao.addDomanda(quesito, 1, 1);
		    
		    request.setParameter("risposta", "SW generici sono sistemi prodotti da una organizzazione e venduti a un mercato di massa, SW specifici sono sistemi commissionati da uno specifico utente e sviluppati specificatamente");
		   request.setParameter("id_quesito", "1");
		   
		   InvioRisposta invio = new InvioRisposta();
		   invio.service(request, response);
		   
		   assertNotNull(QuesitoDao.getRisposteByIdUtente(1));
		   tearDown();
	  }
	  
	  public void testRicercaAq() throws Exception
	  {
		  setUp();
		  System.out.println("testRicercaAq");
		  request=new MockHttpServletRequest();
		  response=new MockHttpServletResponse();
		  
		  Utente docente = new Utente();
		  Utente studente = new Utente();
		  CorsoInsegnamento corso = new CorsoInsegnamento();
			
		  studente.setNome("Antonio");
		  studente.setCognome("Vitiello");
		  studente.setTipo("studente");
		  studente.setUsername("a.vitiello59");
		  studente.setMatricola("0512104732");
		  studente.setEmail("a.vitiello59@studenti.unisa.it");
		  studente.setPassword("0123456789");
		  studente.setNazionalita("Italiana");
		  int codice2 = 8912;
		    
		  UtenteDao.registraUtente(studente, codice2);
			
		  docente.setNome("Andrea");
		  docente.setCognome("DeLucia");
	      docente.setTipo("docente");
		  docente.setUsername("adelucia");
		  docente.setMatricola("0512104731");
		  docente.setEmail("a.vitiello59@studenti.unisa.it");
			docente.setPassword("0123456789");
			docente.setNazionalita("Italiana");
			int codice = 8911;

			UtenteDao.registraUtente(docente, codice);
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

			Lezione lezione = new Lezione();

			lezione.setNome("Scenari e casi d'uso");
			lezione.setData("21/09/2019");
			lezione.setDescrizione("Scenario visionary, as is, training, casi d’uso, diagrammi dei casi d’uso");

			LezioneDao.addLezione(lezione, 1);
			
			Quesito quesito = new Quesito();

		    quesito.setDomanda(
		        "Qual’è la differenza tra prodotto software generico e prodotto software specifico?");
		    quesito.setData("21/12/2019");
		    quesito.setRisposta("vuoto");
		    quesito.setDocenti(docenti);

		    QuesitoDao.addDomanda(quesito, 1, 1);
		    ArrayList<Quesito> domande = QuesitoDao.getDomandeByIdUtente(1);

		    quesito.setRisposta(
		        "SW generici sono sistemi prodotti da una organizzazione e venduti a un mercato di massa, SW specifici sono sistemi commissionati da uno specifico utente e sviluppati specificatamente");

		    quesito.setId(1);
		    QuesitoDao.addRisposta(quesito);
		    
		    request.setParameter("ricerca", "prodotto");
		    
		    RicercaAq ricerca = new RicercaAq();
		    ricerca.service(request, response);
		    
		    /*ArrayList<Quesito> quesiti_ricercati = QuesitoDao.getQuesitiByricerca("prodotto");*/
		    Collection<Quesito> quesiti_ricercati = (Collection<Quesito>) request.getAttribute("quesiti_ricercati");
		    Quesito risposta = null;
		    Iterator<Quesito> iterator = quesiti_ricercati.iterator();

		    while (iterator.hasNext()) {
		      risposta = (Quesito) iterator.next();
		      assertEquals(risposta.getRisposta(), quesito.getRisposta());
		    }
		    
		    tearDown();
	  }
	  
	  public void testVisualizzaDomande() throws Exception
	  {
		  setUp();
		  System.out.println("testVisualizzaDomande");
		  request=new MockHttpServletRequest();
		  response=new MockHttpServletResponse();
		  
		  Utente docente = new Utente();
		  Utente studente = new Utente();
		  CorsoInsegnamento corso = new CorsoInsegnamento();
			
		  studente.setNome("Antonio");
		  studente.setCognome("Vitiello");
		  studente.setTipo("studente");
		  studente.setUsername("a.vitiello59");
		  studente.setMatricola("0512104732");
		  studente.setEmail("a.vitiello59@studenti.unisa.it");
		  studente.setPassword("0123456789");
		  studente.setNazionalita("Italiana");
		  int codice2 = 8912;
		    
		  UtenteDao.registraUtente(studente, codice2);
			
		  docente.setNome("Andrea");
		  docente.setCognome("DeLucia");
	      docente.setTipo("docente");
		  docente.setUsername("adelucia");
		  docente.setMatricola("0512104731");
		  docente.setEmail("a.vitiello59@studenti.unisa.it");
			docente.setPassword("0123456789");
			docente.setNazionalita("Italiana");
			int codice = 8911;

			UtenteDao.registraUtente(docente, codice);
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

			Lezione lezione = new Lezione();

			lezione.setNome("Scenari e casi d'uso");
			lezione.setData("21/09/2019");
			lezione.setDescrizione("Scenario visionary, as is, training, casi d’uso, diagrammi dei casi d’uso");

			LezioneDao.addLezione(lezione, 1);
			
			Quesito quesito = new Quesito();

		    quesito.setDomanda(
		        "Qual’è la differenza tra prodotto software generico e prodotto software specifico?");
		    quesito.setData("21/12/2019");
		    quesito.setRisposta("vuoto");
		    quesito.setDocenti(docenti);

		    QuesitoDao.addDomanda(quesito, 1, 1);
		    ArrayList<Quesito> domande = QuesitoDao.getDomandeByIdUtente(1);
		    
		    request.getSession().setAttribute("account", studente);
		    
		    VisualizzaDomande visualizza = new VisualizzaDomande();
		    visualizza.service(request, response);
		    
		    Collection<Quesito> quesiti = (Collection<Quesito>) request.getAttribute("quesiti");
		    Iterator<Quesito> iterator = quesiti.iterator();
		    Quesito risposta = null;
		    

		    while (iterator.hasNext()) {
		      risposta = (Quesito) iterator.next();
		      assertEquals(risposta.getRisposta(), quesito.getRisposta());
		    }
		    
		    tearDown();
	  }
	  
	  public void testVisualizzaRisposte() throws Exception
	  {
		  setUp();
		  System.out.println("testVisualizzaRisposte");
		  request=new MockHttpServletRequest();
		  response=new MockHttpServletResponse();
		  
		  Utente docente = new Utente();
		  Utente studente = new Utente();
		  CorsoInsegnamento corso = new CorsoInsegnamento();
			
		  studente.setNome("Antonio");
		  studente.setCognome("Vitiello");
		  studente.setTipo("studente");
		  studente.setUsername("a.vitiello59");
		  studente.setMatricola("0512104732");
		  studente.setEmail("a.vitiello59@studenti.unisa.it");
		  studente.setPassword("0123456789");
		  studente.setNazionalita("Italiana");
		  int codice2 = 8912;
		    
		  UtenteDao.registraUtente(studente, codice2);
			
		  docente.setNome("Andrea");
		  docente.setCognome("DeLucia");
	      docente.setTipo("docente");
		  docente.setUsername("adelucia");
		  docente.setMatricola("0512104731");
		  docente.setEmail("a.vitiello59@studenti.unisa.it");
			docente.setPassword("0123456789");
			docente.setNazionalita("Italiana");
			int codice = 8911;

			UtenteDao.registraUtente(docente, codice);
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

			Lezione lezione = new Lezione();

			lezione.setNome("Scenari e casi d'uso");
			lezione.setData("21/09/2019");
			lezione.setDescrizione("Scenario visionary, as is, training, casi d’uso, diagrammi dei casi d’uso");

			LezioneDao.addLezione(lezione, 1);
			
			Quesito quesito = new Quesito();

		    quesito.setDomanda(
		        "Qual’è la differenza tra prodotto software generico e prodotto software specifico?");
		    quesito.setData("21/12/2019");
		    quesito.setRisposta("vuoto");
		    quesito.setDocenti(docenti);

		    QuesitoDao.addDomanda(quesito, 1, 1);
		    ArrayList<Quesito> domande = QuesitoDao.getDomandeByIdUtente(1);
		    
		    request.getSession().setAttribute("account", studente);
		    
		    VisualizzaRisposte visualizza = new VisualizzaRisposte();
		    visualizza.service(request, response);
		    
		    Collection<Quesito> quesiti = (Collection<Quesito>) request.getAttribute("quesiti_risposti");
		    Iterator<Quesito> iterator = quesiti.iterator();
		    Quesito risposta = null;
		    

		    while (iterator.hasNext()) {
		      risposta = (Quesito) iterator.next();
		      assertEquals(risposta.getRisposta(), quesito.getRisposta());
		    }
		    
		    tearDown();
	  }
}
