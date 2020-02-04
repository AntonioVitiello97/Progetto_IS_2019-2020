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
 * Servlet implementation class ConfermaRegistrazione
 *
 * <p>Gestisce la conferma della registrazione da parte dell'utente.
 *
 * @author AntonioVitiello
 */
@WebServlet("/ConfermaRegistrazione")
public class ConfermaRegistrazione extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /** servlet.@see HttpServlet#HttpServlet() */
  public ConfermaRegistrazione() {
    super();
    // TODO Auto-generated constructor stub
  }

  /** servlet.@see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String username = request.getParameter("username");

    int codice = Integer.parseInt(request.getParameter("codice"));

    try {

      Utente user = UtenteDao.getUtenteByUsername(username);

      System.out.println(user.toString());

      final int codice_db = UtenteDao.verificaCodice(user.getId());

      System.out.println("Codice conferma DB: " + codice_db);

      System.out.println("Codice conferma Get: " + codice);

      String email = user.getEmail();
      String dominio = email.substring(email.indexOf("@") + 1);

      UtenteDao.deleteCodiceUtente(codice_db);

      if (username.equals(user.getUsername())) {

        if (codice_db == codice) {

          UtenteDao.aggiornaUtente(user.getId(), dominio);

          /*RequestDispatcher dispatcher =
              getServletContext()
                  .getRequestDispatcher("/gestione_utente/ConfermaRegistrazioneView.jsp");
          dispatcher.forward(request, response);*/
          response.sendRedirect("./gestione_utente/ConfermaRegistrazioneView.jsp");
        } else {
          System.out.println("Codice non presente nel DB");
          RequestDispatcher dispatcher =
              getServletContext()
                  .getRequestDispatcher("/gestione_utente/NegataRegistrazioneView.jsp");
          dispatcher.forward(request, response);
          response.sendRedirect("./gestione_utente/NegataRegistrazioneView.jsp");
        }
      } else {
        System.out.println("Username non presente nel DB");
        /*RequestDispatcher dispatcher =
            getServletContext()
                .getRequestDispatcher("/gestione_utente/NegataRegistrazioneView.jsp");
        dispatcher.forward(request, response);*/
        response.sendRedirect("./gestione_utente/NegataRegistrazioneView.jsp");
      }
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
 		doGet(req, resp);
 	}
}
