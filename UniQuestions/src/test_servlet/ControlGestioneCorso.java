package test_servlet;

import junit.framework.TestCase;

import storage_layer.CorsoInsegnamentoDao;
import storage_layer.DriverManagerConnectionPool;
import storage_layer.UtenteDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import application_logic_layer.gestione_corsi_insegnamento.CorsoInsegnamento;
import application_logic_layer.gestione_corsi_insegnamento.DisiscrizioneCorso;
import application_logic_layer.gestione_corsi_insegnamento.EliminaCorso;
import application_logic_layer.gestione_corsi_insegnamento.InserisciCorso;
import application_logic_layer.gestione_corsi_insegnamento.IscrizioneCorso;
import application_logic_layer.gestione_corsi_insegnamento.VisualizzaCorsi;
import application_logic_layer.gestione_utente.Utente;

public class ControlGestioneCorso extends TestCase {

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
	public void testInserisciCorso() throws Exception
	{
		setUp();
		System.out.println("testInserisciCorso");
		request=new MockHttpServletRequest();
		response=new MockHttpServletResponse();
		
		Utente docente = new Utente();
		docente.setNome("Andrea");
		docente.setCognome("DeLucia");
		docente.setTipo("docente");
		docente.setUsername("adelucia");
		docente.setMatricola("0512104731");
		docente.setEmail("a.vitiello59@studenti.unisa.it");
		docente.setPassword("0123456789");
		docente.setNazionalita("Italiana");
		int codice2 = 8912;

		UtenteDao.registraUtente(docente, codice2);
		Utente utente1 = UtenteDao.getUtenteByUsername("adelucia");
		ArrayList<Utente> docenti = new ArrayList<Utente>();
		docenti.add(utente1);
		
		request.addParameter("nomeCorso", "Ingegneria del Software");
	    request.addParameter("itemCorsoDiLaurea", "Informatica - Triennale");
	    request.addParameter("itemDocenti", "Andrea DeLucia");
	    request.addParameter("itemAnnoAccademico", "2019/2020");
	    request.addParameter("itemAnnoDiStudi","Terzo");
	    request.addParameter("itemSemestre", "Primo");
	    
	    InserisciCorso inserisci = new InserisciCorso();
	    inserisci.service(request, response);
	   
		assertNotNull(CorsoInsegnamentoDao.getCorsoById(1));
		tearDown();
	}
	
	@Test
	public void testDisiscrizioneCorso() throws Exception
	{
		setUp();
		System.out.println("testDisiscrizioneCorso");
		request=new MockHttpServletRequest();
		response=new MockHttpServletResponse();
		
		Utente docente = new Utente();
		Utente studente = new Utente();
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
	    CorsoInsegnamentoDao.iscrizioneCorso(1, 1);
	    
	    
	    request.setParameter("id_corso", "1");
	    request.setParameter("id_utente", "1");
	    
	    DisiscrizioneCorso disiscrizione = new DisiscrizioneCorso();
	    disiscrizione.service(request, response);
	    
	    assertNotNull(CorsoInsegnamentoDao.getListaCorsiIscritti(1));
		//tearDown();
	}
	
	@Test
	public void testEliminaCorso() throws Exception
	{
		setUp();
		System.out.println("testEliminaCorso");
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
	    
	    EliminaCorso elimina = new EliminaCorso();
	    elimina.service(request, response);
	    
	    assertNull(CorsoInsegnamentoDao.getCorsoById(1));
	    tearDown();
	}
	
	@Test
	public void testIscrizioneCorso() throws Exception
	{
		setUp();
		System.out.println("testIscrizioneCorso");
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
	    
	    request.setParameter("id_corso", "1");
	    request.setParameter("id_utente", "2");
	    
	    IscrizioneCorso iscrivi = new IscrizioneCorso();
	    iscrivi.service(request, response);
	    
	    assertNotNull(CorsoInsegnamentoDao.getListaCorsiIscritti(2));
	    tearDown();
	}
	
	@Test
	public void testVisualizzaCorsi() throws Exception
	{
		setUp();
		System.out.println("testVisualizzaCorsi");
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
	    CorsoInsegnamentoDao.iscrizioneCorso(1, 1);
	    
	    request.getSession().setAttribute("account", studente);
	    request.setParameter("actions", "corsi_di_studio");
	    
	    VisualizzaCorsi visualizza = new VisualizzaCorsi();
	    visualizza.service(request, response);
	    
	    ArrayList<CorsoInsegnamento> corsi = new ArrayList<CorsoInsegnamento>();
	    request.setAttribute("corsi", corsi);
	    
	    assertNotNull(corsi.size());
	    tearDown();
	}
}
