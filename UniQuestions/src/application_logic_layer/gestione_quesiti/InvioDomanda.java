package application_logic_layer.gestione_quesiti;

import application_logic_layer.gestione_utente.Utente;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import storage_layer.QuesitoDao;
import storage_layer.UtenteDao;
/**
 * Servlet implementation class InvioDomanda
 *
 * <p>Gestisce l'invio della domanda al docente.
 *
 * @author AntonioVitiello
 */
@WebServlet("/InvioDomanda")
public class InvioDomanda extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /** servlet.@see HttpServlet#HttpServlet() */
  public InvioDomanda() {
    super();
    // TODO Auto-generated constructor stub
  }

  /** servlet.@see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    System.out.println("TEST");
    try {
      Utente user = (Utente) request.getSession().getAttribute("account");

      final int id_lezione = Integer.parseInt(request.getParameter("id_lezione"));
      final int id_utente = Integer.parseInt(request.getParameter("id_utente"));
      GregorianCalendar gc = new GregorianCalendar();
      final String data_odierna =
          gc.get(Calendar.DAY_OF_MONTH)
              + "/"
              + gc.get(Calendar.MONTH)
              + "/"
              + gc.get(Calendar.YEAR);
      System.out.println(gc.get(Calendar.MONTH));
      System.out.println(data_odierna);

      Quesito quesito = new Quesito();

      ArrayList<Utente> docenti = UtenteDao.getDocentiByLezioneId(id_lezione);

      Iterator<Utente> it = docenti.iterator();

      while (it.hasNext()) {
        Utente docente = it.next();
        System.out.println(docente.toString());
      }
      quesito.setDocenti(docenti);
      quesito.setDomanda(request.getParameter("domanda").toString());
      quesito.setData(data_odierna);
      QuesitoDao.addDomanda(quesito, id_lezione, id_utente);

      RequestDispatcher dispatcher =
          getServletContext()
              .getRequestDispatcher(
                  "/gestione_quesiti/NotificaInserimentoQuesitoDomanda.jsp?id_lezione="
                      + id_lezione);
      dispatcher.forward(request, response);
    } catch (SQLException e) {
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
