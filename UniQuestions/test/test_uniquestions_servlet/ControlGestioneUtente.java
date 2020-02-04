package test_servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import application_logic_layer.gestione_utente.ConfermaRegistrazione;
import application_logic_layer.gestione_utente.CryptWithMD5;
import application_logic_layer.gestione_utente.Login;
import application_logic_layer.gestione_utente.Logout;
import application_logic_layer.gestione_utente.ModificaPassword;
import application_logic_layer.gestione_utente.Registrazione;
import application_logic_layer.gestione_utente.ResetPassword;
import application_logic_layer.gestione_utente.Utente;
import junit.framework.TestCase;
import storage_layer.DriverManagerConnectionPool;
import storage_layer.UtenteDao;

public class ControlGestioneUtente extends TestCase {
	
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
	  public void testRegistrazione() throws Exception
	  {
		  setUp();
		  System.out.println("testRegistrazione");
		  request=new MockHttpServletRequest();
		  response=new MockHttpServletResponse();
		 
		    
		 request.setParameter("nome", "Antonio");
		 request.setParameter("cognome", "Vitiello");
		 request.setParameter("username", "a.vitiello59");
		 request.setParameter("email", "a.vitiello59@studenti.unisa.it");
		 request.setParameter("matricola", "05124731");
		 request.setParameter("nazionalita", "Italia");
		 request.setParameter("password", "prova123");
		 
		 Registrazione reg = new Registrazione();
		 reg.service(request, response);
		 
		 assertNotNull(UtenteDao.getUtenteById(1));
		 
		 tearDown();
		  
	  }
	  
	  @Test
	  public void testConfermaRegistrazione() throws Exception
	  {
		  setUp();
		  System.out.println("testConfermaRegistrazione");
		  request=new MockHttpServletRequest();
		  response=new MockHttpServletResponse();
		 
		    
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
		 
		 
		 
		 request.setParameter("username", "a.vitiello59");

		 request.setParameter("codice", "8912");
		 
		 ConfermaRegistrazione confreg = new ConfermaRegistrazione();
		 confreg.service(request, response);
		 
		 String tipo = UtenteDao.getUtenteById(1).getTipo();
		 assertEquals(tipo, "studente");
		 
		 tearDown();
	  }
	  
	  @Test
	  public void testResetPassword() throws Exception
	  {
		  setUp();
		  System.out.println("testResetPassword");
		  request=new MockHttpServletRequest();
		  response=new MockHttpServletResponse();
		 
		    
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
		String vecchia_pass = UtenteDao.getUtenteById(1).getPassword(); 
		
		 request.setParameter("username", "a.vitiello59");

		 request.setParameter("email", "a.vitiello59@studenti.unisa.it");
		 
		 ResetPassword reset = new ResetPassword();
		 reset.service(request, response);
		 
		 String nuova_pass = UtenteDao.getUtenteById(1).getPassword();
		 assertEquals(vecchia_pass, nuova_pass);
		 
		 tearDown();
	  }
	  
	  @Test
	  public void testLogin() throws Exception
	  {
		  setUp();
		  System.out.println("testLogin");
		  request=new MockHttpServletRequest();
		  response=new MockHttpServletResponse();
		 
		    
		  Utente utente = new Utente();

		  utente.setNome("Antonio");
		  utente.setCognome("Vitiello");
		  utente.setTipo("studente");
		  utente.setUsername("a.vitiello59");
		  utente.setMatricola("0512104731");
		  utente.setEmail("a.vitiello59@studenti.unisa.it");
		  utente.setPassword("e32ae4e0d9158c00684ec73ce7803ab1");
		  utente.setNazionalita("Italiana");
		  
		  int codice = 8912;
		  UtenteDao.registraUtente(utente, codice);
		
		 request.setParameter("username", "a.vitiello59");

		 request.setParameter("password", "prova123");
		 
		 Login login = new Login();
		 login.service(request, response);
		 
		 Utente account = (Utente) request.getSession().getAttribute("account");
		 
		 assertEquals(account.getCognome(), "Vitiello");
		 
		 tearDown();
	  }
	  
	  @Test
	  public void testLogout() throws Exception
	  {
		  setUp();
		  System.out.println("testLogout");
		  request=new MockHttpServletRequest();
		  response=new MockHttpServletResponse();
		 
		    
		  Utente utente = new Utente();

		  utente.setNome("Antonio");
		  utente.setCognome("Vitiello");
		  utente.setTipo("studente");
		  utente.setUsername("a.vitiello59");
		  utente.setMatricola("0512104731");
		  utente.setEmail("a.vitiello59@studenti.unisa.it");
		  utente.setPassword("e32ae4e0d9158c00684ec73ce7803ab1");
		  utente.setNazionalita("Italiana");
		  
		  int codice = 8912;
		  UtenteDao.registraUtente(utente, codice);

		  UtenteDao.login(utente);
		  
		  Logout logout = new Logout();
		  logout.service(request, response);
		  
		  Utente account = (Utente) request.getSession().getAttribute("account");
		  
		  assertNull(account);
		  
		  tearDown();
		  
	  }
	  
	  @Test
	  public void testModificaPassword() throws Exception
	  {
		  setUp();
		  System.out.println("testModificaPassword");
		  request=new MockHttpServletRequest();
		  response=new MockHttpServletResponse();
		 
		    
		  Utente utente = new Utente();

		  utente.setNome("Antonio");
		  utente.setCognome("Vitiello");
		  utente.setTipo("studente");
		  utente.setUsername("a.vitiello59");
		  utente.setMatricola("0512104731");
		  utente.setEmail("a.vitiello59@studenti.unisa.it");
		  utente.setPassword("prova123");
		  utente.setNazionalita("Italiana");
		  
		  int codice = 8912;
		 UtenteDao.registraUtente(utente, codice);
		 
		 request.setParameter("username", "a.vitiello59");
		 request.setParameter("vecchia_password", "prova123");
		 request.setParameter("nuova_password", "admin1");
		 
		 ModificaPassword modifica = new ModificaPassword();
		 modifica.service(request, response);
		 
		 assertEquals(UtenteDao.getUtenteById(1).getPassword(), CryptWithMD5.cryptWithMD5("admin1"));
		 tearDown();
	  }
}
