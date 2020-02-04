package application_logic_layer.gestione_lezioni;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import storage_layer.LezioneDao;
/**
 * Servlet implementation class EliminaLezione
 *
 * <p>Gestisce l'eliminazione della lezione.
 *
 * @author UmbertoLibrera
 */
@WebServlet("/EliminaLezione")
public class EliminaLezione extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /** servlet.@see HttpServlet#HttpServlet() */
  public EliminaLezione() {
    super();
    // TODO Auto-generated constructor stub
  }

  /** servlet.@see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ArrayList<Lezione> lezioni = new ArrayList<Lezione>();

    try {
      final int id_lezione = Integer.parseInt(request.getParameter("id_lezione"));
      LezioneDao.removeLezione(id_lezione);

      final String id_corso = request.getParameter("id_corso");
      int id = Integer.parseInt(id_corso);
      final String nome_corso = request.getParameter("nome_corso");

      lezioni = LezioneDao.getListaLezioni(id);
      request.setAttribute("lezioni", lezioni);

      request.setAttribute("id_corso", id_corso);
      request.setAttribute("nome_corso", nome_corso);

      /*RequestDispatcher dispatcher =
          getServletContext().getRequestDispatcher("/gestione_lezioni/VisualizzaLezioneView.jsp");
      dispatcher.forward(request, response);*/
      
      response.sendRedirect("./gestione_lezioni/VisualizzaLezioneView.jsp");

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /** servlet.@see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    doGet(request, response);
  }
  
  @Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doPost(req, resp);
	}
}
