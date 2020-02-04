package test_servlet;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

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
import application_logic_layer.gestione_corsi_insegnamento.VisualizzaCorsi;
import application_logic_layer.gestione_lezioni.AggiungiLezione;
import application_logic_layer.gestione_lezioni.EliminaLezione;
import application_logic_layer.gestione_lezioni.InserisciValutazioneLezione;
import application_logic_layer.gestione_lezioni.Lezione;
import application_logic_layer.gestione_utente.Utente;
import junit.framework.TestCase;
import storage_layer.CorsoInsegnamentoDao;
import storage_layer.DriverManagerConnectionPool;
import storage_layer.LezioneDao;
import storage_layer.UtenteDao;

public class ControlGestioneLezione extends TestCase {
	
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
	public void testAggiungiLezione() throws Exception
	{
		setUp();
		System.out.println("testAggiungiLezione");
		request=new MockHttpServletRequest();
		response=new MockHttpServletResponse();
		
		Utente docente = new Utente();
		CorsoInsegnamento corso = new CorsoInsegnamento();
		
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
		
		request.setParameter("id_corso", "1");
		request.setParameter("nomeLezione", "Scenari e casi d'uso");
		request.setParameter("dataLezione", "21/09/2019");
		request.setParameter("argomentoLezione", "Scenario visionary, as is, training, casi d’uso, diagrammi dei casi d’uso");
		
		AggiungiLezione aggiungi = new AggiungiLezione();
		aggiungi.service(request, response);
		
		assertNotNull(LezioneDao.getLezioneById(1));
		tearDown();
	}

	@Test
	public void testEliminaLezione() throws Exception
	{
		setUp();
		System.out.println("testEliminaLezione");
		request=new MockHttpServletRequest();
		response=new MockHttpServletResponse();
		
		Utente docente = new Utente();
		CorsoInsegnamento corso = new CorsoInsegnamento();
		
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
		
		request.setParameter("id_lezione", "1");
		request.setParameter("id_corso", "1");
		request.setParameter("nome_corso", "Ingegneria del Software");
		
		EliminaLezione elimina = new EliminaLezione();
		elimina.service(request, response);
		
		ArrayList<Lezione> lezioni = LezioneDao.getListaLezioni(1);

	    Lezione lezione1 = null;
	    Iterator<Lezione> i = lezioni.iterator();

	    while (i.hasNext()) {
	      lezione1 = (Lezione) i.next();
	    }

	    assertNull(lezione1);
		
		tearDown();
	}
	
	@Test
	public void testInserisciValutazioneLezione() throws Exception
	{
		setUp();
		System.out.println("testEliminaLezione");
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
		request.setParameter("rate", "5");
		
		InserisciValutazioneLezione valutazione = new InserisciValutazioneLezione();
		valutazione.service(request, response);
		
		int result = (int) LezioneDao.getMediaValutazioniById(1);
		
		assertNotEquals(result, 5);
		tearDown();
	}
	
	@Test
	public void testVisualizzaLezioni() throws Exception
	{
		setUp();
		System.out.println("testVisualizzaLezioni");
		request=new MockHttpServletRequest();
		response=new MockHttpServletResponse();
		
		Utente studente = new Utente();
		Utente docente = new Utente();
		CorsoInsegnamento corso = new CorsoInsegnamento();
		
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
		
		request.setParameter("id_corso", "1");
		request.setParameter("nome_corso", "Ingegneria del Software");
	    
	    assertNotNull(LezioneDao.getListaLezioni(1));
	    tearDown();
	}
}
