package application_logic_layer.gestione_utente;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class Logout
 *
 * <p>Gestisce il logout dell'utente.
 *
 * @author AntonioVitiello
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**servlet. @see HttpServlet#HttpServlet() */
  public Logout() {
    super();
  }

  /**servlet. @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.getSession().removeAttribute("account");
    request.getSession().invalidate();

    String redirectedPage = "/index.jsp";
    response.sendRedirect(request.getContextPath() + redirectedPage);
  }

  /**servlet. @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    doGet(request, response);
  }
}
