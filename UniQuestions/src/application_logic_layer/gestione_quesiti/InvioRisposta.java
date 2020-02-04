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
 * Servlet implementation class InvioRisposta
 *
 * <p>Gestisce l'invio della risposta allo studente.
 *
 * @author AntonioVitiello
 */
@WebServlet("/InvioRisposta")
public class InvioRisposta extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /** servlet.@see HttpServlet#HttpServlet() */
  public InvioRisposta() {
    super();
    // TODO Auto-generated constructor stub
  }

  /** servlet.@see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String risposta = request.getParameter("risposta").toString();
    final String id_quesito = request.getParameter("id_quesito").toString();
    int id = Integer.parseInt(id_quesito);
    Quesito quesito = null;
    try {
      quesito = (Quesito) QuesitoDao.getQuesitoById(id);
      quesito.setRisposta(risposta);
      QuesitoDao.addRisposta(quesito);

      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/VisualizzaDomande");
      dispatcher.forward(request, response);
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
}
