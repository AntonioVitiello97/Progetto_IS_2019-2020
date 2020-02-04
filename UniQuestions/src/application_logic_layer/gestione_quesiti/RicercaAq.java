package application_logic_layer.gestione_quesiti;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import storage_layer.QuesitoDao;
/**
 * Servlet implementation class RicercaAq
 *
 * <p>Gestisce la ricerca delle asked question.
 *
 * @author UmertoLibrera
 */
@WebServlet("/RicercaAq")
public class RicercaAq extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /** servlet.@see HttpServlet#HttpServlet() */
  public RicercaAq() {
    super();
    // TODO Auto-generated constructor stub
  }

  /** servlet.@see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String ricerca = request.getParameter("ricerca");

    try {

      request.setAttribute("quesiti_ricercati", QuesitoDao.getQuesitiByricerca(ricerca));

      /*RequestDispatcher dispatcher =
          getServletContext()
              .getRequestDispatcher("/gestione_quesiti/VisualizzaAskedQuestionView.jsp");
      dispatcher.forward(request, response);*/
      
      response.sendRedirect("./gestione_quesiti/VisualizzaAskedQuestionView.jsp");

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
