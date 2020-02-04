package application_logic_layer.gestione_utente;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import storage_layer.UtenteDao;
/**
 * Servlet implementation class Registrazione
 *
 * <p>Gestisce la registrazione dell'utente.
 *
 * @author AntonioVitiello
 */
@WebServlet("/Registrazione")
public class Registrazione extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**servlet. @see HttpServlet#HttpServlet() */
  public Registrazione() {
    super();
  }

  /**servlet. @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doPost(request, response);
  }

  /**servlet. @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) */
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // response.setContentType("text/html");

    Utente user = new Utente();
    user.setNome(request.getParameter("nome"));
    user.setCognome(request.getParameter("cognome"));
    user.setUsername(request.getParameter("username"));
    user.setEmail(request.getParameter("email"));
    user.setMatricola(request.getParameter("matricola"));
    user.setNazionalita(request.getParameter("nazionalita"));

    // Codifica in Hash Password

    String passwordToHash = request.getParameter("password");
    String generatedPassword = null;
    try {
      // Create MessageDigest instance for MD5
      MessageDigest md = MessageDigest.getInstance("MD5");
      // Add password bytes to digest
      md.update(passwordToHash.getBytes());
      // Get the hash's bytes
      byte[] bytes = md.digest();
      // This bytes[] has bytes in decimal format;
      // Convert it to hexadecimal format
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < bytes.length; i++) {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      // Get complete hashed password in hex format
      generatedPassword = sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    user.setPassword(generatedPassword);
    user.setTipo("non_verificato");

    Random random = new Random();
    int codice = random.nextInt(50000);
    System.out.println("Codice: " + codice);

    try {
      if (UtenteDao.registraUtente(user, codice) == false) {
        /*RequestDispatcher dispatcher =
            getServletContext()
                .getRequestDispatcher("/gestione_utente/NegataRegistrazioneView.jsp");
        dispatcher.forward(request, response);*/
        response.sendRedirect("./gestione_utente/NegataRegistrazioneView.jsp");
      } 
      else {
        String name = (String) request.getParameter("nome");
        String username = (String) request.getParameter("username");
        String surname = (String) request.getParameter("cognome");
        String mail = (String) request.getParameter("email");

        try {
          final String email = "uniquestionsplatform@gmail.com";
          final String password = "plaz seig rapc zehr";
          		
          Properties props = new Properties();
          props.put("mail.smtp.auth", "true");
          props.put("mail.smtp.starttls.enable", "true");
          props.put("mail.smtp.host", "smtp.gmail.com");
          props.put("mail.smtp.port", "587");
          Session session =
              Session.getInstance(
                  props,
                  new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                      return new PasswordAuthentication(email, password);
                    }
                  });
          Message message = new MimeMessage(session);
          message.setFrom(new InternetAddress(username));
          message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
          message.setSubject("Conferma Registrazione UniQuestions");
          message.setText(
              "Ciao "
                  + name
                  + ", \n"
                  + "Per confermare la registrazione vai al seguente link: http://localhost/UniQuestions/ConfermaRegistrazione?codice="
                  + codice
                  + "&username="
                  + username
                  + " \n Grazie!");

          Transport.send(message);
          /*request
              .getRequestDispatcher("/gestione_utente/NotificaRegistrazioneView.jsp")
              .forward(request, response);*/
          response.sendRedirect("./gestione_utente/NotificaRegistrazioneView.jsp");
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    } catch (SQLException e1) {
     /* RequestDispatcher dispatcher =
          getServletContext().getRequestDispatcher("/gestione_utente/NegataRegistrazioneView.jsp");
      dispatcher.forward(request, response);*/
      response.sendRedirect("./gestione_utente/NegataRegistrazioneView.jsp");
      System.out.println("Parametri non rispettati");
      e1.printStackTrace();
    }
  }
  @Override
 	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
 	{
 		doPost(req, resp);
 	}
}
