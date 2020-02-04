package application_logic_layer.gestione_lezioni;

import application_logic_layer.gestione_utente.Utente;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import storage_layer.LezioneDao;

/**
 * Servlet implementation class InserisciValutazioneLezione
 *
 * <p>Gestisce l'inserimento della valutazione alla lezione.
 *
 * @author UmbertoLibrera
 */
@WebServlet("/InserisciValutazioneLezione")
public class InserisciValutazioneLezione extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**servlet. @see HttpServlet#HttpServlet() */
  public InserisciValutazioneLezione() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**servlet. @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
      final int id_lezione = Integer.parseInt(request.getParameter("id_lezione"));
      Utente utente = (Utente) request.getSession().getAttribute("account");

      int val = Integer.parseInt(request.getParameter("rate"));

      LezioneDao.addValutazioneLezione(utente.getId(), id_lezione, val);

      double media = LezioneDao.getMediaValutazioniById(id_lezione);

      System.out.println("Media: " + media);

      LezioneDao.updateValutazioneLezione(id_lezione, media);

      RequestDispatcher dispatcher =
          getServletContext()
              .getRequestDispatcher(
                  "/gestione_lezioni/NotificaInserimentoValutazione.jsp?id_lezione=" + id_lezione);
      dispatcher.forward(request, response);

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
		doGet(req, resp);
	}
}
