package application_logic_layer.gestione_utente;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import storage_layer.UtenteDao;
/**
 * Servlet implementation class ModificaPassword
 *
 * <p>Gestisce la modifica della password dell'utente.
 *
 * @author UmbertoLibrera
 */
@WebServlet("/ModificaPassword")
public class ModificaPassword extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**servlet. @see HttpServlet#HttpServlet() */
  public ModificaPassword() {
    super();
  }

  /**servlet. @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    doPost(request,response);
  }

  /**servlet. @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    final String vecchia_password = request.getParameter("vecchia_password");
    final String nuova_password = request.getParameter("nuova_password");
    String username = request.getParameter("username");

    //String generatedPassword = CryptWithMD5.cryptWithMD5(vecchia_password);

    Utente user = null;
    try {
      user = UtenteDao.getUtenteByUsername(username);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    System.out.println("User password = "+user.getPassword()+"\nGenerated Password "+vecchia_password);
    if (user.getPassword().equals(vecchia_password)) {
      user.setPassword(CryptWithMD5.cryptWithMD5(nuova_password));
      try {
        UtenteDao.resetPassword(user);
        /*RequestDispatcher dispatcher =
            getServletContext().getRequestDispatcher("/gestione_utente/NotificaModificaView.jsp");
        dispatcher.forward(request, response);*/
        response.sendRedirect("./gestione_utente/NotificaModificaView.jsp");
        System.out.println("2");
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    } else {
      request.setAttribute("flag", "modifica");
      System.out.println("1");
      /*RequestDispatcher dispatcher =
          getServletContext().getRequestDispatcher("/gestione_utente/NegatoResetView.jsp");
      dispatcher.forward(request, response);*/
      response.sendRedirect("./gestione_utente/NotificaModificaView.jsp");
    }
  }
  
  @Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
	  doPost(req, resp);
	}
}
