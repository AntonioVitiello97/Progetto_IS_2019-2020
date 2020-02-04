<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,application_logic_layer.gestione_utente.Utente, application_logic_layer.gestione_corsi_insegnamento.CorsoInsegnamento, storage_layer.CorsoInsegnamentoDao"%>



<%
	Utente account = (Utente) request.getSession().getAttribute("account");

	Collection<CorsoInsegnamento> corsi = (Collection<CorsoInsegnamento>) request.getAttribute("corsi");
	Collection<CorsoInsegnamento> corsi_iscritti = (Collection<CorsoInsegnamento>) request
			.getAttribute("corsi_iscritti");

	if (corsi == null) {
		response.sendRedirect("../VisualizzaCorsi?action=corsi_di_studio");
		return;
	}
%>
<!DOCTYPE>
<html>
<head>
<link rel="stylesheet" href="./style.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UniQuestions</title>
</head>
<body>

	<header>

		<div id="Benvenuto" align="center">
			<%
				if (!account.getTipo().equals("admin")) {
			%>
			<a href="./gestione_utente/VisualizzaProfiloView.jsp"
				style="text-decoration: none; color: black;"> <%
 	}
 %> <i class="fa fa-user" style="font-size: 35"></i>
				<p>
					Benvenuto
					<%=account.getUsername()%></p>
				<form action="Logout" method="get">
					<input class="tastologout" type="submit" value="Logout">
				</form>
			</a>
		</div>



		<%
			if (!account.getUsername().equals("admin")) {
		%>
		<div id="contenitore_ricerca" align="center">
			<form action="RicercaAq" method="post">
				<h3>Ricerca AQ</h3>
				<input id="barra_ricerca" type="text" placeholder="Cerca"
					name="ricerca">
			</form>
		</div>

		<%
			}
		%>

	</header>



	<%
		if (account.getTipo().equals("studente")) {
	%>
	<div id="contenitore_link" align="center">
		<p>
			<a href="VisualizzaCorsi?action=<%="i_miei_corsi"%>">I miei
				corsi</a> | <a class="active"
				href="VisualizzaCorsi?action=<%="corsi_di_studio"%>">Corsi di
				studio</a> | <a href="VisualizzaRisposte">Risposte</a>
		</p>
	</div>
	<%
		}
	%>



	<div id="corsi_window">
		<ul>

			<%
				int flag = 0;
				if (corsi.size() > 0) {
					Iterator<CorsoInsegnamento> it = corsi.iterator();
					while (it.hasNext()) {
						CorsoInsegnamento corso = (CorsoInsegnamento) it.next();
						flag = 0;
			%>
			<li>
				<div class="row">
					<%
						for (CorsoInsegnamento corso_iscritto : corsi_iscritti) {
									if (corso.getNome().equals(corso_iscritto.getNome())) {
					%>
					<div id="first_box_element" align="left">
						<i class="fa fa-angle-right"></i> <a href=""><%=corso.getNome()%></a>
					</div>
					Prof:
					<%
						ArrayList<Utente> docenti = CorsoInsegnamentoDao.getListaDocentiByIdCorso(corso.getId());
										Iterator<Utente> iterator = docenti.iterator();
										while (iterator.hasNext()) {
											Utente docente = iterator.next();
					%>
					&nbsp;<%=docente.getNome()%><%=docente.getCognome()%>

					<%
						}
					%>


					<div style="float: right; font-family: futura">
						<a
							href="DisiscrizioneCorso?id_corso=<%=corso_iscritto.getId()%>&id_utente=<%=account.getId()%>"><input
							class="tastorispondi" type="submit" value="Disiscrivimi"></a>
					</div>
					<%
						flag = 1;
									}
								}
					%>


					<%
						if (flag != 1) {
					%>
					<div id="first_box_element" align="left">
						<i class="fa fa-angle-right"></i> <a href=""><%=corso.getNome()%></a>
					</div>
					Prof:
					<%
						ArrayList<Utente> docenti = CorsoInsegnamentoDao.getListaDocentiByIdCorso(corso.getId());
									Iterator<Utente> iterator = docenti.iterator();
									while (iterator.hasNext()) {
										Utente docente = iterator.next();
					%>

					&nbsp;<%=docente.getNome()%><%=docente.getCognome()%>


					<%
						}
					%>
					<%
						}
					%>


					<%
						if (flag != 1) {
					%>
					<div style="float: right; font-family: futura">
						<a
							href="IscrizioneCorso?id_corso=<%=corso.getId()%>&id_utente=<%=account.getId()%>"><input
							class="tastorispondi" type="submit" value="Iscrivimi"></a>
					</div>
					<%
						}
					%>



				</div>
			</li>

			<%
				}
				}
			%>
		</ul>
	</div>


	<div class="container2 signin2">
		&copy; 2019 UniQuestions Inc. All right reserved<br>
	</div>


</body>
</html>