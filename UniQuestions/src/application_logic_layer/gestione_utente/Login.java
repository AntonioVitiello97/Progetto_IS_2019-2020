package application_logic_layer.gestione_utente;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import storage_layer.UtenteDao;
/**
 * Servlet implementation class Login
 *
 * <p>Gestisce il login dell'utente.
 *
 * @author AntonioVitiello
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**servlet. @see HttpServlet#HttpServlet() */
  public Login() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**servlet. @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
	  doPost(request,response);
  }

  /** servlet.@see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Utente user = new Utente();
    Utente user1 = new Utente();

    user.setUsername(request.getParameter("username"));

    String passwordToHash = request.getParameter("password");
    String generatedPassword = CryptWithMD5.cryptWithMD5(passwordToHash);

    user.setPassword(generatedPassword);

    try {
      user1 = UtenteDao.login(user);

      if (user.getUsername().equals(user1.getUsername())
          && user.getPassword().equals(user1.getPassword())
          && (!user1.getTipo().equals("non_verificato"))) {
        HttpSession session = request.getSession(true);
        session.setAttribute("currentSessionUser", user1);
        request.getSession().setAttribute("account", user1);
        System.out.println("Autenticazione Riuscita");
        //response.sendRedirect("gestione_corsi_insegnamento/VisualizzaCorsiView.jsp"); // logged-in page
        response.sendRedirect("./gestione_corsi_insegnamento/VisualizzaCorsiView.jsp");
      } else {
        System.out.println("Autenticazione NON Riuscita");
        //response.sendRedirect("gestione_utente/NegatoLoginView.jsp"); // error page
        response.sendRedirect("./gestione_utente/NegatoLoginView.jsp");
      }

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    //doGet(request, response);
  }
  
  @Override
 	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
 	{
 		doPost(req, resp);
 	}
}