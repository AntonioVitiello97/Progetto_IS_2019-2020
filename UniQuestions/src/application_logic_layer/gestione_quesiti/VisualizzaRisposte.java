package application_logic_layer.gestione_quesiti;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application_logic_layer.gestione_utente.Utente;
import storage_layer.QuesitoDao;
/**
 * Servlet implementation class VisualizzaRisposte
 *
 * <p>Gestisce la visualizzazione delle risposte fatte dallo studente.
 *
 * @author AntonioVitiello
 */
@WebServlet("/VisualizzaRisposte")
public class VisualizzaRisposte extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**servlet. @see HttpServlet#HttpServlet() */
  public VisualizzaRisposte() {
    super();
    // TODO Auto-generated constructor stub
  }

  /** servlet.@see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Utente user = (Utente) request.getSession().getAttribute("account");

    ArrayList<Quesito> quesiti;

    try {
      quesiti = QuesitoDao.getRisposteByIdUtente(user.getId());
      request.setAttribute("quesiti_risposti", quesiti);
      /*RequestDispatcher dispatcher =
          getServletContext().getRequestDispatcher("/gestione_quesiti/VisualizzaRisposteView.jsp");
      dispatcher.forward(request, response);*/
      response.sendRedirect("./gestione_quesiti/VisualizzaRisposteView.jsp");

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /** servlet.@see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    doGet(request, response);
  }
  
  @Override
 	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
 	{
 		doPost(req, resp);
 	}
}
