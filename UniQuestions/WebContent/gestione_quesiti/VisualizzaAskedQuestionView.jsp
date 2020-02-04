<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,application_logic_layer.gestione_utente.Utente, application_logic_layer.gestione_quesiti.Quesito, application_logic_layer.gestione_corsi_insegnamento.CorsoInsegnamento, storage_layer.QuesitoDao, application_logic_layer.gestione_lezioni.Lezione"%>

<%
	Utente account = (Utente) request.getSession().getAttribute("account");

	Collection<Quesito> quesiti_ricercati = (Collection<Quesito>) request.getAttribute("quesiti_ricercati");
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
				<form action="./Logout" method="get">
					<input class="tastologout" type="submit" value="Logout">
				</form>
			</a>
		</div>

		<div id="contenitore_ricerca" align="center">
			<form action="RicercaAq" method="post">
				<h3>Ricerca AQ</h3>
				<input id="barra_ricerca" type="text" placeholder="Cerca"
					name="ricerca">
			</form>
		</div>



		<%
			if (!account.getUsername().equals("admin") && account.getUsername().equals("docente")) {
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
				corsi</a> | <a href="VisualizzaCorsi?action=<%="corsi_di_studio"%>">Corsi
				di studio</a> | <a class="active" href="VisualizzaRisposte">Risposte</a>
		</p>
	</div>
	<%
		}
	%>

	<div id="corsi_window">
		<div id="divCorsi">
			<ul>
				<%
					if (quesiti_ricercati.size() > 0) {
						Iterator<Quesito> it = quesiti_ricercati.iterator();

						while (it.hasNext()) {
							Quesito quesito = (Quesito) it.next();
				%>
				<li>
					<div class="row">
						<div id="first_box_element" align="left">
							<%
								CorsoInsegnamento corso = QuesitoDao.getCorsoByIdQuesito(quesito.getId());
							%>

							<h4>Domanda</h4>
							<p><%=quesito.getDomanda()%></p>
							<h4>Risposta</h4>
							<p><%=quesito.getRisposta()%></p>
						</div>

						<div id="second_box_element" align="right">
							<h3><%=corso.getNome()%></h3>
							<p><%=quesito.getData()%></p>
						</div>




					</div>
				</li>
				<%
					}
					}
				%>
			</ul>
		</div>
	</div>

	<div class="container2 signin2">
		&copy; 2019 UniQuestions Inc. All right reserved<br>
	</div>

	<div id="racchiudi_facebook_instagram1" align="right">
		<a href="https://www.facebook.com/" target="_blank"><img
			src="images/facebook.png" width="20"></a> <a
			href="https://www.instagram.com/" target="_blank"><img
			src="images/instagram.png" width="19"></a>
	</div>
</body>
</html>